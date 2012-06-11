/**
 * 
 */
package com.report.output;

import java.io.OutputStream;

import com.lucaslee.report.ReportManager;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.printer.ExcelCss;
import com.lucaslee.report.printer.ExcelPrinter;
import com.report.BaseReport;

/**
 * 报表输出API
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-15 下午02:25:47
 * @history
 */
public class ReportExcelOutput extends BaseReport {
	/**
	 * 样式
	 */
	protected ExcelCss css;

	/**
	 * 输出
	 * 
	 * @param css
	 *            样式
	 * @param headerTable
	 *            页头
	 * @param colHeader
	 *            表头
	 * @param record
	 *            记录 可进行两次加工处理 详见{@link ReportManager}
	 * @param footerTable
	 *            页尾
	 */
	public ReportExcelOutput(ExcelCss css, Table header,
			HeaderTable colHeader, Table record, Table footerTable) {
		this.css = css;
		this.report = this.report(header, colHeader, record, footerTable);
	}

	/**
	 * 输出excel报表
	 * 
	 * @param report
	 * @param os
	 * @throws Exception
	 */
	public void buildExcelReport(OutputStream os) throws Exception {
		new ExcelPrinter().print(report, css, os, false);
	}
	
}
