package com.jl.common.report.obj;

public class QueryColumn {

	private String columnid;

	private String columnname;
	// 参考XReportIfc中的 _TYPE
	private String columntype;

	private int xoffset;

	private int yoffset;

	private String description;

	private String defaultvalue;

	private boolean isAnd;
	
	private String opesel=">-大于,<-小于,=-等于,>=-大等于,<=-小等于,!=-不等于";

	public boolean isAnd() {
		return isAnd;
	}

	public void setAnd(boolean isAnd) {
		this.isAnd = isAnd;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getColumntype() {
		return columntype;
	}

	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}

	public int getXoffset() {
		return xoffset;
	}

	public void setXoffset(int xoffset) {
		this.xoffset = xoffset;
	}

	public int getYoffset() {
		return yoffset;
	}

	public void setYoffset(int yoffset) {
		this.yoffset = yoffset;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOpesel() {
		return opesel;
	}

	public void setOpesel(String opesel) {
		this.opesel = opesel;
	}

}
