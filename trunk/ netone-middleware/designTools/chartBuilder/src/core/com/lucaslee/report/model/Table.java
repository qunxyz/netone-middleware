package com.lucaslee.report.model;

import java.util.ArrayList;
import java.util.List;

import com.lucaslee.report.ReportException;

/**
 * 定义：表示表格
 */
public class Table extends Rectangle implements Cloneable {

	/** 表格数据。包括行列，单元格 */
	private ArrayList<TableRow> data = new ArrayList<TableRow>();

	/** 表格的HTML的样式属性 */
	private String style = "border-collapse:collapse;";

	/** 单元格填充值 */
	private int cellpadding = 2;

	/** 单元格间距值 */
	private int cellspacing = 0;

	/** 类型。值为后缀为"_TYPE"的常量.用来控制TableCell的cssClass属性 */
	private String type = null;

	/** 用百分比表示的宽度 */
	protected int width = 0;

	/** 列的宽度，百分比 */
	private int[] widths;

	/**
	 * 获得单元格填充值
	 * 
	 * @return
	 */
	public int getCellpadding() {
		return cellpadding;
	}

	/**
	 * 获得单元格间距值
	 * 
	 * @return
	 */
	public int getCellspacing() {
		return cellspacing;
	}

	/**
	 * 获得用百分比表示的宽度
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 设置用百分比表示的宽度.
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		if (width > 100 && width < 0) {
			throw new RuntimeException("Width的值必须在0到100之间。");
		}
		this.width = width;
	}

	/**
	 * 设置单元格间距值
	 * 
	 * @param cellspacing
	 */
	public void setCellspacing(int cellspacing) {
		this.cellspacing = cellspacing;
	}

	/**
	 * 设置单元格填充值
	 * 
	 * @param cellpadding
	 */
	public void setCellpadding(int cellpadding) {
		this.cellpadding = cellpadding;
	}

	public Table() {
		// 设置默认值
		this.width = 75;
		this.align = Rectangle.ALIGN_CENTER;
	}

	/**
	 * 获得行数为row，列数为col的表格，所有单元内容都为空。
	 * 
	 * @param row
	 *            行数
	 * @param col
	 *            列数
	 */
	public Table(int row, int col) {
		this();
		for (int i = 0; i < row; i++) {
			this.data.add(new TableRow(col));
		}
	}

	/**
	 * 获得表格的HTML的样式属性
	 * 
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 设置表格的HTML的样式属性
	 * 
	 * @param s
	 */
	public void setStyle(String s) {
		style = s;
	}

	/**
	 * 获得表格的所有行。
	 * 
	 * @return TableRow[]
	 * @roseuid 3DAE48260308
	 */
	public TableRow[] getRows() {
		return (TableRow[]) data.toArray(new TableRow[0]);
	}

	/**
	 * 获得指定行
	 * 
	 * @param ind
	 *            行序号
	 * @return
	 */
	public TableRow getRow(int ind) {
		return (TableRow) data.get(ind);
	}

	/**
	 * 获得表格的所有列。
	 * 
	 * @return TableColumn[]
	 * @roseuid 3DAE48B60171
	 */
	public TableColumn[] getCols() {
		return null;
	}

	/**
	 * 获得表格的指定列。
	 * 
	 * @param ind
	 *            列号。
	 */
	public TableColumn getCol(int ind) throws ReportException {
		TableColumn rt = new TableColumn();
		for (int i = 0; i < data.size(); i++) {
			TableRow tr = (TableRow) data.get(i);
			// System.out.println(tr.toHTMLCode());
			if (tr == null) {
				throw new ReportException("表数据的格式错误.");
			}
			rt.addCell(tr.getCell(ind));
		}
		return rt;
	}

	/**
	 * 向表格添加一列。
	 * 
	 * @param c
	 *            列对象。
	 */
	public void addCol(TableColumn c) throws ReportException {
		if (data == null || data.size() == 0) { // 无数据则创建行；
			for (int i = 0; i < c.getCellCount(); i++) {
				TableRow r = new TableRow();
				r.addCell(c.getCell(i));
				data.add(r);
			}
		} else { // 有数据则判断是否格式正确，正确则添加数据。
			if (c.getCellCount() != data.size()) {
				throw new ReportException("添加的列和已有的数据冲突.");
			}
			for (int i = 0; i < data.size(); i++) {
				((TableRow) data.get(i)).addCell(c.getCell(i));
			}
		}
	}

	/**
	 * 向表格添加一行。
	 * 
	 * @param tr
	 *            行对象。
	 */
	public void addRow(TableRow tr) throws ReportException {
		if (data != null && data.size() != 0)
			if (this.getColCount() != tr.getCellCount())
				throw new ReportException("要添加的行与表格中已存在的行列数不同，添加失败。");
		data.add(tr);
	}

	/**
	 * 删除表格的一行。
	 * 
	 * @param tr
	 *            行对象。
	 */
	public void deleteRow(TableRow tr) {
		data.remove(tr);
	}

	/**
	 * 向表格插入一行
	 * 
	 * @param ind
	 *            行号。
	 * @param tr
	 *            行。
	 */
	public void insertRow(int ind, TableRow tr) {
		data.add(ind, tr);
	}

	/**
	 * 获得行数。
	 * 
	 * @return int
	 */
	public int getRowCount() {
		return data.size();
	}

