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
 * �������API
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-15 ����02:25:47
 * @history
 */
public class ReportCSVOutput extends BaseReport {

	/**
	 * ���
	 * 
	 * @param css
	 *            ��ʽ
	 * @param header
	 *            ҳͷ
	 * @param colHeader
	 *            ��ͷ
	 * @param record
	 *            ��¼
	 * @param footerTable
	 *            ҳβ
	 */
	public ReportCSVOutput(Table header, HeaderTable colHeader, Table record,
			Table footerTable) {
		this.report = this.report(header, colHeader, record, footerTable);
	}

	/**
	 * ���ݱ����������csv��ʽ�ı���.
	 * 
	 * @param os
	 * @throws Exception
	 */
	public void buildCSVReport(OutputStream os) throws Exception {
		new CSVPrinter().print(report, os);
	}

}
