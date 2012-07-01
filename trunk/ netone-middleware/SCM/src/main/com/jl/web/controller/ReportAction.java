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

public class ReportAction extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 分销商信息
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537483  where column19='1'");
		List list = DbTools.queryData(sb.toString());
		request.setAttribute("list", list);

		String forward = "/xreport/xreport2.jsp";
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
				.append(" ifnull(zcr.fxsno,'') fxsno,ifnull(zcr.fxsname,'') fxsname, ");
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

		if (StringUtils.isNotEmpty(beginDate)) {
			sb.append(" and t.column8>='" + beginDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(endDate)) {
			sb.append(" and t.column8<='" + endDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(clientId)) {
			sb.append(" and t.column12='" + clientId.trim() + "' ");
		}

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

		if (StringUtils.isNotEmpty(beginDate)) {
			sb.append(" and tt.column4>='" + beginDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(endDate)) {
			sb.append(" and tt.column4<='" + endDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(clientId)) {
			sb.append(" and tt.column8='" + clientId.trim() + "' ");
		}

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
		sb.append(" and year(t.column8)= year(now()) ");
		if (StringUtils.isNotEmpty(clientId)) {
			sb.append(" and t.column12='" + clientId.trim() + "' ");
		}
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
		if (StringUtils.isNotEmpty(clientId)) {
			sb.append(" and tt.column8='" + clientId.trim() + "' ");
		}
		sb.append(" group by fxsno ");

		sb.append(" ) zcr ");
		sb.append(" group by zcr.fxsno ");

		System.out.println("SQL:" + sb.toString());

		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();
		headerSet1.add(new TableCell("分销商编码"));
		headerSet1.add(new TableCell("分销商名称"));
		headerSet1.add(new TableCell("期初"));
		headerSet1.add(new TableCell("期初"));
		headerSet1.add(new TableCell("支出"));
		headerSet1.add(new TableCell("支入"));
		headerSet1.add(new TableCell("本年支出"));
		headerSet1.add(new TableCell("本年支入"));
		headerSet1.add(new TableCell("当前余额"));
		headerSet1.add(new TableCell("当前余额"));
		List<TableCell> headerSet2 = new ArrayList();
		headerSet2.add(new TableCell("分销商编码"));
		headerSet2.add(new TableCell("分销商名称"));
		headerSet2.add(new TableCell("方向"));
		headerSet2.add(new TableCell("金额"));
		headerSet2.add(new TableCell("支出"));
		headerSet2.add(new TableCell("支入"));
		headerSet2.add(new TableCell("本年支出"));
		headerSet2.add(new TableCell("本年支入"));
		headerSet2.add(new TableCell("方向"));
		headerSet2.add(new TableCell("金额"));

		ReportExt reportExt = new ReportExt();

		/**
		 * fxsno 分销商编码 fxsname 分销商名称 zcje 支出金额 zrje 支入金额 yue 余额 zcbnlj 本年支出金额累计
		 * bnzrjelj 本年支入金额累计
		 */
		List list = DbTools.queryData(sb.toString());
		double[] tmpdata = new double[1];
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String fxsno = object.get("fxsno").toString();// 分销商编码
			String fxsname = object.get("fxsname").toString();// 分销商名称
			String zcje = object.get("zcje").toString();// 支出金额
			String zrje = object.get("zrje").toString();// 支入金额
			String yue = object.get("yue").toString();// 余额
			String zcbnlj = object.get("zcbnlj").toString();// 本年支出金额累计
			String bnzrjelj = object.get("bnzrjelj").toString();// 本年支入金额累计

			TableRow tr = new TableRow();

			tr.addCell(new TableCell(fxsno));
			tr.addCell(new TableCell(fxsname));

			String getBeforeValue = getBeforeValue(fxsno, beginDate);

			tr.addCell(new TableCell(getFinanceDirection(Double
					.valueOf(getBeforeValue))));
			tr.addCell(new TableCell(""
					+ MathHelper.moneyFormat(convertPlus(Double
							.valueOf(getBeforeValue)))));

			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zcje)));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zrje)));

			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zcbnlj)));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(bnzrjelj)));

			Double ban = new Double("0");
			ban = Double.valueOf(getBeforeValue) + Double.valueOf(zcje)
					- Double.valueOf(zrje);

			tr.addCell(new TableCell(getFinanceDirection(ban)));
			tr.addCell(new TableCell(""
					+ MathHelper.moneyFormat(convertPlus(ban))));

			t.addRow(tr);

			// 合计
			tmpdata[0] += Double.valueOf(ban);
		}

		// 扩展
		List<TableCell> totalTrData = new ArrayList<TableCell>();
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 

		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell("" + getFinanceDirection(tmpdata[0])));// 
		totalTrData.add(new TableCell("" + MathHelper.moneyFormat(tmpdata[0])));// 
		totalTrData.set(0, new TableCell("合计"));
		TableRow tr = reportExt.getOneTableRow(totalTrData);
		tr.setType(Report.GROUP_TOTAL_TYPE);
		t.addRow(tr);

		Report report = reportExt.setComplexColHeader(t, headerSet1,
				headerSet2, true);
		String[] dateTitle = { beginDate + "至" + endDate,
				TimeUtil.formatDate(new Date()) };
		reportExt.setTitleHeader(report, "支出余额统计表", dateTitle, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "支出余额统计表" + currentTimeMillis, report,
				response);
	}

	private String getBeforeValue(String clientId, String beginDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select b.fxsno fxsno,b.fxsname, ");
		sb
				.append(" ifnull(((ifnull(sum(b.zcjebf),0) -ifnull(sum(b.zrjebf),0) )),0) qcyue ");

		sb.append(" from ( ");

		sb.append(" SELECT  ");

		sb.append(" ifnull(t.column12,'') fxsno, ");
		sb.append(" ifnull(fxs.column3,'') fxsname, ");
		sb.append(" ifnull(sum(t1.column5),0) zcjebf, ");
		sb.append(" 0 zrjebf ");
		sb.append(" FROM dyform.DY_661338441749389 t  ");
		sb
				.append(" LEFT JOIN dyform.DY_661338441749388 t1 ON t.LSH = t1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column12 = fxs.column4 ");

		sb
				.append(" WHERE t.STATUSINFO='01' and( t1.STATUSINFO='01' or t1.STATUSINFO = '03') ");
		sb.append(" and t.column8<'" + beginDate + " 00:00:00' ");

		sb.append(" and t.column12='" + clientId + "' ");

		sb.append(" group by fxsno ");

		sb.append(" union all ");

		sb.append(" SELECT  ");

		sb.append(" ifnull(tt.column8,'') fxsno, ");
		sb.append(" ifnull(tfxs.column3,'') fxsname, ");
		sb.append(" 0 zcjebf, ");
		sb.append(" ifnull(sum(tt1.column11),0) zrjebf ");
		sb.append(" FROM dyform.DY_371337952339241 tt  ");
		sb
				.append(" LEFT JOIN dyform.DY_371337952339238 tt1 ON tt.LSH = tt1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 tfxs ON tt.column8 = tfxs.column4 ");

		sb.append(" WHERE tt.STATUSINFO='01' and tt1.STATUSINFO='01' ");
		sb.append(" and tt.column4 <'" + beginDate + " 00:00:00' ");

		sb.append(" and tt.column8='" + clientId + "' ");

		sb.append(" group by fxsno ");

		sb.append(" ) b ");
		sb.append(" group by fxsno ");
		List list = DbTools.queryData(sb.toString());
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			String qcyue = map.get("qcyue").toString();
			return qcyue;
		} else {
			return "0";
		}
	}

	public static String getFinanceDirection(Double value) {
		if (value > 0) {
			return "正";
		} else if (value == 0) {
			return "平";
		} else if (value < 0) {
			return "负";
		}
		return "";
	}

	public static double convertPlus(Double value) {
		if (value < 0) {
			return -1 * value;
		}
		return value;
	}

}
