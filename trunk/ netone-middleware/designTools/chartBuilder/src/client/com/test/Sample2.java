package com.test;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.Table;
import com.report.output.ReportCSVOutput;
import com.report.output.ReportExcelOutput;
import com.report.output.ReportHTMLOutput;
import com.report.output.ReportPDFOutput;

/**
 * serlvet 输出例子
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-21 下午02:35:35
 * @history
 */
public class Sample2 {

	public void format(String _format, String titleName, Report report,
			HttpServletResponse response) {
		String format = _format;
		try {
			Table header = Data.getHeader();
			HeaderTable colHeader = Data.getColHeader();
			Table record = Data.getRecord();
			Table footerTable = Data.getFooterTable();

			if (format == null || format.length() == 0)
				return;

			if (format.equals("html")) {
				// 生成HTML格式报表
				response.setContentType("text/html; charset=GBK");
				response.setCharacterEncoding("GBK");
				OutputStream os = response.getOutputStream();
				ReportHTMLOutput htmloutput = new ReportHTMLOutput(Data
						.getHTMLCss(), header, colHeader, record, footerTable);
				htmloutput.buildHTMLReport(os);
				os.flush();
				os.close();
			} else if (format.equals("excel")) {
				// 生成Excel格式报表
				response.setHeader("Content-Disposition",
						"attachment; filename="
								+ new String(titleName.getBytes("GBK"),
										"ISO8859-1") + ".xls");
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				ReportExcelOutput exceloutput = new ReportExcelOutput(Data
						.getExcelCss(), header, colHeader, record, footerTable);
				exceloutput.buildExcelReport(os);
				os.flush();
				os.close();
			} else if (format.equals("csv")) {
				// 生成CSV格式报表
				response.setContentType("application/vnd.ms-excel");
				OutputStream os = response.getOutputStream();
				ReportCSVOutput csvoutput = new ReportCSVOutput(header,
						colHeader, record, footerTable);
				csvoutput.buildCSVReport(os);
				os.flush();
				os.close();
			} else if (format.equals("pdf")) {
				// 生成PDF格式报表
				response.setContentType("application/pdf");
				OutputStream os = response.getOutputStream();
				ReportPDFOutput htmlpdfput = new ReportPDFOutput(Data
						.getPDFCss(), header, colHeader, record, footerTable);
				htmlpdfput.buildPDFReport(os);
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
