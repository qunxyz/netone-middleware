package com.lucaslee.report;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * �հ׵����ݸ�ʽ����,������ʽ������
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
public class IntegerFormatter implements Formatter {

	private DecimalFormat f = new DecimalFormat();

	public IntegerFormatter() {
		// TODO Ӧ����4��5��

		// ȷ��С��λ��
		f.setMaximumFractionDigits(0);
		f.setMinimumFractionDigits(0);
	}

	/**
	 * �ο������ĵ�
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public String format(String str) throws ParseException {
		double temp = f.parse(str).doubleValue();

		return f.format(temp);

	}
}