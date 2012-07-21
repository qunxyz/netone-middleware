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
public class ReportX13Action extends AbstractAction {
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
		request.setAttribute("list_ChangPingDaLei", ReportBaseData.getChangPingDaLeiInfo());
		request.setAttribute("list_ChengSe", ReportBaseData.getChengSeXingXiInfo());
		request.setAttribute("list_BaoShi", ReportBaseData.getBaoShiMingChengInfo());
		request.setAttribute("list_PingMing", ReportBaseData.getChangPingMingChengInfo());
		
		String forward = "/xreport/xreport13.jsp";
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
		String repselect4 = request.getParameter("repselect4");
		String repselect5 = request.getParameter("repselect5");
		String repselect6 = request.getParameter("repselect6");
		String repselect7 = request.getParameter("repselect7");
		StringBuffer sb = new StringBuffer();

		/** 总库库存明细 */
		sb.append("SELECT  ");
		sb.append("IFNULL(t.column3,'') rkno,IFNULL(t.column6,'') rkdate,IFNULL(t.column8,'') gys,IFNULL(t3.column3,'') gysname, ");
		sb.append("IFNULL(t2.column4,'') txm,IFNULL(t2.column7,'') pm,IFNULL(t2.column11,'') kh,IFNULL(t2.column12,'') zsh, ");
		sb.append("IFNULL(t2.column13,'') ybh,IFNULL(t2.column24,'') sc,IFNULL(t2.column16,0) zz,IFNULL(t2.column17,0) jz, ");
		sb.append("IFNULL(t2.column36,'') zsh,IFNULL(t2.column37,'/') zs,IFNULL(t2.column65,'') fsname,IFNULL(t2.column66,'/') fs, ");
		sb.append("IFNULL(sxz.column4,'') xz,IFNULL(sys.column4,'') ys,IFNULL(sjd.column4,'') jd,IFNULL(scg.column4,'') qg, ");
		sb.append("IFNULL(t2.column31,0) jhcb,IFNULL(t2.column71,0) zscb,IFNULL(t2.column32,0) rksccb, ");
		sb.append("IFNULL(t2.column33,0) bl,IFNULL(t2.column34,0) rksj,IFNULL(t.column13,'') rkbz ");

		sb.append("FROM dyform.DY_271334208897439 t ");
		sb.append("LEFT JOIN dyform.DY_271334208897441 t2 ON t2.FATHERLSH=t.LSH ");
		sb.append("LEFT JOIN dyform.DY_941335942247762 t3 ON t3.column4= t.column8 ");
		sb.append("LEFT JOIN dyform.DY_521339922112143 t4 ON t4.column8 = t.column14 ");
		sb.append("LEFT JOIN dyform.DY_381336140843575 t5 ON t5.column8 = t2.column48 ");
		sb.append("	LEFT JOIN dyform.DY_191339776365900 sxz ON sxz.column5 = t2.column58 ");
		sb.append("LEFT JOIN dyform.DY_191339776365900 sys ON sys.column5 = t2.column55 ");
		sb.append("	LEFT JOIN dyform.DY_191339776365900 sjd ON sjd.column5 = t2.column56 ");
		sb.append("LEFT JOIN dyform.DY_191339776365900 scg ON scg.column5 = t2.column57 ");
		sb.append("WHERE (t.STATUSINFO = '01' AND  t2.STATUSINFO = '01') ");
		if(StringUtils.isNotEmpty(repstrcompare1_START))
		    sb.append(" AND t2.column4= '" + repstrcompare1_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_END))
		    sb.append(" AND t2.column4= '" + repstrcompare1_END + "' "); 
		if(StringUtils.isNotEmpty(repstrcompare1_START))
		    sb.append(" AND t.column3= '" + repstrcompare2_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_END))
		    sb.append(" AND t.column3= '" + repstrcompare2_END + "' "); 
		if(StringUtils.isNotEmpty(reptimes1_START))
		    sb.append("AND t1.column6 >= '" + reptimes1_START + "' ");
		if(StringUtils.isNotEmpty(reptimes1_END))
		    sb.append(" AND t1.column6 <= '" + reptimes1_END + "' ");
		if(StringUtils.isNotEmpty(repselect2))
				sb.append("AND t.column8 = '" + repselect2 + "' ");
		if(StringUtils.isNotEmpty(repselect4))
				sb.append("AND t2.column50 = '" + repselect4 + "' ");
		if(StringUtils.isNotEmpty(repselect5))
				sb.append("AND t.column14 = '" + repselect5 + "' "); 
		if(StringUtils.isNotEmpty(repselect6))
				sb.append("AND t2.column52 = '" + repselect6 + "' ");
		if(StringUtils.isNotEmpty(repselect7))
				sb.append("AND t2.column7 LIKE '" + repselect7 + "' ");
				sb.append("ORDER BY rkno,rkdate ");
		
		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("总库明细".equals(repselect1)) {
			headerSet1.add(new TableCell("总库明细"));
		} else if ("任意排序".equals(repselect1)) {
			headerSet1.add(new TableCell("任意排序"));
		} else if ("总库图片".equals(repselect1)) {
			headerSet1.add(new TableCell("总库图片"));
		
		}
		headerSet1.add(new TableCell("品名"));
		headerSet1.add(new TableCell("款号"));
		headerSet1.add(new TableCell("证书号"));
		headerSet1.add(new TableCell("原编号"));
		headerSet1.add(new TableCell("手寸"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("总重"));
		headerSet1.add(new TableCell("主石号"));
		headerSet1.add(new TableCell("主石规格"));
		headerSet1.add(new TableCell("主石(ct/p)"));
		headerSet1.add(new TableCell("副石名称"));
		headerSet1.add(new TableCell("副石(ct/p)"));
		headerSet1.add(new TableCell("形状"));
		headerSet1.add(new TableCell("颜色"));
		headerSet1.add(new TableCell("净度"));
		headerSet1.add(new TableCell("车工"));
		headerSet1.add(new TableCell("进货成本"));
		headerSet1.add(new TableCell("真实成本"));
		headerSet1.add(new TableCell("市场成本"));
		headerSet1.add(new TableCell("倍率"));
		headerSet1.add(new TableCell("售价"));
		headerSet1.add(new TableCell("产品备注"));

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
			String zscb = object.get("zscb").toString();
			String sccb = object.get("sccb").toString();
			String bl = object.get("bl").toString();
			String cpbz = object.get("cpbz").toString();
			String zsh = object.get("zsh").toString();
			String ys = object.get("ys").toString();
			String xz = object.get("xz").toString();
			String jd = object.get("jd").toString();
			String qg = object.get("qg").toString();
			
			TableRow tr = new TableRow();
			if ("总库明细".equals(repselect1)) {
				tr.addCell(new TableCell("总库明细"));
			} else if ("任意排序".equals(repselect1)) {
				tr.addCell(new TableCell("任意排序"));
			} else if ("总库图片".equals(repselect1)) {
				tr.addCell(new TableCell("总库图片"));
			}
			
			tr.addCell(new TableCell(pm));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(kh),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zsh),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(ybh),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(sc));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zsh),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zs));
			tr.addCell(new TableCell(fxsname));
			tr.addCell(new TableCell(fs));
			tr.addCell(new TableCell(xz));
			tr.addCell(new TableCell(ys));
			tr.addCell(new TableCell(jd));
			tr.addCell(new TableCell(qg));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jhcb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zscb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sccb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(bl),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(cpbz));
			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "总库库存明细表", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "总库库存明细表" + currentTimeMillis, report,
				response);
	}
}
