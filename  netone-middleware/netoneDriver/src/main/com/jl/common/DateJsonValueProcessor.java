package com.jl.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * <code>
 * com.json.test.DateJsonValueProcessor.java
 * <p><li>JavaBean ��������ת����JSON���ԵĴ�������</li></P>
 * <description>��������ת����JSON���ԣ�Ĭ�ϻ��ÿ����λ��ת����JSON����</description>
 ��  author
 *</code>
 * 
 * @version 1.0.0 date 2008-12-3 created by zhang.chao.yi��Jim��
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private DateFormat dateFormat;

	public DateJsonValueProcessor(String format) {
		try {
			dateFormat = new SimpleDateFormat(format);
		} catch (Exception ex) {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		}

	}

	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return process(arg0);
	}

	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		return process(arg1);
	}

	private Object process(Object value) {
		if(value != null){
			return dateFormat.format((Date) value);
		}
		
		return "";
	}

}
