package oe.bi.etl.bus.extractcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.dataModel.bus.DigLevelAtomSQL;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.dataModel.obj.ext.TreeModel;
import oe.bi.etl.bus.arithmetic.NDimension;
import oe.bi.etl.bus.extractcore.util.MakeInExpression;
import oe.bi.etl.obj.DimensionElement;





public class DimensionConditionOptimize {

	/**
	 * 创建维度条件，根据选择的N维度信息创建N维的步长分量条件，带优化的
	 * 
	 * @param choice
	 * @return
	 */
	public static String[] makeDimensionConditionOptimize(
			List dimensionElement, DataModel dataModel, OptimizeTable opt) {
		if (dimensionElement == null || dimensionElement.size() == 0) {
			return new String[0];
		}

		// 获得原子钻取句柄
		DigLevelAtomSQL digLevelAtomSQL = (DigLevelAtomSQL) BiEntry
				.fetchBi("digLevelAtomSQL");

		List allDimensionAtomElement = new ArrayList();
		int maxLength = 0;

		for (Iterator itr = dimensionElement.iterator(); itr.hasNext();) {
			DimensionElement elem = (DimensionElement) itr.next();

			String dimid = elem.getId();
			DimColumn dimColumn = (DimColumn) dataModel.getDimColumns().get(
					dimid);

			String[] nodes = elem.getChoicenode();
			String[] nodeAtomExpress = new String[nodes.length];
			String choiceLevel = elem.getLevelcolumnid();
			Map aimLevelMap = opt.fetchDimensionmap();
			String aimLevelTmp = (String) aimLevelMap.get(dimid);
			String[] aimLevelInfo = aimLevelTmp.split(",");
			String aimDimId = aimLevelInfo[1];
			String aimDimLevel = aimLevelInfo[0];

			for (int i = 0; i < nodes.length; i++) {

				if (TreeModel._TIME_TREE[0][0].equals(dimColumn.getTreeModel())) {
					nodeAtomExpress[i] = MakeInExpression.makeInExpression(
							new String[] { nodes[i] }, aimDimId, dimColumn
									.getSqltype());
				} else if (TreeModel._SQL_TREE[0][0].equals(dimColumn
						.getTreeModel())) {

					String[] obj = digLevelAtomSQL
							.fetchAtom(dataModel.getModelid(), elem
									.getId(),
									new String[] { nodes[i] }, choiceLevel,
									aimDimLevel);
					nodeAtomExpress[i] = MakeInExpression.makeInExpression(obj,
							aimDimId, dimColumn.getSqltype());
				} else {
					throw new RuntimeException("无效的TreeModel："
							+ dimColumn.getTreeModel());
				}
			}
			if (nodes.length > maxLength) {
				maxLength = nodes.length;
			}
			allDimensionAtomElement.add(nodeAtomExpress);
		}
		// 全部维度的原子数组
		String[][] array = (String[][]) allDimensionAtomElement
				.toArray(new String[0][maxLength]);
		// 获得N维度空间构建器
		NDimension nDimensionCondition = (NDimension) BiEntry
				.fetchBi("nDimension");
		// 根据全部维度的原子数组,创建N维空间步长条件
		return nDimensionCondition.nDimension(array, " and ");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
