package oe.bi.datasource;

import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;
/**
 * 维度展现数据逻辑设计
 * 
 * @author chen.jia.xun
 * 
 */

public interface DimensionLoader {
	/**
	 * 装载树图展现所需要的业务数据<br>
	 * 注意：<br>
	 * 该业务数据只有一个层的线性数据（非层次性，也就是说只是树图中的一个层的信息）<br>
	 * 注意:<br>
	 * 数据的装载模式可以有多种比如:SQL数据源，RMI服务...... 这些装载模式的具体固定参数 配置，位于维度模型树是文件夹中的
	 * datasource.properites文件中配置
	 * 
	 * @param param
	 *            modeltreeid 模型树的ID（也就是文件名）
	 * @param param
	 *            nodeid 当前树结点
	 * 
	 * 
	 * @return List 代表着一个层中的N个NodeObj对象的信息
	 */
	List loadDimension(String modeltreeid, NodeObj node);

	/**
	 * 获所有的子节点
	 * 
	 * @param view
	 * @param node
	 * @param allLevelname
	 * @return
	 */
	List getAllDimension(String view, NodeObj node, String allLevelname);

}
