package com.jl.common.report.servlet;

import java.awt.Color;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;

import com.lowagie.text.pdf.BaseFont;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.HTMLCss;
import com.lucaslee.report.printer.PDFCss;
import com.lucaslee.report.printer.PDFCssItem;

public class Data {

	public static void setStyleBorder(HSSFCellStyle style, boolean haveBorder) {
		if (haveBorder) {
			style.setBorderBottom((short) 1);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);
		} else {
			style.setBorderBottom((short) 0);
			style.setBorderLeft((short) 0);
			style.setBorderRight((short) 0);
			style.setBorderTop((short) 0);
		}
	}

	public static ExcelCss getExcelCss() {
		ExcelCss css = new ExcelCss() {
			public void init(HSSFWorkbook workbook) {
				// *****************��������*****************
				// ��ͨ����
				HSSFFont fontNormal = workbook.createFont();
				fontNormal.setFontHeightInPoints((short) 10);
				fontNormal.setFontName("����");

				// ����
				HSSFFont fontBold = workbook.createFont();
				fontBold.setFontHeightInPoints((short) 10);
				fontBold.setFontName("����");
				fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

				// �󡢴�����
				HSSFFont fontBig = workbook.createFont();
				fontBig.setFontHeightInPoints((short) 15);
				fontBig.setFontName("����");
				fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// *****************end��������*****************

				// ***************����EXCEL�������ʽ��******************
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontNormal);
				setStyleBorder(style, true);
				this.setStyle(Report.GROUP_TOTAL_TYPE, style);
				this.setStyle(Report.TOTAL_TYPE, style);

				style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontBold);
				setStyleBorder(style, true);
				this.setStyle(Report.HEAD_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontBig);
				this.setStyle(Report.TITLE_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				setStyleBorder(style, true);
				this.setStyle(Report.DATA_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormal);

				// ����ȱʡ��ʽ�����ô����Խ��Excel��ʽ����Ĵ���
				this.setDefaultStyle(style);

				this.setDefaultColumnWidth((short) 20);

				// ***************end ����EXCEL�������ʽ��******************
			}
		};
		return css;
	}

	public static HTMLCss getHTMLCss() {
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt ����;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setTitle("font: bold 18pt ;");
		css.setData("font: 12pt");
		return css;
	}

	public static PDFCss getPDFCss() {
		PDFCss css = new PDFCss();

		// ******************����pdf��������*****************
		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			BaseFont bfChineseBold = BaseFont.createFont("STSong-Light,Bold",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 10, Font.NORMAL);
			Font FontChineseBold = new Font(bfChineseBold, 10, Font.NORMAL);
			// ******************end ����pdf��������*****************

			// ****************����pdf�������ʽ��*********************

			PDFCssItem item = new PDFCssItem();
			item.setBackgroudColor(new Color(0xd8e4f1));
			item.setFont(FontChinese);
			css.setGroupTotal(item);
			css.setTotal(item);

			item = new PDFCssItem();
			item.setBackgroudColor(new Color(0xffdead));
			item.setFont(FontChineseBold);
			css.setHead(item);

			item = new PDFCssItem();
			item.setFont(new Font(bfChineseBold, 15, Font.BOLD));
			css.setTitle(item);

			item = new PDFCssItem();
			item.setFont(new Font(bfChinese, 10, Font.NORMAL));
			css.setData(item);
			// ****************end ����pdf�������ʽ��*********************
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return css;
	}

	public static Table getHeader() {
		Table headerTable = new Table();
		// ���ñ��Ŀ�ȱ���(�ٷֱ�)
		// int[] widths = { 20, 60, 20 };
		// headerTable.setWidths(widths);

		headerTable.setBorder(0);
		headerTable.setAlign(headerTable.ALIGN_CENTER);

		TableCell tc = null;
		TableRow tr = null;

		tr = new TableRow(3);
		headerTable.addRow(tr);
		tc = tr.getCell(0);
		tc.setColSpan(3);
		tc.setAlign(tc.ALIGN_CENTER);
		tc.setContent("�й�XXX�ɷ����޹�˾XXX�ֹ�˾");
		tr.getCell(1).setIsHidden(true);
		tr.getCell(2).setIsHidden(true);

		tr = new TableRow(3);
		headerTable.addRow(tr);
		tc = tr.getCell(0);
		tc.setColSpan(3);
		tc.setAlign(tc.ALIGN_CENTER);
		tc.setContent("��Ʒ����ͳ�Ʊ���");
		tc.setCssClass(Report.TITLE_TYPE);
		tr.getCell(1).setIsHidden(true);
		tr.getCell(2).setIsHidden(true);

		tr = new TableRow(3);
		headerTable.addRow(tr);

		tr = new TableRow(3);
		headerTable.addRow(tr);
		tc = tr.getCell(0);
		tc.setContent("��λ��xxx�ֹ�˾");
		tc.setAlign(tc.ALIGN_LEFT);
		tc = tr.getCell(1);
		tc.setContent("����:2003-11-11��2003-11-16");
		tc.setAlign(tc.ALIGN_CENTER);
		tc = tr.getCell(2);
		tc.setContent("��λ����  Ԫ");
		tc.setAlign(tc.ALIGN_RIGHT);
		return headerTable;
	}

	public static HeaderTable getColHeader() {
		HeaderTable th = new HeaderTable();
		TableRow thr = new TableRow(4);
		thr.setCell(0, new TableCell("��Ʒ����"));
		thr.setCell(1, new TableCell("��Ʒxx��"));
		thr.setCell(2, new TableCell("��Ʒxx������"));
		thr.setCell(3, new TableCell("��Ʒxx���۶�"));
		th.addRow(thr);

		// TableCell cell = new TableCell("��Ʒ����");
		// thr.addCell(new TableCell("��Ʒ����"));
		return th;
	}

	public static Table getRecord() {
		Table t = new Table();
		double multip = 100.00;
		for (int i = 0; i < 6; i++) {
			TableRow tr = new TableRow();

			tr.addCell(new TableCell("��Ʒ" + i));
			tr.addCell(new TableCell("" + (i * multip)));
			tr.addCell(new TableCell("" + (i + 1) * multip));
			tr.addCell(new TableCell("" + (i + 2) * multip));
			t.addRow(tr);

			t.addRow(tr.cloneAll());

			tr = new TableRow();
			tr.addCell(new TableCell("��Ʒ" + i));
			tr.addCell(new TableCell("" + (i + 1) * multip));
			tr.addCell(new TableCell("" + (i + 2) * multip));
			tr.addCell(new TableCell("" + (i + 2) * multip));
			t.addRow(tr);
		}

		for (int i = 0; i < 0; i++) {
			t.addCol(t.getCol(3).cloneAll());
		}
		return t;
	}

	public static Table getFooterTable() {
		Table footerTable = new Table();

		TableCell tc = null;
		TableRow tr = null;
		tr = new TableRow(3);
		footerTable.setBorder(0);
		footerTable.setAlign(footerTable.ALIGN_CENTER);
		footerTable.addRow(tr);
		tc = tr.getCell(0);
		tr.getCell(0).setContent("�Ʊ���:xxx");
		tc.setAlign(tc.ALIGN_LEFT);
		tr.getCell(1).setContent("�����:xxx");
		tc.setAlign(tc.ALIGN_CENTER);
		tr.getCell(2).setContent("�Ʊ�����:xxx");
		tc.setAlign(tc.ALIGN_RIGHT);

		return footerTable;
	}

}
