package oe.bi.etl.bus.extractcore;

import java.util.Map;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.TargetColumn;


public class FetchDimensionName {

	public static String[] fetchDimensionNameInfo(String[] id,
			DataModel datamodel) {
		Map map = datamodel.getDimColumns();
		String[] dimNames = new String[id.length];
		for (int i = 0; i < id.length; i++) {
			if (map.containsKey(id[i])) {
				DimColumn dimColumn = (DimColumn) map.get(id[i]);
				dimNames[i] = dimColumn.getName();
			}
		}
		return dimNames;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
