package com.lucaslee.report.grouparithmetic;

import java.util.Arrays;

import com.lucaslee.report.ReportException;

/**
 * 取最小值。
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
public class MinArithmetic implements GroupArithmetic {
	public MinArithmetic() {
	}

	/**
	 * 参考父类文档。
	 * 
	 * @param values
	 * @return
	 * @throws ReportException
	 */
	public String getResult(double[] values) throws ReportException {
		if (values == null) {
			throw new ReportException("values can not be null.");
		}
		double[] temp = (double[]) values.clone();
		Arrays.sort(temp);
		return Double.toString(temp[0]);

	}

}