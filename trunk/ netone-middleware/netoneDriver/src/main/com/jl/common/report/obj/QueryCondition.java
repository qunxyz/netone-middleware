package com.jl.common.report.obj;

import java.util.HashMap;
import java.util.Map;

public class QueryCondition {

	private Map column2Value=new HashMap(); // �ֶ���������Ĳ���ֵ
	
	private Map column2Ope=new HashMap(); // �ֶεıȽϲ��������������Ĭ��Ϊ����= ����

	private String selectReportId; // ��ѯ��ѡ�ı���ID

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
