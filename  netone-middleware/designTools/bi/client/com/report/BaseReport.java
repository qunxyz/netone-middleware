/**
 * 
 */
package com.report;

import com.lucaslee.report.ReportManager;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;

/**
 * �������
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-12-21 ����09:27:52
 * @history
 */
public abstract class BaseReport {
	/**
	 * �������
	 */
	protected Report report;

	/**
	 * 
	 * @param header
	 *            ҳͷ
	 * @param tableColHeader
	 *            ��ͷ
	 * @param record
	 *            ��¼ �ɽ������μӹ����� {@link ReportManager}
	 * @param footerTable
	 *            ҳβ
	 * @return
	 */
	public Report report(Table header, HeaderTable colHeader, Table record,
			Table footerTable) {
		Report report = new Report();

		// body
		ReportBody body = new ReportBody();
		body.setData(record);
		report.setBody(body);

		// header
		if (header != null)
			report.setHeaderTable(header);

		// colHeader
		if (colHeader != null)
			report.getBody().setTableColHeader(colHeader);

		// footer
		if (footerTable != null)
			report.setFooterTable(footerTable);

		return report;
	}

}
