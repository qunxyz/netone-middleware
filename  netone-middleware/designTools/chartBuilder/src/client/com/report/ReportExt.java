/**
 * 
 */
package com.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lucaslee.report.ReportException;
import com.lucaslee.report.ReportManager;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Rectangle;
import com.lucaslee.report.model.Report;
import com.lucaslee.report.model.ReportBody;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * ������չ���߰�
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-1-17
 * @history
 */
public class ReportExt {

	/** ������ */

	/** ��ϸ �������� */
	public static final String _REPORT_GATHER_COL_0 = "col0";
	public static final String _REPORT_GATHER_COL_DETAIL = "detail";
	/** ���� */
	public static final String _REPORT_GATHER_COL_GROUPBY = "groupBy";
	/** ������� */
	public static final String _REPORT_GATHER_COL_999 = "col999";

	/** ʵ����� */
	public static final String _REPORT_ENTITY = "entity";
	/** �ؼ��ֱ��� */
	public static final String _REPORT_KEYWORDS = "keywords";
	/** �������ݼ��ϱ��� */
	public static final String _REPORT_GATHER_DOUBLE_ARRAY = "gatherarrays";
	/** �������ݼ��ϱ���2 */
	public static final String _REPORT_GATHER_DOUBLE_ARRAY_2 = "gatherarrays_2";
	/** �����ۼƼ��ϱ��� */
	public static final String _REPORT_YEAR_DOUBLE_ARRAY = "yeararrays";

	/** ����Ȱٷֱ� */
	private final static int width = 95;

	/**
	 * �Զ����ѯ���ؼ��� �� ������ʵ������
	 * 
	 * @param entity
	 *            ����ʵ�����
	 * @param condition
	 *            ����
	 * @param customizeKeyword
	 *            �Զ���ؼ��� ��'_'��Ϊ�ָ�
	 * @return
	 * @throws Exception
	 */
	public Map filterCustomizeQueryKeyword(ReportEntity entity, Map condition,
			String customizeKeyword) throws Exception {
		Map map = new HashMap();// �洢ʵ��
		map.put(_REPORT_ENTITY, entity);// ʵ������
		map.put(_REPORT_KEYWORDS, customizeKeyword);// �����ؼ���
		return map;
	}

	/**
	 * ����
	 * 
	 * @param row
	 *            һ������
	 * @param summaryNeedList
	 *            ��Ҫ�����е�����
	 * @param condition
	 *            ����
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<String> filter(List<String> row, List<String> summaryNeedList,
			Map condition) throws Exception {
		if (condition.containsKey(_REPORT_GATHER_COL_0)) {// ����Ҫ����
			return row;
		}
		if (row.size() != summaryNeedList.size()) {
			throw new Exception("����������һ��!");
		}
		List<String> filteredRow = new ArrayList<String>();
		for (int i = 0; i < row.size(); i++) {
			String v = summaryNeedList.get(i);
			if (condition.containsKey(v) || _REPORT_GATHER_COL_999.equals(v)) {
				filteredRow.add(row.get(i));
			}
		}
		return filteredRow;
	}

	/**
	 * ���� �����˲���Ҫ��ʾ����,Ŀǰ����Ի��������й��ˣ�<BR>
	 * ����ReportExt._REPORT_GATHER_COL_*������ָ������
	 * 
	 * @see ReportExt.filterQueryKeyword(*)����
	 * 
	 * @param row
	 *            һ������
	 * @param summaryNeedList
	 *            ��Ҫ�����е�����
	 * @param condition
	 *            ����
	 * @return
	 * @throws Exception
	 */
	public List<TableCell> filterRow(List<TableCell> row,
			List<String> summaryNeedList, Map condition) throws Exception {
		if (condition.containsKey(_REPORT_GATHER_COL_0)) {// ����Ҫ����
			return row;
		}
		if (row.size() != summaryNeedList.size()) {
			throw new Exception(row.size() + "��" + summaryNeedList.size()
					+ "����������һ��!");
		}
		List<TableCell> filteredRow = new ArrayList<TableCell>();
		for (int i = 0; i < row.size(); i++) {
			String v = summaryNeedList.get(i);
			if (condition.containsKey(v) || _REPORT_GATHER_COL_999.equals(v)) {
				filteredRow.add(row.get(i));
			}
		}
		return filteredRow;
	}

