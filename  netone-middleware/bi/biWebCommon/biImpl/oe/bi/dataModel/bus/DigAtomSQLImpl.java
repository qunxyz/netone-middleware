package oe.bi.dataModel.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.SQLTree;
import oe.bi.dataModel.bus.DigAtomSQL;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.dataModel.bus.util.FlitDimensionValue;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.datasource.DataModelDao;


public class DigAtomSQLImpl implements DigAtomSQL {

	public String[] fetchAtom(String datamodelid, String dimcolumnid,
			String[] node) {
		String[] atom = fetchAtomCore(datamodelid, dimcolumnid, node);
		return FlitDimensionValue.filtDimensionValue(atom);
	}

	private String[] fetchAtomCore(String datamodelId, String dimcolumnId,
			String[] nodeid) {

		DataModelDao datamodeldao = (DataModelDao) BiEntry
				.fetchBi("dataModelDao");

		String level = fetchNodeLevel(nodeid[0]);
		try {
			DataModel datamodel = datamodeldao.fetchDataModel(datamodelId);
			Map map = datamodel.getDimColumns();
			String treeinfo = ((DimColumn) map.get(dimcolumnId))
					.getExtendattribute();
			List listDig = new ArrayList();
			digSQLAtomCore(datamodelId, dimcolumnId, nodeid, Integer
					.parseInt(level), treeinfo, listDig);
			return (String[]) listDig.toArray(new String[0]);

		} catch (UnableLoadDataModel e) {
			e.printStackTrace();
			return null;
		}

	}

	private void digSQLAtomCore(String datamodelId, String dimcolumnId,
			String[] nodeid, int level, String treeinfo, List listBuf) {
		SQLTree sqlTree = (SQLTree) BiEntry.fetchBi("sqlTree");
		for (int i = 0; i < nodeid.length; i++) {
			String[] nodeNext = sqlTree.fetchElement(datamodelId, dimcolumnId,
					nodeid[i], treeinfo, level);
			if (nodeNext == null || nodeNext.length == 0) {
				listBuf.add(nodeid[i]);
			} else {
				digSQLAtomCore(datamodelId, dimcolumnId, nodeNext, ++level,
						treeinfo, listBuf);
			}
		}
	}

	private String fetchNodeLevel(String node) {
		if (node == null || "".equals(node)) {
			return null;
		}
		String[] nodearr = node.split(DigTreeBuilder._NODE_KEY_SPLIT);
		return String.valueOf(nodearr.length);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
