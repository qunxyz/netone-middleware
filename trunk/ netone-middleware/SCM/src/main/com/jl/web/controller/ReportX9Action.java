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
public class ReportX9Action extends AbstractAction {
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
		request.setAttribute("list_ShouHuoYuan", ReportBaseData.getShouHuoYuanInfo());
		request.setAttribute("list_DianMianJingLi", ReportBaseData.getDianMianJingLiInfo());
		request.setAttribute("list_ShouYinBanZu", ReportBaseData.getShouYinBanZuInfo());
		request.setAttribute("list_XiaoShouShuXing", ReportBaseData.getXiaoShouShuXingInfo());
		request.setAttribute("list_FenXiaoGuiZu", ReportBaseData.getFenXiaoGuiZuInfo());
		
		
		String forward = "/xreport/xreport9.jsp";
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

		String repselect4 = request.getParameter("repselect4");
		String repstrcompare1_START = request.getParameter("repstrcompare1_START");
		String repstrcompare1_END = request.getParameter("repstrcompare1_END");
		String repstrcompare2_START = request.getParameter("repstrcompare2_START");
		String repstrcompare2_END = request.getParameter("repstrcompare2_END");
		String reptimes1_START = request.getParameter("reptimes1_START");
		String reptimes1_END = request.getParameter("reptimes1_END");

		String repselect1 = request.getParameter("repselect1");
		String repselect2 = request.getParameter("repselect2");
		String repselect3 = request.getParameter("repselect3");
		String repstr3 = request.getParameter("repstr3");
		String repstr4 = request.getParameter("repstr4");
		String repselect5 = request.getParameter("repselect5");
		String repselect6 = request.getParameter("repselect6");
		String repselect7 = request.getParameter("repselect7");
		String repselect8 = request.getParameter("repselect8");
		String repselect9 = request.getParameter("repselect9");
		String repselect10 = request.getParameter("repselect10");
		String repselect11 = request.getParameter("repselect11");
		String repselect12 = request.getParameter("repselect12");
		String repselect13 = request.getParameter("repselect13");
		String repselect14 = request.getParameter("repselect14");
		StringBuffer sb = new StringBuffer();

		/** 首饰销售统计表(扣除销退) */
		sb.append("SELECT ");
		/* 品名 */
		sb.append("IFNULL(t1.column4,'') pm, ");
		/* 系列名称 */
		sb.append("IFNULL(xl.column3,'') xlname, ");
		/* 按成色 */
		sb.append("IFNULL(cs.column3,'') cs, ");
		/* 按款号 */
		sb.append("IFNULL(rkmx.column11,'') kh, ");
		/* 原编号 */
		sb.append("IFNULL(rkmx.column13,'') ybh, ");
		/* 按手寸 */
		sb.append("IFNULL(rkmx.column24,'') sc, ");
		/* 售货员 */
		sb.append("IFNULL(xsr.column4,'') shy, ");
		/* 按大类 */
		sb.append("IFNULL(xtdl.column3,'') cpdl, ");
		/* 自定大类 */
		sb.append("IFNULL(zdy.column3,'') zdy, ");
		/* 分销商 */
		sb.append("IFNULL(fxs.column3,'') fxsname, ");
		/* 按柜组 */
		sb.append("IFNULL(gz.column4,'') gz, ");
		/* 单号统计 */
		sb.append("IFNULL(t.column3,'') xsno, ");
		/* 货号统计 */
		sb.append("IFNULL(t1.column23,'') hh, ");