	/**
	 * 设置表格的某列。
	 * 
	 * @param ind
	 *            列号。
	 * @param c
	 *            列。
	 */
	public void setCol(int ind, TableColumn c) throws ReportException {
		if (ind >= 0 && this.getColCount() >= ind
				&& c.getCellCount() == this.getRowCount()) {
			for (int i = 0; i < this.getRowCount(); i++) {
				this.getRow(i).setCell(ind, c.getCell(i));
			}
		} else {
			System.out
					.println("ind:" + ind + " col:" + c.getCellCount()
							+ " row:" + this.getRowCount() + " c:"
							+ this.getColCount());
			throw new ReportException("要设置的列和已有的数据冲突.");
		}
	}

	/**
	 * 设置表格的某行
	 * 
	 * @param ind
	 *            行序号
	 * @param r
	 *            行对象
	 * @throws ReportException
	 */
	public void setRow(int ind, TableRow r) throws ReportException {
		data.set(ind, r);
	}

	/**
	 * 获得列数。
	 * 
	 * @return int
	 */
	public int getColCount() {
		int rt = 0;
		if (data != null && this.getRowCount() > 0) {
			rt = ((TableRow) data.get(0)).getCellCount();
		}
		return rt;
	}

	/**
	 * 按行号，列号获得一个单元。
	 * 
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @return
	 */
	public TableCell getCell(int row, int col) throws ReportException {
		return this.getRow(row).getCell(col);
	}

	/**
	 * 按行号、列号设置一个单元格。
	 * 
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @param tc
	 *            单元格s
	 * @throws ReportException
	 */
	public void setCell(int row, int col, TableCell tc) throws ReportException {
		TableRow tr = this.getRow(row);
		tr.setCell(col, tc);
		this.setRow(row, tr);
	}

	/**
	 * 返回一个完全的克隆对象。 即复制的对象的子对象(包括所有后代)与原对象是不同的。
	 */
	public Table cloneAll() throws ReportException {
		Table rt = null;
		try {
			rt = (Table) this.clone();
		} catch (CloneNotSupportedException ex) {
			throw new ReportException(ex.getMessage());
		}
		// 克隆容器
		rt.data = (ArrayList<TableRow>) this.data.clone();
		for (int i = 0; i < this.getRowCount(); i++) {
			// 克隆容器中的元素
			rt.setRow(i, (TableRow) this.getRow(i).cloneAll());
		}
		return rt;
	}

	/**
	 * 获得翻转表,按对角线翻转.注意表格，行的属性没有复制。
	 * 
	 * @return
	 * @throws ReportException
	 */
	public Table getRotateTable() throws ReportException {
		// Table result = new Table(this.getColCount(), this.getRowCount()); //
		// 行列互换
		// // 复制Table的属性
		// this.copyAttributesTo(result);
		Table result;
		try {
			result = (Table) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new ReportException(e);
		}
		// 清空cell数据
		result.data = new ArrayList<TableRow>();

		TableCell temp = new TableCell();
		int tempSpan;
		for (int i = 0; i < this.getRowCount(); i++) {
			TableColumn column = new TableColumn();
			for (int j = 0; j < this.getColCount(); j++) {
				temp = this.getCell(i, j);
				tempSpan = temp.getColSpan();
				temp.setColSpan(temp.getRowSpan());
				temp.setRowSpan(tempSpan);

				// result.setCell(j, i, temp);
				column.addCell(temp);
			}
			result.addCol(column);
		}

		return result;
	}

	/**
	 * 获得类型
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型。
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获得表格数据
	 * 
	 * @return
	 */
	public ArrayList<TableRow> getData() {
		return data;
	}

	/**
	 * 设置表格数据
	 * 
	 * @param data
	 */
	public void setData(ArrayList<TableRow> data) {
		this.data = data;
	}

	/**
	 * 获得所有表格列的相对宽度。
	 * 
	 * @return
	 */
	public int[] getWidths() {
		return widths;
	}

	/**
	 * 设置表格所有列的相对宽度（按百分比表示）。
	 * 
	 * @param widths
	 */
	public void setWidths(int[] widths) {
		this.widths = widths;
	}

	// /**用于测试cloneAll*/
	// protectd void showDiff(Object o1,Object o2,Class c)throws Exception{
	// Field[] fs= c.getDeclaredFields();
	// if (fs != null) {
	// for (int i = 0; i < fs.length; i++) {
	// if(fs[i].get(o1)==null){
	// if(fs[i].get(o1)!=null)
	// System.out.println(""+fs[i].getName()+ " not both null.");
	// }else if (!fs[i].get(o1).equals(fs[i].get(o2))){
	// System.err.println("compare '" + fs[i].getName() +
	// "' not equals.");
	// }
	// }
	//
	// }
	// if(c.equals(Table.class)){
	// Table table1=(Table)o1;
	// Table table2=(Table)o2;
	// for (int i = 0; i < table1.getRowCount(); i++) {
	// showDiff(table1.getRow(i),table2.getRow(i),TableRow.class);
	// showDiff(table1.getRow(i),table2.getRow(i),TableLine.class);
	//
	// for(int j=0;j<table1.getColCount();j++){
	// showDiff(table1.getCell(i,j),table2.getCell(i,j),TableCell.class);
	// showDiff(table1.getCell(i,j),table2.getCell(i,j),Rectangle.class);
	// }
	// }
	//
	// }
	// }
	@Override
	public String toString() {
		if (this == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < this.getRowCount(); i++) {

			result.append(this.getRow(i).toString()).append("\n");
		}
		return result.toString();
	}
}