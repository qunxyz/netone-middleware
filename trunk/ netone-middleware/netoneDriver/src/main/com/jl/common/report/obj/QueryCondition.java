package com.jl.common.report.obj;

import java.util.HashMap;
import java.util.Map;

public class QueryCondition {

	private Map column2Value=new HashMap(); // 字段名和输入的参数值
	
	private Map column2Ope=new HashMap(); // 字段的比较参数，如果不存在默认为等于= 符号

	private String selectReportId; // 查询中选的报表ID

	public Map getColumn2Value() {
		return column2Value;
	}

	public void setColumn2Value(Map column2Value) {
		this.column2Value = column2Value;
	}

	public String getSelectReportId() {
		return selectReportId;
	}

	public void setSelectReportId(String selectReportId) {
		this.selectReportId = selectReportId;
	}

	public Map getColumn2Ope() {
		return column2Ope;
	}

	public void setColumn2Ope(Map column2Ope) {
		this.column2Ope = column2Ope;
	}

}
