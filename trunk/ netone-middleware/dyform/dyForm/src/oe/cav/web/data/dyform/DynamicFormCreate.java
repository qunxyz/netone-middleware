package oe.cav.web.data.dyform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.web.data.dyform.utils.DefaultElementAdder;
import oe.cav.web.data.dyform.utils.DymaticFormButton;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.cav.web.data.dyform.utils.DynamicFormElementAdder;

import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Table;

public class DynamicFormCreate {

	/**
	 * 创建表单
	 * 
	 * @param columns
	 * @param dyform
	 */
	public static void generateCreateView(String rootpath, String formcode,
			String fatherlsh, List columns, DynaUIForm dyform, String hidesome,
			Map columnAccess) {
		DefaultElementAdder.addHideColumn(dyform, "formcode", formcode);
		DefaultElementAdder.addHideColumn(dyform, "hidesome", hidesome);
		DefaultElementAdder.addHideColumn(dyform, "lsh", "");
		DefaultElementAdder.addHideColumn(dyform, "fatherlsh", fatherlsh);

		List listCheck = new ArrayList();

		Table table = new Table();
		for (Iterator itr = columns.iterator(); itr.hasNext();) {
			TCsColumn busEach = (TCsColumn) itr.next();

			String htmltypes = busEach.getHtmltype();
			System.out.println(htmltypes);
			String columnName = busEach.getColumname();
			String columnId = busEach.getColumncode().toLowerCase();
			boolean musk = ColumnExtendInfo._BOOLEAN_TRUE.equals(busEach
					.getMusk()) ? true : false;
			String valuelist = busEach.getValuelist();

			boolean readonly = false;
			if ("readonly".equals(busEach.getOpemode())) {
				readonly = true;
			}

			// String accessmode = (String) columnAccess.get(columnId);
			// boolean readonly = ColumnExtendInfo._ACCESS_TYPE_R
			// .equals(accessmode);
			String accessmode = ColumnExtendInfo._ACCESS_TYPE_W;

			if (!busEach.isUseable()) {
				DefaultElementAdder.addHideColumn(dyform,
						busEach.getColumnid(), valuelist);
				System.out.println(busEach.getColumnid());
				continue;
			}
			listCheck.add(busEach);

			if (ColumnExtendInfo._ACCESS_TYPE_H.equals(accessmode)) {
				DynamicFormElementAdder.addHidden(columnId, valuelist, dyform);
			} else if (ColumnExtendInfo._HTML_TYPE_DATETIME.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_TIME.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_DATE.equals(htmltypes)) {
				DynamicFormElementAdder.addDatetimeRow(table, columnName,
						htmltypes, columnId, valuelist, musk, readonly, false);
			} else if (ColumnExtendInfo._HTML_TYPE_SELECT.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_SELECT_KV.equals(htmltypes)) {
				DynamicFormElementAdder.addSelectRow(table, columnName,
						htmltypes, columnId, valuelist, "", musk, readonly,
						false,null,false);
			} else if (ColumnExtendInfo._HTML_TYPE_TEXTAREA.equals(htmltypes)) {
				DynamicFormElementAdder.addTextAreaRow(table, columnName,
						columnId, valuelist, musk, readonly, false);
			} else if (ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltypes)) {
				// 这些全部不用了,统一采用外部资源来管理
			} else if (ColumnExtendInfo._HTML_TYPE_BUT.equals(htmltypes)) {
				// 这些全部不用了,统一采用外部资源来管理
			} else if (ColumnExtendInfo._HTML_TYPE_TREE.equals(htmltypes)) {
				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				DynamicFormElementAdder.addResourceWithSingle(rootpath, table,
						columnId, columnName, "", valuelist,readonly);
			} else if (ColumnExtendInfo._HTML_TYPE_TREE2.equals(htmltypes)) {
				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				DynamicFormElementAdder.addResourceWithMulti(rootpath, table,
						columnId, columnName, "", valuelist,readonly);
			} else if (ColumnExtendInfo._HTML_TYPE_SCRIPT.equals(htmltypes)) {
				DynamicFormElementAdder.addScript(table, columnId, columnName,
						valuelist, false, htmltypes, busEach
								.getExtendattribute());
			} else if (ColumnExtendInfo._HTML_TYPE_PORTAL_ITEM
					.equals(htmltypes)) {
				DynamicFormElementAdder.addPortalPage(null,table, columnId,
						columnName, valuelist);
			}

			else {
				DynamicFormElementAdder.addInputRow(table, columnName,
						htmltypes, columnId, valuelist,"", musk, readonly, false,null);
			}
		}
		dyform.addControl(table);
		// 添加表单按钮
		addButton(rootpath, dyform, formcode);
		// 添加表单校验参考信息
		DymaticFormCheck.addCheckInfo(listCheck, table);

	}

	/**
	 * 创建表单
	 * 
	 * @param columns
	 * @param dyform
	 */
	public static void generateCreateScriptView(String columnid, String cellid,
			DynaUIForm dyform) {

		Table table = new Table();

		DynamicFormElementAdder.addScriptView(table, cellid);

		dyform.addControl(table);

	}

	private static void addButton(String rootpath, DynaUIForm dyform,
			String formcode) {
		String commitAction = rootpath + "/data/data/createope.do";
		Table bottomAbout = DymaticFormButton.addBottonSave("确定", commitAction,
				true);
		dyform.addControl(bottomAbout);
	}

	private static void addForumButton(String rootpath, DynaUIForm dyform,
			String formcode) {

		String commitAction = rootpath + "/data/data/createforumope.do";
		Table bottomAbout = DymaticFormButton.addBottonSave("创建", commitAction,
				true);
		dyform.addControl(bottomAbout);
	}

}
