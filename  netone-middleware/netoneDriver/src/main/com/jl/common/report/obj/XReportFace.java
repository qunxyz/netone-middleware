package com.jl.common.report.obj;

import java.util.List;
import java.util.Map;

public class XReportFace {

	double width=400;

	String stylename="CSSFILE.CSSFILE.RPSTYLE";

	Map<String, QueryColumn> columns;

	Map<String, Report> reports; //

	Map<String, List<ReportQueryRef>> rq;

	public Map<String, QueryColumn> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, QueryColumn> columns) {
		this.columns = columns;
	}

	public Map<String, Report> getReports() {
		return reports;
	}

	public void setReports(Map<String, Report> reports) {
		this.reports = reports;
	}

	public Map<String, List<ReportQueryRef>> getRq() {
		return rq;
	}

	public void setRq(Map<String, List<ReportQueryRef>> rq) {
		this.rq = rq;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public String getStylename() {
		return stylename;
	}

	public void setStylename(String stylename) {
		this.stylename = stylename;
	}

}
