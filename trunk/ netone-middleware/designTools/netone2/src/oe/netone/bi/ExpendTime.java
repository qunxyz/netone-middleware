package oe.netone.bi;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;

public class ExpendTime {
   public static String[] getExpendTime(String starttime,String endtime)
   {
	    String[] timex = tdo(starttime, endtime, readType(starttime));
	    return timex;
   }
	public static void main(String[] args) {
		String startime = "2011-05-04 12:09:00";
		String endtime = "2011-05-06 12:09:00";
		// int timemode = Calendar.HOUR;
		// Calendar.YEAR,Calendar.MONTH,Calendar.DATE,Calendar.HOUR,Calendar.MINUTE,Calendar.SECOND
		String[] timex = tdo(startime, endtime, readType(startime));

	}

	private static int readType(String startime) {
		int caltype = 0;
		if (StringUtils.isNotEmpty(startime)) {
			if (startime.matches("\\d{4}-\\d{2}")) {
				caltype = Calendar.YEAR;
			} else if (startime.matches("\\d{4}-\\d{2}-\\d{2}")) {
				caltype = Calendar.MONTH;
			} else if (startime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}")) {
				caltype = Calendar.DATE;
			} else if (startime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
				caltype = Calendar.HOUR;
			} else if (startime
					.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
				caltype = Calendar.MINUTE;
			} else if (startime
					.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}")) {
				caltype = Calendar.SECOND;
			}
		}
		return caltype;
	}


	public static String[] tdo(String startime, String endtime, int caltype) {

		if (StringUtils.isEmpty(startime) || StringUtils.isEmpty(endtime)) {
			return new String[0];
		}

		switch (caltype) {
		case 1:
			startime = StringUtils.substring(startime, 0, 7);
			endtime = StringUtils.substring(endtime, 0, 7);
			break;
		case 2:
			startime = StringUtils.substring(startime, 0, 10);
			endtime = StringUtils.substring(endtime, 0, 10);
			break;
		case 3:
			startime = StringUtils.substring(startime, 0, 13);
			endtime = StringUtils.substring(endtime, 0, 13);
			break;
		case 4:
			startime = StringUtils.substring(startime, 0, 16);
			endtime = StringUtils.substring(endtime, 0, 16);
			break;
		case 5:
			startime = StringUtils.substring(startime, 0, 19);
			endtime = StringUtils.substring(endtime, 0, 19);
			break;

		}

		int interval = 1;
		int size = 0;
		String endTime = endtime;
		String time = startime;

		int pretype = 0;
		Calendar cal = Calendar.getInstance();
		pretype = string2Calendar(cal, time);
		Calendar endcal = null;
		if (endTime != null) {
			// ��ʼ��size��ֵ��
			size = 10000;
			endcal = Calendar.getInstance();
			string2Calendar(endcal, endTime);
		}

		String formatstr = "";
		switch (pretype) {
		case 1:
			formatstr = "yyyy";
			break;
		case 2:
			formatstr = "yyyy-MM";
			break;
		case 3:
			formatstr = "yyyy-MM-dd";
			break;
		case 4:
			formatstr = "yyyy-MM-dd HH";
			break;
		case 5:
			formatstr = "yyyy-MM-dd HH:mm";
			break;
		case 6:
			formatstr = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(formatstr);
		List timelist = new ArrayList();
		String str = df.format(new Date(cal.getTimeInMillis()));
		timelist.add(str);
		for (int i = 0; i < size - 1; i++) {
			cal.add(caltype, interval);
			// ������ʱ��㲻Ϊ��ʱ
			if (endcal != null) {
				if (cal.getTimeInMillis() > endcal.getTimeInMillis()) {
					// ��������ʱ�˳�ѭ����
					break;
				}
			}
			String tmpstr = df.format(new Date(cal.getTimeInMillis()));
			System.out.println(tmpstr);
			timelist.add(tmpstr);
		}
		return (String[]) timelist.toArray(new String[0]);

	}

	// ����id��name��level
	public static String[] parsePreStr(String prestr) {
		// ����2006-12-04 00$5&0ʱ,����
		String[] strs = new String[3];
		int i = prestr.indexOf("$");
		int j = prestr.indexOf("&");
		int k = prestr.indexOf(",");
		strs[0] = prestr.substring(0, i);
		strs[2] = prestr.substring(i + 1, j);
		strs[1] = prestr.substring(j + 1, k);
		return strs;
	}

	private static int string2Calendar(Calendar cal, String time) {
		int pretype = 0;
		cal.setTimeInMillis(0);
		if (time.length() >= 4) {
			String year = time.substring(0, 4);
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			pretype = 1;
		}
		if (time.length() >= 7) {
			String month = time.substring(5, 7);
			cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
			pretype = 2;
		}
		if (time.length() >= 10) {
			String day = time.substring(8, 10);
			cal.set(Calendar.DATE, Integer.parseInt(day));
			pretype = 3;
		}
		if (time.length() >= 13) {
			String hour = time.substring(11, 13);
			cal.set(Calendar.HOUR, Integer.parseInt(hour));
			pretype = 4;
		}
		if (time.length() >= 16) {
			String minute = time.substring(14, 16);
			cal.set(Calendar.MINUTE, Integer.parseInt(minute));
			pretype = 5;
		}
		if (time.length() >= 19) {
			String second = time.substring(17, 19);
			cal.set(Calendar.SECOND, Integer.parseInt(second));
			pretype = 6;
		}
		return pretype;
	}

}
