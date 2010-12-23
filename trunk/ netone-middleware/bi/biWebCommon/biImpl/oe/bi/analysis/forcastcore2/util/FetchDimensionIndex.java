package oe.bi.analysis.forcastcore2.util;

import oe.bi.view.obj.GraphModel;

public class FetchDimensionIndex {
	/**
	 * 计算选择的维度的下标
	 * 
	 * @param dimensions
	 * @param listDim
	 * @return
	 */
	public static int fetchDimensionIndex(String[] dimensions, GraphModel dimEle) {

			String dimId = dimEle.getXOffsetDimension();
			for (int i = 0; i < dimensions.length; i++) {
				if (dimensions[i].equals(dimId)) {
					return i;
				}
			}
		throw new RuntimeException("被预测的维度,不属于");

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
