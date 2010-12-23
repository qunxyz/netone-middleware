package oe.bi.dataModel.bus.tree.util;

import oe.bi.BiTree2;

import org.apache.commons.lang.StringUtils;


public class MakeXMLTree2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String makeXMLInfo(String modelid, String columnname,
			String keyid, String keyName, String levelid) {
		String preInfo = StringUtils.replace(BiTree2._TREE_XML,
				BiTree2._TREE_XML_KEY[0], modelid);
		preInfo = StringUtils.replace(preInfo, BiTree2._TREE_XML_KEY[1], keyid);
		preInfo = StringUtils.replace(preInfo, BiTree2._TREE_XML_KEY[2],
				keyName);
		preInfo = StringUtils.replace(preInfo, BiTree2._TREE_XML_KEY[3],
				levelid);
		preInfo = StringUtils.replace(preInfo, BiTree2._TREE_XML_KEY[4],
				columnname);

		return preInfo;
	}

	public static String makeXMLTimeInfo(String modelid, String keyid,
			String keyName, String levelid) {
		String preInfo = StringUtils.replace(BiTree2._TREETIME_XML,
				BiTree2._TREE_XML_KEY[0], modelid);
		preInfo = StringUtils.replace(preInfo, BiTree2._TREE_XML_KEY[1], keyid);
		preInfo = StringUtils.replace(preInfo, BiTree2._TREE_XML_KEY[2],
				keyName);
		preInfo = StringUtils.replace(preInfo, BiTree2._TREE_XML_KEY[3],
				levelid);

		return preInfo;
	}

}
