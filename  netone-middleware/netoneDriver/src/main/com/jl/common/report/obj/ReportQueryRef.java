package com.jl.common.report.obj;

public class ReportQueryRef {

	String reportid; // ����

	String refColumnid;// ����Ĺ����ֶνӿ�

	String querycolumnid; // ��ѯ�ֶ�

	String description;
    
	String type;
	
	String bindclr;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBindclr() {
		return bindclr;
	}

	public void setBindclr(String bindclr) {
		this.bindclr = bindclr;
	}

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getRefColumnid() {
		return refColumnid;
	}

	public void setRefColumnid(String refColumnid) {
		this.refColumnid = refColumnid;
	}

	public String getQuerycolumnid() {
		return querycolumnid;
	}

	public void setQuerycolumnid(String querycolumnid) {
		this.querycolumnid = querycolumnid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
