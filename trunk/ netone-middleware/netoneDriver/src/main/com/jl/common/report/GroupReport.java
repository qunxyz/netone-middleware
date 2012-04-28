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
 * ���ܱ����ʽ���
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-12-21 ����09:19:12
 * @history
 */
public class GroupReport extends BaseReport {

	public GroupReport() {

	}

	/**
	 * ���ݱ����������excel��ʽ�ı���.
	 * 
	 * @param report
	 *            �������
	 * @throws Exception
	 */
	public void getExcelReport(Report report, OutputStream os) throws Exception {
		// ִ��EXCEL��ʽ��������
		ExcelCss css = new ExcelCss() {
			final String CUSTOM_STYLE_RIGHT = "CUSTOM_STYLE_RIGHT";

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
				// ����ȱʡ��ʽ�����ô����Խ��Excel��ʽ����Ĵ���
				this.setDefaultStyle(style);

				this.setDefaultColumnWidth((short) 10);

				// �����Զ������ʽ �������;���
				// style = workbook.createCellStyle();
				// style.setFont(fontNormal);
				// style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				// this.setStyle(CUSTOM_STYLE_RIGHT, style);
				// ***************end ����EXCEL�������ʽ��******************
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
		// ***************����HTML�������ʽ��******************
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt ����;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setTitle("font: bold 18pt ����;");
		css.setData("font: 12pt");
		// ***************end ����HTML�������ʽ��******************

		// ִ��HTML��ʽ��������
		new HTMLPrinter().print(report, css, os);
	}

	public void getPDFReport(Report report, OutputStream os) throws Exception {
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
	public void getCSVReport(Report report, OutputStream os) throws Exception {
		// ִ��CSV��ʽ��������
		new CSVPrinter().print(report, os);
	}

	public void format(String _format, String titleName, Report report,
			HttpServletResponse response) {
		String format = _format;
		try {
			if (format == null || format.length() == 0)
				return;
			
			if (format.equals("html")) {
				// ����HTML��ʽ����
				response.setContentType("text/html; charset=GBK");
				response.setCharacterEncoding("GBK");
				OutputStream os = response.getOutputStream();
				this.getHTMLReport(report, os);
				os.flush();
				os.close();
			} else if (format.equals("excel")) {
				// ����Excel��ʽ����
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
				// ����CSV��ʽ����
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				this.getCSVReport(report, os);
				os.flush();
				os.close();
			} else if (format.equals("pdf")) {
				// ����PDF��ʽ����
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
		// ����ļ�
		FileOutputStream fo = null;
		GroupReport groupReport = new GroupReport();
		try {
			ReportExt reportExt = new ReportExt();
			// ����Excel��ʽ����
			fo = new FileOutputStream("group.xls");

			Table t = new Table();
			double multip = 100.00;
			for (int i = 0; i < 50; i++) {
				TableRow tr = new TableRow();

				tr.addCell(new TableCell("��Ʒ" + i));
				tr.addCell(new TableCell("" + (i * multip)));
				tr.addCell(new TableCell("" + (i + 1) * multip));
				tr.addCell(new TableCell("" + (i + 2) * multip));
				tr.addCell(new TableCell("" + (i + 3) * multip));
				tr.addCell(new TableCell("" + (i + 4) * multip));
				t.addRow(tr);
			}
			String[] headerSet = { "��1", "��2", "��3", "��4", "��5", "��6" };
			Report report = reportExt.setSimpleColHeader(t, headerSet);
			ReportManager rm = new ReportManager();
			int[] totalCols = { 4 };
			rm.generateRowTotal(t, totalCols, false, new SumArithmetic());
			// ********************end ������ͳ��*********************
			reportExt.setSimpleTitleFooter(report, "���Ա���");
			groupReport.getExcelReport(report, fo);
			// GroupReport.getHTMLReport(report, os);
			fo.close();
		} finally {
			if (fo != null)
				fo.close();
		}
	}

}
