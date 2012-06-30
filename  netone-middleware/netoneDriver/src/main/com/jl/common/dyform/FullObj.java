package com.jl.common.dyform;

public class FullObj {
	private String columnname;
	private String columnid;
	private String columnorder;
	private String xoffset;//查询字段需要重新定义X坐标
	private String yoffset;//查询字段需要重新定义Y坐标
	private String extinfo;//查询字段有需要新的条件
	private String ref;//查询字段有需要引用其他表单

	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getXoffset() {
		return xoffset;
	}
	public void setXoffset(String xoffset) {
		this.xoffset = xoffset;
	}
	public String getYoffset() {
		return yoffset;
	}
	public void setYoffset(String yoffset) {
		this.yoffset = yoffset;
	}
	public String getExtinfo() {
		return extinfo;
	}
	public void setExtinfo(String extinfo) {
		this.extinfo = extinfo;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getColumnid() {
		return columnid;
	}
	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}
	public String getColumnorder() {
		return columnorder;
	}
	public void setColumnorder(String columnorder) {
		this.columnorder = columnorder;
	}

}
