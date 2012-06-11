package com.report.flashchart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TimeUtil {
	public TimeUtil() {
	}

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIME_PATTERN = "HH:mm:ss";

	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			DATE_TIME_PATTERN);
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			DATE_PATTERN);
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			TIME_PATTERN);

	public static Date getOffsetDate(int field, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(field, offset);
		return cal.getTime();
	}

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

	public static String formatSQLString(String str) {
		if (str == null)
			return "";
		return str.replaceAll("'", "''");
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null || pattern == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return DATE_FORMAT.format(date);
	}

	public static String formatDateTime(Date date) {
		if (date == null)
			return "";
		return DATE_TIME_FORMAT.format(date);
	}

	public static String formatTime(Date date) {
		if (date == null)
			return "";
		return TIME_FORMAT.format(date);
	}

	public static Date parseDate(String dateStr) {
		try {
			return DATE_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	public static Date parseDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(dateStr);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Date parseDateTime(String dateStr) {
		try {
			return DATE_TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	public static Date parseTime(String dateStr) {
		try {
			return TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static Date firstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, 0);
		date = cal.getTime();
		return cal.getTime();
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return year;
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

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

		Date tdDate = TimeUtil.parseDate("20100101", "yyyyMMdd");
		System.out.println(tdDate);
		String tdNumber = "TD20101201";
		tdNumber = StringUtils.substring(tdNumber, 2, 10);
		System.out.println(tdNumber.length());
		System.out.println(TimeUtil.isDate("20100152", "yyyyMMdd"));
	}
}
