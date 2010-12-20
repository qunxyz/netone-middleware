package oe.cav.web.data.dyform;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.BusExtendInfo;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.web.data.dyform.utils.DefaultElementAdder;
import oe.cav.web.data.dyform.utils.DynamicFormElementAdder;

import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Button;
import com.rongji.webframework.ui.html.Select;
import com.rongji.webframework.ui.html.Submit;
import com.rongji.webframework.ui.html.Table;
import com.rongji.webframework.ui.html.TableRow;

public class DynamicFormExport {
	/**
	 * 创建表单
	 * 
	 * @param columns
	 * @param dyform
	 */
	public static void generateView(String rootpath, String formcode,
			List columns, DynaUIForm dyform, Map columnAccess) {
		DefaultElementAdder.addHideColumn(dyform, "formcode", formcode);
		DefaultElementAdder.addHideColumn(dyform, "statusinfo",
				BusExtendInfo._STATUS_NORMAL);
		Table table = new Table();
		DynamicFormElementAdder.addInputRow(table, "参与者",
				ColumnExtendInfo._HTML_TYPE_TEXT, "participant", "","",
				false, false, false,"");
		
		DynamicFormElementAdder.addDatetimeRow(table, "创建日期",
				ColumnExtendInfo._HTML_TYPE_DATE, "created", "", false, false, false);

		for (Iterator itr = columns.iterator(); itr.hasNext();) {

			TCsColumn busEach = (TCsColumn) itr.next();
			String htmltypes = busEach.getHtmltype();
			String columnName = busEach.getColumname();
			String columnId = busEach.getColumnid();
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
						true,null,false);
			} else if (ColumnExtendInfo._HTML_TYPE_TEXTAREA.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltypes)) {
				//
			} else if(!ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltypes)&&
					  !ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltypes)){
				DynamicFormElementAdder.addInputRow(table, columnName,
						htmltypes, columnId, valuelist,"", musk,
						readonly, true,"");
			}
		}

		DynamicFormElementAdder.addTextAreaRow(table, "特殊条件","superCondition",
				"", false,false, false);

		dyform.addControl(table);

		Table bottomAbout = addBotton(rootpath, formcode);
		dyform.addControl(bottomAbout);
	}

	private static Table addBotton(String rootpath, String formcode) {
		
		Select select =new Select();
		select.setName("typeselect");
		select.options().addOption("xls", "Excel");
		select.options().addOption("pdf", "Pdf");
		
		Submit butExprort = new Submit();
		butExprort.setName("but2");
		butExprort.setValue("导出");
		butExprort.setAction(rootpath + "/data/data/exportope.do");

		Button butBack = new Button();
		butBack.setName("but3");
		butBack.setValue("返回");
		butBack.setOnClick("history.go(-1)");

		Table table = new Table();
		TableRow rows = table.insertRow();
		rows.insertCol().setUI(select);
		rows.insertCol().setUI(butExprort);
		rows.insertCol().setUI(butBack);
		return table;
	}
}
