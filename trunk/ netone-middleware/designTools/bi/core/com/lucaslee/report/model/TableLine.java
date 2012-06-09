package com.lucaslee.report.model;

import com.lucaslee.report.ReportException;

/**
 * 表格中的一个组单元序列。
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
public abstract class TableLine implements Cloneable {

	/** 类型。值为后缀为"_TYPE"的常量.用来控制TableCell的cssClass属性 */
	protected String type = null;

	/**
	 * 获得指定的单元。
	 * 
	 * @param ind
	 *            列中单元号。
	 * @return TableCell
	 */
	public abstract TableCell getCell(int ind) throws ReportException;

	/**
	 * 获得单元数。
	 * 
	 * @return int
	 */
	public abstract int getCellCount();

	/** 向列中添加单元。 */

	public abstract void addCell(TableCell tc);

	/**
	 * 获得序列中所有单元。
	 * 
	 * @return TableCell[]
	 */
	public abstract TableCell[] getCells();

	/**
	 * 设置类型。
	 * 
	 * @return
	 */
	public void setType(String type) throws ReportException {
		this.type = type;
	}

	/**
	 * 获得类型。
	 * 
	 * @param type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置跨度值。
	 * 
	 * @param tc
	 *            要设置跨度的单元格。
	 * @param span
	 *            跨度值
	 */
	public abstract void setSpan(TableCell tc, int span);

	/**
	 * 获得跨度值。
	 * 
	 * @param tc
	 *            单元格
	 * @return 跨度值
	 */
	public abstract int getSpan(TableCell tc);

	@Override
	public TableLine clone() throws CloneNotSupportedException {
		return (TableLine) super.clone();
	}

	@Override
	public String toString() {
		if (this == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < this.getCellCount(); i++) {
			if (i > 0) {
				result.append(",");
			}
			result.append("[").append(this.getCell(i).toString()).append("]");
		}
		return result.toString();
	}

}