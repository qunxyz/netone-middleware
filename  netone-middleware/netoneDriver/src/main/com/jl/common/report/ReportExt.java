/**
 * 
 */
package com.jl.common.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jl.entity.ReportEntity;
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
 * 报表扩展工具包
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-1-17
 * @history
 */
public class ReportExt {

	/** 汇总列 */

	/** 明细 不作汇总 */
	public static final String _REPORT_GATHER_COL_0 = "col0";
	public static final String _REPORT_GATHER_COL_DETAIL = "detail";
	/** 汇总 */
	public static final String _REPORT_GATHER_COL_GROUPBY = "groupBy";
	/** 必须的列 */
	public static final String _REPORT_GATHER_COL_999 = "col999";
	/** 按省公司汇总 */
	public static final String _REPORT_GATHER_COL_1 = "col1";
	/** 按大区域汇总 */
	public static final String _REPORT_GATHER_COL_2 = "col2";
	/** 按营销部汇总 */
	public static final String _REPORT_GATHER_COL_3 = "col3";
	/** 按经销商汇总 */
	public static final String _REPORT_GATHER_COL_4 = "col4";
	/** 按分销商汇总 */
	public static final String _REPORT_GATHER_COL_5 = "col5";
	/** 按业务主任汇总 */
	public static final String _REPORT_GATHER_COL_X = "colx";
	/** 按业务类型汇总 */
	public static final String _REPORT_GATHER_COL_TYPE = "coltype";
	/** 按组汇总 */
	public static final String _REPORT_GATHER_COL_P1 = "colp1";
	/** 按分类汇总 */
	public static final String _REPORT_GATHER_COL_P2 = "colp2";
	/** 按产品汇总 */
	public static final String _REPORT_GATHER_COL_P3 = "colp3";
	/** 按省公司仓库汇总 */
	public static final String _REPORT_GATHER_COL_S1 = "cols1";
	/** 按客户仓库汇总 */
	public static final String _REPORT_GATHER_COL_S2 = "cols2";

	/** 实体变量 */
	public static final String _REPORT_ENTITY = "entity";
	/** 关键字变量 */
	public static final String _REPORT_KEYWORDS = "keywords";
	/** 汇总数据集合变量 */
	public static final String _REPORT_GATHER_DOUBLE_ARRAY = "gatherarrays";
	/** 汇总数据集合变量2 */
	public static final String _REPORT_GATHER_DOUBLE_ARRAY_2 = "gatherarrays_2";
	/** 本年累计集合变量 */
	public static final String _REPORT_YEAR_DOUBLE_ARRAY = "yeararrays";

	/** 表格宽度百分比 */
	private final static int width = 95;

