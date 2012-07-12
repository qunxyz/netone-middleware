package com.lucaslee.report.printer;

import com.lucaslee.report.model.Report;

/**
 * HTML��ӡ��ʹ�õ���ʽ��
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
 * FIXME ����Ĭ����ʽ��
 */
public class HTMLCss {
	public HTMLCss() {
	}

	/**
	 * ������ܲ�����ʽ
	 */
	private String groupTotal = "";

	/**
	 * �ܻ��ܲ�����ʽ
	 */
	private String total = "";

	/**
	 * ���������ͷ������ʽ
	 */
	private String head = "";

	/**
	 * ���ݲ�����ʽ
	 */
	private String data = "";

	/**
	 * ������ⲿ����ʽ
	 */
	private String title = "";

	/**
	 * ��������ϽǱ�ͷ������ʽ
	 */
	private String crossHeadHead = "";

	/**
	 * ������ݲ�����ʽ
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * ��÷�����ܲ�����ʽ
	 * 
	 * @return
	 */
	public String getGroupTotal() {
		return groupTotal;
	}

	/**
	 * ��ñ��������ͷ������ʽ
	 * 
	 * @return
	 */
	public String getHead() {
		return head;
	}

	/**
	 * ��ñ�����ⲿ����ʽ
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * ����ܻ��ܲ�����ʽ
	 * 
	 * @return
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * �����ܻ��ܲ�����ʽ
	 * 
	 * @param total
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * ���ñ�����ⲿ����ʽ
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ���ñ��������ͷ������ʽ
	 * 
	 * @param head
	 */
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * ���÷�����ܲ�����ʽ
	 * 
	 * @param groupTotal
	 */
	public void setGroupTotal(String groupTotal) {
		this.groupTotal = groupTotal;
	}

	/**
	 * �������ݲ�����ʽ
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * ���HTML��ʽ��
	 * 
	 * @return
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("<style>\r\n");
		buf.append(".").append(Report.GROUP_TOTAL_TYPE).append("{").append(
				this.groupTotal).append("}\r\n");
		buf.append(".").append(Report.TOTAL_TYPE).append("{")
				.append(this.total).append("}\r\n");
		buf.append(".").append(Report.HEAD_TYPE).append("{").append(this.head)
				.append("}\r\n");
		buf.append(".").append(Report.DATA_TYPE).append("{").append(this.data)
				.append("}\r\n");
		buf.append(".").append(Report.TITLE_TYPE).append("{")
				.append(this.title).append("}\r\n");
		buf.append(".").append(Report.CROSS_HEAD_HEAD_TYPE).append("{").append(
				this.crossHeadHead).append("}\r\n");

		buf.append("</style>\r\n");
		return buf.toString();
	}

	/**
	 * ��ý�������ϽǱ�ͷ������ʽ
	 * 
	 * @return
	 */
	public String getCrossHeadHead() {
		return crossHeadHead;
	}

	/**
	 * ���ý�������ϽǱ�ͷ������ʽ
	 * 
	 * @param crossHeadHead
	 */
	public void setCrossHeadHead(String crossHeadHead) {
		this.crossHeadHead = crossHeadHead;
	}
}