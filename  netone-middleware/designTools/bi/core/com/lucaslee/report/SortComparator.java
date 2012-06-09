package com.lucaslee.report;

import java.util.Comparator;
import java.util.Vector;

/**
 * 排序比较器.按照指定的序列排序.
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
public class SortComparator implements Comparator {
	/** 按照此序列中的顺序排序. */
	private Vector sequence;

	/**
	 * @param seq
	 *            排序序列。
	 */
	public SortComparator(Vector seq) {
		sequence = seq;
	}

	/**
	 * 获得对象在序列中的序号。
	 * 
	 * @param o
	 *            对象
	 * @return 序号
	 */
	private int getIndex(Object o) {
		if (sequence != null) {
			for (int i = 0; i < sequence.size(); i++) {
				if (o.equals(sequence.elementAt(i))) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 比较两个对象的大小。 保证：在序列中的比不在序列中的小；两个都在序列中的，排在前面的小。 参考父类文档。
	 * 
	 * @param o1
	 *            要比较的对象1
	 * @param o2
	 *            要比较的对象2
	 * @return
	 */
	public int compare(Object o1, Object o2) {
		int ind1 = getIndex(o1);
		int ind2 = getIndex(o2);
		int result = 0;
		if (ind1 >= 0) {
			if (ind2 >= 0) {
				if (ind1 < ind2) {
					result = -1;
				} else if (ind1 == ind2) {
					result = 0;
				} else {// 即：(ind1 > ind2)
					result = 1;
				}
			} else {
				result = -1;
			}
		} else {
			result = 1;
		}
		return result;
	}

}