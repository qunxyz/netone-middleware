package com.lucaslee.report.grouparithmetic;

/**
 * 计算集合的数值个数。
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
	 * 参考父类文档。
	 * 
	 * @param values
	 * @return
	 */
	public String getResult(double[] values) {
		return Integer.toString(values.length);
	}

}