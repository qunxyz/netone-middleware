package oe.bi.etl.obj;

import java.util.Map;

public class MergerObj {

	private String index;

	private String tableinfo;

	private Map targetInfo;

	private Map dimensionInfo;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Map getDimensionInfo() {
		return dimensionInfo;
	}

	public void setDimensionInfo(Map dimensionInfo) {
		this.dimensionInfo = dimensionInfo;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getTableinfo() {
		return tableinfo;
	}

	public void setTableinfo(String tableinfo) {
		this.tableinfo = tableinfo;
	}

	public Map getTargetInfo() {
		return targetInfo;
	}

	public void setTargetInfo(Map targetInfo) {
		this.targetInfo = targetInfo;
	}

}