	/**
	 * ����
	 * 
	 * @param row
	 *            һ������
	 * @param summaryNeedList
	 *            ��Ҫ�����е�����
	 * @param condition
	 *            ����
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public TableCell[] filterRow(TableCell[] row, String[] summaryNeedList,
			Map condition) throws Exception {
		if (condition.containsKey(_REPORT_GATHER_COL_0)) {// ����Ҫ����
			return row;
		}
		if (row.length != summaryNeedList.length) {
			throw new Exception("����������һ��!");
		}
		TableCell[] filteredRow = new TableCell[row.length];
		for (int i = 0; i < row.length; i++) {
			String v = summaryNeedList[i];
			if (condition.containsKey(v) || _REPORT_GATHER_COL_999.equals(v)) {
				filteredRow[i] = row[i];
			}
		}
		return filteredRow;
	}

	/**
	 * ȡ��һ�б������
	 * 
	 * @param _dataHeader
	 *            �������
	 * @param _alignHeader
	 *            ��������
	 * @return
	 * @throws Exception
	 */
	public TableRow getOneTableRow(List<TableCell> _dataHeader)
			throws Exception {
		TableRow tr = new TableRow();
		for (int i = 0; i < _dataHeader.size(); i++) {
			TableCell cell = _dataHeader.get(i);
			if (cell == null) {
				cell = new TableCell("");
			}
			tr.addCell(cell);
		}
		return tr;
	}

	/**
	 * ȡ��һ�б������
	 * 
	 * @param _dataHeader
	 *            �������
	 * @param _alignHeader
	 *            ��������
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public TableRow getOneTableRow(TableCell[] _dataHeader) throws Exception {
		TableRow tr = new TableRow();
		for (int i = 0; i < _dataHeader.length; i++) {
			TableCell cell = _dataHeader[i];
			if (cell == null) {
				cell = new TableCell("");
			}
			tr.addCell(cell);
		}
		return tr;
	}

	/**
	 * ���ñ�����ͷ
	 * 
	 * @param report
	 *            �������
	 * @param headerTitle
	 *            ����ͷ����
	 * @throws Exception
	 */
	public void setTitleHeader(Report report, String headerTitle,
			String[] dateTitle, String[] extendContent) throws ReportException {
		// *****************���ñ���ͷ*********************
		Table headerTable = new Table();
		// ���ñ��Ŀ�ȱ���(�ٷֱ�)
		// int[] widths = { 20, 60, 20 };
		// headerTable.setWidths(widths);
		report.setHeaderTable(headerTable);

		headerTable.setStyle(Report.HEAD_TYPE);
		headerTable.setWidth(width);
		headerTable.setBorder(0);
		headerTable.setAlign(headerTable.ALIGN_CENTER);

		TableCell tc = null;
		TableRow tr = null;

		int colCount = report.getBody().getTableColHeader().getColCount();
		// 1.�������
		if (headerTitle != null && !"".equals(headerTitle)) {
			tr = new TableRow(colCount);
			headerTable.addRow(tr);
			tc = tr.getCell(0);
			tc.setColSpan(colCount);
			tc.setAlign(tc.ALIGN_CENTER);
			tc.setContent(headerTitle);
			for (int i = 1; i < colCount; i++) {
				tr.getCell(i).setIsHidden(true);
			}
			tc.setCssClass(Report.TITLE_TYPE);// ���ñ�����ʽ
		}
		// 2.��չ
		if (extendContent != null && extendContent.length > 0) {
			for (int i = 0; i < extendContent.length; i++) {
				if (colCount <= 4) {
					tr = new TableRow(colCount);
					tc = tr.getCell(colCount - 1);
					tc.setContent(extendContent[i]);
					tc.setCssClass(Report.DATA_TYPE);
					tc.setAlign(Rectangle.ALIGN_RIGHT);
					headerTable.addRow(tr);
				} else {
					tr = new TableRow(colCount);
					tc = tr.getCell(colCount - 2);
					tc.setColSpan(2);
					tc.setContent(extendContent[i]);
					tc.setCssClass(Report.DATA_TYPE);
					tc.setAlign(Rectangle.ALIGN_RIGHT);
					tc = tr.getCell(colCount - 1);
					tc.setIsHidden(true);
					headerTable.addRow(tr);
				}
			}
		}

		// 3.ʱ�����
		if (dateTitle != null && dateTitle.length > 0) {
			if (colCount <= 4) {
				tr = new TableRow(colCount);
				headerTable.addRow(tr);
				tc = tr.getCell(0);
				tc.setContent("���ڷ�Χ:" + dateTitle[0]);
				tc.setAlign(Rectangle.ALIGN_LEFT);
				tc.setCssClass(Report.DATA_TYPE);
				tc = tr.getCell(colCount - 1);
				tc.setContent("��ѯ����:" + dateTitle[1]);
				tc.setCssClass(Report.DATA_TYPE);
				tc.setAlign(Rectangle.ALIGN_RIGHT);
			} else {
				tr = new TableRow(colCount);
				headerTable.addRow(tr);
				tc = tr.getCell(0);
				tc.setContent("���ڷ�Χ:" + dateTitle[0]);
				tc.setAlign(Rectangle.ALIGN_LEFT);
				tc.setCssClass(Report.DATA_TYPE);
				tc.setColSpan(2);
				tc = tr.getCell(1);
				tc.setIsHidden(true);
				tc = tr.getCell(colCount - 2);
				tc.setColSpan(2);
				tc.setContent("��ѯ����:" + dateTitle[1]);
				tc.setCssClass(Report.DATA_TYPE);
				tc.setAlign(Rectangle.ALIGN_RIGHT);
				tc = tr.getCell(colCount - 1);
				tc.setIsHidden(true);
				tc.setCssClass(Report.DATA_TYPE);
			}

		}
		// *****************end ���ñ���ͷ*********************
	}

