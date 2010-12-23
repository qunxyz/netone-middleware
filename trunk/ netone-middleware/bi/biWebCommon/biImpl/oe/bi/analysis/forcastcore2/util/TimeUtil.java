package oe.bi.analysis.forcastcore2.util;

import oe.bi.TimeTree;

public class TimeUtil {

	private static String _FIRST_DAY = "-01 00:00:00.000";

	private static String _FIRST_HOUR = " 00:00:00.000";

	private static String _FIRST_MIN = ":00:00.000";

	private static String _FIRST_SEC = ":00.000";

	private static String _PREVIEW_YEAR_LAST_TIME = "-12-31 23:59:59.999";

	public static String repairedTimeValue(String dimensionvalue) {
		if (dimensionvalue.matches(TimeTree._RULE_YEAR)) {
			// �����깹�����һ������һ˲ʱ
			int value = Integer.valueOf(dimensionvalue).intValue() - 1;
			return value + _PREVIEW_YEAR_LAST_TIME;
		} else if (dimensionvalue.matches(TimeTree._RULE_MONTH)) {
			// �����¹���ɱ��µ�һ�������ʱ��
			return dimensionvalue + _FIRST_DAY;
		} else if (dimensionvalue.matches(TimeTree._RULE_DAY)) {
			// �����չ���ɱ��յ�����ʱ��
			return dimensionvalue + _FIRST_HOUR;
		} else if (dimensionvalue.matches(TimeTree._RULE_HOUR)) {
			// �����չ���ɱ�ʱ����ʱ��
			return dimensionvalue + _FIRST_MIN;
		} else if (dimensionvalue.matches(TimeTree._RULE_MIN)) {
			// �����չ���ɱ�������ʱ��
			return dimensionvalue + _FIRST_SEC;
		}
		return dimensionvalue;

	}

}
