package com.report.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class MathHelper {
	private static final Integer DEF_DIV_SCALE = 10;

	private MathHelper() {

	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static Double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static Double div(Double v1, Double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
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
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
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
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
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
	 * 提供精确的小数位四舍五入处理。
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
	 * 比较
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
	 * 去掉小数点后面多余的零
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
	 * 格式化金额 去除科学计数法
	 * 
	 * @param number
	 * @return
	 */
	public static String moneyFormat(Double number) {
		return moneyFormat(number.doubleValue(),"##0.00");
	}

	/**
	 * 格式化金额 去除科学计数法
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
	 * 格式化金额 去除科学计数法
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
