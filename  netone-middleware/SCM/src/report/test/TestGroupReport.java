package test;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lucaslee.report.DefaultFormatter;
import com.lucaslee.report.ReportException;
import com.lucaslee.report.ReportManager;
import com.lucaslee.report.grouparithmetic.SumArithmetic;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.lucaslee.report.printer.CSVPrinter;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.ExcelPrinter;
import com.lucaslee.report.printer.HTMLCss;
import com.lucaslee.report.printer.HTMLPrinter;
import com.lucaslee.report.printer.PDFCss;
import com.lucaslee.report.printer.PDFCssItem;
import com.lucaslee.report.printer.PDFPrinter;

/**
 * 生成行汇总报表例子。 从main方法开始执行。 注意:例子中对程序段的注释,方法是:在程序段前面加注释,在程序段后面注明段的结束.如:
 * 前面://****xxxx*********** 后面://****end xxxx*******
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:Lucas-lee Soft
 * </p>
 * 
 * @author Lucas Lee
 * @version 1.0
 */
public class TestGroupReport {
	public TestGroupReport() {
	}

	/**
	 * 根据报表对象生成excel格式的报表.
	 * 
	 * @param report
	 *            报表对象
	 * @throws Exception
	 */
	public static void getExcelReport(Report report, OutputStream os)
			throws Exception {
		final String MY_STYLE1 = "MyStyle1";
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

				// 设置自定义的样式
				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				this.setStyle(MY_STYLE1, style);

				
				// 设置自定义的样式2
				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				this.setStyle("donstyle", style);
				
				// ***************end 设置EXCEL报表的样式表******************
			}
		};

		// 演示ExcelPrinter().print()方法中onlyUseCssClassOnDataCell参数的效果。
		// 如果为true，则下列样式属性设置不起作用;否则起作用，但会增加大量style,可能会超过excel的限制。
//		report.getBody().getData().getCell(2, 2).setContent("xxxxxx");
//		report.getBody().getData().getCell(2, 2)
//				.setAlign(Rectangle.ALIGN_CENTER);

		// 应用自定义的样式
