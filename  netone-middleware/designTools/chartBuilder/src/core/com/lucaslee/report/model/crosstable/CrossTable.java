package com.lucaslee.report.model.crosstable;

/**
 * ����һ�������
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
 * Company:
 * </p>
 * 
 * @author Lucas lee
 * @version 1.0
 */
public class CrossTable {
	public CrossTable() {
	}

	/**
	 * 
	 * @param colHeader
	 *            ��ͷ
	 * @param rowHeader
	 *            ��ͷ
	 * @param crossCol
	 *            ���沿��
	 */
	public CrossTable(HeadCol[] colHeader, HeadCol[] rowHeader,
			CrossCol crossCol) {
		this.colHeader = colHeader;
		this.rowHeader = rowHeader;
		this.crossCol = crossCol;
	}

	/**
	 * ��ͷ��
	 */
	private HeadCol[] colHeader;

	/**
	 * ��ͷ��
	 */
	private HeadCol[] rowHeader;

	/**
	 * ���沿�֡�
	 */
	private CrossCol crossCol;

	/**
	 * �����ͷ��
	 * 
	 * @return
	 */
	public HeadCol[] getRowHeader() {
		return rowHeader;
	}

	/**
	 * ��ý��沿��
	 * 
	 * @return
	 */
	public CrossCol getCrossCol() {
		return crossCol;
	}

	/**
	 * �����ͷ
	 * 
	 * @return
	 */
	public HeadCol[] getColHeader() {
		return colHeader;
	}

	/**
	 * ������ͷ
	 * 
	 * @param rowHeader
	 */
	public void setRowHeader(HeadCol[] rowHeader) {
		this.rowHeader = rowHeader;
	}

	/**
	 * ���ý��沿��
	 * 
	 * @param crossCol
	 */
	public void setCrossCol(CrossCol crossCol) {
		this.crossCol = crossCol;
	}

	/**
	 * ������ͷ
	 * 
	 * @param colHeader
	 */
	public void setColHeader(HeadCol[] colHeader) {
		this.colHeader = colHeader;
	}

}