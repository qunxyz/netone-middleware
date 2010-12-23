package oe.bi.view.bus.filt.util;

import java.util.Arrays;

import oe.bi.view.obj.ViewModel;


public class CopyAndSplitTargetViewModel {
	/**
	 * 拷贝数值，并且根据targetChoice过滤指标
	 * 
	 * @param viewmodelNew
	 * @param viewmodelOri
	 * @param targetChoice
	 */
	public static void copyAndSplitTargetViewModel(ViewModel viewmodelNew,
			ViewModel viewmodelOri, String targetChoice) {
		CloneViewModel.cloneViewModel(viewmodelNew, viewmodelOri);
		// 选择targetChoice中的指定指标
		String[] targetId = viewmodelNew.getTargetid();
		String[] targetName = viewmodelNew.getTargetname();
		double[][] targetvalue = viewmodelNew.getTargetvalue();

		if (targetChoice != null && !"".equals(targetChoice)) {
			String[] targetChoicex = targetChoice.split(",");
			if (targetChoicex != null && targetChoicex.length > 0) {
				int[] choiceindex = new int[targetId.length];
				Arrays.fill(choiceindex, -1);
				int choiceindexStep = 0;
				// 得到选择的指标,所属的坐标Index值
				for (int i = 0; i < targetChoicex.length; i++) {
					for (int j = 0; j < targetId.length; j++) {
						if (targetId[j].equals(targetChoicex[i])) {
							choiceindex[choiceindexStep++] = j;
						}
					}
				}
				String[] targetIdNew = new String[choiceindexStep];
				String[] targetNameNew = new String[choiceindexStep];
				double[] targetalarmNew = new double[choiceindexStep];
				double[][] targetvalueNew = new double[targetvalue.length][choiceindexStep];
				for (int i = 0; i < choiceindexStep; i++) {
					int indexRealValue = choiceindex[i];
					targetIdNew[i] = targetId[indexRealValue];
					targetNameNew[i] = targetName[indexRealValue];
					//targetalarmNew[i] = targetalarm[indexRealValue];
					for (int j = 0; j < targetvalue.length; j++) {
						targetvalueNew[j][i] = targetvalue[j][indexRealValue];
					}
				}
				// 写入重新选择后的指标
				viewmodelNew.setTargetid(targetIdNew);
				viewmodelNew.setTargetname(targetNameNew);
				viewmodelNew.setTargetalarm(targetalarmNew);
				viewmodelNew.setTargetvalue(targetvalueNew);
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
