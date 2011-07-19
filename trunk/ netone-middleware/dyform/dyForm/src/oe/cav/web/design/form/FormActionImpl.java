package oe.cav.web.design.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.FormExtendInfo;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.design.form.template.CreateTemplate;
import oe.cav.web.design.form.template.ListTemplate;
import oe.cav.web.design.form.template.ModifyTemplate;
import oe.cav.web.design.form.template.QueryTemplate;
import oe.cav.web.design.form.template.ReadMeTemplate;
import oe.cav.web.util.CommonIoTools;
import oe.cav.web.util.SearchObj;
import oe.cav.web.util.ZipUtil;
import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;

import org.apache.commons.beanutils.BeanUtils;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.BaseAction;

public class FormActionImpl extends BaseAction {

	protected boolean validateLogon() {
		return false;
	}

	public String onCreateviewTemplate(ActionEvent ae) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");

		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");

		String basePath = ae.getRequest().getScheme() + "://"
				+ ae.getRequest().getServerName() + ":"
				+ ae.getRequest().getServerPort();

		String formcode = (String) ae.getParameter("formcode");
		String tempPath = "dytemplate";
		File file = new File(tempPath);
		if (!file.exists()) {
			file.mkdir();
		}
		String tmppath = tempPath + "/" + System.currentTimeMillis();
		File tmppathFi = new File(tmppath);
		tmppathFi.mkdir();

		OutputStream createXmlOut = new FileOutputStream(tmppath
				+ "/Create.jsp");
		InputStream createXmlIn = CreateTemplate
				.todo(ae.getRequest(), basePath);
		CommonIoTools.inputDo(createXmlIn, createXmlOut);

		OutputStream modifyXmlOut = new FileOutputStream(tmppath
				+ "/Modify.jsp");
		InputStream modifyXmlIn = ModifyTemplate
				.todo(ae.getRequest(), basePath);
		CommonIoTools.inputDo(modifyXmlIn, modifyXmlOut);

		OutputStream queryXmlOut = new FileOutputStream(tmppath + "/Query.jsp");
		InputStream queryXmlIn = QueryTemplate.todo(ae.getRequest(), basePath);
		CommonIoTools.inputDo(queryXmlIn, queryXmlOut);

		OutputStream listXmlOut = new FileOutputStream(tmppath + "/List.jsp");
		InputStream ListXmlIn = ListTemplate.todo(ae.getRequest(), dysc
				.fetchColumnList(formcode));
		CommonIoTools.inputDo(ListXmlIn, listXmlOut);

		OutputStream readmeXmlOut = new FileOutputStream(tmppath
				+ "/ReadMe.txt");
		InputStream readmeXmlIn = ReadMeTemplate.todo(ae.getRequest(), dysc
				.fetchColumnList(formcode));
		CommonIoTools.inputDo(readmeXmlIn, readmeXmlOut);

		String zipFile = tmppath + ".zip";
		ZipUtil.zip(zipFile, tmppath, false);
		InputStream zip = new FileInputStream(zipFile);

		// TemplateOperation.download(ae.getResponse(), htmlInfo, formcode);

		ae.getResponse().setContentType("text/html; charset=GBK");
		ae.getResponse().setContentType("application/x-msdownload");
		ae.getResponse().setHeader("Content-Disposition",
				"attachment; filename=" + formcode + ".zip");
		try {
			CommonIoTools.inputDo(zip, ae.getResponse().getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String onAddToPermission(ActionEvent ae) throws Exception {
		// 获得资源ID
		String resourceid = ae.getParameter("rsid");
		// 资源操作句柄
		ResourceRmi resource = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = resource.loadResourceById(resourceid);
		if (ProtectedObjectReference._OBJ_INCLUSTION_YES.equals(upo
				.getInclusion())) {
			WebTip.htmlInfo("非表单无法创建权限", true, ae.getResponse());
			return null;
		}
		// 先清除所有已有的字段资源
		resource.dropAllSubResource(resourceid);
		if ("yes".equals(ae.getParameter("remove"))) {
			WebTip.htmlInfo("清除权限成功", true, ae.getResponse());
			return null;
		}

		String naturalname = upo.getNaturalname();
		String formcode = upo.getExtendattribute();

		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");

		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		// 创建子记录
		List list = dysc.fetchColumnList(formcode);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			String columncode = object.getColumncode();
			String columnname = object.getColumname();
			Long aggrigation = object.getIndexvalue();

			UmsProtectedobject upoNew = new UmsProtectedobject();
			upoNew.setNaturalname(columncode);
			upoNew.setName(columnname);
			upoNew.setAggregation(aggrigation);
			upoNew.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_NO);
			resource.addResource(upoNew, naturalname);
		}

		WebTip.htmlInfo("创建权限成功", true, ae.getResponse());

		return null;
	}

