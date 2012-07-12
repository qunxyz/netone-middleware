package com.lucaslee.report.printer;

/**
 * PDF��ʽ��
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
public class PDFCss {
	/**
	 * ������ܲ�����ʽ
	 */
	private PDFCssItem groupTotal;

	/**
	 * �ܻ��ܲ�����ʽ
	 */
	private PDFCssItem total;

	/**
	 * ���������ͷ������ʽ
	 */
	private PDFCssItem head;

	/**
	 * ���ݲ�����ʽ
	 */
	private PDFCssItem data;

	/**
	 * ������ⲿ����ʽ
	 */
	private PDFCssItem title;

	/**
	 * ��������ϽǱ�ͷ������ʽ
	 */
	private PDFCssItem crossHeadHead;

	public PDFCss() {
	}

	/**
	 * ����ܻ��ܲ�����ʽ
	 * 
	 * @return
	 */
	public PDFCssItem getTotal() {
		return total;
	}

	/**
	 * ��ñ�����ⲿ����ʽ
	 * 
	 * @return
	 */
	public PDFCssItem getTitle() {
		return title;
	}

	/**
	 * ��ñ��������ͷ������ʽ
	 * 
	 * @return
	 */
	public PDFCssItem getHead() {
		return head;
	}

	/**
	 * ��÷�����ܲ�����ʽ
	 * 
	 * @return
	 */

	public PDFCssItem getGroupTotal() {
		return groupTotal;
	}

	/**
	 * ������ݲ�����ʽ
	 * 
	 * @return
	 */
	public PDFCssItem getData() {
		return data;
	}

	/**
	 * �����ܻ��ܲ�����ʽ
	 * 
	 * @param total
	 */
	public void setTotal(PDFCssItem total) {
		this.total = total;
	}

	/**
	 * ���ñ�����ⲿ����ʽ
	 * 
	 * @param title
	 */
	public void setTitle(PDFCssItem title) {
		this.title = title;
	}

	/**
	 * ���ñ��������ͷ������ʽ
	 * 
	 * @param head
	 */
	public void setHead(PDFCssItem head) {
		this.head = head;
	}

	/**
	 * ���÷�����ܲ�����ʽ
	 * 
	 * @param groupTotal
	 */
	public void setGroupTotal(PDFCssItem groupTotal) {
		this.groupTotal = groupTotal;
	}

	/**
	 * �������ݲ�����ʽ
	 * 
	 * @param data
	 */
	public void setData(PDFCssItem data) {
		this.data = data;
	}

	/**
	 * ��ý�������ϽǱ�ͷ������ʽ
	 * 
	 * @return
	 */
	public PDFCssItem getCrossHeadHead() {
		return crossHeadHead;
	}

	/**
	 * ���ý�������ϽǱ�ͷ������ʽ
	 * 
	 * @param crossHeadHead
	 */
	public void setCrossHeadHead(PDFCssItem crossHeadHead) {
		this.crossHeadHead = crossHeadHead;
	}

}