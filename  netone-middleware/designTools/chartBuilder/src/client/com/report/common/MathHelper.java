package com.report.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class MathHelper {
	private static final Integer DEF_DIV_SCALE = 10;

	private MathHelper() {

	}

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������ĺ�
	 */
	public static Double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ĳ�
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ���������Ļ�
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ������������
	 */
	public static Double div(Double v1, Double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return ������������
	 */

	public static Double div(Double v1, Double v2, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
	 */
	public static Double round(Double v, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
	 */
	public static BigDecimal round2(Double v, Integer scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 * @param scale
	 * @return
	 */
	public static BigDecimal round3(BigDecimal v, Integer scale) {
		BigDecimal one = new BigDecimal("1");
		return v.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * �Ƚ�
	 * <P>
	 * -1 : n1<n2 <BR>
	 * 0 : n1=n2 <BR>
	 * 1 : n1>n2
	 * </P>
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static int compare(BigDecimal n1, BigDecimal n2) {
		return n1.compareTo(n2);
	}

	/**
	 * ȥ��С�������������
	 * 
	 * @param v
	 * @return
	 */
	public static String cutZero(String v) {
		if (v.indexOf(".") > -1) {
			while (true) {
				if (v.lastIndexOf("0") == (v.length() - 1)) {
					v = v.substring(0, v.lastIndexOf("0"));
				} else {
					break;
				}
			}
			if (v.lastIndexOf(".") == (v.length() - 1)) {
				v = v.substring(0, v.lastIndexOf("."));
			}
		}
		return v;
	}

	/**
	 * ��ʽ����� ȥ����ѧ������
	 * 
	 * @param number
	 * @return
	 */
	public static String moneyFormat(Double number) {
		return moneyFormat(number.doubleValue(),"##0.00");
	}

	/**
	 * ��ʽ����� ȥ����ѧ������
	 * 
	 * @param number
	 * @return
	 */
	public static String moneyFormat(String number) {
		if (StringUtils.isBlank(number)) {
			return "0";
		}
		
		return moneyFormat(Double.valueOf(number),"##0.00");
	}
	
	
	/**
	 * ��ʽ����� ȥ����ѧ������
	 * 
	 * @param number
	 * @param format
	 * @return
	 */
	public static String moneyFormat(Double number, String format) {
		DecimalFormat a = new DecimalFormat(format);
		return a.format(number.doubleValue());
	}

	public static void main(String[] args) {
		// n1=n2
		System.out.println(MathHelper.compare(new BigDecimal("11.000"),
				new BigDecimal(11)));
		// n1>n2
		System.out.println(MathHelper.compare(new BigDecimal("22210.100"),
				new BigDecimal(11)));
		// n1<n2
		System.out.println(MathHelper.compare(new BigDecimal("12.000"),
				new BigDecimal("12.001")));
		// 0
		System.out.println(cutZero("111.01"));
	}

}
