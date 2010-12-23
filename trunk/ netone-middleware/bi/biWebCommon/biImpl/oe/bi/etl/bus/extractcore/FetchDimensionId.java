package oe.bi.etl.bus.extractcore;

import java.util.Iterator;
import java.util.List;

import oe.bi.etl.obj.DimensionElement;


public class FetchDimensionId {

	public static String[] fetchDimensionIdInfo(List list) {
		String[] dimid = new String[list.size()];
		int i = 0;
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			DimensionElement dimele = (DimensionElement) itr.next();
			dimid[i++] = dimele.getId();
		}
		return dimid;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
