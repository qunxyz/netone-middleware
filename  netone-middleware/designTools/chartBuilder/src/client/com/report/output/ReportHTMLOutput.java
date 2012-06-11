/**
 * 
 */
package com.report.output;

import java.io.OutputStream;

import com.lucaslee.report.ReportManager;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.printer.HTMLCss;
import com.lucaslee.report.printer.HTMLPrinter;
import com.report.BaseReport;

/**
 * 报表输出API
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-15 下午02:25:47
 * @history
 */
public class ReportHTMLOutput extends BaseReport {

	/**
	 * 样式
	 */
	protected HTMLCss css;

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
	public ReportHTMLOutput(HTMLCss css, Table header, HeaderTable colHeader,
			Table record, Table footerTable) {
		this.css = css;
		this.report = this.report(header, colHeader, record, footerTable);
	}

	/**
	 * 输出HTML报表
	 * 
	 * @param report
	 *            报表对象
	 * @param os
	 * @throws Exception
	 */
	public void buildHTMLReport(OutputStream os) throws Exception {
		new HTMLPrinter().print(report, css, os);
	}

}
