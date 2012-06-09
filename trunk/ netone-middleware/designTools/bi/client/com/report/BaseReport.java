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
 * 报表基类
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-12-21 上午09:27:52
 * @history
 */
public abstract class BaseReport {
	/**
	 * 报表对象
	 */
	protected Report report;

	/**
	 * 
	 * @param header
	 *            页头
	 * @param tableColHeader
	 *            表头
	 * @param record
	 *            记录 可进行两次加工处理 {@link ReportManager}
	 * @param footerTable
	 *            页尾
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
