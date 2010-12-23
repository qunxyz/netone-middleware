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
	 * 检查某个维度的水平ID下的所有钻取ID中，哪个在MergerObj中命中（检查方式是从高水平往底水平）
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
