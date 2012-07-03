package com.jl.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.JSONUtil2;
import com.jl.common.ScriptTools;
import com.jl.common.app.AppEntry;
import com.jl.common.app.AppHandleIfc;
import com.jl.common.app.AppObj;
import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyForm;
import com.jl.common.dyform.DyFormBuildHtmlExt;
import com.jl.common.dyform.DyFormColumn;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.dyform.DyFormData;
import com.jl.common.resource.Resource;
import com.jl.common.resource.ResourceNode;
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.DbTools;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfActivePass;
import com.jl.common.workflow.TWfParticipant;
import com.jl.common.workflow.WfEntry;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.FrameService;
import com.lucaslee.report.ReportManager;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.ExcelPrinter;

public class FrameActionExt extends AbstractAction {

	public ActionForward portalView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		loadAccordtree(mapping, form, request, response);
		return mapping.findForward("portalView");
	}

	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		User user = getOnlineUser(request);// 获取当前用户
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);
		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);

		// 样式
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// 按钮控制
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		upo.setNaturalname("BUSSFORM.BUSSFORM.%");
		Map map = new HashMap();
		map.put("naturalname", "like");
		List formlist = rs.fetchResource(upo, map);
		if (formlist.size() != 1) {
			throw new RuntimeException("存在表单异常定义");
		}
		String naturalname_dyform = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		request.setAttribute("naturalname_dyform", naturalname_dyform);

		// 普通查询
		DyFormColumn[] simpleQuerydyform = null;
		List<DyFormColumn> listx = DyEntry.iv().queryColumnQ(formcode);
		simpleQuerydyform = (DyFormColumn[]) listx
				.toArray(new DyFormColumn[listx.size()]);
		String lsh = "";
		boolean issub = false;// 是否子表单
		String forms = DyFormBuildHtmlExt.buildQueryForm0(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(simpleQuerydyform);
		request.setAttribute("queryConditionHtml", queryConditionHtml);

		// 高级查询
		setExtQueryColumnVar(request, formcode);

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frameSCMExt/frameMain-" + naturalname
				+ ".jsp");
		String forward = "/frameSCMExt/frameMain.jsp";
		if (file.exists()) {
			forward = "/frameSCMExt/frameMain-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
		// return mapping.findForward("onMainView");
	}

	public ActionForward onMainView2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		User user = getOnlineUser(request);// 获取当前用户
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);

		// 样式
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// 按钮控制
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		upo.setNaturalname("BUSSFORM.BUSSFORM.%");
		Map map = new HashMap();
		map.put("naturalname", "like");
		List formlist = rs.fetchResource(upo, map);
		if (formlist.size() != 1) {
			throw new RuntimeException("存在表单异常定义");
		}
		String naturalname_dyform = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		request.setAttribute("naturalname_dyform", naturalname_dyform);

		// 子表单0
		DyForm subdyform = dyform.getSubform_()[0];
		String subcolumns = DyFormBuildHtmlExt.buildExtColumns(subdyform, null,
				true, user.getUserCode());
		String subfields = DyFormBuildHtmlExt.buildExtFields(subdyform);
		request.setAttribute("subcolumns", subcolumns);
		request.setAttribute("subfields", subfields);

		// 普通查询
		DyFormColumn[] simpleQuerydyform = null;
		List<DyFormColumn> listx = DyEntry.iv().queryColumnQ(formcode);
		simpleQuerydyform = (DyFormColumn[]) listx
				.toArray(new DyFormColumn[listx.size()]);
		String lsh = "";
		boolean issub = false;// 是否子表单
		String forms = DyFormBuildHtmlExt.buildQueryForm0(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(simpleQuerydyform);
		request.setAttribute("queryConditionHtml", queryConditionHtml);

		// 高级查询
		setExtQueryColumnVar(request, formcode);

		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frameSCMExt/frameMain2-" + naturalname
				+ ".jsp");
		String forward = "/frameSCMExt/frameMain2.jsp";
		if (file.exists()) {
			forward = "/frameSCMExt/frameMain2-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
		// return mapping.findForward("onMainView2");
	}

	public ActionForward onMainView3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		String naturalname = request.getParameter("naturalname");
		User user = getOnlineUser(request);// 获取当前用户
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);

		// 样式
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// 按钮控制
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		upo.setNaturalname("BUSSFORM.BUSSFORM.%");
		Map map = new HashMap();
		map.put("naturalname", "like");
		List formlist = rs.fetchResource(upo, map);
		if (formlist.size() != 1) {
			throw new RuntimeException("存在表单异常定义");
		}
		String naturalname_dyform = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		request.setAttribute("naturalname_dyform", naturalname_dyform);

		// 汇总或明细
		String extmode = request.getParameter("extmode");
		DyFormColumn[] extdyform = null;

		if ("1".equals(extmode)) {
			List<DyFormColumn> list = DyEntry.iv().queryColumnX(formcode, "3");
			extdyform = (DyFormColumn[]) list.toArray(new DyFormColumn[list
					.size()]);
		} else if ("2".equals(extmode)) {
			List<DyFormColumn> list = DyEntry.iv().queryColumnX(formcode, "3");
			extdyform = (DyFormColumn[]) list.toArray(new DyFormColumn[list
					.size()]);
		}
		String extcolumns = DyFormBuildHtmlExt.buildExtColumnsX(extdyform,
				null, true);
		String extfields = DyFormBuildHtmlExt.buildExtFieldsX(extdyform);
		request.setAttribute("extcolumns", extcolumns);
		request.setAttribute("extfields", extfields);

		// 普通查询
		DyFormColumn[] simpleQuerydyform = null;
		List<DyFormColumn> listx = DyEntry.iv().queryColumnQ(formcode);
		simpleQuerydyform = (DyFormColumn[]) listx
				.toArray(new DyFormColumn[listx.size()]);
		String lsh = "";
		boolean issub = false;// 是否子表单
		String forms = DyFormBuildHtmlExt.buildQueryForm0(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(simpleQuerydyform);
		request.setAttribute("queryConditionHtml", queryConditionHtml);

		// 高级查询
		setExtQueryColumnVar(request, formcode);

		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frameSCMExt/frameMain3-" + naturalname
				+ ".jsp");
		String forward = "/frameSCMExt/frameMain3.jsp";
		if (file.exists()) {
			forward = "/frameSCMExt/frameMain3-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
		// return mapping.findForward("onMainView3");
	}

	public ActionForward onMainView4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		String naturalname = request.getParameter("naturalname");
		User user = getOnlineUser(request);// 获取当前用户
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtmlExt.buildExtColumns(dyform, "0", true,
				user.getUserCode());
		String fields = DyFormBuildHtmlExt.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);

		// 样式
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());

		// 按钮控制
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setExtendattribute(formcode);
		upo.setNaturalname("BUSSFORM.BUSSFORM.%");
		Map map = new HashMap();
		map.put("naturalname", "like");
		List formlist = rs.fetchResource(upo, map);
		if (formlist.size() != 1) {
			throw new RuntimeException("存在表单异常定义");
		}
		String naturalname_dyform = ((UmsProtectedobject) formlist.get(0))
				.getNaturalname();
		request.setAttribute("naturalname_dyform", naturalname_dyform);

		// 汇总或明细
		String extmode = request.getParameter("extmode");
		DyFormColumn[] extdyform = null;

		if ("1".equals(extmode)) {
			List<DyFormColumn> list = DyEntry.iv().queryColumnX(formcode, "3");
			extdyform = (DyFormColumn[]) list.toArray(new DyFormColumn[list
					.size()]);
		} else if ("2".equals(extmode)) {
			List<DyFormColumn> list = DyEntry.iv().queryColumnX(formcode, "3");
			extdyform = (DyFormColumn[]) list.toArray(new DyFormColumn[list
					.size()]);
		}
		String extcolumns = DyFormBuildHtmlExt.buildExtColumnsX(extdyform,
				null, true);
		String extfields = DyFormBuildHtmlExt.buildExtFieldsX(extdyform);
		request.setAttribute("extcolumns", extcolumns);
		request.setAttribute("extfields", extfields);

		// 子表单0
		DyForm subdyform = dyform.getSubform_()[0];
		String subcolumns = DyFormBuildHtmlExt.buildExtColumns(subdyform, null,
				true, user.getUserCode());
		String subfields = DyFormBuildHtmlExt.buildExtFields(subdyform);
		request.setAttribute("subcolumns", subcolumns);
		request.setAttribute("subfields", subfields);

		// 普通查询
		DyFormColumn[] simpleQuerydyform = null;
		List<DyFormColumn> listx = DyEntry.iv().queryColumnQ(formcode);
		simpleQuerydyform = (DyFormColumn[]) listx
				.toArray(new DyFormColumn[listx.size()]);
		String lsh = "";
		boolean issub = false;// 是否子表单
		String forms = DyFormBuildHtmlExt.buildQueryForm0(dyform, user
				.getNLevelName()
				+ "/" + user.getUserName() + "," + user.getNLevelName(),
				naturalname, lsh, issub, request.getParameter("url"));
		request.setAttribute("queryform1", forms);
		String queryConditionHtml = DyFormBuildHtmlExt
				.buildQueryCondition(simpleQuerydyform);
		request.setAttribute("queryConditionHtml", queryConditionHtml);

		// 高级查询
		setExtQueryColumnVar(request, formcode);

		String queryColumnHtml = DyFormBuildHtmlExt.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frameSCMExt/frameMain4-" + naturalname
				+ ".jsp");
		String forward = "/frameSCMExt/frameMain4.jsp";
		if (file.exists()) {
			forward = "/frameSCMExt/frameMain4-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
		// return mapping.findForward("onMainView4");
	}

	// 列表页面action
	public ActionForward listmain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frameSCMExt/listmain-" + naturalname
				+ ".jsp");
		String forward = "/frameSCMExt/listmain.jsp";
		if (file.exists()) {
			forward = "/frameSCMExt/listmain-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 列表数据action
	public void list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		StringBuffer script = new StringBuffer();

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frameSCMExt/list-" + naturalname
				+ ".jcode");
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				// System.out.println("以行为单位读取文件内容，一次读一整行：");
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				int line = 1;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					// 显示行号
					// System.out.println("line " + line + ": " + tempString);
					script.append(tempString);
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
			Object obj = ScriptTools.todo(script.toString());
			if (obj == null) {
				response.getWriter().write("0");
			}
			String datatype = request.getParameter("datatype");
			if ("json".equals(datatype)) {
				response.setContentType("text/json;charset=UTF-8");
			}
			response.getWriter().write(obj.toString());
		} else {
			String datatype = request.getParameter("datatype");
			if ("json".equals(datatype)) {
				response.setContentType("text/json;charset=UTF-8");
				response.getWriter().write("{\"error\":\"yes\"}");
			} else {
				response.getWriter().write("error");
			}
		}
	}

	public void onList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String conditions = request.getParameter("conditions");
		String extconditions = request.getParameter("extconditions");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(conditions)) {
			paramJson = JSONObject.fromObject(conditions);
		}
		if (StringUtils.isNotEmpty(extconditions)) {
			// extconditions =
			// DyFormBuildHtmlExt.getQueryCondition(extconditions);
		}
		User user = getOnlineUser(request);
		String naturalname = request.getParameter("naturalname");
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 页码
		PageInfo obj = new PageInfo();
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();

			Integer from_ = Integer.parseInt(start);
			Integer limit_ = Integer.parseInt(limit);
			Integer to_ = from_ + limit_ - 1;

			DyFormData dydata = new DyFormData();
			BeanUtils.copyProperties(dydata, paramJson);
			dydata.setFatherlsh("1");
			dydata.setFormcode(formcode);
			if (!"adminx".equals(user.getUserCode())) {
				dydata.setParticipant(user.getUserCode());
			}

			obj = (PageInfo) ins.queryForPage(dydata, from_, limit_,
					extconditions);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				DyFormData group = (DyFormData) iterator.next();
				String lsh = group.getLsh();

				// String clientId = "";
				// if (group.getParticipant() != null
				// && StringUtils.isNotEmpty(group.getParticipant())) {
				// clientId = group.getParticipant();
				// }
				//
				// String runtimeid = WfEntry.iv().getSession(lsh);
				// if (runtimeid != null && StringUtils.isNotEmpty(runtimeid)) {
				//
				// TWfRuntime runtime = WfEntry.iv().useCoreView()
				// .loadRuntime(runtimeid);
				// // 流程结束 runtime.isDone()已办结
				// // 是否已经提交审批 runtime.isRunning()已处理
				//
				// if (runtime.isReady() == true) {
				// if (user.getUserCode().equals(clientId)) {
				// group.setStatus("<font color='red'>未提交</font>");
				// group.setRun(false);// 编辑
				// } else {
				// group.setStatus("<font color='red'>未提交</font>");
				// group.setRun(true);// 查看
				// }
				// }
				// if (runtime.isDone() == true) {
				// group.setStatus("<font color='#1285C9'>已办结</font>");
				// group.setRun(true);// 查看
				// }
				// if (runtime.isRunning() == true) {
				// group.setStatus("<font color='green'>处理中</font>");
				// group.setRun(true);// 查看
				// }
				// } else {
				// if (user.getUserCode().equals(clientId)) {
				// group.setStatus("<font color='red'>未提交</font>");
				// group.setRun(false);// 编辑
				// } else {
				// group.setStatus("<font color='red'>未提交</font>");
				// group.setRun(true);// 查看
				// }
				// }
				// group.setRuntimeid(runtimeid);

				String jsonStr = JSONUtil2.fromBean(group, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 显示明细
	public void onListdetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		String fatherlsh = request.getParameter("fatherlsh");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			DyForm dyform = DyEntry.iv().loadForm(formcode);

			List list = new ArrayList();

			if (dyform.getSubform_() != null) {
				DyForm subdyform = dyform.getSubform_()[0];

				DyFormData dydata = new DyFormData();
				dydata.setFormcode(subdyform.getFormcode());
				dydata.setFatherlsh(fatherlsh);

				if (StringUtils.isNotEmpty(fatherlsh)) {
					list = DyEntry.iv().queryData(dydata, 0, 9999999, "");
				}
			}

			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DyFormData group = (DyFormData) iterator.next();

				String jsonStr = JSONUtil2.fromBean(group, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(9999999, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 显示扩展明细或汇总信息列表
	public void onListExtdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		String conditions = request.getParameter("conditions");
		String extmode = request.getParameter("extmode");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();

			JSONArray jsonArray = JSONArray.fromObject(conditions);
			StringBuffer extconditions = new StringBuffer();
			int size = 9999999;
			if ("1".equals(extmode)) {
				if (jsonArray.size() > 0) {
					extconditions.append(" and lsh IN (");
					String split = "";
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						extconditions.append(split + "'" + obj.getString("lsh")
								+ "'");
						split = ",";
					}
					extconditions.append(")  ");

					if ("1".equals(extmode)) {
						extconditions.append(" order by created desc ");
					}

				}
				size = 20;
			}
			User user = getOnlineUser(request);
			DyFormData dydata = new DyFormData();
			dydata.setFormcode(formcode);
			dydata.setFatherlsh("1");
			if (!"adminx".equals(user.getUserCode())) {
				dydata.setParticipant(user.getUserCode());
			}
			List list = new ArrayList();

			list = DyEntry.iv().queryData(dydata, 0, size,
					extconditions.toString());

			StringBuffer jsonBuffer = new StringBuffer();
			if ("1".equals(extmode)) {
				String split = "";
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DyFormData group = (DyFormData) iterator.next();

					String jsonStr = JSONUtil2.fromBean(group, "yyyy-MM-dd")
							.toString();
					jsonBuffer.append(split);
					jsonBuffer.append(jsonStr);

					split = ",";
				}

			} else if ("2".equals(extmode)) {
				List<DyFormColumn> listx = DyEntry.iv().queryColumnX(formcode,
						"3");
				DyFormColumn[] extdyformx = (DyFormColumn[]) listx
						.toArray(new DyFormColumn[listx.size()]);
				Map<String, DyFormData> summaryMap = new HashMap<String, DyFormData>();

				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DyFormData group = (DyFormData) iterator.next();
					StringBuffer key = new StringBuffer();
					for (DyFormColumn csColumn : extdyformx) {
						String cid = csColumn.getColumnid();
						String value = BeanUtils.getProperty(group, cid);
						key.append("|^|" + value);
					}
					if (summaryMap.containsKey(key.toString())) {
						DyFormData data = summaryMap.get(key.toString());
						Integer sum = Integer.parseInt(data.getStatistical());
						data.setStatistical("" + (sum + 1));
						data.setLshs(data.getLshs() + group.getLsh() + ",");
						summaryMap.put(key.toString(), data);
					} else {
						group.setStatistical("1");
						group.setLshs(group.getLsh() + ",");
						summaryMap.put(key.toString(), group);
					}
				}
				String split = "";
				for (Iterator iterator = summaryMap.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					DyFormData data = summaryMap.get(key);

					String jsonStr = JSONUtil2.fromBean(data, "yyyy-MM-dd")
							.toString();
					jsonBuffer.append(split);
					jsonBuffer.append(jsonStr);

					split = ",";
				}
			}

			super.writeJsonStr(response, super.buildJsonStr(9999999, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<String, Boolean> permission(HttpServletRequest request,
			boolean isedit, boolean ispermission) throws Exception {
		String lsh = request.getParameter("lsh");
		String query = request.getParameter("query");
		String workcode = request.getParameter("workcode");
		String naturalname = request.getParameter("naturalname");
		if (StringUtils.isNotEmpty(workcode)) {
			isedit = false;
			TWfWorklist wlx = WfEntry.iv().loadWorklist(workcode);
			TWfActive active = WfEntry.iv().loadRuntimeActive(
					wlx.getProcessid(), wlx.getActivityid(), naturalname, "",
					wlx.getRuntimeid());

			boolean isFormEdit = active.isFormEdit();
			boolean isFirstAct = WfEntry.iv().checkFirstAct(workcode);
			if (isFormEdit) {
				ispermission = true;
				isedit = true;
			} else {
				isedit = false;
				ispermission = false;
			}
			if (isFirstAct) {// 退回创建者 开启修改表单功能
				ispermission = true;
				isedit = true;
				request.setAttribute("isFirstAct", isFirstAct);
			}
			request.setAttribute("isEditAct", isedit);
		} else {
			ispermission = true;
			isedit = true;
		}

		if (StringUtils.isEmpty(lsh)) {// 新建
			User user = getOnlineUser(request);
			// boolean permission = SecurityEntry.iv().permission(1
			// user.getUserCode(), naturalname);
			boolean permission = AppEntry.iv().canCreate(naturalname,
					user.getUserName() + "[" + user.getUserCode() + "]");

			request.setAttribute("permission", permission);
		} else {
			request.setAttribute("permission", true);
		}
		if ("look".equals(query)) {// 查看
			isedit = false;
		}

		// 检查确认状态 珠宝业务
		if (StringUtils.isNotEmpty(lsh)) {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			DyFormData dydata = DyEntry.iv().loadData(formcode, lsh);
			if ("01".equals(dydata.getStatusinfo())) {
				isedit = false;
			}
		}

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("isedit", isedit);
		map.put("ispermission", ispermission);
		return map;
	}

	public ActionForward onEditViewMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		request.setAttribute("htmlendinfo", dyform.getHtmlendinfo_());
		String workcode = request.getParameter("workcode");
		String query = request.getParameter("query");
		String lsh = request.getParameter("lsh");
		loadNavInfo(request);
		boolean isedit = true;
		boolean ispermission = true;// 是否启用鉴权
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");

		load(mapping, form, request, response, isedit, ispermission, false);

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frameSCMExt/editframe-" + naturalname
				+ ".jsp");
		String forward = "/frameSCMExt/editframe.jsp";
		if (file.exists()) {
			forward = "/frameSCMExt/editframe-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
		// return mapping.findForward("onEditView");
	}

	// 打印
	public ActionForward onPrintViewMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String naturalname = request.getParameter("naturalname");
		String excel = request.getParameter("excel");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		request.setAttribute("htmlendinfo", dyform.getHtmlendinfo_());
		String workcode = request.getParameter("workcode");
		String query = request.getParameter("query");
		String lsh = request.getParameter("lsh");
		loadNavInfo(request);
		boolean isedit = true;
		boolean ispermission = true;// 是否启用鉴权
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");

		load(mapping, form, request, response, isedit, ispermission, true);

		if ("1".equals(excel)) {
			return mapping.findForward("onPrintExcelView");
		} else {
			return mapping.findForward("onPrintView");
		}
	}

	// 表单预览
	public ActionForward onPreviewMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String formcode = request.getParameter("formcode");
		String $isedit = request.getParameter("isedit");
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		boolean isedit = false;
		if ("1".equals($isedit))
			isedit = true;

		Map issubmap = new HashMap();
		issubmap.put(-1, isedit);
		loadDyForm(mapping, form, request, response, isedit, issubmap);
		return mapping.findForward("onPreviewMain");
	}

	private void loadNavInfo(HttpServletRequest request) throws Exception {
		String workcode = request.getParameter("workcode");
		String naturalname = request.getParameter("naturalname");
		String cuibang = request.getParameter("cuibang");
		if ("true".equals(cuibang)) {
			User user = getOnlineUser(request);
			request.setAttribute("curruser", user.getUserCode());
		}
		if (StringUtils.isEmpty(workcode))
			workcode = "";
		String activityName = WfEntry.iv().getActivityName(naturalname,
				workcode);
		if (StringUtils.isNotEmpty(workcode)) {
			TWfActive twfActive = WfEntry.iv()
					.loadActive(naturalname, workcode);
			request.setAttribute("isFobitzb", twfActive.isFobitzb());
		} else {
			request.setAttribute("isFobitzb", true);
		}

		request.setAttribute("activityName", activityName);
	}

	// 加载
	private void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isedit, boolean permission, boolean isprint)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String lsh = request.getParameter("lsh");
		String workcode = request.getParameter("workcode");
		String naturalname = request.getParameter("naturalname");
		Map groupMap = new HashMap();
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String processid = app.getWorkflowCode_();
			User user = getOnlineUser(request);// 获取当前用户
			String formhtml = "";
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			if (permission) {

				TWfActive act = WfEntry.iv().listCurrentActive(naturalname,
						workcode, user.getUserCode());
				DyEntry.iv().permission(dyform, user.getUserCode(), act);// 表单鉴权
			}

			// 重写dyform对象
			// String path =
			// request.getSession().getServletContext().getRealPath(
			// "/");// 应用服务器目录
			// File file = new File(path + "/frameSCMExt/" + naturalname
			// + ".jcode");
			// if (file.exists()) {
			// StringBuffer script = new StringBuffer();
			//
			// BufferedReader in = new BufferedReader(new FileReader(file));
			// String str;
			// while ((str = in.readLine()) != null) {
			// script.append(str);
			// }
			// in.close();
			//
			// Object[] objArr = { dyform,request };
			//
			// WorkflowConsole console = (WorkflowConsole) RmiEntry
			// .iv("wfhandle");
			// console.exeScript(script.toString(), objArr);
			// }

			// 单品
			String ext = request.getParameter("ext");
			if ("APPFRAME.APPFRAME.JEWELRY.JEWELRY.JEWELRYAPP25"
					.equals(naturalname)) {
				if (StringUtils.isEmpty(ext)) {
					DyFormData dydata = new DyFormData();
					dydata.setFormcode(formcode);
					dydata.setLsh(lsh);
					dydata.setFatherlsh("1");
					List list = DyEntry.iv().queryData(dydata, 0, 1, "");
					if (list.size() > 0) {
						DyFormData mapx = (DyFormData) list.get(0);
						ext = mapx.getColumn14();
					}
				}

				DyForm[] subDyForms = dyform.getSubform_();
				for (int j = 0; j < subDyForms.length; j++) {
					DyFormColumn[] subcolumnx = subDyForms[j].getAllColumn_();

					for (int i = 0; i < subcolumnx.length; i++) {
						subcolumnx[i].setHidden(true);
					}

					for (int i = 0; i < subcolumnx.length; i++) {
						String colid = subcolumnx[i].getColumnid();

						if ("dl001".equals(ext)) {
							if ("column4".equals(colid)
									|| "column48".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column51".equals(colid)
									|| "column50".equals(colid)
									|| "column11".equals(colid)
									|| "column12".equals(colid)
									|| "column13".equals(colid)
									|| "column14".equals(colid)
									|| "column15".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column18".equals(colid)
									|| "column19".equals(colid)
									|| "column21".equals(colid)
									|| "column22".equals(colid)
									|| "column24".equals(colid)
									|| "column25".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl002".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column27".equals(colid)
									|| "column28".equals(colid)
									|| "column53".equals(colid)
									|| "column30".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl003".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column51".equals(colid)
									|| "column12".equals(colid)
									|| "column14".equals(colid)
									|| "column18".equals(colid)
									|| "column20".equals(colid)
									|| "column24".equals(colid)
									|| "column25".equals(colid)
									|| "column26".equals(colid)
									|| "column54".equals(colid)
									|| "column37".equals(colid)
									|| "column38".equals(colid)
									|| "column39".equals(colid)
									|| "column40".equals(colid)
									|| "column55".equals(colid)
									|| "column56".equals(colid)
									|| "column57".equals(colid)
									|| "column44".equals(colid)
									|| "column45".equals(colid)
									|| "column60".equals(colid)
									|| "column61".equals(colid)
									|| "column62".equals(colid)
									|| "column27".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl004".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column12".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column21".equals(colid)
									|| "column24".equals(colid)
									|| "column54".equals(colid)
									|| "column36".equals(colid)
									|| "column37".equals(colid)
									|| "column38".equals(colid)
									|| "column40".equals(colid)
									|| "column55".equals(colid)
									|| "column56".equals(colid)
									|| "column57".equals(colid)
									|| "column46".equals(colid)
									|| "column64".equals(colid)
									|| "column65".equals(colid)
									|| "column66".equals(colid)
									|| "column67".equals(colid)
									|| "column69".equals(colid)
									|| "column70".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl005".equals(ext)) {

							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column16".equals(colid)
									|| "column24".equals(colid)
									|| "column59".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl006".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column15".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column24".equals(colid)
									|| "column27".equals(colid)
									|| "column28".equals(colid)
									|| "column53".equals(colid)
									|| "column59".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {

								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl007".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column24".equals(colid)
									|| "column27".equals(colid)
									|| "column28".equals(colid)
									|| "column53".equals(colid)
									|| "column30".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl008".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column24".equals(colid)
									|| "column27".equals(colid)
									|| "column28".equals(colid)
									|| "column53".equals(colid)
									|| "column30".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl009".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column24".equals(colid)
									|| "column27".equals(colid)
									|| "column28".equals(colid)
									|| "column53".equals(colid)
									|| "column30".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl010".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column24".equals(colid)
									|| "column27".equals(colid)
									|| "column28".equals(colid)
									|| "column53".equals(colid)
									|| "column30".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl011".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column12".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column21".equals(colid)
									|| "column24".equals(colid)
									|| "column54".equals(colid)
									|| "column36".equals(colid)
									|| "column37".equals(colid)
									|| "column38".equals(colid)
									|| "column40".equals(colid)
									|| "column55".equals(colid)
									|| "column56".equals(colid)
									|| "column57".equals(colid)
									|| "column46".equals(colid)
									|| "column64".equals(colid)
									|| "column65".equals(colid)
									|| "column66".equals(colid)
									|| "column67".equals(colid)
									|| "column69".equals(colid)
									|| "column70".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl012".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column12".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column21".equals(colid)
									|| "column24".equals(colid)
									|| "column54".equals(colid)
									|| "column36".equals(colid)
									|| "column37".equals(colid)
									|| "column38".equals(colid)
									|| "column40".equals(colid)
									|| "column55".equals(colid)
									|| "column56".equals(colid)
									|| "column57".equals(colid)
									|| "column46".equals(colid)
									|| "column64".equals(colid)
									|| "column65".equals(colid)
									|| "column66".equals(colid)
									|| "column67".equals(colid)
									|| "column69".equals(colid)
									|| "column70".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl013".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column12".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column21".equals(colid)
									|| "column24".equals(colid)
									|| "column54".equals(colid)
									|| "column36".equals(colid)
									|| "column37".equals(colid)
									|| "column38".equals(colid)
									|| "column40".equals(colid)
									|| "column55".equals(colid)
									|| "column56".equals(colid)
									|| "column57".equals(colid)
									|| "column46".equals(colid)
									|| "column64".equals(colid)
									|| "column65".equals(colid)
									|| "column66".equals(colid)
									|| "column67".equals(colid)
									|| "column69".equals(colid)
									|| "column70".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl014".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column16".equals(colid)
									|| "column24".equals(colid)
									|| "column59".equals(colid)
									|| "column31".equals(colid)
									|| "column32".equals(colid)
									|| "column33".equals(colid)
									|| "column34".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}
						if ("dl015".equals(ext)) {
							if ("column4".equals(colid)
									|| "column52".equals(colid)
									|| "column7".equals(colid)
									|| "column49".equals(colid)
									|| "column11".equals(colid)
									|| "column12".equals(colid)
									|| "column16".equals(colid)
									|| "column17".equals(colid)
									|| "column19".equals(colid)
									|| "column21".equals(colid)
									|| "column24".equals(colid)
									|| "column54".equals(colid)
									|| "column36".equals(colid)
									|| "column37".equals(colid)
									|| "column38".equals(colid)
									|| "column40".equals(colid)
									|| "column55".equals(colid)
									|| "column56".equals(colid)
									|| "column57".equals(colid)
									|| "column46".equals(colid)
									|| "column64".equals(colid)
									|| "column65".equals(colid)
									|| "column66".equals(colid)
									|| "column67".equals(colid)
									|| "column69".equals(colid)
									|| "column70".equals(colid)
									|| "column72".equals(colid)
									|| "column73".equals(colid)
									|| "column74".equals(colid)
									|| "column75".equals(colid)
									|| "column76".equals(colid)
									|| "column77".equals(colid)
									|| "column78".equals(colid)
									|| "column79".equals(colid)
									|| "column80".equals(colid)) {
								subcolumnx[i].setHidden(false);
							}
						}

					}

				}
			}

			TWfActive act = WfEntry.iv().listCurrentActive(naturalname,
					workcode, user.getUserCode());
			Map subformmode = act.getSubformmode();
			// if (StringUtils.isNotEmpty(lsh) &&
			// StringUtils.isNotEmpty(formcode)) {
			formhtml = ins.load(workcode, naturalname, dyform, lsh, isedit,
					subformmode, user.getNLevelName() + "/"
							+ user.getUserName() + "," + user.getNLevelName()
							+ "/", request.getParameter("url"), user, isprint);
			// }
			request.setAttribute("form", formhtml.toString());

			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("AvailWidth", DyFormBuildHtmlExt.AvailWidth
					- DyFormBuildHtmlExt.AvailWidthCorrect);
			if (StringUtils.isNotEmpty(workcode)) {
				TWfWorklist twf = WfEntry.iv().loadWorklist(workcode);
				request.setAttribute("runtimeid", twf.getRuntimeid());

			} else {
				request.setAttribute("processid", processid);
			}
			request.setAttribute("ValidateScript", DyFormBuildHtmlExt
					.buildValidateScript(dyform));
			request
					.setAttribute("datecompFunc", DyFormComp
							.getInitFuncScript());
			request.setAttribute("formcode", formcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 加载
	private void loadDyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isedit, Map issubedit) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String formcode = request.getParameter("formcode");
		Map groupMap = new HashMap();
		try {
			User user = getOnlineUser(request);// 获取当前用户
			String formhtml = "";
			DyForm dyform = DyEntry.iv().loadForm(formcode);

			// if (StringUtils.isNotEmpty(lsh) &&
			// StringUtils.isNotEmpty(formcode)) {
			formhtml = ins.load("", "", dyform, "", isedit, issubedit, user
					.getNLevelName()
					+ "/" + user.getUserName() + "," + user.getNLevelName(),
					request.getParameter("url"), user, false);
			// }
			request.setAttribute("form", formhtml.toString());

			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("AvailWidth", DyFormBuildHtmlExt.AvailWidth
					- DyFormBuildHtmlExt.AvailWidthCorrect);
			request.setAttribute("ValidateScript", DyFormBuildHtmlExt
					.buildValidateScript(dyform));
			request
					.setAttribute("datecompFunc", DyFormComp
							.getInitFuncScript());
			request.setAttribute("formcode", formcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public ActionForward onEditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workcode = request.getParameter("workcode");
		boolean isedit = true;
		if (StringUtils.isNotEmpty(workcode)) {
			isedit = false;
		}
		load(mapping, form, request, response, isedit, false, false);
		return mapping.findForward("onEditView");
	}

	public ActionForward onQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		load(mapping, form, request, response, false, false, false);
		return mapping.findForward("onEditView");
	}

	// 保存
	public ActionForward onSavaOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String naturalname = request.getParameter("naturalname");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		DyFormData mainform = this.getBean(request);
		String subform = request.getParameter("subform");
		try {
			User user = getOnlineUser(request);
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = ins.saveAndUpdate(request, user.getUserName() + "["
					+ user.getUserCode() + "]", formcode, mainform, subform,
					"1");
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "保存信息失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
		return null;
	}

	// 物理删除
	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = "";
			jsonx = ins.delete(request, formcode, lsh);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "作废失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 逻辑删除
	public void onLogicDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String workcode = request.getParameter("workcode");
		try {
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = "";
			if (StringUtils.isNotEmpty(workcode)) {// 存在workcode逻辑删除
				boolean isFirstAct = WfEntry.iv().checkFirstAct(workcode);
				if (!isFirstAct) {
					json.put("error", "yes");
					json.put("tip", "流程不在发起点，不能作废!");
				} else {
					jsonx = ins.deleteByLogic(request, formcode, lsh);
					json = JSONObject.fromObject(jsonx);
				}
			}
		} catch (Exception e) {
			json.put("tip", "作废失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	/**
	 * 客户选择页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onEditViewClient(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = BaseService.getOnlineUser(request);// 获取当前用户
		request.setAttribute("onlineName", user.getUserName());
		return mapping.findForward("onEditViewClient");
	}

	/**
	 * 获取部门节点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void onGetWfNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String lsh = request.getParameter("lsh");
		String runtimeid = request.getParameter("runtimeid");
		String naturalname = request.getParameter("naturalname");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(lsh)) {
				User user = getOnlineUser(request);
				AppObj app = AppEntry.iv().loadApp(naturalname);
				Map map = ins.newWfNode(request, naturalname, app
						.getWorkflowCode_(), runtimeid, user, lsh, user
						.getUserName()
						+ "[" + user.getUserCode() + "]");
				json = JSONObject.fromObject(map);
			}
		} catch (Exception e) {
			json.put("tip", "失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 新建结束
	public void onNewEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String activityid = request.getParameter("activityid");
		String limittimes = request.getParameter("limittime");
		String processlen = request.getParameter("processlen");
		String operatemode = request.getParameter("operatemode");
		String issync_ = request.getParameter("issync");
		JSONObject json = new JSONObject();
		String notice = request.getParameter("notice");
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String filteractiveids = request.getParameter("filteractiveids");
		try {
			User user = getOnlineUser(request);
			boolean issync = false;
			if (StringUtils.isNotEmpty(issync_)) {
				if ("1".equals(issync_)) {
					issync = true;
				} else if ("0".equals(issync_)) {
					issync = false;
				}
			}
			AppObj app = AppEntry.iv().loadApp(naturalname);
			String formcode = app.getDyformCode_();
			String jsonx = ins.newEnd(request, naturalname, formcode + lsh,
					activityid, filteractiveids, workcode, user.getUserName()
							+ "[" + user.getUserCode() + "]", userCodes,
					userNames, limittimes, processlen, issync, operatemode);
			json = JSONObject.fromObject(jsonx);
			// 发送短信
			if (StringUtils.isNotEmpty(notice)) {
				if ("0".equals(notice) && !json.containsKey("error")) {// 0发送1不发送
					String[] userCode_ = userCodes.split(",");
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, lsh, naturalname);
					}
				}
			}

			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 新建结束
	public void onAuditEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String limittimes = request.getParameter("limittime");
		String activityid = request.getParameter("activityid");
		String issync_ = request.getParameter("issync");
		JSONObject json = new JSONObject();
		String notice = request.getParameter("notice");
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String[] userCode_ = userCodes.split(",");
		try {
			User user = getOnlineUser(request);
			boolean issync = false;
			if (StringUtils.isNotEmpty(issync_)) {
				if ("1".equals(issync_)) {
					issync = true;
				} else if ("0".equals(issync_)) {
					issync = false;
				}
			}
			String jsonx = ins.auditEnd(request, naturalname, activityid,
					workcode, user.getUserName() + "[" + user.getUserCode()
							+ "]", userCodes, userNames, limittimes, issync);
			json = JSONObject.fromObject(jsonx);

			// 发送短信
			if (StringUtils.isNotEmpty(notice)) {
				if ("0".equals(notice) && !json.containsKey("error")) {// 0发送1不发送
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, lsh, naturalname);
					}
				}
			}

		} catch (Exception e) {
			json.put("tip", "失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 转办结束
	public void onAssignEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String userCodes = request.getParameter("usercode");
		String userNames = request.getParameter("username");
		String activityid = request.getParameter("activityid");
		String limittimes = request.getParameter("limittime");
		String processlen = request.getParameter("processlen");
		String operatemode = request.getParameter("operatemode");
		String issync_ = request.getParameter("issync");
		JSONObject json = new JSONObject();
		String notice = request.getParameter("notice");
		String naturalname = request.getParameter("naturalname");
		String lsh = request.getParameter("lsh");
		String filteractiveids = request.getParameter("filteractiveids");
		try {
			User user = getOnlineUser(request);
			String jsonx = ins.assignEnd(request, naturalname, activityid,
					workcode, user.getUserName() + "[" + user.getUserCode()
							+ "]", userCodes, userNames);
			json = JSONObject.fromObject(jsonx);
			// 发送短信
			if (StringUtils.isNotEmpty(notice)) {
				if ("0".equals(notice) && !json.containsKey("error")) {// 0发送1不发送
					String[] userCode_ = userCodes.split(",");
					for (int i = 0; i < userCode_.length; i++) {
						WfEntry.iv().notice(user.getUserCode(), userCode_[i],
								"", workcode, lsh, naturalname);
					}
				}
			}
		} catch (Exception e) {
			json.put("tip", "失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 保存意见
	public void onSaveYijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String workcode = request.getParameter("workcode");
		String yijian = request.getParameter("yijian");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(yijian)) {
				yijian = yijian.trim();
			}
			User user = getOnlineUser(request);
			String jsonx = ins.saveYijian(request, workcode,
					user.getUserCode(), yijian);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 加载意见
	public void onLoadYijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workcode = request.getParameter("workcode");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(workcode)) {
				workcode = workcode.trim();
			}
			// 根据WORKCODE 获取意见 加载意见
			User user = getOnlineUser(request);
			// 根据WORKCODE 获取意见 加载意见
			TWfParticipant worklist = WfEntry.iv().loadParticipantinfo(
					workcode, user.getUserCode());
			String jsonx = "";
			if (worklist != null) {
				jsonx = worklist.getAuditnode();
			}

			json.put("yinjian", jsonx);
		} catch (Exception e) {
			json.put("tip", "失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	/**
	 * 审核页面 通过、特送、退办
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward onAuditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		loadNavInfo(request);
		String map = show(request, false);
		request.setAttribute("permission", true);
		/** 样式 */
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		return mapping.findForward("onAuditView");
	}

	// 确认状态
	public void onConfirmStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		FrameService ins = (FrameService) WebApplicationContextUtils
		.getRequiredWebApplicationContext(servlet.getServletContext())
		.getBean("frameService");
		JSONObject json = new JSONObject();
		try {
			String jsonstr = ins.saveConfirmStatus(request, response);
			json = json.fromObject(jsonstr);
		} catch (Exception e) {
			json.put("tip", "确认失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 反确认状态
	public void onUnConfirmStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		FrameService ins = (FrameService) WebApplicationContextUtils
		.getRequiredWebApplicationContext(servlet.getServletContext())
		.getBean("frameService");
		JSONObject json = new JSONObject();
		try {
			String jsonstr = ins.saveUnConfirmStatus(request, response);
			json = json.fromObject(jsonstr);
		} catch (Exception e) {
			json.put("tip", "反确认失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	private DyFormData getBean(HttpServletRequest request) {
		DyFormData data = new DyFormData();
		super.setJavaBean(request, data);
		return data;
	}

	public ActionForward onShowView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String map = show(request, true);
		return mapping.findForward(map);
	}

	/**
	 * 显示流程节点
	 * 
	 * @param request
	 * @param isShowSpecialNode
	 *            是否显示抄送或抄阅
	 * @return
	 * @throws Exception
	 */
	private String show(HttpServletRequest request, boolean isShowSpecialNode)
			throws Exception {
		request.setAttribute("permission", true);
		String chooseFlag = request.getParameter("chooseresult");
		String workcode = request.getParameter("workcode");
		String operatemode = request.getParameter("operatemode");
		String naturalname = request.getParameter("naturalname");
		String commiter = request.getParameter("commiter");
		String filteractiveids = request.getParameter("filteractiveids");// 过滤后的节点

		TWfWorklist bean = (TWfWorklist) WfEntry.iv().loadWorklist(workcode);
		String activityid = bean.getActivityid();
		String runtimeid = bean.getRuntimeid();// 流程实例id
		List<Map> result = new ArrayList();

		User user = getOnlineUser(request);
		String node = user.getDepartmentId();
		request.setAttribute("isAndBranchMode", false);
		if ("03".equals(operatemode)) {// 抄阅
			request.setAttribute("helpTip", "帮助提示: 点击完成,抄阅完毕。");
			request.setAttribute("processEndTip", "抄阅完毕。");
			request.setAttribute("processTitle", "抄阅完毕");
			result.addAll(listTrackActionEnd("抄阅完毕"));
			request.setAttribute("processList", result);
			return "onShowEndView";
		} else if (filteractiveids != null
				&& filteractiveids
						.contains(FrameService.trackActionSpecialTypeEND)) {// 归档
			request.setAttribute("helpTip", "帮助提示: 流程结束,请点击完成,结束流程。");
			request.setAttribute("processEndTip", "流程结束。");
			request.setAttribute("processTitle", "归档");
			result.addAll(listTrackActionEnd("归档"));
			request.setAttribute("processList", result);
			request.setAttribute("isend", "true");
			return "onShowEndView";
		}

		AppObj app = AppEntry.iv().loadApp(naturalname);
		String processid = app.getWorkflowCode_();

		String[] filteractiveids_ = null;
		if (StringUtils.isNotEmpty(filteractiveids)) {
			filteractiveids_ = filteractiveids.split(",");
		}
		boolean isNeedReader = false;
		boolean isNeedAssistant = false;

		/** 样式 */
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String linkcss = DyFormComp.getStyle(getURL(dyform.getStyleinfourl_()));
		request.setAttribute("linkcss", linkcss);
		request.setAttribute("htmltitleinfo", dyform.getHtmltitleinfo_());
		TWfWorklist wlx = bean;
		TWfActive active = WfEntry.iv().loadRuntimeActive(wlx.getProcessid(),
				wlx.getActivityid(), naturalname, "", wlx.getRuntimeid());
		if (chooseFlag.equals("0")) {

			// 获得所有的下一步节点
			List resultList = WfEntry.iv().listNextRouteActive(processid,
					activityid, runtimeid, user.getUserCode());
			if (resultList.size() > 0) {
				for (Iterator iterator = resultList.iterator(); iterator
						.hasNext();) {
					TWfActive object = (TWfActive) iterator.next();

					Map tempMap = new HashMap();

					if (object.isNeedReader() == true) {
						isNeedReader = object.isNeedReader();
					}
					if (object.isNeedAssistant() == true) {
						isNeedAssistant = object.isNeedAssistant();
					}

					tempMap.put("pmode", object.getParticipantmode());
					tempMap.put("autoroute", object.isAutoroute());
					tempMap.put("needsync", object.isNeedsync());
					tempMap.put("needsearch", object.isNeedsearch());
					tempMap.put("singleman", object.isSingleman());
					tempMap.put("needtree", object.isNeedTree());
					tempMap.put("name", object.getName());
					tempMap.put("type", object.getParticipantmode());
					String participant = object.getParticipant();
					participant = participant == null ? "" : participant;
					setWfUser(participant, tempMap);
					tempMap.put("value", "0");
					tempMap.put("activeids", object.getId());

					if (filteractiveids_ != null) {
						for (int i = 0; i < filteractiveids_.length; i++) {
							if (object.getId().equalsIgnoreCase(
									filteractiveids_[i])) {
								result.add(tempMap);
							}
						}
					} else {
						result.add(tempMap);
					}
				}
				// 判断存在归档按钮 显示归档按钮
				boolean isfirst = WfEntry.iv().loadProcess(wlx.getProcessid())
						.getActivity(wlx.getActivityid()).isStartActivity();
				// 是否是创建者
				if (AppHandleIfc._PARTICIPANT_MODE_CREATER.equals(active
						.getParticipantmode())
						&& StringUtils.isEmpty(filteractiveids) && !isfirst) {
					result.addAll(listTrackActionEnd("归档"));
				}
			} else {
				request.setAttribute("helpTip", "帮助提示: 流程结束,请点击完成,结束流程。");
				request.setAttribute("processEndTip", "流程结束。");
				request.setAttribute("processTitle", "归档");
				result.addAll(listTrackActionEnd("归档"));
				request.setAttribute("processList", result);
				request.setAttribute("isend", "true");
				return "onShowEndView";
			}

			request.setAttribute("helpTip", "帮助提示：请选择下一个流程节点及办理该节点人员。");
			if (result.size() > 1) {
				request.setAttribute("processTitle", "流程分支");
				// 检查当前节点的下一步分支是否为同步模式
				request.setAttribute("isAndBranchMode", WfEntry.iv()
						.isAndBranchMode(processid, activityid));
			} else {
				request.setAttribute("processTitle", "人员选择");
			}
			if (isNeedAssistant && isShowSpecialNode) {
				result.addAll(listTrackAction1("0"));
			}
			if (isNeedReader && isShowSpecialNode) {
				result.addAll(listTrackAction2("0"));
			}
			// 判断分布式提交
			// 1 start
			if (active.isSyncto()) {
				if ((filteractiveids == null || "".equals(filteractiveids) || FrameService.trackActionSpecialType3
						.equals(filteractiveids))
						&& !WfEntry.iv().checkFirstAct(workcode)) {
					if (StringUtils.isNotEmpty(filteractiveids)) {
						result.addAll(listTrackAction3_2("3"));// 分布式阅读
					} else {
						result.addAll(listTrackAction3("3"));// 分布式阅读
					}

				}
			}
			// 1 end

			request.setAttribute("processList", result);
			return "onShowView";
		} else if (chooseFlag.equals("1")) {
			// /////////////获得所有可退办节点//////////////////
			List processList = WfEntry.iv().listNextBackActive(runtimeid);
			for (Iterator iterator = processList.iterator(); iterator.hasNext();) {
				TWfActivePass object = (TWfActivePass) iterator.next();
				if (object.isNeedReader() == true) {
					isNeedReader = object.isNeedReader();
				}
				if (object.isNeedAssistant() == true) {
					isNeedAssistant = object.isNeedAssistant();
				}
				Map tempMap = new HashMap();
				tempMap.put("autoroute", object.isAutoroute());
				tempMap.put("needsync", object.isNeedsync());
				tempMap.put("singleman", object.isSingleman());
				tempMap.put("needtree", object.isNeedTree());
				tempMap.put("needsearch", object.isNeedsearch());
				tempMap.put("name", object.getName());
				tempMap.put("type", object.getParticipantmode());
				tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_HUMAN);
				if (true) {// 退办
					tempMap.put("value", "0");

					String[] humens = object.getParticipantOld();
					String[] assistants = object.getAssistant();
					String[] readers = object.getReader();
					StringBuffer usercodeSB = new StringBuffer();
					StringBuffer usernameSB = new StringBuffer();

					for (int i = 0; i < humens.length; i++) {
						usercodeSB.append(StringUtils.substringBetween(
								humens[i], "[", "]")
								+ ",");
						usernameSB.append(StringUtils.substringBefore(
								humens[i], "[")
								+ ",");
					}

					for (int i = 0; i < assistants.length; i++) {
						usercodeSB.append(StringUtils.substringBetween(
								assistants[i], "[", "]")
								+ ",");
						usernameSB.append(StringUtils.substringBefore(
								assistants[i], "[")
								+ ",");
					}

					for (int i = 0; i < readers.length; i++) {
						usercodeSB.append(StringUtils.substringBetween(
								readers[i], "[", "]")
								+ ",");
						usernameSB.append(StringUtils.substringBefore(
								readers[i], "[")
								+ ",");
					}

					tempMap
							.put("usercode", StringUtils.substring(usercodeSB
									.toString(), 0, usercodeSB.toString()
									.length() - 1));
					tempMap
							.put("username", StringUtils.substring(usernameSB
									.toString(), 0, usernameSB.toString()
									.length() - 1));

				}
				tempMap.put("activeids", object.getId());
				if (filteractiveids_ != null) {
					for (int i = 0; i < filteractiveids_.length; i++) {
						if (object.getId()
								.equalsIgnoreCase(filteractiveids_[i])) {
							result.add(tempMap);
						}
					}
				} else {
					result.add(tempMap);
				}
			}
			if (isNeedAssistant && isShowSpecialNode) {
				result.addAll(listTrackAction1("0"));
			}
			if (isNeedReader && isShowSpecialNode) {
				result.addAll(listTrackAction2("0"));
			}
			request.setAttribute("helpTip",
					"帮助提示：这里主要是将表单退回到指定点，确认无误点击完成。如果需修改点击上一步，否取消。");
			request.setAttribute("processTitle", "退办");
			request.setAttribute("processList", result);
			return "onShowView";
		} else if (chooseFlag.equals("2")) {
			// // /////////////获得特办节点//////////////////
			// processList = WfEntry.iv().listNextJumpActive(processid,
			// runtimeid);
			//
			// for (Iterator iterator = processList.iterator();
			// iterator.hasNext();) {
			// TWfActive object = (TWfActive) iterator.next();
			//
			// Map tempMap = new HashMap();
			// tempMap.put("autoroute", object.isAutoroute());
			// tempMap.put("needsync", object.isNeedsync());
			// tempMap.put("singleman", object.isSingleman());
			// tempMap.put("name", object.getName());
			// tempMap.put("type", object.getParticipantmode());
			// tempMap.put("pmode", object.getParticipantmode());
			// String participant = object.getParticipant();
			// participant = participant == null ? "" : participant;
			// setWfUser(participant, tempMap);
			// tempMap.put("value", "0");
			// tempMap.put("activeids", object.getId());
			// result.add(tempMap);
			// }
			//
			// // result.addAll(listTrackAction(node));
			// request.setAttribute("helpTip",
			// "帮助提示：这里主要是将表单特送到指定点，确认无误点击完成。如果需修改点击上一步，否取消。");
			// request.setAttribute("processTitle", "特送");
			// request.setAttribute("processList", result);
			// return mapping.findForward("onShowView");
			return "onShowView";
		} else if (chooseFlag.equals("3")) {// 交办
			TWfActive object = WfEntry.iv().listNextZhuanbangActive(workcode,
					commiter);

			Map tempMap = new HashMap();

			if (object.isNeedReader() == true) {
				isNeedReader = object.isNeedReader();
			}
			if (object.isNeedAssistant() == true) {
				isNeedAssistant = object.isNeedAssistant();
			}

			tempMap.put("pmode", object.getParticipantmode());
			tempMap.put("autoroute", object.isAutoroute());
			tempMap.put("needsync", object.isNeedsync());
			tempMap.put("singleman", object.isSingleman());
			tempMap.put("needtree", object.isNeedTree());
			tempMap.put("name", object.getName());
			tempMap.put("needsearch", object.isNeedsearch());
			tempMap.put("type", object.getParticipantmode());
			String participant = object.getParticipant();
			participant = participant == null ? "" : participant;
			setWfUser(participant, tempMap);
			tempMap.put("value", "0");
			tempMap.put("activeids", object.getId());

			if (filteractiveids_ != null) {
				for (int i = 0; i < filteractiveids_.length; i++) {
					if (object.getId().equalsIgnoreCase(filteractiveids_[i])) {
						result.add(tempMap);
					}
				}
			} else {
				result.add(tempMap);
			}
			request.setAttribute("helpTip", "帮助提示：请选择下一个流程节点及办理该节点人员。");
			request.setAttribute("processTitle", "人员选择");
			request.setAttribute("processList", result);
			return "onShowView";
		}
		return null;
	}

	// 系统自动分配页面
	public ActionForward onAutorouteView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("onAutorouteView");
	}

	// 多选
	public ActionForward onMultiSelectResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		selectrec(request);
		return mapping.findForward("onMultiSelectResource");
	}

	// 单选
	public ActionForward onSingleSelectResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		selectrec(request);
		return mapping.findForward("onSingleSelectResource");
	}

	private void selectrec(HttpServletRequest request) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String naturalname = request.getParameter("naturalname");
		String node = request.getParameter("node");
		ResourceNode recnode = ins.getResourceNode(naturalname, node);
		request.setAttribute("node_", recnode.getNode_());
		request.setAttribute("nodeid", recnode.getNodeid());
		request.setAttribute("nodecode", recnode.getNodecode());
		request.setAttribute("nodename", recnode.getNodename());
		request.setAttribute("parentnode", recnode.getParentnode());
	}

	// 加载资源树
	public void onFindResourceTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String node = request.getParameter("node");
		String str = ins.findResourceTree(node);
		super.writeJsonStr(response, str);
	}

	// 加载资源树节点
	public void onListResourceNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String condition = request.getParameter("condition");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(condition)) {
			paramJson = JSONObject.fromObject(condition);
		}
		String node = paramJson.getString("node");
		String type = paramJson.getString("type");
		String conditions = "";
		if (StringUtils.isNotEmpty(conditions)) {
			conditions = paramJson.getString("conditions");
		}
		String condtition = "";
		if (paramJson.containsKey("query")) {
			condtition = paramJson.getString("query");
			if (StringUtils.isNotEmpty(condtition)) {
				condtition = condtition.trim();
			} else {
				condtition = "";
			}
		}
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 页码
		PageInfo pageinfo = ins.findResourceNodeInfo(type, node, condtition,
				start, limit);

		StringBuffer jsonBuffer = new StringBuffer();
		String split = "";
		for (Iterator iterator = pageinfo.getResultList().iterator(); iterator
				.hasNext();) {
			Resource rec = (Resource) iterator.next();

			String jsonStr = JSONUtil2.fromBean(rec, "yyyy-MM-dd").toString();
			jsonBuffer.append(split);
			jsonBuffer.append(jsonStr);
			split = ",";
		}
		super.writeJsonStr(response, super.buildJsonStr(
				pageinfo.getTotalRows(), jsonBuffer.toString()));
	}

	/**
	 * 显示特殊节点
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction1(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "抄送");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType1);
		list.add(tempMap);
		return list;
	}

	/**
	 * 显示特殊节点
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction2(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "抄阅");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType2);
		list.add(tempMap);
		return list;
	}

	/**
	 * 显示特殊节点
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction3(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "阶段性回复");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType3);
		list.add(tempMap);
		return list;
	}

	private List listTrackAction3_2(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "阶段性回复");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialType3);
		list.add(tempMap);
		return list;
	}

	/**
	 * 显示特殊节点
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackActionEnd(String name) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", name);
		tempMap.put("type", "");
		tempMap.put("pmode", "");
		tempMap.put("needsync", false);
		tempMap.put("value", "0");
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		tempMap.put("activeids", FrameService.trackActionSpecialTypeEND);
		list.add(tempMap);
		return list;
	}

	public static void setWfUser(String[] humens, Map tempMap) {
		StringBuffer usercodeSB = new StringBuffer();
		StringBuffer usernameSB = new StringBuffer();

		for (int i = 0; i < humens.length; i++) {
			usercodeSB.append(StringUtils.substringBetween(humens[i], "[", "]")
					+ ",");
			usernameSB
					.append(StringUtils.substringBefore(humens[i], "[") + ",");
		}

		if (humens.length > 0) {
			tempMap.put("usercode", StringUtils.substring(
					usercodeSB.toString(), 0,
					usercodeSB.toString().length() - 1));
			tempMap.put("username", StringUtils.substring(
					usernameSB.toString(), 0,
					usernameSB.toString().length() - 1));
		}
	}

	public static void setWfUser(String humenstrs, Map tempMap) {
		if (StringUtils.isNotEmpty(humenstrs)) {
			String[] humens = humenstrs.split(",");
			setWfUser(humens, tempMap);
		}
	}

	private String[] getUsers_(List<Client3A> list) {
		String split = "";
		StringBuffer sb = new StringBuffer();
		for (Client3A client3A : list) {
			String usercode = client3A.getClientId();
			String username = client3A.getName();

			sb.append(split + username + "[" + usercode + "]");
			split = ",";
		}
		return sb.toString().split(",");
	}

	public void bussTipListView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String workcode = request.getParameter("workcode");
		String runtimeid = request.getParameter("runtimeid");
		String string = "";

		try {
			if (StringUtils.isEmpty(workcode)) {
				if (StringUtils.isEmpty(runtimeid)) {
					return;
				}
			} else {
				runtimeid = WfEntry.iv().getRuntimeIdByWorkcode(workcode);
			}
			List list = WfEntry.iv().listAllParticipantinfo(runtimeid, true);

			if (list.size() != 0) {
				// TWfWorklistExt o = (TWfWorklistExt) list.get(0);
				// if(StringUtils.isNotEmpty(o.getCustomer()))
				// {

				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					TWfParticipant object = (TWfParticipant) iterator.next();

					String donetime = StringUtils.substringBefore(object
							.getDonetime(), ".");
					if ("04".equals(object.getTypes())) {// 阶段性回复
						donetime = StringUtils.substringBefore(object
								.getCreatetime(), ".");
					}

					if (StringUtils.isNotEmpty(object.getAuditnode())) {
						string += "<div style=\"padding:10px\">"
								+ object.getAuditnode()
								+ "</div><div style=\"text-align:center;border-bottom:1px solid #58b4f0;\"><B>"
								+ object.getUsername() + "</B>  " + donetime
								+ "</div>";
					}
				}
				// }
			}

			if (StringUtils.isEmpty(string)) {
				string = "<div style=\"padding:10px\">无</div>";
			}
			// System.out.println("<div style=\"padding:20px\">无</div><div
			// id=\"whodate\" style=\"text-align:center\"></div>");
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(string);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryActive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String workcode = request.getParameter("workcode");
			String runtimeid = request.getParameter("runtimeid");
			if (StringUtils.isNotEmpty(workcode)) {
				runtimeid = WfEntry.iv().getRuntimeIdByWorkcode(workcode);
			}
			List list = WfEntry.iv().listAllParticipantinfo(runtimeid, false);

			String title = "<tr><td nowrap='nowrap' class='table_td_title' width='20%'>提交日期</td><td nowrap='nowrap' class='table_td_title' width='20%'>提交者</td><td nowrap='nowrap' class='table_td_title' width='40%'>流程节点</td><td nowrap='nowrap' class='table_td_title' width='20%'>执行者</td><td nowrap='nowrap' class='table_td_title' width='20%'>完成时间</td></tr>";

			String showHtmlView = "<table  width=\"100%\" border=1>" + title;

			for (int i = 0; i < list.size(); i++) {
				TWfParticipant object = (TWfParticipant) list.get(i);
				String opemode = "";
				if ("02".equals(object.getTypes())) {
					opemode = "[抄送]";
				}
				if ("03".equals(object.getTypes())) {
					opemode = "[抄阅]";
				}
				if ("04".equals(object.getTypes())) {
					opemode = "[阶段性回复]";
				}
				String sync = object.isSync() ? "[同步]" : "";

				String opemodeX = "";
				if ("01".equals(object.getOpemode())) {
					opemodeX = "提交 ";
				} else if ("02".equals(object.getOpemode())) {
					opemodeX = "特送/退办 ";
				} else if ("03".equals(object.getOpemode())) {
					opemodeX = "归档 ";
				} else if ("04".equals(object.getOpemode())) {
					opemodeX = "催办 ";
				} else if ("05".equals(object.getOpemode())) {
					opemodeX = "转办 ";
				}

				String createtime = object.getCreatetime().substring(0, 16);
				String donetime = object.getDonetime();
				donetime = donetime == null || "".equals(donetime) ? "&nbsp;"
						: donetime.substring(0, 16);

				if ("04".equals(object.getTypes())) {// 阶段性回复
					donetime = createtime;
				}

				showHtmlView += "<tr>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ createtime
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ object.getCommitername()
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"40%\"  class=\"label_nd_2\">"
						+ opemodeX
						+ object.getActname()
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ object.getUsername()
						+ opemode
						+ sync
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ donetime + "</td>";

				showHtmlView += "</tr>";
			}
			showHtmlView += "</table>";
			// System.out.println(showHtmlView);
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(showHtmlView);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <br>
	 * 方法说明：构造器 <br>
	 * 输入参数：String URL 互联网的网页地址。 <br>
	 * 返回类型：
	 */
	public String getURL(String URL) throws Exception {
		StringBuffer sb = new StringBuffer();
		// 创建一个URL对象
		URL url = new URL(URL);

		// 读取从服务器返回的所有文本
		BufferedReader in = new BufferedReader(new InputStreamReader(url
				.openStream()));
		String str;
		while ((str = in.readLine()) != null) {
			// 这里对文本出来
			sb.append(str);
		}
		in.close();
		return sb.toString();
	}

	// start Ext

	public ActionForward onEditFrameExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workcode = request.getParameter("workcode");
		boolean isedit = true;
		boolean ispermission = true;// 是否启用鉴权
		if (StringUtils.isNotEmpty(workcode)) {
			isedit = false;
		}
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");
		loadExt(mapping, form, request, response, isedit, ispermission);
		return mapping.findForward("onEditFrameExt");
	}

	public void onListExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String conditions = request.getParameter("conditions");
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(conditions)) {
			paramJson = JSONObject.fromObject(conditions);
		}
		User user = getOnlineUser(request);
		String formcode = request.getParameter("formcode");
		String fatherlsh = request.getParameter("fatherlsh");
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 页码
		PageInfo obj = new PageInfo();
		try {
			Integer from_ = Integer.parseInt(start);
			Integer limit_ = Integer.parseInt(limit);
			Integer to_ = from_ + limit_ - 1;

			DyFormData dydata = new DyFormData();
			BeanUtils.copyProperties(dydata, paramJson);
			dydata.setFatherlsh(fatherlsh);
			dydata.setFormcode(formcode);

			obj = (PageInfo) ins.queryForPage(dydata, from_, limit_, "");
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				DyFormData data = (DyFormData) iterator.next();

				String jsonStr = JSONUtil2.fromBean(data, "yyyy-MM-dd")
						.toString();
				jsonBuffer.append(split);
				jsonBuffer.append(jsonStr);
				split = ",";
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 保存
	public ActionForward onSavaOrUpdateExt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String formcode = request.getParameter("formcode");
		String fatherlsh = request.getParameter("fatherlsh");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		DyFormData mainform = this.getBean(request);
		String subform = request.getParameter("subform");
		try {
			User user = getOnlineUser(request);
			String jsonx = ins.saveAndUpdate(request, user.getUserName() + "["
					+ user.getUserCode() + "]", formcode, mainform, subform,
					fatherlsh);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "保存信息失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
		return null;
	}

	// 物理删除
	public void onDeleteExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String formcode = request.getParameter("formcode");
		String lsh = request.getParameter("lsh");
		try {
			String jsonx = "";
			jsonx = ins.delete(request, formcode, lsh);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "作废失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 加载
	private void loadExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isedit, boolean permission) throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		String lsh = request.getParameter("lsh");
		String formcode = request.getParameter("formcode");
		String fatherlsh = request.getParameter("fatherlsh");
		Map groupMap = new HashMap();
		try {
			User user = getOnlineUser(request);// 获取当前用户
			String formhtml = "";
			DyForm dyform = DyEntry.iv().loadForm(formcode);
			if (permission) {

			}
			// if (StringUtils.isNotEmpty(lsh) &&
			// StringUtils.isNotEmpty(formcode)) {
			Map issubmap = new HashMap();
			issubmap.put(-1, isedit);
			formhtml = ins.load("", "", dyform, lsh, isedit, issubmap, user
					.getUserName()
					+ "," + user.getDepartmentName(), request
					.getParameter("url"), user, false);
			// }
			request.setAttribute("form", formhtml.toString());
			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("ValidateScript", DyFormBuildHtmlExt
					.buildValidateScript(dyform));
			request
					.setAttribute("datecompFunc", DyFormComp
							.getInitFuncScript());
			request.setAttribute("formcode", formcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// end ext

	// 工单查询页面
	public ActionForward dyformDetailQueryMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	// 工单详情报表
	public void dyformDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		ins.dyformDetail(request, response);
	}

	// 工单详情报表
	public void dyformDealDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		ins.dyformDealDetail(request, response);
	}

	// 加载手风琴树HTML
	private void loadAccordtree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalurl = request.getParameter("naturalurl");
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		User user = getOnlineUser(request);
		String usercode = user.getUserCode();
		List<UmsProtectedobject> list = FrameResource.findByPId(naturalurl,
				usercode);
		for (UmsProtectedobject umsProtectedobject : list) {

			boolean perm = SecurityEntry.iv().permission(usercode,
					umsProtectedobject.getNaturalname());

			if (perm) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", umsProtectedobject.getName());
				map.put("json", FrameResource.buildRootTreeRelation(
						umsProtectedobject.getNaturalname(), true, false,
						usercode));

				jsonList.add(map);
			}
		}

		String html = DyFormComp.getEasyuiAccordionTree(jsonList);
		request.setAttribute("accordhtml", html);
	}

	private void setExtQueryColumnVar(HttpServletRequest request,
			String formcode) {
		// 高级查询字段
		request
				.setAttribute("ExtQueryColumn",
						getExtQueryColumn(formcode, "1"));
		request.setAttribute("ExtQueryColumn2", getExtQueryColumn2(formcode,
				"1"));
	}

	private String getExtQueryColumn(String formcode, String model) {
		StringBuffer s = new StringBuffer();

		// c1
		s.append("'"
				+ DyFormComp.getSelectKV("c1", "", "width:\"99%\"", "", false,
						" - ,(-(", "", false) + "'");
		s.append(",");

		// c2
		s.append("'" + getExtQueryColumnC2(formcode, model) + "'");
		s.append(",");

		// c3
		s.append("'"
				+ DyFormComp.getSelectKV("c3", "", "width:\"99%\"", "", false,
						"&gt;->,&lt;-<,=-=,&gt;=->=,&lt;=-<=,like-包含", "",
						false) + "'");
		s.append(",");

		// c4
		s.append("'"
				+ DyFormComp.getText("c4", "", "width:\"99%\"", "", false, "")
				+ "'");
		s.append(",");

		// c5
		s.append("'"
				+ DyFormComp.getSelectKV("c5", "", "width:\"99%\"", "", false,
						" - ,)-)", "", false) + "'");
		s.append(",");

		// c6
		s.append("'"
				+ DyFormComp.getSelectKV("c6", "", "width:\"99%\"", "", false,
						" - ,OR-OR,AND-AND", "", false) + "'");

		return s.toString();
	}

	private String getExtQueryColumnC2(String formcode, String model) {

		try {
			List<DyFormColumn> list = DyEntry.iv()
					.queryColumnX(formcode, model);
			String split = "";
			StringBuffer value = new StringBuffer();
			for (DyFormColumn dyFormColumn : list) {
				// if (dyFormColumn.isHidden() == false) {
				value.append(split + dyFormColumn.getColumnid() + "-"
						+ dyFormColumn.getColumname());
				split = ",";
				// }
			}
			return DyFormComp.getSelectKV("c2", "", "width:\"99%\"", "", false,
					value.toString(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getExtQueryColumn2(String formcode, String model) {
		try {
			List<DyFormColumn> list = DyEntry.iv()
					.queryColumnX(formcode, model);
			String split = "";
			StringBuffer value = new StringBuffer();
			for (DyFormColumn dyFormColumn : list) {
				// if (dyFormColumn.isHidden() == false) {
				value.append(split + "['" + dyFormColumn.getColumname() + "','"
						+ dyFormColumn.getColumnid() + "']");
				split = ",";
				// }
			}
			return value.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 列配置
	public ActionForward colconfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);

		User user = getOnlineUser(request);
		String forms = DyFormBuildHtmlExt.buildCustomColConfigHtml(dyform, user
				.getUserCode());
		String addtabscript = DyFormBuildHtmlExt.buildCustomColConfigJs(dyform);
		request.setAttribute("form", forms);
		request.setAttribute("addtabscript", addtabscript);
		return mapping.findForward("colconfig");
	}

	// 列配置保存
	public void savecolconfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();

		String formcode = request.getParameter("formcode");
		String subform = request.getParameter("subform");
		JSONArray jsonArray = JSONArray.fromObject(subform);

		User user = getOnlineUser(request);
		List result = new ArrayList();
		Map formcodeMap = new HashMap();// 存放更新form数据
		try {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				DyFormData subdata = new DyFormData();
				BeanUtils.copyProperties(subdata, obj);
				subdata.setFatherlsh("1");

				DyFormData data = new DyFormData();
				data.setColumn3(user.getUserCode());
				data.setColumn5(subdata.getFormcode());
				data.setFatherlsh("1");
				String lsh = DyFormBuildHtmlExt.checkColConfigExist(data);
				JSONObject subdatajson = new JSONObject();
				subdatajson = JSONObject.fromObject(subdata);
				subdatajson.remove("belongx");
				subdatajson.remove("timex");
				subdatajson.remove("sys_int_id");
				subdatajson.remove("lshs");
				subdatajson.remove("participant");
				subdatajson.remove("run");
				subdatajson.remove("runtimeid");
				subdatajson.remove("start_time");
				subdatajson.remove("statistical");
				subdatajson.remove("status");
				subdatajson.remove("statusinfo");
				subdatajson.remove("extendattribute");
				subdatajson.remove("fatherlsh");

				if (StringUtils.isNotEmpty(lsh)) {
					data.setColumn4(subdatajson.toString());
					data.setParticipant(user.getUserCode());
					data.setFatherlsh("1");
					data.setLsh(lsh);
					DyEntry.iv().modifyData(formcode, data);
				} else {
					data.setColumn4(subdatajson.toString());
					data.setParticipant(user.getUserCode());
					data.setFatherlsh("1");
					DyEntry.iv().addData(formcode, data);
				}
			}
			json.put("tip", "保存成功!");
		} catch (Exception e) {
			json.put("tip", "保存失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	// 打印 珠宝 首饰销售打印
	public void print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lsh = request.getParameter("lsh");

		final String MY_STYLE1 = "customStyle1";

		final String MY_DATA_STYLE1 = "customDataStyle1";
		final String MY_DATA_STYLE2 = "customDataStyle2";
		final String MY_HEADER_STYLE1 = "customHeaderStyle1";

		final String MY_RIGHT_STYLE1 = "customRIGHTStyle1";

		// 报表管理器
		ReportManager rm = new ReportManager();
		// 获得原始数据表格
		Table t = new Table(14, 7);

		// 首饰销售
		DyFormData dydata0 = new DyFormData();
		dydata0.setFormcode("8a606025a84f11e19b54fb13b166e993_");
		dydata0.setFatherlsh("1");
		dydata0.setLsh(lsh);
		List listx0 = DyEntry.iv().queryData(dydata0, 0, 1, "");
		dydata0 = (DyFormData) listx0.get(0);
		String clientId = dydata0.getColumn8();// 分销商
		String code = dydata0.getColumn3();// 单号

		// 分销商信息
		DyFormData dydata1 = new DyFormData();
		dydata1.setFormcode("697afe8595db11e191e44dc1824bccae_");
		dydata1.setFatherlsh("1");
		dydata1.setColumn4(clientId);
		List listx1 = DyEntry.iv().queryData(dydata1, 0, 1, "");
		dydata1 = (DyFormData) listx1.get(0);
		String address = dydata1.getColumn7();// 联系地址
		String tel = dydata1.getColumn11();// 联系电话

		// 首饰销售明细数据
		DyFormData dydata = new DyFormData();
		dydata.setFormcode("1dde2f9fa81711e19b54fb13b166e993_");
		dydata.setFatherlsh(lsh);
		List listx = DyEntry.iv().queryData(dydata, 0, 6, "");

		for (int i = 0; i < 4; i++) {
			TableRow tr = new TableRow();

			TableCell tc = new TableCell();
			tc.setColSpan(2);
			tr.addCell(tc);
			TableCell tc2 = new TableCell();
			tc2.setIsHidden(true);
			tr.addCell(tc2);

			tr.addCell(new TableCell());
			tr.addCell(new TableCell());
			tr.addCell(new TableCell());
			tr.addCell(tc);
			tr.addCell(tc2);

			t.setRow(i, tr);
		}

		TableRow tr = new TableRow();

		TableCell tc = new TableCell("" + address, Rectangle.ALIGN_CENTER);
		tc.setColSpan(3);
		tr.addCell(tc);
		TableCell tc2 = new TableCell();
		tc2.setIsHidden(true);
		tr.addCell(tc2);
		tr.addCell(tc2);

		TableCell tcx = new TableCell("" + tel, Rectangle.ALIGN_CENTER);
		tcx.setColSpan(2);
		tr.addCell(tcx);
		tr.addCell(tc2);

		TableCell tcx0 = new TableCell(com.jl.common.TimeUtil
				.getYear(new java.util.Date())
				+ "年"
				+ com.jl.common.TimeUtil.getMonth(new java.util.Date())
				+ "月"
				+ com.jl.common.TimeUtil.getDay(new java.util.Date())
				+ "日	", Rectangle.ALIGN_CENTER);
		tcx0.setColSpan(2);
		tr.addCell(tcx0);
		tr.addCell(tc2);

		t.setRow(4, tr);

		TableRow tr00_ = new TableRow();

		TableCell tc00_ = new TableCell("");
		tc00_.setColSpan(2);
		tr00_.addCell(tc00_);
		tr00_.addCell(tc2);

		TableCell tc01_ = new TableCell("");
		tc01_.setColSpan(3);
		tr00_.addCell(tc01_);
		tr00_.addCell(tc2);
		tr00_.addCell(tc2);

		TableCell tc02_ = new TableCell("");
		tc02_.setColSpan(2);
		tr00_.addCell(tc02_);
		tr00_.addCell(tc2);

		tr00_.setType(MY_DATA_STYLE1);
		t.setRow(5, tr00_);

		int cur = 6;
		Double sum = new Double("0");
		for (int j = 0; j < listx.size(); j++) {
			DyFormData object = (DyFormData) listx.get(j);

			TableRow tr00 = new TableRow();

			TableCell tc00 = new TableCell(object.getColumn3());
			tc00.setColSpan(2);
			tr00.addCell(tc00);
			tr00.addCell(tc2);

			TableCell tc01 = new TableCell(object.getColumn4() + " 1件");
			tc01.setColSpan(3);
			tr00.addCell(tc01);
			tr00.addCell(tc2);
			tr00.addCell(tc2);

			TableCell tc02 = new TableCell(object.getColumn15());
			tc02.setColSpan(2);
			tr00.addCell(tc02);
			tr00.addCell(tc2);

			if (object.getColumn15() != null) {
				sum += Double.valueOf(object.getColumn15());
			}

			tr00.setType(MY_DATA_STYLE1);
			t.setRow(cur + j, tr00);
		}

		TableRow tr000_ = new TableRow();

		TableCell tc000_ = new TableCell("");
		tc000_.setColSpan(2);
		tr000_.addCell(tc000_);
		tr000_.addCell(tc2);

		TableCell tc001_ = new TableCell("");
		tc001_.setColSpan(3);
		tr000_.addCell(tc001_);
		tr000_.addCell(tc2);
		tr000_.addCell(tc2);

		TableCell tc002_ = new TableCell(""
				+ com.jl.common.MathHelper.round(sum, 2));
		tc002_.setColSpan(2);
		tr000_.addCell(tc002_);
		tr000_.addCell(tc2);

		tr000_.setType(MY_DATA_STYLE1);
		t.setRow(13, tr000_);

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// 输出文件
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(("首饰销售单" + code).getBytes("GBK"), "ISO8859-1")
				+ ".xls");
		response.setContentType("application/vnd.ms-excel");
		OutputStream os = response.getOutputStream();
		try {

			// 执行EXCEL格式报表的输出
			ExcelCss css = new ExcelCss() {

				public void init(HSSFWorkbook workbook) {
					// 大、粗字体
					HSSFFont fontBig = workbook.createFont();
					fontBig.setFontHeightInPoints((short) 15);
					fontBig.setFontName("宋体");
					fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

					HSSFFont fontNormal = workbook.createFont();
					fontNormal.setFontHeightInPoints((short) 13);
					fontNormal.setFontName("宋体");
					// *****************end定义字体*****************

					// ***************设置EXCEL报表的样式表******************
					HSSFCellStyle style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(false);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					this.setStyle(Report.DATA_TYPE, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setFont(fontBig);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(false);
					this.setStyle(MY_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(false);
					this.setStyle(MY_RIGHT_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(false);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					this.setStyle(MY_DATA_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(false);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					this.setStyle(MY_DATA_STYLE2, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 0);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(false);
					this.setStyle(MY_HEADER_STYLE1, style);

				}
			};
			new ExcelPrinter().print(report, css, os, true);
		} finally {
			if (os != null)
				os.close();
		}
	}

	public void test() {

		StringBuffer sb = new StringBuffer();

		sb.append(" select");
		sb.append(" zcr.fxsno fxsno,zcr.fxsname fxsname, ");
		sb.append(" ifnull(sum(zcr.zcje),0) zcje, ");
		sb.append(" ifnull(sum(zcr.zrje),0) zrje, ");

		sb.append(" (ifnull(sum(zcr.zcje),0)-ifnull(sum(zcr.zrje),0)) yue, ");
		sb.append(" ifnull(sum(zcr.zcbnlj),0) zcbnlj, ");
		sb.append(" ifnull(sum(zcr.bnzrjelj),0) bnzrjelj ");

		sb.append(" from(  ");
		sb.append(" SELECT  ");
		sb.append(" ifnull(t.column12,'') fxsno, ");
		sb.append(" ifnull(fxs.column3,'')  fxsname, ");
		sb.append(" ifnull(sum(t1.column5),0) zcje, ");
		sb.append(" 0 zrje, ");
		sb.append(" 0 zcbnlj, ");
		sb.append(" 0 bnzrjelj ");
		sb.append(" FROM dyform.DY_661338441749389 t   ");
		sb
				.append(" LEFT JOIN dyform.DY_661338441749388 t1 ON t.LSH = t1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column12 = fxs.column4 ");

		sb
				.append(" WHERE t.STATUSINFO='01' and( t1.STATUSINFO='01' or t1.STATUSINFO = '03') ");
		sb.append(" group by fxsno ");

		sb.append(" union all ");

		sb.append(" SELECT  ");
		sb.append(" ifnull(tt.column8,'') fxsno, ");
		sb.append(" ifnull(tfxs.column3,'')  fxsname, ");
		sb.append(" 0 zcje, ");
		sb.append(" ifnull(sum(tt1.column11),0) zrje, ");
		sb.append(" 0 zcbnlj, ");
		sb.append(" 0 bnzrjelj ");
		sb.append(" FROM dyform.DY_371337952339241 tt   ");
		sb
				.append(" LEFT JOIN dyform.DY_371337952339238 tt1 ON tt.LSH = tt1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 tfxs ON tt.column8 = tfxs.column4 ");

		sb.append(" WHERE tt.STATUSINFO='01' and tt1.STATUSINFO='01' ");
		sb.append(" group by fxsno ");

		sb.append(" union all ");

		sb.append(" SELECT  ");
		sb.append(" ifnull(t.column12,'') fxsno, ");
		sb.append(" ifnull(fxs.column3,'')  fxsname, ");
		sb.append(" 0 zcje, ");
		sb.append(" 0 zrje, ");
		sb.append(" ifnull(sum(t1.column5),0) bnlj, ");
		sb.append(" 0 bnzrjelj ");
		sb.append(" FROM dyform.DY_661338441749389 t   ");
		sb
				.append(" LEFT JOIN dyform.DY_661338441749388 t1 ON t.LSH = t1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column12 = fxs.column4 ");

		sb
				.append(" WHERE t.STATUSINFO='01' and( t1.STATUSINFO='01' or t1.STATUSINFO = '03') ");
		sb.append(" and year(t.column5)= year(now()) ");
		sb.append(" group by fxsno ");

		sb.append(" union all ");

		sb.append(" SELECT  ");
		sb.append(" ifnull(tt.column8,'') fxsno, ");
		sb.append(" ifnull(tfxs.column3,'')  fxsname, ");
		sb.append(" 0 zcje, ");
		sb.append(" 0 zrje, ");
		sb.append(" 0 zcbnlj, ");
		sb.append(" ifnull(sum(tt1.column11),0) bnzrjelj ");
		sb.append(" FROM dyform.DY_371337952339241 tt   ");
		sb
				.append(" LEFT JOIN dyform.DY_371337952339238 tt1 ON tt.LSH = tt1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 tfxs ON tt.column8 = tfxs.column4 ");

		sb.append(" WHERE tt.STATUSINFO='01' and tt1.STATUSINFO='01' ");
		sb.append(" and year(tt.column4) = year(now()) ");
		sb.append(" group by fxsno ");

		sb.append(" ) zcr ");
		sb.append(" group by zcr.fxsno ");

		/**
		 * fxsno 分销商编码 fxsname 分销商名称 zcje 支出金额 zrje 支入金额 yue 余额 zcbnlj 本年支出金额累计
		 * bnzrjelj 本年支入金额累计
		 */
		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			object.get("fxsno");// 分销商编码
			object.get("fxsname");// 分销商名称
			object.get("zcje");// 支出金额
			object.get("zrje");// 支入金额
			object.get("yue");// 余额
			object.get("zcbnlj");// 本年支出金额累计
			object.get("bnzrjelj");// 本年支入金额累计
		}
	}

}
