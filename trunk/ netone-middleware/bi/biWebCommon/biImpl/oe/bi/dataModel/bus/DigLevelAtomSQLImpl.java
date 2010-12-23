package oe.bi.dataModel.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.SQLTree;
import oe.bi.dataModel.bus.DigLevelAtomSQL;
import oe.bi.dataModel.bus.util.FlitDimensionValue;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.datasource.DataModelDao;


public class DigLevelAtomSQLImpl implements  DigLevelAtomSQL{

	public String[] fetchAtom(String datamodelid, String dimcolumnid,
			String[] node, String fromLevel, String toLevel) {
		String[] atom = fetchAtomCore(datamodelid, dimcolumnid, node,
				fromLevel, toLevel);
		return FlitDimensionValue.filtDimensionValue(atom);
	}

	private String[] fetchAtomCore(String datamodelId, String dimcolumnId,
			String[] nodeid, String fromLevel, String toLevel) {

		DataModelDao datamodeldao = (DataModelDao) BiEntry
				.fetchBi("dataModelDao");

		try {
			DataModel datamodel = datamodeldao.fetchDataModel(datamodelId);
			Map map = datamodel.getDimColumns();
			String treeinfo = ((DimColumn) map.get(dimcolumnId))
					.getExtendattribute();
			List listDig = new ArrayList();
			digSQLAtomCore(datamodelId, dimcolumnId, nodeid, Integer
					.parseInt(fromLevel), Integer.parseInt(toLevel), treeinfo,
					listDig);
			return (String[]) listDig.toArray(new String[0]);

		} catch (UnableLoadDataModel e) {
			e.printStackTrace();
			return null;
		}

	}

	private void digSQLAtomCore(String datamodelId, String dimcolumnId,
			String[] nodeid, int level, int toLevel, String treeinfo,
			List listBuf) {
		SQLTree sqlTree = (SQLTree) BiEntry.fetchBi("sqlTree");
		for (int i = 0; i < nodeid.length; i++) {
			String[] nodeNext = sqlTree.fetchElement(datamodelId, dimcolumnId,
					nodeid[i], treeinfo, level);
			if (level == toLevel) {
				listBuf.add(nodeid[i]);
			} else {
				digSQLAtomCore(datamodelId, dimcolumnId, nodeNext, ++level,
						toLevel, treeinfo, listBuf);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
