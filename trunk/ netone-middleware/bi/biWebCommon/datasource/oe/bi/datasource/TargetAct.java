package oe.bi.datasource;

import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;


/**
 * 指标交互逻辑设计
 * 
 * @author chen.jia.xun
 * 
 */
public interface TargetAct {
	/**
	 * 获得指标组<br>
	 * 通过参数参数node对象中的levelname，到统一指标配置文件中寻找所有 ObjectType为levelname的元素， 然后返回
	 * 相关的名字和ID <br>
	 * 注意：<br>
	 * 统一指标配置文件，存储在application的根目录下（比如:tomcat服务器中就是在bin目录下）的bimodel\ 目录中 <br>
	 * 注意:<br>
	 * 参数node对象是所有维度选择结果中的任意一个node（因为每次选择的维度结果中所有的node节点的levelname都是一样的）
	 * 既然这样我们做一个统一约束，这个node就是取所有维度选择结果中的第一个node
	 * 
	 * @param dimType
	 *            维度类型ID
	 * @return 所有的指标组，内容为[0][0]targetId,[0][1]targetName
	 */

	String[][] targetGroupList(NodeObj node);

	/**
	 * 根据指标组ID获得,该指标组中的所有所有的指标元素
	 * 
	 * 注意：<br>
	 * 统一指标配置文件，存储在application的根目录下（比如:tomcat服务器中就是在bin目录下）的bimodel\ 目录中 <br>
	 * 注意:<br>
	 * 指标元素中可能存在维度元素，请根据具体的标志位说明，区分之
	 * 
	 * @param targetGroupid
	 *            指标组ID
	 * @return 指标元素数组,其中每个元素为TargetObj对象
	 */
	List targetElementList(String targetGroupid);

}
