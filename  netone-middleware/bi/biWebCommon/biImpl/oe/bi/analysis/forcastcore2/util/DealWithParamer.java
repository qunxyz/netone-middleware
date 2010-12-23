package oe.bi.analysis.forcastcore2.util;

import java.sql.Timestamp;
import java.util.Arrays;

import oe.bi.dataModel.obj.ext.SqlTypes;


public class DealWithParamer {
	/**
	 * ���ַ������͵�ά��ֵ��ת��Ϊ��֮��Ч����ֵ����
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
		throw new RuntimeException("��Чά�����ͣ�" + type);
	}

	/**
	 * �����е���Ҫ������ά�ȵ�ֵ��ת��Ϊ��ֵ����
	 * 
	 * @param dimensionvalue
	 *            ���е�ά��ֵ
	 * @param type
	 *            ���е�ά������
	 * @return
	 */
	public static double[] makeDimensionToRealvalue(String[][] dimensionvalue,
			int dimensionIndex, String type[]) {
		// ת�����ά����ֵ
		double[] realvalue = new double[dimensionvalue.length];

		for (int j = 0; j < dimensionvalue.length; j++) {
			realvalue[j] = dealwithEach(dimensionvalue[j][dimensionIndex],
					type[dimensionIndex]);
		}
		return realvalue;
	}

	/**
	 * �����е���Ҫ������ά�ȵ�ֵ��ת��Ϊ��ֵ����
	 * 
	 * @param dimensionvalue
	 * @param type
	 * @return
	 */
	public static double[] makeForcastDimensionToRealvalue(String[] dimensionvalue,
			String type) {
		// ת�����ά����ֵ
		Arrays.sort(dimensionvalue);
		double[] realvalue = new double[dimensionvalue.length];

		for (int j = 0; j < dimensionvalue.length; j++) {
			realvalue[j] = dealwithEach(dimensionvalue[j], type);
		}
		return realvalue;
	}
}
