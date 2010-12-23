package oe.bi.etl.bus.extractcore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.etl.obj.DimensionElement;


public class FetchDimensionTypes {

	public static String[] fetchDimensionTypes(List dimesionlist,
			DataModel datamodel) {
		String[] types = new String[dimesionlist.size()];
		Map dimColumns = datamodel.getDimColumns();
		int i = 0;
		for (Iterator itr = dimesionlist.iterator(); itr.hasNext();) {
			DimensionElement dimElement = (DimensionElement) itr.next();
			DimColumn dimColumnPre = (DimColumn) dimColumns.get(dimElement
					.getId());
			if (dimColumnPre == null) {
				throw new RuntimeException(dimElement.getId()
						+ "目标指标组中不存在该字段");
			}
			types[i++] = dimColumnPre.getSqltype();

		}
		return types;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
