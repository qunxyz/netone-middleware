package com.lucaslee.report;

import java.util.Comparator;
import java.util.Vector;

/**
 * ����Ƚ���.����ָ������������.
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
	/** ���մ������е�˳������. */
	private Vector sequence;

	/**
	 * @param seq
	 *            �������С�
	 */
	public SortComparator(Vector seq) {
		sequence = seq;
	}

	/**
	 * ��ö����������е���š�
	 * 
	 * @param o
	 *            ����
	 * @return ���
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
	 * �Ƚ���������Ĵ�С�� ��֤���������еıȲ��������е�С���������������еģ�����ǰ���С�� �ο������ĵ���
	 * 
	 * @param o1
	 *            Ҫ�ȽϵĶ���1
	 * @param o2
	 *            Ҫ�ȽϵĶ���2
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
				} else {// ����(ind1 > ind2)
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