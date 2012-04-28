package com.lucaslee.report.model;

import java.util.ArrayList;

import com.lucaslee.report.ReportException;

/**
 * 表格的行。可以自由增删单元。
 */
public class TableRow extends TableLine {
	/** 数据容器 */
	private ArrayList<TableCell> data = new ArrayList<TableCell>();

	public TableRow() {

	}

	/**
	 * 创建一个空行。
	 * 
	 * @param rowCount
	 *            行中单元的个数。
	 */
	public TableRow(int rowCount) {
		this();
		for (int i = 0; i < rowCount; i++) {
			TableCell c = new TableCell("");
			data.add(c);
		}
	}

	/**
	 * 获得所有的单元。
	 * 
	 * @return TableCell[]
	 */
	public TableCell[] getCells() {
		return (TableCell[]) data.toArray(new TableCell[0]);
	}

	/**
	 * 添加单元。
	 * 
	 * @param cell
	 */
	public void addCell(TableCell cell) {
		data.add(cell);
	}

	/**
	 * 删除指定的单元。
	 * 
	 * @param cell
	 *            要删除的单元。
	 */
	public void deleteCell(TableCell cell) {
		data.remove(cell);
	}

	/**
	 * 获得单元数。
	 * 
	 * @return int
	 */
	public int getCellCount() {
		return data.size();
	}

	/**
	 * 获得指定位置的单元。
	 * 
	 * @param ind
	 *            单元的位置。
	 * @return TableCell
	 */
	public TableCell getCell(int ind) throws ReportException {
		if (ind < 0 || ind > (data.size() - 1)) {
			throw new ReportException("在行中没有这个" + ind + "单元");
		}
		return (TableCell) data.get(ind);
	}

	/**
	 * 设置指定位置的单元。
	 * 
	 * @param ind
	 *            单元位置号。
	 * @param tc
	 *            单元对象。
	 */
	public void setCell(int ind, TableCell tc) {
		data.set(ind, tc);
	}

	/**
	 * 返回一个完全的克隆对象。 即复制的对象的子对象(包括所有后代)与原对象是不同的。
	 */
	public TableRow cloneAll() throws ReportException {
		TableRow rt = null;
		try {
			rt = (TableRow) this.clone();
			// 克隆容器
			rt.data = (ArrayList<TableCell>) this.data.clone();

			for (int i = 0; i < this.getCellCount(); i++) {
				rt.setCell(i, (TableCell) this.getCell(i).clone());
			}
		} catch (CloneNotSupportedException ex) {
			throw new ReportException(ex.getMessage());
		}
		return rt;
	}

	/**
	 * 设置单元格跨度值。
	 * 
	 * @param tc
	 *            单元格
	 * @param span
	 *            跨度值
	 */
	public void setSpan(TableCell tc, int span) {
		tc.setColSpan(span);
	}

	/**
	 * 获得单元格跨度值
	 * 
	 * @param tc
	 *            单元格
	 * @return 跨度值
	 */
	public int getSpan(TableCell tc) {
		return tc.getColSpan();
	}

	/**
	 * 插入单元格。
	 * 
	 * @param cell
	 *            单元格
	 * @param index
	 *            位置序号。原先等于或大于此序号的元素后移。
	 */
	public void insertCell(TableCell cell, int index) {
		data.add(index, cell);
	}

	/**
	 * 获得表格行数据
	 * 
	 * @return
	 */
	public ArrayList getData() {
		return data;
	}

	/**
	 * 设置表格行数据
	 * 
	 * @param data
	 */
	public void setData(ArrayList<TableCell> data) {
		this.data = data;
	}

}