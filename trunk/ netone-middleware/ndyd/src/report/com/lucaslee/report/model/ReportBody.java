package com.lucaslee.report.model;

/**
 * �����������ݱ��
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
public class ReportBody {
	public ReportBody() {
	}

	/**
	 * �����ͷ��
	 */
	private HeaderTable TableColHeader;

	/**
	 * ���ݱ�
	 */
	private Table data;

	/**
	 * ��ñ����ͷ
	 * 
	 * @return
	 */
	public HeaderTable getTableColHeader() {
		return TableColHeader;
	}

	/**
	 * ���ñ����ͷ
	 * 
	 * @param TableColHeader
	 */
	public void setTableColHeader(HeaderTable TableColHeader) {
		this.TableColHeader = TableColHeader;
	}

	/**
	 * ������ݱ�
	 * 
	 * @return
	 */
	public Table getData() {
		return data;
	}

	/**
	 * �������ݱ�
	 * 
	 * @param data
	 */
	public void setData(Table data) {
		this.data = data;
		// ����Ϊ��������
		data.setType(Report.DATA_TYPE);
	}
}