package com.lucaslee.report.grouparithmetic;

/**
 * ��ƽ��ֵ�㷨��
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
public class AverageArithmetic implements GroupArithmetic {
	public AverageArithmetic() {
	}

	/**
	 * �ο������ĵ���
	 * 
	 * @param values
	 * @return
	 */
	public String getResult(double[] values) {
		double result = 0.0;

		for (int i = 0; i < values.length; i++) {
			result += values[i];
		}
		return Double.toString(result / values.length);
	}

}