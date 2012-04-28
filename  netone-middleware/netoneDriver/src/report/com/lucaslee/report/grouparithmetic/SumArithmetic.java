package com.lucaslee.report.grouparithmetic;

/**
 * 求和算法。
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
public class SumArithmetic implements GroupArithmetic {
	public SumArithmetic() {
	}

	/**
	 * 参考父类文档。
	 * 
	 * @param values
	 * @return
	 */
	public String getResult(double[] values) {
		double result = 0.0;
		for (int i = 0; i < values.length; i++) {
			result += values[i];
		}
		return Double.toString(result);
	}
}