package com.lucaslee.report.printer;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel样式表。
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Lucas-Lee Soft
 * </p>
 * 
 * @author Lucas Lee
 * @version 1.0
 */

public abstract class ExcelCss {
	public ExcelCss() {
		super();
	}
	
	/**
	 * 空白样式表。
	 */
	public static ExcelCss BLANK_CSS = new ExcelCss() {
		public void init(HSSFWorkbook wb) {
		}
	};

	// 样式对象容器
	private Map<String, HSSFCellStyle> map = new HashMap<String, HSSFCellStyle>();

	/**
	 * 设置各属性。调用的此类其他方法之前应该调用它。
	 */
	public abstract void init(HSSFWorkbook workbook);

	/**
	 * 缺省列宽度
	 */
	private short defaultColumnWidth = 10;

	public HSSFCellStyle getStyle(String cssName) {
		return map.get(cssName);
	}

	/**
	 * 设置样式。
	 * @param cssName 样式名称。预定义的名称为Report.XXX_Type等。
	 * @param style
	 */
	public void setStyle(String cssName, HSSFCellStyle style) {
		map.put(cssName, style);
	}

	/**
	 * 报表缺省样式。
	 */
	private HSSFCellStyle defaultStyle;

	/**
	 * 获得缺省列宽度
	 * 
	 * @return
	 */
	public short getDefaultColumnWidth() {
		return defaultColumnWidth;
	}

	/**
	 * 设置缺省列宽度
	 * 
	 * @param defaultColumnWidth
	 */
	public void setDefaultColumnWidth(short defaultColumnWidth) {
		this.defaultColumnWidth = defaultColumnWidth;
	}

	/**
	 * 获得报表缺省样式。
	 * 
	 * @return
	 */
	public HSSFCellStyle getDefaultStyle() {
		return defaultStyle;
	}

	/**
	 * 设置报表缺省样式。
	 * 
	 * @param defaultStyle
	 */
	public void setDefaultStyle(HSSFCellStyle defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

}
