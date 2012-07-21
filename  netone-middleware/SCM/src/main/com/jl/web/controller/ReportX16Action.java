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
 * 分销库存明细表
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-7-10 上午10:58:41
 * @history
 */
public class ReportX16Action extends AbstractAction {
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
		
		String forward = "/xreport/xreport16.jsp";
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

		/** 分销库存明细表 */
		sb.append("SELECT ");
		sb.append("IFNULL(t.column3,'') fhno,IFNULL(t.column8,'-') fhdate,IFNULL(fxs.column3,'') fxsname, ");
		sb.append("IFNULL(t1.column3,'') txm,IFNULL(t1.column4,'') pm,IFNULL(t1.column8,'') kh, ");
		sb.append("IFNULL(t1.column9,'') zsh,IFNULL(t1.column13,'') sc, ");
		sb.append("IFNULL(t1.column12,0) jz,IFNULL(t1.column11,0) zz, ");
		sb.append("IFNULL(t1.column14,'/') zs,IFNULL(t1.column19,'/') fs, ");
		sb.append("IFNULL(sys.column4,'') ys,IFNULL(sjd.column4,'') jd,IFNULL(scg.column4,'') cg, ");
		sb.append("IFNULL(t1.column5,0) sj ");
		sb.append("	FROM dyform.DY_661338441749389 t ");
		sb.append("LEFT JOIN dyform.DY_661338441749388 t1 ON t.LSH = t1.FATHERLSH ");
		sb.append("LEFT JOIN dyform.DY_61336130537483 fxs ON t.column12 = fxs.column4 ");
		sb.append("LEFT JOIN dyform.DY_191339776365900 sys ON sys.column5 = t1.column16 ");
		sb.append("LEFT JOIN dyform.DY_191339776365900 sjd ON sjd.column5 = t1.column17 ");
		sb.append("LEFT JOIN dyform.DY_191339776365900 scg ON scg.column5 = t1.column23 ");
		sb.append("LEFT JOIN dyform.DY_271334208897441 rkmx ON rkmx.column3 = t1.column3 ");
		sb.append("LEFT JOIN dyform.DY_271334208897439 rkd ON rkd.LSH= rkmx.FATHERLSH ");
		sb.append("WHERE (t.STATUSINFO = '01' AND t1.STATUSINFO = '01') ");
		if(StringUtils.isNotEmpty(repstrcompare1_START))
			sb.append(" AND t1.column3 >= '" + repstrcompare1_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare1_END))
			sb.append(" AND t1.column3 <= '" + repstrcompare1_END + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_START))
			sb.append(" AND t.column3 >= '" + repstrcompare2_START + "' ");
		if(StringUtils.isNotEmpty(repstrcompare2_END))
			sb.append(" AND t.column3 <= '" + repstrcompare2_END + "' ");
		if(StringUtils.isNotEmpty(reptimes1_START))
			sb.append(" AND t.column8 >= '" + reptimes1_START + "' ");
		if(StringUtils.isNotEmpty(reptimes1_END))
			sb.append(" AND t.column8 <= '" + reptimes1_END + "' ");
		if(StringUtils.isNotEmpty(repselect2))
		sb.append("AND t.column12 = '" + repselect2 + "' ");
		if(StringUtils.isNotEmpty(repselect3))
		sb.append("AND rkd.column8 = '" + repselect3 + "' ");
		if(StringUtils.isNotEmpty(repselect4))
		sb.append("AND rkmx.column50 = '" + repselect4 + "' ");
		if(StringUtils.isNotEmpty(repselect5))
		sb.append("AND t1.column9 = '" + repselect5 + "' ");  
		if(StringUtils.isNotEmpty(repselect6))
		sb.append("AND rkmx.column48='" + repselect6 + "' ");
		if(StringUtils.isNotEmpty(repselect7))
		sb.append("AND t1.column22 = '" + repselect7 + "' "); 
		if(StringUtils.isNotEmpty(repselect8))
		sb.append("AND rkmx.column52 = '" + repselect8 + "' ");
		if(StringUtils.isNotEmpty(repselect9))
		sb.append("AND rkmx.column81 = '" + repselect9 + "' "); 
		if(StringUtils.isNotEmpty(repselect10))
		sb.append("AND t1.column4 LIKE '%" + repselect10 + "%' ");
		sb.append("ORDER BY fhno,fhdate");
		
		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("分库明细".equals(repselect1)) {
			headerSet1.add(new TableCell("分库明细"));
		} else if ("任意排序".equals(repselect1)) {
			headerSet1.add(new TableCell("任意排序"));
		} else if ("按款号".equals(repselect1)) {
			headerSet1.add(new TableCell("按款号"));
		} else if ("分库图片".equals(repselect1)) {
			headerSet1.add(new TableCell("分库图片"));
		}
		headerSet1.add(new TableCell("品名"));
		headerSet1.add(new TableCell("款号"));
		headerSet1.add(new TableCell("证书号"));
		headerSet1.add(new TableCell("手寸"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("总重"));
		headerSet1.add(new TableCell("主石(ct/p)"));
		headerSet1.add(new TableCell("副石(ct/p)"));
		headerSet1.add(new TableCell("颜色"));
		headerSet1.add(new TableCell("净度"));
		headerSet1.add(new TableCell("车工"));
		headerSet1.add(new TableCell("售价"));

		ReportExt reportExt = new ReportExt();
		System.out.println(sb);
		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String pm = (String)object.get("pm");
			String kh = (String)object.get("kh");
			String sc = (String)object.get("sc");		
			String zz = object.get("zz")==null?"0":object.get("zz").toString();
			String jz = object.get("zz")==null?"0":object.get("jz").toString();	
			String zs = (String)object.get("zs");
			String sj = object.get("sj")==null?"0":object.get("sj").toString();
			String fs = (String)object.get("fs").toString();
			String cg = (String)object.get("cg").toString();
			String zsh = (String)object.get("zsh").toString();
			String ys = (String)object.get("ys").toString();
			String jd = (String)object.get("jd").toString();
			
			TableRow tr = new TableRow();
			if ("分库明细".equals(repselect1)) {
				tr.addCell(new TableCell("分库明细"));
			} else if ("任意排序".equals(repselect1)) {
				tr.addCell(new TableCell("任意排序"));
			} else if ("分库图片".equals(repselect1)) {
				tr.addCell(new TableCell("分库图片"));
			
			}
			tr.addCell(new TableCell(pm));
			tr.addCell(new TableCell(kh));
			tr.addCell(new TableCell(zsh));
			tr.addCell(new TableCell(sc));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zs));
			tr.addCell(new TableCell(fs));
			tr.addCell(new TableCell(ys));
			tr.addCell(new TableCell(jd));
			tr.addCell(new TableCell(cg));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
		
			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "分销库存明细表", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "分销库存明细表" + currentTimeMillis, report,
				response);
	}
}