	/**
	 * 获取汇总列表
	 */
	public static Map<String, String> getReportGatherCol() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(_REPORT_GATHER_COL_1, _REPORT_GATHER_COL_1);
		map.put(_REPORT_GATHER_COL_2, _REPORT_GATHER_COL_2);
		map.put(_REPORT_GATHER_COL_3, _REPORT_GATHER_COL_3);
		map.put(_REPORT_GATHER_COL_4, _REPORT_GATHER_COL_4);
		map.put(_REPORT_GATHER_COL_5, _REPORT_GATHER_COL_5);
		map.put(_REPORT_GATHER_COL_TYPE, _REPORT_GATHER_COL_TYPE);
		map.put(_REPORT_GATHER_COL_X, _REPORT_GATHER_COL_X);
		map.put(_REPORT_GATHER_COL_P1, _REPORT_GATHER_COL_P1);
		map.put(_REPORT_GATHER_COL_P2, _REPORT_GATHER_COL_P2);
		map.put(_REPORT_GATHER_COL_P3, _REPORT_GATHER_COL_P3);
		// 仓库
		map.put(_REPORT_GATHER_COL_S1, _REPORT_GATHER_COL_S1);
		map.put(_REPORT_GATHER_COL_S2, _REPORT_GATHER_COL_S2);
		return map;
	}

	/**
	 * 查询出关键字 及 报表行实体数据 供汇总期初、期末、累计数据使用<BR>
	 * 原理:根据ReportExt._REPORT_GATHER_COL_*变量
	 * 对应汇总字段是否过滤，并找出对应字段所对应的值，且必须保证汇总字段存在于ReportExt._REPORT_GATHER_COL_*的变量值
	 * 
	 * @param entity
	 *            数据实体对象
	 * @param condition
	 *            条件
	 * @param excludeMap
	 *            排除
	 * @return
	 * @throws Exception
	 */
	public Map filterQueryKeyword(ReportEntity entity, Map condition,
			Map excludeMap) throws Exception {
		List<String> keyword = new ArrayList<String>();
		Map map = new HashMap();// 存储实体
		map.putAll(condition);
		if (condition.containsKey(_REPORT_GATHER_COL_0)) {// 明细
			String clientId = entity.get_REPORT_FIELD_DEPT_ID_4();// 客户ID
			String groupId = entity.get_REPORT_FIELD_PRODUCT_ID_1();
			String categoriesId = entity.get_REPORT_FIELD_PRODUCT_ID_2();
			String productId = entity.get_REPORT_FIELD_PRODUCT_ID_3();

			if (clientId == null) {
				throw new Exception("客户编号不能为空!");
			}
			clientId = clientId == null ? "" : clientId;
			groupId = groupId == null ? "" : groupId;
			categoriesId = categoriesId == null ? "" : categoriesId;
			productId = productId == null ? "" : productId;
			keyword.add(entity.get_REPORT_FIELD_DEPT_ID_4());// 关键字
			if (StringUtils.isNotEmpty(clientId)) {
				map.put(_REPORT_GATHER_COL_4, clientId);// 搜索关键字 片断
			}
			if (StringUtils.isNotEmpty(groupId)) {
				map.put(_REPORT_GATHER_COL_P1, groupId);// 搜索关键字 片断
			}
			if (StringUtils.isNotEmpty(categoriesId)) {
				map.put(_REPORT_GATHER_COL_P2, categoriesId);// 搜索关键字 片断
			}
			if (StringUtils.isNotEmpty(productId)) {
				map.put(_REPORT_GATHER_COL_P3, productId);// 搜索关键字 片断
			}
		} else {// 汇总
			if (excludeMap == null)
				excludeMap = new HashMap();
			String col1 = entity.get_REPORT_FIELD_DEPT_ID_1();
			String col2 = entity.get_REPORT_FIELD_DEPT_ID_2();
			String col3 = entity.get_REPORT_FIELD_DEPT_ID_3();
			String col4 = entity.get_REPORT_FIELD_DEPT_ID_4();
			String col5 = entity.get_REPORT_FIELD_DEPT_ID_5();
			String colx = entity.get_REPORT_FIELD_DEPT_ID_X();

			String coltype = entity.get_REPORT_FIELD_TYPE();
			String colp1 = entity.get_REPORT_FIELD_PRODUCT_ID_1();
			String colp2 = entity.get_REPORT_FIELD_PRODUCT_ID_2();
			String colp3 = entity.get_REPORT_FIELD_PRODUCT_ID_3();
			// 仓库
			String s1 = entity.get_REPORT_FIELD_DEPT_ID_S1();
			String s2 = entity.get_REPORT_FIELD_DEPT_ID_S2();

			Map<String, String> col = new HashMap<String, String>();
			col.put(_REPORT_GATHER_COL_1, col1 == null ? "" : col1);
			col.put(_REPORT_GATHER_COL_2, col2 == null ? "" : col2);
			col.put(_REPORT_GATHER_COL_3, col3 == null ? "" : col3);
			col.put(_REPORT_GATHER_COL_4, col4 == null ? "" : col4);
			col.put(_REPORT_GATHER_COL_5, col5 == null ? "" : col5);
			col.put(_REPORT_GATHER_COL_X, colx == null ? "" : colx);
			col.put(_REPORT_GATHER_COL_P1, colp1 == null ? "" : colp1);
			col.put(_REPORT_GATHER_COL_P2, colp2 == null ? "" : colp2);
			col.put(_REPORT_GATHER_COL_P3, colp3 == null ? "" : colp3);
			col.put(_REPORT_GATHER_COL_TYPE, coltype == null ? "0" : coltype);
			col.put(_REPORT_GATHER_COL_S1, s1 == null ? "" : s1);
			col.put(_REPORT_GATHER_COL_S2, s2 == null ? "" : s2);
			for (Iterator iterator = col.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				if (condition.containsKey(key) && !excludeMap.containsKey(key)) {
					String tmpkeyword = col.get(key);
					keyword.add(tmpkeyword);
					map.put(key, tmpkeyword);// 搜索关键字 片断
				}
			}

		}
		String split = "";
		StringBuffer keywords = new StringBuffer("");
		for (String string : keyword) {
			keywords.append(split + string);
			split = "_";
		}
		map.put(_REPORT_ENTITY, entity);// 实体数据
		map.put(_REPORT_KEYWORDS, keywords.toString());// 搜索关键字
		return map;
	}

	/**
	 * 查询出关键字 及 报表行实体数据 供汇总期初、期末、累计数据使用<BR>
	 * 原理:根据ReportExt._REPORT_GATHER_COL_*变量
	 * 对应汇总字段是否过滤，并找出对应字段所对应的值，且必须保证汇总字段存在于ReportExt._REPORT_GATHER_COL_*的变量值
	 * 
	 * @param entity
	 *            数据实体对象
	 * @param condition
	 *            条件
	 * @return
	 * @throws Exception
	 */
	public Map filterQueryKeyword(ReportEntity entity, Map condition)
			throws Exception {
		return filterQueryKeyword(entity, condition, null);
	}

	/**
	 * 自定义查询出关键字 及 报表行实体数据
	 * 
	 * @param entity
	 *            数据实体对象
	 * @param condition
	 *            条件
	 * @param customizeKeyword
	 *            自定义关键字 以'_'作为分隔
	 * @return
	 * @throws Exception
	 */
	public Map filterCustomizeQueryKeyword(ReportEntity entity, Map condition,
			String customizeKeyword) throws Exception {
		Map map = new HashMap();// 存储实体
		map.put(_REPORT_ENTITY, entity);// 实体数据
		map.put(_REPORT_KEYWORDS, customizeKeyword);// 搜索关键字
		return map;
	}

	/**
	 * 过滤
	 * 
	 * @param row
	 *            一行数据
	 * @param summaryNeedList
	 *            需要汇总列的描述
	 * @param condition
	 *            条件
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<String> filter(List<String> row, List<String> summaryNeedList,
			Map condition) throws Exception {
		if (condition.containsKey(_REPORT_GATHER_COL_0)) {// 不需要过滤
			return row;
		}
		if (row.size() != summaryNeedList.size()) {
			throw new Exception("对齐列数不一致!");
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
	 * 过滤 （过滤不需要显示的列,目前是针对汇总来进行过滤）<BR>
	 * 根据ReportExt._REPORT_GATHER_COL_*变量来指定过滤
	 * 
	 * @see ReportExt.filterQueryKeyword(*)方法
	 * 
	 * @param row
	 *            一行数据
	 * @param summaryNeedList
	 *            需要汇总列的描述
	 * @param condition
	 *            条件
	 * @return
	 * @throws Exception
	 */
	public List<TableCell> filterRow(List<TableCell> row,
			List<String> summaryNeedList, Map condition) throws Exception {
		if (condition.containsKey(_REPORT_GATHER_COL_0)) {// 不需要过滤
			return row;
		}
		if (row.size() != summaryNeedList.size()) {
			throw new Exception(row.size() + "和" + summaryNeedList.size()
					+ "对齐列数不一致!");
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
	 * 过滤
	 * 
	 * @param row
	 *            一行数据
	 * @param summaryNeedList
	 *            需要汇总列的描述
	 * @param condition
	 *            条件
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public TableCell[] filterRow(TableCell[] row, String[] summaryNeedList,
			Map condition) throws Exception {
		if (condition.containsKey(_REPORT_GATHER_COL_0)) {// 不需要过滤
			return row;
		}
		if (row.length != summaryNeedList.length) {
			throw new Exception("对齐列数不一致!");
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
	 * 取得一行表格数据
	 * 
	 * @param _dataHeader
	 *            表格数据
	 * @param _alignHeader
	 *            表格对齐列
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
	 * 取得一行表格数据
	 * 
	 * @param _dataHeader
	 *            表格数据
	 * @param _alignHeader
	 *            表格对齐列
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

	public void setTitleHeader(Report report, String headerTitle,
			String[] dateTitle, String[] extendContent) throws ReportException {
		this
				.setTitleHeader(report, headerTitle, dateTitle, extendContent,
						true);
	}

	/**
	 * 设置报表报表头
	 * 
	 * @param report
	 *            报表对象
	 * @param headerTitle
	 *            报表头标题
	 * @throws Exception
	 */
	public void setTitleHeader(Report report, String headerTitle,
			String[] dateTitle, String[] extendContent, boolean isdefault)
			throws ReportException {
		// *****************设置报表头*********************
		Table headerTable = new Table();
		// 设置表格的宽度比例(百分比)
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
		// 1.报表标题
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
			tc.setCssClass(Report.TITLE_TYPE);// 设置标题样式
		}
		// 2.扩展
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

		// 3.时间标题
		if (dateTitle != null && dateTitle.length > 0) {
			if (colCount <= 4) {
				tr = new TableRow(colCount);
				headerTable.addRow(tr);
				tc = tr.getCell(0);
				tc.setContent(isdefault ? "日期范围:" : "" + dateTitle[0]);
				tc.setAlign(Rectangle.ALIGN_LEFT);
				tc.setCssClass(Report.DATA_TYPE);
				tc = tr.getCell(colCount - 1);
				tc.setContent(isdefault ? "查询日期:" : "" + dateTitle[1]);
				tc.setCssClass(Report.DATA_TYPE);
				tc.setAlign(Rectangle.ALIGN_RIGHT);
			} else {
				tr = new TableRow(colCount);
				headerTable.addRow(tr);
				tc = tr.getCell(0);
				tc.setContent(isdefault ? "日期范围:" : "" + dateTitle[0]);
				tc.setAlign(Rectangle.ALIGN_LEFT);
				tc.setCssClass(Report.DATA_TYPE);
				tc.setColSpan(2);
				tc = tr.getCell(1);
				tc.setIsHidden(true);
				tc = tr.getCell(colCount - 2);
				tc.setColSpan(2);
				tc.setContent(isdefault ? "查询日期:" : "" + dateTitle[1]);
				tc.setCssClass(Report.DATA_TYPE);
				tc.setAlign(Rectangle.ALIGN_RIGHT);
				tc = tr.getCell(colCount - 1);
				tc.setIsHidden(true);
				tc.setCssClass(Report.DATA_TYPE);
			}

		}
		// *****************end 设置报表头*********************
	}

	/**
	 * 根据报表对象生成excel格式的报表.
	 * 
	 * @param dataTable
	 *            数据对象
	 * @param headerSet
	 *            数据列List list, String dataHeaderSet
	 * @throws Exception
	 */
	public Report getReportList(Collection result, String[] dataHeaderSet,
			String[] headerSet) throws Exception {
		return getReportList(result, dataHeaderSet, headerSet, null);
	}

	/**
	 * 根据报表对象生成excel格式的报表.
	 * 
	 * @param dataTable
	 *            数据对象
	 * @param headerSet
	 *            数据列List list, String dataHeaderSet
	 * @param alignHeaderSet
	 *            Map<String, Integer> 对齐方式 默认居左 Rectangle.ALIGN_LEFT居左
	 *            Rectangle.ALIGN_CENTER居中 Rectangle.ALIGN_RIGHT居右
	 * @throws Exception
	 */
	public Report getReportList(Collection result, String[] dataHeaderSet,
			String[] headerSet, Map<String, Integer> alignHeaderSet)
			throws Exception {
		// 获得原始数据表格
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
		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end 设置报表主体部分**************

		// *****************设置表格的属性********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		// t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end 设置表格的属性********************

		// *****************设置报表主体表格的列头*********************/
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
	 * 设置报表简单报表头
	 * 
	 * @param report
	 *            报表对象
	 * @param headerTitle
	 *            报表头标题
	 * @throws Exception
	 */
	public void setSimpleTitleFooter(Report report, String headerTitle)
			throws ReportException {
		// *****************设置报表头*********************
		Table headerTable = new Table();
		// 设置表格的宽度比例(百分比)
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
		tc.setCssClass(Report.TITLE_TYPE);// 设置标题样式
		// *****************end 设置报表头*********************
	}

	/**
	 * 设置报表主体表格的列头
	 * 
	 * @param dataTable
	 *            数据对象
	 * @param headerSet
	 *            数据列
	 * @throws Exception
	 */
	public Report setSimpleColHeader(Table dataTable, String[] headerSet)
			throws Exception {
		// 获得原始数据表格
		Table t = dataTable;

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end 设置报表主体部分**************

		// *****************设置表格的属性********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end 设置表格的属性********************

		// *****************设置报表主体表格的列头*********************/

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
	 * 设置报表主体表格的列头
	 * 
	 * @param dataTable
	 *            数据对象
	 * @param headerSet
	 *            数据列
	 * @throws Exception
	 */
	public Report setSimpleColHeader(Table dataTable, List<TableCell> headerSet)
			throws Exception {
		// 获得原始数据表格
		Table t = dataTable;

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end 设置报表主体部分**************

		// *****************设置表格的属性********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end 设置表格的属性********************

		// *****************设置报表主体表格的列头*********************/

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
	 * 设置报表复杂主体表格的列头 目前只支持两列
	 * 
	 * @param dataTable
	 *            数据对象
	 * @param headerSet
	 *            数据列
	 * @throws Exception
	 */
	public Report setComplexColHeader(Table dataTable,
			List<TableCell> headerSet1, List<TableCell> headerSet2)
			throws Exception {
		return setComplexColHeader(dataTable, headerSet1, headerSet2, false);
	}

	/**
	 * 设置报表复杂主体表格的列头 两列
	 * 
	 * @param dataTable
	 *            数据对象
	 * @param headerSet
	 *            数据列
	 * @throws Exception
	 */
	public Report setComplexColHeader(Table dataTable,
			List<TableCell> headerSet1, List<TableCell> headerSet2,
			boolean mergeSameCells) throws Exception {
		// 获得原始数据表格
		Table t = dataTable;

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end 设置报表主体部分**************

		// *****************设置表格的属性********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end 设置表格的属性********************
		// *****************设置报表主体表格的列头*********************/

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

		// ***********按指定列分组**********
		if (mergeSameCells) {
			ReportManager rm = new ReportManager();
			int x = headerSet2.size() - 1;
			int[] cols = new int[x];
			for (int i = 0; i < x; i++) {
				cols[i] = i;
			}
			// 合并列中相邻的同值单元
			int[] rows = { 0, 1 };
			th = rm.mergeHeaderSameCells(th, cols, rows);
		}

		report.getBody().setTableColHeader(th);
		return report;
	}

	/**
	 * 设置报表复杂主体表格的列头 三列
	 * 
	 * @param dataTable
	 *            数据对象
	 * @param headerSet
	 *            数据列
	 * @throws Exception
	 */
	public Report setComplexColHeader(Table dataTable,
			List<TableCell> headerSet0, List<TableCell> headerSet1,
			List<TableCell> headerSet2, boolean mergeSameCells)
			throws Exception {
		// 获得原始数据表格
		Table t = dataTable;

		// 定义报表对象
		Report report = new Report();

		// **************设置报表主体部分**************
		ReportBody body = new ReportBody();
		body.setData(t);
		report.setBody(body);
		// **************end 设置报表主体部分**************

		// *****************设置表格的属性********************
		t.setAlign(Rectangle.ALIGN_CENTER);
		t.setWidth(width);
		t.setBorder(1);
		t.setBordercolor(new java.awt.Color(0x000000));
		// *****************end 设置表格的属性********************
		// *****************设置报表主体表格的列头*********************/

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

		// ***********按指定列分组**********
		if (mergeSameCells) {
			ReportManager rm = new ReportManager();
			int x = headerSet2.size() - 1;
			int[] cols = new int[x];
			for (int i = 0; i < x; i++) {
				cols[i] = i;
			}
			// 合并列中相邻的同值单元
			int[] rows = { 0, 1, 2 };
			th = rm.mergeHeaderSameCells(th, cols, rows);
		}

		report.getBody().setTableColHeader(th);

		return report;
	}

}
