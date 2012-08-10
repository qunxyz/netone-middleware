package com.jl.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oe.frame.web.WebCache;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jl.common.JSONUtil2;
import com.jl.common.SpringBeanUtil;
import com.jl.common.TimeUtil;
import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.common.dyform.DyFormComp;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.common.security3a.Client3A;
import com.jl.common.security3a.SecurityEntry;
import com.jl.common.workflow.DbTools;
import com.jl.common.workflow.DbTools2;
import com.jl.common.workflow.TWfWorklistExt;
import com.jl.common.workflow.WfEntry;
import com.jl.common.workflow.worklist.DataObj;
import com.jl.common.workflow.worklist.QueryColumn;
import com.jl.common.workflow.worklist.WlEntry;
import com.jl.dao.CommonDAO;
import com.jl.entity.User;
import com.jl.entity.leaderViewPojo;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * WorkList 待办任务
 */
public class WorkListAction extends AbstractAction {

	private static ResourceBundle config = ResourceBundle.getBundle("config",
			Locale.CHINESE);
	public CommonDAO commonDAOMirror;

	private static int reloadworklist = 0;

	static {
		try {

			// worklist自动刷新周期
			reloadworklist = Integer.parseInt(config
					.getString("reloadworklist")) * 60 * 1000;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 新的待办任务 使用后台物理分页
	public ActionForward onMainView2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String appname = request.getParameter("appname");
		String sortfield = request.getParameter("sortfield");
		String sort = request.getParameter("sort");
		
		List list = WlEntry.iv().listQueryColumn(appname);
		if (StringUtils.isEmpty(sortfield)) {
			sortfield = ((QueryColumn) list.get(list.size() - 1)).getId();
		}
		if (StringUtils.isEmpty(sort)) {
			sort = "desc";
		}
		Integer sortindex = WlEntry.iv().fetchQueryColumnIndex(appname,
				sortfield);
		sortindex = sortindex == null ? 0 : sortindex;
		String aaSorting = "[[" + sortindex + ",'" + sort + "']]";

		// 设置各列宽度
		if (list.size() > 0) {
			JSONArray arr = new JSONArray();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				QueryColumn object = (QueryColumn) iterator.next();
				JSONObject json = new JSONObject();
				json.put("sWidth", object.getWidth());
				arr.add(json);
			}
			request.setAttribute("aoColumns", arr.toString());
		}

		request.setAttribute("aaSorting", aaSorting);
		request.setAttribute("queryColumn", list);
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");
		return mapping.findForward("onMainView2");
	}

