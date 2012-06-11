package com.lucaslee.report.model;

import java.util.ArrayList;

import com.lucaslee.report.ReportException;

/**
 * �����С�����������ɾ��Ԫ��
 */
public class TableRow extends TableLine {
	/** �������� */
	private ArrayList<TableCell> data = new ArrayList<TableCell>();

	public TableRow() {

	}

	/**
	 * ����һ�����С�
	 * 
	 * @param rowCount
	 *            ���е�Ԫ�ĸ�����
	 */
	public TableRow(int rowCount) {
		this();
		for (int i = 0; i < rowCount; i++) {
			TableCell c = new TableCell("");
			data.add(c);
		}
	}

	/**
	 * ������еĵ�Ԫ��
	 * 
	 * @return TableCell[]
	 */
	public TableCell[] getCells() {
		return (TableCell[]) data.toArray(new TableCell[0]);
	}

	/**
	 * ��ӵ�Ԫ��
	 * 
	 * @param cell
	 */
	public void addCell(TableCell cell) {
		data.add(cell);
	}

	/**
	 * ɾ��ָ���ĵ�Ԫ��
	 * 
	 * @param cell
	 *            Ҫɾ���ĵ�Ԫ��
	 */
	public void deleteCell(TableCell cell) {
		data.remove(cell);
	}

	/**
	 * ��õ�Ԫ����
	 * 
	 * @return int
	 */
	public int getCellCount() {
		return data.size();
	}

	/**
	 * ���ָ��λ�õĵ�Ԫ��
	 * 
	 * @param ind
	 *            ��Ԫ��λ�á�
	 * @return TableCell
	 */
	public TableCell getCell(int ind) throws ReportException {
		if (ind < 0 || ind > (data.size() - 1)) {
			throw new ReportException("������û�����" + ind + "��Ԫ");
		}
		return (TableCell) data.get(ind);
	}

	/**
	 * ����ָ��λ�õĵ�Ԫ��
	 * 
	 * @param ind
	 *            ��Ԫλ�úš�
	 * @param tc
	 *            ��Ԫ����
	 */
	public void setCell(int ind, TableCell tc) {
		data.set(ind, tc);
	}

	/**
	 * ����һ����ȫ�Ŀ�¡���� �����ƵĶ�����Ӷ���(�������к��)��ԭ�����ǲ�ͬ�ġ�
	 */
	public TableRow cloneAll() throws ReportException {
		TableRow rt = null;
		try {
			rt = (TableRow) this.clone();
			// ��¡����
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
	 * ���õ�Ԫ����ֵ��
	 * 
	 * @param tc
	 *            ��Ԫ��
	 * @param span
	 *            ���ֵ
	 */
	public void setSpan(TableCell tc, int span) {
		tc.setColSpan(span);
	}

	/**
	 * ��õ�Ԫ����ֵ
	 * 
	 * @param tc
	 *            ��Ԫ��
	 * @return ���ֵ
	 */
	public int getSpan(TableCell tc) {
		return tc.getColSpan();
	}

	/**
	 * ���뵥Ԫ��
	 * 
	 * @param cell
	 *            ��Ԫ��
	 * @param index
	 *            λ����š�ԭ�ȵ��ڻ���ڴ���ŵ�Ԫ�غ��ơ�
	 */
	public void insertCell(TableCell cell, int index) {
		data.add(index, cell);
	}

	/**
	 * ��ñ��������
	 * 
	 * @return
	 */
	public ArrayList getData() {
		return data;
	}

	/**
	 * ���ñ��������
	 * 
	 * @param data
	 */
	public void setData(ArrayList<TableCell> data) {
		this.data = data;
	}

}