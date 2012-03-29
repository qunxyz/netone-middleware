package oe.cav.web.data.dyform;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.FormDymaticTable;
import oe.cav.web.data.dyform.utils.DefaultElementAdder;
import oe.cav.web.data.dyform.utils.DymaticFormButton;
import oe.cav.web.data.dyform.utils.DynamicFormElementAdder;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.SequencedHashMap;
import org.apache.commons.lang.StringUtils;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Label;
import com.rongji.webframework.ui.html.Link;
import com.rongji.webframework.ui.html.Table;

public class DynamicFormModify {

	/**
	 * 修改表单
	 * 
	 * @param columns
	 * @param dyform
	 * @param ae
	 * @param bus
	 *            返回的是cellid 该记录被注册入页资源的标志
	 * @param continuenew
	 *            目前由于创建和修改统一在一起，为了创建可以连续创建（不用关闭窗口再重新打开）加入这个属性来实现连续创建
	 */
	public static void generateModifyView(ActionEvent ae, TCsBus bus,
			List columns, DynaUIForm dyform, String hidesome, Map columnAccess,
			String fatherlsh, TCsForm formx) {
		boolean continuenew = false;
		boolean autoclose = false;

		boolean onlyview = "yes".equals(ae.getAttribute("onlyview"));

		String autocloseStr = ae.getParameter("autoclose");
		String continuenewStr = ae.getParameter("continuenew");
		if (autocloseStr == null || "y".equalsIgnoreCase(autocloseStr)) {
			autoclose = true;
		}
		if (continuenewStr == null || "y".equalsIgnoreCase(continuenewStr)) {
			continuenew = true;
		}

		String rootpath = ae.getRequest().getContextPath();
		StringBuffer cellid = new StringBuffer();

		String formcode = bus.getFormcode();
		String lsh = bus.getLsh();
		DefaultElementAdder.addHideColumn(dyform, "hidesome", hidesome);
		DefaultElementAdder.addHideColumn(dyform, "formcode", formcode);
		DefaultElementAdder.addHideColumn(dyform, "created", bus.getCreated());
		DefaultElementAdder.addHideColumn(dyform, "participant", bus
				.getParticipant());

		DefaultElementAdder.addHideColumn(dyform, "extendattribute", bus
				.getExtendattribute());
		DefaultElementAdder.addHideColumn(dyform, "lsh", lsh);
		DefaultElementAdder.addHideColumn(dyform, "fatherlsh", bus
				.getFatherlsh());
		DefaultElementAdder.addHideColumn(dyform, "statusinfo", bus
				.getStatusinfo());

		Table table = new Table();
		table.setAttribute("class", "table");
		List listCheck = new ArrayList();
		// 增加表单的头部的用户信息
		Label label = new Label();
		label.setValue("&nbsp;[" + bus.getParticipant() + ","
				+ bus.getCreated() + "]");
		dyform.addControl(label);

		// 增加表单的头部描述信息
		String ext = "";
		try {
			DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
			ext = dysc.loadForm(formcode).getExtendattribute();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ext != null && !ext.equals("")) {
			Label label2 = new Label();
			label2.setValue(ext);
			dyform.addControl(label2);
		}

		// 暂存表单信息
		Map columnCache = new SequencedHashMap();

		int maxcol = FormDymaticTable.maxCol(formcode);
		for (Iterator itr = columns.iterator(); itr.hasNext();) {

			TCsColumn busEach = (TCsColumn) itr.next();
			String htmltypes = busEach.getHtmltype();
			String viewtype = busEach.getViewtype();
			String columnName = busEach.getColumname();
			String columnId = busEach.getColumncode();
			boolean musk = ColumnExtendInfo._BOOLEAN_TRUE.equals(busEach
					.getMusk()) ? true : false;

			int rowAndCol[] = FormDymaticTable.getRowCol(formcode, columnId);

			if (!busEach.isUseable()) {
				// DefaultElementAdder.addHideColumn(dyform,
				// busEach.getColumnid(), "");
				continue;
			}
			listCheck.add(busEach);

			// String accessmode = (String) columnAccess.get(columnId);
			// boolean readonly = ColumnExtendInfo._ACCESS_TYPE_R
			// .equals(accessmode);
			String accessmode = ColumnExtendInfo._ACCESS_TYPE_W;
			boolean readonly = false;
			if ("1".equals(busEach.getOpemode())) {
				readonly = true;
			}

			String valuelist = busEach.getValuelist();

			String valueis = null;
			try {
				valueis = (String) BeanUtils.getProperty(bus, columnId
						.toLowerCase());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ColumnExtendInfo._ACCESS_TYPE_H.equals(accessmode)) {
				DynamicFormElementAdder.addHidden(columnId, valuelist, dyform);
			} else if (ColumnExtendInfo._HTML_TYPE_DATETIME.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_TIME.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_DATE.equals(htmltypes)) {
				DynamicFormElementAdder.addDatetimeRow(table, columnName,
						htmltypes, columnId, valueis, musk, readonly, false,
						rowAndCol[0], rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_SELECT.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_SELECT_KV.equals(htmltypes)) {
				DynamicFormElementAdder.addSelectRow(table, columnName,
						htmltypes, columnId, valuelist, valueis, musk,
						readonly, false, null, false, rowAndCol[0],
						rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_TEXTAREA.equals(htmltypes)) {
				DynamicFormElementAdder.addTextAreaRow(table, columnName,
						columnId, valueis, musk, readonly, false, rowAndCol[0],
						rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_TREE.equals(htmltypes)) {

				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				DynamicFormElementAdder.addResourceWithSingle(rootpath, table,
						columnId, columnName, valueis, valuelist, readonly,
						rowAndCol[0], rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_TREE2.equals(htmltypes)) {
				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				DynamicFormElementAdder.addResourceWithMulti(rootpath, table,
						columnId, columnName, valueis, valuelist, readonly,
						rowAndCol[0], rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_HUMAN.equals(htmltypes)) {
				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				DynamicFormElementAdder.addResourceWithSingleHuman(rootpath,
						table, columnId, columnName, valueis, valuelist,
						readonly, rowAndCol[0], rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_HUMAN2.equals(htmltypes)) {
				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				DynamicFormElementAdder.addResourceWithMultiHuman(rootpath,
						table, columnId, columnName, valueis, valuelist,
						readonly, rowAndCol[0], rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_SCRIPT.equals(htmltypes)) {
				DynamicFormElementAdder.addScript(table, columnId, columnName,
						valuelist, false, htmltypes, busEach
								.getExtendattribute(), rowAndCol[0],
						rowAndCol[1], columnCache);
			} else if (ColumnExtendInfo._HTML_TYPE_PORTAL_ITEM
					.equals(htmltypes)) {
				DynamicFormElementAdder.addPortal(rootpath, table, columnId,
						columnName, valueis, valuelist, readonly, rowAndCol[0],
						rowAndCol[1], columnCache, onlyview);
				// if (bus.getLsh() != null) {// 只有记录创建后才能加 Portal页
				// String cellidPre = DynamicFormElementAdder.addPortalPage(
				// bus, table, columnId, columnName, valuelist,
				// rowAndCol[0], rowAndCol[1], columnCache);
				// cellid.append("," + cellidPre);
				// }

			} else if (ColumnExtendInfo._HTML_TYPE_FCK_ITEM.equals(htmltypes)) {

				DynamicFormElementAdder.addFck(rootpath, table, columnId,
						columnName, valueis, valuelist, readonly, rowAndCol[0],
						rowAndCol[1], columnCache, onlyview);

				// if (bus.getLsh() != null) {// 只有记录创建后才能加 Portal页
				// // String cellidPre = DynamicFormElementAdder.addFck(
				// // bus, table, columnId, columnName, valuelist);
				// // cellid.append("," + cellidPre);
				// boolean onview = false;
				// DynamicFormElementAdder.addFckRow(ae, bus, table,
				// columnName, columnId, valuelist, onview,
				// rowAndCol[0], rowAndCol[1], columnCache);
				// }

			} else {
				DynamicFormElementAdder.addInputRow(table, columnName,
						htmltypes, columnId, valueis, valuelist, musk,
						readonly, false, lsh, busEach.getExtendattribute(),
						rowAndCol[0], rowAndCol[1], columnCache, busEach
								.getChecktype(), viewtype, bus);
			}
		}

		FormDymaticTable.makeTable(columnCache, table, maxcol);
		dyform.addControl(table);
		// 添加按钮
		if (!onlyview) {
			addButton(rootpath, dyform, formcode, bus, fatherlsh, continuenew,
					autoclose, formx);
		}

		// 添加表单校验参考信息
		// DymaticFormCheck.addCheckInfo(listCheck, table);
		// if (cellid != null && cellid.length() > 0) {
		// ae.setAttribute("cellid", cellid.substring(1));
		// } else {
		// ae.setAttribute("cellid", "");
		// }

	}

	private static void addButton(String rootpath, DynaUIForm dyform,
			String formcode, TCsBus bus, String fatherlsh, boolean continuenew,
			boolean autoclose, TCsForm formx) {
		String extendattribute = bus.getExtendattribute();
		if (extendattribute != null && !extendattribute.equals("")) {
			// 说明该表单是集成在工作流中应用的
			String wfinfo = StringUtils.substringBetween(extendattribute,
					"wf[", "]");
			if (wfinfo != null && wfinfo.length() > 0) {

				String commitAction = rootpath + "/data/data/modifyope.do";
				Table bottomAbout = DymaticFormButton.addBottonSave("保存",
						commitAction, autoclose);
				dyform.addControl(bottomAbout);

				// String wfAction = rootpath
				// + "/data/data/modifyope.do?wf=commit";
				// Table bottomAbout2 = DymaticFormButton.addBottonSave("保存提交",
				// wfAction, true);
				//
				// dyform.addControl(bottomAbout2);
				return;
			}
		}

		String commitAction = rootpath + "/data/data/modifyope.do";
		Table bottomAbout = DymaticFormButton.addBottonSave("保存", commitAction,
				autoclose);
		dyform.addControl(bottomAbout);
		// 为了方便起见，希望创建能不关闭表单，继续一直创建
		if (continuenew) {
			String commitActionx = rootpath
					+ "/data/showdata/createview.do?fatherlsh=" + fatherlsh
					+ "&formcode=" + formcode;
			Table bottomAboutx = DymaticFormButton.addBottonOpen("继续创建",
					commitActionx);
			dyform.addControl(bottomAboutx);
		}

		if (formx != null) {
			String subformlist = formx.getSubform();
			String subform[] = null;
			if (subformlist != null && !subformlist.equals("")) {
				subform = subformlist.split(",");

				for (int i = 0; i < subform.length; i++) {
					try {
						String subformcode = StringUtils.substringBetween(
								subform[i], "[", "]");
						String subformName = StringUtils.substringBefore(
								subform[i], "[");
						Link link = new Link();
						link.setTitle("[子表单-" + subformName + "]");
						String href = rootpath + "/data/data/list.do?lsh="
								+ bus.getLsh() + "&formcode=" + subformcode;
						link.setHref(href);
						link.setAttribute("target", "_blank");
						dyform.addControl(link);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
}