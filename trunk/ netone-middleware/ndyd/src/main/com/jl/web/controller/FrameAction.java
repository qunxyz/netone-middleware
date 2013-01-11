package com.jl.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oe.env.client.EnvService;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.rmi.client.RmiEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
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
import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.dyform.DyFormData;
import com.jl.common.resource.Resource;
import com.jl.common.resource.ResourceNode;
import com.jl.common.security3a.Client3A;
import com.jl.common.workflow.TWfActive;
import com.jl.common.workflow.TWfActivePass;
import com.jl.common.workflow.TWfParticipant;
import com.jl.common.workflow.WfEntry;
import com.jl.entity.User;
import com.jl.service.BaseService;
import com.jl.service.FrameService;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class FrameAction extends AbstractAction {

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

		String formcode = null;

		AppObj app = AppEntry.iv().loadApp(naturalname);
		formcode = app.getDyformCode_();

		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtml.buildExtColumns(dyform, "1", true);
		String fields = DyFormBuildHtml.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);
		String queryColumnHtml = DyFormBuildHtml.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);
		String queryConditionHtml = DyFormBuildHtml.buildQueryCondition(dyform
				.getQueryColumn_());
		request.setAttribute("queryConditionHtml", queryConditionHtml);

		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frame/frameMain-" + naturalname + ".jsp");
		String forward = "/frame/frameMain.jsp";
		if (file.exists()) {
			forward = "/frame/frameMain-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
		// return mapping.findForward("onMainView");
	}

	// 列表页面action
	public ActionForward listmain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalname = request.getParameter("naturalname");
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frame/listmain-" + naturalname + ".jsp");
		String forward = "/frame/listmain.jsp";
		if (file.exists()) {
			forward = "/frame/listmain-" + naturalname + ".jsp";
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
		File file = new File(path + "/frame/list-" + naturalname + ".jcode");
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
		JSONObject paramJson = null;
		if (StringUtils.isNotEmpty(conditions)) {
			paramJson = JSONObject.fromObject(conditions);
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

			obj = (PageInfo) ins.queryForPage(dydata, from_, limit_, "");
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				DyFormData group = (DyFormData) iterator.next();

				String lsh = group.getLsh();

				String clientId = "";
				if (group.getParticipant() != null
						&& StringUtils.isNotEmpty(group.getParticipant())) {
					clientId = group.getParticipant();
				}

				String runtimeid = WfEntry.iv().getSession(lsh);
				if (runtimeid != null && StringUtils.isNotEmpty(runtimeid)) {

					TWfRuntime runtime = WfEntry.iv().useCoreView()
							.loadRuntime(runtimeid);
					// 流程结束 runtime.isDone()已办结
					// 是否已经提交审批 runtime.isRunning()已处理

					if (runtime.isReady() == true) {
						if (user.getUserCode().equals(clientId)) {
							group.setStatus("<font color='red'>未提交</font>");
							group.setRun(false);// 编辑
						} else {
							group.setStatus("<font color='red'>未提交</font>");
							group.setRun(true);// 查看
						}
					}
					if (runtime.isDone() == true) {
						group.setStatus("<font color='#1285C9'>已办结</font>");
						group.setRun(true);// 查看
					}
					if (runtime.isRunning() == true) {
						group.setStatus("<font color='green'>处理中</font>");
						group.setRun(true);// 查看
					}
				} else {
					if (user.getUserCode().equals(clientId)) {
						group.setStatus("<font color='red'>未提交</font>");
						group.setRun(false);// 编辑
					} else {
						group.setStatus("<font color='red'>未提交</font>");
						group.setRun(true);// 查看
					}
				}
				group.setRuntimeid(runtimeid);

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

	public Map<String, Boolean> permission(HttpServletRequest request,
			boolean isedit, boolean ispermission) throws Exception {
		String lsh = request.getParameter("lsh");
		String query = request.getParameter("query");
		String workcode = request.getParameter("workcode");
		String naturalname = request.getParameter("naturalname");
		if (StringUtils.isNotEmpty(workcode)) {
			isedit = false;
			TWfWorklist wlx = WfEntry.iv().loadWorklist(workcode);
			TWfActive active = new TWfActive();
			try {
				active = WfEntry.iv().loadRuntimeActive(wlx.getProcessid(),
						wlx.getActivityid(), naturalname, "",
						wlx.getRuntimeid());
			} catch (Exception e) {
				e.printStackTrace();
				active.setFobitzb(false);
			}

			boolean isFormEdit = active.isFormEdit();
			boolean isFirstAct = WfEntry.iv().checkFirstAct(workcode);
			boolean checkFinalAct = WfEntry.iv().checkFinalAct(workcode);
			if (isFormEdit) {
				ispermission = true;
				isedit = true;
			} else {
				isedit = false;
				ispermission = false;
			}
			if (isFirstAct || checkFinalAct) {// 退回创建者 开启修改表单功能
				ispermission = true;
				isedit = true;
				request.setAttribute("isFirstAct", isFirstAct);
			}

			boolean formlock = WfEntry.iv().bussFormLockByWorkcode(workcode);
			// formlock==true 流程发起 不能作废
			request.setAttribute("formlock", formlock);
			request.setAttribute("isEditAct", isedit);
		} else {
			ispermission = true;
			isedit = true;
		}

		if (StringUtils.isEmpty(lsh)) {// 新建
			User user = getOnlineUser(request);
			// boolean permission = SecurityEntry.iv().permission(1
			// user.getUserCode(), naturalname);
			if (user != null) {
				boolean permission = AppEntry.iv().canCreate(naturalname,
						user.getUserName() + "[" + user.getUserCode() + "]");

				request.setAttribute("permission", permission);
			}
		} else {
			request.setAttribute("permission", true);
		}
		if ("look".equals(query)) {// 查看
			isedit = false;
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
		request.setAttribute("htmlendinfo", dyform.getHtmlendinfo_());
		String workcode = request.getParameter("workcode");
		String query = request.getParameter("query");
		String lsh = request.getParameter("lsh");
		String operatemode = request.getParameter("operatemode");
		loadNavInfo(request);
		boolean isedit = true;
		boolean ispermission = true;// 是否启用鉴权
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");

		load(mapping, form, request, response, isedit, ispermission);
		// return mapping.findForward("onEditView");
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frame/editframe-" + naturalname + ".jsp");
		String forward = "/frame/editframe.jsp";
		if (file.exists()) {
			forward = "/frame/editframe-" + naturalname + ".jsp";
		}
		String isadd = request.getParameter("isadd");

		String urltemplate = app.getDescription();
		printHelpText(urltemplate, request);

		if (StringUtils.isNotEmpty(urltemplate) && !"03".equals(operatemode)) {
			// 针对新增的模板
			if (!"1".equals(isadd)) {
				// forward = forward = "/frame/frameExtPage.jsp";
				// request.setAttribute("urltext", urltemplate);
			}

		}
		request.setAttribute("htmltitleinfo", app.getFormtitle());
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 表单预览
	public ActionForward onPreviewMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String formcode = request.getParameter("formcode");
		String naturalname = request.getParameter("naturalname");
		String $isedit = request.getParameter("isedit");
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		AppObj app = AppEntry.iv().loadApp(naturalname);
		request.setAttribute("htmltitleinfo", app.getFormtitle());
		boolean isedit = false;
		if ("1".equals($isedit))
			isedit = true;

		Map issubmap = new HashMap();
		issubmap.put(-1, isedit);
		loadDyForm(mapping, form, request, response, isedit, issubmap);
		// return mapping.findForward("onPreviewMain");
		String path = request.getSession().getServletContext().getRealPath("/");// 应用服务器目录
		File file = new File(path + "/frame/editframe-" + naturalname + ".jsp");
		String forward = "/frame/previewframe.jsp";
		if (file.exists()) {
			forward = "/frame/previewframe-" + naturalname + ".jsp";
		}
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
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
		String activityName = "";
		try {
			activityName = WfEntry.iv().getActivityName(naturalname, workcode);
		} catch (Exception e) {
			activityName = "未知节点";
		}
		if (StringUtils.isNotEmpty(workcode)) {
			try {
				TWfActive twfActive = WfEntry.iv().loadActive(naturalname,
						workcode);
				request.setAttribute("isFobitzb", twfActive.isFobitzb());
			} catch (Exception e) {
				request.setAttribute("isFobitzb", true);
			}
		} else {
			request.setAttribute("isFobitzb", true);
		}

		request.setAttribute("activityName", activityName);
	}

	// 加载
	private void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			boolean isedit, boolean permission) throws Exception {
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
			TWfActive act = WfEntry.iv().listCurrentActive(naturalname,
					workcode, user.getUserCode());
			if (permission) {
				DyEntry.iv().permission(dyform, user.getUserCode(), act);// 表单鉴权
			}
			Map subformmode = act.getSubformmode();
			// if (StringUtils.isNotEmpty(lsh) &&
			// StringUtils.isNotEmpty(formcode)) {
			formhtml = ins.load(workcode, naturalname, dyform, lsh, isedit,
					subformmode, user.getNLevelName() + "/"
							+ user.getUserName() + "," + user.getNLevelName(),
					request.getParameter("url"));
			// }
			request.setAttribute("form", formhtml.toString());

			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("AvailWidth", DyFormBuildHtml.AvailWidth
					- DyFormBuildHtml.AvailWidthCorrect);
			if (StringUtils.isNotEmpty(workcode)) {
				TWfWorklist twf = WfEntry.iv().loadWorklist(workcode);
				request.setAttribute("runtimeid", twf.getRuntimeid());

			} else {
				request.setAttribute("processid", processid);
			}
			if (permission) {
				request.setAttribute("ValidateScript", DyFormBuildHtml
						.buildValidateScript(dyform));
				}else{
					request.setAttribute("ValidateScript"," return true;");	
				}

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
					request.getParameter("url"));
			// }
			request.setAttribute("form", formhtml.toString());

			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("AvailWidth", DyFormBuildHtml.AvailWidth
					- DyFormBuildHtml.AvailWidthCorrect);
			request.setAttribute("ValidateScript", DyFormBuildHtml
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
		load(mapping, form, request, response, isedit, false);
		return mapping.findForward("onEditView");
	}

	public ActionForward onQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		load(mapping, form, request, response, false, false);
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
		request.setAttribute("htmltitleinfo", app.getFormtitle());
		printHelpText(app.getDescription(), request);
		return mapping.findForward("onAuditView");
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
				&& (filteractiveids
						.contains(FrameService.trackActionSpecialTypeEND) || filteractiveids
						.contains(FrameService.trackActionSpecialType4))) {// 归档
			request.setAttribute("helpTip", "帮助提示: 流程结束,请点击完成,结束流程。");
			request.setAttribute("processEndTip", "");
			request.setAttribute("processTitle", "归档");
			// result.addAll(listTrackActionEnd("归档"));
			
			if ( FrameService.trackActionSpecialType4.equalsIgnoreCase(filteractiveids)){
				result.addAll(listTrackAction4("0"));//归档
			}
			if ( FrameService.trackActionSpecialType2.equalsIgnoreCase(filteractiveids)){
				//result.addAll(listTrackAction2_2("0"));//抄阅
				//request.setAttribute("processTitle", "抄阅并归档");
			}
			if (filteractiveids==null) {
				result.addAll(listTrackAction4("0"));//归档
				//result.addAll(listTrackAction2_2("0"));//抄阅
			}
			request.setAttribute("processList", result);
			request.setAttribute("isend", "true");
			return "onShowEndView";
		}

		AppObj app = AppEntry.iv().loadApp(naturalname);
		String processid = app.getWorkflowCode_();
		printHelpText(app.getDescription(), request);
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
		request.setAttribute("htmltitleinfo", app.getFormtitle());
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

				boolean isEnd = WfEntry.iv().loadProcess(wlx.getProcessid())
						.getActivity(wlx.getActivityid()).isExitActivity();
				// 是否是创建者
				if (AppHandleIfc._PARTICIPANT_MODE_CREATER.equals(active
						.getParticipantmode())
						&& StringUtils.isEmpty(filteractiveids) && isEnd) {
					// result.addAll(listTrackActionEnd("归档"));
					if ( FrameService.trackActionSpecialType4.equalsIgnoreCase(filteractiveids)){
						result.addAll(listTrackAction4("0"));//归档
					}
					if ( FrameService.trackActionSpecialType2.equalsIgnoreCase(filteractiveids)){
						//result.addAll(listTrackAction2_2("0"));//抄阅
					}
					if (filteractiveids==null) {
						result.addAll(listTrackAction4("0"));//归档
						//result.addAll(listTrackAction2_2("0"));//抄阅
					}
				}
			} else {
				request.setAttribute("helpTip", "帮助提示: 流程结束,请点击完成,结束流程。");
				request.setAttribute("processEndTip", "");
				request.setAttribute("processTitle", "归档");
				// result.addAll(listTrackActionEnd("归档"));
				if ( FrameService.trackActionSpecialType4.equalsIgnoreCase(filteractiveids)){
					result.addAll(listTrackAction4("0"));//归档
				}
				if ( FrameService.trackActionSpecialType2.equalsIgnoreCase(filteractiveids)){
					result.addAll(listTrackAction2_2("0"));//抄阅
					//request.setAttribute("processTitle", "抄阅并归档");
				}
				if (filteractiveids==null) {
					result.addAll(listTrackAction4("0"));//归档
					//result.addAll(listTrackAction2_2("0"));//抄阅
				}
				
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
	private List listTrackAction2_2(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "抄阅并归档");
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

	/**
	 * 显示特殊节点 归档并抄阅
	 * 
	 * @param node
	 * @return
	 */
	private List listTrackAction4(String node) {
		List list = new ArrayList();
		Map tempMap = new HashMap();
		tempMap.put("name", "归档");
		tempMap.put("type", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("pmode", AppHandleIfc._PARTICIPANT_MODE_DEPT);
		tempMap.put("needsync", false);
		tempMap.put("value", node);
		tempMap.put("singleman", false);
		tempMap.put("autoroute", false);
		//tempMap.put("activeids", FrameService.trackActionSpecialType4);
		tempMap.put("activeids", FrameService.trackActionSpecialTypeEND);
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
								+ StringUtils.substringBefore(object.getUsername(), " ") + "</B>  " + donetime
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
				//时间计算
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 java.util.Date start = df.parse(createtime);
				 long l=0;
				 if(!"&nbsp;".equals(donetime)){
					 java.util.Date end = df.parse(donetime);
					 l=end.getTime()-start.getTime();
				 }
				 long day=l/(24*60*60*1000);
				 long hour=(l/(60*60*1000)-day*24);
				 long min=((l/(60*1000))-day*24*60-hour*60);

				
				if ("04".equals(object.getTypes())) {// 阶段性回复
					donetime = createtime;
				}

				showHtmlView += "<tr>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ createtime
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ StringUtils.substringBefore(object.getCommitername(), " ")
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"40%\"  class=\"label_nd_2\">"
						+ opemodeX
						+ object.getActname()
						+ "(用时" + day + "天" + hour + "小时" + min + "分" + ")" 
						+ "</td>"
						+ "<td nowrap=\"nowrap\" width=\"20%\"  class=\"label_nd_2\">"
						+ StringUtils.substringBefore(object.getUsername(), " ")
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

	// 编辑表单管理页面
	public ActionForward onMainViewExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		String formcode = request.getParameter("formcode");
		String submode = request.getParameter("submode");
		if ("3".equals(submode)) {
			return onEditFrameExt(mapping, form, request, response);
		}

		String workcode = request.getParameter("workcode");
		boolean isedit = true;
		boolean ispermission = true;// 是否启用鉴权
		Map<String, Boolean> pmap = permission(request, isedit, ispermission);
		isedit = pmap.get("isedit");
		ispermission = pmap.get("ispermission");

		request.setAttribute("limit", new DyForm().getEachPageSize_());
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtml.buildExtColumns(dyform, "2", isedit);
		String fields = DyFormBuildHtml.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);
		return mapping.findForward("onMainViewExt");
	}

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
					.getParameter("url"));
			// }
			request.setAttribute("form", formhtml.toString());
			String linkcss = DyFormComp.getStyle(getURL(dyform
					.getStyleinfourl_()));
			request.setAttribute("linkcss", linkcss);
			request.setAttribute("ValidateScript", DyFormBuildHtml
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

	// 合同管理
	public ActionForward onContractMgr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// this.getClientPermissions(request, response);
		request.setAttribute("limit", new DyForm().getEachPageSize_());
		// String naturalname = request.getParameter("naturalname");
		String naturalname = "APPFRAME.APPFRAME.HTTX";
		AppObj app = AppEntry.iv().loadApp(naturalname);
		String formcode = app.getDyformCode_();
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		String columns = DyFormBuildHtml.buildExtColumns(dyform, "1", true);
		String fields = DyFormBuildHtml.buildExtFields(dyform);
		request.setAttribute("columns", columns);
		request.setAttribute("fields", fields);
		String queryColumnHtml = DyFormBuildHtml.buildQueryColumn(dyform
				.getQueryColumn_());
		request.setAttribute("queryColumnsHtml", queryColumnHtml);
		String queryConditionHtml = DyFormBuildHtml.buildQueryCondition(dyform
				.getQueryColumn_());
		request.setAttribute("queryConditionHtml", queryConditionHtml);
		return mapping.findForward("onContractMgr");
	}

	// 加载手风琴树HTML
	private void loadAccordtree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String naturalurl = request.getParameter("naturalurl");
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		List<UmsProtectedobject> list = FrameResource.findByPId(naturalurl);
		for (UmsProtectedobject umsProtectedobject : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", umsProtectedobject.getName());
			map.put("json", FrameResource.buildRootTreeRelation(
					umsProtectedobject.getNaturalname(), true, false));

			jsonList.add(map);
		}

		String html = DyFormComp.getEasyuiAccordionTree(jsonList);
		request.setAttribute("accordhtml", html);
	}

	// 动态报表
	public ActionForward queryDyReportView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "/frame/dyReportView.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 动态报表
	public void queryDyReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FrameService ins = (FrameService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("frameService");
		ins.queryDyReport(request, response);
	}

	// 获取帮助信息
	private void printHelpText(String urltemplate, HttpServletRequest request)
			throws Exception {
		EnvService env = (EnvService) RmiEntry.iv("envinfo");
		if (StringUtils.isNotEmpty(urltemplate)) {
			String urltemplates = "&nbsp;&nbsp;<a target='_blank' href='"
					+ env.fetchEnvValue("WEBSER_FCK")
					+ "/PagelistViewSvl?pagename=simplefcklist&chkid="
					+ urltemplate
					+ "'  ><font size='3' color='red'>工单填写须知</font></a>";
			request.setAttribute("helptext", (urltemplates == null ? ""
					: urltemplates));
		}
	}

}
