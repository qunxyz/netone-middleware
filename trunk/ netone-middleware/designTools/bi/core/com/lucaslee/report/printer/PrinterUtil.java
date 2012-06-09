package com.lucaslee.report.printer;

import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.crosstable.CrossTable;

/**
 * 打印机工具类。
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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PrinterUtil {
	public PrinterUtil() {
	}

	/**
	 * @todo 完善 从单元格里获得交叉表表头的内容。
	 * @param tc
	 *            包含交叉表定义的单元格。CssClass类型一定是交叉表表头的表头.
	 * @return
	 */
	public static String[] getCrossHeadHeadContent(TableCell tc) {
		CrossTable crossTab = (CrossTable) tc.getContent();

		int count = crossTab.getColHeader().length
				+ crossTab.getRowHeader().length + 1;

		String[] strs = new String[count];
		for (int i = 0; i < crossTab.getColHeader().length; i++) {
			strs[i] = crossTab.getColHeader()[i].getHeaderText();
		}
		strs[crossTab.getColHeader().length] = crossTab.getCrossCol()
				.getHeaderText();
		for (int i = 0; i < crossTab.getRowHeader().length; i++) {
			strs[i + crossTab.getColHeader().length + 1] = crossTab
					.getRowHeader()[i].getHeaderText();
		}
		return strs;
	}

}