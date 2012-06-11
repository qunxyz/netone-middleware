package com.lucaslee.report.model;

/**
 * ��ʾ��ͷ�ı��
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
public class HeaderTable extends Table {
	public HeaderTable() {
		super();
		this.setType(Report.HEAD_TYPE);
	}

	/**
	 * 
	 * @param row
	 *            ����
	 * @param col
	 *            ����
	 */
	public HeaderTable(int row, int col) {
		super(row, col);
		this.setType(Report.HEAD_TYPE);
	}
}