package oe.bi.etl.bus.optimizecore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.datasource.DataModelDao;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.TargetElement;


public class ChoiceBestLevel {

	private static final int _MAX_WEIGHT = 99999;

	public static OptimizeTable choiceBestLevel(ChoiceInfo arg0) {
		// 获得选择维度
		List listDimension = arg0.getDimensionElement();
		// 获得选择指标
		List listTarget = arg0.getTargetElement();
		// 获得模型对象
		String datamodelid = arg0.getDataModelid();
		DataModelDao dao = (DataModelDao) BiEntry.fetchBi("dataModelDao");
		DataModel datamodel = null;
		try {
			datamodel = dao.fetchDataModel(datamodelid);
		} catch (UnableLoadDataModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map mapOptimize = datamodel.getOptimizes();
		int weight = _MAX_WEIGHT;
		OptimizeTable opiFinal = null;
		for (Iterator itr = mapOptimize.keySet().iterator(); itr.hasNext();) {
			OptimizeTable opiTab = (OptimizeTable) mapOptimize.get(itr.next());
			Map targetMap = opiTab.fetchTargetmap();
			Map dimensionMap = opiTab.fetchDimensionmap();
			// 检查归并元素中是否存在所选择的全部指标
			boolean loseTarget = false;
			for (Iterator itrx = listTarget.iterator(); itrx.hasNext();) {
				TargetElement targetColumn = (TargetElement) itrx.next();
				if (!targetMap.containsKey(targetColumn.getId())) {
					loseTarget = true;
					break;
				}
			}
			if (loseTarget) {
				continue;
			}
			// 检查归并元素中是否存在所有的维度（或者维度的下级粒度）
			boolean loseDimension = false;
			int minWeight = 0;
			for (Iterator itrx = listDimension.iterator(); itrx.hasNext();) {
				DimensionElement dimColumn = (DimensionElement) itrx.next();
				String dimensionid = dimColumn.getId();
				if (!dimensionMap.containsKey(dimensionid)) {
					loseDimension = true;
					break;
				}
				int choiceLevel = Integer
						.parseInt(dimColumn.getLevelcolumnid());
				String thisLevelTmp = (String) dimensionMap.get(dimensionid);
				String thisLevelStr = thisLevelTmp.split(",")[0];
				int thisLevel = Integer.parseInt(thisLevelStr);
				if (choiceLevel > thisLevel) {// 选择的小于归并表的水平，那么该归并表无法使用
					loseDimension = true;
					break;
				}
				minWeight += thisLevel;
			}
			if (loseDimension) {
				continue;
			}
			if (minWeight < weight) {
				opiFinal = opiTab;
				weight = minWeight;
			}
		}
		return opiFinal;
	}
}
