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
 * �������API
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-15 ����02:25:47
 * @history
 */
public class ReportPDFOutput extends BaseReport {

	/**
	 * ��ʽ
	 */
	protected PDFCss css;

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
	public ReportPDFOutput(PDFCss css, Table header, HeaderTable colHeader,
			Table record, Table footerTable) {
		this.css = css;
		this.report = this.report(header, colHeader, record, footerTable);
	}

	/**
	 * ���PDF����
	 * 
	 * @param report
	 *            �������
	 * @param os
	 * @throws Exception
	 */
	public void buildPDFReport(OutputStream os) throws Exception {
		new PDFPrinter().print(report, css, os);
	}

}
