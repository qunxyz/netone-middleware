package com.jl.web.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import oescript.parent.OeScript;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jl.common.MathHelper;
import com.jl.common.TimeUtil;
import com.lucaslee.report.ReportManager;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.ExcelPrinter;

public class HGScriptReportBase extends OeScript {

	// ��Ʒ����
	public void listtable1(String lsh, HttpServletResponse response)
			throws Exception {
		final String MY_STYLE1 = "customStyle1";

		final String MY_DATA_STYLE1 = "customDataStyle1";
		final String MY_DATA_STYLE2 = "customDataStyle2";
		final String MY_HEADER_STYLE1 = "customHeaderStyle1";

		final String MY_RIGHT_STYLE1 = "customRIGHTStyle1";

		// ���������
		ReportManager rm = new ReportManager();
		// ���ԭʼ���ݱ��
		Table t = new Table();

		List x = new ArrayList();
		// String lsh = "d8bf0c2580554d5d846cdfdb0bad6fe1";
		x.add(x);
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con,
				"SELECT * from DY_571336475650683 where lsh='" + lsh + "'  ");
		db.close(con);

		TableRow thr = new TableRow(9);
		TableCell htc = new TableCell("��Ʒ�����۸������", Rectangle.ALIGN_CENTER);
		htc.setColSpan(9);
		thr.setCell(0, htc);
		thr.setType(MY_STYLE1);
		t.addRow(thr);

		TableCell hiddencell = new TableCell("");
		hiddencell.setIsHidden(true);

