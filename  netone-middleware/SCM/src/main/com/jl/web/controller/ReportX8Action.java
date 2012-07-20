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
public class ReportX8Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");

		// 分销商信息
		request.setAttribute("list_FClient", ReportBaseData.getFClientInfo());
		request.setAttribute("list_GClient", ReportBaseData.getGClientInfo());
		request.setAttribute("list_ChangPingDaLei", ReportBaseData.getChangPingDaLeiInfo());
		request.setAttribute("list_ChengSe", ReportBaseData.getChengSeXingXiInfo());
		request.setAttribute("list_BaoShi", ReportBaseData.getBaoShiMingChengInfo());
		request.setAttribute("list_PingMing", ReportBaseData.getChangPingMingChengInfo());
		request.setAttribute("list_FenXiaoGuiZu", ReportBaseData.getFenXiaoGuiZuInfo());
		request.setAttribute("list_ZiDingDaLei", ReportBaseData.getZiDingDaLeiInfo());
		request.setAttribute("list_JingYingPingPai", ReportBaseData.getJingYingPingPaiInfo());
		
		String forward = "/xreport/xreport8.jsp";
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
		String repselect8 = request.getParameter("repselect8");
		String repselect9 = request.getParameter("repselect9");
		String repselect10 = request.getParameter("repselect10");
		StringBuffer sb = new StringBuffer();

		/** 分销库存统计表 */
		sb.append(" SELECT "); 
		/* 小品名 */
		sb.append(" IFNULL(t1.column4,'') pm, ");
		/* 系列名称 */
		sb.append(" IFNULL(xl.column3,'') xlname, ");
		/* 按款号 */
		sb.append(" IFNULL(t1.column8,'') kh, ");
		/* 原编号 */
		sb.append(" IFNULL(t1.column10,'') ybh, ");
		/* 按手寸 */
		sb.append(" IFNULL(t1.column13,'') sc, ");
		/* 分销商 */
		sb.append(" IFNULL(fxs.column3,'') fxsname, ");
		/* 按柜组 */
		sb.append(" IFNULL(gz.column4,'') gzname, "); 
		/* 按大类 */
		sb.append(" IFNULL(xtdl.column3,'') xtdlname, "); 
		/* 自定义大类 */
		sb.append(" IFNULL(zdy.column3,'') zdydlname, ");
		/* 石料统计 该分类去除，因无相应信息*/

		sb.append(" IFNULL(COUNT(t1.column3),0) sl, ");
		sb.append(" IFNULL(SUM(t1.column12),0) jz,IFNULL(SUM(t1.column11),0) zz, ");
		sb.append(" IFNULL(t1.column14,'/') zs,IFNULL(t1.column19,'/') fs, ");
		sb.append(" IFNULL(SUM(t1.column5),0) sj,IFNULL(rkmx.column31,0) jhcb ");

		sb.append(" FROM dyform.DY_661338441749389 t ");
		sb.append(" LEFT JOIN dyform.DY_661338441749388 t1 ON t.LSH = t1.FATHERLSH ");
		sb.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column12 = fxs.column4 ");
		sb.append(" LEFT JOIN dyform.DY_271334208897441 rkmx ON rkmx.column4 = t1.column3 ");
		sb.append(" LEFT JOIN dyform.DY_271334208897439 rkd ON rkmx.FATHERLSH=rkd.LSH ");
		sb.append(" LEFT JOIN dyform.DY_381336140843574 xl ON xl.column9 = rkmx.column51 ");
		sb.append(" LEFT JOIN dyform.DY_521339922112143 xtdl ON t1.column22 = xtdl.column8 ");
		sb.append(" LEFT JOIN dyform.DY_381336140843575 zdy ON zdy.column8 = rkmx.column48 ");
		sb.append(" LEFT JOIN dyform.DY_61336130537510 gz ON gz.column7 = t.column9 ");

		sb.append(" WHERE (t.STATUSINFO = '01' AND t1.STATUSINFO = '01') ");
		if(StringUtils.isNotEmpty(repstrcompare1_START))
			sb.append(" AND t1.column3 >= '" + repstrcompare1_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare1_END))
			sb.append(" AND t1.column3 <= '" + repstrcompare1_END + "' ");
		
		
		
		if(StringUtils.isNotEmpty(repstrcompare2_START))
			sb.append(" AND t.column3= '" + repstrcompare2_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_END))
			sb.append(" AND t.column3= '" + repstrcompare2_END + "' ");
		if(StringUtils.isNotEmpty(reptimes1_START))
			sb.append(" AND t.column8= '" + reptimes1_START + "' ");
		if(StringUtils.isNotEmpty(reptimes1_END))
			sb.append(" AND t.column8= '" + reptimes1_END + "' ");
		if(StringUtils.isNotEmpty(repselect2))
			sb.append(" AND t.column12 = '" + repselect2 + "' ");
		if(StringUtils.isNotEmpty(repselect3))
			sb.append(" AND rkd.column8 = '" + repselect3 + "' ");
		if(StringUtils.isNotEmpty(repselect4))
			sb.append(" AND rkmx.column50  = '" + repselect4 + "' ");
		if(StringUtils.isNotEmpty(repselect5))
			sb.append(" AND t1.column9 = '" + repselect5 + "' " );
		if(StringUtils.isNotEmpty(repselect6))
			sb.append("AND rkmx.column48 = '" + repselect6 + "' ");
		if(StringUtils.isNotEmpty(repselect7))
			sb.append(" AND t1.column22 = '" + repselect7 + "' " );
		if(StringUtils.isNotEmpty(repselect8))
			sb.append("AND rkmx.column52 = '" + repselect8 +"' ");
		if(StringUtils.isNotEmpty(repselect9))
			sb.append(" AND rkmx.column81 = '" + repselect9 + "' " );
		if(StringUtils.isNotEmpty(repselect10))
			sb.append("AND t1.column4 LIKE '%" + repselect10 + "%'");

		/* 小品名 */
		if("小品名".equals(repselect1))
			sb.append(" GROUP BY t1.column4 ");
		/* 系列名称 */
		if("系列名称".equals(repselect1))
		sb.append(" GROUP BY rkmx.column51 ");
		/* 按款号 */
		if("按款号".equals(repselect1))
		sb.append(" GROUP BY t1.column8 ");
		/* 原编号 */
		if("原编号".equals(repselect1))
		sb.append(" GROUP BY t1.column10 ");
		/* 按手寸 */
		if("按手寸".equals(repselect1))
		sb.append(" GROUP BY t1.column13 ");
		/* 分销商 */
		if("分销商".equals(repselect1))
		sb.append(" GROUP BY t.column12 ");
		/* 按柜组 */
		if("按柜组".equals(repselect1))
		sb.append(" GROUP BY t.column9 ");
		/* 按大类 */
		if("按大类".equals(repselect1))
		sb.append(" GROUP BY t1.column22 ");
		/* 自定大类 */
		if("自定大类".equals(repselect1))
		sb.append(" GROUP BY rkmx.column48 ");
		/* 石料统计 */
		
		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("小品名".equals(repselect1)) {
			headerSet1.add(new TableCell("品名"));
		} else if ("系列名称".equals(repselect1)) {
			headerSet1.add(new TableCell("系列名称"));
		} else if ("按款号".equals(repselect1)) {
			headerSet1.add(new TableCell("按款号"));
		} else if ("原编号".equals(repselect1)) {
			headerSet1.add(new TableCell("原编号"));
		} else if ("按手寸".equals(repselect1)) {
			headerSet1.add(new TableCell("按手寸"));
		} else if ("按大类".equals(repselect1)) {
			headerSet1.add(new TableCell("按大类"));
		} else if ("按柜组".equals(repselect1)) {
			headerSet1.add(new TableCell("按柜组"));
		} else if ("自定大类".equals(repselect1)) {
			headerSet1.add(new TableCell("自定大类"));
		} else if ("分销商".equals(repselect1)) {
			headerSet1.add(new TableCell("分销商"));
		}
		headerSet1.add(new TableCell("数量"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("总重"));
		headerSet1.add(new TableCell("主石(ct/p)"));
		headerSet1.add(new TableCell("副石(ct/p)"));
		headerSet1.add(new TableCell("售价"));
		headerSet1.add(new TableCell("进货成本"));

		ReportExt reportExt = new ReportExt();

		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String pm = object.get("pm").toString();
			String xlname = object.get("xlname").toString();
			String kh = object.get("kh").toString();
			String ybh = object.get("ybh").toString();
			String sc = object.get("sc").toString();
			String fxsname = object.get("fxsname").toString();
			String zdydlname = object.get("zdydlname").toString();
			String gzname = object.get("gzname").toString();
			String sl = object.get("sl").toString();
			String zz = object.get("zz").toString();
			String jz = object.get("jz").toString();
			String xtdlname = object.get("xtdlname").toString();
			String zs = object.get("zs").toString();
			String sj = object.get("sj").toString();
			String fs = object.get("fs").toString();
			String jhcb = object.get("jhcb").toString();

			TableRow tr = new TableRow();
			if ("小品名".equals(repselect1)) {
				tr.addCell(new TableCell(pm));
			} else if ("系列名称".equals(repselect1)) {
				tr.addCell(new TableCell(xlname));
			} else if ("原编号".equals(repselect1)) {
				tr.addCell(new TableCell(ybh));
			}else if ("按款号".equals(repselect1)) {
				tr.addCell(new TableCell(kh));
			} else if ("按手寸".equals(repselect1)) {
				tr.addCell(new TableCell(sc));
			} else if ("按大类".equals(repselect1)) {
				tr.addCell(new TableCell(xtdlname));
			} else if ("自定大类".equals(repselect1)) {
				tr.addCell(new TableCell(zdydlname));
			}else if ("按柜组".equals(repselect1)) {
				tr.addCell(new TableCell(gzname));
			} else if ("分销商".equals(repselect1)) {
				tr.addCell(new TableCell(fxsname));
			}
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sl),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zs));
			tr.addCell(new TableCell(fs));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jhcb),
					Rectangle.ALIGN_RIGHT));
			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "分销库存统计表", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "分销库存统计表" + currentTimeMillis, report,
				response);
	}
}
