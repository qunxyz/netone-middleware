package com.lucaslee.report.grouparithmetic;

import com.lucaslee.report.ReportException;

/**
 * ͳ���㷨�ӿڡ�
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
	 * ���ͳ�ƽ����
	 * 
	 * @param values
	 *            Ҫͳ�Ƶ���ֵ���ϡ�
	 * @return ���
	 */
	public String getResult(double[] values) throws ReportException;
}