//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.etl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.view.obj.ViewModel;
import oe.frame.web.page.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * MyEclipse Struts Creation date: 07-13-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class EtlTableVMAction extends Action {

	// --------------------------------------------------------- Instance
	// Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 决定直接跳转到图标页面
		String task = request.getParameter("task");

		if (request.getAttribute("errsmg") != null) {
			return mapping.findForward("TABLE_VIEW");
		}

		ViewModel viewModel = (ViewModel) request.getSession().getAttribute("viewModelNext");

		// 指标值
		double[][] targetValue = viewModel.getTargetvalue();

		// 维度值
		String[][] dimValue = viewModel.getDimensionvalue();

		// 指标名称
		String[] targetName = viewModel.getTargetname();

		// 维度名称
		String[] dimName = viewModel.getDimensionname();

		// 维度ID
		String[] dimID = viewModel.getDimensionid();

		// 指标ID
		String[] targetID = viewModel.getTargetid();

		Map otherdim = viewModel.getOtherDim();

		List titlelist = new ArrayList();
		List valuelist = new ArrayList();
		Map titlemap = new LinkedHashMap();

		for (int i = 0; i < dimID.length; i++) {
			titlemap.put(dimID[i], dimName[i]);
		}
		for (int i = 0; i < targetID.length; i++) {
			titlemap.put(targetID[i], targetName[i]);
		}
		for (int i = 0; i < dimValue.length; i++) {
			Map valuemap = new LinkedHashMap();
			for (int j = 0; j < dimID.length; j++) {
				valuemap.put(dimID[j], dimValue[i][j]);
			}
			for (int j = 0; j < targetID.length; j++) {
				valuemap.put(targetID[j], targetValue[i][j]);
			}
			valuelist.add(valuemap);
		}
		for (Iterator itr = otherdim.keySet().iterator(); itr.hasNext();) {
			String dimkey = (String) itr.next();
			List mapPre = (List) otherdim.get(dimkey);
			String key = StringUtils.substringBefore(dimkey, "[");
			String keyName = StringUtils.substringBetween(dimkey, "[", "]");
			titlemap.put(key, keyName);
			int i = 0;
			List tmplist = new ArrayList();
			for (Iterator iterator = valuelist.iterator(); iterator.hasNext();) {
				Map valuemap = (Map) iterator.next();
				if (i < mapPre.size()) {
					String valuePre = (String) mapPre.get(i);
					valuemap.put(key, valuePre);
				}
				tmplist.add(valuemap);
				i++;
			}
			valuelist = tmplist;
		}
		titlelist.add(titlemap);

		// 初始化分页信息
		String strpageinfo = request.getParameter(PageInfo.KEY);

		PageInfo pginfo = null;

		if (strpageinfo == null || strpageinfo.equals("")) {
			pginfo = new PageInfo(targetValue.length, request, "etlpage2");
		} else {
			pginfo = new PageInfo(request, "etlpage2");
			pginfo.parseStr(strpageinfo);
		}

		// 生成分页显示的数据

		int beginindex = pginfo.getPageStartIndex();
		int endindex = pginfo.getPageEndIndex();
		int pagecount = pginfo.getPageItemCount();

		request.setAttribute("titlelist", titlelist);
		if (beginindex >= 0 && endindex >= 0 && endindex >= beginindex) {
			valuelist = valuelist.subList(beginindex, endindex + 1);
		}
		request.setAttribute("valuelist", valuelist);

		if (request.getAttribute("FirstGraphModel") != null) {
			request.getSession().setAttribute("FirstGraphModel", request.getAttribute("FirstGraphModel"));
			request.setAttribute("ShowFirstGraphModel", "y");
		}
		// 使用flowpage
		if ("flowpage".equals(request.getParameter("flowpage"))) {
			request.setAttribute("flowpage", "flowpage");
			request.setAttribute("endTime2", request.getParameter("endTime"));
			request.setAttribute("dataModelid2", request.getParameter("dataModelid"));
			request.setAttribute("timelevel2", request.getParameter("timelevel"));
			request.setAttribute("hour2", request.getParameter("hour"));
			request.setAttribute("flowpage2", "flowpage");
		}
		if ("flowpage".equals(request.getParameter("flowpage2"))) {
			request.setAttribute("flowpage", "flowpage");
			request.setAttribute("endTime", request.getParameter("endTime2"));
			request.setAttribute("dataModelid", request.getParameter("dataModelid2"));
			request.setAttribute("timelevel", request.getParameter("timelevel2"));
			request.setAttribute("hour", request.getParameter("hour2"));
			request.setAttribute("flowpage2", "flowpage");
		}
		// --- nihq 2008-2-13 START------------
		// 跳转原始图表
		if ("chart0".equals(task)) {
			return mapping.findForward("chart0");
		}
		// ------------nihq 2008-2-13 END----------
		return mapping.findForward("TABLE_VIEW");

	}
}