		sb.append("IFNULL(COUNT(t1.column3),0) sl, ");
		sb.append("IFNULL(SUM(rkmx.column16),0) zz,IFNULL(SUM(rkmx.column17),0) jz, ");
		sb.append("IFNULL(rkmx.column37,'/') zs,IFNULL(rkmx.column66,'/') fs, ");
		sb.append("IFNULL(SUM(t1.column11),0) sj, ");
		sb.append("IFNULL(SUM(t1.column15),0) ssj ");
		sb.append("FROM dyform.DY_371337952339241 t ");
		sb.append("LEFT JOIN dyform.DY_371337952339238 t1 ON t.LSH = t1.FATHERLSH ");
		sb.append("LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4 ");
		sb.append("LEFT JOIN dyform.DY_271334208897441 rkmx ON rkmx.column4 = t1.column3 ");
		sb.append("LEFT JOIN dyform.DY_271334208897439 rkd ON rkmx.FATHERLSH=rkd.LSH ");
		sb.append("LEFT JOIN dyform.DY_381336140843574 xl ON xl.column9 = rkmx.column51 ");
		sb.append("LEFT JOIN dyform.DY_61336130537507 cs ON rkmx.column52 = cs.column9 ");
		sb.append("LEFT JOIN dyform.DY_521339922112143 xtdl ON t1.column19 = xtdl.column8 ");
		sb.append("LEFT JOIN dyform.DY_381336140843575 zdy ON zdy.column8 = rkmx.column48 ");
		sb.append("LEFT JOIN dyform.DY_61336130537510 gz ON gz.column7 = t1.column18 ");
		sb.append("LEFT JOIN dyform.DY_381336140843571 xsr ON xsr.column3 = t.column10 ");
		sb.append("WHERE t.STATUSINFO = '01' AND t1.STATUSINFO = '01'  ");
		System.out.println(StringUtils.isNotEmpty(repstrcompare1_START));
		if(StringUtils.isNotEmpty(repstrcompare1_START))
		    sb.append(" AND t.column3= '" + repstrcompare1_START + "' ");
		System.out.println(StringUtils.isNotEmpty(repstrcompare2_END));
		if(StringUtils.isNotEmpty(repstrcompare2_END))
		    sb.append(" AND t.column3= '" + repstrcompare1_END + "' "); 
		System.out.println(StringUtils.isNotEmpty(repstrcompare1_START));
		if(StringUtils.isNotEmpty(repstrcompare2_START))
		    sb.append(" AND t.column3= '" + repstrcompare2_START + "' ");
		System.out.println(StringUtils.isNotEmpty(repstrcompare2_END));
		if(StringUtils.isNotEmpty(repstrcompare2_END))
		    sb.append(" AND t.column3= '" + repstrcompare2_END + "' ");
		System.out.println(StringUtils.isNotEmpty(reptimes1_START));
		if(StringUtils.isNotEmpty(reptimes1_START))
		    sb.append("AND t1.column4 >= '" + reptimes1_START + "' ");
		System.out.println(StringUtils.isNotEmpty(reptimes1_END));
		if(StringUtils.isNotEmpty(reptimes1_END))
		    sb.append(" AND t1.column4 <= '" + reptimes1_END + "' ");
		System.out.println(StringUtils.isNotEmpty(repselect1));
		if(StringUtils.isNotEmpty(repselect1))
			sb.append(" AND t.column8= '" + repselect1 + "' ");

