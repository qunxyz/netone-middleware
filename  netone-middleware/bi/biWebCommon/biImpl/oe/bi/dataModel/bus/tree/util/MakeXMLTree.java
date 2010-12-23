package oe.bi.dataModel.bus.tree.util;

import oe.bi.BiTree;

import org.apache.commons.lang.StringUtils;


public class MakeXMLTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String makeXMLInfo(String datamodelid, String columnid,
			String keyid, String keyName, String levelid) {
		String preInfo = StringUtils.replace(BiTree._TREE_XML,
				BiTree._TREE_XML_KEY[0], keyid);
		preInfo = StringUtils
				.replace(preInfo, BiTree._TREE_XML_KEY[1], keyName);
		preInfo = StringUtils.replace(preInfo, BiTree._TREE_XML_KEY[3],
				datamodelid);
		preInfo = StringUtils.replace(preInfo, BiTree._TREE_XML_KEY[2],
				columnid);
		preInfo = StringUtils
				.replace(preInfo, BiTree._TREE_XML_KEY[4], levelid);
		return preInfo;
	}

}
