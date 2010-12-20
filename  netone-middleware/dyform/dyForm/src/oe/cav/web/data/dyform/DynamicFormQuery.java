package oe.cav.web.data.dyform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.BusExtendInfo;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.web.data.dyform.utils.DefaultElementAdder;
import oe.cav.web.data.dyform.utils.DymaticFormButton;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.cav.web.data.dyform.utils.DynamicFormElementAdder;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.DynaUIForm;

import com.rongji.webframework.ui.html.Label;

import com.rongji.webframework.ui.html.Table;

public class DynamicFormQuery {

	/**
	 * ������
	 * 
	 * @param columns
	 * @param dyform
	 */
	public static void generateQueryView(ActionEvent ae, String formcode,
			List columns, DynaUIForm dyform, Map columnAccess) {
		DefaultElementAdder.addHideColumn(dyform, "formcode", formcode);
		DefaultElementAdder.addHideColumn(dyform, "statusinfo",
				BusExtendInfo._STATUS_NORMAL);
		Table table = new Table();

		Label label = new Label();
		label
				.setValue("<em>�����Ҫģ����ѯ��ô����������Ĳ�ѯֵǰ�����ģ��ƥ�����%</em>");
		DynamicFormElementAdder
				.addRow(
						"--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
						table, label, "");

		DynamicFormElementAdder.addInputRow(table, "������[participant]",
				ColumnExtendInfo._HTML_TYPE_TEXT, "participant", "","", false,
				false, false,"");

		DynamicFormElementAdder.addDatetimeRow(table, "��������[created]",
				ColumnExtendInfo._HTML_TYPE_DATE, "created", "", false, false,
				false);
		List list = new ArrayList();
		for (Iterator itr = columns.iterator(); itr.hasNext();) {

			TCsColumn busEach = (TCsColumn) itr.next();
			list.add(busEach);
			String htmltypes = busEach.getHtmltype();
			String columnName = busEach.getColumname();

			String columnId = busEach.getColumnid();
			columnName = columnName + "[" + columnId + "]";
			boolean musk = ColumnExtendInfo._BOOLEAN_TRUE.equals(busEach
					.getMusk()) ? true : false;
			String valuelist = busEach.getValuelist();
			String checktypes = busEach.getChecktype();
			// String accessmode = (String) columnAccess.get(columnId);
			// boolean readonly = ColumnExtendInfo._ACCESS_TYPE_R
			// .equals(accessmode);
			String accessmode = ColumnExtendInfo._ACCESS_TYPE_W;
			boolean readonly = false;

			if (ColumnExtendInfo._ACCESS_TYPE_H.equals(accessmode)) {
				//
			} else if (ColumnExtendInfo._HTML_TYPE_DATETIME.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_TIME.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_DATE.equals(htmltypes)) {
				DynamicFormElementAdder.addDatetimeRow(table, columnName,
						htmltypes, columnId, valuelist, musk, readonly, true);
			} else if (ColumnExtendInfo._HTML_TYPE_SELECT.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_SELECT_KV.equals(htmltypes)) {
				DynamicFormElementAdder.addSelectRow(table, columnName,
						htmltypes, columnId, valuelist, "", musk, readonly,
						true, null, false);
			} else if (ColumnExtendInfo._HTML_TYPE_TEXTAREA.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltypes)) {
				//
			} else if (!ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltypes)
					&& !ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltypes)) {
				DynamicFormElementAdder.addInputQueryRow(table, columnName,
						htmltypes, columnId, valuelist, checktypes);
			}
		}
		Label label2 = new Label();
		label2
		.setValue("<em>�����Ҫ�����ӵĲ�ѯӦ��,���ÿ��Ը���ÿ���ֶεĶ�����(�ڱ�������[]�ж��������)����д����SQL92�﷨��������,ע����俪ʼ������AND ���� OR �߼����ӷ���.</em>");
		DynamicFormElementAdder.addRow("��ʾ:", table, label2, "");
		DynamicFormElementAdder.addTextAreaRow(table, "��������", "superCondition",
				"", false, false, false);

		dyform.addControl(table);

		addBotton(ae, dyform, formcode);
		// ��ӱ�У��ο���Ϣ
		DymaticFormCheck.addCheckInfo(list, table);
	}

	private static void addBotton(ActionEvent ae, DynaUIForm dyform,
			String formcode) {

		String commitAction = ae.getRequest().getContextPath()
				+ "/data/data/list.do?quetip=true&mode="
				+ ae.getRequest().getParameter("mode");
		Table bottomAbout = DymaticFormButton.addBottonSave("��ѯ", commitAction,
				true);
		dyform.addControl(bottomAbout);

		// Submit butQuery = new Submit();
		// butQuery.setName("but1");
		// butQuery.setValue("��ѯ");
		// butQuery.setAction(rootpath + "/data/data/list.do?quetip=true");
		//
		// Button butBack = new Button();
		// butBack.setName("but3");
		// butBack.setValue("�ر�");
		// butBack.setOnClick("javascript:windows.close()");
		//
		// Table table = new Table();
		// TableRow rows = table.insertRow();
		// rows.insertCol().setUI(butQuery);
		// rows.insertCol().setUI(butBack);
		// return table;
	}
}
