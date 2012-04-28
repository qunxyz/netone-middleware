package com.jl.common.report.obj.core.table;

import com.jl.common.report.obj.core.Read_clr;

public class Read_td {
	private int width;
	private int height;
	private String tableid;
	private int row;
	private int rowspan;
	private int col;
	private int colspan;
	private String control;
	private Read_clr tdclr;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public Read_clr getTdclr() {
		return tdclr;
	}

	public void setTdclr(Read_clr tdclr) {
		this.tdclr = tdclr;
	}

}
