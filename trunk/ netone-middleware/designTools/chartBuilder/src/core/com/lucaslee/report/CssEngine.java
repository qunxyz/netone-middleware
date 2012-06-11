package com.lucaslee.report;

import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

/**
 * ��ʽ�����档<BR>
 * ������(table,row..)��type������Ԫ�ش��ݣ����Ԫ����type���ԣ������cell�ｫ��ʽ��class����Ϊtypeֵ��
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
	 * ������ʽ����class���ԡ� ����Ԫ�ص�type���Ը���û�и����Ե���Ԫ�ء���ı���ڲ�����
	 * 
	 * @param t
	 * @return
	 * @throws ReportException
	 */
	public static Table applyCss(Table t) throws ReportException {
		if (t == null)
			return null;

		Table result = t.cloneAll();

		// ���д���
		String type = result.getType();
		TableRow tr = null;

		// ע�⣺���ﲻ������Ӧ������ʽ��ʱ��table/typeΪ�����˳�����Ҫ����Ӧ������ʽ��
		for (int i = 0; i < result.getRowCount(); i++) {
			tr = result.getRow(i);
			if (tr.getType() == null) {
				tr.setType(type);
			}
			// Ӧ���е���ʽ��
			result.setRow(i, applylCss(tr));
		}

		// ���д���

		return result;
	}

	/**
	 * ������ʽ����class���ԡ�
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
		if (type != null) {// �ж�������ʽ��������Ԫ��ֵ��
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