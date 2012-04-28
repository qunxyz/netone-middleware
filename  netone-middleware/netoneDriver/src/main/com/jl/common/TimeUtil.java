package com.jl.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author qiuguichang
 * @time 2009-03-23
 * @version 1.0
 * @histroy 1.0 date 2009-3-23 modified by qiu.gui.chang
 */
public class TimeUtil {
	public TimeUtil() {
	}

	// 日期时间的输出样式字符串
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIME_PATTERN = "HH:mm:ss";

	// 时间日期输出是样的初始化对象
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			DATE_TIME_PATTERN);
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			DATE_PATTERN);
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			TIME_PATTERN);

	/**
	 * 获取当前时间指定偏移类的时间对象
	 * 
	 * @param field
	 *            偏移的域?? 以Calendar常量取?， ?? Calendar.DATE
	 * @param offset
	 *            正负数偏移量
	 * @return
	 */
	public static Date getOffsetDate(int field, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(field, offset);
		return cal.getTime();
	}

	/**
	 * 获取日期对象的指定域??
	 * 
	 * @param dt
	 *            日期对象，如果为空将使用当前时间
	 * @param field
	 *            指定域， 以Calendar常量取??
	 * @param offset
	 *            正负数偏移量，不偏移??0
	 * @return
	 */
	public static int getDateField(Date dt, int field, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt == null ? new Date() : dt);
		if (offset != 0)
			;
		cal.add(field, offset);
		return cal.get(field);
	}

	public static int getDateField(Date dt, int field) {
		return getDateField(dt, field, 0);
	}

	public static int getDateField(int field) {
		return getDateField(null, field, 0);
	}

	public static int getDateField(int field, int offset) {
		return getDateField(null, field, offset);
	}

	/**
	 * 格式化SQL中的字串参数(??'号改??'')
	 * 
	 * @param str
	 * @return
	 */
	public static String formatSQLString(String str) {
		if (str == null)
			return "";
		return str.replaceAll("'", "''");
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd HH:mm:ss)的日??+时间字符??
	 * 
	 * @param date
	 *            Date
	 * @param pattern
	 *            String 指定的格式字符串
	 * @return String date或pattern为空,返回空串
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null || pattern == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd)的日期字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return DATE_FORMAT.format(date);
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd HH:mm:ss)的日??+时间字符??
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		if (date == null)
			return "";
		return DATE_TIME_FORMAT.format(date);
	}

	/**
	 * 格式化为默认格式(HH:mm:ss)的时间字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatTime(Date date) {
		if (date == null)
			return "";
		return TIME_FORMAT.format(date);
	}

	/**
	 * 将字符串转化为日期对??,应用格式 yyyy-MM-dd
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		try {
			return DATE_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对??,应用指定的格??
	 * 
	 * @param dateStr
	 *            String 日期字符??
	 * @param pattern
	 *            String 格式字符??
	 * @return Date
	 */
	public static Date parseDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(dateStr);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对??,应用格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateString
	 *            String
	 * @return Date
	 */
	public static Date parseDateTime(String dateStr) {
		try {
			return DATE_TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对??,应用格式 HH:mm:ss
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 */
	public static Date parseTime(String dateStr) {
		try {
			return TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 取得本月最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 取得本月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date firstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, 0);
		date = cal.getTime();
		return cal.getTime();
	}

	/**
	 * 取得年
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return year;
	}

	/**
	 * 取得月
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 取得天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 判断是否符合时间格式
	 * 
	 * @param str
	 * @param dateFormat
	 * @return
	 */
	public static boolean isDate(String str, String dateFormat) {
		if (str != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					dateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str));
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static void main(String args[]) {
		
		Date tdDate = TimeUtil.parseDate("20100101","yyyyMMdd");
		System.out.println(tdDate);
		String tdNumber = "TD20101201";
		tdNumber = StringUtils.substring(tdNumber, 2,10);
		System.out.println(tdNumber.length());
		System.out.println(TimeUtil.isDate("20100152","yyyyMMdd"));
	}
}
