package com.lucaslee.report;

import java.text.ParseException;

/**
 * 用于格式化数据的接口。
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
public interface Formatter {

	/**
	 * 格式化字符串。
	 * 
	 * @param str
	 *            要格式化的字符串
	 * @return 格式化后的字符串
	 * @throws ReportException
	 */
	public abstract String format(String str) throws ParseException;
}