package com.jl.common.dyform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import net.sf.json.JSONObject;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.env.client.EnvService;
import oe.frame.web.WebCache;
import oe.midware.dyform.service.DyFormService;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.ScriptTools;
import com.jl.common.app.AppEntry;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfRelevant;
import com.jl.common.workflow.WfEntry;

public final class DyformConsoleImpl implements DyFormConsoleIfc {

	static final String number_c = "/^[-]?([0-9]+)\\.?([0-9]*)$/";
	static final String mail_c = "/^[_a-z0-9]+@([_a-z0-9]+\\.)+[a-z0-9]{2,3}$/";
	static final String ip_c = "/^((\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5]))$/";

	static boolean needscript = false;
	static boolean needCache = false;
	static {
		try {
			ResourceBundle rsx = ResourceBundle.getBundle("config");
			String need = rsx.getString("needCoreScript");
			if ("yes".equals(need)) {
				needscript = true;
			}
			String needCacheTmp = rsx.getString("needCache");
			if ("yes".equals(needCacheTmp)) {
				needCache = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String addData(String formid, DyFormData bus) throws Exception {
		if (bus == null) {
			throw new Exception("空表单");
		}
		if (bus.getParticipant() == null || bus.getParticipant().equals("")) {
			throw new Exception("缺少参与者");
		}
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus busx = new TCsBus();
		BeanUtils.copyProperties(busx, bus);
		busx.setLsh(UUID.randomUUID().toString().replaceAll("-", ""));
		// busx.setBelongx("");
		busx.setCreated((new Timestamp(System.currentTimeMillis()).toString()));
		busx.setStatusinfo("00");
		busx.setTimex((new Timestamp(System.currentTimeMillis()).toString()));
		busx.setFormcode(formid);
		DyAnalysisXml dayx = new DyAnalysisXml();
		if (needscript)
			dayx.scriptPre(formid, busx, "PNewSave");
		String lsh = dy.addData(formid, busx);
		System.out.println("new data coming:" + lsh);
		if (needscript)
			dayx.script(formid, lsh, "NewSave");
		return lsh;
	}

	public boolean deleteData(String formid, String id) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");

		DyAnalysisXml dayx = new DyAnalysisXml();
		if (needscript)
			dayx.script(formid, id, "PDelete");
		boolean rs = dy.deleteData(formid, id);
		if (needscript)
			dayx.script(formid, id, "Delete");
		return rs;

	}

	public List<DyFormColumn> fetchColumnList(String formid) throws Exception {
		if (needCache && WebCache.containCache("fetchColumnList" + formid)) {
			return (List) WebCache.getCache("fetchColumnList" + formid);
		}
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		List listcolumn = dy.fetchColumnList(formid);

		List newColumn = new ArrayList();
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		for (Iterator iterator = listcolumn.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			String typex = object.getViewtype();
			if ("20".equals(typex) || "21".equals(typex) || "29".equals(typex)) {
				// continue;
			}
			if (object.getColumncode().startsWith("num_")) {
				object.setViewtype("01");
			}
			if (object.getColumncode().startsWith("time_")) {
				object.setViewtype("04");
			}
			DyFormColumn columnnew = new DyFormColumn();
			BeanUtils.copyProperties(columnnew, object);
			this.loadColumnx(columnnew);
			this.dealWithDefaultValue(columnnew, rs);
			newColumn.add(columnnew);
		}
		if (needCache)
			WebCache.setCache("fetchColumnList" + formid, newColumn, null);
		return newColumn;
	}

	public List<DyFormColumn> fetchColumnListForDesign(String formid)
			throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		List listcolumn = dy.fetchColumnList(formid);
		List newColumn = new ArrayList();
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		for (Iterator iterator = listcolumn.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			String typex = object.getViewtype();
			if ("20".equals(typex) || "21".equals(typex) || "29".equals(typex)) {
				// continue;
			}
			DyFormColumn columnnew = new DyFormColumn();
			BeanUtils.copyProperties(columnnew, object);
			loadColumnx(columnnew);
			// dealWithKvDict(columnnew, rs);
			newColumn.add(columnnew);
		}

		return newColumn;
	}

	private void dealWithDefaultValue(DyFormColumn columnnew, ResourceRmi rs) {
		// 扩展处理 k-v 列表，支持字典应用
		String htmltype = columnnew.getViewtype();

		// System.out.println(htmltype);
		if ("11".equals(htmltype)) {
			// KV列表有4种模式 1、手工配置的备选值 2、来自资源树某层目录的值 3、来自SOA脚本 4、来自其他动态表单的字段
			StringBuffer but = new StringBuffer();
			String valuelist = columnnew.getValuelist();
			// 来自资源树某层目录的值
			String rsinfo = StringUtils.substringBetween(valuelist, "[TREE:",
					"]");

			if (StringUtils.isNotEmpty(rsinfo)) {
				String valuetmp[] = StringUtils.split(rsinfo, ",");
				String rsNaturaname = valuetmp[0];
				String rsKey = valuetmp.length == 2 ? valuetmp[1] : "name";
				if (!StringUtils.contains(rsNaturaname, ".")) {
					rsNaturaname = rsNaturaname + "." + rsNaturaname;
				}
				try {
					UmsProtectedobject upo = rs
							.loadResourceByNatural(rsNaturaname);
					List sub = rs.subResource(upo.getId());

					for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						String key = object.getName();
						if (rsKey.equals("naturalname")) {
							key = object.getNaturalname();
						} else if (rsKey.equals("id")) {
							key = object.getId();
						} else if (rsKey.equals("nameid")) {
							key = object.getName() + "[" + object.getId() + "]";
						} else if (rsKey.equals("namenatual")) {
							key = object.getName() + "["
									+ object.getNaturalname() + "]";
						} else if (rsKey.equals("actionurl")) {
							key = object.getActionurl();
						}
						but.append(key + "-" + object.getName() + ",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 来自SOA脚本
			but = dealWithSoa(valuelist, rs, but);
			// 来自其他表单字段
			rsinfo = StringUtils.substringBetween(valuelist, "[DYFORM:", "]");

			if (StringUtils.isNotEmpty(rsinfo)) {
				String form[] = StringUtils.split(rsinfo, ",");
				DyFormData dfd = new DyFormData();
				dfd.setFatherlsh("1");
				dfd.setFormcode(form[0]);
				String condition = form.length == 4 ? form[3] : "";
				try {
					List data = DyEntry.iv().queryData(dfd, 0, 1000, condition);
					for (Iterator iterator = data.iterator(); iterator
							.hasNext();) {
						DyFormData object = (DyFormData) iterator.next();
						Object key = BeanUtils.getProperty(object, form[1]);
						String value = "";
						// 字段下拉字段组合
						if (StringUtils.contains(form[2], "@")) {
							String[] valuex = StringUtils.split(form[2], "@");
							for (int i = 0; i < valuex.length; i++) {
								value = value
										+ BeanUtils.getProperty(object,
												valuex[i]);
							}
						} else {
							value = BeanUtils.getProperty(object, form[2]);
						}

						String keyinfo = key == null ? "" : key.toString();
						String valueinfo = value == null ? "" : value
								.toString();
						but.append(keyinfo + "-" + valueinfo + ",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (but.length() > 0) {
				columnnew.setValuelist(but.toString());
			}
		} else if ("17".equals(htmltype) || "18".equals(htmltype)
				|| "22".equals(htmltype) || "23".equals(htmltype)
				|| "27".equals(htmltype) || "28".equals(htmltype)) {
			// 处理树
			String valuelist = columnnew.getValuelist();
			// 来自资源树某层目录的值
			String rsinfo = StringUtils.substringBetween(valuelist, "[TREE:",
					"]");
			if (StringUtils.isNotEmpty(rsinfo)) {
				String name = StringUtils.substringBefore(valuelist, "[TREE:");
				String treex[] = rsinfo.split(",");
				// 在树中只取一个naturalname其他不用
				columnnew.setValuelist(name + "[" + treex[0] + "]");
			}
		} else if ("30".equals(htmltype) || "31".equals(htmltype)
				|| "32".equals(htmltype)) {
			columnnew.setDefaultValue("");
		} else {
			String valuelist = columnnew.getValuelist();
			if (StringUtils.isNotEmpty(valuelist)) {
				valuelist = valuelist.trim();
			}
			StringBuffer but = new StringBuffer();
			but = dealWithSoa(valuelist, rs, but);
			if (but.length() > 0) {
				columnnew.setDefaultValue(but.toString());
			} else {
				columnnew.setDefaultValue(valuelist);
			}
		}

	}

	private StringBuffer dealWithSoa(String valuelist, ResourceRmi rs,
			StringBuffer but) {
		String rsinfo = StringUtils.substringBetween(valuelist, "[SOA:", "]");
		if (StringUtils.isNotEmpty(rsinfo)) {
			String naturalname[] = rsinfo.split(",");
			UmsProtectedobject upo = null;
			try {
				upo = rs.loadResourceByNatural(naturalname[0]);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EnvService env = null;
			try {

				env = (EnvService) RmiEntry.iv("envinfo");
				String value = env.fetchEnvValue("WEBSER_APPFRAME");
				value = value + "Soasvl?naturalname=" + naturalname[0]
						+ (naturalname.length == 2 ? naturalname[1] : "");

				// System.out.println("sync user to php:"+url);
				// 通讯协议
				URL rul = new URL(value);
				// 获得数据流
				URLConnection urlc = rul.openConnection();
				InputStream input = urlc.getInputStream();
				// 进行数据交换

				int read = 0;
				while ((read = input.read()) != -1) {
					but.append((char) read);
				}
				String str = but.toString();
				str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
				but = new StringBuffer();
				but.append(str);
				return but;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return but;

	}

	private void loadColumnx(DyFormColumn columnnew) throws Exception {

		String checktype = columnnew.getChecktype();
		String columnId = columnnew.getColumnid();
		if (columnId.equalsIgnoreCase("belongx")
				|| columnId.equalsIgnoreCase("timex")) {
			// 是否隐蔽
			columnnew.setHidden(true);
			return;
		}

		String ext = columnnew.getExtendattribute();
		if (ext != null && !ext.equals("")) {
			DyAnalysisXml ds = new DyAnalysisXml();
			// 设置获得焦点事件
			String focusScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_FOCUSSCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setFocusScript(ds.dealWithResourceScript(focusScript,
					"WEBSOASCRIPT"));

			// 设置失去焦点事件
			String loseFocusScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_LOSEFOCUSSCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			if (loseFocusScript == null) {
				loseFocusScript = "";
			}

			String check_req = "";
			String reqExpressionular = "";
			if ("mail".equals(checktype)) {
				check_req = mail_c;
				reqExpressionular = mail_c;
			}
			if ("ip".equals(checktype)) {
				check_req = ip_c;
				reqExpressionular = ip_c;
			}
			if ("number".equals(checktype)) {
				check_req = number_c;
				reqExpressionular = number_c;
			}
			if (!check_req.equals("")) {
				loseFocusScript = "if($(this).val()!=''){ if(!$(this).val().match("
						+ check_req + ")){alert('无效格式');$(this).val('');}}" + loseFocusScript;
			}
			columnnew.setLoseFocusScript(ds.dealWithResourceScript(
					loseFocusScript, "WEBSOASCRIPT"));

			// 是否是一组字段
			String groupScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_GROUPSIZE,
					DymaticFormCheck._FINAL_CHECK);
			if (StringUtils.isNotEmpty(groupScript)) {
				columnnew.setGroupsize(Integer.parseInt(groupScript));
			}

			// 设置初始化事件
			String initScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_INITSCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setInitScript(ds.dealWithResourceScript(initScript,
					"WEBSOASCRIPT"));

			// 设置改变事件
			String onchange = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_ONCHANGESCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setOnchangeScript(ds.dealWithResourceScript(onchange,
					"WEBSOASCRIPT"));

			// 设置校验脚本
			String reqExpressionularX = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_CHECKRULE,
					DymaticFormCheck._FINAL_CHECK);
			if (reqExpressionularX != null && !reqExpressionularX.equals("")) {
				String rule[] = StringUtils.split(reqExpressionularX, ";");
				if (rule.length > 1) {
					columnnew.setRegExpression(rule[0]);
				} else {
					columnnew.setRegExpression(reqExpressionular);
				}
			} else {
				columnnew.setRegExpression(reqExpressionular);
			}
			if (columnnew.getColumname().endsWith("$#")) {
				// 是否隐蔽
				columnnew.setHidden(true);
			} else {
				columnnew.setHidden(false);
			}
			// 是否必填
			columnnew.setMusk_("1".equals(columnnew.getMusk()));
			// 是否只读
			columnnew.setReadonly("1".equals(columnnew.getOpemode()));
			// 设置控件宽的
			String width = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_WIDTH,
					DymaticFormCheck._FINAL_CHECK);
			if (width != null && !width.equals("")) {
				columnnew.setWidth(Double.parseDouble(width));
			}
			// 设置汇总类型
			String summarytype = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_SUMMARYTYPE,
					DymaticFormCheck._FINAL_CHECK);
			if (summarytype != null && !summarytype.equals("")) {
				columnnew.setSummarytype(summarytype);
			} else {
				columnnew.setSummarytype("");
			}
			// 设置XY坐标
			String xyoffset = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_OFFSET,
					DymaticFormCheck._FINAL_CHECK);
			if (xyoffset != null && !xyoffset.equals("")) {
				String xy[] = StringUtils.split(xyoffset, "-");
				if (xy.length == 2) {
					columnnew.setYoffset(Double.parseDouble(xy[0]));
					columnnew.setXoffset(Double.parseDouble(xy[1]));
				}
			}
		} else {
			// 是否隐蔽
			columnnew.setHidden(false);
			// 是否必填
			columnnew.setMusk_("1".equals(columnnew.getMusk()));
			// 是否只读
			columnnew.setReadonly("1".equals(columnnew.getOpemode()));
		}
	}

	public DyFormColumn loadColumn(String formid, String columnid)
			throws Exception {

		List list = fetchColumnList(formid);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DyFormColumn object = (DyFormColumn) iterator.next();
			if(object.getColumnid().equals(columnid))
			return object;
		}
		return null;
	}

	public DyFormData loadData(String formcode, String bussid) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bus = new TCsBus();
		if (StringUtils.isNotEmpty(bussid)) {
			bus = dy.loadData(bussid, formcode);
		}
		bus.setFormcode(formcode);
		DyFormData data = new DyFormData();
		BeanUtils.copyProperties(data, bus);
		if (data != null && !"".equals(data.getLsh())) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, bussid, "SelectRead");
		}
		dealWithImgFile(data, formcode);
		// data = dealWithDataTransformInScript(data, formcode);
		return data;
	}

	public DyFormData loadDataS(String formcode, String bussid)
			throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bus = dy.loadData(bussid, formcode);
		DyFormData data = new DyFormData();
		BeanUtils.copyProperties(data, bus);
		if (data != null && !"".equals(data.getLsh())) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, bussid, "SelectUpdate");
		}
		dealWithImgFile(data, formcode);
		return data;
	}

	private void dealWithImgFile(DyFormData data, String formcode)
			throws Exception {
		// 针对附件字段的特别处理，附件字段需要写入表单的lsh，用来展示附件链接使用
		List list = this.fetchColumnListForDesign(formcode);
		String appfileColumnid = "";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			if (object.getViewtype().equals("15")) {
				appfileColumnid = object.getColumnid();
			}
		}
		EnvService env = null;
		String value = null;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			value = env.fetchEnvValue("WEBSER_APPFRAMEX");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (StringUtils.isNotEmpty(data.getLsh())) {
			String urlhead = value + "ListPicSvl?id=" + data.getLsh();
			BeanUtils.setProperty(data, appfileColumnid, urlhead);
		} else {
			BeanUtils.setProperty(data, appfileColumnid, "");
		}

	}

	private void dealWithImgFile(List<DyFormData> data, String formcode)
			throws Exception {
		// 针对附件字段的特别处理，附件字段需要写入表单的lsh，用来展示附件链接使用
		List list = this.fetchColumnListForDesign(formcode);
		String appfileColumnid = "";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			if (object.getViewtype().equals("15")) {
				appfileColumnid = object.getColumnid();
			}
		}
		EnvService env = null;
		String value = null;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			value = env.fetchEnvValue("WEBSER_APPFRAMEX");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			DyFormData object = (DyFormData) iterator.next();
			if (StringUtils.isNotEmpty(object.getLsh())) {
				String urlhead = value + "ListPicSvl?id=" + object.getLsh();
				BeanUtils.setProperty(object, appfileColumnid, urlhead);
			} else {
				BeanUtils.setProperty(object, appfileColumnid, "");
			}
		}

	}

	public DyForm loadForm(String formid) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsForm form = dy.loadForm(formid);
		DyForm dyform = new DyForm();
		BeanUtils.copyProperties(dyform, form);
		// 设置表单字段
		List column = this.fetchColumnList(formid);
		dyform.setAllColumn_((DyFormColumn[]) column
				.toArray(new DyFormColumn[0]));

		// 设置列表展示字段
		String listinfo = dyform.getListinfo();
		String[] infox = StringUtils.split(listinfo, ",");
		for (int i = 0; i < infox.length; i++) {
			if (infox[i].equals("1")) {
				infox[i] = "belongx";
			} else if (infox[i].equals("2")) {
				infox[i] = "timex";
			} else {
				infox[i] = "column" + infox[i];
			}
		}

		List dispColumnAndQueryColumn = new ArrayList();
		for (Iterator iterator = column.iterator(); iterator.hasNext();) {
			DyFormColumn object = (DyFormColumn) iterator.next();
			for (int i = 0; i < infox.length; i++) {
				if (object.getColumnid().equals(infox[i])) {
					dispColumnAndQueryColumn.add(object);
					break;
				}
			}

		}
		// if (dispColumnAndQueryColumn.size() < 2) {
		// dispColumnAndQueryColumn.addAll(column);
		// }
		DyFormColumn[] columnArr = (DyFormColumn[]) dispColumnAndQueryColumn
				.toArray(new DyFormColumn[0]);
		dyform.setQueryColumn_(columnArr);
		// 设置查询展示字段
		dyform.setListColumn_(columnArr);

		String htmlinfo = form.getExtendattribute();
		if (StringUtils.isEmpty(htmlinfo)) {
			htmlinfo = form.getFormname();
		}
		dyform.setHtmltitleinfo_(htmlinfo);

		EnvService env = (EnvService) RmiEntry.iv("envinfo");
		String csshead = env.fetchEnvValue("WEBSER_CMSWEB");
		dyform.setStyleinfo_(dyform.getStyleinfo());
		String stylename = StringUtils.substringBetween(dyform.getStyleinfo(),
				"[", "]");
		dyform.setStyleinfourl_(csshead + "/DownloadSvl2?name=" + stylename);
		String subformlist = dyform.getSubform();
		if (subformlist != null && !subformlist.equals("")) {
			String subform[] = null;
			if (subformlist != null) {
				subform = subformlist.split(",");
			}
			List subformAll = new ArrayList();
			for (int i = 0; i < subform.length; i++) {
				try {
					String subformcode = StringUtils.substringBetween(
							subform[i], "[", "]");
					DyForm formsub = this.loadForm(subformcode);
					formsub.setSub(true);
					String submode = StringUtils
							.substringAfter(subform[i], "=");
					if (StringUtils.isNotEmpty(submode)
							&& submode.length() == 1) {
						formsub.setSubmode(submode);
					}
					subformAll.add(formsub);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dyform.setSubform_((DyForm[]) subformAll.toArray(new DyForm[0]));
		}
		// dyform = dealWithFormTransformInScript(dyform,formid);

		return dyform;
	}

	public boolean modifyData(String formid, DyFormData bus) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		String lsh = bus.getLsh();
		bus.setFormcode(formid);
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		DyAnalysisXml dayx = new DyAnalysisXml();
		if (needscript)
			dayx.script(formid, lsh, "PUpdateSave"); // 正常保存
		boolean rs = dy.modifyData(bux);
		if (rs) {
			if (bux.getStatusinfo() == null) {
				if (needscript)
					dayx.script(formid, lsh, "UpdateSave"); // 正常保存
			}
			updateRevx(lsh);
			// if(bux.getStatusinfo()!=null&&bux.getStatusinfo().equals("02")){
			// dayx.script(formid, lsh, "Onaffirm");//反确认
			// }
			// if(bux.getStatusinfo()!=null&&bux.getStatusinfo().equals("01")){
			// dayx.script(formid, lsh, "Yesaffirm");//确认
			// }
			
			
		}
		return rs;
	}
	
	private void updateRevx(String bussid){
		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			WorkflowView wfview = (WorkflowView) RmiEntry.iv("wfview");
			List list=wfview.coreSqlview("select appname,runtimeid from t_wf_relevantvar_tmp where lsh='"+bussid+"'");

			if(list.size()>0){
				String runtimeid=(String)((Map)list.get(0)).get("runtimeid");
				String appname=(String)((Map)list.get(0)).get("appname");
			
				List appdy = AppEntry.iv().wf2dyformBindCfg(appname);
				// 动态复制动态表单中的业务数据到工作流中
				StringBuffer rev_view_sql_column = new StringBuffer();
				StringBuffer rev_view_sql_value = new StringBuffer();
				int count = 0;
				for (Iterator iterator = appdy.iterator(); iterator.hasNext();) {
					TWfRelevant object = (TWfRelevant) iterator.next();
					String revid = object.getRevid();
					String columnid = object.getRev2column();
					if (columnid == null) {
						continue;
					}
					if (revid.startsWith("r_")) {
						continue;
					}
					String formcode = object.getRev2formcode();
					DyFormData data = DyEntry.iv().loadData(formcode, bussid);
					columnid=columnid.toLowerCase();
					String value = BeanUtils.getProperty(data, columnid);
					
					TWfRelevantvar rev = wfview.fetchRelevantVar(runtimeid, revid);// 获得表单变量
					rev.setValuenow(value);
					console.updateRelevantvar(rev);
					
					value = StringUtils.replace(value, "'", "’");
					rev_view_sql_value.append(",'" + value + "'");
					rev_view_sql_column.append(",d" + count++);
				}
				String rev_view_sql_column_s = "runtimeid,appname,lsh"
						+ rev_view_sql_column.toString();
				String rev_view_sql_value_s = "'" + runtimeid + "','" + appname
						+ "','" + bussid + "'" + rev_view_sql_value.toString();
				String rev_view_sql = "insert into t_wf_relevantvar_tmp("
						+ rev_view_sql_column_s + ")values(" + rev_view_sql_value_s
						+ ")";
				
					String sql="delete from t_wf_relevantvar_tmp where runtimeid='"+runtimeid+"'";
					console.coreSqlhandle(sql);
					console.coreSqlhandle(rev_view_sql);
					}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	


	public List queryData(DyFormData bus, int form, int to, String condition)
			throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		if (StringUtils.isEmpty(condition)) {
			condition = " order by timex";
		}
		if (StringUtils.isEmpty(bus.getFatherlsh())) {
			return new ArrayList();
		}

		String formcode = bus.getFormcode();
		String subcondtion = subCondition(formcode, bux);
		List list = dy.queryData(bux, form, to, subcondtion + condition);

		DyAnalysisXml dayx = new DyAnalysisXml();

		if (list == null || list.size() == 0) {
			// 创建初始化处理，有些时候主表单需要自动带出几条子表单信息
			// 信息格式为 List(TCsBus)
			// 根据外部脚本自动构造子表单初始数据信息
			Object rsinfo = dayx.script(formcode, null, "InitCreate");
			if (rsinfo != null
					&& rsinfo.getClass().getName()
							.equals("java.util.ArrayList")) {
				list = (ArrayList) rsinfo;
			}
		}
		// 处理列表预处理事件
		List listx = dayx.script2(formcode, list);
		if (listx != null && listx.size() > 0) {
			list = listx;
		}
		if (list == null) {
			list = new ArrayList();
		}
		List listnew = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			DyFormData data = new DyFormData();
			BeanUtils.copyProperties(data, object);
			listnew.add(data);
		}
		this.dealWithImgFile(listnew, formcode);
		// listnew = this.dealWithDataTransformInScript(listnew, formcode);
		return listnew;
	}

	public int queryDataNum(DyFormData bus, String condition) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		String subcondtion = subCondition(bux.getFormcode(), bux);
		int number = dy.queryDataNum(bux, condition);
		return number;

	}

	private String subCondition(String formcode, TCsBus bus) throws Exception {
		// 处理扩展条件
		List listx = this.queryColumnQ(formcode);
		StringBuffer butCon = new StringBuffer();
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			DyFormColumn objx = (DyFormColumn) iterator.next();
			String columnid = objx.getColumnid();
			String value = BeanUtils.getProperty(bus, columnid);
			String addcondition = objx.getExtendattribute();
			// 自定义的查询字段如果有特殊条件的情况下
			if (StringUtils.isNotEmpty(value)
					&& StringUtils.isNotEmpty(addcondition)) {
				// 特殊条件中可能且仅有可能存在该字段的变量值格式为 $()
				String key = "$(value)";
				// 将条件中的变量替换为真实值
				butCon.append(" "
						+ StringUtils.replace(addcondition, key, value));
				// 将表单中该字段设置为空，避免被拿去普通查询
				BeanUtils.setProperty(bus, columnid, "");
			}
		}
		return butCon.toString();
	}

	public int deleteAll(String formcode, String fatherlsh) throws Exception {
		DyFormData dydataSub = new DyFormData();
		dydataSub.setFormcode(formcode);// 子表单的formcode
		dydataSub.setFatherlsh(fatherlsh);
		List listSub = DyEntry.iv().queryData(dydataSub, 0, 100, "");
		for (Iterator iterator = listSub.iterator(); iterator.hasNext();) {
			DyFormData object = (DyFormData) iterator.next();
			this.deleteData(formcode, object.getLsh());
		}
		return listSub.size();
	}

	public String[] addAll(List<DyFormData> data) throws Exception {
		List lsh = new ArrayList();
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			DyFormData dyFormData = (DyFormData) iterator.next();
			String formcode = dyFormData.getFormcode();
			if (formcode == null) {
				throw new Exception("缺少表单标识码formcode");
			}
			String lshpre = this.addData(formcode, dyFormData);
			lsh.add(lshpre);
		}
		return (String[]) lsh.toArray(new String[0]);
	}

	public boolean deleteDataByLogic(String formid, String id) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	public void permission(DyForm form, String usercode) throws Exception {
		// TODO Auto-generated method stub
		columnPermission(form, usercode);
	}

	private void columnPermission(DyForm form, String usercode)
			throws Exception {
		CupmRmi cupm = null;
		ResourceRmi rmi = null;
		List formlist = null;
		try {
			cupm = (CupmRmi) RmiEntry.iv("cupm");
			rmi = (ResourceRmi) RmiEntry.iv("resource");

			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setExtendattribute(form.getFormcode());
			upo.setNaturalname("BUSSFORM.BUSSFORM.%");
			Map map = new HashMap();
			map.put("naturalname", "like");
			formlist = rmi.fetchResource(upo, map);

			if (formlist.size() != 1) {
				StringBuffer but = new StringBuffer("Error Resource:");
				for (Iterator iterator = formlist.iterator(); iterator
						.hasNext();) {
					UmsProtectedobject object = (UmsProtectedobject) iterator
							.next();
					but.append(object.getName() + "[" + object.getNaturalname()
							+ "]");
				}
				throw new RuntimeException("表单定义异常存在1个以上的表单资源:" + but);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DyFormColumn[] columnList = form.getAllColumn_();
		String preNaturalname = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		String preId = ((UmsProtectedobject) formlist.get(0)).getId();
		Map mapPermissionkey = new HashMap();
		try {
			List subResource = rmi.subResource(preId);
			if (subResource == null || subResource.size() == 0) {
				// 如果没有注册表单字段的保护资源，那么认为该表单不需要安全保护，直接返回所有的字段许可
				return;
			}
			for (Iterator iterator = subResource.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				// System.out.println("-------------:"+object.getNaturalname());
				mapPermissionkey.put(object.getNaturalname(), "");
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < columnList.length; i++) {
			DyFormColumn object = columnList[i];
			String naturalname = preNaturalname + "."
					+ object.getColumncode().toUpperCase();
			// System.out.println("-------------x:"+naturalname);
			if (!mapPermissionkey.containsKey(naturalname)) {
				continue;
			}
			boolean man = cupm.checkUserPermission("0000", usercode,
					naturalname, "7");
			if (!man) {
				object.setReadonly(true);
				boolean read = cupm.checkUserPermission("0000", usercode,
						naturalname, "3");
				if (!read) {
					object.setHidden(true);
				}
			}
		}
	}

	public void permission(DyForm form, String usercode, TWfActive act)
			throws Exception {
		String formcolumn = act.getEditcolumn();
		if (formcolumn == null || formcolumn.equals("")) {// 能进入
			// permission方法意味着需要编辑表单，如果用户没有配置需要编辑那些表单，那么默认全部都能编辑
		} else {
			String[] columnxtmp = StringUtils.split(formcolumn, ",");
			Map permssionColumn = new HashMap();

			for (int i = 0; i < columnxtmp.length; i++) {
				String key = StringUtils.substringBetween(columnxtmp[i], "[",
						"]");
				permssionColumn.put(key, "");
			}
			if (permssionColumn.size() == 0) {
				return;
			}
			DyFormColumn[] columnx = form.getAllColumn_();
			for (int i = 0; i < columnx.length; i++) {
				if (!permssionColumn.containsKey(columnx[i].getColumnid())) {
					columnx[i].setReadonly(true);
					columnx[i].setMusk_(false);// 如果是只读那么需要忽略其必须控制
				}
			}
		}

		// 表单字段授权控制
		columnPermission(form, usercode);

		// 流程表单控制
		try {

			// 字表单字段控制
			String subcolumn = act.getSubcolumn();
			if (subcolumn == null || subcolumn.equals("")) {// 能进入
				// permission方法意味着需要编辑表单，如果用户没有配置需要编辑那些表单，那么默认全部都能编辑
				return;
			}
			subcolumn = subcolumn.replace("&quot;", "\"");
			subcolumn = subcolumn.replace("&amp;", "&");
			String[] subarr = subcolumn.split("&");
			DyForm[] subDyForms = form.getSubform_();
			for (int j = 0; j < subDyForms.length; j++) {
				for (int sub = 0; sub < subarr.length; sub++) {
					String substr = subarr[sub];
					JSONObject jsonobj = JSONObject.fromObject(substr);
					if (subDyForms[j].getFormcode().equals(
							jsonobj.get("subformcode").toString())) {

						String[] read = StringUtils.split((jsonobj.get("read"))
								.toString(), ",");
						Map readmap = new HashMap();
						// 只读的字段
						for (int i = 0; i < read.length; i++) {
							String key = StringUtils.substringBetween(read[i],
									"[", "]");
							readmap.put(key, "");
						}
						String[] hide = StringUtils.split((jsonobj.get("hide"))
								.toString(), ",");
						Map hidemap = new HashMap();
						// 隐藏的字段
						for (int i = 0; i < hide.length; i++) {
							String key = StringUtils.substringBetween(hide[i],
									"[", "]");
							hidemap.put(key, "");
						}

						if (hidemap.size() == 0 && readmap.size() == 0) {
							return;
						}
						DyFormColumn[] subcolumnx = subDyForms[j]
								.getAllColumn_();

						for (int i = 0; i < subcolumnx.length; i++) {
							if (readmap.size() != 0) {
								if (readmap.containsKey(subcolumnx[i]
										.getColumnid())) {
									subcolumnx[i].setReadonly(true);
									subcolumnx[i].setMusk_(false);// 如果是只读那么需要忽略其必须控制
								}
							}
							if (hidemap.size() != 0) {
								if (hidemap.containsKey(subcolumnx[i]
										.getColumnid())) {
									subcolumnx[i].setHidden(true);// 如果是隐藏
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public List<DyFormColumn> queryColumnX(String formcode, String model)
			throws Exception {
		if (needCache
				&& WebCache.containCache("queryColumnX" + model + formcode)) {
			return (List) WebCache.getCache("queryColumnX" + model + formcode);
		}
		// TODO Auto-generated method stub
		DyColumnQuery dyfcQuery = new DyColumnQuery();
		List list = dyfcQuery.QueryColumn(formcode, model);
		List newColumn = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			DyFormColumn columnnew = new DyFormColumn();
			BeanUtils.copyProperties(columnnew, object);
			loadColumnx(columnnew);
			dealWithDefaultValue(columnnew, rs);

			newColumn.add(columnnew);
		}
		if (needCache)
			WebCache.setCache("queryColumnX" + model + formcode, newColumn,
					null);
		return newColumn;
	}

	public List<DyFormColumn> queryColumnQ(String formcode) throws Exception {

		if (needCache && WebCache.containCache("queryColumnQ" + formcode)) {
			return (List) WebCache.getCache("queryColumnQ" + formcode);
		}
		// TODO Auto-generated method stub
		DyColumnQuery dyfcQuery = new DyColumnQuery();
		List list = dyfcQuery.QueryColumnQ(formcode);
		List newColumn = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DyFormColumn object = (DyFormColumn) iterator.next();
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			object.setReadonly(false);
			dealWithDefaultValue(object, rs);
			newColumn.add(object);
		}
		if (needCache)
			WebCache.setCache("queryColumnQ" + formcode, newColumn, null);
		return newColumn;
	}

	public boolean whenFlowPageEdit(String bussid, String participant)
			throws Exception {
		String runtimeid = WfEntry.iv().getSession("bussid");
		if (StringUtils.isEmpty(runtimeid)) {
			return true;// 说明无流程
		}
		if (StringUtils.contains(participant, "[")) {
			participant = StringUtils.substringBetween(participant, "[", "]");
		}
		List list = WfEntry
				.iv()
				.useCoreView()
				.coreSqlview(
						"select t1.workcode workcode from t_wf_participant t1,t_wf_worklist t2 where t1.workcode=t2.workcode and t1.usercode='"
								+ participant
								+ "' and t1.STATUSNOW='01' and  t2.RUNTIMEID='"
								+ runtimeid + "'");

		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String[] manageColumn(String formcode, String participant)
			throws Exception {
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");

		if ("adminx".equals(participant)) {
			List columnx = this.fetchColumnList(formcode);
			List columnxx = new ArrayList();
			for (Iterator iterator = columnx.iterator(); iterator.hasNext();) {
				DyFormColumn object = (DyFormColumn) iterator.next();
				columnxx.add(object.getColumnid());
			}
			return (String[]) columnxx.toArray(new String[0]);
		}

		UmsProtectedobject upo2 = new UmsProtectedobject();
		upo2.setNaturalname("BUSSFORM.BUSSFORM.%");
		upo2.setExtendattribute(formcode);
		Map map = new HashMap();
		map.put("naturalname", "like");

		List formlist = rs.fetchResource(upo2, map);

		if (formlist.size() == 1) {
			upo2 = ((UmsProtectedobject) formlist.get(0));
		}
		List listx = rs.subResource(upo2.getId());
		List columnAvail = new ArrayList();
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator.next();
			String objname = object.getNaturalname();
			String columnid = StringUtils.substringAfterLast(objname, ".");
			if (columnid.toLowerCase().startsWith("column")) {
				boolean avail = SecurityEntry.iv().permission(participant,
						objname);
				if (avail)
					columnAvail.add(columnid);
			}
		}
		return (String[]) columnAvail.toArray(new String[0]);
	}

	/**
	 * 嵌入脚本处理数据转化脚本<BR>
	 * 
	 * @author don add
	 * @param data
	 * @param formcode
	 * @throws Exception
	 */
	private List dealWithDataTransformInScript(List<DyFormData> data,
			String formcode) throws Exception {
		// 当前目录的URI
		URL paths = Thread.currentThread().getContextClassLoader().getResource(
				"");
		String path = paths.getPath();

		Object[] obj = { data };
		StringBuffer scripts = new StringBuffer();

		File file = new File(path + "/script/formdatalist-" + formcode
				+ ".jcode");
		// 存在脚本，读取脚本内容并动态执行
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				int line = 1;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					scripts.append(tempString);
					line++;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			// 执行脚本
			return (List) ScriptTools.todo(scripts.toString(), obj);
		}
		return data;
	}

	/**
	 * 嵌入脚本处理数据转化脚本<BR>
	 * 
	 * @author don add
	 * @param data
	 * @param formcode
	 * @throws Exception
	 */
	private DyFormData dealWithDataTransformInScript(DyFormData data,
			String formcode) throws Exception {
		// 当前目录的URI
		URL paths = Thread.currentThread().getContextClassLoader().getResource(
				"");
		String path = paths.getPath();

		Object[] obj = { data };
		StringBuffer scripts = new StringBuffer();

		File file = new File(path + "/script/formdata-" + formcode + ".jcode");
		// 存在脚本，读取脚本内容并动态执行
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				int line = 1;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					scripts.append(tempString);
					line++;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			// 执行脚本
			return (DyFormData) ScriptTools.todo(scripts.toString(), obj);
		}
		return data;
	}

	/**
	 * 嵌入脚本处理表单转化脚本<BR>
	 * 
	 * @author don add
	 * @param data
	 * @param formcode
	 * @throws Exception
	 */
	private DyForm dealWithFormTransformInScript(DyForm dyform, String formcode)
			throws Exception {
		// 当前目录的URI
		URL paths = Thread.currentThread().getContextClassLoader().getResource(
				"");
		String path = paths.getPath();

		Object[] obj = { dyform };
		StringBuffer scripts = new StringBuffer();

		File file = new File(path + "/script/form-" + formcode + ".jcode");
		// 存在脚本，读取脚本内容并动态执行
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				int line = 1;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					scripts.append(tempString);
					line++;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			// 执行脚本
			return (DyForm) ScriptTools.todo(scripts.toString(), obj);
		}
		return dyform;
	}

}
