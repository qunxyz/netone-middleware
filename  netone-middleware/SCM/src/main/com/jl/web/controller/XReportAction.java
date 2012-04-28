package com.jl.web.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.dyform.DyFormComp;
import com.jl.common.report.XReportFaceApi;
import com.jl.common.report.XReportIfc;
import com.jl.common.report.obj.QueryColumn;
import com.jl.common.report.obj.QueryCondition;
import com.jl.common.report.obj.Report;
import com.jl.common.report.obj.XReportFace;
import com.jl.common.report.servlet.ReportBuilder;
import com.jl.common.resource.ResourceNode;
import com.jl.service.FrameService;

public class XReportAction extends AbstractAction {

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		XReportIfc ins = (XReportIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("xReportIfc");
		String naturalname = request.getParameter("naturalname");
		XReportFace face = ins.buildFace(naturalname);
		Map<String, QueryColumn> columnsmap = face.getColumns();
		List<QueryColumn> list = new ArrayList<QueryColumn>();
		for (Iterator iterator = columnsmap.keySet().iterator(); iterator
				.hasNext();) {
			String object = (String) iterator.next();
			list.add(columnsmap.get(object));
		}

		Map<String, Report> reportsMap = face.getReports();

		// TODO 方法
		QueryColumn[] queryColumn = (QueryColumn[]) list
				.toArray(new QueryColumn[list.size()]);
		Double ww = face.getWidth();
		XReportFaceApi.setAvailWidth(ww.intValue());

		String queryFormHTML = XReportFaceApi.buildQueryForm(queryColumn,
				reportsMap, naturalname);
		List tabList = XReportFaceApi.buildTab(reportsMap);
		request.setAttribute("queryFormHTML", queryFormHTML);
		request.setAttribute("tabList", tabList);
		request.setAttribute("AvailWidth", XReportFaceApi.getAvailWidth() + 40);
		request.setAttribute("datecompFunc", DyFormComp.getInitFuncScript());
		return mapping.findForward("onMainView");
	}

	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		XReportIfc ins = (XReportIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("xReportIfc");
		String naturalname = request.getParameter("naturalname");
		Map data = setRequestMap(request);

		String reportid = (String) data.get("_REPORTID");
		if (reportid != null) {
			QueryCondition condition = new QueryCondition();
			condition.setSelectReportId(reportid);
			Map column2Value = new HashMap();
			Map column2Ope = new HashMap();
			column2Value = data;

			List<String> removeKey = new ArrayList<String>();
			for (Iterator iterator = data.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				if (key.contains(XReportFaceApi.$OPE)) {
					String opekey = key.replace(XReportFaceApi.$OPE, "");
					column2Ope.put(opekey, data.get(key));
					removeKey.add(key);
				}
				if (key.contains(XReportFaceApi.$START)) {
					removeKey.add(key);
				}
				if (key.contains(XReportFaceApi.$END)) {
					removeKey.add(key);
				}
			}

			// 移除不用KEY
			column2Value.remove("method");
			column2Value.remove("naturalname");
			column2Value.remove("reportid");
			for (Iterator iterator = removeKey.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				column2Value.remove(string);
			}

			condition.setColumn2Ope(column2Ope);
			condition.setColumn2Value(column2Value);

			// debug
			System.out.println("column2Ope:" + condition.getColumn2Ope());
			System.out.println("column2Value:" + condition.getColumn2Value());
			System.out.println("SelectReportId:"
					+ condition.getSelectReportId());

			// TODO 方法
			// 确定报表的展示格式
			String type = request.getParameter("format");
			if (StringUtils.isEmpty(type)) {
				type = "html";
			}
			ins.buildReport(type, naturalname, condition, response);
		}

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

	/**
	 * 设置请求对象封装成Map对象
	 * 
	 * @author Don
	 * @param request
	 *            请求对象
	 * @param obj
	 */
	protected Map setRequestMap(HttpServletRequest request) {
		Map map = new HashMap();
		Enumeration paramNameSet = request.getParameterNames();
		try {
			while (paramNameSet.hasMoreElements()) {
				String paramName = (String) paramNameSet.nextElement();

				String[] values = request.getParameterValues(paramName);
				if (values != null && values.length == 1) {
					map.put(paramName, values[0]);
				} else if (values != null && values.length > 1) {
					map.put(paramName, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

}
