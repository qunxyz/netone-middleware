package test;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lucaslee.report.DefaultFormatter;
import com.lucaslee.report.ReportException;
import com.lucaslee.report.ReportManager;
import com.lucaslee.report.grouparithmetic.SumArithmetic;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;
import com.lucaslee.report.model.crosstable.CrossCol;
import com.lucaslee.report.model.crosstable.CrossTable;
import com.lucaslee.report.model.crosstable.HeadCol;
import com.lucaslee.report.printer.CSVPrinter;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.ExcelPrinter;
import com.lucaslee.report.printer.HTMLCss;
import com.lucaslee.report.printer.HTMLPrinter;
import com.lucaslee.report.printer.PDFCss;
import com.lucaslee.report.printer.PDFCssItem;
import com.lucaslee.report.printer.PDFPrinter;

/**
 * ���ɽ���������ӡ� ��main������ʼִ�С� ע��:�����жԳ���ε�ע��,������:�ڳ����ǰ���ע��,�ڳ���κ���ע���εĽ���.��:
 * ǰ��://****xxxx*********** ����://****end xxxx*******
 * 
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
public class TestCrossReport {
	public TestCrossReport() {
	}

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
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontNormal);
				TestGroupReport.setStyleBorder(style, true);
				this.setStyle(Report.GROUP_TOTAL_TYPE, style);

				this.setStyle(Report.TOTAL_TYPE, style);

				style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontBold);
				TestGroupReport.setStyleBorder(style, true);
				this.setStyle(Report.HEAD_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontBig);
				this.setStyle(Report.TITLE_TYPE, style);

				style = workbook.createCellStyle();
				style.setFont(fontNormal);
				TestGroupReport.setStyleBorder(style, true);
				this.setStyle(Report.DATA_TYPE, style);

				style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.ORANGE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontNormal);
				TestGroupReport.setStyleBorder(style, true);
				this.setStyle(Report.CROSS_HEAD_HEAD_TYPE, style);

				this.setDefaultColumnWidth((short) 10);
				// ***************end ����EXCEL�������ʽ��******************
			}
		};

		// ִ��EXCEL��ʽ��������
		new ExcelPrinter().print(report, css, os, false);

		System.out.println("����Excel��ʽ����ɹ���");
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
		css.setTitle("font: bold 18pt ;");
		css.setData("font: 12pt");
		css.setCrossHeadHead("BACKGROUND-COLOR: #a68763; font: 9pt ");
		// ***************end ����HTML�������ʽ��******************

		// ִ��HTML��ʽ��������
		new HTMLPrinter().print(report, css, os);

		System.out.println("����HTML��ʽ����ɹ���");
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
	 * ���ñ����ͷ����β��.
	 * 
	 * @param report
	 *            Ҫ����ͷβ�ı������.
	 * @throws ReportException
	 */
	private static void setTitleFooter(Report report) throws ReportException {
		// *****************���ñ���ͷ��*********************
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
		tc.setContent("�������ڣ�2003-11-11��2003-11-16");
		tc.setAlign(tc.ALIGN_CENTER);
		tc = tr.getCell(2);
		tc.setAlign(tc.ALIGN_RIGHT);
		tc.setContent("��λ����  Ԫ");
		// *****************end ���ñ���ͷ��*********************

		// *****************���ñ���β��*********************
		Table footerTable = new Table();
		report.setFooterTable(footerTable);

		tr = new TableRow(3);
		footerTable.setBorder(0);
		footerTable.setAlign(footerTable.ALIGN_CENTER);
		footerTable.addRow(tr);
		tr.getCell(0).setContent("�Ʊ���:xxx");
		tr.getCell(0).setAlign(tc.ALIGN_LEFT);
		tr.getCell(1).setContent("�����:xxx");
		tr.getCell(1).setAlign(tc.ALIGN_CENTER);
		tr.getCell(2).setContent("�Ʊ�����:xxx");
		tr.getCell(2).setAlign(tc.ALIGN_RIGHT);
		// *****************end ���ñ���β��*********************
	}

	/**
	 * ��xml�ļ����ԭʼ���ݱ��.ע������ʹ�õ�crossTabSample.xmlӦ������Ϊ���ļ����ڵ�·��.
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Table getTableByXML() throws Exception {
		// xml�ļ�·��,Ӧ������ΪcrossTabSample.xml���ڵ�·��
		String fileName = "crossTabSample.xml";
		Table t = new Table();

		// ��JAXP��ȡxml�ļ�
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(new File(fileName));
		Element e = doc.getDocumentElement();
		NodeList xmlTab = e.getChildNodes();
		for (int i = 0; i < xmlTab.getLength(); i++) {
			if (xmlTab.item(i).getNodeName().equals("Row")) {
				TableRow tr = new TableRow();
				NodeList xmlTR = xmlTab.item(i).getChildNodes();
				for (int j = 0; j < xmlTR.getLength(); j++) {
					if (xmlTR.item(j).getNodeName().equals("data")) {
						TableCell tc = new TableCell();
						Node node = xmlTR.item(j).getFirstChild();
						if (node != null) {
							tc.setContent(node.getNodeValue());
						}
						tr.addCell(tc);
					}
				}
				t.addRow(tr);
			}
		}
		return t;
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

		// �������ԭʼ���ݱ�����
		Table t = getTableByXML();

		// ************���彻���*************
		HeadCol[] colH = { new HeadCol(2, "����") };
		HeadCol[] rowH = { new HeadCol(0, "Ʒ��"), new HeadCol(1, "�г�") };
		Vector sort1 = new Vector();
		sort1.add("����λ");
		sort1.add("������");
		colH[0].setSortSeq(sort1);

		CrossCol crs = new CrossCol(3, "���۶�", new SumArithmetic());
		CrossTable crossTab = new CrossTable(colH, rowH, crs);
		// ************end ���彻���*************

		// ͨ��ԭʼ���ݺͽ����Ķ������ɽ����
		t = rm.generateCrossTab(t, crossTab);

		// ������ͳ��
		t = rm.generateCrossTabRowTotal(t, crossTab, true, new SumArithmetic());

		// ������ͳ��
		t = rm
				.generateCrossTabColTotal(t, crossTab, false,
						new SumArithmetic());

		// ��ʽ������
		t = rm.formatData(t, crossTab, new DefaultFormatter());

		// ****************���屨�����������****************
		Report report = new Report();

		// ���������Ϊ���������
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// ���ñ����ͷ��β������
		setTitleFooter(report);
		// ****************end ���屨�����������************
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
			fo = new FileOutputStream("crossTab.html");
			getHTMLReport(report, fo);
			fo.close();

			// ����PDF��ʽ����
			fo = new FileOutputStream("crossTab.pdf");
			getPDFReport(report, fo);
			fo.close();

			// ����CSV��ʽ����
			fo = new FileOutputStream("crossTab.csv");
			getCSVReport(report, fo);
			fo.close();

			// ����Excel��ʽ����
			fo = new FileOutputStream("crossTab.xls");
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
