package oe.bi.view.bus.filt.util;

import oe.bi.view.obj.ViewModel;

public class CloneViewModel {

	/**
	 * 拷贝数值，并且根据targetChoice过滤指标
	 * 
	 * @param viewmodelNew
	 * @param viewmodelOri
	 * @param targetChoice
	 */
	public static void cloneViewModel(ViewModel viewmodelNew,
			ViewModel viewmodelOri) {
		viewmodelNew.setDimensionid(viewmodelOri.getDimensionid());
		viewmodelNew.setDimensionname(viewmodelOri.getDimensionname());
		viewmodelNew.setDimensionType(viewmodelOri.getDimensionType());
		viewmodelNew.setExtendAttribute(viewmodelOri.getExtendAttribute());

		// 拷贝维度信息
		String[][] dimensionValues = viewmodelOri.getDimensionvalue();
		if (dimensionValues == null || dimensionValues.length == 0) {
			viewmodelNew.setDimensionvalue(dimensionValues);
			return;
		}
		String[][] dimensionValuesClone = new String[dimensionValues.length][dimensionValues[0].length];
		for (int i = 0; i < dimensionValues.length; i++) {
			for (int k = 0; k < dimensionValues[0].length; k++) {
				dimensionValuesClone[i][k] = dimensionValues[i][k];
			}
		}
		viewmodelNew.setDimensionvalue(dimensionValuesClone);
		// 选择targetChoice中的指定指标
		String[] targetId = viewmodelOri.getTargetid();
		String[] targetName = viewmodelOri.getTargetname();
		double[] targetalarm = viewmodelOri.getTargetalarm();
		double[][] targetvalue = viewmodelOri.getTargetvalue();

		int targetId_len = targetId == null ? 0 : targetId.length;
		String[] targetIdNew = new String[targetId_len];
		for (int i = 0; i < targetId_len; i++) {
			targetIdNew[i] = targetId[i];
		}
		int targetNameNew_len = targetName == null ? 0 : targetName.length;
		String[] targetNameNew = new String[targetNameNew_len];
		for (int i = 0; i < targetNameNew_len; i++) {
			targetNameNew[i] = targetName[i];
		}
		int targetalarmNew_len = targetalarm == null ? 0 : targetalarm.length;
		double[] targetalarmNew = new double[targetalarmNew_len];
		for (int i = 0; i < targetalarmNew_len; i++) {
			targetalarmNew[i] = targetalarm[i];
		}
		int targetvalueNew_len = targetvalue == null ? 0 : targetvalue.length;
		int targetvalueNew_len1 = targetvalue == null ? 0
				: targetvalue[0].length;
		double[][] targetvalueNew = new double[targetvalueNew_len][targetvalueNew_len1];
		for (int i = 0; i < targetvalueNew_len; i++) {
			for (int j = 0; j < targetvalueNew_len1; j++) {
				targetvalueNew[i][j] = targetvalue[i][j];
			}
		}

		// 拷贝指标
		viewmodelNew.setTargetid(targetIdNew);
		viewmodelNew.setTargetname(targetNameNew);
		viewmodelNew.setTargetalarm(targetalarmNew);
		viewmodelNew.setTargetvalue(targetvalueNew);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
