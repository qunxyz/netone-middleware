package oe.bi.dataModel.bus;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.datasource.obj.Datasource;


public interface DigTreeBuilder {

	String _SRC_DATAMODELID = "datamodelid";

	String _SRC_DIMENSIONID = "dimensionid";

	String _SRC_DIMENSIONVALUE = "dimensionvalue";

	String _SRC_ANALYSIS_TYPE = "analysis";

	String _SRC_LEVEL = "level";

	String _NODE_KEY_SPLIT = "M99X";

	// ///////// ���ϵĽڵ� /////////////
	String _NODE_LEVELID = "levelid";

	String _NODE_ONLOAD = "onload";

	String _NODE_TEXT = "text";

	String _NODE_ACTION = "action";

	String _NODE_SRC = "src";

	String _NODE_ID = "id";

	// /////////////////////////////////
	int _NODE_KEY_SPLIT_LENGTH = _NODE_KEY_SPLIT.length();

	/**
	 * ����ά���� �ĸ����裺 1����DimColumn.treemodel 2������ѯSQL 3��ò�ѯ���
	 * 4����ά���ֵ��ת����XML�ĵ��洢��ָ��Ŀ¼��
	 * 
	 * @param datamodel
	 * @param dimColumn
	 */
	void buildTree(DataModel datamodel, Datasource datasour);

}
