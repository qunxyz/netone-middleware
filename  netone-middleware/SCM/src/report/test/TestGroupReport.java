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
 * �����л��ܱ������ӡ� ��main������ʼִ�С� ע��:�����жԳ���ε�ע��,������:�ڳ����ǰ���ע��,�ڳ���κ���ע���εĽ���.��:
 * ǰ��://****xxxx*********** ����://****end xxxx*******
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
	 * ���ݱ����������excel��ʽ�ı���.
	 * 
	 * @param report
	 *            �������
	 * @throws Exception
	 */
	public static void getExcelReport(Report report, OutputStream os)
			throws Exception {
		final String MY_STYLE1 = "MyStyle1";
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

				// �����Զ������ʽ
				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				this.setStyle(MY_STYLE1, style);

				
				// �����Զ������ʽ2
				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				this.setStyle("donstyle", style);
				
				// ***************end ����EXCEL�������ʽ��******************
			}
		};

		// ��ʾExcelPrinter().print()������onlyUseCssClassOnDataCell������Ч����
		// ���Ϊtrue����������ʽ�������ò�������;���������ã��������Ӵ���style,���ܻᳬ��excel�����ơ�
//		report.getBody().getData().getCell(2, 2).setContent("xxxxxx");
//		report.getBody().getData().getCell(2, 2)
//				.setAlign(Rectangle.ALIGN_CENTER);

		// Ӧ���Զ������ʽ
//		report.getHeaderTable().getCell(1, 1).setCssClass("donstyle");
		report.getHeaderTable().getCol(2).setType("donstyle");

		// ִ��EXCEL��ʽ��������
		new ExcelPrinter().print(report, css, os, false);

		System.out.println("����Excel��ʽ����ɹ���");
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
		// ����������ļ�
		FileOutputStream fo = new FileOutputStream("group.html");

		// ***************����HTML�������ʽ��******************
		HTMLCss css = new HTMLCss();
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt ����;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt ����;");
		css.setTitle("font: bold 18pt ;");
		css.setData("font: 12pt");
		// ***************end ����HTML�������ʽ��******************

		// ִ��HTML��ʽ��������
		new HTMLPrinter().print(report, css, os);

		System.out.println("����HTML��ʽ����ɹ���");
	}

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
		// ****************end ����pdf�������ʽ��*********************

		// ִ��PDF��ʽ��������
		new PDFPrinter().print(report, css, os);

		System.out.println("����PDF��ʽ����ɹ���");
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

		System.out.println("����CSV��ʽ����ɹ���");
	}

	/**
	 * ���ԭʼ���ݱ��.����һ���������ݱ��.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Table getTable() throws Exception {

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

	/**
	 * Ϊ�������ñ���ͷ��β.
	 * 
	 * @param report
	 *            Ҫ���õı���
	 * @throws ReportException
	 */
	private static void setTitleFooter(Report report) throws ReportException {
		// *****************���ñ���ͷ*********************
		Table headerTable = new Table();
		// ���ñ��Ŀ�ȱ���(�ٷֱ�)
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

		// *****************end ���ñ���ͷ*********************

		// *****************���ñ���β*********************
		Table footerTable = new Table();
		report.setFooterTable(footerTable);

		tr = new TableRow(3);
		footerTable.setBorder(0);
		footerTable.setAlign(footerTable.ALIGN_CENTER);
		footerTable.addRow(tr);
		tr.getCell(0).setContent("�Ʊ���:xxx");
		tc.setAlign(tc.ALIGN_LEFT);
		tr.getCell(1).setContent("�����:xxx");
		tc.setAlign(tc.ALIGN_CENTER);
		tr.getCell(2).setContent("�Ʊ�����:xxx");
		tc.setAlign(tc.ALIGN_RIGHT);
		// *****************end ���ñ���β*********************

	}

	/**
	 * ��ñ������
	 * 
	 * @throws Exception
	 * @return Report
	 */
	public static Report getReport() throws Exception {
		// ���������
		ReportManager rm = new ReportManager();

		// ���ԭʼ���ݱ��
		Table t = getTable();

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end ���ñ������岿��**************

		// ***********��ָ���з���**********
		int[] cols = { 0, 1 };
		// �ϲ��������ڵ�ֵͬ��Ԫ
		t = rm.mergeSameCells(t, cols, rm.COLUMN_ORIENTATION);
		// ���е��Ⱥ�˳��,��ɷ���
		t = rm.split(t, cols);
		// ***********end ��ָ���з���**********

		// *****************���ñ�������********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(75);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end ���ñ�������********************

		// ********************������ͳ��*********************
		int[] totalCols = { 1, 2, 3 };
		rm.generateRowTotal(t, totalCols, true, new SumArithmetic());
		// ********************end ������ͳ��*********************

		// *********************��ʽ������**********************
		int[] formatCols = { 1, 2, 3 };
		t = rm.formatData(t, formatCols, new DefaultFormatter());
		// *********************end ��ʽ������**********************

		// *****************���ñ������������ͷ*********************/
		HeaderTable th = new HeaderTable();
		report.getBody().setTableColHeader(th);
		TableRow thr = new TableRow(4);
		th.addRow(thr);
		thr.setCell(0, new TableCell("��Ʒ����"));
		thr.setCell(1, new TableCell("��Ʒxx��"));
		thr.setCell(2, new TableCell("��Ʒxx������"));
		thr.setCell(3, new TableCell("��Ʒxx���۶�"));
		// *****************end ���ñ������������ͷ*********************/

		// ���ñ����ͷ��β
		setTitleFooter(report);

		return report;
	}

	/**
	 * ���ɱ���.
	 * 
	 * @throws Exception
	 */
	public static void generateReport() throws Exception {
		Report report = getReport();
		// ����ļ�
		FileOutputStream fo = null;
		try {
			// ����HTML��ʽ����

			fo = new FileOutputStream("group.html");
			getHTMLReport(report, fo);
			fo.close();
//
//			// ����PDF��ʽ����
			fo = new FileOutputStream("group.pdf");
			getPDFReport(report, fo);
			fo.close();
//
//			// ����CSV��ʽ����
//			fo = new FileOutputStream("group.csv");
//			getCSVReport(report, fo);
//			fo.close();

			// ����Excel��ʽ����
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
