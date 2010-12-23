package oe.bi.etl.bus.extractcore.util;

import oe.bi.TimeTree;
import oe.bi.dataModel.obj.ext.SqlTypes;


public class DimensionColumnModify {

	public static String modifyColumn(String dimensionColumn, String type,
			String valueExample) {

		if (SqlTypes._DIM_TYPE_NUMBER[0].equals(type)
				|| SqlTypes._DIM_TYPE_STRING[0].equals(type)) {
			return dimensionColumn;
		} else if (SqlTypes._DIM_TYPE_DATE[0].equals(type)) {
			if (valueExample.matches(TimeTree._RULE_YEAR)) {
				return " to_char(" + dimensionColumn + ",'yyyy')";
			} else if (valueExample.matches(TimeTree._RULE_MONTH)) {
				return " to_char(" + dimensionColumn + ",'yyyy-mm')";
			} else if (valueExample.matches(TimeTree._RULE_DAY)) {
				return " to_char(" + dimensionColumn + ",'yyyy-mm-dd')";
			} else if (valueExample.matches(TimeTree._RULE_HOUR)) {
				return " to_char(" + dimensionColumn + ",'yyyy-mm-dd hh24')";
			} else if (valueExample.matches(TimeTree._RULE_MIN)) {
				return " to_char(" + dimensionColumn + ",'yyyy-mm-dd hh24:mi')";
			} else if (valueExample.matches(TimeTree._RULE_SEC)) {
				return " to_char(" + dimensionColumn
						+ ",'yyyy-mm-dd hh24:mi:ss')";
			}
		}
		throw new RuntimeException("无效维度类型 " + type);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
