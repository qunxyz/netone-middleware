package com.jl.common.report;

/**
 * 报表框架访问入口
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class ReportEntry {

	public static XReportIfc iv() {

		return new XReportImple();
	}

}
