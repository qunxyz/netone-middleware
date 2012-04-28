package com.lucaslee.report.model;

import java.util.ArrayList;
import java.util.List;

import com.lucaslee.report.ReportException;

/**
 * ���壺��ʾ���
 */
public class Table extends Rectangle implements Cloneable {

	/** ������ݡ��������У���Ԫ�� */
	private ArrayList<TableRow> data = new ArrayList<TableRow>();

	/** ����HTML����ʽ���� */
	private String style = "border-collapse:collapse;";

	/** ��Ԫ�����ֵ */
	private int cellpadding = 2;

	/** ��Ԫ����ֵ */
	private int cellspacing = 0;

	/** ���͡�ֵΪ��׺Ϊ"_TYPE"�ĳ���.��������TableCell��cssClass���� */
	private String type = null;

	/** �ðٷֱȱ�ʾ�Ŀ�� */
	protected int width = 0;

	/** �еĿ�ȣ��ٷֱ� */
	private int[] widths;

	/**
	 * ��õ�Ԫ�����ֵ
	 * 
	 * @return
	 */
	public int getCellpadding() {
		return cellpadding;
	}

	/**
	 * ��õ�Ԫ����ֵ
	 * 
	 * @return
	 */
	public int getCellspacing() {
		return cellspacing;
	}

	/**
	 * ����ðٷֱȱ�ʾ�Ŀ��
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * �����ðٷֱȱ�ʾ�Ŀ��.
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		if (width > 100 && width < 0) {
			throw new RuntimeException("Width��ֵ������0��100֮�䡣");
		}
		this.width = width;
	}

	/**
	 * ���õ�Ԫ����ֵ
	 * 
	 * @param cellspacing
	 */
	public void setCellspacing(int cellspacing) {
		this.cellspacing = cellspacing;
	}

	/**
	 * ���õ�Ԫ�����ֵ
	 * 
	 * @param cellpadding
	 */
	public void setCellpadding(int cellpadding) {
		this.cellpadding = cellpadding;
	}

	public Table() {
		// ����Ĭ��ֵ
		this.width = 75;
		this.align = Rectangle.ALIGN_CENTER;
	}

	/**
	 * �������Ϊrow������Ϊcol�ı�����е�Ԫ���ݶ�Ϊ�ա�
	 * 
	 * @param row
	 *            ����
	 * @param col
	 *            ����
	 */
	public Table(int row, int col) {
		this();
		for (int i = 0; i < row; i++) {
			this.data.add(new TableRow(col));
		}
	}

	/**
	 * ��ñ���HTML����ʽ����
	 * 
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * ���ñ���HTML����ʽ����
	 * 
	 * @param s
	 */
	public void setStyle(String s) {
		style = s;
	}

	/**
	 * ��ñ��������С�
	 * 
	 * @return TableRow[]
	 * @roseuid 3DAE48260308
	 */
	public TableRow[] getRows() {
		return (TableRow[]) data.toArray(new TableRow[0]);
	}

	/**
	 * ���ָ����
	 * 
	 * @param ind
	 *            �����
	 * @return
	 */
	public TableRow getRow(int ind) {
		return (TableRow) data.get(ind);
	}

	/**
	 * ��ñ��������С�
	 * 
	 * @return TableColumn[]
	 * @roseuid 3DAE48B60171
	 */
	public TableColumn[] getCols() {
		return null;
	}

	/**
	 * ��ñ���ָ���С�
	 * 
	 * @param ind
	 *            �кš�
	 */
	public TableColumn getCol(int ind) throws ReportException {
		TableColumn rt = new TableColumn();
		for (int i = 0; i < data.size(); i++) {
			TableRow tr = (TableRow) data.get(i);
			// System.out.println(tr.toHTMLCode());
			if (tr == null) {
				throw new ReportException("�����ݵĸ�ʽ����.");
			}
			rt.addCell(tr.getCell(ind));
		}
		return rt;
	}

	/**
	 * �������һ�С�
	 * 
	 * @param c
	 *            �ж���
	 */
	public void addCol(TableColumn c) throws ReportException {
		if (data == null || data.size() == 0) { // �������򴴽��У�
			for (int i = 0; i < c.getCellCount(); i++) {
				TableRow r = new TableRow();
				r.addCell(c.getCell(i));
				data.add(r);
			}
		} else { // ���������ж��Ƿ��ʽ��ȷ����ȷ��������ݡ�
			if (c.getCellCount() != data.size()) {
				throw new ReportException("��ӵ��к����е����ݳ�ͻ.");
			}
			for (int i = 0; i < data.size(); i++) {
				((TableRow) data.get(i)).addCell(c.getCell(i));
			}
		}
	}

