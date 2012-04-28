package com.lucaslee.report.model;

import java.awt.Color;

/**
 * <p>
 * Title: �����ζ���
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

	/** ����ˮƽ���� */
	public static final int ALIGN_LEFT = 0;

	/** ����ˮƽ���� */
	public static final int ALIGN_CENTER = 1;

	/** ����ˮƽ���� */
	public static final int ALIGN_RIGHT = 2;

	/** ������ֱ���� */
	public static final int VALIGN_TOP = 0;

	/** ���д�ֱ���� */
	public static final int VALIGN_MIDDLE = 1;

	/** ���״�ֱ���� */
	public static final int VALIGN_BOTTOM = 2;

	/** ˮƽ���뷽ʽ */
	protected int align = ALIGN_LEFT;

	/** ��ֱ���뷽ʽ */
	protected int valign = VALIGN_MIDDLE;

	/** �ðٷֱȱ�ʾ�ĸ߶� */
	protected int height = 0;

	/** �߿��� */
	protected int border = 1;

	/** �߿���ɫ */
	protected Color bordercolor = Color.black;

	/** ������ɫ */
	protected Color backgroudColor = Color.white;

	public Rectangle() {
	}

	/**
	 * ���ˮƽ���뷽ʽ
	 * 
	 * @return
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * ��ñ�����ɫ
	 * 
	 * @return
	 */
	public Color getBackgroudColor() {
		return backgroudColor;
	}

	/**
	 * ��ñ߿���ɫ
	 * 
	 * @return
	 */
	public Color getBordercolor() {
		return bordercolor;
	}

	/**
	 * ��ô�ֱ���뷽ʽ
	 * 
	 * @return
	 */
	public int getValign() {
		return valign;
	}

	/**
	 * ���ô�ֱ���뷽ʽ
	 * 
	 * @param valign
	 */
	public void setValign(int valign) {
		this.valign = valign;
	}

	/**
	 * ���ñ߿���ɫ
	 * 
	 * @param bordercolor
	 *            ������ɫ
	 */
	public void setBordercolor(Color bordercolor) {
		this.bordercolor = bordercolor;
	}

	/**
	 * ���ñ�����ɫ
	 * 
	 * @param backgroudColor
	 */
	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

	/**
	 * ����ˮƽ���뷽ʽ
	 * 
	 * @param align
	 */
	public void setAlign(int align) {
		this.align = align;
	}

	/**
	 * ��ñ߿���
	 * 
	 * @return
	 */
	public int getBorder() {
		return border;
	}

	/**
	 * ���ñ߿���
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
	 * ����ðٷֱȱ�ʾ�ĸ߶ȡ�
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * �����ðٷֱȱ�ʾ�ĸ߶�
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}