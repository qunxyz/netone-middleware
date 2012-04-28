package com.lucaslee.report;

import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * 样式表引擎。<BR>
 * 将容器(table,row..)的type属性向元素传递，如果元素无type属性，最后在cell里将样式表class设置为type值。
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:Lucas-lee Soft
 * </p>
 * 
 * @author Lucas Lee
 * @version 1.0
 */
public class CssEngine {
	public CssEngine() {
	}

	/**
	 * 处理样式表中class属性。 将父元素的type属性赋给没有赋属性的子元素。会改变入口参数。
	 * 
	 * @param t
	 * @return
	 * @throws ReportException
	 */
	public static Table applyCss(Table t) throws ReportException {
		if (t == null)
			return null;

		Table result = t.cloneAll();

		// 按行处理
		String type = result.getType();
		TableRow tr = null;

		// 注意：这里不可象在应用行样式表时做table/type为空则退出，需要继续应用行样式表。
		for (int i = 0; i < result.getRowCount(); i++) {
			tr = result.getRow(i);
			if (tr.getType() == null) {
				tr.setType(type);
			}
			// 应用行的样式表
			result.setRow(i, applylCss(tr));
		}

		// 按列处理

		return result;
	}

	/**
	 * 处理样式表中class属性。
	 * 
	 * @param tr
	 * @return
	 * @throws ReportException
	 */
	public static TableRow applylCss(TableRow tr) throws ReportException {
		if (tr == null)
			return null;

		TableRow result = tr.cloneAll();
		String type = result.getType();
		TableCell tc = null;
		if (type != null) {// 行对象无样式表则不用向单元赋值。
			for (int i = 0; i < result.getCellCount(); i++) {
				tc = result.getCell(i);

				if (tc.getCssClass() == null) {
					tc.setCssClass(type);
				}
			}
		}

		return result;
	}
}