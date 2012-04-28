package com.lucaslee.report;

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
public class NullFormatter implements Formatter {

	/* (non-Javadoc)
	 * @see com.lucaslee.report.Formatter#format(java.lang.String)
	 */
	public String format(String str) throws ParseException {
		return str;
	}
}