package com.lucaslee.report.printer;

import java.awt.Color;

import com.lowagie.text.Font;



/**
 * PDF��ʽ�����Ŀ��
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
	 * ����
	 */
	private Font font;

	/**
	 * ������ɫ
	 */
	private Color backgroudColor;

	/**
	 * �������
	 * 
	 * @return
	 */
	public Font getFont() {
		return font;
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
	 * ��������
	 * 
	 * @param font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * ���ñ�����ɫ
	 * 
	 * @param backgroudColor
	 */
	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

}