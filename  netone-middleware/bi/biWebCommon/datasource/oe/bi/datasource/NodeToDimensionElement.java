package oe.bi.datasource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.etl.obj.DimensionElement;


/**
 * NodeObj 与 DimensionElement的 适配程序
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class NodeToDimensionElement {
	/**
	 * 将node对象列表，转换成DimensionElement元素
	 * 
	 * @param node
	 *            node对象列表 里面保存多个NodeObj对象
	 * @return
	 */
	static public DimensionElement nodeToDim(List node) {
		DimensionElement dime = new DimensionElement();
		if (node == null && node.size() == 0) {
			return dime;
		}
		// 获得水平值和字段-对于所有的node元素来说这两个参数都是一致的，因为所有的node必须是具备相同水平值和相同字段名
		String level = ((NodeObj) node.get(0)).getLevelname();
		String column = ((NodeObj) node.get(0)).getColumnname();
		dime.setLevelcolumnid(level);
		dime.setId(column);

		// 依次获得每个node的id 和 name ，其中id对应的就是node的值信息
		List listid = new ArrayList();
		List listname = new ArrayList();
		for (Iterator itr = node.iterator(); itr.hasNext();) {
			NodeObj nodePre = (NodeObj) itr.next();
			String nodeid = nodePre.getNodeid();
			String nodename = nodePre.getNodename();
			listid.add(nodeid);
			listname.add(nodename);
		}
		String[] listidArr = (String[]) listid.toArray(new String[0]);
		String[] listnameArr = (String[]) listname.toArray(new String[0]);
		dime.setChoicenode(listidArr);
		dime.setChoicenodename(listnameArr);
		return dime;
	}
}
