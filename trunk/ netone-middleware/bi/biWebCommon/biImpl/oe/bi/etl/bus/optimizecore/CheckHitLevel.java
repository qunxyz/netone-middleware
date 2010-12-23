package oe.bi.etl.bus.optimizecore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.MergerObj;


public class CheckHitLevel {
	/**
	 * ���ĳ��ά�ȵ�ˮƽID�µ�������ȡID�У��ĸ���MergerObj�����У���鷽ʽ�ǴӸ�ˮƽ����ˮƽ��
	 * 
	 * @param levelDigId
	 * @param mergerobj
	 * @return
	 */
	public static String checkHitLevel(DataModel datamodel,
			DimensionElement dimEle, OptimizeTable opiTab) {
		String[] levelDigId = FetchDimensionElementAllDigId
				.fetchDimensionAllDig(datamodel, dimEle.getId(),
						dimEle.getLevelcolumnid());

		Map dimensionMap = opiTab.fetchDimensionmap();
		for (int i = 0; i < levelDigId.length; i++) {
			if (dimensionMap.containsKey(levelDigId[i])) {
				return levelDigId[i];
			}
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
