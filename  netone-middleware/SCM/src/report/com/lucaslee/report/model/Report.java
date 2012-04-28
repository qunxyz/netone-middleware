package com.lucaslee.report.model;

/**
 * ����
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
public class Report {

	/** **********************��Ԫ���ж��������,������ʽ��************************** */
	/** ���� */
	public static final String DATA_TYPE = "MyReport_data";

	/** ������� */
	public static final String GROUP_TOTAL_TYPE = "MyReport_groupTotal";

	/** ���� */
	public static final String TOTAL_TYPE = "MyReport_total";

	/** ��ͷ����ͷ */
	public static final String HEAD_TYPE = "MyReport_head";

	/** ���� */
	public static final String TITLE_TYPE = "MyReport_title";

	/** �����ı�ͷ�ı�ͷ */
	public static final String CROSS_HEAD_HEAD_TYPE = "MyReport_cross_head_head";

	/** *********************************************************************** */

	/**
	 * ��ע���
	 */
	private Table headerTable;

	/**
	 * ������
	 */
	private ReportBody body;

	/**
	 * ��ע���
	 */
	private Table footerTable;

	public Report() {
	}

	/**
	 * ��ý�ע���
	 * 
	 * @return
	 */
	public Table getFooterTable() {
		return footerTable;
	}

	/**
	 * �����ע���
	 * 
	 * @return
	 */
	public Table getHeaderTable() {
		return headerTable;
	}

	/**
	 * ���������
	 * 
	 * @return
	 */
	public ReportBody getBody() {
		return body;
	}

	/**
	 * ����������
	 * 
	 * @param mainTable
	 */
	public void setBody(ReportBody mainTable) {
		this.body = mainTable;
	}

	/**
	 * ������ע���
	 * 
	 * @param headerTable
	 */
	public void setHeaderTable(Table headerTable) {
		this.headerTable = headerTable;
	}

	/**
	 * ���ý�ע���
	 * 
	 * @param footerTable
	 */
	public void setFooterTable(Table footerTable) {
		this.footerTable = footerTable;
	}

}