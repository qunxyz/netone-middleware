package com.jl.common.report.obj.core;

import java.util.List;

public class Read_report {

	private List<Read_columns> columnslist;
	private List<Read_dataset> datasetlist;
	private List<Read_label> lablelist;
	private List<Read_table> tablelist;
	private List<Read_record> recordlist;
	public List<Read_columns> getColumnslist() {
		return columnslist;
	}
	public void setColumnslist(List<Read_columns> columnslist) {
		this.columnslist = columnslist;
	}
	public List<Read_dataset> getDatasetlist() {
		return datasetlist;
	}
	public void setDatasetlist(List<Read_dataset> datasetlist) {
		this.datasetlist = datasetlist;
	}
	public List<Read_label> getLablelist() {
		return lablelist;
	}
	public void setLablelist(List<Read_label> lablelist) {
		this.lablelist = lablelist;
	}
	public List<Read_table> getTablelist() {
		return tablelist;
	}
	public void setTablelist(List<Read_table> tablelist) {
		this.tablelist = tablelist;
	}
	public List<Read_record> getRecordlist() {
		return recordlist;
	}
	public void setRecordlist(List<Read_record> recordlist) {
		this.recordlist = recordlist;
	}
	
}
