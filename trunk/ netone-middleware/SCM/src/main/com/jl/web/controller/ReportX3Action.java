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
public class ReportX3Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");

		// 分销商信息
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537483  where column19='1'");
		List list = DbTools.queryData(sb.toString());
		request.setAttribute("list", list);

		String forward = "/xreport/xreport6.jsp";
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

		String repstrcompare1_START = request
				.getParameter("repstrcompare1_START");
		String repstrcompare1_END = request.getParameter("repstrcompare1_END");
		String repstrcompare2_START = request
				.getParameter("repstrcompare2_START");
		String repstrcompare2_END = request.getParameter("repstrcompare2_END");
		String reptimes1_START = request.getParameter("reptimes1_START");
		String reptimes1_END = request.getParameter("reptimes1_END");
		String repselect1 = request.getParameter("repselect1");
		String repselect2 = request.getParameter("repselect2");
		String repselect3 = request.getParameter("repselect3");
		String repstrdim3 = request.getParameter("repstrdim3");
		String repstrdim4 = request.getParameter("repstrdim4");
		String repselect4 = request.getParameter("repselect4");
		String repselect5 = request.getParameter("repselect5");
		String repselect6 = request.getParameter("repselect6");
		String repselect7 = request.getParameter("repselect7");
		String repstrdim5 = request.getParameter("repstrdim5");
		String repselect8 = request.getParameter("repselect8");
		String repselect9 = request.getParameter("repselect9");

		StringBuffer sb = new StringBuffer();

		/** 首饰销退统计表 */
		sb.append(" SELECT  ");
		/** 小品名 */
		sb.append(" IFNULL(t1.column20,'') pm, ");
		/** 分销商 */
		sb.append(" IFNULL(fxs.column3,'') fxsname, ");
		/** 按单号 */
		sb.append(" IFNULL(t.column3,'') xtno, ");

		sb.append(" IFNULL(COUNT(t1.column3),0) sl, ");
		sb
				.append(" IFNULL(SUM(fxrh.column11),0) zz,IFNULL(SUM(fxrh.column12),0) jz, ");
		sb
				.append(" IFNULL(fxrh.column14,'/') zs,IFNULL(fxrh.column19,'/') fs, ");
		sb
				.append(" IFNULL(SUM(t1.column12),0) ssj,IFNULL(SUM(ssxs.column12),0) zkr, ");
		sb.append(" IFNULL(SUM(t1.column13),0) xtj, ");
		sb.append(" IFNULL(t.column8,'') fxsno ");
		sb.append(" FROM dyform.DY_621338820565627 t ");
		sb
				.append(" LEFT JOIN dyform.DY_621338820565625 t1 ON t.LSH = t1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4 ");
		sb
				.append(" LEFT JOIN dyform.DY_371337952339238 ssxs ON ssxs.column3 = t1.column3 ");
		sb
				.append(" LEFT JOIN dyform.DY_661338441749388 fxrh ON fxrh.column3 = t1.column3 ");
		sb
				.append(" LEFT JOIN dyform.DY_271334208897441 rkmx ON  rkmx.column4 = t1.column3 ");
		sb
				.append(" LEFT JOIN dyform.DY_271334208897439 rkd ON rkd.LSH = rkmx.FATHERLSH ");
		sb.append(" WHERE t.STATUSINFO = '01' ");
		if (StringUtils.isNotEmpty(repstrcompare1_START)) {
			sb.append(" AND t1.column3 >= '" + repstrcompare1_START.trim()
					+ "' ");
		}
		if (StringUtils.isNotEmpty(repstrcompare1_END)) {
			sb
					.append(" AND t1.column3 <= '" + repstrcompare1_END.trim()
							+ "' ");
		}
		if (StringUtils.isNotEmpty(repstrcompare2_START)) {
			sb.append(" AND t.column3>='" + repstrcompare2_START.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repstrcompare2_END)) {
			sb.append(" AND t.column3<='" + repstrcompare2_END.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(reptimes1_START)) {
			sb.append(" AND t.column4 >= '" + reptimes1_START.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(reptimes1_END)) {
			sb.append(" AND t.column4 <= '" + reptimes1_END.trim() + "' ");
		}

		if (StringUtils.isNotEmpty(repselect1)) {
			sb.append(" AND t.column8 = '" + repselect1.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect2)) {
			sb.append(" AND rkd.column8='" + repselect2.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect3)) {
			sb.append(" AND rkmx.column50='" + repselect3.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repstrdim3)) {
			sb.append(" AND ssxs.column12 = '" + repstrdim3.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repstrdim4)) {
			sb.append(" AND ssxs.column13 = '" + repstrdim4.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect4)) {
			sb.append(" AND t1.column23 = '" + repselect4.trim() + "'  ");
		}
		if (StringUtils.isNotEmpty(repselect5)) {
			sb.append(" AND rkmx.column48='" + repselect5.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect6)) {
			sb.append(" AND t1.column17 = '" + repselect6.trim() + "'  ");
		}
		if (StringUtils.isNotEmpty(repselect7)) {
			sb.append(" AND rkmx.column52 = '" + repselect7.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repstrdim5)) {
			sb.append(" AND t1.column20= '" + repstrdim5.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect8)) {
			sb.append(" AND t.column10= '" + repselect8.trim() + "' ");
		}

		if ("小品名".equals(repselect9)) {
			sb.append(" GROUP BY t1.column20 ");
		} else if ("分销商".equals(repselect9)) {
			sb.append(" GROUP BY t.column8 ");
		} else if ("按单号".equals(repselect9)) {
			sb.append(" GROUP BY t.column3 ");
		}

		// 获得原始数据表格
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("小品名".equals(repselect9)) {
			headerSet1.add(new TableCell("品名"));
		} else if ("分销商".equals(repselect9)) {
			headerSet1.add(new TableCell("分销商"));
		} else if ("按单号".equals(repselect9)) {
			headerSet1.add(new TableCell("单号"));
		}
		headerSet1.add(new TableCell("数量"));
		headerSet1.add(new TableCell("总重"));
		headerSet1.add(new TableCell("金重"));
		headerSet1.add(new TableCell("主石(ct/p)"));
		headerSet1.add(new TableCell("副石(ct/p)"));
		headerSet1.add(new TableCell("售价"));

		ReportExt reportExt = new ReportExt();

		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();

			String pm = object.get("pm").toString();
			String fxsname = object.get("fxsname").toString();
			String ttno = (String) object.get("ttno");
			String sl = (String) object.get("sl").toString();
			String zz = (String) object.get("zz").toString();
			String jz = (String) object.get("jz").toString();
			String zs = (String) object.get("zs").toString();
			String fs = (String) object.get("fs").toString();
			String sj = (String) object.get("sj");

			TableRow tr = new TableRow();
			if ("小品名".equals(repselect9)) {
				tr.addCell(new TableCell(pm));
			} else if ("分销商".equals(repselect9)) {
				tr.addCell(new TableCell(fxsname));
			} else if ("按单号".equals(repselect9)) {
				tr.addCell(new TableCell(ttno));
			}

			tr.addCell(new TableCell("" + MathHelper.moneyFormat(sl),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(zz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(jz),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell(zs));
			tr.addCell(new TableCell(fs));
			tr.addCell(new TableCell(sj));

			t.addRow(tr);
		}

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		reportExt.setTitleHeader(report, "分销调退统计表", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "分销调退统计表" + currentTimeMillis, report,
				response);
	}
}
