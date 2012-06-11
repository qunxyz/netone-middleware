package com.test;

import java.io.FileOutputStream;

import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.report.output.ReportCSVOutput;
import com.report.output.ReportExcelOutput;
import com.report.output.ReportHTMLOutput;
import com.report.output.ReportPDFOutput;

/**
 * �����л��ܱ�������
 */
public class Sample1 {

	/**
	 * ���ɱ���.
	 * 
	 * @throws Exception
	 */
	public static void generateReport() throws Exception {
		// ����ļ�
		FileOutputStream fo = null;
		try {
			Table header = Data.getHeader();
			HeaderTable colHeader = Data.getColHeader();
			Table record = Data.getRecord();
			Table footerTable = Data.getFooterTable();

			// ����HTML��ʽ����
			ReportHTMLOutput htmloutput = new ReportHTMLOutput(Data
					.getHTMLCss(), header, colHeader, record, footerTable);
			fo = new FileOutputStream("dist/sample1.html");
			htmloutput.buildHTMLReport(fo);
			fo.close();

			// ����PDF��ʽ����
			ReportPDFOutput htmlpdfput = new ReportPDFOutput(Data.getPDFCss(),
					header, colHeader, record, footerTable);
			fo = new FileOutputStream("dist/sample1.pdf");
			htmlpdfput.buildPDFReport(fo);
			fo.close();

			// ����CSV��ʽ����
			ReportCSVOutput csvoutput = new ReportCSVOutput(header, colHeader,
					record, footerTable);
			fo = new FileOutputStream("dist/sample1.csv");
			csvoutput.buildCSVReport(fo);
			fo.close();

			// ����Excel��ʽ����
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
