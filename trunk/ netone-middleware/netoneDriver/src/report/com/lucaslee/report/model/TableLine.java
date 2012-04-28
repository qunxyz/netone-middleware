package com.lucaslee.report.model;

import com.lucaslee.report.ReportException;

/**
 * ����е�һ���鵥Ԫ���С�
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
public abstract class TableLine implements Cloneable {

	/** ���͡�ֵΪ��׺Ϊ"_TYPE"�ĳ���.��������TableCell��cssClass���� */
	protected String type = null;

	/**
	 * ���ָ���ĵ�Ԫ��
	 * 
	 * @param ind
	 *            ���е�Ԫ�š�
	 * @return TableCell
	 */
	public abstract TableCell getCell(int ind) throws ReportException;

	/**
	 * ��õ�Ԫ����
	 * 
	 * @return int
	 */
	public abstract int getCellCount();

	/** ��������ӵ�Ԫ�� */

	public abstract void addCell(TableCell tc);

	/**
	 * ������������е�Ԫ��
	 * 
	 * @return TableCell[]
	 */
	public abstract TableCell[] getCells();

	/**
	 * �������͡�
	 * 
	 * @return
	 */
	public void setType(String type) throws ReportException {
		this.type = type;
	}

	/**
	 * ������͡�
	 * 
	 * @param type
	 */
	public String getType() {
		return type;
	}

	/**
	 * ���ÿ��ֵ��
	 * 
	 * @param tc
	 *            Ҫ���ÿ�ȵĵ�Ԫ��
	 * @param span
	 *            ���ֵ
	 */
	public abstract void setSpan(TableCell tc, int span);

	/**
	 * ��ÿ��ֵ��
	 * 
	 * @param tc
	 *            ��Ԫ��
	 * @return ���ֵ
	 */
	public abstract int getSpan(TableCell tc);

	@Override
	public TableLine clone() throws CloneNotSupportedException {
		return (TableLine) super.clone();
	}

	@Override
	public String toString() {
		if (this == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < this.getCellCount(); i++) {
			if (i > 0) {
				result.append(",");
			}
			result.append("[").append(this.getCell(i).toString()).append("]");
		}
		return result.toString();
	}

}