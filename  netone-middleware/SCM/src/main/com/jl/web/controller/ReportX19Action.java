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
public class ReportX19Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");
		String forward = "/xreport/xreport19.jsp";
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
		// 客户名称
		String repstrdim3 = request.getParameter("repstrdim3");
		// 客户卡号
		String repstrdim13 = request.getParameter("repstrdim13");
		// 手机号码
		String repstrdim4 = request.getParameter("repstrdim4");
		//用户
		String yh = request.getParameter("yh");
		StringBuffer sb = new StringBuffer();

		/** 首饰销售明细表 */
		sb.append("SELECT '' timex,'' belongx, ");
		sb.append("IFNULL(t.column3,'') 'ssdh',"
				+ "IFNULL(t.column4,'-') 'ssdate', ");
		sb.append("IFNULL(t.column8,'') 'fxsno',"
				+ "IFNULL(fxs.column3,'') 'fxsname', ");
		sb.append("IFNULL(t1.column3,'') 'txm',"
				+ "IFNULL(t1.column4,'') 'pm', ");
		sb.append("IFNULL(t1.column20,'') 'kh',"
				+ "IFNULL(t1.column21,'') 'zsh', ");
		sb.append("IFNULL(fxrh.column13,'') 'sc',"
				+ "IFNULL(fxrh.column11,0) 'zz',"
				+ "IFNULL(fxrh.column12,0) 'jz', ");
		sb.append("IFNULL(ssrkmx.column24,'') 'zhushigg', ");
		sb.append("IFNULL(t1.column23,'') 'hh', ");
		sb.append("IFNULL(fxrh.column14,'/') 'zhushi',"
				+ "IFNULL(fxrh.column19,'/') 'fushi', ");
		sb.append("IFNULL(fxrh.column16,'') 'yanse',"
				+ "IFNULL(fxrh.column17,'') 'jd',"
				+ "IFNULL(fxrh.column23,'') 'cg', ");
		sb.append("IFNULL(t1.column11,0) 'sj',"
				+ "IFNULL(t1.column12,0) 'zkr',"
				+ "IFNULL(t1.column15,0) 'ssj', ");
		sb.append("IFNULL(t1.column16,'') 'xsxbz',"
				+ "IFNULL(fxrh.column20,'') 'cpbz', ");
		sb.append("IFNULL(ssrk.column8,'') 'gys',");
		sb.append("IFNULL(t1.column33,'') 'dyjf',");
		sb.append("IFNULL(t1.column34,'') 'dkje',");
		sb.append("IFNULL(khxx.column6,'') 'khsr',");
		sb.append("IFNULL(ssrkmx.column50,'') 'pinpai',"
				+ "IFNULL(t.column12,'') 'khname', ");
		sb.append("IFNULL(t.column13,'') 'hycard',"
				+ "IFNULL(t1.column17,'') 'xssx',"
				+ "IFNULL(ssrkmx.column48,'') 'zdydl', ");
		sb.append("IFNULL(t1.column19,'') 'cpdl',"
				+ "IFNULL(ssrkmx.column52,'') 'cs',"
				+ "IFNULL(ssrkmx.column7,'') 'zsname', ");
		sb.append("IFNULL(t.column10,'') 'shy',"
				+ "IFNULL(t.column11,'') 'dmjl',"
				+ "IFNULL(t.column9,'') 'sybz', ");
		sb.append("IFNULL(fxrhd.column9,'') 'xsgz',"
				+ "IFNULL(t.column16,'') 'bz', "
				+ "IFNULL(khxx.column3,'') 'khxm', "
				+ "IFNULL(khxx.column27,'') 'jfkh', "
				+ "IFNULL(khxx.column16,'') 'yddh1', "
				+ "IFNULL(khxx.column17,'') 'yddh2' ");
		// 首饰销售单
		sb.append("FROM dyform.DY_371337952339241 t ");
		// 销售明细
		sb
				.append("LEFT JOIN dyform.DY_371337952339238 t1 ON t.LSH = t1.FATHERLSH ");
		sb
				.append("LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4 ");
		sb
				.append("LEFT JOIN dyform.DY_661338441749388 fxrh ON t1.column3 = fxrh.column3 ");
		sb
				.append("LEFT JOIN dyform.DY_661338441749389 fxrhd ON fxrhd.LSH=fxrh.FATHERLSH ");
		sb
				.append("LEFT JOIN dyform.DY_271334208897441 ssrkmx ON  ssrkmx.column4 = t1.column3 ");
		sb
				.append("LEFT JOIN dyform.DY_271334208897439 ssrk ON ssrk.LSH = ssrkmx.FATHERLSH ");
		sb
				.append("LEFT JOIN dyform.DY_131337490209098 khxx ON khxx.column27 = t.column13 ");
		sb
				.append("WHERE t.STATUSINFO='01' AND t1.STATUSINFO='01' AND fxrh.STATUSINFO = '03' AND t.column8 in (SELECT column4 FROM dyform.DY_61336130537483 WHERE column21 LIKE '%"+ yh  +"[%')");
		if (StringUtils.isNotEmpty(repstrdim3))
			sb.append(" AND t.column12 like '%" + repstrdim3 + "%' ");
		if (StringUtils.isNotEmpty(repstrdim13))
			sb.append(" AND t.column13 like '%" + repstrdim13 + "%' ");
		if (StringUtils.isNotEmpty(repstrdim4))
			sb.append(" AND (khxx.column16 like '%" + repstrdim4
					+ "%' or khxx.column17 like '%" + repstrdim4 + "%')");

		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();
		headerSet1.add(new TableCell("客户姓名"));
		headerSet1.add(new TableCell("积分卡号"));
		headerSet1.add(new TableCell("客户生日"));
		headerSet1.add(new TableCell("移动电话1"));
		headerSet1.add(new TableCell("移动电话2"));
		headerSet1.add(new TableCell("货号"));
		headerSet1.add(new TableCell("条形码"));
		headerSet1.add(new TableCell("品名"));
		headerSet1.add(new TableCell("款号"));
		headerSet1.add(new TableCell("证书号"));
		headerSet1.add(new TableCell("手寸"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("颜色"));
		headerSet1.add(new TableCell("净度"));
		headerSet1.add(new TableCell("车工"));
		headerSet1.add(new TableCell("售价"));
		headerSet1.add(new TableCell("折扣率"));
		headerSet1.add(new TableCell("实售价"));
		headerSet1.add(new TableCell("抵用积分"));
		headerSet1.add(new TableCell("抵扣金额"));
		headerSet1.add(new TableCell("销售小备注"));
		headerSet1.add(new TableCell("产品备注"));

		ReportExt reportExt = new ReportExt();
		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String pm = (String) object.get("pm");
			String hh = (String) object.get("hh");
			String txm = (String) object.get("txm");
			String kh = (String) object.get("kh");
			String zsh = (String) object.get("zsh");
			String sc = (String) object.get("sc");
			String yanse = (String) object.get("yanse");
			String jz = object.get("jz") == null ? "0" : object.get("jz")
					.toString();
			String jd = (String) object.get("jd");
			String cg = (String) object.get("cg");
			String sj = object.get("sj") == null ? "0" : object.get("sj")
					.toString();
			String zkr = (String) object.get("zkr");
			String ssj = object.get("ssj") == null ? "0" : object.get("ssj")
					.toString();
			String xsxbz = (String) object.get("xsxbz");
			String bz = (String) object.get("bz");
			String khxm = (String) object.get("khxm");
			String jfkh = (String) object.get("jfkh");
			String yddh1 = (String) object.get("yddh1");
			String yddh2 = (String) object.get("yddh2");

			String khsr = (String) object.get("khsr");
			String dyjf = (String) object.get("dyjf");
			String dkje = (String) object.get("dkje");

			TableRow tr = new TableRow();
			tr.addCell(new TableCell(khxm));
			tr.addCell(new TableCell(jfkh));
			tr.addCell(new TableCell(khsr));
			tr.addCell(new TableCell(yddh1));
			tr.addCell(new TableCell(yddh2));
			tr.addCell(new TableCell(hh));
			tr.addCell(new TableCell(txm));
			tr.addCell(new TableCell(pm));
			tr.addCell(new TableCell(kh));
			tr.addCell(new TableCell(zsh));
			tr.addCell(new TableCell(sc));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(yanse));
			tr.addCell(new TableCell(jd));
			tr.addCell(new TableCell(cg));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zkr));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(ssj),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(dyjf),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(dkje),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(xsxbz));
			tr.addCell(new TableCell(bz));
			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "客户消费记录查询", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "客户消费记录查询" + currentTimeMillis, report,
				response);
	}
}