	private SearchObj setListInfo(ActionEvent ae) {

		FormForm form = (FormForm) ae.getForm();
		String systemid = form.getSystemid();
		if (systemid == null || systemid.equals("")) {
			systemid = "oec";
		}
		TCsForm busForm = new TCsForm();
		busForm.setSystemid(systemid);
		busForm.setStatusinfo(FormExtendInfo._STATUS_NORMAL);
		// 查询对象设置
		SearchObj search = new SearchObj();
		search.setSearchobj(busForm);
		search.setSql(" order by created,designer desc");

		return search;
	}

	public String onCreateview(ActionEvent ae) throws Exception {

		FormForm form = (FormForm) ae.getForm();
		TCsForm busForm = new TCsForm();

		busForm.setSystemid(form.getSystemid());
		busForm.setListinfo(FormExtendInfo._DEFAULT_LIST_COLUMN);
		busForm.setButinfo(FormExtendInfo._DEFAULT_LIST_BUT);
		busForm.setOrderinfo(FormExtendInfo._DEFAULT_ORDER);
		busForm.setViewbutinfo(FormExtendInfo._DEFAULT_VIEW_BUT);

		BeanUtils.copyProperties(form, busForm);
		form.setDefaultbut(FormExtendInfo._DEFAULT_LIST_BUT);
		form.setDefaultviewbut(FormExtendInfo._DEFAULT_VIEW_BUT);
		// TCsForm system = new TCsForm();
		// system.setSystemid(form.getSystemid());
		// ae.setAttribute("subform", formDao.queryObjects(system));
		return "createview";
	}

	private void flitFormBySelf(List listform, String formcode) {

		for (Iterator itr = listform.iterator(); itr.hasNext();) {
			TCsForm forms = (TCsForm) itr.next();
			if (formcode.equals(forms.getFormcode())) {
				listform.remove(forms);
				return;
			}
		}
	}

	public String onCreateope(ActionEvent ae) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");

		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		FormForm form = (FormForm) ae.getForm();

		TCsForm busForm = new TCsForm();
		BeanUtils.copyProperties(busForm, form);

		busForm.setCreated((new Date(System.currentTimeMillis())).toString());
		busForm.setStatusinfo(FormExtendInfo._STATUS_NORMAL);
		Security ser = new Security(ae.getRequest());
		busForm.setParticipant(ser.getUserLoginName());
		busForm.setDesigner(ser.getUserLoginName());

		String pagepath = ae.getRequest().getParameter("pagepath");
		String[] dors = dys.create(busForm, pagepath);

		// 增加扩展属性的展示 主要针对 html 头信息的追加
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		String rsname = pagepath + "." + dors[0];
		UmsProtectedobject upo = rsrmi.loadResourceByNatural(rsname);
		upo.setDescription(busForm.getExtendattribute());
		upo.setReference(busForm.getDescription());
		rsrmi.updateResource(upo);
		// //////////////////////////////////////////