	public ActionForward moreWorklist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("moreWorklist");
	}

	// 前台more链接中的所有工单展示
	public void moreWorklistAll(ActionMapping mapping, ActionForm form,
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
		String sEcho = (String) conditionMap.get("sEcho");
		String iDisplayStart = (String) conditionMap.get("iDisplayStart");// start
		String iDisplayLength = (String) conditionMap.get("iDisplayLength");// limit
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
		} catch (Exception e) {
			e.printStackTrace();
		}

		User user = getOnlineUser(request);// 当前登录者
		String str = " AND u.usercode='" + user.getUserCode() + "'";
		if ("adminx".equals(user.getUserCode())) {
			str = " ";
		}
		String sql = null;
		String count_sql = null;
		String listtype = request.getParameter("listtype");
		if(StringUtils.isEmpty(listtype)){
			listtype="01";
		}
		// 首页more链接中处理
		if ("01".equals(listtype)) {// 待办
			sql = "SELECT"
					+ " w3.lsh lsh,po.name naturalname,w3.appname naturalname2,w2.workcode workcode,w3.d0 formtitle,w2.actname actname,w1.starttime starttime,w2.commitername commitername,w2.commitercode commiter"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS ='01'"
					+ " AND w2.statusnow ='01' AND w2.types IN('01')" + str
					+ " ORDER BY w1.starttime DESC limit " + iDisplayStart
					+ "," + iDisplayLength;
			count_sql = "SELECT"
					+ " count(*) total"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS='01'"
					+ " AND w2.statusnow ='01' AND w2.types IN('01')" + str;

		} else if ("02".equals(listtype)) {// 办理未归档
			sql = "SELECT"
					+ " w3.lsh lsh,po.name naturalname,w3.appname naturalname2,w2.workcode workcode,w3.d0 formtitle,w2.actname actname,w1.starttime starttime,w2.commitername commitername,w2.commitercode commiter"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_runtime wx ON w1.runtimeid = wx.runtimeid"
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS IN('01','02')"
					+ " AND w2.statusnow='02' AND w2.types IN('01') AND wx.statusnow='01'" + str
					+ " ORDER BY w1.starttime DESC limit " + iDisplayStart
					+ "," + iDisplayLength;
			count_sql = "SELECT"
					+ " count(*) total"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_runtime wx ON w1.runtimeid = wx.runtimeid"
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS IN('01','02')"
					+ " AND w2.statusnow ='02' AND w2.types IN('01')  AND wx.statusnow='01'" + str;
		} else if ("03".equals(listtype)) {// 办理已归档
			sql = "SELECT"
					+ " w3.lsh lsh,po.name naturalname,w3.appname naturalname2,w2.workcode workcode,w3.d0 formtitle,w2.actname actname,w1.starttime starttime,w2.commitername commitername,w2.commitercode commiter"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS='02'"
					+ " AND w2.statusnow='02' AND w2.types IN('01')" + str
					+ " ORDER BY w1.starttime DESC limit " + iDisplayStart
					+ "," + iDisplayLength;
			count_sql = "SELECT"
					+ " count(*) total"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS='02'"
					+ " AND w2.statusnow='02' AND w2.types IN('01')" + str;
		} else if ("00".equals(listtype)) {// 所有
			sql = "SELECT"
					+ " w3.lsh lsh,po.name naturalname,w3.appname naturalname2,w2.workcode workcode,w3.d0 formtitle,w2.actname actname,w1.starttime starttime,w2.commitername commitername,w2.commitercode commiter"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS IN('01','02')"
					+ " AND w2.statusnow IN('01','02') AND w2.types IN('01')"
					+ str + " ORDER BY w1.starttime DESC limit "
					+ iDisplayStart + "," + iDisplayLength;
			count_sql = "SELECT"
					+ " count(*) total"
					+ " FROM netone.t_wf_worklist w1 "
					+ " LEFT JOIN netone.t_wf_participant w2 ON w1.workcode = w2.WORKCODE"
					+ " LEFT JOIN netone.t_wf_relevantvar_tmp w3 ON w3.runtimeid = w1.runtimeid"
					+ " LEFT JOIN iss.t_user u ON u.usercode=w2.usercode"
					+ " LEFT JOIN iss.t_department w4 ON u.departmentid=w4.departmentid"
					+ " LEFT JOIN netone.ums_protectedobject po ON po.NATURALNAME=w1.PROCESSID"
					+ " WHERE po.naturalname LIKE 'BUSSWF.BUSSWF.NDYD.%' AND  w1.EXECUTESTATUS IN('01','02')"
					+ " AND w2.statusnow IN('01','02') AND w2.types IN('01')"
					+ str;
		}
		// System.out.println("SQL=" + sql + "\n" + "user=" +
		// user.getUserCode());
		List list = wfview.coreSqlview(sql);
		List list_count = wfview.coreSqlview(count_sql);
		int total = 0;
		for (Iterator iterator = list_count.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			String count = map.get("total").toString();
			if (count != null && !"".equals(count)) {
				total = Integer.parseInt(count);
			}
		}

		String projectname = DyFormBuildHtml.projectname + "/";
		JSONArray arr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Map data = (Map) list.get(i);
			String $naturalname = (String) data.get("naturalname");
			String $formtitle = (String) data.get("formtitle");
			String $starttime = (String) data.get("starttime");
			String $actname = (String) data.get("actname");
			String $commiter = (String) data.get("commiter");
			String $lsh = (String) data.get("lsh");
			String $workcode = (String) data.get("workcode");
			String $naturalname2 = (String) data.get("naturalname2");

			String query = "&query=look&";
			if ("01".equals(listtype)) {
				query = "&";
			}
			String url = projectname
					+ "frame.do?method=onEditViewMain&naturalname="
					+ $naturalname2 + "&lsh=" + $lsh + "&workcode=" + $workcode
					+ "&operatemode=01" + query + "cuibang=true&commiter="
					+ $commiter;// here
			$naturalname = DyFormComp.getHref($naturalname, $naturalname, url,
					"", "_blank");
			$formtitle = DyFormComp.getHref($formtitle, $formtitle, url, "",
					"_blank");
			$starttime = DyFormComp.getHref($starttime, $starttime, url, "",
					"_blank");
			$actname = DyFormComp
					.getHref($actname, $actname, url, "", "_blank");
			$commiter = DyFormComp.getHref($commiter, $commiter, url, "",
					"_blank");

			String[] datarr = { $formtitle,$naturalname, $starttime, $actname,
					$commiter };

			arr.add(datarr);
		}
		String jsonstr = buildJsonStr_(sEcho, total, total, arr.toString());
		// System.out.println("json=" + arr.toString());
		super.writeJsonStr(response, jsonstr);
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
		String appname = (String) conditionMap.get("appname");
		String sortfield_ = (String) conditionMap.get("iSortCol_0");
		String sort = (String) conditionMap.get("sSortDir_0");
		String mode_ = (String) conditionMap.get("mode");
		String listtype = (String) conditionMap.get("listtype");
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
			List listx = WlEntry.iv().listQueryColumn(appname);
			if (StringUtils.isEmpty(sortfield_)) {
				sortfield = listx.size() - 1;
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
			QueryColumn sortColumn = WlEntry.iv().loadQueryColumn(appname,
					sortfield);
			QueryColumn queryColumn = WlEntry.iv().loadQueryColumn(appname,
					index);
			queryColumn.setValue(condition);
			queryColumn
					.setOrder(" order by " + sortColumn.getId() + " " + sort);
			boolean mode = false;
			if (StringUtils.isNotEmpty(mode_)) {
				if ("1".equals(mode_)) {
					mode = true;
				}
			}

			List<DataObj> list = WlEntry.iv().worklist(user.getUserCode(),
					appname, mode, start, length, listtype, queryColumn);
			int total = list.size();

			String projectname = DyFormBuildHtml.projectname + "/";
			JSONArray arr = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				String[] data = list.get(i).getData();
				String[] $id = list.get(i).getId();
				String url = projectname + list.get(i).getUrl();
				for (int j = 0; j < data.length; j++) {
					String title = data[j];
					String content = data[j];
					// System.out.println("data:"+$id[j]+":"+content);
					if (title == null)
						title = "";
					if (content == null)
						content = "";
					// if (content != null && content.length() > 10) {
					// content = content.substring(0, 10) + "...";
					// }
					data[j] = DyFormComp.getHref(content, title, url, "",
							"_blank");
				}
				arr.add(data);
			}
			String jsonstr = buildJsonStr_(sEcho, total, total, arr.toString());
			// System.out.println("json=" + arr.toString());
			super.writeJsonStr(response, jsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 导出数据后台待办任务数据
	public void exportWorklist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String conditions = request.getParameter("conditions");
		String format = request.getParameter("format");
		Map conditionMap = new HashMap();
		JSONArray jsonArr = JSONArray.fromObject(conditions);
		for (Iterator iterator = jsonArr.iterator(); iterator.hasNext();) {
			JSONObject object = (JSONObject) iterator.next();
			conditionMap.put(object.getString("name").toString(), object
					.getString("value").toString());
		}
		String appname = (String) conditionMap.get("appname");
		String sortfield_ = (String) conditionMap.get("iSortCol_0");
		String sort = (String) conditionMap.get("sSortDir_0");
		String mode_ = (String) conditionMap.get("mode");
		String listtype = (String) conditionMap.get("listtype");
		String querycolumnindex = (String) conditionMap.get("querycolumn");
		String condition = (String) conditionMap.get("condition");
		String iDisplayStart = (String) conditionMap.get("iDisplayStart");
		String iDisplayLength = (String) conditionMap.get("iDisplayLength");
		try {
			if (StringUtils.isEmpty(sort)) {
				sort = "desc";
			}
			int sortfield = 0;
			List listx = WlEntry.iv().listQueryColumn(appname);
			if (StringUtils.isEmpty(sortfield_)) {
				sortfield = listx.size() - 1;
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
			QueryColumn sortColumn = WlEntry.iv().loadQueryColumn(appname,
					sortfield);
			QueryColumn queryColumn = WlEntry.iv().loadQueryColumn(appname,
					index);
			queryColumn.setValue(condition);
			queryColumn
					.setOrder(" order by " + sortColumn.getId() + " " + sort);
			boolean mode = false;
			if (StringUtils.isNotEmpty(mode_)) {
				if ("1".equals(mode_)) {
					mode = true;
				}
			}
			List<DataObj> list = WlEntry.iv().worklist(user.getUserCode(),
					appname, mode, start, length, listtype, queryColumn);

			List<TableCell> headerList = new ArrayList<TableCell>();
			for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
				QueryColumn colx = (QueryColumn) iterator.next();
				headerList.add(new TableCell("" + colx.getName()));
			}

			Table t = new Table();
			ReportExt reportExt = new ReportExt();

			for (int i = 0; i < list.size(); i++) {
				String[] data = list.get(i).getData();
				String[] $id = list.get(i).getId();
				TableRow trdata = new TableRow();
				for (int j = 0; j < data.length; j++) {
					String title = data[j];
					String content = data[j];
					// System.out.println("data:"+$id[j]+":"+content);
					if (title == null)
						title = "";
					if (content == null)
						content = "";

					trdata.addCell(new TableCell("" + content));
				}
				t.addRow(trdata);
			}

			Report reportX = reportExt.setSimpleColHeader(t, headerList);
			Long currentTimeMillis = System.currentTimeMillis();
			GroupReport groupReport = new GroupReport();
			response.reset();
			groupReport.format(format, "应用" + currentTimeMillis, reportX,
					response);
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

	public ActionForward onMainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Client3A client = SecurityEntry.iv().onlineUser(request);
		// 获得登陆用户信息

		request.setAttribute("reloadworklist", reloadworklist);
		// 获得该用户的代办任务
		String mode = request.getParameter("mode");
		List list = null;
		if ("1".equals(mode)) {// 已办结但未归档
			request.setAttribute("mode", mode);
			list = WfEntry.iv().worklistDone(client.getClientId());
		} else if ("2".equals(mode)) {// 已办结且归档
			list = WfEntry.iv()
					.worklistDoneAndProcessDone(client.getClientId());
		} else {
			list = WfEntry.iv().worklist(client.getClientId());
		}
		String title = "";
		StringBuffer radioChoose = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TWfWorklistExt object = (TWfWorklistExt) iterator.next();
			List revList = object.getRev();
			if (title.equals("")) {
				int i = 0;
				for (Iterator iterator2 = revList.iterator(); iterator2
						.hasNext();) {
					TWfRelevantvar objectx = (TWfRelevantvar) iterator2.next();
					String titlename = StringUtils.substringBetween(objectx
							.getExtendattribute(), ":", ";");
					title += "<th>" + titlename + "<br></th>";

					if (i == 0)
						radioChoose
								.append("<span name='radioField"
										+ i
										+ "' style='padding-right: 15px; width: '>"
										+ "<input type='radio' name='fieldChoose' value='"
										+ i + "' checked='checked'/>"
										+ titlename + "</span>");
					else
						radioChoose
								.append("<span name='radioField"
										+ i
										+ "' style='padding-right: 15px;'>"
										+ "<input type='radio' name='fieldChoose' value='"
										+ i + "'/>" + titlename + "</span>");
					i++;
				}

				request.setAttribute("titleExt", title);
			}

			StringBuffer valuelist = new StringBuffer();
			for (Iterator iterator2 = revList.iterator(); iterator2.hasNext();) {
				TWfRelevantvar objectx = (TWfRelevantvar) iterator2.next();
				valuelist.append("<TD>" + objectx.getValuenow() + "</TD>");
				// System.out.println("dfdfd" + objectx.getValuenow());
			}
			object.setExtinfo(valuelist.toString());
		}
		request.setAttribute("workLists", list);
		request.setAttribute("radioChoose", radioChoose.toString());
		return mapping.findForward("onMainView");
	}

	public ActionForward onShowCountView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();

		return mapping.findForward("onShowLeftMainView");
	}

	public ActionForward onShowProcessView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		throw new RuntimeException("功能禁用");
		// HttpSession session = request.getSession();
		// JSONObject json = new JSONObject();
		// String processStr = request.getParameter("processstr");// 格式
		// // processid,processname;processid2,processname;
		//
		// List deptList = WfEntry.iv_s().rank("dept");
		// List userList = WfEntry.iv_s().rank("user");
		// List processList = WfEntry.iv_s().rank("process");
		//
		// session.setAttribute("deptList", deptList);
		// session.setAttribute("userList", userList);
		// session.setAttribute("processList", processList);
		//
		// session.setAttribute("processStr", processStr);
		// return mapping.findForward("onShowLeftMainView");
	}

	public ActionForward onShowBaseTable(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		throw new RuntimeException("功能禁用");
		// String processId = request.getParameter("selectProcess");
		// System.out.println(processId);
		// List baseDataList = WfEntry.iv_s().table(processId);
		//
		// List list = WfEntry.iv().useCoreView().coreSqlview(
		// "select count(*) countx from t_wf_runtime where processid='"
		// + processId + "'");
		// Map map = (Map) list.get(0);
		// Long longx = (Long) map.get("countx");
		//
		// List list2 = WfEntry.iv().useCoreView().coreSqlview(
		// "select count(*) countx from t_wf_runtime where processid='"
		// + processId + "' and STATUSNOW='01'");
		// Map map2 = (Map) list2.get(0);
		// Long longx2 = (Long) map2.get("countx");
		// System.out.println("流程总数：" + longx.intValue() + " 当前流程：" + longx2);
		// request.setAttribute("allListCount", longx);
		// request.setAttribute("subListCount", longx2);
		//
		// request.setAttribute("baseDataList", baseDataList);
		//
		// return mapping.findForward("onShowLeftMainView");

	}

