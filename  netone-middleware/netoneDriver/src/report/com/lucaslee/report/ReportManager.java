package com.lucaslee.report;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.lang.ObjectUtils;

import com.lucaslee.report.grouparithmetic.GroupArithmetic;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableColumn;
import com.lucaslee.report.model.TableLine;
import com.lucaslee.report.model.TableRow;
import com.lucaslee.report.model.crosstable.CrossTable;
import com.lucaslee.report.model.crosstable.HeadCol;

/**
 * �������������Ҫ�ı���������ڴ����н��С�
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

public class ReportManager {

	// Ϊtrue���ӡ������Ϣ
	private boolean debug = false;

	/** �з���,��������ķ���ˮƽ���ҡ� */
	public static final int ROW_ORIENTATION = 1;

	/** �з��򣬼�������ķ��򣬴�ֱ���� */
	public static final int COLUMN_ORIENTATION = 2;

	/**
	 * ���һ�����Ƿ��������С�
	 * 
	 * @param a
	 *            Ҫ������
	 * @param col
	 *            ����
	 * @return
	 */
	private boolean isAmonge(int a, int[] col) {
		for (int i = 0; i < col.length; i++) {
			if (a == col[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���һ��������.
	 * 
	 * @param t
	 *            ������
	 * @param from
	 *            ��ʼ��,��������
	 * @param end
	 *            ��ֹ��,����������
	 * @param memo
	 *            �����е�ע��
	 * @param totalCols
	 *            ���л����к�����
	 * @param arith
	 *            ͳ���㷨
	 */
	private TableRow getTotalRow(Table t, int from, int end, String memo,
			int[] totalCols, GroupArithmetic arith) throws ReportException {
		TableRow totalRow = new TableRow();

		int[] tempTotalCols = (int[]) totalCols.clone(); // ��¡���±�����Ϊ�˲��ı���ڲ���
		Arrays.sort(tempTotalCols);
		int nonTotal = 0; // ָ��ͷ��������Ҫ���ܵ��У�ֵΪ���������һ��������
		if (tempTotalCols.length > 0) {
			nonTotal = tempTotalCols[0] - 1;
		}

		// ���ע�͵�Ԫ
		TableCell desc = new TableCell(memo);
		desc.setColSpan(nonTotal + 1);
		totalRow.addCell(desc);

		// �⼸����Ԫ��Ϊ�˷��ϱ���׼��ÿ�е�cell��һ�£�,ʹÿ�еĵ�Ԫ�����,����ʾ����
		for (int i = 0; i < nonTotal; i++) {
			TableCell nullCell = new TableCell("");
			nullCell.setIsHidden(true);
			totalRow.addCell(nullCell);
		}

		for (int k = 0; k < t.getColCount(); k++) { // ������
			if (k > nonTotal) { // "С��"�в��������
				TableCell c = new TableCell("");

				if (isAmonge(k, totalCols)) { // ָ����ͳ���вŽ��л���,��ͳ������һ�����ַ���

					double[] values = new double[end - from]; // ��¼Ҫ���ܵ�һ�����е�����
					for (int j = from; j < end; j++) { // ��������ɨ��
						try {
							if (debug) {
								System.out.println("row:" + j + " col:" + k);
							}
							String value = (String) t.getRow(j).getCell(k)
									.getContent();
							if (value != null) {
								values[j - from] = Double.parseDouble(value);
							}
						} catch (NumberFormatException e) {
						}
					}
					c.setContent(arith.getResult(values) + "");
				}
				totalRow.addCell(c);
			}
		}

		return totalRow;
	}

	/**
	 * �ø������γɵı���߷ָ����С� һ��������ж�����Ҫ����ֵͬ�ϲ����˷���ʹǰ�����������ȫ���ݺ������.
	 * �����߼���˵�����˱��ʾһ�������ǰ��������Ǻ���ĸ������ࡣ ʵ�ֵĲ����ǣ��ø������γɵı���߷ָ����С�
	 * 
	 * @param t
	 *            ���
	 * @param cols
	 *            Ҫ���кϲ�����,Ӧ����������
	 */
	public Table split(Table t, int[] cols) throws ReportException {
		for (int i = 1; i < cols.length; i++) { // ��������ɨ��
			TableColumn pre = t.getCol(i - 1);
			TableColumn curr = t.getCol(i);
			for (int j = 0; j < pre.getCellCount(); j++) { // ��������ɨ��
				if (pre.getCell(j).getIsHidden() == false) {
					if (curr.getCell(j).getIsHidden() == false || j == 0) {
						continue;
					}
					for (int k = j - 1; k >= 0; k--) { // ��������ɨ��
						if (curr.getCell(k).getIsHidden() == false) {
							curr.getCell(j).setRowSpan(
									curr.getCell(k).getRowSpan() - (j - k));
							curr.getCell(k).setRowSpan(j - k);
							break;
						}
					}
					curr.getCell(j).setIsHidden(false);

				}
			}
		}
		return t;
	}

	/**
	 * ��ʽ�����������ݡ�
	 * 
	 * @param t
	 *            �������ݱ�
	 * @param crossTab
	 *            �������
	 * @param f
	 *            ��ʽ������
	 * @return ���������ݱ�
	 * @throws ReportException
	 */
	public Table formatData(Table t, CrossTable crossTab, Formatter f)
			throws ReportException {
		return formatData(t, crossTab.getColHeader().length, t.getRowCount(),
				crossTab.getRowHeader().length, t.getColCount(), f);
	}

	/**
	 * ��ʽ������.
	 * 
	 * @param t
	 *            Ҫ����ı�
	 * @param fromRow
	 *            ��ʼ��,��������
	 * @param toRow
	 *            ��ֹ��,����������
	 * @param fromCol
	 *            ��ʼ��,��������
	 * @param toCol
	 *            ��ֹ��,����������
	 * @param f
	 *            ��ʽ������
	 * @return ������ı�
	 * @throws ReportException
	 */
	public Table formatData(Table t, int fromRow, int toRow, int fromCol,
			int toCol, Formatter f) throws ReportException {
		String str;
		for (int i = fromRow; i < toRow; i++) {
			for (int j = fromCol; j < toCol; j++) {
				try {
					str = f.format((String) t.getCell(i, j).getContent());
					t.getCell(i, j).setContent(str);
				} catch (IllegalArgumentException e) {
					// ���Բ�����
				} catch (ParseException e) {
					// ���Բ�����
				}
			} // for
		}
		return t;

	}

	/**
	 * ��ʽ���������.
	 * 
	 * @param t
	 *            ���
	 * @param cols
	 *            �ڴ���������вŽ��и�ʽ��
	 * @param f
	 *            ��ʽ������
	 */
	public Table formatData(Table t, int[] cols, Formatter f)
			throws ReportException {
		for (int i = 0; i < cols.length; i++) {
			formatData(t, 0, t.getRowCount(), cols[i], cols[i] + 1, f);
		}
		return t;
	}

	/**
	 * ����һ���л��У��ϲ�������������ͬ�ĵ�Ԫ��
	 * 
	 * @param line
	 *            Ҫ�������ж���
	 * @return
	 */
	public TableLine mergeSameCell(TableLine line) throws ReportException {

		String curr = null; // ��ǰ��Ԫ����
		String pre = null; // ��һ��Ԫ����
		int pointer = 0; // λ��ָ�룬ָ������ֵͬ��Ԫ��ĵ�һ����Ԫ
		int count = 1; // ����ֵͬ��Ԫ���ڵ�Ԫ��
		for (int i = 0; i < line.getCellCount(); i++) { // ��������ɨ��
			curr = (String) line.getCell(i).getContent();
			if (i > 0) {
				if ((curr != null && ObjectUtils.equals(curr, pre))
						|| (curr == null && pre == null)) { // ����ǰ��Ԫ����һ����Ԫֵ��ȣ�
					count++;
					// ���õ�һ��ֵͬ��Ԫ��spanֵ��1;
					line.setSpan(line.getCell(pointer), count);
					line.getCell(i).setIsHidden(true); // �Ҳ���ʾ����Ԫ��
				} else { // �������
					pointer = i; // ��ָ��ָ�򱾵�Ԫ
					count = 1; // ����ռ�����
				}
			}
			pre = curr;
		}
		return line;
	}

	/**
	 * ����һ�������󣬺ϲ�������������ͬ�ĵ�Ԫ��
	 * 
	 * @param t
	 *            ���
	 * @param lines
	 *            Ҫ�ϲ����е��кš�
	 * @param orientation
	 *            �������л��з��򡣽��ܱ����к�׺Ϊ_ORIENTATION�ĳ�����
	 */
	public Table mergeSameCells(Table t, int[] lines, int orientation)
			throws ReportException {
		for (int i = 0; i < lines.length; i++) {
			if (orientation == this.ROW_ORIENTATION) {
				t
						.setRow(lines[i], (TableRow) mergeSameCell(t
								.getRow(lines[i])));
			} else if (orientation == this.COLUMN_ORIENTATION) {
				t.setCol(lines[i], (TableColumn) mergeSameCell(t
						.getCol(lines[i])));
			}
		}
		return t;
	}

	/**
	 * ����һ�������ͷ���󣬺ϲ�������������ͬ�ĵ�Ԫ��
	 * 
	 * @param h
	 *            �����ͷ
	 * @param colLines
	 *            Ҫ�ϲ����е��кš�
	 * @param rowLines
	 *            Ҫ�ϲ����е��кš�
	 */
	public HeaderTable mergeHeaderSameCells(HeaderTable h, int[] colLines,
			int[] rowLines) throws ReportException {
		// �ϲ���
		for (int i = 0; i < colLines.length; i++) {
			h.setCol(colLines[i], (TableColumn) mergeSameCell(h
					.getCol(colLines[i])));
		}
		// �ϲ���
		for (int i = 0; i < rowLines.length; i++) {
			h.setRow(rowLines[i], (TableRow) mergeSameCell(h
					.getRow(rowLines[i])));
		}
		return h;
	}

	/**
	 * ��ò��ظ���Ԫ�ؼ��ϣ�����ָ��˳������
	 * 
	 * @param line
	 *            Ҫ����ļ��ϡ�
	 * @param seq
	 *            ����ָ�������Ԫ�ؼ��ϡ� �磺����D��B��CԪ�ؼ��ϣ�����������������򣬲��ڴ������еĲ�����
	 * @return
	 * @throws ReportException
	 */
	public Set getDistinctSet(TableLine line, Vector seq)
			throws ReportException {
		Comparator comp = new SortComparator(seq);
		Vector contents = new Vector();
		for (int i = 0; i < line.getCellCount(); i++) {
			contents.add(line.getCell(i).getContent());
		}
		// ȥ���ظ�Ԫ��
		TreeSet temp = new TreeSet(contents);
		// ��ָ��˳������
		TreeSet result = new TreeSet(comp);
		result.addAll(temp);
		return result;
	}

	/**
	 * �ڱ��ָ���������в����⼸�еĵ�Ԫ��ƥ����к�
	 * 
	 * @param col
	 *            �к�����
	 * @param val
	 *            Ҫ�Ƚϵ�ֵ����,Ӧ�ú�col�ĳ�����ͬ
	 * @param t
	 *            ��
	 */
	private int searchRow(int[] col, Object[] val, Table t)
			throws ReportException {
		if (col.length != val.length) {
			throw new ReportException("����Ĳ�������.");
		}
		int rt = -1;
		if (t == null || t.getRowCount() <= 0) {
			return rt;
		}

		for (int i = 0; i < t.getRowCount(); i++) {
			boolean tag = true; // ��ÿһ�еĲ���ǰ����Ϊtrue
			for (int j = 0; j < col.length; j++) {
				if (debug) {
					System.out.println(t.getCell(i, col[j]).getContent() + "--"
							+ val[j]);
				}
				if (!ObjectUtils.equals(t.getCell(i, col[j]).getContent(),
						val[j])) {

					tag = false;
					if (debug) {
						System.out.println("�����:" + tag);
					}
					break;
				}
			}
			if (tag == true) { // �������һ�еıȽ��У���ƥ�䣬�򷵻��кš�
				if (debug)
					System.out.println("kk:" + tag);

				return i;
			}
		}
		if (debug) {
			System.out.println("searchRow end-------------");
		}
		return rt;
	}

	/**
	 * ���ɽ������л���ͳ�ơ� <BR>
	 * TODO ������ת��ʽ����֤�㷨��Ӧ��ֱ��ʹ���л����㷨��Ӧ�ñȽ�����������
	 * 
	 * @param t
	 *            ���ݱ�
	 * @param crossTab
	 *            ��������
	 * @param isSubTotal
	 *            �Ƿ�С�ơ�true�����С�ơ�
	 * @param arith
	 *            ͳ���㷨
	 * @return �����ı��
	 * @throws ReportException
	 */
	public Table generateCrossTabColTotal(Table t, CrossTable crossTab,
			boolean isSubTotal, GroupArithmetic arith) throws ReportException {
		int[] cols = new int[t.getRowCount() - crossTab.getColHeader().length];
		for (int i = 0; i < cols.length; i++) {
			cols[i] = i + crossTab.getColHeader().length;
		}
		Table result = CssEngine.applyCss(t);
		result = result.getRotateTable();

		result = generateRowTotal(result, crossTab.getRowHeader().length,
				result.getRowCount(), cols, isSubTotal, arith);
		result = CssEngine.applyCss(result);
		result = result.getRotateTable();
		return result;
	}

	/**
	 * �Խ�������л���ͳ�ơ�
	 * 
	 * @param t
	 *            ���ݱ�
	 * @param crossTab
	 *            �����
	 * @param isSubTotal
	 *            �Ƿ�С�ƣ�true��С��
	 * @param arith
	 *            ͳ���㷨
	 * @return �����ı��
	 * @throws ReportException
	 */
	public Table generateCrossTabRowTotal(Table t, CrossTable crossTab,
			boolean isSubTotal, GroupArithmetic arith) throws ReportException {
		int[] totalCols = new int[t.getColCount()
				- crossTab.getRowHeader().length];
		for (int i = 0; i < totalCols.length; i++) {
			totalCols[i] = i + crossTab.getRowHeader().length;
		}

		Table result = generateRowTotal(t, crossTab.getColHeader().length, t
				.getRowCount(), totalCols, isSubTotal, arith);
		return result;
	}

	/**
	 * �����л���ͳ�ơ�����С�ơ��ܼơ�
	 * 
	 * @param t
	 *            ���ݱ�
	 * @param totalCols
	 *            ͳ�Ƶ���
	 * @param isSubTotal
	 *            �Ƿ�С��
	 * @param arith
	 *            ͳ���㷨
	 * @return ���������ݱ�
	 * @throws ReportException
	 */
	public Table generateRowTotal(Table t, int[] totalCols, boolean isSubTotal,
			GroupArithmetic arith) throws ReportException {
		return generateRowTotal(t, 0, t.getRowCount(), totalCols, isSubTotal,
				arith, null);
	}

	/**
	 * �����л���ͳ�ơ�����С�ơ��ܼơ�
	 * 
	 * @param t
	 *            ���ݱ�
	 * @param totalCols
	 *            ͳ�Ƶ���
	 * @param isSubTotal
	 *            �Ƿ�С��
	 * @param arith
	 *            ͳ���㷨
	 * @param subTitle
	 *            С�Ʊ���
	 * @return ���������ݱ�
	 * @throws ReportException
	 */
	public Table generateRowTotal(Table t, int[] totalCols, boolean isSubTotal,
			GroupArithmetic arith, String subTitle) throws ReportException {
		return generateRowTotal(t, 0, t.getRowCount(), totalCols, isSubTotal,
				arith, subTitle);
	}

	/**
	 * �����л���ͳ�ơ�����С�ơ��ܼơ�
	 * 
	 * @param t
	 *            ���ݱ�
	 * @param fromRow
	 *            Ҫͳ�Ƶ��п�ʼ���кţ��������С�
	 * @param toRow
	 *            Ҫͳ�Ƶ��н������кţ����������С�
	 * @param totalCols
	 *            ͳ�Ƶ���
	 * @param isSubTotal
	 *            �Ƿ�С��
	 * @param arith
	 *            ͳ���㷨
	 * @param subTitle
	 *            С�Ʊ���
	 * @return ���������ݱ�
	 * @throws ReportException
	 */
	private Table generateRowTotal(Table t, int fromRow, int toRow,
			int[] totalCols, boolean isSubTotal, GroupArithmetic arith)
			throws ReportException {
		return generateRowTotal(t, fromRow, toRow, totalCols, isSubTotal,
				arith, null);
	}

	/**
	 * �����л���ͳ�ơ�����С�ơ��ܼơ�
	 * 
	 * @param t
	 *            ���ݱ�
	 * @param fromRow
	 *            Ҫͳ�Ƶ��п�ʼ���кţ��������С�
	 * @param toRow
	 *            Ҫͳ�Ƶ��н������кţ����������С�
	 * @param totalCols
	 *            ͳ�Ƶ���
	 * @param isSubTotal
	 *            �Ƿ�С��
	 * @param arith
	 *            ͳ���㷨
	 * @param subTitle
	 *            С�Ʊ���
	 * @return ���������ݱ�
	 * @throws ReportException
	 */
	private Table generateRowTotal(Table t, int fromRow, int toRow,
			int[] totalCols, boolean isSubTotal, GroupArithmetic arith,
			String subTitle) throws ReportException {
		if (subTitle == null) {
			subTitle = "С��";
		}
		if (t == null || t.getRowCount() == 0) {
			return t;
		}
		Table tempTable = (Table) t.cloneAll(); // ���������ܼ�
		int count = t.getRowCount();
		int tempToRow = toRow; // ��ֹ�кš����ڻᶯ̬�����У���������к���Ҫ��������ġ�

		// ����С��
		if (isSubTotal) {
			int pointer = fromRow; // ɨ����ʱ��ָ��
			int prePointer = pointer; // ָ��ǰ��ĵ�һ��Ԫ�ص�ָ��
			while (pointer <= tempToRow) { // ��������ɨ��
				if (debug)
					System.out.println("p:" + pointer + " pre:" + prePointer
							+ " rowCount:" + t.getRowCount());
				TableColumn tc = t.getCol(0); // ����t�Ƕ�̬������(����Ҫ�����),�������������ѭ����
				if (pointer > 0) {
					// ����������һ��,��õ�һ��
					if (pointer == t.getRowCount()) {
						TableRow insertTR = getTotalRow(t, prePointer, pointer,
								subTitle, totalCols, arith);
						insertTR.setType(Report.GROUP_TOTAL_TYPE);
						t.insertRow(pointer, insertTR);
						if (debug)
							System.out.println("get one group while pre="
									+ prePointer + " and p=" + pointer);
						break; // ����ɨ��
					}
					// �����ǰ��Ԫ������ǰһ��ͬ�еĵ�Ԫ����õ�һ��
					else if (!ObjectUtils.equals(tc.getCell(pointer)
							.getContent(), tc.getCell(prePointer).getContent())) {
						TableRow insertTR = getTotalRow(t, prePointer, pointer,
								subTitle, totalCols, arith);
						insertTR.setType(Report.GROUP_TOTAL_TYPE);
						t.insertRow(pointer, insertTR);

						if (debug)
							System.out.println("++"
									+ "get one group while pre=" + prePointer
									+ " and p=" + pointer);
						// ׼����ʼ��һ��ɨ�衣����ǰ�������һ�У�����Ҫ������һ���ļ�¼
						tempToRow++; // �Ѿ������У����Խ�ֹ�к�ҲҪ��1
						pointer++;
						prePointer = pointer;
					}
				}
				pointer++;
			}
		}

		// �����ܼ�
		TableRow insertTR = getTotalRow(tempTable, fromRow, toRow, "�ܼ�",
				totalCols, arith);
		insertTR.setType(Report.TOTAL_TYPE);
		t.addRow(insertTR);
		return t;
	}

	/**
	 * ���ɽ����
	 * 
	 * @param srcTab
	 *            ԭʼ���ݱ�
	 * @param crossTab
	 *            ����������
	 * @return �����
	 */
	public Table generateCrossTab(Table srcTab, CrossTable crossTab)
			throws ReportException {
		Table result = new Table();
		// ���ɽ�������ͷ����ͷ
		// ���distinct����ͷ,����ж����ͷ�������ɵϿ�����
		Vector tempRH = new Vector(); // ������ͷ��Ϣ
		for (int i = 0; i < crossTab.getRowHeader().length; i++) {
			tempRH.add(getDistinctSet(srcTab.getCol(crossTab.getRowHeader()[i]
					.getIndex()), crossTab.getRowHeader()[i].getSortSeq()));
		}
		// ���distinct����ͷ
		Vector tempCH = new Vector(); // ������ͷ��Ϣ
		for (int i = 0; i < crossTab.getColHeader().length; i++) {
			tempCH.add(getDistinctSet(srcTab.getCol(crossTab.getColHeader()[i]
					.getIndex()), crossTab.getColHeader()[i].getSortSeq()));
		}

		TableLine[] rowHead = getHeadForCross(tempRH, TableColumn.class);
		TableLine[] colHead = getHeadForCross(tempCH, TableRow.class);

		// ����ͷ��
		for (int i = 0; i < rowHead.length; i++) {
			result.addCol((TableColumn) rowHead[i]);
		}

		// ����հ��С���������
		for (int i = 0; i < ((TableLine) colHead[0]).getCellCount(); i++) {
			result.addCol(new TableColumn(rowHead[0].getCellCount()));
		}

		// �ټ���ͷ��(Ҫ�Ӻ���ǰ��,���ܻ����ȷ��˳��)
		for (int i = colHead.length - 1; i >= 0; i--) {
			TableRow tempTR = (TableRow) colHead[i];
			for (int j = 0; j < rowHead.length; j++) {
				TableCell cell = new TableCell("");
				tempTR.insertCell(cell, 0);
			}
			result.insertRow(0, tempTR);
		}

		// ����ͷ��ͷ���ɽ��沿������
		for (int i = colHead.length; i < result.getRowCount(); i++) {
			for (int j = rowHead.length; j < result.getColCount(); j++) {
				result.getCell(i, j).setContent(
						getCrossValByHead(srcTab, result, crossTab, i, j));
			}
		}
		// ������ͷ����ͷ��˵�����֡�
		for (int i = 0; i < colHead.length; i++) {
			for (int j = 0; j < rowHead.length; j++) {
				if (!(i == 0 && j == 0)) {
					result.getCell(i, j).setIsHidden(true);
				}
			}
		}
		int len = crossTab.getColHeader().length
				+ crossTab.getRowHeader().length;

		// Table headHeadTable = new Table(len, len);//�����ͷ�ı�ͷ
		// headHeadTable.setType(Report.CROSS_HEAD_HEAD_TYPE);
		// headHeadTable.setBorder(0);
		// headHeadTable.setWidth(100);
		// headHeadTable.setHeight(100);
		// headHeadTable.setCellpadding(0);
		// headHeadTable.setCellspacing(0);
		// HeadCol[] hhVal = new HeadCol[len];
		// System.arraycopy(crossTab.getColHeader(), 0, hhVal, 0,
		// crossTab.getColHeader().length);
		// System.arraycopy(crossTab.getRowHeader(), 0, hhVal,
		// crossTab.getColHeader().length,
		// crossTab.getRowHeader().length);
		// for (int i = 0; i < len; i++) {
		// headHeadTable.getCell(i, len - 1 -
		// i).setContent(hhVal[i].getHeaderText());
		// }
		TableCell headHeadCell = result.getCell(0, 0);
		headHeadCell.setColSpan(rowHead.length);
		headHeadCell.setRowSpan(colHead.length);
		headHeadCell.setCssStyle("margin: 0px;padding: 0px;");
		headHeadCell.setCssClass(Report.CROSS_HEAD_HEAD_TYPE);
		headHeadCell.setContent(crossTab);

		// �ڱ�������Ϻ󣬿��Խ����С��л��ܡ�

		return result;
	}

	/**
	 * �ҳ�������������,������������Ҫȡ��ֵ�����������า�ǣ���ʹ�ñ�Ĳ����㷨��
	 * 
	 * @param t
	 *            ԭʼ���ݱ�
	 * @param cols
	 *            �е����
	 * @param cmpVals
	 *            ���бȽϵ�ֵ��Ӧ�ø�cols��Сһ����1
	 * @param valueCol
	 *            Ҫȡ�õ�ֵ���к�.
	 * @return
	 */
	private double[] findRows(Table t, int[] cols, String[] cmpVals,
			int valueCol) throws ReportException {
		if (cols == null || cmpVals == null) {
			throw new ReportException("cols��cmpValuesΪ�ա�");
		}
		if (cols.length != cmpVals.length) {
			throw new ReportException("cols��cmpVals�Ĵ�СӦ��һ�¡�");
		}
		Vector tmpResult = new Vector();
		for (int i = 0; i < t.getRowCount(); i++) {
			boolean pass = true;
			for (int j = 0; j < cols.length; j++) {
				if (!ObjectUtils.equals(t.getCell(i, cols[j]).getContent(),
						cmpVals[j])) {
					// Ĭ��Ϊͨ����ִ��һƱ�����
					pass = false;
					break;
				}
			}
			if (pass) {
				tmpResult.add(t.getRow(i).getCell(valueCol).getContent());
			}
		}
		double[] result = new double[tmpResult.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = Double.parseDouble((String) tmpResult.elementAt(i));
		}

		return result;
	}

	/**
	 * ���Ŀ�����ĳ��ĳ�еĽ���ֵ��
	 * 
	 * @param srcTab
	 *            ���ݱ�
	 * @param destTab
	 *            Ŀ�����һ�����ݽ����
	 * @param crossTab
	 *            ����������
	 * @param row
	 *            �к�
	 * @param col
	 *            �к�
	 * @return �к��кŵĽ���ֵ
	 * @throws ReportException
	 */
	private String getCrossValByHead(Table srcTab, Table destTab,
			CrossTable crossTab, int row, int col) throws ReportException {
		// ȷ����������
		HeadCol[] headCols = crossTab.getColHeader();
		HeadCol[] headRows = crossTab.getRowHeader();

		String[] cmpVals = new String[headCols.length + headRows.length];
		int[] cols = new int[headCols.length + headRows.length];

		for (int i = 0; i < headCols.length; i++) {
			cols[i] = headCols[i].getIndex();
			cmpVals[i] = (String) destTab.getCell(i, col).getContent();

		}
		for (int i = 0; i < headRows.length; i++) {
			cols[i + headCols.length] = headRows[i].getIndex();

			cmpVals[i + headCols.length] = (String) destTab.getCell(row, i)
					.getContent();

		}

		// �ҳ��������������е�Ԫ
		double[] values = findRows(srcTab, cols, cmpVals, crossTab
				.getCrossCol().getIndex());

		return crossTab.getCrossCol().getArith().getResult(values);

	}

	/**
	 * Ϊ�����������ͷ����ͷ��
	 * 
	 * @param orglLine
	 *            ԭʼ�������С�����������У�ÿ�����ж���һ�����ظ�ֵ�ļ��ϡ�
	 * @param cls
	 *            ָ������ֵ�����е����͡�
	 * @return ��ͷ����ͷ��
	 * @throws ReportException
	 */
	private TableLine[] getHeadForCross(Vector orglLine, Class cls)
			throws ReportException {
		TableLine[] lines = new TableLine[orglLine.size()];
		try {
			for (int i = 0; i < orglLine.size(); i++) {

				int span = 1; // ��Ԫ��Ŀ�Խ��
				// ��Խ�����ڱ����к���������а�����Ԫ���ĳ˻�
				for (int j = i + 1; j < orglLine.size(); j++) {
					span *= ((Set) orglLine.elementAt(j)).size();
				}

				int repeat = 1; // �����ظ��Ĵ�����
				// �ظ��������ڱ�����֮ǰ�������а�����Ԫ���ĳ˻�
				for (int j = 0; j < i; j++) {
					repeat *= ((Set) orglLine.elementAt(j)).size();
				}

				lines[i] = (TableLine) cls.newInstance();

				// ���ظ�������ظ�����
				for (int j = 0; j < repeat; j++) {
					Iterator itr = ((Set) orglLine.elementAt(i)).iterator();
					while (itr.hasNext()) {
						String value = (String) itr.next();
						TableCell cell = new TableCell(value);
						lines[i].setSpan(cell, span);
						lines[i].addCell(cell);
						// �������ص�cell������cell�ĸ�����
						for (int k = 0; k < span - 1; k++) {
							TableCell hiddenCell = new TableCell(value);
							hiddenCell.setIsHidden(true);
							lines[i].addCell(hiddenCell);
						} // for
					} // while
				} // for
				lines[i].setType(Report.HEAD_TYPE); // һ��Ҫ�������е�Ԫ�������������

				// ���ڽ�����з�ת�ķ���������Ҫ����ʽ������Ԫ
				for (int k = 0; k < lines[i].getCellCount(); k++) {
					lines[i].getCell(k).setCssClass(lines[i].getType());
				}

				if (debug)
					System.out.println("count:" + lines[i].getCellCount()
							+ " repeat:" + repeat + " span:" + span + " size:"
							+ ((Set) orglLine.elementAt(i)).size());
			} // for
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ReportException(ex.toString());
		}
		return lines;
	}
}