	/**
	 * �������һ�С�
	 * 
	 * @param tr
	 *            �ж���
	 */
	public void addRow(TableRow tr) throws ReportException {
		if (data != null && data.size() != 0)
			if (this.getColCount() != tr.getCellCount())
				throw new ReportException("Ҫ��ӵ����������Ѵ��ڵ���������ͬ�����ʧ�ܡ�");
		data.add(tr);
	}

	/**
	 * ɾ������һ�С�
	 * 
	 * @param tr
	 *            �ж���
	 */
	public void deleteRow(TableRow tr) {
		data.remove(tr);
	}

	/**
	 * �������һ��
	 * 
	 * @param ind
	 *            �кš�
	 * @param tr
	 *            �С�
	 */
	public void insertRow(int ind, TableRow tr) {
		data.add(ind, tr);
	}

	/**
	 * ���������
	 * 
	 * @return int
	 */
	public int getRowCount() {
		return data.size();
	}

	/**
	 * ���ñ���ĳ�С�
	 * 
	 * @param ind
	 *            �кš�
	 * @param c
	 *            �С�
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
			throw new ReportException("Ҫ���õ��к����е����ݳ�ͻ.");
		}
	}

	/**
	 * ���ñ���ĳ��
	 * 
	 * @param ind
	 *            �����
	 * @param r
	 *            �ж���
	 * @throws ReportException
	 */
	public void setRow(int ind, TableRow r) throws ReportException {
		data.set(ind, r);
	}

	/**
	 * ���������
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
	 * ���кţ��кŻ��һ����Ԫ��
	 * 
	 * @param row
	 *            �к�
	 * @param col
	 *            �к�
	 * @return
	 */
	public TableCell getCell(int row, int col) throws ReportException {
		return this.getRow(row).getCell(col);
	}

	/**
	 * ���кš��к�����һ����Ԫ��
	 * 
	 * @param row
	 *            �к�
	 * @param col
	 *            �к�
	 * @param tc
	 *            ��Ԫ��s
	 * @throws ReportException
	 */
	public void setCell(int row, int col, TableCell tc) throws ReportException {
		TableRow tr = this.getRow(row);
		tr.setCell(col, tc);
		this.setRow(row, tr);
	}

	/**
	 * ����һ����ȫ�Ŀ�¡���� �����ƵĶ�����Ӷ���(�������к��)��ԭ�����ǲ�ͬ�ġ�
	 */
	public Table cloneAll() throws ReportException {
		Table rt = null;
		try {
			rt = (Table) this.clone();
		} catch (CloneNotSupportedException ex) {
			throw new ReportException(ex.getMessage());
		}
		// ��¡����
		rt.data = (ArrayList<TableRow>) this.data.clone();
		for (int i = 0; i < this.getRowCount(); i++) {
			// ��¡�����е�Ԫ��
			rt.setRow(i, (TableRow) this.getRow(i).cloneAll());
		}
		return rt;
	}

	/**
	 * ��÷�ת��,���Խ��߷�ת.ע�����е�����û�и��ơ�
	 * 
	 * @return
	 * @throws ReportException
	 */
	public Table getRotateTable() throws ReportException {
		// Table result = new Table(this.getColCount(), this.getRowCount()); //
		// ���л���
		// // ����Table������
		// this.copyAttributesTo(result);
		Table result;
		try {
			result = (Table) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new ReportException(e);
		}
		// ���cell����
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
	 * �������
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * �������͡�
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * ��ñ������
	 * 
	 * @return
	 */
	public ArrayList<TableRow> getData() {
		return data;
	}

	/**
	 * ���ñ������
	 * 
	 * @param data
	 */
	public void setData(ArrayList<TableRow> data) {
		this.data = data;
	}

	/**
	 * ������б���е���Կ�ȡ�
	 * 
	 * @return
	 */
	public int[] getWidths() {
		return widths;
	}

	/**
	 * ���ñ�������е���Կ�ȣ����ٷֱȱ�ʾ����
	 * 
	 * @param widths
	 */
	public void setWidths(int[] widths) {
		this.widths = widths;
	}

	// /**���ڲ���cloneAll*/
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