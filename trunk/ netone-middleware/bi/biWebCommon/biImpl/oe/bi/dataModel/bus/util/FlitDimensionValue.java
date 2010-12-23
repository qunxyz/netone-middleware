package oe.bi.dataModel.bus.util;

import java.util.ArrayList;
import java.util.List;

import oe.bi.TimeTree;
import oe.bi.dataModel.bus.DigTreeBuilder;
import oe.bi.dataModel.obj.ext.TreeModel;


public class FlitDimensionValue {

	/**
	 * 在钻取树上，节点的ID是保存在节点的NodeId上的，其中最后一个分割符号_NODE_KEY_SPLIT后的值是 该节点的真实值
	 * 
	 * @param atom
	 * @return
	 */
	public static String[] filtDimensionValue(String[] atom) {
		List atomlist = new ArrayList();
		for (int i = 0; i < atom.length; i++) {
			String atomPre = atom[i];

			String[] atomPreArr = atomPre.split(",");
			StringBuffer buf = new StringBuffer();
			for (int j = 0; j < atomPreArr.length; j++) {
				buf.append("," + dealwithPreAtom(atomPreArr[j]));
			}
			atomlist.add(buf.toString().substring(1));
		}
		return (String[]) atomlist.toArray(new String[0]);
	}

	private static String dealwithPreAtom(String atomPre) {
		if (atomPre != null
				&& atomPre.indexOf(DigTreeBuilder._NODE_KEY_SPLIT) > 0) {
			atomPre = atomPre.substring(0, atomPre.length()
					- DigTreeBuilder._NODE_KEY_SPLIT_LENGTH);
			int lastIndex = atomPre.lastIndexOf(DigTreeBuilder._NODE_KEY_SPLIT);
			if (lastIndex != -1) {
				return atomPre.substring(lastIndex
						+ DigTreeBuilder._NODE_KEY_SPLIT_LENGTH);
			}
		}
		return atomPre;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