//		report.getHeaderTable().getCell(1, 1).setCssClass("donstyle");
		report.getHeaderTable().getCol(2).setType("donstyle");

		// 执行EXCEL格式报表的输出
		new ExcelPrinter().print(report, css, os, false);

		System.out.println("生成Excel格式报表成功。");
	}

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

	public static void getHTMLReport(Report report, OutputStream os)
			throws Exception {
		// 设置输出的文件
		FileOutputStream fo = new FileOutputStream("group.html");

		// ***************设置HTML报表的样式表******************
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 隶书;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt 隶书;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 隶书;");
		css.setTitle("font: bold 18pt ;");
		css.setData("font: 12pt");
		// ***************end 设置HTML报表的样式表******************

		// 执行HTML格式报表的输出
		new HTMLPrinter().print(report, css, os);

		System.out.println("生成HTML格式报表成功。");
	}

	public static void getPDFReport(Report report, OutputStream os)
			throws Exception {
		// ******************定义pdf中文字体*****************
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		BaseFont bfChineseBold = BaseFont.createFont("STSong-Light,Bold",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font FontChinese = new Font(bfChinese, 10, Font.NORMAL);
		Font FontChineseBold = new Font(bfChineseBold, 10, Font.NORMAL);
		// ******************end 定义pdf中文字体*****************

		// ****************设置pdf报表的样式表*********************
		PDFCss css = new PDFCss();

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

		// 执行PDF格式报表的输出
		new PDFPrinter().print(report, css, os);

		System.out.println("生成PDF格式报表成功。");
	}

	/**
	 * 根据报表对象生成csv格式的报表.
	 * 
	 * @param report
	 *            报表对象
	 * @throws Exception
	 */
	public static void getCSVReport(Report report, OutputStream os)
			throws Exception {
		// 执行CSV格式报表的输出
		new CSVPrinter().print(report, os);

		System.out.println("生成CSV格式报表成功。");
	}

	/**
	 * 获得原始数据表格.生成一个例子数据表格.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Table getTable() throws Exception {

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

	/**
	 * 为报表设置报表头和尾.
	 * 
	 * @param report
	 *            要设置的报表
	 * @throws ReportException
	 */
	private static void setTitleFooter(Report report) throws ReportException {
		// *****************设置报表头*********************
		Table headerTable = new Table();
		// 设置表格的宽度比例(百分比)
		int[] widths = { 20, 60, 20 };
		headerTable.setWidths(widths);
		report.setHeaderTable(headerTable);

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

		// *****************end 设置报表头*********************

		// *****************设置报表尾*********************
		Table footerTable = new Table();
		report.setFooterTable(footerTable);

		tr = new TableRow(3);
		footerTable.setBorder(0);
		footerTable.setAlign(footerTable.ALIGN_CENTER);
		footerTable.addRow(tr);
		tr.getCell(0).setContent("制表人:xxx");
		tc.setAlign(tc.ALIGN_LEFT);
		tr.getCell(1).setContent("审核人:xxx");
		tc.setAlign(tc.ALIGN_CENTER);
		tr.getCell(2).setContent("制表日期:xxx");
		tc.setAlign(tc.ALIGN_RIGHT);
		// *****************end 设置报表尾*********************

	}

	/**
	 * 获得报表对象
	 * 
	 * @throws Exception
	 * @return Report
	 */
	public static Report getReport() throws Exception {
		// 报表管理器
		ReportManager rm = new ReportManager();

		// 获得原始数据表格
		Table t = getTable();

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end 设置报表主体部分**************

		// ***********按指定列分组**********
		int[] cols = { 0, 1 };
		// 合并列中相邻的同值单元
		t = rm.mergeSameCells(t, cols, rm.COLUMN_ORIENTATION);
		// 按列的先后顺序,完成分组
		t = rm.split(t, cols);
		// ***********end 按指定列分组**********

		// *****************设置表格的属性********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(75);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end 设置表格的属性********************

		// ********************进行行统计*********************
		int[] totalCols = { 1, 2, 3 };
		rm.generateRowTotal(t, totalCols, true, new SumArithmetic());
		// ********************end 进行行统计*********************

		// *********************格式化数据**********************
		int[] formatCols = { 1, 2, 3 };
		t = rm.formatData(t, formatCols, new DefaultFormatter());
		// *********************end 格式化数据**********************

		// *****************设置报表主体表格的列头*********************/
		HeaderTable th = new HeaderTable();
		report.getBody().setTableColHeader(th);
		TableRow thr = new TableRow(4);
		th.addRow(thr);
		thr.setCell(0, new TableCell("产品名称"));
		thr.setCell(1, new TableCell("产品xx量"));
		thr.setCell(2, new TableCell("产品xx销售量"));
		thr.setCell(3, new TableCell("产品xx销售额"));
		// *****************end 设置报表主体表格的列头*********************/

		// 设置报表的头和尾
		setTitleFooter(report);

		return report;
	}

	/**
	 * 生成报表.
	 * 
	 * @throws Exception
	 */
	public static void generateReport() throws Exception {
		Report report = getReport();
		// 输出文件
		FileOutputStream fo = null;
		try {
			// 生成HTML格式报表

			fo = new FileOutputStream("group.html");
			getHTMLReport(report, fo);
			fo.close();
//
//			// 生成PDF格式报表
			fo = new FileOutputStream("group.pdf");
			getPDFReport(report, fo);
			fo.close();
//
//			// 生成CSV格式报表
//			fo = new FileOutputStream("group.csv");
//			getCSVReport(report, fo);
//			fo.close();

			// 生成Excel格式报表
			fo = new FileOutputStream("group.xls");
			getExcelReport(report, fo);
			fo.close();
		} finally {
			if (fo != null)
				fo.close();
		}

	}

	public static void main(String[] args) throws Exception {
		generateReport();
	}

}
