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
 * ���汨���ʽ���
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-12-21 ����09:23:08
 * @history
 */
public class CrossReport extends BaseReport{
	/**
	 * ���ݱ����������csv��ʽ�ı���.
	 * 
	 * @param report
	 *            �������
	 * @throws Exception
	 */
	public static void getExcelReport(Report report, OutputStream os)
			throws Exception {

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
				// ***************end ����EXCEL�������ʽ��******************
			}
		};

		// ִ��EXCEL��ʽ��������
		new ExcelPrinter().print(report, css, os, false);

	}

	/**
	 * ���ݱ����������HTML��ʽ�ı���.
	 * 
	 * @param report
	 *            �������
	 * @throws Exception
	 */
	public static void getHTMLReport(Report report, OutputStream os)
			throws Exception {

		// ***************����HTML�������ʽ��******************
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt ����;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setTitle("font: bold 18pt ����;");
		css.setData("font: 12pt");
		css.setCrossHeadHead("BACKGROUND-COLOR: #a68763; font: 9pt ");
		// ***************end ����HTML�������ʽ��******************

		// ִ��HTML��ʽ��������
		new HTMLPrinter().print(report, css, os);

	}

	/**
	 * ���ݱ����������pdf��ʽ�ı���.
	 * 
	 * @param report
	 *            �������
	 * @throws Exception
	 */
	public static void getPDFReport(Report report, OutputStream os)
			throws Exception {
		// ******************����pdf��������*****************
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		BaseFont bfChineseBold = BaseFont.createFont("STSong-Light,Bold",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font FontChinese = new Font(bfChinese, 10, Font.NORMAL);
		Font FontChineseBold = new Font(bfChineseBold, 10, Font.NORMAL);
		// ******************end ����pdf��������*****************

		// ****************����pdf�������ʽ��*********************
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
		// ****************end ����pdf�������ʽ��*********************

		// ִ��PDF��ʽ��������
		new PDFPrinter().print(report, css, os);

	}

	/**
	 * ���ݱ����������csv��ʽ�ı���.
	 * 
	 * @param report
	 *            �������
	 * @throws Exception
	 */
	public static void getCSVReport(Report report, OutputStream os)
			throws Exception {
		// ִ��CSV��ʽ��������
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
				// ����HTML��ʽ����
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
				// ����Excel��ʽ����
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
				// ����CSV��ʽ����
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				groupReport.getCSVReport(report, os);
			} else if (format.equals("pdf")) {
				// ����PDF��ʽ����
				response.setContentType("application/pdf");
				OutputStream os = response.getOutputStream();
				groupReport.getPDFReport(report, os);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
