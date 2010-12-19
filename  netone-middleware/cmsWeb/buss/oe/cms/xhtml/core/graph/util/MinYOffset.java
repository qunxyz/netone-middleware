package oe.cms.xhtml.core.graph.util;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class MinYOffset {

	public static String minY(String[] y) {
		if (y == null || y.length == 0) {
			return "0";
		}
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < y.length; i++) {
			if (y[i] != null) {
				but.append(y[i] + ",");
			}
		}
		return minyCore(but.toString());

	}

	private static String minyCore(String y) {
		System.out.println(y);
		y = StringUtils.replace(y, ",,", ",");
		String[] yArr = y.split(",");
		if (yArr != null || yArr.length > 0) {

			float[] arr = new float[yArr.length];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = Float.parseFloat(yArr[i]);
			}
			Arrays.sort(arr);

			try {
				double minvalue = arr[0] * 0.8;
				System.out.println(minvalue);
				return String.valueOf(minvalue);
			} catch (Exception e) {
				return "0";
			}
		}
		return "0";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String abc = "1,3,6,8,2,0.12";

	}
}
