/**
 * 
 */
package com.report.output;

import java.io.OutputStream;

import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.printer.PDFCss;
import com.lucaslee.report.printer.PDFPrinter;
import com.report.BaseReport;

/**
 * 报表输出API
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-15 下午02:25:47
 * @history
 */
public class ReportPDFOutput extends BaseReport {

	/**
	 * 样式
	 */
	protected PDFCss css;

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
	public ReportPDFOutput(PDFCss css, Table header, HeaderTable colHeader,
			Table record, Table footerTable) {
		this.css = css;
		this.report = this.report(header, colHeader, record, footerTable);
	}

	/**
	 * 输出PDF报表
	 * 
	 * @param report
	 *            报表对象
	 * @param os
	 * @throws Exception
	 */
	public void buildPDFReport(OutputStream os) throws Exception {
		new PDFPrinter().print(report, css, os);
	}

}
