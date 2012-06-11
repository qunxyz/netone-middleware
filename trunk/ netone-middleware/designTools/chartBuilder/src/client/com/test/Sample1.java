package com.test;

import java.io.FileOutputStream;

import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.report.output.ReportCSVOutput;
import com.report.output.ReportExcelOutput;
import com.report.output.ReportHTMLOutput;
import com.report.output.ReportPDFOutput;

/**
 * 生成行汇总报表例子
 */
public class Sample1 {

	/**
	 * 生成报表.
	 * 
	 * @throws Exception
	 */
	public static void generateReport() throws Exception {
		// 输出文件
		FileOutputStream fo = null;
		try {
			Table header = Data.getHeader();
			HeaderTable colHeader = Data.getColHeader();
			Table record = Data.getRecord();
			Table footerTable = Data.getFooterTable();

			// 生成HTML格式报表
			ReportHTMLOutput htmloutput = new ReportHTMLOutput(Data
					.getHTMLCss(), header, colHeader, record, footerTable);
			fo = new FileOutputStream("dist/sample1.html");
			htmloutput.buildHTMLReport(fo);
			fo.close();

			// 生成PDF格式报表
			ReportPDFOutput htmlpdfput = new ReportPDFOutput(Data.getPDFCss(),
					header, colHeader, record, footerTable);
			fo = new FileOutputStream("dist/sample1.pdf");
			htmlpdfput.buildPDFReport(fo);
			fo.close();

			// 生成CSV格式报表
			ReportCSVOutput csvoutput = new ReportCSVOutput(header, colHeader,
					record, footerTable);
			fo = new FileOutputStream("dist/sample1.csv");
			csvoutput.buildCSVReport(fo);
			fo.close();

			// 生成Excel格式报表
			ReportExcelOutput exceloutput = new ReportExcelOutput(Data
					.getExcelCss(), header, colHeader, record, footerTable);
			fo = new FileOutputStream("dist/sample1.xls");
			exceloutput.buildExcelReport(fo);
			fo.close();
		} finally {
			if (fo != null)
				fo.close();
		}

	}

	public static void main(String[] args) throws Exception {
		generateReport();
	}

}
