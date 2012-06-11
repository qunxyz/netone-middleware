package com.lucaslee.report.model.crosstable;

import java.util.Vector;

/**
 * 交叉表的行头和列头。
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
public class HeadCol {
	public HeadCol() {
	}

	/**
	 * 
	 * @param index
	 *            交叉表头在原始数据表格中的列序号
	 * @param headerText
	 *            说明文字
	 */
	public HeadCol(int index, String headerText) {
		this.index = index;
		this.headerText = headerText;
	}

	/**
	 * 
	 * @param index
	 *            交叉表头在原始数据表格中的列序号
	 * @param headerText
	 *            说明文字
	 * @param sortSeq
	 *            用于执行排序的值序列
	 */
	public HeadCol(int index, String headerText, Vector sortSeq) {
		this.index = index;
		this.headerText = headerText;
		this.sortSeq = sortSeq;
	}

	/** 交叉表头在原始数据表格中的列序号 */
	private int index;

	/**
	 * 说明文字。
	 */
	private String headerText;

	/**
	 * 用于执行排序的值序列.
	 */
	private Vector sortSeq;

	/**
	 * 获得交叉表头原始表格数据中的列序号。
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置交叉表头原始表格数据中的列序号。
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 获得用于执行排序的值序列
	 * 
	 * @return
	 */
	public Vector getSortSeq() {
		return sortSeq;
	}

	/**
	 * 设置用于执行排序的值序列
	 * 
	 * @param sortSeq
	 */
	public void setSortSeq(Vector sortSeq) {
		this.sortSeq = sortSeq;
	}

	/**
	 * 获得说明文字
	 * 
	 * @return
	 */
	public String getHeaderText() {
		return headerText;
	}

	/**
	 * 设置说明文字
	 * 
	 * @param headerText
	 */
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

}