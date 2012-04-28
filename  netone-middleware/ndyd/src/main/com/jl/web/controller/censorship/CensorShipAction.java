/**
 * 
 */
package com.jl.web.controller.censorship;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.serialize.dao.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.JSONUtil2;
import com.jl.common.TimeUtil;
import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.workflow.worklist.DataObj;
import com.jl.common.workflow.worklist.QueryColumn;
import com.jl.entity.CensorShip;
import com.jl.entity.CensorShipLog;
import com.jl.entity.CensorShipStatus;
import com.jl.entity.User;
import com.jl.service.CensorShipService;
import com.jl.web.controller.AbstractAction;

public class CensorShipAction extends AbstractAction {

	/** 日志 */
	private final Logger log = Logger.getLogger(CensorShipAction.class);

	// 进入页面的主入口
	public ActionForward onMainView2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("onMainView2");
	}

	public void onSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		CensorShip CensorShip = getCensorShipBean(request);
		try {
			User user = this.getOnlineUser(request);
			String jsonx = censorShipService.save(request, CensorShip, user);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "保存信息失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void onPack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");

		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String id = request.getParameter("unid");
		CensorShip CensorShip = getCensorShipBean(request);
		CensorShipLog CensorShipLog = getCensorShipLogBean(request);
		CensorShipStatus CensorShipStatus = getCensorShipStatusBean(request);
		try {
			if (StringUtils.isNotEmpty(id)) {
				User user = getOnlineUser(request);
				String jsonx = censorShipService.pack(request, user,
						CensorShip, CensorShipLog, CensorShipStatus);
				json = JSONObject.fromObject(jsonx);
			} else {

			}
		} catch (Exception e) {
			json.put("tip", "归档失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}

	}

	public void onAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String unid = request.getParameter("unid");
		CensorShipStatus CensorShipStatus = getCensorShipStatusBean(request);
		CensorShipLog CensorShipLog = getCensorShipLogBean(request);
		try {
			User user = getOnlineUser(request);
			String jsonx = censorShipService.audit(request, unid, user,
					CensorShipLog, CensorShipStatus);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "提交失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void onAssign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String id = request.getParameter("unid");
		String notice = request.getParameter("notice");
		CensorShipLog CensorShipLog = getCensorShipLogBean(request);
		CensorShipStatus CensorShipStatus = getCensorShipStatusBean(request);
		json.put("tip", "交办失败!");
		try {
			if (StringUtils.isEmpty(id)) {

			} else {
				id = id.trim();
				User user = getOnlineUser(request);
				String jsonx = censorShipService.assign(request, id, user,
						CensorShipLog, CensorShipStatus, notice);
				json = JSONObject.fromObject(jsonx);
			}
		} catch (Exception e) {
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void onAuditNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String id = request.getParameter("unid");
		String notice = request.getParameter("notice");
		CensorShipLog CensorShipLog = getCensorShipLogBean(request);
		CensorShipStatus CensorShipStatus = getCensorShipStatusBean(request);
		json.put("tip", "提交下一处理失败!");
		try {
			if (StringUtils.isEmpty(id)) {
			} else {
				id = id.trim();
				User user = getOnlineUser(request);

				String jsonx = censorShipService.auditNext(request, id, user,
						CensorShipLog, CensorShipStatus, notice);
				json = JSONObject.fromObject(jsonx);
			}
		} catch (Exception e) {
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void onDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		String id = request.getParameter("unid");
		try {
			String jsonx = censorShipService.delete(request, id);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "作废失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	public void onSelectView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		String status = request.getParameter("status");
		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(status)) {
			conditionMap.put("status", status.trim());
		}
		User user = getOnlineUser(request);
		conditionMap.put("perunid", user.getUserCode());
		if ("adminx".equals(user.getUserCode())) {// 管理员
			// conditionMap.put("perunid", "0");
		}
		String start = request.getParameter("start");// 开始索引
		String limit = request.getParameter("limit");// 页码
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = censorShipService.select(conditionMap);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";

			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				CensorShip b = (CensorShip) iterator.next();
				boolean exclude = false;

				if (StringUtils.isNotEmpty(status)) {
					CensorShipStatus x = new CensorShipStatus();
					x.setUnid(b.getUnid());
					x.setPerunid(user.getUserCode());
					Integer isx = (Integer) getCommonDAO().findForObject(
							"CensorShip.checkIsCreater", x);
					if (isx > 0) {
						b.setIscreater(true);

						b.setHandlerid(user.getUserCode());
						Integer pass = (Integer) getCommonDAO().findForObject(
								"CensorShip.selectAllCensorShipStatusBystatus",
								b);
						if (pass == null)
							pass = 0;
						if (pass > 0) {
							exclude = true;
						}

					} else {
						b.setIscreater(false);
					}
				} else {
					b.setIscreater(false);
				}

				if (exclude == false) {
					String jsonStr = JSONUtil2.fromBean(b,
							"yyyy-MM-dd HH:mm:ss").toString();
					jsonBuffer.append(split);
					jsonBuffer.append(jsonStr);
					split = ",";
				}
			}
			super.writeJsonStr(response, super.buildJsonStr(total, jsonBuffer
					.toString()));
		} catch (Exception e) {
			log.error("出错!", e);
		}

	}

	public ActionForward onEditCensorShip(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		loadCensorShip(request);
		return mapping.findForward("onEditCensorShip");
	}

	public ActionForward onEditX(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		loadCensorShip(request);
		return mapping.findForward("onEditX");
	}

	public ActionForward onEditXmain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return onEditX(mapping, form, request, response);
	}

	private void loadCensorShip(HttpServletRequest request) {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		String id = request.getParameter("unid");
		String page = request.getParameter("page");
		try {
			User user = getOnlineUser(request);
			Map map = censorShipService.load(id, user);

			CensorShip CensorShip = (CensorShip) map.get("CensorShip");
			CensorShipStatus CensorShipStatus = (CensorShipStatus) map
					.get("CensorShipStatus");
			CensorShipLog CensorShipLog = (CensorShipLog) map
					.get("CensorShipLog");

			if (CensorShip == null)
				CensorShip = new CensorShip();
			if (CensorShipStatus == null)
				CensorShipStatus = new CensorShipStatus();
			if (CensorShipLog == null)
				CensorShipLog = new CensorShipLog();

			request.setAttribute("node", user.getDepartmentId());
			request.setAttribute("CensorShip", CensorShip);
			request.setAttribute("CensorShipStatus", CensorShipStatus);
			request.setAttribute("CensorShipLog", CensorShipLog);
		} catch (Exception e) {
			log.error("出错", e);
		}
	}

	// 督办流程跳转页面
	public ActionForward onEditCensorShipX(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		String step = request.getParameter("step");
		String totalstep = request.getParameter("totalstep");
		String pageurl = "";
		if ("new_1".equals(page)) {// 督办新建页面
			pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
					+ page;
			step = "1";
			totalstep = "2";
		} else if ("new_2".equals(page)) {// 提交下一节点
			pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
					+ page;
			step = "2";
			totalstep = "2";
		} else if ("new_3".equals(page)) {// 创建完成
			pageurl = "";
			step = "2";
			totalstep = "2";
		} else if ("audit_1".equals(page)) {// 审核 主页面
			pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
					+ page;
			step = "1";
			totalstep = "3";
		} else if ("audit_2".equals(page)) {// 填写意见
			pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
					+ page;
			step = "1";
			totalstep = "3";
		} else if ("audit_2.5".equals(page)) {// 交办
			pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
					+ page;
			step = "2.5";
			totalstep = "3";
		} else if ("audit_3".equals(page)) {// 结束 办理完毕
			pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
					+ page;
			step = "3";
			totalstep = "3";
		} else {// 默认督办新建页面
			if ("audit".equals(page)) {
				pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
						+ page;
				step = "1";
				totalstep = "2";
			} else {
				pageurl = "/censorship/censorship.do?method=onEditCensorShip_"
						+ page;
				step = "1";
				totalstep = "2";
			}
		}
		request.setAttribute("pageurl", pageurl);
		request.setAttribute("page", page);
		request.setAttribute("totalstep", totalstep);
		request.setAttribute("step", step);
		return mapping.findForward("onEditCensorShipX");
	}

	// 保存意见
	public void onSaveyijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		request.setAttribute("ErrorJson", "Yes");// Json出错提示
		JSONObject json = new JSONObject();
		CensorShipStatus CensorShipStatus = getCensorShipStatusBean(request);
		try {
			User user = getOnlineUser(request);

			String jsonx = censorShipService.saveyijian(request, user,
					CensorShipStatus);
			json = JSONObject.fromObject(jsonx);
		} catch (Exception e) {
			json.put("tip", "提交失败!");
			json.put("error", "yes");
			log.error("出错", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// 加载日志
	public ActionForward onLoadLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		String unid = request.getParameter("unid");
		Map map = censorShipService.loadlog(unid);
		List packlog = (List) map.get("packlog");
		List packlog1 = (List) map.get("packlog1");
		List packlog2 = (List) map.get("packlog2");
		List packlog3 = (List) map.get("packlog3");
		if (packlog == null)
			packlog = new ArrayList();
		if (packlog1 == null)
			packlog1 = new ArrayList();
		if (packlog2 == null)
			packlog2 = new ArrayList();
		if (packlog3 == null)
			packlog3 = new ArrayList();

		request.setAttribute("packlog", packlog);
		request.setAttribute("packlog1", packlog1);
		request.setAttribute("packlog2", packlog2);
		request.setAttribute("packlog3", packlog3);
		return mapping.findForward("onLoadLog");
	}

	/**
	 * 审核页面
	 * 
	 * @throws Exception
	 */
	public ActionForward onAuditView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String unid = request.getParameter("unid");
		User user = getOnlineUser(request);
		Map map = new HashMap();
		map.put("node", user.getDepartmentId());
		Integer isHaveChildren = (Integer) getCommonDAO().findForObject(
				"Department.findIsHaveChildren", map);
		if (isHaveChildren <= 0) {
			request.setAttribute("isend", true);
		} else {
			request.setAttribute("isend", false);
		}

		CensorShipStatus x = new CensorShipStatus();
		x.setUnid(unid);
		x.setPerunid(user.getUserCode());
		Integer isx = (Integer) getCommonDAO().findForObject(
				"CensorShip.checkIsCreater", x);
		if (isx > 0) {
			request.setAttribute("ispack", true);
		} else {
			request.setAttribute("ispack", false);
		}

		return mapping.findForward("onAuditView");
	}

	public ActionForward onShowView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String chooseFlag = request.getParameter("chooseresult");
		User user = getOnlineUser(request);
		request.setAttribute("node", user.getDepartmentId());

		if (chooseFlag.equals("0")) {
			request.setAttribute("helpTip", "帮助提示：请选择下一个流程节点及办理该节点人员。");
			request.setAttribute("processTitle", "人员选择");
		} else if (chooseFlag.equals("1")) {
			request.setAttribute("helpTip", "帮助提示：请选择交办的人员。");
			request.setAttribute("processTitle", "人员选择");
			return mapping.findForward("onShowView");
		} else if (chooseFlag.equals("2")) {
			request.setAttribute("helpTip", "帮助提示：点击完成，办理完毕。");
			request.setAttribute("processTitle", "办理完毕");
			return mapping.findForward("onShowView2A3");
		} else if (chooseFlag.equals("3")) {
			request.setAttribute("helpTip", "帮助提示：点击完成，归档。");
			request.setAttribute("processTitle", "归档");
			return mapping.findForward("onShowView2A3");
		}
		return mapping.findForward("onShowView");
	}

	// 加载意见
	public void onLoadYijian(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String unid = request.getParameter("unid");
		CensorShipStatus s = getCensorShipStatusBean(request);
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(unid)) {
				unid = unid.trim();
			}
			User user = getOnlineUser(request);
			s.setPerunid(user.getUserCode());
			String yijian = (String) getCommonDAO().findForObject(
					"CensorShip.loadYijian", s);
			if (yijian == null)
				yijian = "";
			json.put("yijian", yijian);
		} catch (Exception e) {
			json.put("tip", "失败!");
			json.put("error", "yes");
			e.printStackTrace();
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	private CensorShip getCensorShipBean(HttpServletRequest request) {
		CensorShip CensorShip = new CensorShip();
		String unid = request.getParameter("unid");
		String subject = request.getParameter("subject");
		String handler = request.getParameter("handler");
		String chargedept = request.getParameter("chargedept");
		String duetime = request.getParameter("duetime");
		String donetime = request.getParameter("donetime");
		String transdept = request.getParameter("transdept");
		String memo = request.getParameter("memo");
		String newtime = request.getParameter("newtime");
		String frome = request.getParameter("frome");

		if (StringUtils.isNotEmpty(unid)) {
			CensorShip.setUnid(unid.trim());
		}
		if (StringUtils.isNotEmpty(subject)) {
			CensorShip.setSubject(subject.trim());
		}
		if (StringUtils.isNotEmpty(handler)) {
			CensorShip.setHandler(handler.trim());
		}
		if (StringUtils.isNotEmpty(chargedept)) {
			CensorShip.setChargedept(chargedept.trim());
		}
		if (StringUtils.isNotEmpty(duetime)) {
			CensorShip.setDuetime(TimeUtil.parseDate(duetime.trim()));
		}
		if (StringUtils.isNotEmpty(donetime)) {
			CensorShip.setDonetime(TimeUtil.parseDate(donetime.trim(),
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtils.isNotEmpty(transdept)) {
			CensorShip.setTransdept(transdept.trim());
		}
		if (StringUtils.isNotEmpty(memo)) {
			CensorShip.setMemo(memo.trim());
		}
		if (StringUtils.isNotEmpty(newtime)) {
			CensorShip.setNewtime(TimeUtil.parseDate(newtime.trim(),
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtils.isNotEmpty(frome)) {
			CensorShip.setFrome(frome.trim());
		}

		return CensorShip;
	}

	private CensorShipStatus getCensorShipStatusBean(HttpServletRequest request) {
		CensorShipStatus CensorShipStatus = new CensorShipStatus();
		String unid = request.getParameter("unid");
		String perunid = request.getParameter("perunid");
		String parentunid = request.getParameter("parentunid");
		String yijian = request.getParameter("yijian");
		String state = request.getParameter("state");
		String isdelete = request.getParameter("isdelete");
		String loglinkunid = request.getParameter("loglinkunid");

		if (StringUtils.isNotEmpty(unid)) {
			CensorShipStatus.setUnid(unid.trim());
		}
		if (StringUtils.isNotEmpty(perunid)) {
			CensorShipStatus.setPerunid(perunid.trim());
		}
		if (StringUtils.isNotEmpty(parentunid)) {
			CensorShipStatus.setParentunid(parentunid.trim());
		}
		if (StringUtils.isNotEmpty(yijian)) {
			CensorShipStatus.setYijian(yijian.trim());
		}
		if (StringUtils.isNotEmpty(state)) {
			CensorShipStatus.setState(Integer.parseInt(state.trim()));
		}
		if (StringUtils.isNotEmpty(isdelete)) {
			CensorShipStatus.setIsdelete(Integer.parseInt(isdelete.trim()));
		}
		if (StringUtils.isNotEmpty(loglinkunid)) {
			CensorShipStatus.setLoglinkunid(loglinkunid.trim());
		}
		return CensorShipStatus;
	}

	private CensorShipLog getCensorShipLogBean(HttpServletRequest request) {
		CensorShipLog CensorShipLog = (CensorShipLog) new CensorShipLog();

		String punid = request.getParameter("punid");
		String unid = request.getParameter("log_unid");
		String sname = request.getParameter("sname");
		String actionname = request.getParameter("actionname");
		String tname = request.getParameter("tname");
		String addtime = request.getParameter("addtime");

		if (StringUtils.isNotEmpty(punid)) {
			CensorShipLog.setPunid(punid.trim());
		}
		if (StringUtils.isNotEmpty(unid)) {
			CensorShipLog.setUnid(unid.trim());
		}
		if (StringUtils.isNotEmpty(sname)) {
			CensorShipLog.setSname(sname.trim());
		}
		if (StringUtils.isNotEmpty(actionname)) {
			CensorShipLog.setActionname(actionname.trim());
		}
		if (StringUtils.isNotEmpty(tname)) {
			CensorShipLog.setTname(tname.trim());
		}
		if (StringUtils.isNotEmpty(addtime)) {
			CensorShipLog.setAddtime(TimeUtil.parseDate(addtime.trim()));
		}
		return CensorShipLog;
	}

	private QueryColumn loadQueryColumnById(int index) {
		return loadQueryColumn().get(index);
	}

	private Map<Integer, QueryColumn> loadQueryColumn() {
		Map<Integer, QueryColumn> queryColumn = new HashMap<Integer, QueryColumn>();
		QueryColumn col = new QueryColumn();
		col.setId("subject");
		col.setIndex(0);
		col.setOrder("desc");
		col.setTime(false);
		col.setValue("");
		col.setName("督办标题");
		queryColumn.put(0, col);

		QueryColumn col1 = new QueryColumn();
		col1.setId("handler");
		col1.setIndex(1);
		col1.setOrder("desc");
		col1.setTime(false);
		col1.setValue("");
		col1.setName("发起人");
		queryColumn.put(1, col1);

		QueryColumn col2 = new QueryColumn();
		col2.setId("duetime");
		col2.setIndex(2);
		col2.setOrder("desc");
		col2.setTime(true);
		col2.setValue("");
		col2.setName("需要完成时间");
		queryColumn.put(2, col2);

		QueryColumn col3 = new QueryColumn();
		col3.setId("NULL");
		col3.setIndex(3);
		col3.setOrder("desc");
		col3.setTime(false);
		col3.setValue("");
		col3.setName("当前流程环节名");
		queryColumn.put(3, col3);
		return queryColumn;
	}

	private List<QueryColumn> listQueryColumns() {
		List<QueryColumn> list = new ArrayList<QueryColumn>();
		Map map = loadQueryColumn();
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			list.add((QueryColumn) map.get(key));
		}
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				QueryColumn col0 = (QueryColumn) o1;
				QueryColumn col1 = (QueryColumn) o2;

				Integer x0 = col0.getIndex();
				Integer x1 = col1.getIndex();
				// 首先比较行，如果行相同，则比较列
				int flag = x0.compareTo(x1);
				return flag;
			}
		});
		return list;
	}

	/** 待办新UI */
	// 新的待办任务 使用后台物理分页
	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aaSorting = "[[" + 2 + ",'" + "desc" + "']]";
		request.setAttribute("aaSorting", aaSorting);

		List list = listQueryColumns();
		request.setAttribute("queryColumn", list);
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");
		return mapping.findForward("onMainView");
	}

	// 查询后台待办任务数据
	public void worklist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String conditions = request.getParameter("conditions");
		Map conditionMap = new HashMap();
		JSONArray jsonArr = JSONArray.fromObject(conditions);
		for (Iterator iterator = jsonArr.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			conditionMap.put(object.getString("name").toString(), object
					.getString("value").toString());
		}
		String sortfield_ = (String) conditionMap.get("iSortCol_0");
		String sort = (String) conditionMap.get("sSortDir_0");
		String status_ = (String) conditionMap.get("status");
		String sEcho = (String) conditionMap.get("sEcho");
		String querycolumnindex = (String) conditionMap.get("querycolumn");
		String condition = (String) conditionMap.get("condition");
		String iDisplayStart = (String) conditionMap.get("iDisplayStart");
		String iDisplayLength = (String) conditionMap.get("iDisplayLength");
		try {
			if (StringUtils.isEmpty(sort)) {
				sort = "desc";
			}
			int sortfield = 0;
			if (StringUtils.isEmpty(sortfield_)) {
				sortfield = 0;
			} else {
				sortfield = Integer.parseInt(sortfield_);
			}
			if (StringUtils.isNotEmpty(condition)) {
				condition = condition.trim();
			}
			int start = 0;
			if (StringUtils.isNotEmpty(iDisplayStart)) {
				start = Integer.parseInt(iDisplayStart);
			}
			int length = 10;
			if (StringUtils.isNotEmpty(iDisplayLength)) {
				length = Integer.parseInt(iDisplayLength);
			}

			User user = getOnlineUser(request);
			Integer index = 0;
			if (StringUtils.isNotEmpty(querycolumnindex)) {
				index = Integer.parseInt(querycolumnindex);
			}
			QueryColumn sortColumn = loadQueryColumnById(sortfield);
			QueryColumn queryColumn = loadQueryColumnById(index);
			queryColumn.setValue(condition);

			String conditions_ = "";
			if ("NULL".equals(sortColumn.getId())) {
				queryColumn.setOrder("");
			} else {
				queryColumn.setOrder(" order by " + sortColumn.getId() + " "
						+ sort);
			}
			if (!"NULL".equals(queryColumn.getId())) {
				if (queryColumn.isTime()) {
					String[] times = queryColumn.getValue().split("_");
					conditions_ = " and " + queryColumn.getId() + " >= '"
							+ times[0] + "' ";
					conditions_ += " and " + queryColumn.getId() + " <= '"
							+ times[1] + "' ";
				} else {
					conditions_ = " and " + queryColumn.getId() + " like '%"
							+ queryColumn.getValue() + "%' ";
				}

			}

			PageInfo pageinfo = worklist(status_, user.getUserCode(), start,
					length, queryColumn, conditions_);
			List<DataObj> list = pageinfo.getResultList();
			int total = pageinfo.getTotalRows();

			String projectname = DyFormBuildHtml.projectname + "/";
			JSONArray arr = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				String[] data = list.get(i).getData();
				String url = projectname + list.get(i).getUrl();
				for (int j = 0; j < data.length; j++) {
					String title = data[j];
					String content = data[j];
					if (content == null)
						content = "";
					if (content != null && content.length() > 10) {
						content = content.substring(0, 10) + "...";
					}
					data[j] = DyFormComp.getHref(content, title, url, "",
							"_blank");
				}
				arr.add(data);
			}
			String jsonstr = buildJsonStr_(sEcho, total, total, arr.toString());
			super.writeJsonStr(response, jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getJsonParam(HttpServletRequest request) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

	private String buildJsonStr_(String sEcho, int iTotalRecords,
			int iTotalDisplayRecords, String json) {
		StringBuilder store = new StringBuilder();
		store.append("{");

		store.append("\"sEcho\"");
		store.append(":");
		store.append(sEcho + ",");

		store.append("\"iTotalRecords\"");
		store.append(":");
		store.append(iTotalRecords + ",");

		store.append("\"iTotalDisplayRecords\"");
		store.append(":");
		store.append(iTotalDisplayRecords + ",");

		store.append("\"aaData\"");
		store.append(":");
		store.append(json);
		store.append("}");
		return store.toString();
	}

	private PageInfo worklist(String status, String userCode, int start,
			int limit, QueryColumn queryColumn, String condition) {
		CensorShipService censorShipService = (CensorShipService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("censorShipService");
		Map conditionMap = new HashMap();
		if (StringUtils.isNotEmpty(status)) {
			conditionMap.put("status", status.trim());
		}
		conditionMap.put("perunid", userCode);
		if ("adminx".equals(userCode)) {// 管理员
			// conditionMap.put("perunid", "0");
		}

		conditionMap.put("ordersql", queryColumn.getOrder());
		conditionMap.put("condition", condition);
		PageInfo obj = new PageInfo();
		conditionMap.put("startIndex", start);
		conditionMap.put("pageSize", limit);
		try {
			obj = censorShipService.select(conditionMap);
			int total = obj.getTotalRows();
			List result = obj.getResultList();
			StringBuffer jsonBuffer = new StringBuffer();
			String split = "";

			List<DataObj> allList = new ArrayList<DataObj>();

			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				CensorShip b = (CensorShip) iterator.next();
				boolean exclude = false;

				if (StringUtils.isNotEmpty(status)) {
					CensorShipStatus x = new CensorShipStatus();
					x.setUnid(b.getUnid());
					x.setPerunid(userCode);
					Integer isx = (Integer) getCommonDAO().findForObject(
							"CensorShip.checkIsCreater", x);
					if (isx > 0) {
						b.setIscreater(true);

						b.setHandlerid(userCode);
						Integer pass = (Integer) getCommonDAO().findForObject(
								"CensorShip.selectAllCensorShipStatusBystatus",
								b);
						if (pass == null)
							pass = 0;
						if (pass > 0) {
							exclude = true;
						}

					} else {
						b.setIscreater(false);
					}
				} else {
					b.setIscreater(false);
				}

				if (b.isIscreater() == true) {
					b.setNote(returnUrl(b.getUnid(), "0", "false"));
				} else {
					b.setNote(returnUrl(b.getUnid(), "1", "false"));
				}
				if ("".equals(status) || "1".equals(status)
						|| "2".equals(status)) {
					b.setNote(returnUrl(b.getUnid(), "-1", "true"));
				}

				if (exclude == false) {
					String[] arr1 = new String[4];
					arr1[0] = b.getSubject();
					arr1[1] = b.getHandler();
					arr1[2] = TimeUtil.formatDate(b.getDuetime(),
							"yyyy-MM-dd HH:mm:ss");
					arr1[3] = "督办处理";

					String[] arr2 = new String[4];
					arr2[0] = "subject";
					arr2[1] = "handler";
					arr2[2] = "duetime";
					arr2[3] = "operate";

					DataObj DataObj = new DataObj();
					DataObj.setData(arr1);
					DataObj.setId(arr2);
					DataObj.setUrl(b.getNote());
					allList.add(DataObj);
				} else {
					total--;
				}
			}
			obj.setResultList(allList);
			obj.setTotalRows(total);
		} catch (Exception e) {
			log.error("出错!", e);
		}
		return obj;
	}

	private static String returnUrl(String unid, String t, String readonly) {
		return "/censorship/censorship.do?method=onEditXmain" + "&unid=" + unid
				+ "&t=" + t + "&readonly=" + readonly;
	}

}
