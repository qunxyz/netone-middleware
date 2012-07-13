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

public class ReportXAction extends AbstractAction {
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

		String forward = "/xreport/xreport3.jsp";
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
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String clientId = request.getParameter("clientId");
		StringBuffer sb = new StringBuffer();

		sb.append(" select");
		sb
				.append(" IFNULL(t.column8,'') 'fxsno',IFNULL(fxs.column3,'') 'fxsname', ");
		sb.append(" SUM(IFNULL(t1.column15,0)) dpxse, ");
		sb.append(" SUM(IFNULL(rkmx.column31,0)) dpcgcb, ");

		sb
				.append(" IFNULL(SUM(IFNULL(t1.column15,0)) - SUM(IFNULL(rkmx.column31,0)),0) mlr  ");
		sb.append(" FROM dyform.DY_371337952339241 t ");
		sb
				.append(" LEFT JOIN dyform.DY_371337952339238 t1 ON t.LSH = t1.FATHERLSH");
		sb
				.append(" LEFT JOIN dyform.DY_61336130537483 fxs ON t.column8 = fxs.column4");
		sb
				.append(" LEFT JOIN dyform.DY_271334208897441 rkmx ON t1.column3 = rkmx.column4");
		sb.append(" WHERE t.STATUSINFO='01' ");
		if (StringUtils.isNotEmpty(beginDate)) {
			sb.append(" and t.column4>='" + beginDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(endDate)) {
			sb.append(" and t.column4<='" + endDate.trim() + "' ");
		}
		if (StringUtils.isNotEmpty(clientId)) {
			sb.append(" and t.column8='" + clientId.trim() + "' ");
		}
		sb.append(" GROUP BY fxsno ");
		sb.append("ORDER BY fxsno");

		System.out.println("SQL:" + sb.toString());

		// ���ԭʼ���ݱ��
		Table t = new Table();

		List<TableCell> headerSet1 = new ArrayList();
		headerSet1.add(new TableCell("�����̱���"));
		headerSet1.add(new TableCell("����������"));
		headerSet1.add(new TableCell("��Ʒ���۽��"));
		headerSet1.add(new TableCell("��Ʒ�ɹ��ɱ�"));
		headerSet1.add(new TableCell("ë����"));

		ReportExt reportExt = new ReportExt();

		/**
		 * fxsno �����̱��� fxsname ���������� zcje ֧����� zrje ֧���� yue ��� zcbnlj ����֧������ۼ�
		 * bnzrjelj ����֧�����ۼ�
		 */
		List list = DbTools.queryData(sb.toString());
		double[] tmpdata = new double[3];
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String fxsno = object.get("fxsno").toString();// �����̱���
			String fxsname = object.get("fxsname").toString();// ����������
			String dpxse = object.get("dpxse").toString();// ��Ʒ���۽��
			String dpcgcb = object.get("dpcgcb").toString();// ��Ʒ�ɹ��ɱ�
			String mlr = object.get("mlr").toString();// ë����

			TableRow tr = new TableRow();

			tr.addCell(new TableCell(fxsno));
			tr.addCell(new TableCell(fxsname));

			tr.addCell(new TableCell("" + MathHelper.moneyFormat(dpxse),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(dpcgcb),
					Rectangle.ALIGN_RIGHT));
			tr.addCell(new TableCell("" + MathHelper.moneyFormat(mlr),
					Rectangle.ALIGN_RIGHT));

			t.addRow(tr);

			// �ϼ�
			tmpdata[0] += Double.valueOf(dpxse);
			tmpdata[1] += Double.valueOf(dpcgcb);
			tmpdata[2] += Double.valueOf(mlr);
		}

		// ��չ
		List<TableCell> totalTrData = new ArrayList<TableCell>();
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell(""));// 
		totalTrData.add(new TableCell("" + MathHelper.moneyFormat(tmpdata[0]),
				Rectangle.ALIGN_RIGHT));// 
		totalTrData.add(new TableCell("" + MathHelper.moneyFormat(tmpdata[1]),
				Rectangle.ALIGN_RIGHT));// 

		totalTrData.add(new TableCell("" + MathHelper.moneyFormat(tmpdata[2]),
				Rectangle.ALIGN_RIGHT));// 

		totalTrData.set(0, new TableCell("�ϼ�"));
		TableRow tr = reportExt.getOneTableRow(totalTrData);
		tr.setType(Report.GROUP_TOTAL_TYPE);
		t.addRow(tr);

		Report report = reportExt.setSimpleColHeader(t, headerSet1);
		String[] dateTitle = { beginDate + "��" + endDate,
				TimeUtil.formatDate(new Date()) };
		reportExt.setTitleHeader(report, "���������", dateTitle, null);
		Long currentTimeMillis = System.currentTimeMillis();
		GroupReport groupReport = new GroupReport();
		response.reset();
		groupReport.format(format, "���������" + currentTimeMillis, report,
				response);
	}

}
