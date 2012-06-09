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
 * Excel��ӡ���� <BR>
 * ע�⣺ ���ڴ󱨱�ʱ��������ʽ����Ĵ��󣬽�������Ƕ���ExcelCss��ȱʡ��ʽ������δָ����ʽ��cell��ʹ������<BR>
 * ���Ϊ����sheet����2000��ʱ����ռ�úܶ��ڴ��ʱ��;����ֳɶ��sheet�����б��������(��δʵ��)��<BR>
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
	 * ��ӡ�����������
	 * 
	 * @param r
	 *            ����
	 * @param result
	 *            �����
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
	 * ��ӡ�����������
	 * 
	 * @param r
	 *            ����
	 * @param css
	 *            ��ʽ��
	 * @param result
	 *            �����
	 * @param onlyUseCssClassOnDataCell
	 *            ����������ĵ�Ԫ���ϣ��Ƿ�ֻʹ����ʽ���е���ʽ��<BR>
	 *            ���Ϊfalse,�򵥶�������Щ��Ԫ�����ʽ����(���뷽ʽ�������)��Ч;<BR>
	 *            ��֮��Ч��������ÿ����Ԫ�񴴽�һ����ʽ������ʽ������Excel�������޵ġ�<BR>
	 *            �����������Ԫ��ʱ�����Ƽ���Ϊtrue��
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	public void print(Report r, ExcelCss css, OutputStream result,
			boolean onlyUseCssClassOnDataCell)
			throws com.lucaslee.report.ReportException, IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		// ������ʽ�������ֵ
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
	 * ��ȿ�¡һ��Font��������HSSFFont��֧��clone�������������θ������ԡ�
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
	 * ��ȿ�¡һ��style��������HSSFCellStyle��֧��clone�������������θ������ԡ�<BR>
	 * HSSFCellUtilԴ��˵��,cell.setStyle(...)������style�Ĺ��ܡ�
	 * ��֤�Ƿ�wb.createFont()�����������⣬��verified�����������취ֱ��new
	 * HSSFFontʵ��������ͬһ�����µ��࣬����������
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

		// ��ȿ�¡����
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
	 * ��ӡ���Ԫ��
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
		//cell.setEncoding(HSSFCell.ENCODING_UTF_16); // ����cell���������ĸ�λ�ֽڽض�
		// ���õ�Ԫ���ֵ
		// ��������ͷ�ı�ͷ
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
		// �ϲ���Ԫ��
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
		// Ӧ����ʽ����ʽ���ṩĬ��ֵ����Ԫ�����������ֵ�Ḳ����ʽ��Ķ�Ӧֵ
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
	 * ��ȫ��cell���Ի�þ�ȷ����ʽ����������Ҫ�½�һ����ʽ������ʽ�ĸ�����excel�������޵ġ�
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

		// ���ö��뷽ʽ
		style.setAlignment(getAlign(tableCell.getAlign()));
		style.setVerticalAlignment(getVAlign(tableCell.getValign()));

		// �����Ƿ�������
		style.setWrapText(!tableCell.getNoWrap());
		return style;
	}

	/**
	 * ���ˮƽ���뷽ʽ
	 * 
	 * @param i
	 *            ˮƽ���볣��
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
			throw new RuntimeException("�޷�ʶ���ALIGN������");
		}
	}

	/**
	 * ��ô�ֱ���뷽ʽ
	 * 
	 * @param i
	 *            ��ֱ���뷽ʽ����
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
			throw new RuntimeException("�޷�ʶ���VALIGN������");
		}

	}

	/**
	 * ��ӡ�����С�
	 * 
	 * @param tableRow
	 * @param css
	 * @param row
	 * @param rowNum
	 *            ��ǰ��������
	 * @param sheet
	 * @param haveBorder
	 *            �Ƿ��б߿�
	 * @param wb
	 * @throws ReportException
	 * @throws IOException
	 */
	private void print(com.lucaslee.report.model.TableRow tableRow,
			ExcelCss css, HSSFRow row, int rowNum, HSSFSheet sheet,
			boolean haveBorder, boolean onlyCssClass, HSSFWorkbook wb)
			throws ReportException, IOException {
		HSSFCell cell = null;
		// ��������ͷ�ı�ͷ
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
	 * ��ӡ���
	 * 
	 * @param t
	 * @param css
	 * @param sheet
	 * @param currRow
	 *            ��ǰ��������
	 * @param haveBorder
	 *            �Ƿ��б߿�
	 * @param wb
	 * @return
	 * @throws ReportException
	 * @throws IOException
	 */
	private int print(com.lucaslee.report.model.Table t, ExcelCss css,
			HSSFSheet sheet, int currRow, boolean haveBorder,
			boolean onlyCssClass, HSSFWorkbook wb) throws ReportException,
			IOException {
		// ����ʽ���Խ��͵�ÿ����Ԫ��
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
	 * ��ӡ�������塣
	 * 
	 * @param body
	 * @param css
	 * @param sheet
	 * @param currRow
	 *            ��ǰ��������
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