		String mcol1 = "";
		String mcol2 = "";
		String mcol3 = "";
		String mcol4 = "";
		String mcol5 = "";
		String mcol6 = "";
		String mcol7 = "";
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			mcol1 = (String) map.get("column3");
			mcol2 = (String) map.get("column4");
			mcol3 = (String) map.get("column5");
			mcol4 = (String) map.get("column6");
			mcol5 = (String) map.get("column7");
			mcol6 = (String) map.get("column8");
			mcol7 = (String) map.get("column9");
		}

		// 0
		TableRow tr0 = new TableRow();
		if (org.apache.commons.lang.StringUtils.isNotEmpty(mcol1)) {
			mcol1 = com.jl.common.TimeUtil.formatDate(com.jl.common.TimeUtil
					.parseDate(mcol1), "yyyy��MM��dd��");
		}

		TableCell tc0 = new TableCell("����ʱ��:" + mcol1);
		tc0.setColSpan(5);
		tr0.addCell(tc0);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		TableCell tc4 = new TableCell("��˾�������:" + mcol2);
		tc4.setColSpan(4);
		tr0.addCell(tc4);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		t.addRow(tr0);

		// 00
		TableRow thr0 = new TableRow(9);
		TableCell htc0 = new TableCell("��λ:Ԫ/��", Rectangle.ALIGN_RIGHT);
		htc0.setColSpan(9);
		thr0.setCell(0, htc0);
		thr0.setType(MY_RIGHT_STYLE1);
		t.addRow(thr0);

		// 1
		TableRow tr1 = new TableRow();

		TableCell tc00_ = new TableCell("����", Rectangle.ALIGN_CENTER);
		tc00_.setAlign(tc00_.ALIGN_CENTER);
		tc00_.setCssClass(MY_DATA_STYLE2);
		tc00_.setRowSpan(2);
		tr1.addCell(tc00_);

		TableCell tc00 = new TableCell("����:" + mcol3);
		tc00.setColSpan(4);
		tr1.addCell(tc00);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		TableCell tc44 = new TableCell("��ַ:" + mcol4);
		tc44.setColSpan(4);
		tr1.addCell(tc44);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		tr1.setType(MY_DATA_STYLE1);
		t.addRow(tr1);

		// 2
		TableRow tr2 = new TableRow();
		TableCell tc000_ = new TableCell("����");
		tc000_.setAlign(tc000_.ALIGN_CENTER);
		tc000_.setIsHidden(true);
		tr2.addCell(tc000_);

		TableCell tc000 = new TableCell("��ϵ��:" + mcol5);
		tc000.setColSpan(2);
		tr2.addCell(tc000);
		tr2.addCell(hiddencell);

		TableCell tc444 = new TableCell("��ϵ��ʽ:" + mcol6);
		tc444.setColSpan(3);
		tr2.addCell(tc444);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);

		TableCell tc666 = new TableCell("��ʽ:" + getSETTLEMENTPERIOD(mcol7));
		tc666.setColSpan(3);
		tr2.addCell(tc666);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.setType(MY_DATA_STYLE1);
		t.addRow(tr2);

		con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List listx = db.queryData(con,
				"SELECT * from DY_571336475650684 where fatherlsh='" + lsh
						+ "' ");
		db.close(con);

		TableRow trx = new TableRow();
		trx.setType(MY_HEADER_STYLE1);
		trx.addCell(new TableCell("����Ʒ��", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("���", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("������", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("������", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("������", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("ҵ��Ա���", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("����Ա���", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("��˾ë����", Rectangle.ALIGN_CENTER));
		trx.addCell(new TableCell("��ע", Rectangle.ALIGN_CENTER));
		t.addRow(trx);

		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String col3 = (String) object.get("column3");
			String col5 = (String) object.get("column5");
			String col6 = (String) object.get("column6");
			String col7 = (String) object.get("column7").toString();
			String col8 = (String) object.get("column8").toString();
			String col9 = (String) object.get("column9").toString();
			String col10 = (String) object.get("column10").toString();
			String col11 = (String) object.get("column11").toString();
			String col12 = (String) object.get("column12");

			TableRow tr = new TableRow();
			tr.addCell(new TableCell(col3));
			tr.addCell(new TableCell(col5));
			tr.addCell(new TableCell(col6));
			tr.addCell(new TableCell(col7));
			tr.addCell(new TableCell(col8));
			tr.addCell(new TableCell(col9));
			tr.addCell(new TableCell(col10));
			tr.addCell(new TableCell(col11));
			tr.addCell(new TableCell(col12));
			tr.setType(MY_DATA_STYLE1);
			t.addRow(tr);
		}

		TableRow trx_ = new TableRow();
		TableCell tcx_1 = new TableCell("�����̻�ҵ��Աȷ��:");
		tcx_1.setColSpan(3);
		trx_.addCell(tcx_1);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_2 = new TableCell("����ȷ��:");
		tcx_2.setColSpan(2);
		trx_.addCell(tcx_2);
		trx_.addCell(hiddencell);

		TableCell tcx_3 = new TableCell("����ȷ��:");
		tcx_3.setColSpan(2);
		trx_.addCell(tcx_3);
		trx_.addCell(hiddencell);

		TableCell tcx_4 = new TableCell("�ܾ���ȷ��:");
		tcx_4.setColSpan(2);
		trx_.addCell(tcx_4);
		trx_.addCell(hiddencell);

		t.addRow(trx_);

		TableRow trx2_ = new TableRow();
		TableCell tcx_1_2 = new TableCell("����:");
		tcx_1_2.setColSpan(3);
		trx2_.addCell(tcx_1_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_2_2 = new TableCell("����:");
		tcx_2_2.setColSpan(2);
		trx2_.addCell(tcx_2_2);
		trx2_.addCell(hiddencell);

		TableCell tcx_3_2 = new TableCell("����:");
		tcx_3_2.setColSpan(2);
		trx2_.addCell(tcx_3_2);
		trx2_.addCell(hiddencell);

		TableCell tcx_4_2 = new TableCell("����:");
		tcx_4_2.setColSpan(2);
		trx2_.addCell(tcx_4_2);
		trx2_.addCell(hiddencell);

		t.addRow(trx2_);

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// ����ļ�
		// FileOutputStream fo = null;
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(("��Ʒ�����۸������" + lsh).getBytes("GBK"), "ISO8859-1")
				+ ".xls");
		response.setContentType("application/vnd.ms-excel");
		OutputStream fo = response.getOutputStream();
		try {
			// fo = new FileOutputStream("xxx.xls");

			// ִ��EXCEL��ʽ��������
			ExcelCss css = new ExcelCss() {

				public void init(HSSFWorkbook workbook) {
					// �󡢴�����
					HSSFFont fontBig = workbook.createFont();
					fontBig.setFontHeightInPoints((short) 15);
					fontBig.setFontName("����");
					fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

					HSSFFont fontNormal = workbook.createFont();
					fontNormal.setFontHeightInPoints((short) 10);
					fontNormal.setFontName("����");
					// *****************end��������*****************

					// ***************����EXCEL�������ʽ��******************
					HSSFCellStyle style = workbook.createCellStyle();
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(Report.DATA_TYPE, style);

					style = workbook.createCellStyle();
					style.setFont(fontBig);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_STYLE1, style);

					style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					this.setStyle(MY_RIGHT_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_DATA_STYLE2, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					this.setStyle(MY_HEADER_STYLE1, style);

				}
			};
			new ExcelPrinter().print(report, css, fo, true);
			// System.out.println("���ɳɹ���");
		} finally {
			if (fo != null)
				fo.close();
		}
	}

	// ����
	public void listtable2(String lsh, HttpServletResponse response)
			throws Exception {
		final String MY_STYLE1 = "customStyle1";

		final String MY_DATA_STYLE1 = "customDataStyle1";
		final String MY_DATA_STYLE1_BOLD = "customDataStyle1Bold";
		final String MY_DATA_STYLE2 = "customDataStyle2";
		final String MY_HEADER_STYLE1 = "customHeaderStyle1";

		final String MY_RIGHT_STYLE1 = "customRIGHTStyle1";

		final String bold_normal_style = "bold_normal_style";
		
		final String bold_normal_style_noborder = "bold_normal_style_noborder";

		final String bold_normal_style_noborder2 = "bold_normal_style_noborder2";
		
		final String bold_normal_style_left = "bold_normal_style_left";

		final String simple_style_left = "simple_style_left";

		// ִ��EXCEL��ʽ��������
		ExcelCss css = new ExcelCss() {

			public void init(HSSFWorkbook workbook) {
				// �󡢴�����
				HSSFFont fontBig = workbook.createFont();
				fontBig.setFontHeightInPoints((short) 15);
				fontBig.setFontName("����");
				fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

				HSSFFont fontNormalBold = workbook.createFont();
				fontNormalBold.setFontHeightInPoints((short) 13);
				fontNormalBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontNormalBold.setFontName("����");

				HSSFFont fontNormal = workbook.createFont();
				fontNormal.setFontHeightInPoints((short) 10);
				fontNormal.setFontName("����");

				HSSFFont fontNormalBold2 = workbook.createFont();
				fontNormalBold2.setFontHeightInPoints((short) 10);
				fontNormalBold2.setBoldweight((short) 600);
				fontNormalBold2.setFontName("����");
				// *****************end��������*****************

				// ***************����EXCEL�������ʽ��******************
				HSSFCellStyle style = workbook.createCellStyle();
				style.setBorderBottom((short) 0);
				style.setBorderLeft((short) 0);
				style.setBorderRight((short) 0);
				style.setBorderTop((short) 0);
				style.setWrapText(true);
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				this.setStyle(Report.DATA_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontBig);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style.setBorderBottom((short) 0);
				style.setBorderLeft((short) 0);
				style.setBorderRight((short) 0);
				style.setBorderTop((short) 0);
				style.setWrapText(true);
				this.setStyle(MY_STYLE1, style);

				style = workbook.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				style.setBorderBottom((short) 0);
				style.setBorderLeft((short) 0);
				style.setBorderRight((short) 0);
				style.setBorderTop((short) 0);
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style.setWrapText(true);
				this.setStyle(MY_RIGHT_STYLE1, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				style.setBorderBottom((short) 1);
				style.setBorderLeft((short) 1);
				style.setBorderRight((short) 1);
				style.setBorderTop((short) 1);
				style.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
				style.setWrapText(true);
				this.setStyle(MY_DATA_STYLE1, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormalBold);
				style.setBorderBottom((short) 1);
				style.setBorderLeft((short) 1);
				style.setBorderRight((short) 1);
				style.setBorderTop((short) 1);
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style.setWrapText(true);
				this.setStyle(MY_DATA_STYLE1_BOLD, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormalBold2);
				style.setBorderBottom((short) 1);
				style.setBorderLeft((short) 1);
				style.setBorderRight((short) 1);
				style.setBorderTop((short) 1);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style.setWrapText(true);
				this.setStyle(bold_normal_style, style);
				
				style = workbook.createCellStyle();
				style.setFont(fontNormalBold);
				this.setStyle(bold_normal_style_noborder, style);
				
				style = workbook.createCellStyle();
				style.setFont(fontNormalBold2);
				this.setStyle(bold_normal_style_noborder2, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormalBold2);
				style.setBorderBottom((short) 1);
				style.setBorderLeft((short) 1);
				style.setBorderRight((short) 1);
				style.setBorderTop((short) 1);
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style.setWrapText(true);
				this.setStyle(bold_normal_style_left, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				style.setBorderBottom((short) 1);
				style.setBorderLeft((short) 1);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				style.setBorderRight((short) 1);
				style.setBorderTop((short) 1);
				style.setWrapText(true);
				this.setStyle(MY_DATA_STYLE2, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				style.setBorderBottom((short) 1);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style.setBorderLeft((short) 1);
				style.setBorderRight((short) 1);
				style.setBorderTop((short) 1);
				style.setWrapText(true);// �Զ�����
				style
						.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				this.setStyle(MY_HEADER_STYLE1, style);

				style = workbook.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				style.setVerticalAlignment(HSSFCellStyle.ALIGN_FILL);
				style.setWrapText(true);
				this.setStyle(simple_style_left, style);

				int[] widths = { (short) 12, (short) 7, (short) 7, (short) 5,
						(short) 5, (short) 5, (short) 5, (short) 5, (short) 5,
						(short) 5, (short) 6, (short) 8, (short) 8, (short) 10 };
				this.setWidths(widths);
			}
		};

		// ���������
		ReportManager rm = new ReportManager();
		// ���ԭʼ���ݱ��
		Table t = new Table();

		List x = new ArrayList();
		// String lsh = "c5f422d673024f8eaaff7863d8dce68e";
		x.add(x);
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db
				.queryData(
						con,
						"SELECT t.*,sub.column7 begindate,sub.column8 as enddate from DY_991336361733786 t left join DY_991336361733788 sub on sub.fatherlsh=t.lsh  where t.lsh='"
								+ lsh + "'  ");
		db.close(con);

		TableRow thr = new TableRow(14);
		TableCell htc = new TableCell("          ����������",
				Rectangle.ALIGN_CENTER);
		htc.setHeight(21);
		htc.setColSpan(12);
		thr.setCell(0, htc);
		thr.setType(MY_STYLE1);

		TableCell htc2 = new TableCell("��λ����Ԫ/����");
		htc2.setColSpan(2);
		htc2.setCssClass(MY_RIGHT_STYLE1);
		thr.setCell(12, htc2);
		t.addRow(thr);

		TableCell hiddencell = new TableCell("");
		hiddencell.setIsHidden(true);

		String mcol1 = "";
		String mcol2 = "";
		String mcol3 = "";
		String mcol4 = "";
		String mcol5 = "";
		String mcol6 = "";
		String mcol7 = "";

		String wf1 = "";// ����1
		String wf2 = "";// ����2
		String wf3 = "";// ����3
		String wf4 = "";// ����4

		String clientid = "";
		String clientcate = "";// �ͻ�����
		String begindate = "";// ������ʼʱ��
		String enddate = "";// ��������ʱ��
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			mcol1 = (String) map.get("column3");
			mcol2 = (String) map.get("column4");
			mcol3 = (String) map.get("column5");
			mcol4 = (String) map.get("column6");
			mcol5 = (String) map.get("column7");
			mcol6 = (String) map.get("column8");
			mcol7 = (String) map.get("column9");
			clientid = (String) map.get("column10");
			clientcate = (String) map.get("column11");
			wf1 = (String) map.get("column12");
			wf2 = (String) map.get("column13");
			wf3 = (String) map.get("column14");
			wf4 = (String) map.get("column15");
			begindate = (String) map.get("begindate");
			enddate = (String) map.get("enddate");
		}

		Connection conxx = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List listxx = db.queryData(conxx,
				"SELECT * from DY_991336361733788    where fatherlsh='" + lsh
						+ "'  ");
		db.close(conxx);

		String cxStartdate = "";// ������ʼʱ��
		String cxEnddate = "";// ��������ʱ��
		String cxCheckdate = "";// ����ʱ��
		if (listxx.size() > 0) {
			Map map = (Map) listxx.get(0);
			cxStartdate = (String) map.get("column7").toString();
			cxEnddate = (String) map.get("column8").toString();
			cxCheckdate = (String) map.get("column9").toString();
		}

		// 0
		TableRow tr0 = new TableRow();

		TableCell tc0 = new TableCell("��˾�������:" + mcol1);
		tc0.setHeight(21);
		tc0.setColSpan(7);
		tr0.addCell(tc0);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		TableCell tc4 = new TableCell("���ҷ������:" + mcol2);
		tc4.setHeight(21);
		tc4.setColSpan(7);
		tr0.addCell(tc4);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		tr0.setType(bold_normal_style_noborder2);
		t.addRow(tr0);

		// 1
		TableRow tr1 = new TableRow();

		TableCell tc00_ = new TableCell("��������", Rectangle.ALIGN_CENTER);
		tc00_.setAlign(tc00_.ALIGN_CENTER);
		tc00_.setRowSpan(2);
		tr1.addCell(tc00_);

		TableCell tc00 = new TableCell("����:" + getSubmessByFTypeID(clientcate));
		tc00.setColSpan(6);
		tc00.setHeight(21);
		tc00.setCssClass(bold_normal_style_left);
		tr1.addCell(tc00);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		TableCell tc44 = new TableCell("��ַ:" + mcol4);
		tc44.setColSpan(7);
		tc44.setCssClass(bold_normal_style_left);
		tr1.addCell(tc44);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);

		tr1.setType(bold_normal_style);
		t.addRow(tr1);

		// 2
		TableRow tr2 = new TableRow();
		TableCell tc000_ = new TableCell("��������");
		tc000_.setAlign(tc000_.ALIGN_CENTER);
		tc000_.setCssClass(bold_normal_style_left);
		tc000_.setIsHidden(true);
		tc000_.setHeight(21);
		tr2.addCell(tc000_);

		TableCell tc000 = new TableCell("��ϵ��:" + mcol5);
		tc000.setColSpan(5);
		tc000.setCssClass(bold_normal_style_left);
		tr2.addCell(tc000);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);

		TableCell tc444 = new TableCell("��ϵ��ʽ:" + mcol6);
		tc444.setCssClass(bold_normal_style_left);
		tc444.setColSpan(5);
		tr2.addCell(tc444);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);

		TableCell tc666 = new TableCell("��ʽ:" + getSETTLEMENTPERIOD(mcol7));
		tc666.setColSpan(3);
		tc666.setCssClass(bold_normal_style_left);
		tr2.addCell(tc666);
		tr2.addCell(hiddencell);
		tr2.addCell(hiddencell);
		tr2.setType(bold_normal_style);
		t.addRow(tr2);

		con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List listx = db.queryData(con,
				"SELECT * from DY_991336361733787  where fatherlsh='" + lsh
						+ "' ");
		db.close(con);

		// ����Ʒ�� ��� ���� ��˾���� �������� ����Ԥ������ ҵ���� ��ע
		// ���� ���� ���� ���� ���� ���� ���� ����

		TableRow trx = new TableRow();
		trx.setType(MY_HEADER_STYLE1);
		TableCell cl1 = new TableCell("����Ʒ��", Rectangle.VALIGN_MIDDLE);
		cl1.setHeight(21);
		cl1.setRowSpan(2);
		trx.addCell(cl1);
		TableCell cl2 = new TableCell("���", Rectangle.VALIGN_MIDDLE);
		cl2.setRowSpan(2);
		trx.addCell(cl2);
		TableCell cl3 = new TableCell("����", Rectangle.VALIGN_MIDDLE);
		cl3.setRowSpan(2);
		trx.addCell(cl3);
		TableCell ttc = new TableCell("��˾����", Rectangle.VALIGN_MIDDLE);
		ttc.setRowSpan(1);
		ttc.setColSpan(4);
		trx.addCell(ttc);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);
		TableCell ttc2 = new TableCell("��������", Rectangle.VALIGN_MIDDLE);
		ttc2.setRowSpan(1);
		ttc2.setColSpan(4);
		trx.addCell(ttc2);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);
		trx.addCell(hiddencell);

		TableCell tcclll0 = new TableCell("Ԥ������", Rectangle.VALIGN_MIDDLE);
		tcclll0.setRowSpan(2);
		trx.addCell(tcclll0);
		TableCell tcclll1 = new TableCell("ҵ����", Rectangle.VALIGN_MIDDLE);
		tcclll1.setRowSpan(2);
		trx.addCell(tcclll1);
		TableCell tcclll2 = new TableCell("��ע", Rectangle.VALIGN_MIDDLE);
		tcclll2.setRowSpan(2);
		trx.addCell(tcclll2);
		trx.setType(bold_normal_style);
		t.addRow(trx);

		TableRow trx2 = new TableRow();
		trx2.setType(MY_HEADER_STYLE1);
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		trx2.addCell(new TableCell("����"));
		trx2.addCell(new TableCell("����"));
		trx2.addCell(new TableCell("����"));
		trx2.addCell(new TableCell("����"));
		trx2.addCell(new TableCell("����"));
		trx2.addCell(new TableCell("����"));
		trx2.addCell(new TableCell("����"));
		trx2.addCell(new TableCell("����"));
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		trx2.addCell(hiddencell);
		trx2.setType(bold_normal_style);
		t.addRow(trx2);

		double[] sumx = new double[4];
		StringBuffer productidStrs = new StringBuffer();
		String split = "";
		for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String col3 = (String) object.get("column3") == null ? "" : object
					.get("column3").toString();
			String col4 = (String) object.get("column4") == null ? "" : object
					.get("column4").toString();
			String col5 = object.get("column5") == null ? "0"
					: ((BigDecimal) object.get("column5")).toString();
			String col6 = object.get("column6") == null ? "0"
					: ((BigDecimal) object.get("column6")).toString();
			String col7 = object.get("column7") == null ? "0"
					: ((BigDecimal) object.get("column7")).toString();
			String col8 = object.get("column8") == null ? "0"
					: ((BigDecimal) object.get("column8")).toString();
			String col9 = object.get("column9") == null ? "0"
					: ((BigDecimal) object.get("column9")).toString();
			String col10 = object.get("column10") == null ? "0"
					: ((BigDecimal) object.get("column10")).toString();
			String col11 = object.get("column11") == null ? "0"
					: ((BigDecimal) object.get("column11")).toString();
			String col12 = object.get("column12") == null ? "0"
					: ((BigDecimal) object.get("column12")).toString();
			String col20 = object.get("column20") == null ? "0"
					: ((BigDecimal) object.get("column20")).toString();

			String col13 = object.get("column13") == null ? "0"
					: ((BigDecimal) object.get("column13")).toString();
			String col14 = object.get("column14") == null ? "0"
					: ((BigDecimal) object.get("column14")).toString();
			String col15 = object.get("column15") == null ? "" : object.get(
					"column15").toString();
			String col16 = object.get("column16") == null ? "0"
					: ((String) object.get("column16")).toString();
			String col17 = object.get("column17") == null ? "" : object.get(
					"column17").toString();

			String column19 = object.get("column19") == null ? "" : object.get(
					"column19").toString();

			productidStrs.append(split + column19);
			split = ",";

			TableRow tr = new TableRow();
			tr.addCell(new TableCell(col3));
			tr.addCell(new TableCell(col4));
			tr.addCell(new TableCell(col5));

			tr.addCell(new TableCell(col6));
			tr.addCell(new TableCell(col7));
			tr.addCell(new TableCell(col8));
			tr.addCell(new TableCell(col9));

			tr.addCell(new TableCell(col10));
			tr.addCell(new TableCell(col11));
			tr.addCell(new TableCell(col12));
			tr.addCell(new TableCell(col20));

			tr.addCell(new TableCell(col16 + "~" + col17));
			tr.addCell(new TableCell(col14));
			tr.addCell(new TableCell(col15));
			tr.setType(MY_DATA_STYLE1);
			t.addRow(tr);

			sumx[0] += Double.valueOf(col6) + Double.valueOf(col10);
			sumx[1] += Double.valueOf(col7) + Double.valueOf(col11);
			sumx[2] += Double.valueOf(col8) + Double.valueOf(col12);
			sumx[3] += Double.valueOf(col9) + Double.valueOf(col20);
		}

		TableRow trx2_00 = new TableRow();
		TableCell tcx_1_200 = new TableCell("��  ��", Rectangle.ALIGN_CENTER);
		tcx_1_200.setColSpan(2);
		tcx_1_200.setHeight(21);
		trx2_00.addCell(tcx_1_200);
		trx2_00.addCell(hiddencell);

		TableCell tcx_2_200 = new TableCell("����:" + sumx[0]);
		tcx_2_200.setColSpan(3);
		trx2_00.addCell(tcx_2_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);

		TableCell tcx_3_200 = new TableCell("����:" + sumx[1]);
		tcx_3_200.setColSpan(3);
		trx2_00.addCell(tcx_3_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);

		TableCell tcx_4_200 = new TableCell("����:" + sumx[2]);
		tcx_4_200.setColSpan(3);
		trx2_00.addCell(tcx_4_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);

		TableCell tcx_5_200 = new TableCell("����:" + sumx[3]);
		tcx_5_200.setColSpan(3);
		trx2_00.addCell(tcx_5_200);
		trx2_00.addCell(hiddencell);
		trx2_00.addCell(hiddencell);
		trx2_00.setType(MY_DATA_STYLE1);
		t.addRow(trx2_00);

		TableRow trx000_ = new TableRow();
		TableCell c00 = new TableCell("����ʱ��:"
				+ TimeUtil.formatDate(TimeUtil.parseDate(cxStartdate,
						"yyyy-MM-dd"), "yyyy��MM��dd��")
				+ "-"
				+ TimeUtil.formatDate(TimeUtil.parseDate(cxEnddate,
						"yyyy-MM-dd"), "yyyy��MM��dd��"));
		c00.setHeight(21);
		c00.setColSpan(8);
		trx000_.addCell(c00);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);
		trx000_.addCell(hiddencell);

		{
			TableCell c01 = new TableCell("����ʱ��:"
					+ TimeUtil.formatDate(TimeUtil.parseDate(cxCheckdate,
							"yyyy-MM-dd"), "yyyy��MM��dd��"));
			c01.setColSpan(6);
			trx000_.addCell(c01);
			trx000_.addCell(hiddencell);
			trx000_.addCell(hiddencell);
			trx000_.addCell(hiddencell);
			trx000_.addCell(hiddencell);
			trx000_.addCell(hiddencell);
			trx000_.setType(MY_DATA_STYLE1);
			t.addRow(trx000_);
		}

		TableRow thr00000 = new TableRow(14);
		TableCell htc00 = new TableCell("�������Ҫ���ϣ����ҷ������뼰������");
		htc00.setColSpan(14);
		htc00.setHeight(21);
		thr00000.setCell(0, htc00);
		thr00000.setType(MY_DATA_STYLE1_BOLD);
		t.addRow(thr00000);

		TableRow trx_ = new TableRow();

		TableCell tcx_1_ = new TableCell("�����̻�ҵ��Աȷ��:"
				+ ((wf1 == null || "".equals(wf1)) ? ""
						: wf1.split("\\|\\|")[0]));
		tcx_1_.setHeight(25);
		tcx_1_.setColSpan(3);
		trx_.addCell(tcx_1_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_2_ = new TableCell("����ȷ��:"
				+ ((wf2 == null || "".equals(wf2)) ? ""
						: wf2.split("\\|\\|")[0]));
		tcx_2_.setColSpan(4);
		trx_.addCell(tcx_2_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_3_ = new TableCell("����ȷ��:"
				+ ((wf3 == null || "".equals(wf3)) ? ""
						: wf3.split("\\|\\|")[0]));
		tcx_3_.setColSpan(3);
		trx_.addCell(tcx_3_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		TableCell tcx_4_ = new TableCell("�ܾ���ȷ��:"
				+ ((wf4 == null || "".equals(wf4)) ? ""
						: wf4.split("\\|\\|")[0]));
		tcx_4_.setColSpan(4);
		trx_.addCell(tcx_4_);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);
		trx_.addCell(hiddencell);

		t.addRow(trx_);

		TableRow trx2_ = new TableRow();
		TableCell tcx_1_2 = new TableCell("����:"
				+ ((wf1 == null || "".equals(wf1)) ? ""
						: wf1.split("\\|\\|")[1]));
		tcx_1_2.setHeight(25);
		tcx_1_2.setColSpan(3);
		trx2_.addCell(tcx_1_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_2_2 = new TableCell("����:"
				+ ((wf2 == null || "".equals(wf2)) ? ""
						: wf2.split("\\|\\|")[1]));
		tcx_2_2.setColSpan(4);
		trx2_.addCell(tcx_2_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_3_2 = new TableCell("����:"
				+ ((wf3 == null || "".equals(wf3)) ? ""
						: wf3.split("\\|\\|")[1]));
		tcx_3_2.setColSpan(3);
		trx2_.addCell(tcx_3_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);

		TableCell tcx_4_2 = new TableCell("����:"
				+ ((wf4 == null || "".equals(wf4)) ? ""
						: wf4.split("\\|\\|")[1]));
		tcx_4_2.setColSpan(4);
		trx2_.addCell(tcx_4_2);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);
		trx2_.addCell(hiddencell);
		trx2_.setType(simple_style_left);
		t.addRow(trx2_);

		// ����
		TableRow thrnull = new TableRow(14);
		t.addRow(thrnull);

		// ������ͳ��
		TableRow thr000001 = new TableRow(14);
		TableCell htc001 = new TableCell(mcol3
				+ TimeUtil.formatDate(TimeUtil.parseDate(begindate,
						"yyyy-MM-dd"), "yyyy��MM��dd��")
				+ "��"
				+ TimeUtil.formatDate(
						TimeUtil.parseDate(enddate, "yyyy-MM-dd"),
						"yyyy��MM��dd��") + "�������");
		htc001.setAlign(Rectangle.VALIGN_MIDDLE);
		htc001.setColSpan(14);
		thr000001.setCell(0, htc001);
		thr000001.setType(bold_normal_style_noborder);
		t.addRow(thr000001);

		// ���ۼ�¼
		TableRow tr11 = new TableRow();

		tr11.addCell(new TableCell("����", Rectangle.ALIGN_CENTER));
		TableCell cc1 = new TableCell("���ݱ��", Rectangle.ALIGN_CENTER);
		tr11.addCell(cc1);

		TableCell cc2 = new TableCell("������λ", Rectangle.ALIGN_CENTER);
		cc2.setColSpan(2);
		tr11.addCell(cc2);
		tr11.addCell(hiddencell);

		tr11.addCell(new TableCell("��Ʒ���", Rectangle.ALIGN_CENTER));

		TableCell cc3 = new TableCell("��Ʒ����", Rectangle.ALIGN_CENTER);
		tr11.addCell(cc3);

		tr11.addCell(new TableCell("����ͺ�", Rectangle.ALIGN_CENTER));
		tr11.addCell(new TableCell("��λ", Rectangle.ALIGN_CENTER));
		tr11.addCell(new TableCell("ʵ������", Rectangle.ALIGN_CENTER));

		tr11.addCell(new TableCell("���õ�λ", Rectangle.ALIGN_CENTER));
		tr11.addCell(new TableCell("���õ�λ����", Rectangle.ALIGN_CENTER));

		tr11.addCell(new TableCell("����", Rectangle.ALIGN_CENTER));
		tr11.addCell(new TableCell("���", Rectangle.ALIGN_CENTER));
		tr11.addCell(new TableCell("��Ӧ����", Rectangle.ALIGN_CENTER));

		tr11.setType(bold_normal_style);
		t.addRow(tr11);

		StringBuffer sqlstr = new StringBuffer();
		sqlstr
				.append(" select v1.FDate as FDate,org.FName as orgName,v1.FBillNo,t3192.FShortNumber as FItemIDName,t3192.Fname as FItemName,unit.FName as UnitName,t3192.FModel as FItemModel,isnull(convert(decimal(22,2),u1.FConsignPrice),0) FConsignPrice,isnull(convert(decimal(22,2),u1.Fauxqty),0) Fauxqty,isnull(convert(decimal(22,2),u1.FConsignAmount),0) FConsignAmount,u1.FMapName, ");

		sqlstr
				.append("Case WHEN t3192.FStoreUnitID=0 THEN '' Else  unit.FName end AS FCUUnitName,");
		sqlstr
				.append("cast((Case WHEN t3192.FStoreUnitID=0 THEN '' Else  convert(decimal(22,2),u1.FQty/unit.FCoefficient) end) as varchar) AS FCUUnitQty ");

		sqlstr.append(" from ICStockBill v1  ");
		sqlstr
				.append(" Inner Join ICStockBillEntry u1 on v1.FInterID=u1.FInterID ");
		sqlstr
				.append(" left outer join t_ICItem t3192 on u1.FItemID=t3192.FItemID ");
		sqlstr
				.append(" left outer join t_Organization org on org.FItemID=v1.FSupplyID ");
		sqlstr
				.append(" left outer join t_MeasureUnit unit on unit.FItemID=u1.FUnitID ");
		sqlstr.append(" where v1.FTranType=21 and org.FTypeID="
				+ (("".equals(clientcate)) ? "" : clientcate)
				+ " and u1.FItemID in (" + productidStrs.toString() + ")"
				+ " and v1.fdate>='" + cxStartdate + "' " + " and v1.fdate<='"
				+ cxEnddate + "' ");

		// sqlstr.append(" where v1.FTranType=21 ");//����

		Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");

		List selllist = db.queryData(hgcon, sqlstr.toString());
		double[] total = new double[2];
		for (Iterator iterator = selllist.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			String FItemIDName = (String) object.get("FItemIDName");
			String FItemName = (String) object.get("FItemName");
			String FItemModel = (String) object.get("FItemModel");
			BigDecimal FConsignPrice = (BigDecimal) object.get("FConsignPrice");
			BigDecimal Fauxqty = (BigDecimal) object.get("Fauxqty");

			String FCUUnitName = (String) object.get("FCUUnitName");
			String FCUUnitQty = (String) object.get("FCUUnitQty");

			BigDecimal FConsignAmount = (BigDecimal) object
					.get("FConsignAmount");
			String FDate = (String) object.get("FDate").toString();
			String orgName = (String) object.get("orgName");
			String FBillNo = (String) object.get("FBillNo");
			String UnitName = (String) object.get("UnitName");
			/** ��Ӧ���� 03�� */
			String FMapName = (String) object.get("FMapName");

			TableRow tr = new TableRow();

			tr.addCell(new TableCell(FDate.substring(0, 11)));

			TableCell ccx1 = new TableCell(FBillNo);
			tr.addCell(ccx1);

			TableCell ccx2 = new TableCell(orgName);
			ccx2.setColSpan(2);
			tr.addCell(ccx2);
			tr.addCell(hiddencell);

			tr.addCell(new TableCell(FItemIDName));

			TableCell ccx3 = new TableCell(FItemName);
			tr.addCell(ccx3);

			tr.addCell(new TableCell(FItemModel));
			tr.addCell(new TableCell(UnitName));
			tr.addCell(new TableCell("" + Fauxqty));

			tr.addCell(new TableCell(FCUUnitName));
			tr.addCell(new TableCell("" + FCUUnitQty));

			tr.addCell(new TableCell("" + FConsignPrice));
			tr.addCell(new TableCell("" + FConsignAmount));
			tr.addCell(new TableCell("" + FMapName));

			tr.setType(MY_DATA_STYLE1);
			t.addRow(tr);

			total[0] += FConsignAmount.doubleValue();
			total[1] += ("".equals(FCUUnitQty)) ? 0.0 : Double
					.valueOf(FCUUnitQty);
		}
		db.close(hgcon);

		TableRow tr = new TableRow();
		tr.addCell(new TableCell("�ϼ�"));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell("" + MathHelper.round(total[1], 2)));
		tr.addCell(new TableCell(""));
		tr.addCell(new TableCell("" + total[0]));
		tr.addCell(new TableCell(""));
		tr.setType(bold_normal_style);
		t.addRow(tr);

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// ����ļ�
		// FileOutputStream fo = null;
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(("����������" + lsh).getBytes("GBK"), "ISO8859-1")
				+ ".xls");
		response.setContentType("application/vnd.ms-excel");
		OutputStream fo = response.getOutputStream();
		try {
			// fo = new FileOutputStream("xxx2.xls");

			new ExcelPrinter().print(report, css, fo, true);
			// System.out.println("���ɳɹ���");
		} finally {
			if (fo != null)
				fo.close();
		}
	}

	// ���û�����
	public void listtable3(String lsh, HttpServletResponse response)
			throws Exception {
		final String MY_STYLE1 = "customStyle1";

		final String MY_DATA_STYLE1 = "customDataStyle1";
		final String MY_DATA_STYLE2 = "customDataStyle2";
		final String MY_HEADER_STYLE1 = "customHeaderStyle1";

		final String MY_RIGHT_STYLE1 = "customRIGHTStyle1";

		// ���������
		ReportManager rm = new ReportManager();
		// ���ԭʼ���ݱ��
		Table t = new Table();

		List x = new ArrayList();
		// String lsh = "2a5964344ebd48ff83b3af65c56989fd";
		x.add(x);
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");
		List list = db.queryData(con,
				"SELECT * from DY_991336361733789  where lsh='" + lsh + "'  ");
		db.close(con);

		TableRow thr = new TableRow(8);
		TableCell htc = new TableCell("���û������", Rectangle.ALIGN_CENTER);
		htc.setHeight(25);
		htc.setColSpan(8);
		thr.setCell(0, htc);
		thr.setType(MY_STYLE1);
		t.addRow(thr);

		String mcol1 = "";
		String mcol2 = "";
		String mcol3 = "";
		String mcol4 = "";
		String mcol5 = "";
		String mcol6 = "";
		String mcol7 = "";
		String mcol8 = "";
		String mcol9 = "";
		String mcol10 = "";
		String mcol11 = "";
		String mcol12 = "";
		String mcol13 = "";
		String mcol14 = "";
		String mcol15 = "";
		String mcol16 = "";
		String mcol17 = "";
		String mcol18 = "";
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			mcol1 = (String) map.get("column3");
			mcol2 = (String) map.get("column4");
			mcol3 = (String) map.get("column5");
			mcol4 = (String) map.get("column6");
			mcol5 = (String) map.get("column21");
			mcol6 = (String) map.get("column22");
			mcol7 = (String) map.get("column23");
			mcol8 = (String) map.get("column12");
			mcol9 = (String) map.get("column13");
			mcol10 = (String) map.get("column16");
			mcol11 = (String) map.get("column10");
			mcol12 = (String) map.get("column14");
			mcol13 = (String) map.get("column9");
			mcol14 = (String) map.get("column17");
			mcol15 = (String) map.get("column11");
			mcol16 = (String) map.get("column18");
			mcol17 = (String) map.get("column20");
			mcol18 = (String) map.get("column19");
		}
		TableCell hiddencell = new TableCell("");
		hiddencell.setIsHidden(true);
		// 0
		TableRow tr0 = new TableRow();
		TableCell tc0 = new TableCell("�ͻ����:" + mcol1);
		tc0.setColSpan(4);
		tc0.setHeight(22);
		tr0.addCell(tc0);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);

		TableCell tc0_ = new TableCell("�ͻ�����:" + mcol2);
		tc0_.setColSpan(4);
		tr0.addCell(tc0_);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.addCell(hiddencell);
		tr0.setType(MY_DATA_STYLE1);

		t.addRow(tr0);

		// 1
		TableRow tr1 = new TableRow();
		TableCell tc1 = new TableCell("��˾��ַ:" + mcol3);
		tc1.setColSpan(8);
		tc1.setHeight(22);
		tr1.addCell(tc1);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.addCell(hiddencell);
		tr1.setType(MY_DATA_STYLE1);
		t.addRow(tr1);

		// 2
		TableRow tr2 = new TableRow();
		TableCell tc2 = new TableCell("�ͻ��㼶:" + GETCLIENTLEVEL(mcol4));
		tc2.setColSpan(2);
		tc2.setHeight(22);
		tr2.addCell(tc2);
		tr2.addCell(hiddencell);

		TableCell tc2_ = new TableCell("��������:" + GETDEPTLEVEL(mcol5));
		tc2_.setColSpan(2);
		tr2.addCell(tc2_);
		tr2.addCell(hiddencell);

		TableCell tc2_2 = new TableCell("���ʽ:" + getSETTLEMENTPERIOD(mcol6));
		tc2_2.setColSpan(2);
		tr2.addCell(tc2_2);
		tr2.addCell(hiddencell);

		TableCell tc2_3 = new TableCell("�ͻ�����:" + getSubmessByFTypeID(mcol7));
		tc2_3.setColSpan(2);
		tr2.addCell(tc2_3);
		tr2.addCell(hiddencell);

		tr2.setType(MY_DATA_STYLE1);

		t.addRow(tr2);

		// 3
		TableRow tr3 = new TableRow();
		TableCell tc3 = new TableCell("��ϵ��:" + mcol8);
		tc3.setColSpan(4);
		tc3.setHeight(22);
		tr3.addCell(tc3);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);

		TableCell tc3_ = new TableCell("��ϵ�绰:" + mcol9);
		tc3_.setColSpan(4);
		tr3.addCell(tc3_);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);
		tr3.addCell(hiddencell);

		tr3.setType(MY_DATA_STYLE1);

		t.addRow(tr3);

		// 4
		TableRow tr4 = new TableRow();
		TableCell tc4 = new TableCell("������:" + mcol10);
		tc4.setColSpan(2);
		tc4.setHeight(23);
		tr4.addCell(tc4);
		tr4.addCell(hiddencell);

		TableCell tc4_ = new TableCell("ҵ������:" + mcol11);
		tc4_.setColSpan(2);
		tr4.addCell(tc4_);
		tr4.addCell(hiddencell);

		TableCell tc4_2 = new TableCell("������:" + mcol12);
		tc4_2.setColSpan(2);
		tr4.addCell(tc4_2);
		tr4.addCell(hiddencell);

		TableCell tc4_3 = new TableCell("�ܾ���:" + mcol13);
		tc4_3.setColSpan(2);
		tr4.addCell(tc4_3);
		tr4.addCell(hiddencell);

		tr4.setType(MY_DATA_STYLE1);

		t.addRow(tr4);

		// 5
		TableRow tr5 = new TableRow();
		TableCell tc5 = new TableCell("����ʱ��:" + mcol14);
		tc5.setHeight(23);
		tc5.setColSpan(2);
		tr5.addCell(tc5);
		tr5.addCell(hiddencell);

		TableCell tc5_ = new TableCell("���ʱ��:" + mcol15);
		tc5_.setColSpan(2);
		tr5.addCell(tc5_);
		tr5.addCell(hiddencell);

		TableCell tc5_2 = new TableCell("���ʱ��:" + mcol16);
		tc5_2.setColSpan(2);
		tr5.addCell(tc5_2);
		tr5.addCell(hiddencell);

		TableCell tc5_3 = new TableCell("���ʱ��:" + mcol17);
		tc5_3.setColSpan(2);
		tr5.addCell(tc5_3);
		tr5.addCell(hiddencell);

		tr5.setType(MY_DATA_STYLE1);

		t.addRow(tr5);

		// 6
		TableRow tr6 = new TableRow();
		TableCell tc6 = new TableCell("��ע:" + mcol18);
		tc6.setColSpan(8);
		tc6.setHeight(22);
		tr6.addCell(tc6);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.addCell(hiddencell);
		tr6.setType(MY_DATA_STYLE1);
		t.addRow(tr6);

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// ����ļ�
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(("���û�����" + lsh).getBytes("GBK"), "ISO8859-1")
				+ ".xls");
		response.setContentType("application/vnd.ms-excel");
		OutputStream fo = response.getOutputStream();
		try {
			// fo = new FileOutputStream("xxx3.xls");

			// ִ��EXCEL��ʽ��������
			ExcelCss css = new ExcelCss() {

				public void init(HSSFWorkbook workbook) {
					// �󡢴�����
					HSSFFont fontBig = workbook.createFont();
					fontBig.setFontHeightInPoints((short) 15);
					fontBig.setFontName("����");
					fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

					HSSFFont fontNormalBold = workbook.createFont();
					fontNormalBold.setFontHeightInPoints((short) 10);
					fontNormalBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					fontNormalBold.setFontName("����");

					HSSFFont fontNormal = workbook.createFont();
					fontNormal.setFontHeightInPoints((short) 10);
					fontNormal.setFontName("����");
					// *****************end��������*****************

					// ***************����EXCEL�������ʽ��******************
					HSSFCellStyle style = workbook.createCellStyle();
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(true);
					style
							.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					this.setStyle(Report.DATA_TYPE, style);

					style = workbook.createCellStyle();
					style.setFont(fontBig);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style
							.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style.setWrapText(true);
					this.setStyle(MY_STYLE1, style);

					style = workbook.createCellStyle();
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					style.setBorderBottom((short) 0);
					style.setBorderLeft((short) 0);
					style.setBorderRight((short) 0);
					style.setBorderTop((short) 0);
					style
							.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style.setWrapText(true);
					this.setStyle(MY_RIGHT_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					style.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
					style.setWrapText(true);
					this.setStyle(MY_DATA_STYLE1, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setBorderLeft((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style
							.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					style.setWrapText(true);
					this.setStyle(MY_DATA_STYLE2, style);

					style = workbook.createCellStyle();
					style.setFont(fontNormal);
					style.setBorderBottom((short) 1);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					style.setBorderLeft((short) 1);
					style.setBorderRight((short) 1);
					style.setBorderTop((short) 1);
					style.setWrapText(true);// �Զ�����
					style
							.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
					this.setStyle(MY_HEADER_STYLE1, style);

					int[] widths = { (short) 15, (short) 15, (short) 9,
							(short) 9, (short) 9, (short) 9, (short) 9 };
					this.setWidths(widths);

				}
			};
			new ExcelPrinter().print(report, css, fo, true);
			// System.out.println("���ɳɹ���");
		} finally {
			if (fo != null)
				fo.close();
		}
	}

	// ��������
	public static String getSETTLEMENTPERIOD(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db.queryData(hgcon,
					"select FItemID,FName,FID,FDay from t_SetDL where FDeleted=0 and FItemID="
							+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

	// ��ȡ��������ByFTypeID
	public static String getSubmessByFTypeID(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db.queryData(hgcon,
					"select * from t_submessage where ftypeid=501 and FInterID="
							+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

	// ��ȡ�ͻ�����
	public static String GETCLIENTLEVEL(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db
					.queryData(
							hgcon,
							"select * from t_item where FItemClassID=1 and FDeleted=0 and fDetail=0 and FItemID="
									+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

	// ��ȡ���ż���
	public static String GETDEPTLEVEL(String id) {
		if (StringUtils.isNotEmpty(id)) {
			Connection hgcon = db.con("DATASOURCE.DATASOURCE.HGDB");
			List list = db.queryData(hgcon,
					"select * from t_item where FItemClassID=2 and FDeleted=0 and FItemID="
							+ id);
			if (list.size() > 0) {
				Map map = (Map) list.get(0);
				String fname = (String) map.get("FName");
				return fname;
			}
			db.close(hgcon);
		}
		return "";
	}

}
