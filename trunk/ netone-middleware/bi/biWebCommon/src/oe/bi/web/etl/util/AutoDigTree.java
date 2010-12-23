package oe.bi.web.etl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.datasource.DimensionLoader;


public class AutoDigTree implements AutoDig{

	public  void digTree(NodeObj objnode, int loop, List value) {
		List nodeList = eachTreeElementList(objnode);
		if (loop != 1 && nodeList != null && nodeList.size() > 0) {
			for (int i = 0; i < nodeList.size(); i++) {
				digTree(objnode, loop - 1, value);
			}
		}
		if (loop == 1) {
			value.add(new Integer(nodeList.size()));
		}
	}

	public  List eachTreeElementList(NodeObj objnode) {
		DimensionLoader dl = (DimensionLoader)BiEntry.fetchBi("dimensionLoader");
		return dl.loadDimension(objnode.getTreeModelId(), objnode);
	}

	/**
	 * 计算某个网元节点之下的第N层的所有节点个数,比如:福州(REGIN下的所有CELL网元)
	 * 
	 * @param objnode
	 * @return
	 */
	public  int countCell(NodeObj objnode) {
		Map map = new HashMap();
		map.put("REGION", new Integer(5));
		map.put("MSC", new Integer(4));
		map.put("BSC", new Integer(3));
		map.put("BTS", new Integer(2));
		map.put("CELL", new Integer(1));
		map.put("CARRIER", new Integer(0));

		// 当前的位置
		String current_level = objnode.getLevelname();
		int current_level_num = ((Integer) map.get(current_level)).intValue();

		// 需要自动专取到的位置
		String digto_level = objnode.getColumnname();
		int digto_level_num = ((Integer) map.get(digto_level)).intValue();

		// 需要自动专取的层次
		int loop = current_level_num - digto_level_num;

		// 开始自动专取,将最后一层的信息保存在listFinallValue中返回
		List listFinallValue = new ArrayList();
		digTree(objnode, loop, listFinallValue);

		// 获得最后一层的节点总数
		int totalcount = 0;
		for (Iterator iter = listFinallValue.iterator(); iter.hasNext();) {
			Integer element = (Integer) iter.next();
			totalcount += element.intValue();
		}
		return totalcount;
	}

}