//	public ActionForward phpListDetailMain(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		return mapping.findForward("phpListDetailMain");
//	}

	// 领导视图详细列表
	public ActionForward phpListDetailMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map conditionMap = new HashMap();
		// JSONArray jsonArr = JSONArray.fromObject(conditions);
		// for (Iterator iterator = jsonArr.iterator(); iterator.hasNext();) {
		// JSONObject object = (JSONObject) iterator.next();
		// conditionMap.put(object.getString("name").toString(), object
		// .getString("value").toString());
		// }
		// 接收参数
		String type = request.getParameter("type");// 类型
		String did = request.getParameter("did");// 部门id
		String total_ = request.getParameter("total");// 总数
		String naturalname_ = request.getParameter("naturalname");// naturalname
		String name = naturalname_ + did;

		// 得到缓存数据
		List list = new ArrayList();
		Map map_all = new HashMap();
		if (naturalname_.equals("all")) {

			if ("week".equals(type)) {
				map_all = (Map) WebCache.getCache("all" + did);// 获取对应部门信息集合
				list = (List) map_all.get("week_all");
			} else if ("dearling".equals(type)) {
				map_all = (Map) WebCache.getCache("all" + did);// 获取对应部门信息集合
				list = (List) map_all.get("dearling_all");
			} else if ("after28".equals(type)) {
				map_all = (Map) WebCache.getCache("all" + did);// 获取对应部门信息集合
				list = (List) map_all.get("after28_all");
			}

		} else {
			if ("week".equals(type)) {
				map_all = (Map) WebCache.getCache(name);// 获取对应部门信息集合
				list = (List) map_all.get("week");
			} else if ("dearling".equals(type)) {
				map_all = (Map) WebCache.getCache(name);// 获取对应部门信息集合
				list = (List) map_all.get("dearling");
			} else if ("after28".equals(type)) {
				map_all = (Map) WebCache.getCache(name);// 获取对应部门信息集合
				list = (List) map_all.get("after28");
			}
		}
		int total_value = 0;
		if (list != null) {

			//System.out.println("list=" + list.toString());
			User user = getOnlineUser(request);// 获取当前登录者信息
			String clientid = user.getUserId();
			Client3A client3a = SecurityEntry.iv().loadUser(clientid);
			leaderViewPojo lvp = new leaderViewPojo();
			List ltdata = new ArrayList();// 取值
			// 权限判断

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				lvp = (leaderViewPojo) iterator.next();
				// flag状态 如果是0则只显示不做超链接 如果是1显示并做超链接
				lvp.setFlag(0);
				if (SecurityEntry.iv().permission(clientid, "DEPT.DEPT")) {// 大领导
					lvp.setFlag(1);
				} else if (SecurityEntry.iv().permission(clientid,
						"DEPT.DEPT.297236c57ea1410d841db89adbfd3f08")
						&& client3a.getBelongto().equals(did)) {// 部门领导
					lvp.setFlag(1);
				} else if (lvp.getUsercode().equals(user.getUserCode())||lvp.getCommiter().equals(user.getUserCode())) {// 普通用户
					lvp.setFlag(1);
				}
				if (StringUtils.isNotEmpty(lvp.getNaturalname2())) {
					ltdata.add(lvp);
				}
			}
			 request.setAttribute("ltdata", ltdata);
		}// if end
		return mapping.findForward("phpListDetailMain");
	}
}
