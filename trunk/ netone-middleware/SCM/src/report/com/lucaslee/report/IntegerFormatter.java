package com.lucaslee.report;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 空白的数据格式化类,不做格式化处理。
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
		// TODO 应进行4舍5入

		// 确定小数位数
		f.setMaximumFractionDigits(0);
		f.setMinimumFractionDigits(0);
	}

	/**
	 * 参考父类文档
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