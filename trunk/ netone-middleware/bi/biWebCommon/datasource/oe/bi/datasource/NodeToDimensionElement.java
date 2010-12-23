package oe.bi.datasource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.bi.dao.ui.obj.NodeObj;
import oe.bi.etl.obj.DimensionElement;


/**
 * NodeObj �� DimensionElement�� �������
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class NodeToDimensionElement {
	/**
	 * ��node�����б�ת����DimensionElementԪ��
	 * 
	 * @param node
	 *            node�����б� ���汣����NodeObj����
	 * @return
	 */
	static public DimensionElement nodeToDim(List node) {
		DimensionElement dime = new DimensionElement();
		if (node == null && node.size() == 0) {
			return dime;
		}
		// ���ˮƽֵ���ֶ�-�������е�nodeԪ����˵��������������һ�µģ���Ϊ���е�node�����Ǿ߱���ͬˮƽֵ����ͬ�ֶ���
		String level = ((NodeObj) node.get(0)).getLevelname();
		String column = ((NodeObj) node.get(0)).getColumnname();
		dime.setLevelcolumnid(level);
		dime.setId(column);

		// ���λ��ÿ��node��id �� name ������id��Ӧ�ľ���node��ֵ��Ϣ
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
