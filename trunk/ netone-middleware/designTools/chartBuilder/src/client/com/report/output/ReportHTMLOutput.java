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
 * �������API
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-15 ����02:25:47
 * @history
 */
public class ReportHTMLOutput extends BaseReport {

	/**
	 * ��ʽ
	 */
	protected HTMLCss css;

	/**
	 * ���
	 * 
	 * @param css
	 *            ��ʽ
	 * @param headerTable
	 *            ҳͷ
	 * @param colHeader
	 *            ��ͷ
	 * @param record
	 *            ��¼ �ɽ������μӹ����� ���{@link ReportManager}
	 * @param footerTable
	 *            ҳβ
	 */
	public ReportHTMLOutput(HTMLCss css, Table header, HeaderTable colHeader,
			Table record, Table footerTable) {
		this.css = css;
		this.report = this.report(header, colHeader, record, footerTable);
	}

	/**
	 * ���HTML����
	 * 
	 * @param report
	 *            �������
	 * @param os
	 * @throws Exception
	 */
	public void buildHTMLReport(OutputStream os) throws Exception {
		new HTMLPrinter().print(report, css, os);
	}

}
