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
 * @version 1.0.0 2012-7-9 ����10:55:00
 * @history
 */
public class ReportX1Action extends AbstractAction {
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

		String forward = "/xreport/xreport4.jsp";
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
		// ��������
		String repstrcompare2_START = request
				.getParameter("repstrcompare2_START");
		String repstrcompare2_END = request.getParameter("repstrcompare2_END");
		// ��������
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

		sb.append(" SELECT ");
		/** ��Ʒ�� չʾ�ֶ� */
		sb.append(" IFNULL(t1.column4,'') pm, ");

		/** �������� չʾ�ֶ� */
		sb.append(" IFNULL(fxs.column3,'') fxsname, ");

		/** ������ չʾ�ֶ� */
		sb.append(" IFNULL(gz.column4,'') gzname, ");

		/** ������ չʾ�ֶ� */
		sb.append(" IFNULL(t.column3,'') jhno, ");

		sb.append(" IFNULL(COUNT(t1.column3),0) sl, ");
		sb
				.append(" SUM(IFNULL(t1.column11,0)) zz,SUM(IFNULL(t1.column12,0)) jz, ");
		sb.append(" IFNULL(t1.column14,'/') zs,IFNULL(t1.column19,'/') fs, ");
		sb.append(" SUM(IFNULL(t1.column5,0)) sj ");
		sb.append(" FROM dyform.DY_661338441749389 t ");
		sb
				.append(" LEFT JOIN dyform.DY_661338441749388 t1 ON t.LSH = t1.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column12 = fxs.column4 ");
		sb
				.append(" LEFT JOIN dyform.DY_271334208897441 rkmx ON  rkmx.column4 = t1.column3 ");
		sb
				.append(" LEFT JOIN dyform.DY_271334208897439 rkd ON rkd.LSH = rkmx.FATHERLSH ");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537510 gz ON gz.column7 = t.column9 ");

		sb.append(" WHERE t.STATUSINFO='01' ");
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
			sb.append(" AND t.column8 >= '" + reptimes1_START.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(reptimes1_END)) {
			sb.append(" AND t.column8 <= '" + reptimes1_END.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(repselect1)) {
			sb.append(" AND t.column12='" + repselect1.trim() + "' ");
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
			sb.append(" AND t1.column4 LIKE '%" + repstrdim4.trim() + "%'  ");
		}
		if (StringUtils.isNotEmpty(repstrdim3)) {
			sb.append(" AND t.column10 like '%" + repstrdim3.trim() + "%' ");
		}
		if ("СƷ��".equals(repselect9)) {
			sb.append(" group by t1.column4 ");
		} else if ("������".equals(repselect9)) {
			sb.append(" group by t.column12 ");
		} else if ("������".equals(repselect9)) {
			sb.append("  group by t.column9 ");
		} else if ("������".equals(repselect9)) {
			sb.append(" group by t.column3 ");
		}
		/**
		 * ��Ʒ�� group by t1.column4 �������� group by t.column12 ������ group by
		 * t.column9 ������ group by t.column3
		 */

		// ���ԭʼ���ݱ��
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();

		if ("СƷ��".equals(repselect9)) {
			headerSet1.add(new TableCell("Ʒ��"));
		} else if ("������".equals(repselect9)) {
			headerSet1.add(new TableCell("������"));
		} else if ("������".equals(repselect9)) {
			headerSet1.add(new TableCell("����"));
		} else if ("������".equals(repselect9)) {
			headerSet1.add(new TableCell("����"));
		}
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("����"));
		headerSet1.add(new TableCell("��ʯ(ct/p)"));
		headerSet1.add(new TableCell("��ʯ"));
		headerSet1.add(new TableCell("�ۼ�"));

		ReportExt reportExt = new ReportExt();

		List list = DbTools.queryData(sb.toString());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();

			String pm = object.get("pm").toString();
			String fxsname = object.get("fxsname").toString();
			String gzname = object.get("gzname").toString();
			String jhno = object.get("jhno").toString();
			String sl = object.get("sl").toString();
			String jz = object.get("jz").toString();
			String zz = object.get("zz").toString();
			String zs = object.get("zs").toString();
			String fs = object.get("fs").toString();
			String sj = object.get("sj").toString();

			TableRow tr = new TableRow();
			if ("СƷ��".equals(repselect9)) {
				tr.addCell(new TableCell(pm));
			} else if ("������".equals(repselect9)) {
				tr.addCell(new TableCell(fxsname));
			} else if ("������".equals(repselect9)) {
				tr.addCell(new TableCell(gzname));
			} else if ("������".equals(repselect9)) {
				tr.addCell(new TableCell(jhno));
			}

			tr.addCell(new TableCell("" + sl, Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + zz));
			tr.addCell(new TableCell("" + jz));
			tr.addCell(new TableCell("" + zs));
			tr.addCell(new TableCell("" + fs));
			tr.addCell(new TableCell("" + sj));

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
