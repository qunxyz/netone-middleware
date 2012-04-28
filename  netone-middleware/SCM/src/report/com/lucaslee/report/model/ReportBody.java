package com.lucaslee.report.model;

/**
 * 报表主体数据表格。
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
	 * 表的列头。
	 */
	private HeaderTable TableColHeader;

	/**
	 * 数据表。
	 */
	private Table data;

	/**
	 * 获得表的列头
	 * 
	 * @return
	 */
	public HeaderTable getTableColHeader() {
		return TableColHeader;
	}

	/**
	 * 设置表的列头
	 * 
	 * @param TableColHeader
	 */
	public void setTableColHeader(HeaderTable TableColHeader) {
		this.TableColHeader = TableColHeader;
	}

	/**
	 * 获得数据表
	 * 
	 * @return
	 */
	public Table getData() {
		return data;
	}

	/**
	 * 设置数据表
	 * 
	 * @param data
	 */
	public void setData(Table data) {
		this.data = data;
		// 设置为数据类型
		data.setType(Report.DATA_TYPE);
	}
}