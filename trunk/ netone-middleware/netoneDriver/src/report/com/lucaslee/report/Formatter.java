package com.lucaslee.report;

import java.text.ParseException;

/**
 * ���ڸ�ʽ�����ݵĽӿڡ�
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
	 * ��ʽ���ַ�����
	 * 
	 * @param str
	 *            Ҫ��ʽ�����ַ���
	 * @return ��ʽ������ַ���
	 * @throws ReportException
	 */
	public abstract String format(String str) throws ParseException;
}