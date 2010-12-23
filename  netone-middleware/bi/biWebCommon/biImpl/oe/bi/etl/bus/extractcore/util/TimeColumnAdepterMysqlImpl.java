package oe.bi.etl.bus.extractcore.util;

import oe.bi.BiEntry;
import oe.bi.TimeColumnAdepter;
import oe.bi.TimeTree;
import oe.bi.dataModel.obj.ext.SqlTypes;


public class TimeColumnAdepterMysqlImpl implements TimeColumnAdepter {

	private  String makeYearCondtion(String dimId, String dimensionvalue) {
		int nextyear = Integer.parseInt(dimensionvalue) + 1;
		String toYear = "'" + dimensionvalue
				+ "-01-01')";
		String fromYear = "'" + nextyear
				+ "-01-01'- INTERVAL 1 day ";
		return dimId + " between " + toYear + " and " + fromYear;
	}

	private  String monthAddOne(String dimensionvalue) {
		String monthid = dimensionvalue.substring(5, 7);
		String yearid = dimensionvalue.substring(0, 4);
		String monthNextValue = null;
		int nextmonth = 0;
		int monthvalue = Integer.parseInt(monthid);
		if (monthvalue < 12) {
			nextmonth = monthvalue + 1;
			monthNextValue = yearid + "-" + nextmonth;
		} else {
			int yearNext = Integer.parseInt(yearid) + 1;
			monthNextValue = yearNext + "-" + "01";
		}
		return monthNextValue;
	}

	private  String makeMonthCondition(String dimId, String dimensionvalue) {
		String dimvalue = monthAddOne(dimensionvalue);
		String toMonth = "'" + dimensionvalue
				+ "-01'";
		String fromMonth = "'" + dimvalue
				+ "-01'- INTERVAL 1 day ";
		return dimId + " between " + toMonth + " and " + fromMonth;
	}

	private  String addDayOne(String dimensionvalue) {
		String time = dimensionvalue.substring(0, 7);
		String day = dimensionvalue.substring(8, 10);
		TimeTree timeTree = (TimeTree) BiEntry.fetchBi("timeTree");
		String[][] dayinfo = timeTree.fetchDay(time);
		int dayValue = Integer.parseInt(day);
		if (dayValue == dayinfo.length ) {
			time = monthAddOne(time);
			day = "1";
		} else {
			day = (dayValue + 1) + "";
		}
		return time + "-" + day;
	}

	private  String makeDayCondtion(String dimId, String dimensionvalue) {
		String newValue = addDayOne(dimensionvalue);
		String toDay = "'" + dimensionvalue
				+ "'";
		String fromDay = "'" + newValue
				+ "'- INTERVAL 1 second  ";

		return dimId + " between " + toDay + " and " + fromDay;
	}

	private  String addHourOne(String dimensionvalue) {

		String yearMonDay = dimensionvalue.substring(0, 10);
		String hour = dimensionvalue.substring(11, 13);
		int hourvalue = Integer.parseInt(hour);
		if (hourvalue != 23) {
			hour = hourvalue + 1 + "";
		} else {
			yearMonDay = addDayOne(yearMonDay);
			hour = "00";
		}
		return yearMonDay + " " + hour;
	}

	private  String makehourCondition(String dimId, String dimensionvalue) {
		String newData = addHourOne(dimensionvalue);
		String tohour = "'" + dimensionvalue
				+ "'";
		String fromhour = "'" + newData
				+ "' - INTERVAL 1 second";

		return dimId + " between " + tohour + " and " + fromhour;
	}

	private  String addMinOne(String dimensionvalue) {
		String time = dimensionvalue.substring(0, 13);
		String min = dimensionvalue.substring(14, 16);
		if (min.equals("59")) {
			time = addHourOne(time);
			min = "00";
		} else {
			min = (Integer.parseInt(min) + 1) + "";
		}
		return time + ":"+min;
	}

	private  String makeMinCondition(String dimId, String dimensionvalue) {
		String newData = addMinOne(dimensionvalue);
		String tohour = "'" + dimensionvalue
				+ "'";
		String fromhour = "'" + newData
				+ "'- INTERVAL 1 second";

		return dimId + " between " + tohour + " and " + fromhour;
	}

	private  String addSecOne(String dimensionvalue) {
		String time = dimensionvalue.substring(0, 16);
		String sec = dimensionvalue.substring(17, 19);
		if (sec.equals("59")) {
			time = addMinOne(time);
			sec = "00";
		} else {
			sec = (Integer.parseInt(sec) + 1) + "";
		}
		return time + ":"+sec;
	}

