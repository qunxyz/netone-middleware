package com.lucaslee.report.grouparithmetic;

import com.lucaslee.report.ReportException;

/**
 * 统计算法接口。
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
public interface GroupArithmetic {
	/**
	 * 获得统计结果。
	 * 
	 * @param values
	 *            要统计的数值集合。
	 * @return 结果
	 */
	public String getResult(double[] values) throws ReportException;
}