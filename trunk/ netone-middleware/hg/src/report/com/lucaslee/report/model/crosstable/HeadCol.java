package com.lucaslee.report.model.crosstable;

import java.util.Vector;

/**
 * ��������ͷ����ͷ��
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
public class HeadCol {
	public HeadCol() {
	}

	/**
	 * 
	 * @param index
	 *            �����ͷ��ԭʼ���ݱ���е������
	 * @param headerText
	 *            ˵������
	 */
	public HeadCol(int index, String headerText) {
		this.index = index;
		this.headerText = headerText;
	}

	/**
	 * 
	 * @param index
	 *            �����ͷ��ԭʼ���ݱ���е������
	 * @param headerText
	 *            ˵������
	 * @param sortSeq
	 *            ����ִ�������ֵ����
	 */
	public HeadCol(int index, String headerText, Vector sortSeq) {
		this.index = index;
		this.headerText = headerText;
		this.sortSeq = sortSeq;
	}

	/** �����ͷ��ԭʼ���ݱ���е������ */
	private int index;

	/**
	 * ˵�����֡�
	 */
	private String headerText;

	/**
	 * ����ִ�������ֵ����.
	 */
	private Vector sortSeq;

	/**
	 * ��ý����ͷԭʼ��������е�����š�
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * ���ý����ͷԭʼ��������е�����š�
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * �������ִ�������ֵ����
	 * 
	 * @return
	 */
	public Vector getSortSeq() {
		return sortSeq;
	}

	/**
	 * ��������ִ�������ֵ����
	 * 
	 * @param sortSeq
	 */
	public void setSortSeq(Vector sortSeq) {
		this.sortSeq = sortSeq;
	}

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

}