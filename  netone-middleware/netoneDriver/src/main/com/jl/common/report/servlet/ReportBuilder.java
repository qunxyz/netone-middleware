package com.jl.common.report.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.report.output.ReportCSVOutput;
import com.report.output.ReportExcelOutput;
import com.report.output.ReportHTMLOutput;
import com.report.output.ReportPDFOutput;

public class ReportBuilder {

	/**
	 * 生成报表.
	 * 
	 * @throws Exception
	 */
	public static void generateReport(String type, String name,String condition,
			HttpServletResponse rs,HttpServletRequest rq) throws Exception {

		DataRep data = new DataRep();
		data.fetchData(name,condition,rq);
		Table header = null;
		HeaderTable colHeader = data.getColHeader();
		Table record = data.getRecord();

		Table footerTable=new Table();
		record= data.getFooterTable(record);

		if ("pdf".equals(type)) {
			rs.setContentType("application/pdf");
			// 生成PDF格式报表
			ReportPDFOutput htmlpdfput = new ReportPDFOutput(Data.getPDFCss(),
					header, colHeader, record, footerTable);

			htmlpdfput.buildPDFReport(rs.getOutputStream());
		} else if ("excel".equals(type)) {
			rs.setHeader("Content-Disposition", "attachment; filename="
					+ new String("report".getBytes("GBK"), "ISO8859-1")
					+ ".xls");
			rs.setContentType("application/vnd.ms-excel");
			// 生成Excel格式报表
			ReportExcelOutput exceloutput = new ReportExcelOutput(Data
					.getExcelCss(), header, colHeader, record, footerTable);

			exceloutput.buildExcelReport(rs.getOutputStream());
		} else if ("csv".equals(type)) {
			rs.setContentType("application/vnd.ms-excel");
			// 生成CSV格式报表
			ReportCSVOutput csvoutput = new ReportCSVOutput(header, colHeader,
					record, footerTable);

			csvoutput.buildCSVReport(rs.getOutputStream());
		} else if ("html".equals(type)) {
			rs.setContentType("text/html; charset=GBK");
			// 生成HTML格式报表
			ReportHTMLOutput htmloutput = new ReportHTMLOutput(Data
					.getHTMLCss(), header, colHeader, record, footerTable);

			htmloutput.buildHTMLReport(rs.getOutputStream());
		}

	}

}
