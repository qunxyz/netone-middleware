package oe.bi.web.etl.util;

import java.util.List;
import java.util.Map;

import oe.bi.common.PropertiesConf;
import oe.bi.dao.ui.obj.NodeObj;



public class AutoDigSQL implements AutoDig {

	static PropertiesConf conf = new PropertiesConf("analyse.properties");

	/**
	 * ����ĳ����Ԫ�ڵ�֮�µĵ�N������нڵ����,����:����(REGIN�µ�����CELL��Ԫ)
	 * 
	 * @param objnode
	 * @return
	 */
	public int countCell(NodeObj objnode) {

		// // ��ǰ��λ��
		// String current_level = objnode.getLevelname();
		//
		// String digto_level = objnode.getColumnname();
		// String fetchSql = digto_level + "." + current_level + ".sql";
		// String limitSQl = conf.getProperty(fetchSql);
		// limitSQl = limitSQl.replaceAll("%name%", objnode.getNodeid());
		// limitSQl = limitSQl.replaceAll("%id%", objnode.getNodeid());
		// System.out.println(limitSQl);
		// return DbTools.countData(limitSQl);
		return 0;
	}
}