	/**
	 * ���ݱ����������excel��ʽ�ı���.
	 * 
	 * @param dataTable
	 *            ���ݶ���
	 * @param headerSet
	 *            ������List list, String dataHeaderSet
	 * @throws Exception
	 */
	public Report getReportList(Collection result, String[] dataHeaderSet,
			String[] headerSet) throws Exception {
		return getReportList(result, dataHeaderSet, headerSet, null);
	}

	/**
	 * ���ݱ����������excel��ʽ�ı���.
	 * 
	 * @param dataTable
	 *            ���ݶ���
	 * @param headerSet
	 *            ������List list, String dataHeaderSet
	 * @param alignHeaderSet
	 *            Map<String, Integer> ���뷽ʽ Ĭ�Ͼ��� Rectangle.ALIGN_LEFT����
	 *            Rectangle.ALIGN_CENTER���� Rectangle.ALIGN_RIGHT����
	 * @throws Exception
	 */
	public Report getReportList(Collection result, String[] dataHeaderSet,
			String[] headerSet, Map<String, Integer> alignHeaderSet)
			throws Exception {
		// ���ԭʼ���ݱ��
		Table t = new Table();
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			TableRow tr = new TableRow();

			for (String set : dataHeaderSet) {
				String v = "" + object.get(set);

				if (alignHeaderSet != null) {
					if (alignHeaderSet.containsKey(set)) {
						tr.addCell(new TableCell(v, alignHeaderSet.get(set)));
					} else {
						tr.addCell(new TableCell(v));
					}
				} else {
					tr.addCell(new TableCell(v));
				}
			}
			t.addRow(tr);
		}
		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end ���ñ������岿��**************

		// *****************���ñ�������********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		// t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end ���ñ�������********************

