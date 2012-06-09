package com.lucaslee.report.printer;

import java.awt.Color;

import com.lowagie.text.Font;



/**
 * PDF样式表的项目。
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
 * @author Lucas Lee
 * @version 1.0
 */
public class PDFCssItem {
	public PDFCssItem() {
	}

	/**
	 * 字体
	 */
	private Font font;

	/**
	 * 背景颜色
	 */
	private Color backgroudColor;

	/**
	 * 获得字体
	 * 
	 * @return
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * 获得背景颜色
	 * 
	 * @return
	 */
	public Color getBackgroudColor() {
		return backgroudColor;
	}

	/**
	 * 设置字体
	 * 
	 * @param font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param backgroudColor
	 */
	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

}