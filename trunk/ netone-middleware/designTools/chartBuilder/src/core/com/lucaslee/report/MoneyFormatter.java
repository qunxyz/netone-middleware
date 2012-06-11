package com.lucaslee.report;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * ������ݸ�ʽ����
 */
public class MoneyFormatter implements Formatter {

	private DecimalFormat f = new DecimalFormat();

	private String format;

	public MoneyFormatter(String format) {
		this.format = format;
		f = new DecimalFormat(format);
	}

	public MoneyFormatter() {
		this.format = "##0.00";
		f = new DecimalFormat(format);
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