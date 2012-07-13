package com.jl.web.controller;

import java.util.List;

import com.jl.common.workflow.DbTools;

public class ReportBaseData {

	/**
	 * 分销商信息
	 * 
	 * @return
	 */
	public static List getFClientInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537483  where column19='1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

}
