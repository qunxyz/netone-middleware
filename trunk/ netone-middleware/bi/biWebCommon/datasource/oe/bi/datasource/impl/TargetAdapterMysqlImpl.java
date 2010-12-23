package oe.bi.datasource.impl;

import java.util.Iterator;
import java.util.List;

import oe.bi.etl.bus.TargetAdapter;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;


public class TargetAdapterMysqlImpl implements TargetAdapter {

	public String adapt(ChoiceInfo cho, List list) {
		List listX = cho.getDimensionElement();
		StringBuffer but = new StringBuffer();

		// ����߼����ڷ����򵼵����һ���п��Զ����е�ָ���趨����,������Щ���ƶ���һ����,����ʵ����������һ��dimensionelement��ȡһ�����ǿ��Ե�
		int limit = 0;
		for (Iterator itr = listX.iterator(); itr.hasNext();) {
			DimensionElement dime = (DimensionElement) itr.next();
			String level = dime.getLevelcolumnid();
			String name = dime.getName();

			// ע��@ָ����ָ���������,ֻ��Ҫ����������߼�
			if ("@".equals(level)) {
				if (this._TARGET_TYPE_ORDER.equals(name)) {

					if (dime.getChoicenode() != null
							&& dime.getChoicenode().length > 0) {
						try {
							limit = Integer.parseInt(dime.getChoicenode()[0]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (dime.isDesc()) {
						but.append("," + dime.getChoicenodename()[0] + " desc");
					} else {
						but.append("," + dime.getChoicenodename()[0] + " asc");
					}
					String[] node = dime.getChoicenode();
					if (node != null && node.length == 1) {
						if (node[0] != null && !node[0].equals(""))
							limit = Integer.parseInt(node[0]);
					}
				}
			} else {
				list.add(dime);
			}
		}
		String preOrderinfo = "";
		if (but.length() > 0) {
			preOrderinfo = " order by " + but.substring(1);
		}
		if (limit > 0) {
			preOrderinfo = preOrderinfo + " limit 0," + limit;
		}
		// ��otherinfo�б�����,���޵����� ���û��Լ��༭��չ������
		String otherSQLinfo = cho.getOtherinfo();

		return otherSQLinfo + preOrderinfo;
	}
}
