package com.jl.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jl.common.MathHelper;
import com.jl.common.TimeUtil;
import com.jl.common.report.GroupReport;
import com.jl.common.report.ReportExt;
import com.jl.common.workflow.DbTools;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

public class ReportXAction extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");

		// 分销商信息
		request.setAttribute("list", ReportBaseData.getFClientInfo());

		String forward = "/xreport/xreport3.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true不使用转向,默认是false代表转向
		return af;
	}

	// 打印 珠宝 首饰销售打印
	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String format = request.getParameter("format");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String clientId = request.getParameter("clientId");
		StringBuffer sb = new StringBuffer();

		sb.append(" select");
		sb
				.append(" IFNULL(t.column8,'') 'fxsno',IFNULL(fxs.column3,'') 'fxsname', ");
		sb.append(" SUM(IFNULL(t1.column15,0)) dpxse, ");
		sb.append(" SUM(IFNULL(rkmx.column31,0)) dpcgcb, ");

		sb
				.append(" IFNULL(SUM(IFNULL(t1.column15,0)) - SUM(IFNULL(rkmx.column31,0)),0) mlr  ");
		sb.append(" FROM dyform.DY_371337952339241 t ");
		sb
				.append(" LEFT JOIN dyform.DY_371337952339238 t1 ON t.LSH = t1.FATHERLSH");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4");
		sb
				.append(" LEFT JOIN dyform.DY_271334208897441 rkmx ON t1.column3 = rkmx.column4");
		sb.append(" WHERE t.STATUSINFO='01' ");
		if (StringUtils.isNotEmpty(beginDate)) {
			sb.append(" and t.column4>='" + beginDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(endDate)) {
			sb.append(" and t.column4<='" + endDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(clientId)) {
			sb.append(" and t.column8='" + clientId.trim() + "' ");
		}
		sb.append(" GROUP BY fxsno ");
		sb.append("ORDER BY fxsno");

		System.out.println("SQL:" + sb.toString());

		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();
		headerSet1.add(new TableCell("分销商编码"));
		headerSet1.add(new TableCell("分销商名称"));
		headerSet1.add(new TableCell("单品销售金额"));
		headerSet1.add(new TableCell("单品采购成本"));
		headerSet1.add(new TableCell("毛利润"));

		ReportExt reportExt = new ReportExt();

		/**
		 * fxsno 分销商编码 fxsname 分销商名称 zcje 支出金额 zrje 支入金额 yue 余额 zcbnlj 本年支出金额累计
		 * bnzrjelj 本年支入金额累计
		 */
		List list = DbTools.queryData(sb.toString());
		double[] tmpdata = new double[3];
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String fxsno = object.get("fxsno").toString();// 分销商编码
			String fxsname = object.get("fxsname").toString();// 分销商名称
			String dpxse = object.get("dpxse").toString();// 单品销售金额
			String dpcgcb = object.get("dpcgcb").toString();// 单品采购成本
			String mlr = object.get("mlr").toString();// 毛利润

			TableRow tr = new TableRow();

			tr.addCell(new TableCell(fxsno));
			tr.addCell(new TableCell(fxsname));

			tr.addCell(new TableCell("" + MathHelper.moneyFormat(dpxse),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(dpcgcb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(mlr),
					Rectangle.ALIGN_RIGHT));

			t.addRow(tr);

			// 合计
			tmpdata[0] += Double.valueOf(dpxse);
			tmpdata[1] += Double.valueOf(dpcgcb);
			tmpdata[2] += Double.valueOf(mlr);
		}

		// 扩展
		List<TableCell> totalTrData = new ArrayList<TableCell>();
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell("" + MathHelper.moneyFormat(tmpdata[0]),
				Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + MathHelper.moneyFormat(tmpdata[1]),
				Rectangle.ALIGN_RIGHT));// 

		totalTrData.add(new TableCell("" + MathHelper.moneyFormat(tmpdata[2]),
				Rectangle.ALIGN_RIGHT));// 

		totalTrData.set(0, new TableCell("合计"));
		TableRow tr = reportExt.getOneTableRow(totalTrData);
		tr.setType(Report.GROUP_TOTAL_TYPE);
		t.addRow(tr);

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		String[] dateTitle = { beginDate + "至" + endDate,
				TimeUtil.formatDate(new Date()) };
		reportExt.setTitleHeader(report, "利润分析表", dateTitle, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "利润分析表" + currentTimeMillis, report,
				response);
	}

}
