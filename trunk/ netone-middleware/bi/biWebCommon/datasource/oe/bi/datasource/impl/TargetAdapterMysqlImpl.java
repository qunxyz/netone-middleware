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

		// 设计逻辑上在分析向导的最后一步中可以对所有的指标设定限制,并且这些限制都是一样的,所以实际上在任意一个dimensionelement中取一个都是可以的
		int limit = 0;
		for (Iterator itr = listX.iterator(); itr.hasNext();) {
			DimensionElement dime = (DimensionElement) itr.next();
			String level = dime.getLevelcolumnid();
			String name = dime.getName();

			// 注：@指名是指标过滤条件,只需要处理排序的逻辑
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
		// 在otherinfo中保存了,门限的条件 和用户自己编辑扩展的条件
		String otherSQLinfo = cho.getOtherinfo();

		return otherSQLinfo + preOrderinfo;
	}
}