		// *****************���ñ������������ͷ*********************/
		HeaderTable th = new HeaderTable();
		report.getBody().setTableColHeader(th);
		TableRow thr = new TableRow(headerSet.length);
		th.addRow(thr);
		for (int i = 0; i < headerSet.length; i++) {
			thr.setCell(i, new TableCell(headerSet[i], Rectangle.ALIGN_CENTER));
		}
		return report;
	}

	/**
	 * ���ñ���򵥱���ͷ
	 * 
	 * @param report
	 *            �������
	 * @param headerTitle
	 *            ����ͷ����
	 * @throws Exception
	 */
	public void setSimpleTitleFooter(Report report, String headerTitle)
			throws ReportException {
		// *****************���ñ���ͷ*********************
		Table headerTable = new Table();
		// ���ñ��Ŀ�ȱ���(�ٷֱ�)
		// int[] widths = { 20, 60, 20 };
		// headerTable.setWidths(widths);
		report.setHeaderTable(headerTable);

		headerTable.setBorder(0);
		headerTable.setAlign(headerTable.ALIGN_CENTER);

		TableCell tc = null;
		TableRow tr = null;

		int colCount = report.getBody().getTableColHeader().getColCount();
		tr = new TableRow(colCount);
		headerTable.addRow(tr);
		tc = tr.getCell(0);
		tc.setColSpan(colCount);
		tc.setAlign(tc.ALIGN_CENTER);
		tc.setContent(headerTitle);
		for (int i = 1; i < colCount; i++) {
			tr.getCell(i).setIsHidden(true);
		}
		tc.setCssClass(Report.TITLE_TYPE);// ���ñ�����ʽ
		// *****************end ���ñ���ͷ*********************
	}

	/**
	 * ���ñ������������ͷ
	 * 
	 * @param dataTable
	 *            ���ݶ���
	 * @param headerSet
	 *            ������
	 * @throws Exception
	 */
	public Report setSimpleColHeader(Table dataTable, String[] headerSet)
			throws Exception {
		// ���ԭʼ���ݱ��
		Table t = dataTable;

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end ���ñ������岿��**************

		// *****************���ñ�������********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end ���ñ�������********************

		// *****************���ñ������������ͷ*********************/

		HeaderTable th = new HeaderTable();
		TableRow thr = new TableRow(headerSet.length);
		for (int i = 0; i < headerSet.length; i++) {
			TableCell tc = new TableCell(headerSet[i], Rectangle.ALIGN_CENTER);
			thr.setCell(i, tc);
		}
		
		th.addRow(thr);

		report.getBody().setTableColHeader(th);
		return report;
	}

	/**
	 * ���ñ������������ͷ
	 * 
	 * @param dataTable
	 *            ���ݶ���
	 * @param headerSet
	 *            ������
	 * @throws Exception
	 */
	public Report setSimpleColHeader(Table dataTable, List<TableCell> headerSet)
			throws Exception {
		// ���ԭʼ���ݱ��
		Table t = dataTable;

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end ���ñ������岿��**************

		// *****************���ñ�������********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end ���ñ�������********************

		// *****************���ñ������������ͷ*********************/

		HeaderTable th = new HeaderTable();
		TableRow thr = new TableRow(headerSet.size());
		for (int i = 0; i < headerSet.size(); i++) {
			TableCell tc = headerSet.get(i);
			tc.setAlign(Rectangle.ALIGN_CENTER);
			thr.setCell(i, tc);
		}
		th.addRow(thr);

		report.getBody().setTableColHeader(th);
		return report;
	}

	/**
	 * ���ñ��������������ͷ Ŀǰֻ֧������
	 * 
	 * @param dataTable
	 *            ���ݶ���
	 * @param headerSet
	 *            ������
	 * @throws Exception
	 */
	public Report setComplexColHeader(Table dataTable,
			List<TableCell> headerSet1, List<TableCell> headerSet2)
			throws Exception {
		return setComplexColHeader(dataTable, headerSet1, headerSet2, false);
	}

	/**
	 * ���ñ��������������ͷ ����
	 * 
	 * @param dataTable
	 *            ���ݶ���
	 * @param headerSet
	 *            ������
	 * @throws Exception
	 */
	public Report setComplexColHeader(Table dataTable,
			List<TableCell> headerSet1, List<TableCell> headerSet2,
			boolean mergeSameCells) throws Exception {
		// ���ԭʼ���ݱ��
		Table t = dataTable;

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end ���ñ������岿��**************

		// *****************���ñ�������********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end ���ñ�������********************
		// *****************���ñ������������ͷ*********************/

		HeaderTable th = new HeaderTable();
		TableRow thr = new TableRow(headerSet1.size());
		for (int i = 0; i < headerSet1.size(); i++) {
			TableCell tc1 = headerSet1.get(i);
			tc1.setAlign(Rectangle.ALIGN_CENTER);
			thr.setCell(i, tc1);
		}
		th.addRow(thr);
		if (headerSet2.size() > 0) {
			TableRow thr2 = new TableRow(headerSet1.size());
			for (int i = 0; i < headerSet2.size(); i++) {
				TableCell tc2 = headerSet2.get(i);
				tc2.setAlign(Rectangle.ALIGN_CENTER);
				thr2.setCell(i, tc2);
			}
			th.addRow(thr2);
		}

		// ***********��ָ���з���**********
		if (mergeSameCells) {
			ReportManager rm = new ReportManager();
			int x = headerSet2.size() - 1;
			int[] cols = new int[x];
			for (int i = 0; i < x; i++) {
				cols[i] = i;
			}
			// �ϲ��������ڵ�ֵͬ��Ԫ
			int[] rows = { 0, 1 };
			th = rm.mergeHeaderSameCells(th, cols, rows);
		}

		report.getBody().setTableColHeader(th);
		return report;
	}

	/**
	 * ���ñ��������������ͷ ����
	 * 
	 * @param dataTable
	 *            ���ݶ���
	 * @param headerSet
	 *            ������
	 * @throws Exception
	 */
	public Report setComplexColHeader(Table dataTable,
			List<TableCell> headerSet0, List<TableCell> headerSet1,
			List<TableCell> headerSet2, boolean mergeSameCells)
			throws Exception {
		// ���ԭʼ���ݱ��
		Table t = dataTable;

		// ���屨�����
		Report report = new Report();

		// **************���ñ������岿��**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end ���ñ������岿��**************

		// *****************���ñ�������********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end ���ñ�������********************
		// *****************���ñ������������ͷ*********************/

		HeaderTable th = new HeaderTable();

		if (headerSet0.size() > 0) {
			TableRow thr = new TableRow(headerSet0.size());
			for (int i = 0; i < headerSet0.size(); i++) {
				TableCell tc0 = headerSet0.get(i);
				tc0.setAlign(Rectangle.ALIGN_CENTER);
				thr.setCell(i, tc0);
			}
			th.addRow(thr);
		}

		TableRow thr = new TableRow(headerSet1.size());
		for (int i = 0; i < headerSet1.size(); i++) {
			TableCell tc1 = headerSet1.get(i);
			tc1.setAlign(Rectangle.ALIGN_CENTER);
			thr.setCell(i, tc1);
		}
		th.addRow(thr);

		if (headerSet2.size() > 0) {
			TableRow thr2 = new TableRow(headerSet1.size());
			for (int i = 0; i < headerSet2.size(); i++) {
				TableCell tc2 = headerSet2.get(i);
				tc2.setAlign(Rectangle.ALIGN_CENTER);
				thr2.setCell(i, tc2);
			}
			th.addRow(thr2);
		}

		// ***********��ָ���з���**********
		if (mergeSameCells) {
			ReportManager rm = new ReportManager();
			int x = headerSet2.size() - 1;
			int[] cols = new int[x];
			for (int i = 0; i < x; i++) {
				cols[i] = i;
			}
			// �ϲ��������ڵ�ֵͬ��Ԫ
			int[] rows = { 0, 1, 2 };
			th = rm.mergeHeaderSameCells(th, cols, rows);
		}

		report.getBody().setTableColHeader(th);

		return report;
	}

}
