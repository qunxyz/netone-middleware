package oe.bi.web.etl.util;

import java.util.List;
import java.util.Map;

import oe.bi.common.PropertiesConf;
import oe.bi.dao.ui.obj.NodeObj;



public class AutoDigSQL implements AutoDig {

	static PropertiesConf conf = new PropertiesConf("analyse.properties");

	/**
	 * 计算某个网元节点之下的第N层的所有节点个数,比如:福州(REGIN下的所有CELL网元)
	 * 
	 * @param objnode
	 * @return
	 */
	public int countCell(NodeObj objnode) {

		// // 当前的位置
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
