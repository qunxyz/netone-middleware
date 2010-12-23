//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.bi.web.etl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.common.chart.JFreeChartUtil;
import oe.bi.common.chart.ResponseChartInfo;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.view.bus.filt.DimensionFilt;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;
import oe.bi.view.obj.ext.GraphTypes;
import oe.bi.web.etl.chart.EtlChart;
import oe.frame.web.form.RequestUtil;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;


/**
 * MyEclipse Struts Creation date: 07-13-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class EtlChartVMAction extends Action {
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

		String act = request.getParameter("act");

		ViewModel viewModel = (ViewModel) request.getSession().getAttribute("viewModelNext");

		if ("CHART_SELECT".equals(act)) {
			// 处理图表的选择定制

			if (request.getAttribute("errsmg") != null) {
				return mapping.findForward("CHART_SELECT");
			}

			request.setAttribute("graphTypeList", GraphTypes._GH_ALL);

			DimensionFilt dimfilt = (DimensionFilt) BiEntry.fetchBi("dimensionFilt");

			String[][] dimvalues = dimfilt.allDimensionValueList(viewModel);
			request.setAttribute("dimvalues", dimvalues);

			ArrayList tglist = new ArrayList();
			String[] tgids = viewModel.getTargetid();
			String[] tgnames = viewModel.getTargetname();
			if (tgids != null && tgnames != null) {
				for (int i = 0; i < tgids.length; i++) {
					LabelValueBean lvb = new LabelValueBean(tgnames[i], tgids[i]);
					tglist.add(lvb);
				}
			}

			request.setAttribute("tglist", tglist);

			GraphModel gm = createGraphModel(request, true);
			if (gm != null) {
				request.setAttribute("GraphModel", gm);
				request.setAttribute("multichart", request.getParameter("multichart"));
			}
			// --- nihq 2008-2-13 START------------
			// 目的:新增图表分析设计
			// 跳转原始图表
			if ("chart0".equals(request.getParameter("task"))) {
				return mapping.findForward("CHART_SELECT0");
			}
			// ------------nihq 2008-2-13 END----------

			return mapping.findForward("CHART_SELECT");

		} else if ("CHART_SHOW".equals(act)) {

			if (request.getAttribute("errsmg") != null) {
				return mapping.findForward("CHART_VIEW");
			}

			DimensionFilt dimfilt = (DimensionFilt) BiEntry.fetchBi("dimensionFilt");

			GraphModel gm = createGraphModel(request, false);

			boolean multichart = false;
			String multichartstr = request.getParameter("multichart");
			if (multichartstr != null && multichartstr.equals("1")) {
				multichart = true;
			}

			if (gm.getGraphType().endsWith("ne-time") || gm.getGraphType().endsWith("time-ne")) {
				multichart = true;
			}

			ViewModel newvm = null;

			if (gm.getGraphType().endsWith("ne-time") || gm.getGraphType().endsWith("time-ne")) {
				newvm = viewModel;
			} else {
				try {
					newvm = dimfilt.filtvalue(viewModel, gm);
				} catch (MoreThenOneDimensionViewModel e) {
					request.setAttribute("errsmg", e.getMessage());
					e.printStackTrace();
				}
			}

			// 构建图表数据
			String pngwidthstr = request.getParameter("pngwidth");
			String maxvaluestr = request.getParameter("maxvalue");
			String showvalue = request.getParameter("showvalue");
			String pictitle = request.getParameter("pictitle");
			String piccolor = request.getParameter("piccolor");
			String xqingxie = request.getParameter("xqingxie");
			if(request.getSession().getAttribute("choiceinfo")!=null){
				ChoiceInfo choiceinfo = (ChoiceInfo)request.getSession().getAttribute("choiceinfo");
				pngwidthstr = choiceinfo.getPngwidth();
				maxvaluestr = choiceinfo.getMaxvalue();
				showvalue = choiceinfo.getShowvalue();
				pictitle = choiceinfo.getPictitle();
				piccolor = choiceinfo.getPiccolor();
				xqingxie = choiceinfo.getXqingxie();
				request.getSession().removeAttribute("choiceinfo");
			}
			int pngwidth = 0;
			int maxvalue = 0;
			try {
				if (pngwidthstr != null) {
					pngwidth = Integer.parseInt(pngwidthstr);
				}
			} catch (Exception e) {
			}
			try {
				if (maxvaluestr != null) {
					maxvalue = Integer.parseInt(maxvaluestr);
				}
			} catch (Exception e) {
			}
			ArrayList chartlist = new ArrayList();
			if (!multichart) {
				EtlChart etlchart = new EtlChart(newvm, gm);
				etlchart.setMaxValue(maxvalue);
				etlchart.setPicWidth(pngwidth);
				etlchart.setPiccolor(piccolor);
				etlchart.setPictitle(pictitle);
				etlchart.setXqingxie(xqingxie);
				if (showvalue != null) {
					etlchart.setShowvalue(showvalue);
				}
				ResponseChartInfo respchart = JFreeChartUtil.cacheChartToSession(etlchart.getJfreeChart(), etlchart
						.getChartparam(), request, response, 0);
				chartlist.add(respchart);
			} else {
				String seltg = gm.getChoicetarget();
				String[] tgs;
				if (seltg.equals("")) {
					tgs = newvm.getTargetid();
				} else {
					tgs = seltg.split(",");
				}
				for (int i = 0; i < tgs.length; i++) {
					EtlChart etlchart = new EtlChart(newvm, gm, tgs[i]);
					etlchart.setMaxValue(maxvalue);
					etlchart.setPicWidth(pngwidth);
					etlchart.setPiccolor(piccolor);
					etlchart.setPictitle(pictitle);
					etlchart.setXqingxie(xqingxie);
					ResponseChartInfo respchart = JFreeChartUtil.cacheChartToSession(etlchart.getJfreeChart(), etlchart
							.getChartparam(), request, response, i);
					chartlist.add(respchart);

				}
			}

			request.setAttribute("chartlist", chartlist);

			RequestUtil.setParamMapToRequest(request);

			// 使用flowpage
			if ("flowpage".equals(request.getParameter("flowpage"))) {
				request.setAttribute("flowpage", "flowpage");
			}
			// --- nihq 2008-2-13 START------------
			// 决定直接跳转到图表页面
			request.setAttribute("task", request.getParameter("task"));
			// ------------nihq 2008-2-13 END----------
			return mapping.findForward("CHART_VIEW");
		}

		return null;
	}

	private GraphModel createGraphModel(HttpServletRequest request, boolean encode) {

		if (request.getParameter("ShowFirstGraphModel") != null) {
			return (GraphModel) request.getSession().getAttribute("FirstGraphModel");
		}

		// 构造GraphModel by hls
		String seldimvalue = iso8859ToGBK(request.getParameter("seldimvalue"), encode);
		String seltgvalue = iso8859ToGBK(request.getParameter("seltgvalue"), encode);
		//System.out.println("seltgvalue==" + seltgvalue);
		String OffsetDim = iso8859ToGBK(request.getParameter("OffsetDim"), encode);
		String selcharttype = iso8859ToGBK(request.getParameter("selcharttype"), encode);

		GraphModel gm = null;

		if (OffsetDim != null && !OffsetDim.equals("")) {
			gm = new GraphModel();
			gm.setChoicetarget(seltgvalue);
			gm.setGraphType(selcharttype);
			gm.setXOffsetDimension(OffsetDim);
			String[] seldims = seldimvalue.split(",");
			HashMap dimmap = new HashMap();
			for (int i = 0; i < seldims.length; i++) {
				int index = seldims[i].indexOf("=");
				if (index != -1) {
					String key = seldims[i].substring(0, index);
					String value = seldims[i].substring(index + 1);
					dimmap.put(key, value);
				}
			}
			gm.setOtherDimension(dimmap);
		}

		return gm;
	}

	private String iso8859ToGBK(String str, boolean encode) {
		if (!encode) {
			return str;
		}

		if (str != null) {
			try {
				return new String(str.getBytes("iso-8859-1"), "GBK");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
