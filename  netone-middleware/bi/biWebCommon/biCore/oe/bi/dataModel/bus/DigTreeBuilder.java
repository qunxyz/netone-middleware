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

	// ///////// 树上的节点 /////////////
	String _NODE_LEVELID = "levelid";

	String _NODE_ONLOAD = "onload";

	String _NODE_TEXT = "text";

	String _NODE_ACTION = "action";

	String _NODE_SRC = "src";

	String _NODE_ID = "id";

	// /////////////////////////////////
	int _NODE_KEY_SPLIT_LENGTH = _NODE_KEY_SPLIT.length();

	/**
	 * 创建维度树 四个步骤： 1解析DimColumn.treemodel 2构建查询SQL 3获得查询结果
	 * 4将二维结果值，转换成XML文档存储到指定目录下
	 * 
	 * @param datamodel
	 * @param dimColumn
	 */
	void buildTree(DataModel datamodel, Datasource datasour);

}
