package com.lucaslee.report.printer;

import java.io.IOException;
import java.io.OutputStream;

import com.lucaslee.report.CssEngine;
import com.lucaslee.report.Printer;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * HTML打印机。
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:Lucas-lee Soft
 * </p>
 * 
 * @author Lucas Lee
 * @version 1.0
 */
public class HTMLPrinter implements Printer {
	/** 编码,如GBK、ISO8859-1,默认为"GBK" */
	private String encoding = "GBK";

	public HTMLPrinter() {
	}

	public HTMLPrinter(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 将整数转换为两字符的十六进制字符串。
	 * 
	 * @param i
	 * @return
	 */
	private static String getHexStr(int i) {
		String result = Integer.toHexString(i);
		if (i <= 0xf) {
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 将颜色对象转换成RGB字符串。
	 * 
	 * @param c
	 * @return
	 */
	private static String getRGBHexStr(java.awt.Color c) {
		String result = "#";

		result += getHexStr(c.getRed());
		result += getHexStr(c.getGreen());
		result += getHexStr(c.getBlue());
		return result;
	}

	/**
	 * 获得水平对齐方式。
	 * 
	 * @param i
	 *            水平对齐常数。
	 * @return HTML中对应的字符串
	 */
	private String getAlignStr(int i) {
		switch (i) {
		case Rectangle.ALIGN_LEFT:
			return "left";
		case Rectangle.ALIGN_CENTER:
			return "center";
		case Rectangle.ALIGN_RIGHT:
			return "right";
		default:
			throw new RuntimeException("无法识别的align参数。");
		}
	}

	/**
	 * 获得垂直对齐方式.
	 * 
	 * @param i
	 *            水平对齐常数。
	 * @return HTML中对应的字符串
	 */
	private String getVAlignStr(int i) {
		switch (i) {
		case Rectangle.VALIGN_TOP:
			return "top";
		case Rectangle.VALIGN_MIDDLE:
			return "middle";
		case Rectangle.VALIGN_BOTTOM:
			return "bottom";
		default:
			throw new RuntimeException("无法识别的valign参数。");
		}

	}

	/**
	 * 获得所有矩形参数
	 * 
	 * @param r
	 * @return
	 */
	private String getMultiParamStr(Rectangle r) {
		StringBuffer buf = new StringBuffer();
		buf.append(" border='").append(r.getBorder()).append("'").append(
				" bordercolor='").append(getRGBHexStr(r.getBordercolor()))
				.append("'").append(" bgcolor='").append(
						getRGBHexStr(r.getBackgroudColor())).append("'")
				.append(" align='").append(getAlignStr(r.getAlign())).append(
						"'").append(" valign='").append(
						getVAlignStr(r.getValign())).append("'").append(
						" height='").append(r.getHeight()).append("%'");
		return buf.toString();
	}

	/**
	 * 打印报表到输出流。
	 * 
	 * @param r
	 *            报表
	 * @param result
	 *            输出流
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	public void print(Report r, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		print(r, new HTMLCss(), result);
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
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	public void print(Report r, HTMLCss css, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		// 加入css
		result.write(css.toString().getBytes(encoding));

		if (r.getHeaderTable() != null) {
			print(r.getHeaderTable(), result);
		}
		if (r.getBody() != null) {
			print(r.getBody(), result);
		}
		if (r.getFooterTable() != null) {
			print(r.getFooterTable(), result);
		}
	}

	/**
	 * 打印报表主体到输出流。
	 * 
	 * @param body
	 *            报表主体
	 * @param result
	 *            输出流
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	private void print(ReportBody body, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		Table data = body.getData().cloneAll();
		Table header = body.getTableColHeader();
		header = CssEngine.applyCss(header);
		if (header != null) {
			for (int i = header.getRowCount() - 1; i >= 0; i--) {
				data.insertRow(0, header.getRow(i));
			}
		}
		print(data, result);
	}

	/**
	 * 打印表格到输出流
	 * 
	 * @param t
	 *            表格
	 * 
	 * @param result
	 *            输出流
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	private void print(Table t, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		StringBuffer buf = new StringBuffer();
		t = CssEngine.applyCss(t);
		buf.append("\n<table ").append(" style='").append(t.getStyle()).append(
				"' ").append(getMultiParamStr(t)).append(" cellPadding='")
				.append(t.getCellpadding()).append("'")
				.append(" cellSpacing='").append(t.getCellspacing())
				.append("'").append(" width='").append(t.getWidth()).append(
						"%'").append(">\n");
		result.write(buf.toString().getBytes(encoding));

		for (int i = 0; i < t.getRowCount(); i++) {
			print(t.getRow(i), t.getWidths(), result);
		}

		result.write("</table>\n".getBytes(encoding));
	}

	/**
	 * 打印表格行到输出流
	 * 
	 * @param tr
	 *            表格行
	 * @param widths
	 *            所有列的宽度
	 * @param result
	 *            输出流
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	private void print(TableRow tr, int[] widths, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		StringBuffer buf = new StringBuffer();
		buf.append("<tr ").append(">\n");

		result.write(buf.toString().getBytes(encoding));

		int width = -1;
		for (int j = 0; j < tr.getCellCount(); j++) {
			TableCell tc = tr.getCell(j);
			if (widths != null && widths.length > j)
				width = widths[j];
			print(tc, width, result);
		}
		result.write("</tr>\n".getBytes(encoding));

	}

	/**
	 * 打印表格单元到输出流
	 * 
	 * @param tc
	 *            表格单元
	 * @param width
	 *            百分比宽度
	 * @param result
	 *            输出流
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	private void print(TableCell tc, int width, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		if (tc.getIsHidden()) {

		} else {
			StringBuffer buf = new StringBuffer();
			buf.append("<td rowspan=").append(tc.getRowSpan()).append(
					" colspan=").append(tc.getColSpan()).append(" ");
			if (tc.getCssClass() != null)
				buf.append(" class='").append(tc.getCssClass()).append("'");
			
			if (width>0){
				buf.append(" width=\"" + width + "%\"");
			}

			if (Report.CROSS_HEAD_HEAD_TYPE.equals(tc.getCssClass())) {
				buf.append(" nowrap=\"true\"");
			} else {
				buf.append(" nowrap=\"" + tc.getNoWrap() + "\"");
			}

			buf.append(" style='").append(tc.getCssStyle()).append("'").append(
					getMultiParamStr(tc)).append(">");

			result.write(buf.toString().getBytes(encoding));

			// 处理交叉表表头的表头
			if (Report.CROSS_HEAD_HEAD_TYPE.equals(tc.getCssClass())) {
				String blank = "&nbsp;&nbsp;&nbsp;&nbsp;";
				String[] strs = PrinterUtil.getCrossHeadHeadContent(tc);
				for (int i = 0; i < strs.length; i++) {
					if (i != 0)
						result.write("<br>".getBytes(encoding));
					for (int j = 0; j < strs.length - i - 1; j++) {
						result.write(blank.getBytes(encoding));
					}
					if (strs[i] != null)
						result.write(strs[i].getBytes(encoding));
				}
			} else {// 普通单元
				String content = (String) tc.getContent();
				if (content != null) {
					result.write(content.getBytes(encoding));
				}
				result.write("".getBytes(encoding));
			}// if
			result.write("</td>\n".getBytes(encoding));
		}
	}

}
