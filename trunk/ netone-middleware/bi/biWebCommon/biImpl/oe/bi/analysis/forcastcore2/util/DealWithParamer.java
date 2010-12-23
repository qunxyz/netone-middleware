package oe.bi.analysis.forcastcore2.util;

import java.sql.Timestamp;
import java.util.Arrays;

import oe.bi.dataModel.obj.ext.SqlTypes;


public class DealWithParamer {
	/**
	 * 将字符串类型的维度值，转换为与之等效的数值类型
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	private static double dealwithEach(String dimensionvalue, String type) {
		if (dimensionvalue == null || dimensionvalue.equals("")) {
			return 0.0;
		}
		if (SqlTypes._DIM_TYPE_STRING[0].equals(type)) {
			return dimensionvalue.hashCode();
		} else if (SqlTypes._DIM_TYPE_DATE[0].equals(type)) {
			dimensionvalue = TimeUtil.repairedTimeValue(dimensionvalue);
			return Timestamp.valueOf(dimensionvalue).getTime();
		} else if (SqlTypes._DIM_TYPE_NUMBER[0].equals(type)) {
			return Double.valueOf(dimensionvalue).doubleValue();
		}
		throw new RuntimeException("无效维度类型：" + type);
	}

	/**
	 * 将所有的需要分析的维度的值，转换为数值类型
	 * 
	 * @param dimensionvalue
	 *            所有的维度值
	 * @param type
	 *            所有的维度类型
	 * @return
	 */
	public static double[] makeDimensionToRealvalue(String[][] dimensionvalue,
			int dimensionIndex, String type[]) {
		// 转换后的维度数值
		double[] realvalue = new double[dimensionvalue.length];

		for (int j = 0; j < dimensionvalue.length; j++) {
			realvalue[j] = dealwithEach(dimensionvalue[j][dimensionIndex],
					type[dimensionIndex]);
		}
		return realvalue;
	}

	/**
	 * 将所有的需要分析的维度的值，转换为数值类型
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public static double[] makeForcastDimensionToRealvalue(String[] dimensionvalue,
			String type) {
		// 转换后的维度数值
		Arrays.sort(dimensionvalue);
		double[] realvalue = new double[dimensionvalue.length];

		for (int j = 0; j < dimensionvalue.length; j++) {
			realvalue[j] = dealwithEach(dimensionvalue[j], type);
		}
		return realvalue;
	}
}
