package com.jl.common.dyform;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.env.client.EnvService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jl.common.workflow.TWfActive;


public final class DyformConsoleImpl implements DyFormConsoleIfc {

	static final String number_c = "/^[-]?([0-9]+)\\.?([0-9]*)$/";
	static final String mail_c = "/^[_a-z0-9]+@([_a-z0-9]+\\.)+[a-z0-9]{2,3}$/";
	static final String ip_c = "/^((\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|\\d\\d|[0-1]\\d\\d|2[0-4]\\d|25[0-5]))$/";

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
		String lsh = dy.addData(formid, busx);
		System.out.println("lsh:"+lsh);
		if (StringUtils.isNotEmpty(lsh)) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formid, lsh, "NewSave");
		}
		return lsh;
	}

	public boolean deleteData(String formid, String id) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		boolean rs = dy.deleteData(formid, id);
		if (rs) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formid, id, "Delete");
		}
		return rs;
	}

	public List<DyFormColumn> fetchColumnList(String formid) throws Exception {
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
			dealWithKvDict(columnnew, rs);
			newColumn.add(columnnew);
		}

		// DyFormColumn columnnew = new DyFormColumn();
		// columnnew.setColumnid("extendattribute");
		// columnnew.setColumname("扩展信息");
		// columnnew.setViewtype("13");
		// columnnew.setHidden(true);
		// columnnew.setMusk_(true);
		// newColumn.add(columnnew);

		return newColumn;
	}

	private void dealWithKvDict(DyFormColumn columnnew, ResourceRmi rs) {
		// 扩展处理 k-v 列表，支持字典应用
		String htmltype = columnnew.getViewtype();
		if ("11".equals(htmltype)) {
			String valuelist = columnnew.getValuelist();
			String rsinfo = StringUtils.substringBetween(valuelist, "[", "]");
			if (rsinfo != null && !rsinfo.equals("")) {
				if (!StringUtils.contains(rsinfo, ".")) {
					rsinfo = rsinfo + "." + rsinfo;
				}
				StringBuffer but = new StringBuffer();
				try {
					UmsProtectedobject upo = rs.loadResourceByNatural(rsinfo);
					List sub = rs.subResource(upo.getId());

					for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						but.append(object.getNaturalname() + "-"
								+ object.getName() + ",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (but.length() > 0) {
					columnnew.setValuelist(but.toString());
				}
			}
		}
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

			// 设置获得焦点事件
			String focusScript = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_FOCUSSCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setFocusScript(focusScript);
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
						+ check_req + ")){alert('无效格式');}}" + loseFocusScript;
			}
			columnnew.setLoseFocusScript(loseFocusScript);

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
			columnnew.setInitScript(initScript);

			// 设置改变事件
			String onchange = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_ONCHANGESCRIPT,
					DymaticFormCheck._FINAL_CHECK);
			columnnew.setOnchangeScript(onchange);

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

			// 是否隐蔽
			columnnew.setHidden(false);
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
			} else {
				columnnew.setWidth(70);
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
			object.getColumnid().equals(columnid);
			return object;
		}
		return null;
	}

	public DyFormData loadData(String formcode, String bussid) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bus = dy.loadData(bussid, formcode);
		DyFormData data = new DyFormData();
		BeanUtils.copyProperties(data, bus);
		if (data != null && !data.getLsh().equals("")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, bussid, "SelectRead");
		}
		return data;
	}

	public DyFormData loadDataS(String formcode, String bussid)
			throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bus = dy.loadData(bussid, formcode);
		DyFormData data = new DyFormData();
		BeanUtils.copyProperties(data, bus);
		if (data != null && !data.getLsh().equals("")) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formcode, bussid, "SelectUpdate");
		}
		return data;
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

		return dyform;
	}

	public boolean modifyData(String formid, DyFormData bus) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		String lsh = bus.getLsh();
		bus.setFormcode(formid);
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		boolean rs = dy.modifyData(bux);
		if (rs) {
			DyAnalysisXml dayx = new DyAnalysisXml();
			dayx.script(formid, lsh, "UpdateSave");
		}
		return rs;
	}

	public List queryData(DyFormData bus, int form, int to, String condition)
			throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		if (StringUtils.isEmpty(condition)) {
			condition = " order by timex";
		}
		List list = dy.queryData(bux, form, to, condition);

		if (list == null || list.size() == 0) {
			String formcode = bus.getFormcode();

			DyAnalysisXml dayx = new DyAnalysisXml();
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
		List listnew = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsBus object = (TCsBus) iterator.next();
			DyFormData data = new DyFormData();
			BeanUtils.copyProperties(data, object);
			listnew.add(data);
		}
		return listnew;
	}

	public int queryDataNum(DyFormData bus, String condition) throws Exception {
		DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
		TCsBus bux = new TCsBus();
		BeanUtils.copyProperties(bux, bus);
		int number = dy.queryDataNum(bux, condition);
		return number;

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
		try {
			List subResource = rmi.subResource(preId);
			if (subResource == null || subResource.size() == 0) {
				// 如果没有注册表单字段的保护资源，那么认为该表单不需要安全保护，直接返回所有的字段许可
				return;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < columnList.length; i++) {
			DyFormColumn object = columnList[i];
			String naturalname = preNaturalname + "."
					+ object.getColumncode().toUpperCase();
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
			return;
		}
		String[] columnxtmp = StringUtils.split(formcolumn, ",");
		Map permssionColumn = new HashMap();

		for (int i = 0; i < columnxtmp.length; i++) {
			String key = StringUtils.substringBetween(columnxtmp[i], "[", "]");
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

	public List<TCsColumn> QueryColumn(String formcode, String model)
			throws Exception {
		// TODO Auto-generated method stub
		DyColumnQuery dyfcQuery = new DyColumnQuery();
		dyfcQuery.QueryColumn(formcode, model);
		return dyfcQuery.QueryColumn(formcode, model);
	}

}
