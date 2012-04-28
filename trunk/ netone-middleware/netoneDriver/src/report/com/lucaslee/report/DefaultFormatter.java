package com.lucaslee.report;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

/**
 * ȱʡ�����ݸ�ʽ���ࡣ
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
public class DefaultFormatter implements Formatter {
	private DecimalFormat f = new DecimalFormat();

	public DefaultFormatter() {
		// TODO Ӧ����4��5��

		// ȷ��С��λ��
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
	}

	/**
	 * �ο������ĵ�
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public String format(String str) throws ParseException {
		if (StringUtils.isBlank(str)) {
			return "";
		}

		double temp = f.parse(str).doubleValue();

		return f.format(temp);

	}
}