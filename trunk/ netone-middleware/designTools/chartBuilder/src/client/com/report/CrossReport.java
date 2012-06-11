/**
 * 
 */
package com.report;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.printer.CSVPrinter;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.ExcelPrinter;
import com.lucaslee.report.printer.HTMLCss;
import com.lucaslee.report.printer.HTMLPrinter;
import com.lucaslee.report.printer.PDFCss;
import com.lucaslee.report.printer.PDFCssItem;
import com.lucaslee.report.printer.PDFPrinter;

/**
 * 
 * 交叉报表格式输出
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-12-21 上午09:23:08
 * @history
 */
public class CrossReport extends BaseReport{
	/**
	 * 根据报表对象生成csv格式的报表.
	 * 
	 * @param report
	 *            报表对象
	 * @throws Exception
	 */
	public static void getExcelReport(Report report, OutputStream os)
			throws Exception {

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
				GroupReport groupReport = new GroupReport();
				
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontNormal);
				groupReport.setStyleBorder(style, true);
				this.setStyle(Report.GROUP_TOTAL_TYPE, style);

				this.setStyle(Report.TOTAL_TYPE, style);

				style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontBold);
				groupReport.setStyleBorder(style, true);
				this.setStyle(Report.HEAD_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontBig);
				this.setStyle(Report.TITLE_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				groupReport.setStyleBorder(style, true);
				this.setStyle(Report.DATA_TYPE, style);

				style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.ORANGE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontNormal);
				groupReport.setStyleBorder(style, true);
				this.setStyle(Report.CROSS_HEAD_HEAD_TYPE, style);

				this.setDefaultColumnWidth((short) 10);
				// ***************end 设置EXCEL报表的样式表******************
			}
		};

		// 执行EXCEL格式报表的输出
		new ExcelPrinter().print(report, css, os, false);

	}

	/**
	 * 根据报表对象生成HTML格式的报表.
	 * 
	 * @param report
	 *            报表对象
	 * @throws Exception
	 */
	public static void getHTMLReport(Report report, OutputStream os)
			throws Exception {

		// ***************设置HTML报表的样式表******************
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 宋体;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt 宋体;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 宋体;");
		css.setTitle("font: bold 18pt 宋体;");
		css.setData("font: 12pt");
		css.setCrossHeadHead("BACKGROUND-COLOR: #a68763; font: 9pt ");
		// ***************end 设置HTML报表的样式表******************

		// 执行HTML格式报表的输出
		new HTMLPrinter().print(report, css, os);

	}

	/**
	 * 根据报表对象生成pdf格式的报表.
	 * 
	 * @param report
	 *            报表对象
	 * @throws Exception
	 */
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

		item = new PDFCssItem();
		item.setFont(new Font(bfChinese, 7, Font.NORMAL));
		item.setBackgroudColor(new Color(0xffdead));
		css.setCrossHeadHead(item);
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
	public static void getCSVReport(Report report, OutputStream os)
			throws Exception {
		// 执行CSV格式报表的输出
		new CSVPrinter().print(report, os);
	}
	
	public static void format(String _format, String titleName, Report report,
			HttpServletResponse response) {
		String format = _format;
		if (format == null || format.length() == 0)
			return;
		try {
			GroupReport groupReport = new GroupReport();
			if (format.equals("html")) {
				// 生成HTML格式报表
				response.setContentType("text/html; charset=GBK");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				groupReport.getHTMLReport(report, baos);
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<body>");
				out.println(new String(baos.toByteArray(), "GBK"));
				out.println("</body>");
				out.println("</html>");
				baos.flush();
				baos.close();
			} else if (format.equals("excel")) {
				// 生成Excel格式报表
				response.setHeader("Content-Disposition",
						"attachment; filename="
								+ new String(titleName.getBytes("GBK"),
										"ISO8859-1") + ".xls");
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				groupReport.getExcelReport(report, os);
				os.flush();
				os.close();
			} else if (format.equals("csv")) {
				// 生成CSV格式报表
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				groupReport.getCSVReport(report, os);
			} else if (format.equals("pdf")) {
				// 生成PDF格式报表
				response.setContentType("application/pdf");
				OutputStream os = response.getOutputStream();
				groupReport.getPDFReport(report, os);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
