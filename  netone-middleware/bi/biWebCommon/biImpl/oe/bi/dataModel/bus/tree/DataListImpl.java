package oe.bi.dataModel.bus.tree;

import java.util.StringTokenizer;

import oe.bi.BiTree;
import oe.bi.DataList;
import oe.bi.dataModel.bus.tree.util.MakeXMLTree;


public class DataListImpl implements DataList, BiTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String fetchDataInfo(String datamodelid, String columnid,
			String datainfo, int level) {
		if (level > 0) {
			return HEAD_XML + END_XML;
		}
		if (datainfo == null || datainfo.equals("")) {
			return HEAD_XML + END_XML;
		}
		String[] datainfos = datainfo.split(";");
		if (datainfos.length == 0) {
			return HEAD_XML + END_XML;
		}
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < datainfos.length; i++) {
			String[] keyName = new String[2];
			StringTokenizer st = new StringTokenizer(datainfos[i], "[");
			if (st.hasMoreTokens()) {
				StringTokenizer stNext = new StringTokenizer(st.nextToken(),
						"]");
				if (stNext.hasMoreTokens()) {
					keyName[0] = stNext.nextToken();
				}
			}
			if (st.hasMoreTokens()) {
				StringTokenizer stNext = new StringTokenizer(st.nextToken(),
						"]");
				if (stNext.hasMoreTokens()) {
					keyName[1] = stNext.nextToken();
				}
			}
			String xmlinfo = MakeXMLTree.makeXMLInfo(datamodelid, columnid,
					keyName[0], keyName[1], "2");
			buf.append(xmlinfo);
		}

		// TODO Auto-generated method stub
		return HEAD_XML + buf.toString() + END_XML;
	}

	public String[] fetchDataElement(String datamodelid, String columnid,
			String datainfo, int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
