package oe.bi.etl.bus.extractcore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.bi.BiEntry;
import oe.bi.dataModel.bus.DigAtom;
import oe.bi.dataModel.bus.DigAtomSQL;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.ext.SqlTypes;
import oe.bi.dataModel.obj.ext.TreeModel;
import oe.bi.etl.bus.arithmetic.NDimension;
import oe.bi.etl.bus.extractcore.util.DimensionColumnModify;
import oe.bi.etl.bus.extractcore.util.MakeInExpression;
import oe.bi.etl.obj.DimensionElement;





public class DimensionCondition {

	/**
	 * ����ά������������ѡ���Nά����Ϣ����Nά�Ĳ�����������
	 * 
	 * @param choice
	 * @return
	 */
	public static String[] makeDimensionCondition(List dimensionElement,
			DataModel dataModel) {
		if (dimensionElement == null || dimensionElement.size() == 0) {
			return new String[0];
		}

		List allDimensionAtomElement = new ArrayList();
		int maxLength = 0;
		for (Iterator itr = dimensionElement.iterator(); itr.hasNext();) {
			DimensionElement elem = (DimensionElement) itr.next();
			String dimid = elem.getId();
			DimColumn dimColumn = (DimColumn) dataModel.getDimColumns().get(
					dimid);
			String treeModel = dimColumn.getTreeModel();
			String sqlType = dimColumn.getSqltype();
			String[] nodes = elem.getChoicenode();
			String[] nodeAtomExpress = new String[nodes.length];
			for (int i = 0; i < nodes.length; i++) {
				if (TreeModel._SQL_TREE[0][0].equals(treeModel)) {
					// ���ԭ����ȡ���
					DigAtomSQL digAtomSQL = (DigAtomSQL) BiEntry
							.fetchBi("digAtomSQL");
					String[] obj = digAtomSQL.fetchAtom(dataModel.getModelid(),
							elem.getId(), new String[] { nodes[i] });
					nodeAtomExpress[i] = MakeInExpression.makeInExpression(obj,
							dimid, sqlType);
				} else if (TreeModel._TIME_TREE[0][0].equals(treeModel)) {// ʱ������������ȡ��ԭ��
					nodeAtomExpress[i] = MakeInExpression.makeInExpression(
							new String[] { nodes[i] }, dimid, sqlType);
				} else if (TreeModel._DATA_LIST[0][0].equals(treeModel)) {
					nodeAtomExpress[i] = MakeInExpression.makeInExpression(// �б�����������ȡ��ԭ��
							new String[] { nodes[i] }, dimid, sqlType);
				} else {
					throw new RuntimeException("��ЧTreeModel����");
					// // ���ԭ����ȡ���
					// DigAtom digAtom = (DigAtom) BiEntry.fetchBi("digAtom");
					// String[] obj = digAtom.fetchAtom(dataModel.getModelid(),
					// elem.getDimcolumnid(), nodes[i]);
					// nodeAtomExpress[i] =
					// MakeInExpression.makeInExpression(obj,
					// dimid, sqlType);
				}
			}
			if (nodes.length > maxLength) {
				maxLength = nodes.length;
			}
			allDimensionAtomElement.add(nodeAtomExpress);
		}
		// ȫ��ά�ȵ�ԭ������
		String[][] array = (String[][]) allDimensionAtomElement
				.toArray(new String[0][maxLength]);
		// ���Nά�ȿռ乹����
		NDimension nDimensionCondition = (NDimension) BiEntry
				.fetchBi("nDimension");
		// ����ȫ��ά�ȵ�ԭ������,����Nά�ռ䲽������
		return nDimensionCondition.nDimension(array, " and ");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
