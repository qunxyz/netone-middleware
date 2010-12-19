package oe.cms.xhtml.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimeUtil {

	Log log = LogFactory.getLog(TimeUtil.class);

	public final static SimpleDateFormat DATABASE_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static String[] fetchCurtimeinfo(String timetype) {
		long sysdate = System.currentTimeMillis();
		Date date = new Date(sysdate);
		// sTime区间开始时间，eTime区间的结束时间
		String sTime = null;
		String eTime = null;
		// 定义时间区间数组
		String[] timeSection = new String[2];
		/*
		 * 传进来的参数为空或空字符串，即返回null
		 */
		if (timetype == null || timetype.equals("")) {
			return null;
		}
		/*
		 * 根据输入的时间类型做判断 1.date:返回当前时间00:00:00和当前时间23:59:59 2.week:返回当前周的周日和周一
		 * 3.month:返回当前月的1号和下个月1号 4.season:返加当前季节第一个月和下个季节的第一个月 5.year:返回当前年和下一年
		 * 6.除些之间的参数返回null
		 */
		if (timetype.equals("date")) {
			sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), date
					.getMonth(), date.getDate()));
			eTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), date
					.getMonth(), date.getDate() + 1));
			timeSection[0] = sTime;
			timeSection[1] = eTime;
		} else if (timetype.equals("week")) {
			// weekDay定义当前时间为周几
			int weekDay = date.getDay();
			// sNow处理区间开始时间的加减操作，eNow处理区间结束时间的加减操作
			Calendar sNow = Calendar.getInstance();
			Calendar eNow = Calendar.getInstance();
			sNow.add(Calendar.DAY_OF_WEEK, -weekDay);
			eNow.add(Calendar.DAY_OF_WEEK, -weekDay + 7);
			sTime = DATABASE_DATE_FORMAT.format(sNow.getTime());
			eTime = DATABASE_DATE_FORMAT.format(eNow.getTime());
			timeSection[0] = sTime;
			timeSection[1] = eTime;
		} else if (timetype.equals("month")) {
			sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), date
					.getMonth(), 1));
			eTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), date
					.getMonth() + 1, 1));
			timeSection[0] = sTime;
			timeSection[1] = eTime;
		} else if (timetype.equals("season")) {
			/*
			 * mDay定义月份并判断属于哪个季节 a.1-3表示第一季节 b.3-6表示第二季节 c.6-9表示第三季节
			 * d.9-12表示第四季节
			 */

			int mDay = date.getMonth() + 1;
			if (mDay > 0 && mDay <= 3) {
				sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), 0,
						1));
				eTime = DATABASE_DATE_FORMAT.format(new Date(
						date.getYear() + 1, 2, 31));
			} else if (mDay > 3 && mDay <= 6) {
				sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), 3,
						1));
				eTime = DATABASE_DATE_FORMAT.format(new Date(
						date.getYear() + 1, 5, 30));
			} else if (mDay > 6 && mDay <= 9) {
				sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), 5,
						1));
				eTime = DATABASE_DATE_FORMAT.format(new Date(
						date.getYear() + 1, 8, 30));

			} else {
				sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), 8,
						1));
				eTime = DATABASE_DATE_FORMAT.format(new Date(
						date.getYear() + 1, 11, 31));
			}
			timeSection[0] = sTime;
			timeSection[1] = eTime;
		} else if (timetype.equals("year")) {
			sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), 0, 1));
			eTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear() + 1, 0,
					1));
			timeSection[0] = sTime;
			timeSection[1] = eTime;
		} else {
			return null;
		}
		return timeSection;
	}

	public static void main(String[] args) {
		TimeUtil dateString = new TimeUtil();
		/*
		 * 测试用例:1：正常参数 datetype参数为:date,week,month,season,year
		 */
		String dataType = "date,week,month,season,year";
		for (int i = 0; i < dataType.split(",").length; i++) {
			String[] rightTest = dateString.fetchCurtimeinfo(dataType
					.split(",")[i]);
			for (int j = 0; j < 2; j++) {
				System.out.println("数据类型[" + dataType.split(",")[i] + "]的返回值:"
						+ rightTest[j]);
			}
		}
		/*
		 * 测试用例子:边值参数 如一些特殊字符(",",".","*"等之类的参数)
		 */
		String[] specialTest1 = dateString.fetchCurtimeinfo(".");
		System.out.println("边值参数1=" + specialTest1);
		String[] specialTest2 = dateString.fetchCurtimeinfo("*");
		System.out.println("边值参数2=" + specialTest2);
		/*
		 * 测试用例子：1.错误非法参数 传入非法的参数（即dataType以外的参数和空）
		 */
		String[] errorTest1 = dateString.fetchCurtimeinfo("");
		System.out.println("错误的参数1=" + errorTest1);
		String[] errorTest2 = dateString.fetchCurtimeinfo("fdsdfsadsf");
		System.out.println("错误的参数2=" + errorTest2);
		String[] errorTest3 = dateString.fetchCurtimeinfo(null);
		System.out.println("错误的参数3=" + errorTest3);
	}

}
