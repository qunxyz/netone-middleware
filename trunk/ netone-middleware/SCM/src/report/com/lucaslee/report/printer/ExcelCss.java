package com.lucaslee.report.printer;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel��ʽ��
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
	 * �հ���ʽ��
	 */
	public static ExcelCss BLANK_CSS = new ExcelCss() {
		public void init(HSSFWorkbook wb) {
		}
	};

	// ��ʽ��������
	private Map<String, HSSFCellStyle> map = new HashMap<String, HSSFCellStyle>();

	/**
	 * ���ø����ԡ����õĴ�����������֮ǰӦ�õ�������
	 */
	public abstract void init(HSSFWorkbook workbook);

	/**
	 * ȱʡ�п��
	 */
	private short defaultColumnWidth = 10;

	public HSSFCellStyle getStyle(String cssName) {
		return map.get(cssName);
	}

	/**
	 * ������ʽ��
	 * @param cssName ��ʽ���ơ�Ԥ���������ΪReport.XXX_Type�ȡ�
	 * @param style
	 */
	public void setStyle(String cssName, HSSFCellStyle style) {
		map.put(cssName, style);
	}

	/**
	 * ����ȱʡ��ʽ��
	 */
	private HSSFCellStyle defaultStyle;

	/**
	 * ���ȱʡ�п��
	 * 
	 * @return
	 */
	public short getDefaultColumnWidth() {
		return defaultColumnWidth;
	}

	/**
	 * ����ȱʡ�п��
	 * 
	 * @param defaultColumnWidth
	 */
	public void setDefaultColumnWidth(short defaultColumnWidth) {
		this.defaultColumnWidth = defaultColumnWidth;
	}

	/**
	 * ��ñ���ȱʡ��ʽ��
	 * 
	 * @return
	 */
	public HSSFCellStyle getDefaultStyle() {
		return defaultStyle;
	}

	/**
	 * ���ñ���ȱʡ��ʽ��
	 * 
	 * @param defaultStyle
	 */
	public void setDefaultStyle(HSSFCellStyle defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

}
