package com.lucaslee.report.model;

import java.awt.Color;

/**
 * <p>
 * Title: 长方形对象。
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
 * @author lucas lee
 * @version 1.0
 */
public class Rectangle implements Cloneable {

	/** 靠左水平对齐 */
	public static final int ALIGN_LEFT = 0;

	/** 靠中水平对齐 */
	public static final int ALIGN_CENTER = 1;

	/** 靠右水平对齐 */
	public static final int ALIGN_RIGHT = 2;

	/** 靠顶垂直对齐 */
	public static final int VALIGN_TOP = 0;

	/** 靠中垂直对齐 */
	public static final int VALIGN_MIDDLE = 1;

	/** 靠底垂直对齐 */
	public static final int VALIGN_BOTTOM = 2;

	/** 水平对齐方式 */
	protected int align = ALIGN_LEFT;

	/** 垂直对齐方式 */
	protected int valign = VALIGN_MIDDLE;

	/** 用百分比表示的高度 */
	protected int height = 0;

	/** 边框宽度 */
	protected int border = 1;

	/** 边框颜色 */
	protected Color bordercolor = Color.black;

	/** 背景颜色 */
	protected Color backgroudColor = Color.white;

	public Rectangle() {
	}

	/**
	 * 获得水平对齐方式
	 * 
	 * @return
	 */
	public int getAlign() {
		return align;
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
	 * 获得边框颜色
	 * 
	 * @return
	 */
	public Color getBordercolor() {
		return bordercolor;
	}

	/**
	 * 获得垂直对齐方式
	 * 
	 * @return
	 */
	public int getValign() {
		return valign;
	}

	/**
	 * 设置垂直对齐方式
	 * 
	 * @param valign
	 */
	public void setValign(int valign) {
		this.valign = valign;
	}

	/**
	 * 设置边框颜色
	 * 
	 * @param bordercolor
	 *            背景颜色
	 */
	public void setBordercolor(Color bordercolor) {
		this.bordercolor = bordercolor;
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param backgroudColor
	 */
	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

	/**
	 * 设置水平对齐方式
	 * 
	 * @param align
	 */
	public void setAlign(int align) {
		this.align = align;
	}

	/**
	 * 获得边框宽度
	 * 
	 * @return
	 */
	public int getBorder() {
		return border;
	}

	/**
	 * 设置边框宽度
	 * 
	 * @param border
	 */
	public void setBorder(int border) {
		this.border = border;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * 获得用百分比表示的高度。
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 设置用百分比表示的高度
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}