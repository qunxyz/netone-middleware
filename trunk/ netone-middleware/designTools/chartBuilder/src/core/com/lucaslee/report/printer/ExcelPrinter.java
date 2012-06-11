package com.lucaslee.report.printer;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.lucaslee.report.CssEngine;
import com.lucaslee.report.Printer;
import com.lucaslee.report.ReportException;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.crosstable.CrossTable;

/**
 * Excel打印机。 <BR>
 * 注意： 对在大报表时，产生样式过多的错误，解决方法是定义ExcelCss的缺省样式，所有未指定样式的cell均使用它。<BR>
 * 输出为单个sheet超过2000行时，会占用很多内存和时间;如果分成多个sheet，会有倍数的提高(暂未实现)。<BR>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Lucas-Lee Soft
 * </p>
 * 
 * @author Lucas Lee
 * @version 1.0
 * 
 */
public class ExcelPrinter implements Printer {

	private static Log log = LogFactory.getLog(ExcelPrinter.class);

	public ExcelPrinter() {
	}

	/**
	 * 打印报表到输出流。
	 * 
	 * @param r
	 *            报表
	 * @param result
	 *            输出流
	 * @throws ReportException
	 * @throws IOException
	 */
	public void print(Report r, OutputStream result,
			boolean onlyUserCssClassOnDataArea) throws ReportException,
			IOException {
		print(r, ExcelCss.BLANK_CSS, result, onlyUserCssClassOnDataArea);
	}

	public void print(Report r, OutputStream result) throws ReportException,
			IOException {
		print(r, ExcelCss.BLANK_CSS, result, true);
	}

	/**
	 * 打印报表到输出流。
	 * 
	 * @param r
	 *            报表
	 * @param css
	 *            样式表
	 * @param result
	 *            输出流
	 * @param onlyUseCssClassOnDataCell
	 *            在数据区域的单元格上，是否只使用样式表中的样式。<BR>
	 *            如果为false,则单独设置这些单元格的样式属性(对齐方式、字体等)无效;<BR>
	 *            反之有效，代价是每个单元格创建一个样式对象，样式个数在Excel中是受限的。<BR>
	 *            在输出大量单元格时，不推荐设为true。
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	public void print(Report r, ExcelCss css, OutputStream result,
			boolean onlyUseCssClassOnDataCell)
			throws com.lucaslee.report.ReportException, IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		// 设置样式表的属性值
		css.init(wb);

		wb.setSheetName(0, "Report");

		sheet.setDefaultColumnWidth(css.getDefaultColumnWidth());

		int currRow = 0;

		if (r.getHeaderTable() != null) {
			currRow = print(r.getHeaderTable(), css, sheet, currRow, false,
					false, wb);
		}
		if (r.getBody() != null) {
			currRow = print(r.getBody(), css, sheet, currRow, wb,
					onlyUseCssClassOnDataCell);
		}
		if (r.getFooterTable() != null) {
			currRow = print(r.getFooterTable(), css, sheet, currRow, false,
					false, wb);
		}
		HSSFFooter footer = sheet.getFooter();
		footer.setCenter(HSSFFooter.page() + " / " + HSSFFooter.numPages());

		wb.write(result);

		log.debug("fontCount:" + wb.getNumberOfFonts() + " styleCount:"
				+ wb.getNumCellStyles());
	}

	private int fontCount = 0;

	private int styleCount = 0;

	/**
	 * 深度克隆一个Font对象。由于HSSFFont不支持clone方法，所以依次复制属性。
	 * 
	 * @param wb
	 * @param f
	 * @return
	 */
	private HSSFFont cloneFont(HSSFWorkbook wb, HSSFFont f) {
		HSSFFont result = wb.createFont();

		result.setBoldweight(f.getBoldweight());
		result.setColor(f.getColor());
		result.setFontHeight(f.getFontHeight());
		result.setFontHeightInPoints(f.getFontHeightInPoints());
		result.setFontName(f.getFontName());
		result.setItalic(f.getItalic());
		result.setStrikeout(f.getStrikeout());
		result.setTypeOffset(f.getTypeOffset());
		result.setUnderline(f.getUnderline());
		return result;
	}

