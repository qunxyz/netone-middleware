package com.lucaslee.report.grouparithmetic;

/**
 * ���㼯�ϵ���ֵ������
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
public class CountArithmetic implements GroupArithmetic {
	public CountArithmetic() {
	}

	/**
	 * �ο������ĵ���
	 * 
	 * @param values
	 * @return
	 */
	public String getResult(double[] values) {
		return Integer.toString(values.length);
	}

}