		/* 品名 */
		if("品名".equals(repselect4))
			sb.append("GROUP BY t1.column4 ");
		/* 系列名称 */
		if("系列名称".equals(repselect4))
			sb.append("GROUP BY rkmx.column51 ");
		/* 按成色 */
		if("按成色".equals(repselect4))
			sb.append("GROUP BY rkmx.column52 ");
		/* 按款号 */
		if("按款号".equals(repselect4))
			sb.append("GROUP BY rkmx.column11 ");
		/* 原编号 */
		if("原编号".equals(repselect4))
			sb.append("GROUP BY rkmx.column13 ");
		/* 按手寸 */
		if("按手寸".equals(repselect4))
			sb.append("GROUP BY rkmx.column24 ");
		/* 售货员 */
		if("售货员".equals(repselect4))
			sb.append("GROUP BY t.column10 ");
		/* 按大类 */
		if("按大类".equals(repselect4))
			sb.append("GROUP BY t1.column19 ");
		/* 自定大类 */
		if("自定大类".equals(repselect4))
			sb.append("GROUP BY rkmx.column48 ");
		/* 分销商 */
		if("分销商".equals(repselect4))
			sb.append("GROUP BY t1.column8 ");
		/* 按柜组 */
		if("按柜组".equals(repselect4))
			sb.append("GROUP BY t1.column18 ");
		/* 单号统计 */
		if("单号统计".equals(repselect4))
			sb.append("GROUP BY t.column3 ");
		/* 货号统计 */
		if("货号统计".equals(repselect4))
			sb.append("GROUP BY t1.column23 ");
		
		
		
		
		
		
		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("小品名".equals(repselect4)) {
			headerSet1.add(new TableCell("品名"));
		} else if ("系列名称".equals(repselect4)) {
			headerSet1.add(new TableCell("系列名称"));
		} else if ("按款号".equals(repselect4)) {
			headerSet1.add(new TableCell("按款号"));
		} else if ("原编号".equals(repselect4)) {
			headerSet1.add(new TableCell("原编号"));
		} else if ("按手寸".equals(repselect4)) {
			headerSet1.add(new TableCell("按手寸"));
		} else if ("按大类".equals(repselect4)) {
			headerSet1.add(new TableCell("按大类"));
		} else if ("按柜组".equals(repselect4)) {
			headerSet1.add(new TableCell("按柜组"));
		} else if ("自定大类".equals(repselect4)) {
			headerSet1.add(new TableCell("自定大类"));
		} else if ("分销商".equals(repselect4)) {
			headerSet1.add(new TableCell("分销商"));
		} else if ("按成色".equals(repselect4)) {
			headerSet1.add(new TableCell("按成色"));
		} else if ("售货员".equals(repselect4)) {
			headerSet1.add(new TableCell("售货员"));
		} else if ("销售提成".equals(repselect4)) {
			headerSet1.add(new TableCell("销售提成"));
		} else if ("石料统计".equals(repselect4)) {
			headerSet1.add(new TableCell("石料统计"));
		} else if ("单号统计".equals(repselect4)) {
			headerSet1.add(new TableCell("单号统计"));
		} else if ("货号统计".equals(repselect4)) {
			headerSet1.add(new TableCell("货号统计"));
		}
		headerSet1.add(new TableCell("数量"));
		headerSet1.add(new TableCell("总重"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("主石(ct/p)"));
		headerSet1.add(new TableCell("副石(ct/p)"));
		headerSet1.add(new TableCell("售价"));
		headerSet1.add(new TableCell("实售价"));
		headerSet1.add(new TableCell("进货成本"));
		ReportExt reportExt = new ReportExt();
System.out.println(sb.toString());
		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String pm = (String)object.get("pm");
			String xlname = (String)object.get("xlname");
			String kh = (String)object.get("kh");
			String ybh = (String)object.get("ybh");
			String sc = (String)object.get("sc");
			String fxsname = (String)object.get("fxsname");
			String zdydlname = (String)object.get("zdydlname");
			String gzname = (String)object.get("gzname");
			String sl = object.get("sl")==null?"0":object.get("sl").toString();
			String zz = object.get("zz")==null?"0":object.get("zz").toString();
			String jz = object.get("jz")==null?"0":object.get("jz").toString();
			String xtdlname = (String)object.get("xtdlname");
			String zs = (String)object.get("zs");
			String sj = object.get("sj")==null?"0":object.get("sj").toString();
			String fs = (String)object.get("fs");
			String jhcb = object.get("jhcb")==null?"0":object.get("jhcb").toString();
			String cs = (String)object.get("cs");
			String shy = (String)object.get("shy");
			String xsno = (String)object.get("xsno");
			String ssj = object.get("ssj")==null?"0":object.get("ssj").toString();
			String hh = (String)object.get("hh");
			
			TableRow tr = new TableRow();
			if ("小品名".equals(repselect4)) {
				tr.addCell(new TableCell(pm));
			} else if ("系列名称".equals(repselect4)) {
				tr.addCell(new TableCell(xlname));
			} else if ("原编号".equals(repselect4)) {
				tr.addCell(new TableCell(ybh));
			} else if ("按款号".equals(repselect4)) {
				tr.addCell(new TableCell(kh));
			} else if ("按手寸".equals(repselect4)) {
				tr.addCell(new TableCell(sc));
			} else if ("按大类".equals(repselect4)) {
				tr.addCell(new TableCell(xtdlname));
			} else if ("自定大类".equals(repselect4)) {
				tr.addCell(new TableCell(zdydlname));
			} else if ("按柜组".equals(repselect4)) {
				tr.addCell(new TableCell(gzname));
			} else if ("分销商".equals(repselect4)) {
				tr.addCell(new TableCell(fxsname));
			} else if ("按成色".equals(repselect4)) {
				tr.addCell(new TableCell(cs));
			} else if ("售货员".equals(repselect4)) {
				tr.addCell(new TableCell(shy));
			} else if ("单号统计".equals(repselect4)) {
				tr.addCell(new TableCell(xsno));
			} else if ("货号统计".equals(repselect4)) {
				tr.addCell(new TableCell(hh));
			}
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sl),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz), 
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zs));
			tr.addCell(new TableCell(fs));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(ssj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jhcb),
					Rectangle.ALIGN_RIGHT));
			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "首饰销售统计表", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "首饰销售统计表" + currentTimeMillis, report,
				response);
	}
}
