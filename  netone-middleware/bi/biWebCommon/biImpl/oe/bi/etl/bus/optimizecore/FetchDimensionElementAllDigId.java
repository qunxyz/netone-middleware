package oe.bi.etl.bus.optimizecore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.util.DimColumnUtil;
import oe.bi.dataModel.util.TreeModel;


public class FetchDimensionElementAllDigId {
	/**
	 * 获得一个维度的选择水平ID的所有下级钻取ID
	 * 
	 * @param datamodel
	 * @param dimensionid
	 * @param levelDimensionid
	 * @return
	 */
	public static String[] fetchDimensionAllDig(DataModel datamodel,
			String dimensionid, String levelDimensionid) {
		DimColumn dimColumn = (DimColumn) datamodel.getDimColumns().get(
				dimensionid);
		List treeModels = DimColumnUtil
				.fetchTreeModel(dimColumn.getTreeModel());
		List listDigDimensionLevelid = new ArrayList();
		for (Iterator itr = treeModels.iterator(); itr.hasNext();) {
			TreeModel treeMdoel = (TreeModel) itr.next();
			String columnid = treeMdoel.getTableName() + "."
					+ treeMdoel.getColumnid();
			if (levelDimensionid.equals(columnid)) {
				listDigDimensionLevelid.add(levelDimensionid);
				for (; itr.hasNext();) {
					TreeModel treeMdoelDig = (TreeModel) itr.next();
					String columnidDig = treeMdoelDig.getTableName() + "."
							+ treeMdoelDig.getColumnid();
					listDigDimensionLevelid.add(columnidDig);
				}
				return (String[]) listDigDimensionLevelid
						.toArray(new String[0]);
			}
		}
		return new String[] { levelDimensionid };
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
