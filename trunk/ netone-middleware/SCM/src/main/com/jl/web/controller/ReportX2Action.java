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
 * ��������ͳ�Ʊ�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-7-10 ����10:58:41
 * @history
 */
public class ReportX2Action extends AbstractAction {
	public ActionForward querymain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("endTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		request.setAttribute("beginTime", TimeUtil.formatDate(new Date(),
				"yyyy-MM")
				+ "-01");

		// ��������Ϣ
		request.setAttribute("list", ReportBaseData.getFClientInfo());

		String forward = "/xreport/xreport5.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��ӡ �鱦 �������۴�ӡ
	public void query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String format = request.getParameter("format");
		// ������
		String repstrcompare1_START = request
				.getParameter("repstrcompare1_START");
		String repstrcompare1_END = request.getParameter("repstrcompare1_END");
		// �˻�����
		String repstrcompare2_START = request
				.getParameter("repstrcompare2_START");
		String repstrcompare2_END = request.getParameter("repstrcompare2_END");
		// �˻�����
		String reptimes1_START = request.getParameter("reptimes1_START");
		String reptimes1_END = request.getParameter("reptimes1_END");
		// ������
		String repselect1 = request.getParameter("repselect1");
		// ��Ӧ��
		String repselect2 = request.getParameter("repselect2");
		// ��ӪƷ��
		String repselect3 = request.getParameter("repselect3");
		// ��Ʒ����
		String repselect4 = request.getParameter("repselect4");
		// �Զ������
		String repselect5 = request.getParameter("repselect5");
		// ��ɫ
		String repselect6 = request.getParameter("repselect6");
		// ��ʯ
		String repselect7 = request.getParameter("repselect7");
		// Ʒ��
		String repstrdim4 = request.getParameter("repstrdim4");
		// ��ע
		String repstrdim3 = request.getParameter("repstrdim3");
		// ͳ������
		String repselect9 = request.getParameter("repselect9");

		StringBuffer sb = new StringBuffer();

		/** ��������ͳ�Ʊ� */
		sb.append(" SELECT  ");

		if ("СƷ��".equals(repselect9)) {
			sb.append(" IFNULL(t1.column24,'') pm, ");
		} else if ("������".equals(repselect9)) {
			sb.append(" IFNULL(fxs.column3,'') fxsname, ");
		} else if ("������".equals(repselect9)) {
			sb.append(" IFNULL(t.column3,'') ttno, ");
		}

		sb.append(" IFNULL(COUNT(t1.column3),0) sl, ");
		sb
				.append(" IFNULL(SUM(IFNULL(t1.column12,0)),0) zz,IFNULL(SUM(IFNULL(t1.column13,0)),0) jz, ");
		sb.append(" IFNULL(t1.column15,'/') zs,IFNULL(t1.column20,'/') fs, ");
		sb.append(" IFNULL(t1.column5,0) sj, ");
		sb.append(" IFNULL(t.column10,'') note, ");
		sb.append(" IFNULL(t1.column8,'') fxsno ");
		sb.append(" FROM dyform.DY_661338441749392 t ");
		sb
				.append(" LEFT JOIN dyform.DY_661338441749391 t1 ON t.LSH = t1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4 ");
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
			sb.append(" AND t.column3 >= '" + repstrcompare2_START.trim()
					+ "' ");
		}
		if (StringUtils.isNotEmpty(repstrcompare2_END)) {
			sb.append(" AND t.column3 <= '" + repstrcompare2_END.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(reptimes1_START)) {
			sb.append(" AND t.column4 >= '" + reptimes1_START.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(reptimes1_END)) {
			sb.append(" AND t.column4 <= '" + reptimes1_END.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect1)) {
			sb.append(" AND t.column8='" + repselect1.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect2)) {
			sb.append(" AND rkd.column8='" + repselect2.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect3)) {
			sb.append(" AND rkmx.column50='" + repselect3.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect4)) {
			sb.append(" AND t1.column8 = '" + repselect4.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect5)) {
			sb.append(" AND rkmx.column48='" + repselect5.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect6)) {
			sb.append(" AND rkmx.column52='" + repselect6.trim() + "'  ");
		}
		if (StringUtils.isNotEmpty(repselect7)) {// ��ʯ
		}
		if (StringUtils.isNotEmpty(repstrdim4)) {
			sb.append(" AND t1.column24 LIKE '%" + repstrdim4.trim() + "%'  ");
		}
		if (StringUtils.isNotEmpty(repstrdim3)) {
			sb.append(" AND t.column10 like '%" + repstrdim3.trim() + "%' ");
		}
		if ("СƷ��".equals(repselect9)) {
			sb.append(" group by t1.column24 ");
		} else if ("������".equals(repselect9)) {
			sb.append(" group by t1.column8 ");
		} else if ("������".equals(repselect9)) {
			sb.append(" group by t.column3 ");
		}

		// ���ԭʼ���ݱ��
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("СƷ��".equals(repselect9)) {
			headerSet1.add(new TableCell("Ʒ��"));
		} else if ("������".equals(repselect9)) {
			headerSet1.add(new TableCell("������"));
		} else if ("������".equals(repselect9)) {
			headerSet1.add(new TableCell("����"));
		}
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("��ʯ(ct/p)"));
		headerSet1.add(new TableCell("��ʯ(ct/p)"));
		headerSet1.add(new TableCell("�ۼ�"));

		ReportExt reportExt = new ReportExt();

		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();

			String pm = (String) object.get("pm");
			String fxsname = (String) object.get("fxsname");
			String ttno = (String) object.get("ttno");
			String sl = (String) object.get("sl").toString();
			String zz = (String) object.get("zz");
			String jz = (String) object.get("jz");
			String zs = (String) object.get("zs");
			String fs = (String) object.get("fs");
			String sj = (String) object.get("sj");

			TableRow tr = new TableRow();
			if ("СƷ��".equals(repselect9)) {
				tr.addCell(new TableCell(pm));
			} else if ("������".equals(repselect9)) {
				tr.addCell(new TableCell(fxsname));
			} else if ("������".equals(repselect9)) {
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
		reportExt.setTitleHeader(report, "��������ͳ�Ʊ�", null, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "��������ͳ�Ʊ�" + currentTimeMillis, report,
				response);
	}

}
