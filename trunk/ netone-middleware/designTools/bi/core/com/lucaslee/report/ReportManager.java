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
 * 报表管理器。主要的报表操作都在此类中进行。
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

	// 为true则打印调试信息
	private boolean debug = false;

	/** 行方向,即行延伸的方向，水平向右。 */
	public static final int ROW_ORIENTATION = 1;

	/** 列方向，即列延伸的方向，垂直向下 */
	public static final int COLUMN_ORIENTATION = 2;

	/**
	 * 检测一个数是否在数组中。
	 * 
	 * @param a
	 *            要检测的数
	 * @param col
	 *            数组
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
	 * 获得一个汇总行.
	 * 
	 * @param t
	 *            表格对象
	 * @param from
	 *            起始行,包括此行
	 * @param end
	 *            终止行,不包括此行
	 * @param memo
	 *            汇总行的注释
	 * @param totalCols
	 *            进行汇总列号数组
	 * @param arith
	 *            统计算法
	 */
	private TableRow getTotalRow(Table t, int from, int end, String memo,
			int[] totalCols, GroupArithmetic arith) throws ReportException {
		TableRow totalRow = new TableRow();

		int[] tempTotalCols = (int[]) totalCols.clone(); // 克隆到新变量是为了不改变入口参数
		Arrays.sort(tempTotalCols);
		int nonTotal = 0; // 指明头几个不需要汇总的列，值为这其中最后一个列数。
		if (tempTotalCols.length > 0) {
			nonTotal = tempTotalCols[0] - 1;
		}

		// 添加注释单元
		TableCell desc = new TableCell(memo);
		desc.setColSpan(nonTotal + 1);
		totalRow.addCell(desc);

		// 这几个单元是为了符合表格标准（每行的cell数一致）,使每行的单元数相等,不显示出来
		for (int i = 0; i < nonTotal; i++) {
			TableCell nullCell = new TableCell("");
			nullCell.setIsHidden(true);
			totalRow.addCell(nullCell);
		}

		for (int k = 0; k < t.getColCount(); k++) { // 遍历列
			if (k > nonTotal) { // "小计"列不用添加了
				TableCell c = new TableCell("");

				if (isAmonge(k, totalCols)) { // 指定的统计列才进行汇总,非统计列是一个空字符串

					double[] values = new double[end - from]; // 记录要汇总的一个序列的数据
					for (int j = from; j < end; j++) { // 从上至下扫描
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
	 * 用父级列形成的表格线分割子列。 一个表可能有多列需要进行同值合并，此方法使前面的列总能完全包容后面的列.
	 * 即从逻辑上说，若此表表示一个分类表，前面的列总是后面的父级分类。 实现的操作是，用父级列形成的表格线分割子列。
	 * 
	 * @param t
	 *            表格
	 * @param cols
	 *            要进行合并的列,应该是连续的
	 */
	public Table split(Table t, int[] cols) throws ReportException {
		for (int i = 1; i < cols.length; i++) { // 从左至右扫描
			TableColumn pre = t.getCol(i - 1);
			TableColumn curr = t.getCol(i);
			for (int j = 0; j < pre.getCellCount(); j++) { // 从上至下扫描
				if (pre.getCell(j).getIsHidden() == false) {
					if (curr.getCell(j).getIsHidden() == false || j == 0) {
						continue;
					}
					for (int k = j - 1; k >= 0; k--) { // 从下至上扫描
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
	 * 格式化交叉表的数据。
	 * 
	 * @param t
	 *            交叉数据表
	 * @param crossTab
	 *            交叉表定义
	 * @param f
	 *            格式化对象
	 * @return 处理后的数据表
	 * @throws ReportException
	 */
	public Table formatData(Table t, CrossTable crossTab, Formatter f)
			throws ReportException {
		return formatData(t, crossTab.getColHeader().length, t.getRowCount(),
				crossTab.getRowHeader().length, t.getColCount(), f);
	}

	/**
	 * 格式化数据.
	 * 
	 * @param t
	 *            要处理的表
	 * @param fromRow
	 *            开始行,包含此行
	 * @param toRow
	 *            终止行,不包含此行
	 * @param fromCol
	 *            开始列,包含此列
	 * @param toCol
	 *            终止列,不包含此列
	 * @param f
	 *            格式化对象
	 * @return 处理完的表
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
					// 忽略并继续
				} catch (ParseException e) {
					// 忽略并继续
				}
			} // for
		}
		return t;

	}

	/**
	 * 格式化表格数据.
	 * 
	 * @param t
	 *            表格
	 * @param cols
	 *            在此数组里的列才进行格式化
	 * @param f
	 *            格式化对象
	 */
	public Table formatData(Table t, int[] cols, Formatter f)
			throws ReportException {
		for (int i = 0; i < cols.length; i++) {
			formatData(t, 0, t.getRowCount(), cols[i], cols[i] + 1, f);
		}
		return t;
	}

	/**
	 * 操作一个行或列，合并相邻且内容相同的单元。
	 * 
	 * @param line
	 *            要操作的列对象。
	 * @return
	 */
	public TableLine mergeSameCell(TableLine line) throws ReportException {

		String curr = null; // 当前单元内容
		String pre = null; // 上一单元内容
		int pointer = 0; // 位置指针，指向相邻同值单元组的第一个单元
		int count = 1; // 相邻同值单元组内单元数
		for (int i = 0; i < line.getCellCount(); i++) { // 从上至下扫描
			curr = (String) line.getCell(i).getContent();
			if (i > 0) {
				if ((curr != null && ObjectUtils.equals(curr, pre))
						|| (curr == null && pre == null)) { // 若当前单元与上一个单元值相等，
					count++;
					// 则让第一个同值单元的span值加1;
					line.setSpan(line.getCell(pointer), count);
					line.getCell(i).setIsHidden(true); // 且不显示本单元。
				} else { // 若不相等
					pointer = i; // 则指针指向本单元
					count = 1; // 且清空计数器
				}
			}
			pre = curr;
		}
		return line;
	}

	/**
	 * 操作一个表格对象，合并相邻且内容相同的单元。
	 * 
	 * @param t
	 *            表格
	 * @param lines
	 *            要合并的列的列号。
	 * @param orientation
	 *            操作的行或列方向。接受本类中后缀为_ORIENTATION的常数。
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
	 * 操作一个表的列头对象，合并相邻且内容相同的单元。
	 * 
	 * @param h
	 *            表的列头
	 * @param colLines
	 *            要合并的列的列号。
	 * @param rowLines
	 *            要合并的列的列号。
	 */
	public HeaderTable mergeHeaderSameCells(HeaderTable h, int[] colLines,
			int[] rowLines) throws ReportException {
		// 合并列
		for (int i = 0; i < colLines.length; i++) {
			h.setCol(colLines[i], (TableColumn) mergeSameCell(h
					.getCol(colLines[i])));
		}
		// 合并行
		for (int i = 0; i < rowLines.length; i++) {
			h.setRow(rowLines[i], (TableRow) mergeSameCell(h
					.getRow(rowLines[i])));
		}
		return h;
	}

	/**
	 * 获得不重复的元素集合，并按指定顺序排序。
	 * 
	 * @param line
	 *            要处理的集合。
	 * @param seq
	 *            用于指定排序的元素集合。 如：给出D，B，C元素集合，则结果按这个序列排序，不在此序列中的不排序。
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
		// 去除重复元素
		TreeSet temp = new TreeSet(contents);
		// 按指定顺序排序
		TreeSet result = new TreeSet(comp);
		result.addAll(temp);
		return result;
	}

	/**
	 * 在表的指定若干列中查找这几列的单元都匹配的行号
	 * 
	 * @param col
	 *            列号数组
	 * @param val
	 *            要比较的值数组,应该和col的长度相同
	 * @param t
	 *            表
	 */
	private int searchRow(int[] col, Object[] val, Table t)
			throws ReportException {
		if (col.length != val.length) {
			throw new ReportException("输入的参数有误.");
		}
		int rt = -1;
		if (t == null || t.getRowCount() <= 0) {
			return rt;
		}

		for (int i = 0; i < t.getRowCount(); i++) {
			boolean tag = true; // 在每一行的查找前，置为true
			for (int j = 0; j < col.length; j++) {
				if (debug) {
					System.out.println(t.getCell(i, col[j]).getContent() + "--"
							+ val[j]);
				}
				if (!ObjectUtils.equals(t.getCell(i, col[j]).getContent(),
						val[j])) {

					tag = false;
					if (debug) {
						System.out.println("不相等:" + tag);
					}
					break;
				}
			}
			if (tag == true) { // 如果在这一行的比较中，都匹配，则返回行号。
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
	 * 生成交叉表的列汇总统计。 <BR>
	 * TODO 仅用旋转方式来验证算法，应该直接使用列汇总算法，应该比较容易做到。
	 * 
	 * @param t
	 *            数据表
	 * @param crossTab
	 *            交叉表对象
	 * @param isSubTotal
	 *            是否小计。true则进行小计。
	 * @param arith
	 *            统计算法
	 * @return 处理后的表格
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
	 * 对交叉表做行汇总统计。
	 * 
	 * @param t
	 *            数据表
	 * @param crossTab
	 *            交叉表
	 * @param isSubTotal
	 *            是否小计，true则小计
	 * @param arith
	 *            统计算法
	 * @return 处理后的表格
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
	 * 生成行汇总统计。包括小计、总计。
	 * 
	 * @param t
	 *            数据表
	 * @param totalCols
	 *            统计的列
	 * @param isSubTotal
	 *            是否小计
	 * @param arith
	 *            统计算法
	 * @return 处理后的数据表
	 * @throws ReportException
	 */
	public Table generateRowTotal(Table t, int[] totalCols, boolean isSubTotal,
			GroupArithmetic arith) throws ReportException {
		return generateRowTotal(t, 0, t.getRowCount(), totalCols, isSubTotal,
				arith, null);
	}

	/**
	 * 生成行汇总统计。包括小计、总计。
	 * 
	 * @param t
	 *            数据表
	 * @param totalCols
	 *            统计的列
	 * @param isSubTotal
	 *            是否小计
	 * @param arith
	 *            统计算法
	 * @param subTitle
	 *            小计标题
	 * @return 处理后的数据表
	 * @throws ReportException
	 */
	public Table generateRowTotal(Table t, int[] totalCols, boolean isSubTotal,
			GroupArithmetic arith, String subTitle) throws ReportException {
		return generateRowTotal(t, 0, t.getRowCount(), totalCols, isSubTotal,
				arith, subTitle);
	}

	/**
	 * 生成行汇总统计。包括小计、总计。
	 * 
	 * @param t
	 *            数据表
	 * @param fromRow
	 *            要统计的行开始的行号，包括此行。
	 * @param toRow
	 *            要统计的行结束的行号，不包括此行。
	 * @param totalCols
	 *            统计的列
	 * @param isSubTotal
	 *            是否小计
	 * @param arith
	 *            统计算法
	 * @param subTitle
	 *            小计标题
	 * @return 处理后的数据表
	 * @throws ReportException
	 */
	private Table generateRowTotal(Table t, int fromRow, int toRow,
			int[] totalCols, boolean isSubTotal, GroupArithmetic arith)
			throws ReportException {
		return generateRowTotal(t, fromRow, toRow, totalCols, isSubTotal,
				arith, null);
	}

	/**
	 * 生成行汇总统计。包括小计、总计。
	 * 
	 * @param t
	 *            数据表
	 * @param fromRow
	 *            要统计的行开始的行号，包括此行。
	 * @param toRow
	 *            要统计的行结束的行号，不包括此行。
	 * @param totalCols
	 *            统计的列
	 * @param isSubTotal
	 *            是否小计
	 * @param arith
	 *            统计算法
	 * @param subTitle
	 *            小计标题
	 * @return 处理后的数据表
	 * @throws ReportException
	 */
	private Table generateRowTotal(Table t, int fromRow, int toRow,
			int[] totalCols, boolean isSubTotal, GroupArithmetic arith,
			String subTitle) throws ReportException {
		if (subTitle == null) {
			subTitle = "小计";
		}
		if (t == null || t.getRowCount() == 0) {
			return t;
		}
		Table tempTable = (Table) t.cloneAll(); // 用来生成总计
		int count = t.getRowCount();
		int tempToRow = toRow; // 截止行号。由于会动态插入行，所以这个行号是要不断增大的。

		// 生成小计
		if (isSubTotal) {
			int pointer = fromRow; // 扫描行时的指针
			int prePointer = pointer; // 指向当前组的第一个元素的指针
			while (pointer <= tempToRow) { // 从上至下扫描
				if (debug)
					System.out.println("p:" + pointer + " pre:" + prePointer
							+ " rowCount:" + t.getRowCount());
				TableColumn tc = t.getCol(0); // 由于t是动态增长的(后面要添加行),所以这个语句放在循环里
				if (pointer > 0) {
					// 如果到了最后一行,则得到一组
					if (pointer == t.getRowCount()) {
						TableRow insertTR = getTotalRow(t, prePointer, pointer,
								subTitle, totalCols, arith);
						insertTR.setType(Report.GROUP_TOTAL_TYPE);
						t.insertRow(pointer, insertTR);
						if (debug)
							System.out.println("get one group while pre="
									+ prePointer + " and p=" + pointer);
						break; // 结束扫描
					}
					// 如果当前单元不等于前一行同列的单元，则得到一组
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
						// 准备开始新一轮扫描。由于前面插入了一行，所以要跳到下一条的记录
						tempToRow++; // 已经加了行，所以截止行号也要加1
						pointer++;
						prePointer = pointer;
					}
				}
				pointer++;
			}
		}

		// 生成总计
		TableRow insertTR = getTotalRow(tempTable, fromRow, toRow, "总计",
				totalCols, arith);
		insertTR.setType(Report.TOTAL_TYPE);
		t.addRow(insertTR);
		return t;
	}

	/**
	 * 生成交叉表。
	 * 
	 * @param srcTab
	 *            原始数据表
	 * @param crossTab
	 *            交叉表定义对象。
	 * @return 结果表
	 */
	public Table generateCrossTab(Table srcTab, CrossTable crossTab)
			throws ReportException {
		Table result = new Table();
		// 生成交叉表的行头和列头
		// 获得distinct的行头,如果有多个行头，则生成迪卡尔积
		Vector tempRH = new Vector(); // 保存行头信息
		for (int i = 0; i < crossTab.getRowHeader().length; i++) {
			tempRH.add(getDistinctSet(srcTab.getCol(crossTab.getRowHeader()[i]
					.getIndex()), crossTab.getRowHeader()[i].getSortSeq()));
		}
		// 获得distinct的列头
		Vector tempCH = new Vector(); // 保存列头信息
		for (int i = 0; i < crossTab.getColHeader().length; i++) {
			tempCH.add(getDistinctSet(srcTab.getCol(crossTab.getColHeader()[i]
					.getIndex()), crossTab.getColHeader()[i].getSortSeq()));
		}

		TableLine[] rowHead = getHeadForCross(tempRH, TableColumn.class);
		TableLine[] colHead = getHeadForCross(tempCH, TableRow.class);

		// 加行头；
		for (int i = 0; i < rowHead.length; i++) {
			result.addCol((TableColumn) rowHead[i]);
		}

		// 补齐空白列、数据区域；
		for (int i = 0; i < ((TableLine) colHead[0]).getCellCount(); i++) {
			result.addCol(new TableColumn(rowHead[0].getCellCount()));
		}

		// 再加列头；(要从后往前加,才能获得正确的顺序)
		for (int i = colHead.length - 1; i >= 0; i--) {
			TableRow tempTR = (TableRow) colHead[i];
			for (int j = 0; j < rowHead.length; j++) {
				TableCell cell = new TableCell("");
				tempTR.insertCell(cell, 0);
			}
			result.insertRow(0, tempTR);
		}

		// 按行头列头生成交叉部分数据
		for (int i = colHead.length; i < result.getRowCount(); i++) {
			for (int j = rowHead.length; j < result.getColCount(); j++) {
				result.getCell(i, j).setContent(
						getCrossValByHead(srcTab, result, crossTab, i, j));
			}
		}
		// 设置行头和列头的说明文字。
		for (int i = 0; i < colHead.length; i++) {
			for (int j = 0; j < rowHead.length; j++) {
				if (!(i == 0 && j == 0)) {
					result.getCell(i, j).setIsHidden(true);
				}
			}
		}
		int len = crossTab.getColHeader().length
				+ crossTab.getRowHeader().length;

		// Table headHeadTable = new Table(len, len);//交叉表头的表头
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

		// 在本方法完毕后，可以进行行、列汇总。

		return result;
	}

	/**
	 * 找出符合条件的行,返回所有行中要取的值。可以在子类覆盖，以使用别的查找算法。
	 * 
	 * @param t
	 *            原始数据表
	 * @param cols
	 *            列的序号
	 * @param cmpVals
	 *            进行比较的值，应该跟cols大小一样。1
	 * @param valueCol
	 *            要取得的值的列号.
	 * @return
	 */
	private double[] findRows(Table t, int[] cols, String[] cmpVals,
			int valueCol) throws ReportException {
		if (cols == null || cmpVals == null) {
			throw new ReportException("cols或cmpValues为空。");
		}
		if (cols.length != cmpVals.length) {
			throw new ReportException("cols和cmpVals的大小应该一致。");
		}
		Vector tmpResult = new Vector();
		for (int i = 0; i < t.getRowCount(); i++) {
			boolean pass = true;
			for (int j = 0; j < cols.length; j++) {
				if (!ObjectUtils.equals(t.getCell(i, cols[j]).getContent(),
						cmpVals[j])) {
					// 默认为通过，执行一票否决制
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
	 * 获得目标表中某行某列的交叉值。
	 * 
	 * @param srcTab
	 *            数据表
	 * @param destTab
	 *            目标表，是一个数据交叉表。
	 * @param crossTab
	 *            交叉表定义对象
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @return 行号列号的交叉值
	 * @throws ReportException
	 */
	private String getCrossValByHead(Table srcTab, Table destTab,
			CrossTable crossTab, int row, int col) throws ReportException {
		// 确定查找条件
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

		// 找出符合条件的所有单元
		double[] values = findRows(srcTab, cols, cmpVals, crossTab
				.getCrossCol().getIndex());

		return crossTab.getCrossCol().getArith().getResult(values);

	}

	/**
	 * 为交叉表生成行头或列头。
	 * 
	 * @param orglLine
	 *            原始数据序列。包含多个序列，每个序列都是一个不重复值的集合。
	 * @param cls
	 *            指定返回值数组中的类型。
	 * @return 行头或列头。
	 * @throws ReportException
	 */
	private TableLine[] getHeadForCross(Vector orglLine, Class cls)
			throws ReportException {
		TableLine[] lines = new TableLine[orglLine.size()];
		try {
			for (int i = 0; i < orglLine.size(); i++) {

				int span = 1; // 单元格的跨越数
				// 跨越数等于本序列后的所有序列包含单元数的乘积
				for (int j = i + 1; j < orglLine.size(); j++) {
					span *= ((Set) orglLine.elementAt(j)).size();
				}

				int repeat = 1; // 序列重复的次数。
				// 重复次数等于本序列之前所有序列包含单元数的乘积
				for (int j = 0; j < i; j++) {
					repeat *= ((Set) orglLine.elementAt(j)).size();
				}

				lines[i] = (TableLine) cls.newInstance();

				// 按重复数添加重复序列
				for (int j = 0; j < repeat; j++) {
					Iterator itr = ((Set) orglLine.elementAt(i)).iterator();
					while (itr.hasNext()) {
						String value = (String) itr.next();
						TableCell cell = new TableCell(value);
						lines[i].setSpan(cell, span);
						lines[i].addCell(cell);
						// 创建隐藏的cell，补足cell的个数。
						for (int k = 0; k < span - 1; k++) {
							TableCell hiddenCell = new TableCell(value);
							hiddenCell.setIsHidden(true);
							lines[i].addCell(hiddenCell);
						} // for
					} // while
				} // for
				lines[i].setType(Report.HEAD_TYPE); // 一定要放在所有单元都加入后再设置

				// 由于交叉表有翻转的方法，所以要将样式赋给单元
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