	/**
	 * 深度克隆一个style对象。由于HSSFCellStyle不支持clone方法，所以依次复制属性。<BR>
	 * HSSFCellUtil源码说明,cell.setStyle(...)有重用style的功能。
	 * 验证是否wb.createFont()就能引起问题，（verified）如果是则想办法直接new
	 * HSSFFont实例（创建同一个包下的类，工厂方法）
	 * @param wb
	 * @param s
	 * @return
	 */
	private HSSFCellStyle cloneStyle(HSSFWorkbook wb, HSSFCellStyle s) {
		HSSFCellStyle result = wb.createCellStyle();
		styleCount++;
		result.setAlignment(s.getAlignment());
		result.setBorderBottom(s.getBorderBottom());
		result.setBorderLeft(s.getBorderLeft());
		result.setBorderRight(s.getBorderRight());
		result.setBorderTop(s.getBorderTop());
		result.setBottomBorderColor(s.getBottomBorderColor());
		result.setDataFormat(s.getDataFormat());
		result.setFillBackgroundColor(s.getFillBackgroundColor());
		result.setFillForegroundColor(s.getFillForegroundColor());
		result.setFillPattern(s.getFillPattern());

		// 深度克隆字体
		result.setFont(cloneFont(wb, wb.getFontAt(s.getFontIndex())));

		result.setHidden(s.getHidden());
		result.setIndention(s.getIndention());
		result.setLeftBorderColor(s.getLeftBorderColor());
		result.setLocked(s.getLocked());
		result.setRightBorderColor(s.getRightBorderColor());
		result.setRotation(s.getRotation());
		result.setTopBorderColor(s.getTopBorderColor());
		result.setVerticalAlignment(s.getVerticalAlignment());
		result.setWrapText(s.getWrapText());
		return result;
	}

