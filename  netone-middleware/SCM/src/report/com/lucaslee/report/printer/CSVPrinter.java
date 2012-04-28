package com.lucaslee.report.printer;

import java.io.IOException;
import java.io.OutputStream;

import com.lucaslee.report.Printer;
import com.lucaslee.report.ReportException;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * CSV��ӡ����
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: ÿ����Ԫ�á�,���ָ����м��û��з��ָ���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:Lucas-lee Soft
 * </p>
 * 
 * @author Lucas Lee
 * @version 1.0
 */
public class CSVPrinter implements Printer {
	public CSVPrinter() {
	}

	/**
	 * ��ӡ�����������
	 * 
	 * @param r
	 *            ����
	 * @param result
	 *            �����
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	public void print(Report r, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		if (r.getHeaderTable() != null) {
			print(r.getHeaderTable(), result);
		}
		if (r.getBody() != null) {
			print(r.getBody(), result);
		}
		if (r.getFooterTable() != null) {
			print(r.getFooterTable(), result);
		}
	}

	/**
	 * ��ӡ�������嵽�������
	 * 
	 * @param body
	 *            ��������
	 * @param result
	 *            �����
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	private void print(ReportBody body, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		Table data = body.getData().cloneAll();
		Table header = body.getTableColHeader();
		if (header != null) {
			for (int i = header.getRowCount() - 1; i >= 0; i--) {
				data.insertRow(0, header.getRow(i));
			}
		}
		print(data, result);

	}

	/**
	 * @todo���� ��õ�Ԫ�������
	 * @param tc
	 * @return
	 * @throws ReportException
	 */
	private String print(TableCell tc) throws ReportException {
		StringBuffer buf = new StringBuffer();
		if (Report.CROSS_HEAD_HEAD_TYPE.equals(tc.getCssClass())) {
			String[] strs = PrinterUtil.getCrossHeadHeadContent(tc);
			for (int i = strs.length - 1; i >= 0; i--) {
				if (i != strs.length - 1)
					buf.append("/");
				buf.append(strs[i]);
			}
		} else {
			String content = (String) tc.getContent();
			buf.append(content == null ? "" : content);
		}
		String result = buf.toString();
		// �滻�ؼ����š���Ӣ�ĵĶ����滻Ϊ���ĵĶ��ţ������з����س������滻Ϊ�ո�
		result = result.replaceAll(",", "��");
		result = result.replaceAll("\r\n", " ");
		result = result.replaceAll("\n", " ");
		return result;
	}

	/**
	 * ��ӡ��������
	 * 
	 * @param t
	 *            ���
	 * @param result
	 *            �����
	 * @throws com.lucaslee.report.ReportException
	 * @throws IOException
	 */
	private void print(Table t, OutputStream result)
			throws com.lucaslee.report.ReportException, IOException {
		StringBuffer buf = new StringBuffer();
		final String delimiter = ",";
		TableRow tempTR = null;
		TableCell tempCell = null;
		int colCount = 0;
		for (int i = 0; i < t.getRowCount(); i++) {
			tempTR = t.getRow(i);
			colCount = t.getColCount();
			for (int j = 0; j < colCount; j++) {
				tempCell = tempTR.getCell(j);
				if (tempCell.getIsHidden() == false) {
					buf.append(print(tempCell));
				}
				if (j < colCount - 1) {
					buf.append(delimiter);
				} else {
					buf.append("\r\n");
				}
			}
		}
		result.write(buf.toString().getBytes());
	}

}