		// ae.setAttribute("tip", WebTip.tip(dors));
		if (dors == null || dors.length != 2) {
			WebTip.htmlInfo("创建失败", false, ae.getResponse());
		} else {
			String columnUrl = ae.getRequest().getContextPath()
					+ "/design/system/column/list.do?formcode=" + dors[1];
			String info = "<script>if(confirm('需要继续创建表单字段')){opener.search();window.open('"
					+ columnUrl
					+ "','_self')}else{window.close();window.opener.location.reload();}</script>";
			WebTip.htmlInfoOri(info, ae.getResponse());
		}
		return null;

	}

	public String onModifyview(ActionEvent ae) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");

		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		FormForm form = (FormForm) ae.getForm();
		// 装载对象
		String formcode = form.getFormcode();
		TCsForm busForm = dysc.loadForm(formcode);

		// 装载子表单
		BeanUtils.copyProperties(form, busForm);
		form.setDimdata(busForm.getBelongx());

		if (busForm.getSubform() != null && busForm.getSubform().length() > 0) {
			form.setCheckFather("1");

			form.setSubform(busForm.getSubform());
		}

		// 增加扩展属性的展示 主要针对 html 头信息的追加
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		List listq = rsrmi.queryObjectProtectedObj(upo, null, 0, 1, "");
		if (listq != null && listq.size() == 1) {
			form.setExtendattribute(((UmsProtectedobject) listq.get(0))
					.getDescription());
			form.setDescription(((UmsProtectedobject) listq.get(0))
					.getReference());
		}
		// ///////////////////////////////

		// TCsForm system = new TCsForm();
		// system.setSystemid(form.getSystemid());
		// List list = formDao.queryObjects(system);
		// // flitFormBySelf(list, formcode);
		// ae.setAttribute("subform", list);
		return "modifyview";
	}

	public String onQueryview(ActionEvent ae) throws Exception {

		FormForm form = (FormForm) ae.getForm();
		TCsForm busForm = new TCsForm();
		busForm.setSystemid(form.getSystemid());
		BeanUtils.copyProperties(form, busForm);
		return "queryview";
	}

	public String onDropope(ActionEvent ae) throws Exception {
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");

		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		FormForm form = (FormForm) ae.getForm();
		String formcode = form.getFormcode();

		TCsForm bufForm = dysc.loadForm(formcode);
		Security ser = new Security(ae.getRequest());
		bufForm.setParticipant(ser.getUserLoginName());

		boolean dors = dys.dropForm(bufForm);
		ae.setAttribute("tip", WebTip.tip(dors));

		if (!dors) {
			WebTip.htmlInfo("删除失败", true, ae.getResponse());
		} else {
			WebTip.htmlInfo("删除成功", true, true, ae.getResponse());
		}
		return null;
	}

	public String onModifyope(ActionEvent ae) throws Exception {

		FormForm form = (FormForm) ae.getForm();
		TCsForm busForm = new TCsForm();
		BeanUtils.copyProperties(busForm, form);
		DyFormDesignService dys = (DyFormDesignService) RmiEntry.iv("dydesign");

		DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
		// 还原SQL表单原先的 SQL信息
		TCsForm busFormOld = dysc.loadForm(form.getFormcode());
		busForm.setSqlinfo(busFormOld.getSqlinfo());
		// ////////////////////
		if ("0".equals(form.getCheckFather())) {
			busForm.setSubform("");
		} else if ("1".equals(form.getCheckFather())
				&& form.getSubform() != null) {
			busForm.setSubform(form.getSubform());
		}
		Security ser = new Security(ae.getRequest());
		busForm.setParticipant(ser.getUserLoginName());
		// 原先设计中用description来存储动态表单的实际库表
		busForm.setDescription(busFormOld.getDescription());
		boolean dors = dys.updateForm(busForm);
		ae.setAttribute("tip", WebTip.tip(dors));

		// 增加扩展属性的展示 主要针对 html 头信息的追加
		ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(busForm.getFormcode());
		List listq = rsrmi.queryObjectProtectedObj(upo, null, 0, 1, "");
		if (listq != null && listq.size() == 1) {
			UmsProtectedobject upox = (UmsProtectedobject) listq.get(0);
			upox.setDescription(busForm.getExtendattribute());
			upox.setReference(busForm.getDescription());
			rsrmi.updateResource(upox);
		}

		if (!dors) {
			WebTip.htmlInfo("修改失败", true, ae.getResponse());
		} else {
			WebTip.htmlInfo("修改成功", true, true, ae.getResponse());
		}
		return null;

	}

}
