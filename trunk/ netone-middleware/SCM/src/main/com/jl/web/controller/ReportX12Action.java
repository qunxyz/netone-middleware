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

/**
 * 首饰销退统计表
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-7-10 上午10:58:41
 * @history
 */
public class ReportX12Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");

		// 分销商信息
		request.setAttribute("list_GClient", ReportBaseData.getGClientInfo());
		request.setAttribute("list_JingYingPingPai", ReportBaseData.getJingYingPingPaiInfo());
		request.setAttribute("list_ChangPingDaLei", ReportBaseData.getChangPingDaLeiInfo());
		request.setAttribute("list_ChengSe", ReportBaseData.getChengSeXingXiInfo());
		request.setAttribute("list_BaoShi", ReportBaseData.getBaoShiMingChengInfo());
		request.setAttribute("list_PingMing", ReportBaseData.getChangPingMingChengInfo());
		
		
		String forward = "/xreport/xreport12.jsp";
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

		String repselect1 = request.getParameter("repselect1");
		String repstrcompare1_START = request.getParameter("repstrcompare1_START");
		String repstrcompare1_END = request.getParameter("repstrcompare1_END");
		String repstrcompare2_START = request.getParameter("repstrcompare2_START");
		String repstrcompare2_END = request.getParameter("repstrcompare2_END");
		String reptimes1_START = request.getParameter("reptimes1_START");
		String reptimes1_END = request.getParameter("reptimes1_END");

		
		String repselect2 = request.getParameter("repselect2");
		String repselect3 = request.getParameter("repselect3");
		String repselect4 = request.getParameter("repselect4");
		String repselect5 = request.getParameter("repselect5");
		String repselect6 = request.getParameter("repselect6");
		String repselect7 = request.getParameter("repselect7");
		String repstr3 = request.getParameter("repstr3");
		String repstr4 = request.getParameter("repstr4");
		String repstr5 = request.getParameter("repstr5");
		StringBuffer sb = new StringBuffer();

		
		
		
		/** 首饰退库统计表 */
		sb.append(" SELECT "); 
		/* 小品名 */
		sb.append(" IFNULL(t1.column4,'') pm, ");
		/* 供应商 */
		sb.append(" IFNULL(t5.column3,'') gysname, ");
		/* 按单号 */
		sb.append(" IFNULL(t.column3,'') tkno, ");

		sb.append(" IFNULL(COUNT(t1.column3),0) sl, ");
		sb.append(" IFNULL(SUM(t1.column13),0) zz, ");
		sb.append(" IFNULL(SUM(t1.column15),0) jz, ");
		sb.append(" IFNULL(t1.column17,'/') zs, ");
		sb.append(" IFNULL(t1.column22,'/') fs, ");
		sb.append(" IFNULL(SUM(t1.column29),0)jhcb, ");
		sb.append(" IFNULL(SUM(t1.column27),0) zscb, ");
		sb.append(" IFNULL(SUM(t1.column28),0) sccb, ");
		sb.append(" IFNULL(SUM(t1.column6),0) sj, ");
		sb.append(" IFNULL(t3.column8,'') gys ");
		sb.append(" FROM dyform.DY_621338820565623 t ");
		sb.append(" LEFT JOIN dyform.DY_621338820565622 t1 ON t1.FATHERLSH=t.LSH ");
		sb.append(" LEFT JOIN dyform.DY_271334208897441 t2 ON t1.column3 = t2.column4 ");
		sb.append(" LEFT JOIN dyform.DY_271334208897439 t3 ON t2.FATHERLSH=t3.LSH ");
		sb.append(" LEFT JOIN dyform.DY_521339922112143 t4 ON t4.column8 = t3.column14 ");
		sb.append(" LEFT JOIN dyform.DY_941335942247762 t5 ON t5.column4= t3.column8 ");
		sb.append(" WHERE t.STATUSINFO = '01' ");
		if(StringUtils.isNotEmpty(repstrcompare1_START))
		sb.append(" AND t1.column3 >= '" + repstrcompare1_START + "'   ");
		if(StringUtils.isNotEmpty(repstrcompare1_END))
			sb.append(" AND t1.column3 <= '" + repstrcompare1_END + "'   ");
		if(StringUtils.isNotEmpty(repstrcompare2_START))
		sb.append(" AND t.column3 >= '" + repstrcompare2_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_END))
			sb.append(" AND t.column3 <= '" + repstrcompare2_END + "' ");
		if(StringUtils.isNotEmpty(reptimes1_START))
		sb.append(" AND t.tkdate >= '" + reptimes1_START + "' ");
		if(StringUtils.isNotEmpty(reptimes1_END))
			sb.append(" AND t.tkdate <= '" + reptimes1_END + "' ");
		if(StringUtils.isNotEmpty(repselect2))
		sb.append(" AND t3.column8 = '" + repselect2 + "'  ");
		if(StringUtils.isNotEmpty(repselect4))
		sb.append(" AND t2.column50 = '" + repselect4 + "'  ");
		if(StringUtils.isNotEmpty(repselect5))
		sb.append(" AND t3.column14 = '" + repselect5 + "'  ");
		if(StringUtils.isNotEmpty(repselect6))
		sb.append(" AND t2.column52 = '" + repselect6 + "'  ");
		if(StringUtils.isNotEmpty(repselect7))
		sb.append(" AND t1.column4 LIKE '%" + repselect7+ "%' ");
		if(StringUtils.isNotEmpty(repstr3))
		sb.append(" AND t.column4 LIKE '%" + repstr3+ "%'  ");
		if(StringUtils.isNotEmpty(repstr4))
		sb.append(" AND t.column8 LIKE '%" + repstr4+ "%'  ");
		if(StringUtils.isNotEmpty(repstr5))
		sb.append(" AND t1.column7 LIKE '%" + repstr5+ "%' ");

		/* 小品名 */
		if("小品名".equals(repselect1))
		sb.append(" GROUP BY pm "); 
		/* 供应商  */
		if("供应商".equals(repselect1))
		sb.append(" GROUP BY gys ");
		/* 按单号 */
		if("按单号".equals(repselect1))
		sb.append(" GROUP BY tkno ");
		
		


		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("小品名".equals(repselect1)) {
			headerSet1.add(new TableCell("品名"));
		} else if ("供应商".equals(repselect1)) {
			headerSet1.add(new TableCell("供应商"));
		} else if ("按单号".equals(repselect1)) {
			headerSet1.add(new TableCell("按单号"));
		} 
		headerSet1.add(new TableCell("数量"));
		headerSet1.add(new TableCell("总重"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("主石(ct/p)"));
		headerSet1.add(new TableCell("副石(ct/p)"));
		headerSet1.add(new TableCell("进货成本"));
		headerSet1.add(new TableCell("真实成本"));
		headerSet1.add(new TableCell("市场成本"));
		headerSet1.add(new TableCell("售价"));
		
		ReportExt reportExt = new ReportExt();

		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String pm = object.get("pm").toString();
			String gysname = object.get("gysname").toString();
			String tkno = object.get("tkno").toString();
			String sl = object.get("sl").toString();
			String zz = object.get("zz").toString();
			String jz = object.get("jz").toString();
			String zs = object.get("zs").toString();
			String fs = object.get("fs").toString();
			String jhcb = object.get("jhcb").toString();
			String zscb = object.get("zscb").toString();
			String sccb = object.get("sccb").toString();
			String sj = object.get("sj").toString();
			String gys = object.get("gys").toString();

			
			TableRow tr = new TableRow();
			if ("小品名".equals(repselect1)) {
				tr.addCell(new TableCell(pm));
			} else if ("供应商".equals(repselect1)) {
				tr.addCell(new TableCell(gysname));
			} else if ("按单号".equals(repselect1)) {
				tr.addCell(new TableCell(tkno));
			}
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sl),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zs));
			tr.addCell(new TableCell(fs));
			
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jhcb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zscb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sccb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
			t.addRow(tr);
		}
		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "首饰退库统计表", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "首饰退库统计表" + currentTimeMillis, report,
				response);
	}
}
