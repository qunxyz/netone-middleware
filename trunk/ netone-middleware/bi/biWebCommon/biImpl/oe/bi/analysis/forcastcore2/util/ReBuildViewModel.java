package oe.bi.analysis.forcastcore2.util;

import java.util.Map;

import oe.bi.view.obj.ViewModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ReBuildViewModel {
	private static Log _log = LogFactory.getLog(ReBuildViewModel.class);

	public static void modifyViewObj(ViewModel viewObj, double[][] target,
			String[] dimensionvalue, Map otherDimension) {

		int oriLen = viewObj.getTargetvalue().length;
		int newLen = oriLen + target.length;
		// 重新修改指标
		double[][] targetValue = viewObj.getTargetvalue();
		double[][] targetValueNew = new double[newLen][targetValue[0].length];
		System.arraycopy(targetValue, 0, targetValueNew, 0, targetValue.length);
		System.arraycopy(target, 0, targetValueNew, targetValue.length,
				target.length);
		viewObj.setTargetvalue(targetValueNew);
		// 重新修改维度
		String[][] dimensionValue = viewObj.getDimensionvalue();
		String[][] dimensionValueNew = new String[newLen][dimensionValue[0].length];
		System.arraycopy(dimensionValue, 0, dimensionValueNew, 0,
				dimensionValue.length);

		String[] dimensionId = viewObj.getDimensionid();
		for (int i = oriLen; i < newLen; i++) {
			for (int j = 0; j < dimensionId.length; j++) {
				Object dimChoiceValue = (Object) otherDimension
						.get(dimensionId[j]);
				dimensionValueNew[i][j] = dimChoiceValue == null ? dimensionvalue[i-oriLen]
						: (String) dimChoiceValue;
			}

		}
		viewObj.setDimensionvalue(dimensionValueNew);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
