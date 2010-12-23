package oe.bi.web.view.tree;

import java.util.List;

public interface XmlTreeDataGeter {
	
	/**
	 * 获取树图节点的子节点
	 * @param condition 条件，自行定义结构，一般该节点的id是必要的条件
	 * @return
	 */
	public List getChildrenById(String condition); 
	
	
	/**
	 * 根据节点的id获得节点相应的对象。 
	 * @param id
	 * @return
	 */
	public Object getNodeObjByid(String id);

}
