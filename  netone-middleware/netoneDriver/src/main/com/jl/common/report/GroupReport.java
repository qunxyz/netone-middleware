/**
 * 
 */
package com.jl.common.report;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lucaslee.report.ReportManager;
import com.lucaslee.report.grouparithmetic.SumArithmetic;
import com.lucaslee.report.model.Report;
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
 * 汇总报表格式输出
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-12-21 上午09:19:12
 * @history
 */
public class GroupReport extends BaseReport {

	public GroupReport() {

	}

	/**
	 * 根据报表对象生成excel格式的报表.
	 * 
	 * @param report
	 *            报表对象
	 * @throws Exception
	 */
	public void getExcelReport(Report report, OutputStream os) throws Exception {
		// 执行EXCEL格式报表的输出
		ExcelCss css = new ExcelCss() {
			final String CUSTOM_STYLE_RIGHT = "CUSTOM_STYLE_RIGHT";

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
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				setStyleBorder(style, true);
				this.setStyle(Report.HEAD_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontBig);
				this.setStyle(Report.TITLE_TYPE, style);

				// style = workbook.createCellStyle();
				// style.setFont(fontNormal);
				// setStyleBorder(style, true);
				// style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				// this.setStyle(Report.DATA_TYPE, style);

				// style = workbook.createCellStyle();
				// style.setFont(fontNormal);
				//
				// 设置缺省样式。设置此项以解决Excel样式过多的错误。
				this.setDefaultStyle(style);

				this.setDefaultColumnWidth((short) 10);

				// 设置自定义的样式 数字类型居右
				// style = workbook.createCellStyle();
				// style.setFont(fontNormal);
				// style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				// this.setStyle(CUSTOM_STYLE_RIGHT, style);
				// ***************end 设置EXCEL报表的样式表******************
			}
		};
		new ExcelPrinter().print(report, css, os, false);
	}

	public void setStyleBorder(HSSFCellStyle style, boolean haveBorder) {
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

	public void getHTMLReport(Report report, OutputStream os) throws Exception {
		// ***************设置HTML报表的样式表******************
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 宋体;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt 宋体;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 宋体;");
		css.setTitle("font: bold 18pt 宋体;");
		css.setData("font: 12pt");
		// ***************end 设置HTML报表的样式表******************

		// 执行HTML格式报表的输出
		new HTMLPrinter().print(report, css, os);
	}

	public void getPDFReport(Report report, OutputStream os) throws Exception {
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

	}

	/**
	 * 根据报表对象生成csv格式的报表.
	 * 
	 * @param report
	 *            报表对象
	 * @throws Exception
	 */
	public void getCSVReport(Report report, OutputStream os) throws Exception {
		// 执行CSV格式报表的输出
		new CSVPrinter().print(report, os);
	}

	public void format(String _format, String titleName, Report report,
			HttpServletResponse response) {
		String format = _format;
		try {
			if (format == null || format.length() == 0)
				return;
			
			if (format.equals("html")) {
				// 生成HTML格式报表
				response.setContentType("text/html; charset=GBK");
				response.setCharacterEncoding("GBK");
				OutputStream os = response.getOutputStream();
				this.getHTMLReport(report, os);
				os.flush();
				os.close();
			} else if (format.equals("excel")) {
				// 生成Excel格式报表
				response.setHeader("Content-Disposition",
						"attachment; filename="
								+ new String(titleName.getBytes("GBK"),
										"ISO8859-1") + ".xls");
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				this.getExcelReport(report, os);
				os.flush();
				os.close();
			} else if (format.equals("csv")) {
				// 生成CSV格式报表
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				this.getCSVReport(report, os);
				os.flush();
				os.close();
			} else if (format.equals("pdf")) {
				// 生成PDF格式报表
				response.setContentType("application/pdf");
				OutputStream os = response.getOutputStream();
				this.getPDFReport(report, os);
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// 输出文件
		FileOutputStream fo = null;
		GroupReport groupReport = new GroupReport();
		try {
			ReportExt reportExt = new ReportExt();
			// 生成Excel格式报表
			fo = new FileOutputStream("group.xls");

			Table t = new Table();
			double multip = 100.00;
			for (int i = 0; i < 50; i++) {
				TableRow tr = new TableRow();

				tr.addCell(new TableCell("产品" + i));
				tr.addCell(new TableCell("" + (i * multip)));
				tr.addCell(new TableCell("" + (i + 1) * multip));
				tr.addCell(new TableCell("" + (i + 2) * multip));
				tr.addCell(new TableCell("" + (i + 3) * multip));
				tr.addCell(new TableCell("" + (i + 4) * multip));
				t.addRow(tr);
			}
			String[] headerSet = { "列1", "列2", "列3", "列4", "列5", "列6" };
			Report report = reportExt.setSimpleColHeader(t, headerSet);
			ReportManager rm = new ReportManager();
			int[] totalCols = { 4 };
			rm.generateRowTotal(t, totalCols, false, new SumArithmetic());
			// ********************end 进行行统计*********************
			reportExt.setSimpleTitleFooter(report, "测试标题");
			groupReport.getExcelReport(report, fo);
			// GroupReport.getHTMLReport(report, os);
			fo.close();
		} finally {
			if (fo != null)
				fo.close();
		}
	}

}
