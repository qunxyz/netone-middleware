package com.lucaslee.report.printer;

import com.lucaslee.report.model.Report;

/**
 * HTML打印机使用的样式表。
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
 * FIXME 加入默认样式，
 */
public class HTMLCss {
	public HTMLCss() {
	}

	/**
	 * 分组汇总部分样式
	 */
	private String groupTotal = "";

	/**
	 * 总汇总部分样式
	 */
	private String total = "";

	/**
	 * 报表主体表头部分样式
	 */
	private String head = "";

	/**
	 * 数据部分样式
	 */
	private String data = "";

	/**
	 * 报表标题部分样式
	 */
	private String title = "";

	/**
	 * 交叉表左上角表头部分样式
	 */
	private String crossHeadHead = "";

	/**
	 * 获得数据部分样式
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * 获得分组汇总部分样式
	 * 
	 * @return
	 */
	public String getGroupTotal() {
		return groupTotal;
	}

	/**
	 * 获得报表主体表头部分样式
	 * 
	 * @return
	 */
	public String getHead() {
		return head;
	}

	/**
	 * 获得报表标题部分样式
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 获得总汇总部分样式
	 * 
	 * @return
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * 设置总汇总部分样式
	 * 
	 * @param total
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * 设置报表标题部分样式
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 设置报表主体表头部分样式
	 * 
	 * @param head
	 */
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * 设置分组汇总部分样式
	 * 
	 * @param groupTotal
	 */
	public void setGroupTotal(String groupTotal) {
		this.groupTotal = groupTotal;
	}

	/**
	 * 设置数据部分样式
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 获得HTML样式表。
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
	 * 获得交叉表左上角表头部分样式
	 * 
	 * @return
	 */
	public String getCrossHeadHead() {
		return crossHeadHead;
	}

	/**
	 * 设置交叉表左上角表头部分样式
	 * 
	 * @param crossHeadHead
	 */
	public void setCrossHeadHead(String crossHeadHead) {
		this.crossHeadHead = crossHeadHead;
	}
}