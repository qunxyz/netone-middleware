package com.lucaslee.report.model;

import java.util.ArrayList;

import com.lucaslee.report.ReportException;

/**
 * ����С�<BR>
 * ���в�ͬ��������ɾ��Ԫ���Ƿ�����ʶ����õġ�
 */
public class TableColumn extends TableLine {

	/** �������� */
	private ArrayList<TableCell> data = new ArrayList<TableCell>();

	public TableColumn() {

	}

	/**
	 * @param cellCount
	 *            ��Ԫ��
	 */
	public TableColumn(int cellCount) {
		for (int i = 0; i < cellCount; i++) {
			TableCell c = new TableCell("");
			this.data.add(c);
		}
	}

	/**
	 * ����������е�Ԫ��
	 * 
	 * @return TableCell[]
	 */
	public TableCell[] getCells() {
		return null;
	}

	/**
	 * ������е�Ԫ����
	 */
	public int getCellCount() {
		return data.size();
	}

	/** ��������ӵ�Ԫ�� */
	public void addCell(TableCell tc) {
		data.add(tc);
	}

	/**
	 * ���ָ���ĵ�Ԫ��
	 * 
	 * @param ind
	 *            ���е�Ԫ�š�
	 */
	public TableCell getCell(int ind) {
		return (TableCell) data.get(ind);
	}

	/**
	 * ����һ����ȫ�Ŀ�¡���� �����ƵĶ�����Ӷ���(�������к��)��ԭ�����ǲ�ͬ�ġ�
	 */
	public TableColumn cloneAll() throws ReportException {
		TableColumn rt;
		try {
			rt = (TableColumn) this.clone();

			// ��¡����
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
	 * ���õ�Ԫ��Ŀ��ֵ
	 * 
	 * @param tc
	 *            ��Ԫ��
	 * @param span
	 *            ���ֵ
	 */
	public void setSpan(TableCell tc, int span) {
		tc.setRowSpan(span);
	}

	/**
	 * ��õ�Ԫ��Ŀ��ֵ.
	 * 
	 * @param tc
	 * @return
	 */
	public int getSpan(TableCell tc) {
		return tc.getRowSpan();
	}

	/**
	 * ���������
	 * 
	 * @return
	 */
	public ArrayList<TableCell> getData() {
		return data;
	}

	/**
	 * ���������ݡ�
	 * 
	 * @param data
	 */
	public void setData(ArrayList<TableCell> data) {
		this.data = data;
	}
}