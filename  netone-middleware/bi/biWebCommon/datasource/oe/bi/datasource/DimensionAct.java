package oe.bi.datasource;

import oe.bi.dao.ui.obj.NodeObj;

/**
 * 维度交互逻辑设计
 * 
 * @author chen.jia.xun
 * 
 */
public interface DimensionAct {

	/**
	 * 显示出所有的维度模型树 <br>
	 * 注意：<br>
	 * 维度模型树是文件信息，存储在application的根目录下（比如:tomcat服务器中就是在bin目录下）的bimodel\dimtree
	 * 目录中
	 * 
	 * @return 所有的维度模型树，内容为[0][0]modelTreeId（维度模型树的文件名）,[0][1]modelTreeName
	 */
	String[][] listTree();

	/**
	 * 树图展开过程中的交互，目的用于展开当前节点的下子树信息
	 * 
	 * 注意：<br>
	 * 维度模型树是文件信息，存储在application的根目录下（比如:tomcat服务器中就是在bin目录下）的bimodel\dimtree
	 * 目录中,参数treeModelId是维度模型树的文件名，可以定位到具体的模型树 <br>
	 * 注意：<br>
	 * 树图的具体业务数据，从接口DimensionLoader中的loadDimension()方法中获得
	 * 
	 * @param treeModelId
	 *            树图对应的模型ID
	 * @param curNode
	 *            当前的节点
	 * @return 树图的前端展示xml文档(XML格式与先前的相同，请参考BiTree接口设计描述)
	 */
	String subTree(String treeModelId, NodeObj curNode);

	/**
	 * 时间树
	 * 
	 * @param curNode
	 *            当前的节点
	 * @return 树图的前端展示xml文档(XML格式与先前的相同，请参考BiTree接口设计描述)
	 */
	String timeTree(NodeObj curNode);

}
