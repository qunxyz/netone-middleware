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
				// *****************定义字体*****************
				// 普通字体
				HSSFFont fontNormal = workbook.createFont();
				fontNormal.setFontHeightInPoints((short) 10);
				fontNormal.setFontName("宋体");

				// 粗体
				HSSFFont fontBold = workbook.createFont();
				fontBold.setFontHeightInPoints((short) 10);
				fontBold.setFontName("宋体");
				fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

				// 大、粗字体
				HSSFFont fontBig = workbook.createFont();
				fontBig.setFontHeightInPoints((short) 15);
				fontBig.setFontName("宋体");
				fontBig.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// *****************end定义字体*****************

				// ***************设置EXCEL报表的样式表******************
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

				// 设置缺省样式。设置此项以解决Excel样式过多的错误。
				this.setDefaultStyle(style);

				this.setDefaultColumnWidth((short) 20);

				// ***************end 设置EXCEL报表的样式表******************
			}
		};
		return css;
	}

	public static HTMLCss getHTMLCss() {
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 隶书;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt 隶书;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 隶书;");
		css.setTitle("font: bold 18pt ;");
		css.setData("font: 12pt");
		return css;
	}

	public static PDFCss getPDFCss() {
		PDFCss css = new PDFCss();

		// ******************定义pdf中文字体*****************
		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			BaseFont bfChineseBold = BaseFont.createFont("STSong-Light,Bold",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 10, Font.NORMAL);
			Font FontChineseBold = new Font(bfChineseBold, 10, Font.NORMAL);
			// ******************end 定义pdf中文字体*****************

			// ****************设置pdf报表的样式表*********************

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
			// ****************end 设置pdf报表的样式表*********************
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return css;
	}

	public static Table getHeader() {
		Table headerTable = new Table();
		// 设置表格的宽度比例(百分比)
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
		tc.setContent("中国XXX股份有限公司XXX分公司");
		tr.getCell(1).setIsHidden(true);
		tr.getCell(2).setIsHidden(true);

		tr = new TableRow(3);
		headerTable.addRow(tr);
		tc = tr.getCell(0);
		tc.setColSpan(3);
		tc.setAlign(tc.ALIGN_CENTER);
		tc.setContent("产品销售统计报表");
		tc.setCssClass(Report.TITLE_TYPE);
		tr.getCell(1).setIsHidden(true);
		tr.getCell(2).setIsHidden(true);

		tr = new TableRow(3);
		headerTable.addRow(tr);

		tr = new TableRow(3);
		headerTable.addRow(tr);
		tc = tr.getCell(0);
		tc.setContent("单位：xxx分公司");
		tc.setAlign(tc.ALIGN_LEFT);
		tc = tr.getCell(1);
		tc.setContent("日期:2003-11-11至2003-11-16");
		tc.setAlign(tc.ALIGN_CENTER);
		tc = tr.getCell(2);
		tc.setContent("单位：吨  元");
		tc.setAlign(tc.ALIGN_RIGHT);
		return headerTable;
	}

	public static HeaderTable getColHeader() {
		HeaderTable th = new HeaderTable();
		TableRow thr = new TableRow(4);
		thr.setCell(0, new TableCell("产品名称"));
		thr.setCell(1, new TableCell("产品xx量"));
		thr.setCell(2, new TableCell("产品xx销售量"));
		thr.setCell(3, new TableCell("产品xx销售额"));
		th.addRow(thr);

		// TableCell cell = new TableCell("产品名称");
		// thr.addCell(new TableCell("产品名称"));
		return th;
	}

	public static Table getRecord() {
		Table t = new Table();
		double multip = 100.00;
		for (int i = 0; i < 6; i++) {
			TableRow tr = new TableRow();

			tr.addCell(new TableCell("产品" + i));
			tr.addCell(new TableCell("" + (i * multip)));
			tr.addCell(new TableCell("" + (i + 1) * multip));
			tr.addCell(new TableCell("" + (i + 2) * multip));
			t.addRow(tr);

			t.addRow(tr.cloneAll());

			tr = new TableRow();
			tr.addCell(new TableCell("产品" + i));
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
		tr.getCell(0).setContent("制表人:xxx");
		tc.setAlign(tc.ALIGN_LEFT);
		tr.getCell(1).setContent("审核人:xxx");
		tc.setAlign(tc.ALIGN_CENTER);
		tr.getCell(2).setContent("制表日期:xxx");
		tc.setAlign(tc.ALIGN_RIGHT);

		return footerTable;
	}

}
