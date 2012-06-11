/**
 * 
 */
package com.report.output;

import java.io.OutputStream;

import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.printer.CSVPrinter;
import com.report.BaseReport;

/**
 * 报表输出API
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-15 下午02:25:47
 * @history
 */
public class ReportCSVOutput extends BaseReport {

	/**
	 * 输出
	 * 
	 * @param css
	 *            样式
	 * @param header
	 *            页头
	 * @param colHeader
	 *            表头
	 * @param record
	 *            记录
	 * @param footerTable
	 *            页尾
	 */
	public ReportCSVOutput(Table header, HeaderTable colHeader, Table record,
			Table footerTable) {
		this.report = this.report(header, colHeader, record, footerTable);
	}

	/**
	 * 根据报表对象生成csv格式的报表.
	 * 
	 * @param os
	 * @throws Exception
	 */
	public void buildCSVReport(OutputStream os) throws Exception {
		new CSVPrinter().print(report, os);
	}

}
