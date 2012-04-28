package com.lucaslee.report.printer;

import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lucaslee.report.CssEngine;
import com.lucaslee.report.ReportException;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.crosstable.CrossTable;

/**
 * PDF格式打印机。
 * <p>
 * Title:
 * </p>
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
 * @author not attributable
 * @version 1.0
 */
public class PDFPrinter implements com.lucaslee.report.Printer {

	/**
	 * 默认字体
	 */
	private Font defaultFont;

	public PDFPrinter() throws DocumentException, IOException {
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		BaseFont bfChineseBold = BaseFont.createFont("STSong-Light,Bold",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		BaseFont bfEnglish = BaseFont.createFont("Helvetica", BaseFont.CP1252,
				BaseFont.NOT_EMBEDDED);
		Font FontChinese = new Font(bfChinese, 12, Font.NORMAL); // 创建中文字体
		defaultFont = new Font(bfChinese, 12);

	}

	/**
	 * 打印表格单元。
	 * 
	 * @param tableCell
	 *            表格单元
	 * @param css
	 *            样式表
	 * @param table
	 *            itext表格
	 * @param haveBorder
	 *            是否有边框
	 * @throws BadElementException
	 * @throws com.lowagie.text.DocumentException
	 * @throws IOException
	 * @throws ReportException
	 */
	private void print(com.lucaslee.report.model.TableCell tableCell,
			PDFCss css, com.lowagie.text.Table table, boolean haveBorder)
			throws BadElementException, com.lowagie.text.DocumentException,
			IOException, ReportException {

		if (tableCell.getIsHidden() == false) {
			Cell cell = null;

			// 应用样式表
			PDFCssItem item = null;
			if (Report.DATA_TYPE.equals(tableCell.getCssClass())) {
				item = css.getData();
			} else if (Report.GROUP_TOTAL_TYPE.equals(tableCell.getCssClass())) {
				item = css.getGroupTotal();
			} else if (Report.HEAD_TYPE.equals(tableCell.getCssClass())) {
				item = css.getHead();
			} else if (Report.TITLE_TYPE.equals(tableCell.getCssClass())) {
				item = css.getTitle();
			} else if (Report.TOTAL_TYPE.equals(tableCell.getCssClass())) {
				item = css.getTotal();
			} else if (Report.CROSS_HEAD_HEAD_TYPE.equals(tableCell
					.getCssClass())) {
				item = css.getCrossHeadHead();
			}
			if (item != null) {
				cell = getCell(tableCell, item.getFont());
				cell.setBackgroundColor(item.getBackgroudColor());
			} else {
				cell = getCell(tableCell, defaultFont);
			}
			cell.setHorizontalAlignment(getAlign(tableCell.getAlign()));

			if (Report.CROSS_HEAD_HEAD_TYPE.equals(tableCell.getCssClass())) {
				cell.setVerticalAlignment(com.lowagie.text.Table.ALIGN_MIDDLE);
			} else {
				cell.setVerticalAlignment(getVAlign(tableCell.getValign()));
			}

			cell.setColspan(tableCell.getColSpan());
			cell.setRowspan(tableCell.getRowSpan());

			// 设置是否允许换行。（itext的setNoWrap有bug，不用它。）
			if (tableCell.getNoWrap() == true
					&& Report.CROSS_HEAD_HEAD_TYPE.equals(tableCell
							.getCssClass())) {
				cell.setMaxLines(1);
			} else {
				cell.setMaxLines(Integer.MAX_VALUE);
			}
			if (haveBorder == false) {
				cell.setBorder(cell.NO_BORDER);
			}

			table.addCell(cell);
		}

	}

	/**
	 * 从report的TableCell获得itext的Cell对象。根据tc的CssClass不同做处理。
	 * 
	 * @param tc
	 *            TableCell
	 * @param font
	 *            pdf字体
	 * @return itext的Cell对象
	 * @throws BadElementException
	 */
	private Cell getCell(TableCell tc, Font font) throws BadElementException {
		Phrase phrase = new Phrase();
		Cell c = null;
		if (Report.CROSS_HEAD_HEAD_TYPE.equals(tc.getCssClass())) {// 是交叉表表头的表头

			String[] strs = PrinterUtil.getCrossHeadHeadContent(tc);
			String blank = "";
			for (int i = strs.length - 1; i >= 0; i--) {
				blank = " ";
				if (strs[i] != null) {
					Chunk chunk = new Chunk(strs[i] + blank, font);
					chunk.setTextRise((float) -1.7 * i + 4);
					phrase.add(chunk);
				}
			}
			c = new Cell(phrase);
		} else {
			phrase = new Phrase((String) tc.getContent(), font);
			c = new Cell(phrase);
		}
		return c;
	}

	/**
	 * 获得交叉表头的表头的内容。暂时保留.已经不用了.
	 * 
	 * @param tableCell
	 * @return
	 * @deprecated
	 */
	private String getCrossTabHeadHead2(TableCell tableCell) {
		String content;
		StringBuffer buf = new StringBuffer();
		CrossTable crossTab = (CrossTable) tableCell.getContent();

		int count = crossTab.getColHeader().length
				+ crossTab.getRowHeader().length + 1;
		String blank = "          ";
		String[] strs = new String[count];
		for (int i = 0; i < crossTab.getColHeader().length; i++) {
			strs[i] = crossTab.getColHeader()[i].getHeaderText();
		}
		strs[crossTab.getColHeader().length] = crossTab.getCrossCol()
				.getHeaderText();
		for (int i = 0; i < crossTab.getRowHeader().length; i++) {
			strs[i + crossTab.getColHeader().length + 1] = crossTab
					.getRowHeader()[i].getHeaderText();
		}
		for (int i = 0; i < strs.length; i++) {
			if (i != 0)

				for (int j = 0; j < strs.length - i - 1; j++) {
					buf.append(blank);
				}
			if (strs[i] != null)
				buf.append(strs[i]);
		}
		content = buf.toString();
		return content;

	}

	/**
	 * 打印表格行。
	 * 
	 * @param tableRow
	 *            表格行
	 * @param css
	 *            样式表
	 * @param table
	 *            itext表格
	 * @param haveBorder
	 *            是否有边框
	 * @throws BadElementException
	 * @throws DocumentException
	 * @throws IOException
	 * @throws ReportException
	 */
	private void print(com.lucaslee.report.model.TableRow tableRow, PDFCss css,
			com.lowagie.text.Table table, boolean haveBorder)
			throws BadElementException, DocumentException, IOException,
			ReportException {
		TableCell tempCell = null;
		for (int j = 0; j < tableRow.getCellCount(); j++) {
			print(tableRow.getCell(j), css, table, haveBorder);
		}
	}

	/**
	 * 获得水平对齐方式
	 * 
	 * @param i
	 *            水平对齐常数
	 * @return
	 */
	private int getAlign(int i) {
		switch (i) {
		case com.lucaslee.report.model.Rectangle.ALIGN_LEFT:
			return com.lowagie.text.Table.ALIGN_LEFT;
		case com.lucaslee.report.model.Rectangle.ALIGN_CENTER:
			return com.lowagie.text.Table.ALIGN_CENTER;
		case com.lucaslee.report.model.Rectangle.ALIGN_RIGHT:
			return com.lowagie.text.Table.ALIGN_RIGHT;
		default:
			throw new RuntimeException("无法识别的ALIGN参数。");
		}
	}

	/**
	 * 获得垂直对齐方式
	 * 
	 * @param i
	 *            垂直对齐方式常数
	 * @return
	 */
	private int getVAlign(int i) {
		switch (i) {
		case com.lucaslee.report.model.Rectangle.VALIGN_TOP:
			return com.lowagie.text.Table.ALIGN_TOP;
		case com.lucaslee.report.model.Rectangle.VALIGN_MIDDLE:
			return com.lowagie.text.Table.ALIGN_MIDDLE;
		case com.lucaslee.report.model.Rectangle.VALIGN_BOTTOM:
			return com.lowagie.text.Table.ALIGN_BOTTOM;
		default:
			throw new RuntimeException("无法识别的VALIGN参数。");
		}

	}

	/**
	 * 设置矩形的多个参数
	 * 
	 * @param itextRect
	 * @param reportRect
	 */
	private void setMultiParam(com.lowagie.text.Rectangle itextRect,
			com.lucaslee.report.model.Rectangle reportRect) {
		itextRect.setBorderWidth(reportRect.getBorder() - 1); // 因为pdf的边框比较粗
		itextRect.setBorderColor(reportRect.getBordercolor());
		itextRect.setBackgroundColor(reportRect.getBackgroudColor());
	}

	/**
	 * 打印报表到输出流
	 * 
	 * @param r
	 *            报表
	 * @param result
	 *            输出流
	 * @throws ReportException
	 * @throws IOException
	 */
	public void print(com.lucaslee.report.model.Report r, OutputStream result)
			throws ReportException, IOException {
		print(r, new PDFCss(), result);
	}

	/**
	 * 打印报表到输出流
	 * 
	 * @param r
	 *            报表
	 * @param css
	 *            样式表
	 * @param result
	 *            输出流
	 * @throws ReportException
	 * @throws IOException
	 */
	public void print(com.lucaslee.report.model.Report r, PDFCss css,
			OutputStream result) throws ReportException, IOException {
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, result); // 输出文档
			setHeaderFooter(document); // 必须在document.open之前设置
			document.open();

			if (r.getHeaderTable() != null) {
				print(r.getHeaderTable(), css, document);
			}
			if (r.getBody() != null) {
				print(r.getBody(), css, document);
			}
			if (r.getFooterTable() != null) {
				print(r.getFooterTable(), css, document);
			}
			document.close(); // 关闭文档
		} catch (DocumentException ex) {
			ex.printStackTrace();
			throw new ReportException(ex.getMessage());
		}
	}

	/**
	 * 设置首注，脚注。
	 * 
	 * @param document
	 */
	private void setHeaderFooter(Document document) {
		HeaderFooter footer = new HeaderFooter(new Phrase("页码：", defaultFont),
				true);
		footer.setAlignment(Element.ALIGN_CENTER);
		footer.setBorder(com.lowagie.text.Rectangle.TOP);
		document.setFooter(footer);
	}

	/**
	 * 打印报表主体到输出流
	 * 
	 * @param body
	 *            报表主体
	 * @param css
	 *            样式表
	 * @param document
	 *            itext文档对象
	 * @throws ReportException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void print(com.lucaslee.report.model.ReportBody body, PDFCss css,
			Document document) throws ReportException, IOException,
			DocumentException {
		com.lucaslee.report.model.Table data = body.getData().cloneAll();
		com.lucaslee.report.model.Table header = body.getTableColHeader();
		header = CssEngine.applyCss(header);
		if (header != null) {
			for (int i = header.getRowCount() - 1; i >= 0; i--) {
				data.insertRow(0, header.getRow(i));
			}
		}
		print(data, css, document);

	}

	/**
	 * 打印表格
	 * 
	 * @param t
	 *            表格
	 * @param css
	 *            样式表
	 * @param document
	 *            pdf文档对象
	 * @return
	 * @throws ReportException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void print(com.lucaslee.report.model.Table t, PDFCss css,
			Document document) throws ReportException, IOException,
			DocumentException {
		// 将样式属性解释到每个单元格
		t = CssEngine.applyCss(t);

		// 表格
		// 确定表格的列数
		com.lowagie.text.Table table = new com.lowagie.text.Table(t
				.getColCount());
		if (t.getWidths() != null)
			table.setWidths(t.getWidths());
		table.setWidth(t.getWidth());
		table.setSpacing(t.getCellspacing());
		table.setPadding(t.getCellpadding());
		table.setAlignment(getAlign(t.getAlign()));
		setMultiParam(table, t);

		boolean haveBorder = false;
		if (t.getBorder() <= 0) {
			haveBorder = false;
		} else {
			haveBorder = true;
		}
		for (int i = 0; i < t.getRowCount(); i++) {
			print(t.getRow(i), css, table, haveBorder);
		}
		document.add(table);
	}

}
