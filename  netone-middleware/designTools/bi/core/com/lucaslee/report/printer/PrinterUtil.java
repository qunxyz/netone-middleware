package com.lucaslee.report.printer;

import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.crosstable.CrossTable;

/**
 * ��ӡ�������ࡣ
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
	 * @todo ���� �ӵ�Ԫ�����ý�����ͷ�����ݡ�
	 * @param tc
	 *            �����������ĵ�Ԫ��CssClass����һ���ǽ�����ͷ�ı�ͷ.
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