package oe.bi.datasource.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;


import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.DimColumn;
import oe.bi.dataModel.obj.ext.TreeModel;
import oe.bi.datasource.ConditionAdapter;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;

public class ConditionAdapterMysqlImpl implements ConditionAdapter {
	static String _TIME_MODEL = "yyyy-mm-dd hh24";

	static String _TIME_BEFORE = "#[0-9]+#";

	static String _TIME_BETWEEN = "#[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}#";

	public String makeDimensionCondition(List dimensionElement,
			DataModel dataModel, ChoiceInfo cho) {
		if (dimensionElement == null || dimensionElement.size() == 0) {
			return "";
		}

		List allDimensionAtomElement = new ArrayList();
		int maxLength = 0;
		StringBuffer butAll = new StringBuffer();
		for (Iterator itr = dimensionElement.iterator(); itr.hasNext();) {
			DimensionElement elem = (DimensionElement) itr.next();
			String dimid = elem.getId();
			DimColumn dimColumn = (DimColumn) dataModel.getDimColumns().get(
					dimid);
			String treeModel = null;
			if (dimColumn == null) {
				throw new RuntimeException(dimid + "Ŀ��ָ�����в����ڸ��ֶ�");
			} else {
				treeModel = dimColumn.getTreeModel();
			}
			String[] nodes = elem.getChoicenode();
			String[] nodeAtomExpress = new String[nodes.length];

			if (TreeModel._TIME_TREE[0][0].equals(treeModel)) {// ʱ������������ȡ��ԭ��
				String level = elem.getLevelcolumnid();
				String strContime = "";
				// ��� ʱ��ѡ��ģʽ�е� ��Ե�ǰ��ǰN��ʱ��ķ���
				if (nodes != null && nodes.length == 1
						&& nodes[0].matches(_TIME_BEFORE)) {

					strContime = dealwithTimeBefore(nodes[0], level, dimid);

				} else if (nodes != null && nodes.length == 2
						&& nodes[0].matches(_TIME_BETWEEN)) {
					strContime = dealwithTimeBetween(nodes[0], nodes[1], level,
							dimid);
				} else {
					strContime = dealwithTimePoint(nodes, dimid);
				}

				butAll.append(strContime);

			} else if (TreeModel._DATA_LIST[0][0].equals(treeModel)) {
				String rscondition = "";
				// ά�ȴ���3����ʽ
				if (cho.isDynamicDim()) {// ��̬ά��,���ɸ��ݷ��������л��
					rscondition = " 1=1 ";
				} else if (cho.getXlevel() != null
						&& !"".equals(cho.getXlevel())) { // ���ά�ȣ����ݲ�����Զ�ѡ���������µ�ά��
					// ����Ҫ�����ѡ���ʱ��,��Ҫ���������
					StringBuffer but1 = new StringBuffer();
					for (int i = 0; i < nodes.length; i++) {
						String nodex = StringUtils.substringBetween(nodes[i],
								"[", "]");
						nodex = "%" + nodex + "%";
						but1.append(" or " + dimid + " like '" + nodex + "'");
					}
					
					rscondition = "(" + but1.substring(3) + ")";

				} else {// �̶�ά��

					// ��ͨѡ��ڵ�
					StringBuffer but1 = new StringBuffer(" " + dimid + " in (");
					for (int i = 0; i < nodes.length - 1; i++) {
						but1.append("'" + nodes[i] + "',");
					}
					but1.append("'" + nodes[nodes.length - 1] + "')");
					rscondition = but1.toString();

				}
				butAll.append(" and " + rscondition);
			} else {
				throw new RuntimeException("��ЧTreeModel����");

			}

			if (nodes.length > maxLength) {
				maxLength = nodes.length;
			}
			allDimensionAtomElement.add(nodeAtomExpress);
		}

		return butAll.toString();
	}

	// �ο�֤ȯ����ϵͳ�еĵ�ǰ���� ʱ��ѡ��
	private String dealwithTimeBefore(String node, String level, String dimid) {
		long eachTime = 0;
		int lentime = 13;
		if ("1h".equals(level)) {
			eachTime = 3600000;
		} else if ("1d".equals(level)) {
			eachTime = 86400000;
			lentime = 10;
		}

		String beforeNum = StringUtils.substringBetween(node, "#", "#");
		int beforeNumValue = Integer.parseInt(beforeNum);
		long currenttime = System.currentTimeMillis();

		long beforetime = currenttime - eachTime * beforeNumValue;

		String beforetimeStr = (new Timestamp(beforetime)).toString()
				.substring(0, lentime);
		String newtimeStr = (new Timestamp(currenttime)).toString().substring(
				0, lentime);

		return " and " + dimid + " between  '" + beforetimeStr + "' and '"
				+ newtimeStr + "'";

	}

	// ʱ�䷶Χ-�ͻ�֧���е�Ӧ��
	private String dealwithTimeBetween(String node1, String node2,
			String level, String dimid) {
		String fromTime = StringUtils.substringBetween(node1, "#", "#");
		String toTime = StringUtils.substringBetween(node2, "#", "#");
		return " and " + dimid + " between '" + fromTime + "' and '" + toTime
				+ "'";
	}

	// ʱ���-��ͳ����Ԫ����Ӧ��
	private String dealwithTimePoint(String[] nodes, String dimid) {
		StringBuffer but2 = new StringBuffer();
		for (int i = 0; i < nodes.length; i++) {
			but2.append(",'" + nodes[i] + "'");
		}
		return " and " + dimid + " in (" + but2.substring(1) + ")";
	}
}
