package oe.bi.dataModel.bus;

import oe.bi.exceptions.TreeModelException;
import oe.bi.view.obj.GraphModel;


public interface ActionDigTree {

	String _ROOT_NODE = "root";

	/**
	 * 根据节点动态展开树，与WEB上的树形结构交互使用
	 * 
	 * @param datamodelId
	 * @param dimcolumnId
	 * @param nodeid
	 * @return
	 */
	String fetchTreeElement(String datamodelId, String dimcolumnId,
			String nodeid, String levels) throws TreeModelException;

	/**
	 * 根据节点动态展开树，用于预测分析中选择节点使用
	 * 
	 * @param datamodelId
	 * @param dimcolumnId
	 * @param nodeid
	 * @return
	 */
	String fetchTreeElement(String datamodelId, String dimcolumnId,
			String dimensionlevle, String nodeid, String levels,
			GraphModel graphModel) throws TreeModelException;
}