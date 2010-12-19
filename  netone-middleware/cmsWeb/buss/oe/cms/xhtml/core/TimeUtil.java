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
		// sTime���俪ʼʱ�䣬eTime����Ľ���ʱ��
		String sTime = null;
		String eTime = null;
		// ����ʱ����������
		String[] timeSection = new String[2];
		/*
		 * �������Ĳ���Ϊ�ջ���ַ�����������null
		 */
		if (timetype == null || timetype.equals("")) {
			return null;
		}
		/*
		 * ���������ʱ���������ж� 1.date:���ص�ǰʱ��00:00:00�͵�ǰʱ��23:59:59 2.week:���ص�ǰ�ܵ����պ���һ
		 * 3.month:���ص�ǰ�µ�1�ź��¸���1�� 4.season:���ӵ�ǰ���ڵ�һ���º��¸����ڵĵ�һ���� 5.year:���ص�ǰ�����һ��
		 * 6.��Щ֮��Ĳ�������null
		 */
		if (timetype.equals("date")) {
			sTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), date
					.getMonth(), date.getDate()));
			eTime = DATABASE_DATE_FORMAT.format(new Date(date.getYear(), date
					.getMonth(), date.getDate() + 1));
			timeSection[0] = sTime;
			timeSection[1] = eTime;
		} else if (timetype.equals("week")) {
			// weekDay���嵱ǰʱ��Ϊ�ܼ�
			int weekDay = date.getDay();
			// sNow�������俪ʼʱ��ļӼ�������eNow�����������ʱ��ļӼ�����
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
			 * mDay�����·ݲ��ж������ĸ����� a.1-3��ʾ��һ���� b.3-6��ʾ�ڶ����� c.6-9��ʾ��������
			 * d.9-12��ʾ���ļ���
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
		 * ��������:1���������� datetype����Ϊ:date,week,month,season,year
		 */
		String dataType = "date,week,month,season,year";
		for (int i = 0; i < dataType.split(",").length; i++) {
			String[] rightTest = dateString.fetchCurtimeinfo(dataType
					.split(",")[i]);
			for (int j = 0; j < 2; j++) {
				System.out.println("��������[" + dataType.split(",")[i] + "]�ķ���ֵ:"
						+ rightTest[j]);
			}
		}
		/*
		 * ����������:��ֵ���� ��һЩ�����ַ�(",",".","*"��֮��Ĳ���)
		 */
		String[] specialTest1 = dateString.fetchCurtimeinfo(".");
		System.out.println("��ֵ����1=" + specialTest1);
		String[] specialTest2 = dateString.fetchCurtimeinfo("*");
		System.out.println("��ֵ����2=" + specialTest2);
		/*
		 * ���������ӣ�1.����Ƿ����� ����Ƿ��Ĳ�������dataType����Ĳ����Ϳգ�
		 */
		String[] errorTest1 = dateString.fetchCurtimeinfo("");
		System.out.println("����Ĳ���1=" + errorTest1);
		String[] errorTest2 = dateString.fetchCurtimeinfo("fdsdfsadsf");
		System.out.println("����Ĳ���2=" + errorTest2);
		String[] errorTest3 = dateString.fetchCurtimeinfo(null);
		System.out.println("����Ĳ���3=" + errorTest3);
	}

}
