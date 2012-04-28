package com.jl.common.report.servlet;

import javax.servlet.http.HttpServletResponse;

import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.report.output.ReportCSVOutput;
import com.report.output.ReportExcelOutput;
import com.report.output.ReportHTMLOutput;
import com.report.output.ReportPDFOutput;

public class ReportBuilder {

	/**
	 * ���ɱ���.
	 * 
	 * @throws Exception
	 */
	public static void generateReport(String type, String name,String condition,
			HttpServletResponse rs) throws Exception {

		DataRep data = new DataRep();
		data.fetchData(name,condition);
		Table header = null;
		HeaderTable colHeader = data.getColHeader();
		Table record = data.getRecord();

		Table footerTable = data.getFooterTable();

		if ("pdf".equals(type)) {
			rs.setContentType("application/pdf");
			// ����PDF��ʽ����
			ReportPDFOutput htmlpdfput = new ReportPDFOutput(Data.getPDFCss(),
					header, colHeader, record, footerTable);

			htmlpdfput.buildPDFReport(rs.getOutputStream());
		} else if ("excel".equals(type)) {
			rs.setHeader("Content-Disposition", "attachment; filename="
					+ new String("report".getBytes("GBK"), "ISO8859-1")
					+ ".xls");
			rs.setContentType("application/vnd.ms-excel");
			// ����Excel��ʽ����
			ReportExcelOutput exceloutput = new ReportExcelOutput(Data
					.getExcelCss(), header, colHeader, record, footerTable);

			exceloutput.buildExcelReport(rs.getOutputStream());
		} else if ("csv".equals(type)) {
			rs.setContentType("application/vnd.ms-excel");
			// ����CSV��ʽ����
			ReportCSVOutput csvoutput = new ReportCSVOutput(header, colHeader,
					record, footerTable);

			csvoutput.buildCSVReport(rs.getOutputStream());
		} else if ("html".equals(type)) {
			rs.setContentType("text/html; charset=GBK");
			// ����HTML��ʽ����
			ReportHTMLOutput htmloutput = new ReportHTMLOutput(Data
					.getHTMLCss(), header, colHeader, record, footerTable);

			htmloutput.buildHTMLReport(rs.getOutputStream());
		}

	}

}
