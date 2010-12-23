package oe.bi.dataModel.bus.util;

import java.util.ArrayList;
import java.util.List;

import oe.bi.TimeTree;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.dataModel.obj.ext.TreeModel;


public class FlitDimensionValue3 {

	/**
	 * 在钻取树上，节点的ID是保存在节点的NodeId上的，其中最后一个分割符号_NODE_KEY_SPLIT后的值是 该节点的真实值
	 * 
	 * @param atom
	 * @return
	 */
	public static String[] filtDimensionValue(String valuestr) {
		if (valuestr == null) {
			valuestr = "";
		}
		String[] atom = valuestr.split("#");
		if (atom == null || atom.length == 0) {
			return null;
		}

		List atomlist = new ArrayList();
		if (atom.length <= 1) {
			return null;
		}
		for (int i = 1; i < atom.length; i++) {
			String[] valueTmp = atom[i].split(",");
			String[] valueNext = valueTmp[1].split("=");
			atomlist.add(valueNext[1]);
		}
		return (String[]) atomlist.toArray(new String[0]);
	}
}
