package com.lucaslee.report.model.crosstable;

import com.lucaslee.report.grouparithmetic.GroupArithmetic;

/**
 * ������еĽ��沿�֡�
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
public class CrossCol {
	public CrossCol() {
	}

	/**
	 * 
	 * @param index
	 *            ���沿����ԭʼ��������е������
	 * @param headerText
	 *            ˵������
	 * @param arith
	 *            ���沿�ֵĻ����㷨
	 */
	public CrossCol(int index, String headerText, GroupArithmetic arith) {
		this.index = index;
		this.headerText = headerText;
		this.arith = arith;
	}

	/**
	 * ���沿����ԭʼ��������е�����š�
	 */
	private int index;

	/**
	 * �����㷨��
	 */
	private GroupArithmetic arith;

	/**
	 * ˵�����֡�
	 */
	private String headerText;

	/**
	 * ���˵������
	 * 
	 * @return
	 */
	public String getHeaderText() {
		return headerText;
	}

	/**
	 * ����˵������
	 * 
	 * @param headerText
	 */
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	/**
	 * ��ý��沿����ԭʼ��������е�����š�
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * ��ý��沿�ֵĻ����㷨��
	 * 
	 * @return
	 */
	public GroupArithmetic getArith() {
		return arith;
	}

	/**
	 * ���ý��沿����ԭʼ��������е�����š�
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * ���ý��沿�ֵĻ����㷨��
	 * 
	 * @param arith
	 */
	public void setArith(GroupArithmetic arith) {
		this.arith = arith;
	}

}