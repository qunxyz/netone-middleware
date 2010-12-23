package oe.bi.analysis.forcastcore2.util;

import oe.bi.view.obj.GraphModel;

public class FetchDimensionIndex {
	/**
	 * ����ѡ���ά�ȵ��±�
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
		throw new RuntimeException("��Ԥ���ά��,������");

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
