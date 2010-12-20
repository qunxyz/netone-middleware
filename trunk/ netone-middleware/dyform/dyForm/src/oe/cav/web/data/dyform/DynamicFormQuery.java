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
	 * 创建表单
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
				.setValue("<em>如果需要模糊查询那么可以在输入的查询值前后加入模糊匹配符号%</em>");
		DynamicFormElementAdder
				.addRow(
						"--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
						table, label, "");

		DynamicFormElementAdder.addInputRow(table, "参与者[participant]",
				ColumnExtendInfo._HTML_TYPE_TEXT, "participant", "","", false,
				false, false,"");

		DynamicFormElementAdder.addDatetimeRow(table, "创建日期[created]",
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
		.setValue("<em>如果需要做复杂的查询应用,您用可以根据每个字段的定义名(在本界面中[]中定义的命名)来编写基于SQL92语法的子条件,注意语句开始必须有AND 或者 OR 逻辑连接符号.</em>");
		DynamicFormElementAdder.addRow("提示:", table, label2, "");
		DynamicFormElementAdder.addTextAreaRow(table, "特殊条件", "superCondition",
				"", false, false, false);

		dyform.addControl(table);

		addBotton(ae, dyform, formcode);
		// 添加表单校验参考信息
		DymaticFormCheck.addCheckInfo(list, table);
	}

	private static void addBotton(ActionEvent ae, DynaUIForm dyform,
			String formcode) {

		String commitAction = ae.getRequest().getContextPath()
				+ "/data/data/list.do?quetip=true&mode="
				+ ae.getRequest().getParameter("mode");
		Table bottomAbout = DymaticFormButton.addBottonSave("查询", commitAction,
				true);
		dyform.addControl(bottomAbout);

		// Submit butQuery = new Submit();
		// butQuery.setName("but1");
		// butQuery.setValue("查询");
		// butQuery.setAction(rootpath + "/data/data/list.do?quetip=true");
		//
		// Button butBack = new Button();
		// butBack.setName("but3");
		// butBack.setValue("关闭");
		// butBack.setOnClick("javascript:windows.close()");
		//
		// Table table = new Table();
		// TableRow rows = table.insertRow();
		// rows.insertCol().setUI(butQuery);
		// rows.insertCol().setUI(butBack);
		// return table;
	}
}