	private  String makeSecCondition(String dimId, String dimensionvalue) {
		String newData = addSecOne(dimensionvalue);
		String tohour = "'" + dimensionvalue
				+ "'";
		String fromhour = "'" + newData
				+ "'- INTERVAL 1 second";

		return dimId + " between " + tohour + " and " + fromhour;
	}

	/**
	 * 处理 维度类型参数 对于维度可能存在的类型: SqlTypes中的除了Number类型外,其余的都将作为字符串来处理
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public  String adaptDateDimensionTypes(String dimensionvalue,
			String dimId) {

		if (dimensionvalue.matches(TimeTree._RULE_YEAR)) {
			return makeYearCondtion(dimId, dimensionvalue);
		} else if ((dimensionvalue.matches(TimeTree._RULE_MONTH))) {
			return makeMonthCondition(dimId, dimensionvalue);
		} else if ((dimensionvalue.matches(TimeTree._RULE_DAY))) {
			return makeDayCondtion(dimId, dimensionvalue);
		} else if ((dimensionvalue.matches(TimeTree._RULE_HOUR))) {
			return makehourCondition(dimId, dimensionvalue);
		} else if ((dimensionvalue.matches(TimeTree._RULE_MIN))) {
			return makeMinCondition(dimId, dimensionvalue);
		} else if ((dimensionvalue.matches(TimeTree._RULE_SEC))) {
			return makeSecCondition(dimId, dimensionvalue);
		}
		throw new RuntimeException("不支持的时间:" + dimensionvalue);

	}

	/**
	 * 处理 维度类型参数 对于维度可能存在的类型: SqlTypes中的除了Number类型外,其余的都将作为字符串来处理
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public  String adaptStringNumberDimensionTypes(String dimensionvalue,
			String type) {
		if (SqlTypes._DIM_TYPE_NUMBER[0].equals(type)) {
			return "," + dimensionvalue;
		} else if (SqlTypes._DIM_TYPE_STRING[0].equals(type)) {
			return ",'" + dimensionvalue + "'";
		}
		// else if (SqlTypes._DIM_TYPE_DATE[0].equals(type)) {
		// if (dimensionvalue.matches(TimeTree._RULE_YEAR)) {
		// return ",trunc(to_date('" + dimensionvalue
		// + "','yyyy'),'yyyy')";
		// } else if ((dimensionvalue.matches(TimeTree._RULE_MONTH))) {
		// return ",trunc(to_date('" + dimensionvalue
		// + "','yyyy-mm'),'mm')";
		// } else if ((dimensionvalue.matches(TimeTree._RULE_DAY))) {
		// return ",trunc(to_date('" + dimensionvalue
		// + "','yyyy-mm-dd'),'dd')";
		// } else if ((dimensionvalue.matches(TimeTree._RULE_HOUR))) {
		// return ",trunc(to_date('" + dimensionvalue
		// + "','yyyy-mm-dd hh:mi:ss'),'hh24')";
		// } else if ((dimensionvalue.matches(TimeTree._RULE_MIN))) {
		// return ",trunc(to_date('" + dimensionvalue
		// + "','yyyy-mm-dd hh:mi:ss'),'mm')";
		// } else if ((dimensionvalue.matches(TimeTree._RULE_SEC))) {
		// return ",trunc(to_date('" + dimensionvalue
		// + "','yyyy-mm-dd hh:mi:ss'),'ss')";
		// }
		//
		// }
		// throw new RuntimeException("无效维度类型 " + type);
		// if (SqlTypes._DIM_SQL_TYPE_DATE[0].equals(type)) {
		// return ",to_date('" + dimensionvalue + "','YYYY-MM-DD')";
		//
		// } else if (SqlTypes._DIM_SQL_TYPE_NUMBER[0].equals(type)) {
		// return "," + dimensionvalue;
		// } else if (SqlTypes._DIM_SQL_TYPE_STRING[0].equals(type)) {
		// return ",'" + dimensionvalue + "'";
		// } else if (SqlTypes._DIM_SQL_TYPE_DATETIME[0].equals(type)) {
		// return ",to_date('" + dimensionvalue + "','YYYY-MM-DD hh:mm:ss')";
		// } else if (SqlTypes._DIM_SQL_YPE_TIMESTAMP[0].equals(type)) {
		// return ",to_date('" + dimensionvalue
		// + "','YYYY-MM-DD hh:mm:ss.xxx')";
		// // }
		throw new RuntimeException("无效维度类型 " + type);

	}


}
