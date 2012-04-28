package com.lucaslee.report.model;

import java.util.ArrayList;

import com.lucaslee.report.ReportException;

/**
 * 表格列。<BR>
 * 与行不同，不可增删单元，是方便访问而设置的。
 */
public class TableColumn extends TableLine {

	/** 数据容器 */
	private ArrayList<TableCell> data = new ArrayList<TableCell>();

	public TableColumn() {

	}

	/**
	 * @param cellCount
	 *            单元数
	 */
	public TableColumn(int cellCount) {
		for (int i = 0; i < cellCount; i++) {
			TableCell c = new TableCell("");
			this.data.add(c);
		}
	}

	/**
	 * 获得列中所有单元。
	 * 
	 * @return TableCell[]
	 */
	public TableCell[] getCells() {
		return null;
	}

	/**
	 * 获得列中单元数。
	 */
	public int getCellCount() {
		return data.size();
	}

	/** 向列中添加单元。 */
	public void addCell(TableCell tc) {
		data.add(tc);
	}

	/**
	 * 获得指定的单元。
	 * 
	 * @param ind
	 *            列中单元号。
	 */
	public TableCell getCell(int ind) {
		return (TableCell) data.get(ind);
	}

	/**
	 * 返回一个完全的克隆对象。 即复制的对象的子对象(包括所有后代)与原对象是不同的。
	 */
	public TableColumn cloneAll() throws ReportException {
		TableColumn rt;
		try {
			rt = (TableColumn) this.clone();

			// 克隆容器
			rt.data = (ArrayList<TableCell>) this.data.clone();

			for (int i = 0; i < this.getCellCount(); i++) {
				rt.addCell((TableCell) this.getCell(i).clone());
			}
			
		} catch (CloneNotSupportedException e) {
			throw new ReportException(e);
		}
		return rt;
	}

	/**
	 * 设置单元格的跨度值
	 * 
	 * @param tc
	 *            单元格
	 * @param span
	 *            跨度值
	 */
	public void setSpan(TableCell tc, int span) {
		tc.setRowSpan(span);
	}

	/**
	 * 获得单元格的跨度值.
	 * 
	 * @param tc
	 * @return
	 */
	public int getSpan(TableCell tc) {
		return tc.getRowSpan();
	}

	/**
	 * 获得列数据
	 * 
	 * @return
	 */
	public ArrayList<TableCell> getData() {
		return data;
	}

	/**
	 * 设置列数据。
	 * 
	 * @param data
	 */
	public void setData(ArrayList<TableCell> data) {
		this.data = data;
	}
}