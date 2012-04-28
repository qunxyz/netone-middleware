package com.jl.common.report;

import javax.servlet.http.HttpServletResponse;

import com.jl.common.report.obj.QueryCondition;

import com.jl.common.report.obj.XReportFace;

public interface XReportIfc {
	// 报表查找字段类型
	String _TYPE[][] = { { "01", "时间" }, { "02", "日期" }, { "03", "日期时间" },
			{ "04", "字符" }, { "05", "数字" }, { "06", "时间范围" }, { "07", "日期范围" },
			{ "08", "日期时间范围" }, { "09", "数字范围" }, { "10", "时间比较" },
			{ "11", "数值比较" }, { "12", "单选人员" }, { "13", "多选人员" },
			{ "14", "单选目录" }, { "15", "多选目录" }, { "16", "单选资源" },
			{ "17", "多选资源" }, { "18", "下拉" }, { "19", "日期比较" },
			{ "20", "日期时间比较" },{"21","字符串范围"},{"22","模糊查询"} };

	/**
	 * 构造报表查询界面
	 * 
	 * @param naturalname
	 * @return
	 */
	XReportFace buildFace(String naturalname);

	/**
	 * 构造报表
	 * 
	 * @param naturalname
	 *            查询设计对象的name
	 * @param type
	 *            最终导出报表的类型 html\xls\cvs\pdf
	 * @return
	 */
	Object buildReport(String naturalname, String type,QueryCondition qd,
			HttpServletResponse rs);

}