	/**
	 * 打印表格单元。
	 * 
	 * @param tableCell
	 *            TableCell
	 * @param css
	 *            ExcelCss
	 * @param cell
	 *            HSSFCell
	 * @param rowNum
	 *            int
	 * @param colNum
	 *            int
	 * @param sheet
	 *            HSSFSheet
	 * @param haveBorder
	 *            boolean
	 * @param wb
	 *            HSSFWorkbook
	 * @throws IOException
	 * @throws ReportException
	 */
	private void print(com.lucaslee.report.model.TableCell tableCell,
			ExcelCss css, HSSFCell cell, int rowNum, int colNum,
			HSSFSheet sheet, boolean haveBorder, boolean onlyCssClass,
			HSSFWorkbook wb) throws IOException, ReportException {

		HSSFCellStyle style = getCellStyle(tableCell, haveBorder, onlyCssClass,
				wb, css);

		cell.setCellStyle(style);
		//cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 设置cell编码解决中文高位字节截断
		// 设置单元格的值
		// 处理交叉表表头的表头
		if (Report.CROSS_HEAD_HEAD_TYPE.equals(tableCell.getCssClass())) {
			String blank = "    ";
			String[] strs = PrinterUtil.getCrossHeadHeadContent(tableCell);
			StringBuffer content = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				if (i != 0)
					content.append("\n");
				for (int j = 0; j < strs.length - i - 1; j++) {
					content.append(blank);
				}
				if (strs[i] != null)
					content.append(strs[i]);
			}
			style.setWrapText(true);
			cell.setCellValue(content.toString());
		} else {
			cell.setCellValue((String) tableCell.getContent());
		}
		// 合并单元格
		if (tableCell.getIsHidden() == false) {
			if (tableCell.getRowSpan() > 1 || tableCell.getColSpan() > 1) {
				sheet.addMergedRegion(new Region(rowNum, (short) colNum, rowNum
						+ tableCell.getRowSpan() - 1, (short) (colNum
						+ tableCell.getColSpan() - 1)));
			}
		}
	}

	private HSSFCellStyle getCellStyle(
			com.lucaslee.report.model.TableCell tableCell, boolean haveBorder,
			boolean onlyCssClass, HSSFWorkbook wb, ExcelCss css) {
		// 应用样式表。样式表提供默认值。单元格如果有属性值会覆盖样式表的对应值
		HSSFCellStyle style = null;

		if (tableCell.getCssClass() != null) {
			style = css.getStyle(tableCell.getCssClass());
		} else {
			style = css.getDefaultStyle();
		}
		if (onlyCssClass == false) {
			style = getFullCellStyle(tableCell, haveBorder, wb, style);
		}
		return style;
	}

	/**
	 * 按全部cell属性获得精确的样式。代价是需要新建一个样式，而样式的个数在excel中是受限的。
	 * 
	 * @param tableCell
	 * @param haveBorder
	 * @param wb
	 * @param style
	 * @return
	 */
	private HSSFCellStyle getFullCellStyle(
			com.lucaslee.report.model.TableCell tableCell, boolean haveBorder,
			HSSFWorkbook wb, HSSFCellStyle style) {
		if (style == null) {
			style = wb.createCellStyle();
		} else {
			style = cloneStyle(wb, style);
		}

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

		// 设置对齐方式
		style.setAlignment(getAlign(tableCell.getAlign()));
		style.setVerticalAlignment(getVAlign(tableCell.getValign()));

		// 设置是否允许换行
		style.setWrapText(!tableCell.getNoWrap());
		return style;
	}

	/**
	 * 获得水平对齐方式
	 * 
	 * @param i
	 *            水平对齐常数
	 * @return
	 */

	private short getAlign(int i) {
		switch (i) {
		case com.lucaslee.report.model.Rectangle.ALIGN_LEFT:
			return HSSFCellStyle.ALIGN_LEFT;
		case com.lucaslee.report.model.Rectangle.ALIGN_CENTER:
			return HSSFCellStyle.ALIGN_CENTER;
		case com.lucaslee.report.model.Rectangle.ALIGN_RIGHT:
			return HSSFCellStyle.ALIGN_RIGHT;
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
	private short getVAlign(int i) {
		switch (i) {
		case com.lucaslee.report.model.Rectangle.VALIGN_TOP:
			return HSSFCellStyle.VERTICAL_TOP;
		case com.lucaslee.report.model.Rectangle.VALIGN_MIDDLE:
			return HSSFCellStyle.VERTICAL_CENTER;
		case com.lucaslee.report.model.Rectangle.VALIGN_BOTTOM:
			return HSSFCellStyle.VERTICAL_BOTTOM;
		default:
			throw new RuntimeException("无法识别的VALIGN参数。");
		}

	}

	/**
	 * 打印报表行。
	 * 
	 * @param tableRow
	 * @param css
	 * @param row
	 * @param rowNum
	 *            当前的行数。
	 * @param sheet
	 * @param haveBorder
	 *            是否有边框
	 * @param wb
	 * @throws ReportException
	 * @throws IOException
	 */
	private void print(com.lucaslee.report.model.TableRow tableRow,
			ExcelCss css, HSSFRow row, int rowNum, HSSFSheet sheet,
			boolean haveBorder, boolean onlyCssClass, HSSFWorkbook wb)
			throws ReportException, IOException {
		HSSFCell cell = null;
		// 处理交叉表表头的表头
		if (Report.CROSS_HEAD_HEAD_TYPE.equals(tableRow.getCell(0)
				.getCssClass())) {
			CrossTable crossTab = (CrossTable) tableRow.getCell(0).getContent();
			row
					.setHeight((short) (row.getHeight() * (crossTab
							.getColHeader().length
							+ crossTab.getRowHeader().length + 1)));
		}

		for (int j = 0; j < tableRow.getCellCount(); j++) {
			cell = row.createCell((short) j);
			print(tableRow.getCell(j), css, cell, rowNum, j, sheet, haveBorder,
					onlyCssClass, wb);
		}
	}

	/**
	 * 打印表格。
	 * 
	 * @param t
	 * @param css
	 * @param sheet
	 * @param currRow
	 *            当前的行数。
	 * @param haveBorder
	 *            是否有边框
	 * @param wb
	 * @return
	 * @throws ReportException
	 * @throws IOException
	 */
	private int print(com.lucaslee.report.model.Table t, ExcelCss css,
			HSSFSheet sheet, int currRow, boolean haveBorder,
			boolean onlyCssClass, HSSFWorkbook wb) throws ReportException,
			IOException {
		// 将样式属性解释到每个单元格
		t = CssEngine.applyCss(t);

		HSSFRow row = null;
		int result = currRow;
		for (int i = 0; i < t.getRowCount(); i++) {
			row = sheet.createRow(currRow + i);
			print(t.getRow(i), css, row, currRow + i, sheet, haveBorder,
					onlyCssClass, wb);
			result++;
		}
		return result;
	}

	/**
	 * 打印报表主体。
	 * 
	 * @param body
	 * @param css
	 * @param sheet
	 * @param currRow
	 *            当前的行数。
	 * @param wb
	 * @param onlyUserCssClassOnDataArea
	 * @return
	 * @throws ReportException
	 * @throws IOException
	 */
	private int print(com.lucaslee.report.model.ReportBody body, ExcelCss css,
			HSSFSheet sheet, int currRow, HSSFWorkbook wb,
			boolean onlyUserCssClassOnDataArea) throws ReportException,
			IOException {
		Table data = body.getData().cloneAll();
		Table header = body.getTableColHeader();
		header = CssEngine.applyCss(header);
		if (header != null) {
			for (int i = header.getRowCount() - 1; i >= 0; i--) {
				data.insertRow(0, header.getRow(i));
			}
		}
		return print(data, css, sheet, currRow, true,
				onlyUserCssClassOnDataArea, wb);
	}
}