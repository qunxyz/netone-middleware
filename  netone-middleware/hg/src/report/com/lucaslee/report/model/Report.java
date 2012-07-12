package com.lucaslee.report.model;

/**
 * 报表。
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

	/** **********************单元序列对象的类型,用于样式表************************** */
	/** 数据 */
	public static final String DATA_TYPE = "MyReport_data";

	/** 分组汇总 */
	public static final String GROUP_TOTAL_TYPE = "MyReport_groupTotal";

	/** 汇总 */
	public static final String TOTAL_TYPE = "MyReport_total";

	/** 行头或列头 */
	public static final String HEAD_TYPE = "MyReport_head";

	/** 标题 */
	public static final String TITLE_TYPE = "MyReport_title";

	/** 交叉表的表头的表头 */
	public static final String CROSS_HEAD_HEAD_TYPE = "MyReport_cross_head_head";

	/** *********************************************************************** */

	/**
	 * 首注表格。
	 */
	private Table headerTable;

	/**
	 * 主体表格。
	 */
	private ReportBody body;

	/**
	 * 脚注表格。
	 */
	private Table footerTable;

	public Report() {
	}

	/**
	 * 获得脚注表格
	 * 
	 * @return
	 */
	public Table getFooterTable() {
		return footerTable;
	}

	/**
	 * 获得首注表格
	 * 
	 * @return
	 */
	public Table getHeaderTable() {
		return headerTable;
	}

	/**
	 * 获得主体表格
	 * 
	 * @return
	 */
	public ReportBody getBody() {
		return body;
	}

	/**
	 * 设置主体表格
	 * 
	 * @param mainTable
	 */
	public void setBody(ReportBody mainTable) {
		this.body = mainTable;
	}

	/**
	 * 设置首注表格
	 * 
	 * @param headerTable
	 */
	public void setHeaderTable(Table headerTable) {
		this.headerTable = headerTable;
	}

	/**
	 * 设置脚注表格
	 * 
	 * @param footerTable
	 */
	public void setFooterTable(Table footerTable) {
		this.footerTable = footerTable;
	}

}