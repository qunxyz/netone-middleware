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
	 * ����ĳ����Ԫ�ڵ�֮�µĵ�N������нڵ����,����:����(REGIN�µ�����CELL��Ԫ)
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

		// ��ǰ��λ��
		String current_level = objnode.getLevelname();
		int current_level_num = ((Integer) map.get(current_level)).intValue();

		// ��Ҫ�Զ�רȡ����λ��
		String digto_level = objnode.getColumnname();
		int digto_level_num = ((Integer) map.get(digto_level)).intValue();

		// ��Ҫ�Զ�רȡ�Ĳ��
		int loop = current_level_num - digto_level_num;

		// ��ʼ�Զ�רȡ,�����һ�����Ϣ������listFinallValue�з���
		List listFinallValue = new ArrayList();
		digTree(objnode, loop, listFinallValue);

		// ������һ��Ľڵ�����
		int totalcount = 0;
		for (Iterator iter = listFinallValue.iterator(); iter.hasNext();) {
			Integer element = (Integer) iter.next();
			totalcount += element.intValue();
		}
		return totalcount;
	}

}
