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
 * 生成交叉表报表例子。 从main方法开始执行。 注意:例子中对程序段的注释,方法是:在程序段前面加注释,在程序段后面注明段的结束.如:
 * 前面://****xxxx*********** 后面://****end xxxx*******
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
				// ***************end 设置EXCEL报表的样式表******************
			}
		};

		// 执行EXCEL格式报表的输出
		new ExcelPrinter().print(report, css, os, false);

		System.out.println("生成Excel格式报表成功。");
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
		css.setGroupTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 隶书;");
		css.setHead("BACKGROUND-COLOR: #ffdead; font: bold 12pt 隶书;");
		css.setTotal("BACKGROUND-COLOR: #d8e4f1; font: bold 12pt 隶书;");
		css.setTitle("font: bold 18pt ;");
		css.setData("font: 12pt");
		css.setCrossHeadHead("BACKGROUND-COLOR: #a68763; font: 9pt ");
		// ***************end 设置HTML报表的样式表******************

		// 执行HTML格式报表的输出
		new HTMLPrinter().print(report, css, os);

		System.out.println("生成HTML格式报表成功。");
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
	 * 设置报表的头部和尾部.
	 * 
	 * @param report
	 *            要设置头尾的报表对象.
	 * @throws ReportException
	 */
	private static void setTitleFooter(Report report) throws ReportException {
		// *****************设置报表头部*********************
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
		tc.setContent("报表日期：2003-11-11至2003-11-16");
		tc.setAlign(tc.ALIGN_CENTER);
		tc = tr.getCell(2);
		tc.setAlign(tc.ALIGN_RIGHT);
		tc.setContent("单位：吨  元");
		// *****************end 设置报表头部*********************

		// *****************设置报表尾部*********************
		Table footerTable = new Table();
		report.setFooterTable(footerTable);

		tr = new TableRow(3);
		footerTable.setBorder(0);
		footerTable.setAlign(footerTable.ALIGN_CENTER);
		footerTable.addRow(tr);
		tr.getCell(0).setContent("制表人:xxx");
		tr.getCell(0).setAlign(tc.ALIGN_LEFT);
		tr.getCell(1).setContent("审核人:xxx");
		tr.getCell(1).setAlign(tc.ALIGN_CENTER);
		tr.getCell(2).setContent("制表日期:xxx");
		tr.getCell(2).setAlign(tc.ALIGN_RIGHT);
		// *****************end 设置报表尾部*********************
	}

	/**
	 * 从xml文件获得原始数据表格.注意其中使用的crossTabSample.xml应该设置为此文件所在的路径.
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Table getTableByXML() throws Exception {
		// xml文件路径,应该设置为crossTabSample.xml所在的路径
		String fileName = "crossTabSample.xml";
		Table t = new Table();

		// 用JAXP读取xml文件
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
	 * 获得报表对象
	 * 
	 * @throws Exception
	 * @return Report
	 */
	public static Report getReport() throws Exception {
		// 报表管理器
		ReportManager rm = new ReportManager();

		// 待处理的原始数据表格对象
		Table t = getTableByXML();

		// ************定义交叉表*************
		HeadCol[] colH = { new HeadCol(2, "厂家") };
		HeadCol[] rowH = { new HeadCol(0, "品种"), new HeadCol(1, "市场") };
		Vector sort1 = new Vector();
		sort1.add("本单位");
		sort1.add("长江牌");
		colH[0].setSortSeq(sort1);

		CrossCol crs = new CrossCol(3, "销售额", new SumArithmetic());
		CrossTable crossTab = new CrossTable(colH, rowH, crs);
		// ************end 定义交叉表*************

		// 通过原始数据和交叉表的定义生成交叉表
		t = rm.generateCrossTab(t, crossTab);

		// 进行行统计
		t = rm.generateCrossTabRowTotal(t, crossTab, true, new SumArithmetic());

		// 进行列统计
		t = rm
				.generateCrossTabColTotal(t, crossTab, false,
						new SumArithmetic());

		// 格式化数据
		t = rm.formatData(t, crossTab, new DefaultFormatter());

		// ****************定义报表的其他部分****************
		Report report = new Report();

		// 将交叉表设为报表的主体
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);

		// 设置报表的头和尾两部分
		setTitleFooter(report);
		// ****************end 定义报表的其他部分************
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
			fo = new FileOutputStream("crossTab.html");
			getHTMLReport(report, fo);
			fo.close();

			// 生成PDF格式报表
			fo = new FileOutputStream("crossTab.pdf");
			getPDFReport(report, fo);
			fo.close();

			// 生成CSV格式报表
			fo = new FileOutputStream("crossTab.csv");
			getCSVReport(report, fo);
			fo.close();

			// 生成Excel格式报表
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
