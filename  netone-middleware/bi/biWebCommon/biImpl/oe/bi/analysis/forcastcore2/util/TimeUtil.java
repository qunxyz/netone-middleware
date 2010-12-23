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
			// 将本年构造成上一年的最后一瞬时
			int value = Integer.valueOf(dimensionvalue).intValue() - 1;
			return value + _PREVIEW_YEAR_LAST_TIME;
		} else if (dimensionvalue.matches(TimeTree._RULE_MONTH)) {
			// 将本月构造成本月第一天的最早时刻
			return dimensionvalue + _FIRST_DAY;
		} else if (dimensionvalue.matches(TimeTree._RULE_DAY)) {
			// 将本日构造成本日的最早时刻
			return dimensionvalue + _FIRST_HOUR;
		} else if (dimensionvalue.matches(TimeTree._RULE_HOUR)) {
			// 将本日构造成本时最早时刻
			return dimensionvalue + _FIRST_MIN;
		} else if (dimensionvalue.matches(TimeTree._RULE_MIN)) {
			// 将本日构造成本分最早时刻
			return dimensionvalue + _FIRST_SEC;
		}
		return dimensionvalue;

	}

}
