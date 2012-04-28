package com.jl.common.report.obj.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Getdatalist {

	Map recorddatalist = new HashMap();
	List columnslist;

	public Map getRecorddatalist() {
		return recorddatalist;
	}

	public void setRecorddatalist(Map recorddatalist) {
		this.recorddatalist = recorddatalist;
	}

	public List getColumnslist() {
		return columnslist;
	}

	public void setColumnslist(List columnslist) {
		this.columnslist = columnslist;
	}

}
