package oe.cav.web.data.dyform.utils;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cms.service.CmsService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Br;
import com.rongji.webframework.ui.html.Button;
import com.rongji.webframework.ui.html.DateTime;
import com.rongji.webframework.ui.html.Div;
import com.rongji.webframework.ui.html.Hidden;
import com.rongji.webframework.ui.html.HtmlUI;
import com.rongji.webframework.ui.html.Input;
import com.rongji.webframework.ui.html.Label;
import com.rongji.webframework.ui.html.Link;
import com.rongji.webframework.ui.html.Select;
import com.rongji.webframework.ui.html.Table;
import com.rongji.webframework.ui.html.TableColumn;
import com.rongji.webframework.ui.html.TableRow;
import com.rongji.webframework.ui.html.TextArea;

public class DynamicFormElementAdder {

	public static void addTextAreaRow(Table table, String title,
			String columnId, String valuelist, boolean musk, boolean readonly,
			boolean ifQuery) {
		if (!ifQuery) {
			TextArea textArea = new TextArea();
			textArea.setName(columnId);

			textArea.setAttribute("cols", "80%");
			textArea.setAttribute("rows", "15");
			textArea.setReadOnly(readonly);
			if (readonly) {
				textArea.setStyle("background-color:#CCCCCC");
			}
			textArea.setValue(valuelist);
			textArea.setRequire(musk);

			addRow(title, table, textArea, columnId);
		}
	}

	public static void addViewInfoRow(Table table, String title,
			String valuelist, boolean first) {
		Label label = new Label();

		if (first) {
			label.setValue("<font size='2'><strong>" + valuelist
					+ "</strong></font>");
		} else {
			label.setValue("<font size='2'>" + title + ": " + valuelist
					+ "</font>");
		}
		label.setAttribute("width", "80%");
		addViewInfoRow(table, label);
	}

	public static String addViewInfoLink(String valuelist, String time,
			String partiticpant, String onclick, String reNum) {
		time = time.substring(0, 16);

		String link = "<font style='display:none'>$_</font><a href='" + onclick
				+ "' target='_blank'" + valuelist
				+ "</a><font style='display:none'>_$</font>";

		return "<tr><td nowrap>" + link + "</td><td>" + reNum
				+ "</td><td nowrap>" + partiticpant + "</td><td nowrap>" + time
				+ "</td></tr>";
	}

	public static void addInputRow(Table table, String title, String htmltypes,
			String columnId, String valueis, String valuelist, boolean musk,
			boolean readonly, boolean ifQuery, String lsh) {
		Input input = new Input();
		input.setType(htmltypes);
		input.setName(columnId);

		if (ColumnExtendInfo._HTML_TYPE_CHECKBOX.equals(htmltypes)) {

			if (valueis != null && !valueis.equals("")) {
				input.setAttribute("checked", "checked");// 如果有值那么让它被选中
			}
		}

		if (!ifQuery) {
			if (lsh == null) {
				// 是创建
				input.setValue(valuelist);
			} else {
				input.setValue(valueis);
			}
			input.setRequire(musk);
			if (readonly) {
				input.setReadOnly(readonly);
				input.setStyle("background-color:#CCCCCC");
			}

		}
		addRow(title, table, input, columnId);
	}

	public static void addFckRow(ActionEvent ae, TCsBus bus, Table table,
			String title, String columnId, String valuelist, boolean onview) {

		if (bus == null) {
			return;
		}
		String ext = bus.getExtendattribute();
		if (ext == null || ext.equals("")) {
			return;
		}
		String dyinfo = StringUtils.substringBetween(ext, "dyfck[", "]");
		String info[] = null;
		if (dyinfo != null) {
			info = dyinfo.split(";");
		}

		if (info != null && info.length > 0) {
			for (int i = 0; i < info.length; i++) {
				String[] preinfo = info[i].split(":");
				if (preinfo.length != 2) {
					continue;
				}
				String columnNaturalname = preinfo[1];

				if (columnId.equalsIgnoreCase(columnNaturalname)) {
					String url = null;
					if ("yes".equals(ae.getAttribute("onlyview"))) {
						// 用于显示中的FCK文档，存在两种模式，格式valuelist的说明，决定编辑还是只读，默认不填写是只读
						if ("outedit".equals(valuelist)) {
							// 编辑模式中的FCK
							Link input = new Link();
							input.setTitle("[在线文档-" + title + "]");
							input.setId(columnId);
							input
									.setHref("/fck/PagelistModifySvl?task=edit&pagename=fcklist&chkid="
											+ preinfo[0] + "\" target=\"_blank");
							if (onview) {
								addViewInfoRow(table, input);
							} else {
								addRow2Space(title, table, input, columnId);
							}

						} else {
							url = "/fck/PagelistViewSvl?pagename=simplefcklist&chkid="
									+ preinfo[0];
							Div div = new Div();

							div
									.setTitle("\" id='"
											+ columnId
											+ "'><iframe src='"
											+ url
											+ "' frameborder='0' width='900' height='400'></iframe><br id=\"");
							if (onview) {
								addViewInfoRow(table, div);
							} else {
								addRow2Space(title, table, div, columnId);
							}
						}

					} else {
						// 编辑模式中的FCK
						Link input = new Link();
						input.setTitle("[在线文档-" + title + "]");
						input.setId(columnId);
						input
								.setHref("/fck/PagelistModifySvl?task=edit&pagename=fcklist&chkid="
										+ preinfo[0] + "\" target=\"_blank");

						addRow(title, table, input, columnId);

					}

				}

			}

		}

	}

	public static void addInputQueryRow(Table table, String title,
			String htmltypes, String columnId, String valuelist,
			String checkTypes) {
		Input input = new Input();
		input.setType(htmltypes);
		input.setName(columnId);

		if (ColumnExtendInfo._HTML_TYPE_CHECKBOX.equals(htmltypes)) {
			input.setValue(valuelist);
			if (valuelist != null && !valuelist.equals("")) {
				input.setAttribute("checked", "checked");// 如果有值那么让它被选中
			}

		}

		addRow(title, table, input, columnId);
	}

	public static String addPortalPage(TCsBus bus, Table table,
			String columnId, String title, String valuelist) {
		if (bus == null) {
			return null;
		}
		String ext = bus.getExtendattribute();
		if (ext == null || ext.equals("")) {
			return null;
		}
		String dyinfo = StringUtils.substringBetween(ext, "dyportal[", "]");

		String cellid = null;
		String[] info = null;
		if (dyinfo != null) {
			info = dyinfo.split(";");
		}

		if (info != null && info.length > 0) {
			for (int i = 0; i < info.length; i++) {
				String[] preinfo = info[i].split(":");
				if (preinfo.length != 2) {
					continue;
				}
				String columnNaturalname = StringUtils.substringAfterLast(
						preinfo[1], ".");

				if (columnId.equalsIgnoreCase(columnNaturalname)) {
					cellid = preinfo[0];
					Div div = new Div();
					div.setId(preinfo[0]);

					addRow(title, table, div, columnId);
				}

			}

		}

		return cellid;

	}

	public static String addFck(TCsBus bus, Table table, String columnId,
			String title, String valuelist) {
		if (bus == null) {
			return null;
		}
		String ext = bus.getExtendattribute();
		if (ext == null || ext.equals("")) {
			return null;
		}
		String dyinfo = StringUtils.substringBetween(ext, "dyfck[", "]");

		String fckid = null;
		String info[] = dyinfo.split(";");
		if (info.length > 0) {
			for (int i = 0; i < info.length; i++) {
				String[] preinfo = info[i].split(":");
				if (preinfo.length != 2) {
					continue;
				}
				String columnNaturalname = preinfo[1];

				if (columnId.equalsIgnoreCase(columnNaturalname)) {
					fckid = preinfo[0];
					Div div = new Div();
					div.setId(preinfo[0]);

					addRow(title, table, div, columnId);
				}

			}

		}

		return fckid;

	}

	public static void addScript(Table table, String columnId, String title,
			String valuelist, boolean ifQuery, String htmltypes,
			String extendattribute) {

		if (!ifQuery) {

			String script = scriptExpress(valuelist);
			// 解析公式中,并且创建公式显示信息
			Div expressionView = new Div();
			expressionView.setStyle("width:400;background:#CCCCCC;");

			String expressionPersent = StringUtils.substringBetween(script,
					"<!--", "-->");
			Label labelTitle = new Label();
			labelTitle.setValue(expressionPersent == null ? "" : "<em>"
					+ expressionPersent + "</em>");
			expressionView.addUI(labelTitle);
			expressionView.addUI(new Br());
			expressionView.addUI(new Br());

			String expressionBody = StringUtils.substringAfter(script, "-->");
			String expressionParamer = StringUtils.substringBetween(
					expressionBody, "{", "}");
			String[] expressionParamers = StringUtils.split(expressionParamer,
					"&+");
			if (expressionParamers.length > 4) {
				expressionView.setStyle("width:"
						+ (expressionParamers.length * 90)
						+ ";background:#CCCCCC;");
			}

			StringBuffer butParamLink = new StringBuffer();

			for (int i = 0; i < expressionParamers.length; i++) {
				String[] paramer = StringUtils.split(expressionParamers[i],
						":=");
				Label label = new Label();
				label.setValue(paramer[1] + ":");
				expressionView.addUI(label);
				Input input = new Input();
				String htmlparamerId = columnId + "_" + paramer[0];
				input.setName(htmlparamerId);
				input.setStyle("width:40");
				expressionView.addUI(input);

				butParamLink.append(htmlparamerId + "@99@");
			}

			if (extendattribute != null && !extendattribute.equals("")) {

				Button butX = new Button();
				extendattribute = StringUtils.replace(extendattribute,
						"thiscol", columnId);
				extendattribute = StringUtils.replace(extendattribute, "\"",
						"'");
				butX.setValue("获得参数值");
				butX.setOnClick(extendattribute);
				expressionView.addUI(butX);
			}

			expressionView.addUI(new Br());
			expressionView.addUI(new Br());
			Button but = new Button();
			but.setValue("计算");
			but.setOnClick("calculate('" + columnId + "','" + butParamLink
					+ "','" + valuelist + "')");
			expressionView.addUI(but);

			Input inputResult = new Input();
			inputResult.setName(columnId);
			expressionView.addUI(inputResult);

			addRow(title, table, expressionView, columnId);

		} else {
			addInputRow(table, title, htmltypes, columnId, valuelist,
					valuelist, false, false, ifQuery, null);
		}
	}

	/**
	 * @param table
	 * @param cellid
	 */
	public static void addScriptView(Table table, String cellid) {

		String columnId = "columnx";
		String script = scriptExpress(cellid);
		// 解析公式中,并且创建公式显示信息
		Div expressionView = new Div();
		expressionView.setStyle("width:400;background:#CCCCCC;");

		String expressionPersent = StringUtils.substringBetween(script, "<!--",
				"-->");
		Label labelTitle = new Label();
		labelTitle.setValue(expressionPersent == null ? "" : "<em>"
				+ expressionPersent + "</em>");
		expressionView.addUI(labelTitle);
		expressionView.addUI(new Br());
		expressionView.addUI(new Br());

		String expressionBody = StringUtils.substringAfter(script, "-->");
		String expressionParamer = StringUtils.substringBetween(expressionBody,
				"{", "}");
		String[] expressionParamers = StringUtils
				.split(expressionParamer, "&+");
		if (expressionParamers.length > 4) {
			expressionView.setStyle("width:" + (expressionParamers.length * 90)
					+ ";background:#CCCCCC;");
		}

		StringBuffer butParamLink = new StringBuffer();
		Table tablex = new Table();

		for (int i = 0; i < expressionParamers.length; i++) {
			TableRow row = tablex.insertRow();
			String[] paramer = StringUtils.split(expressionParamers[i], ":=");
			Label label = new Label();
			label.setValue(paramer[1] + ":");
			row.insertCol().insertUI(label);
			Input input = new Input();
			String htmlparamerId = columnId + "_" + paramer[0];
			input.setName(htmlparamerId);
			input.setStyle("width:100");
			row.insertCol().insertUI(input);
			butParamLink.append(htmlparamerId + "@99@");
		}
		expressionView.addUI(tablex);

		expressionView.addUI(new Br());
		Button but = new Button();
		but.setValue("保存");
		but.setOnClick("save('" + columnId + "','" + butParamLink + "','"
				+ cellid + "')");
		expressionView.addUI(but);

		Button but1 = new Button();
		but1.setValue("测试");
		but1.setOnClick("test('" + columnId + "','" + butParamLink + "','"
				+ cellid + "')");
		expressionView.addUI(but1);

		addRow(" ", table, expressionView, columnId);

		Div div = new Div();
		div.setId("DISP");
		addRow(" ", table, div, columnId);

	}

	private static String scriptExpress(String jppid) {
		if (jppid == null || jppid.equals("")) {
			return "";
		}

		CmsService cmsServicex;
		try {
			cmsServicex = (CmsService) RmiEntry.iv("cmshandle");
			String script = cmsServicex.fetchJppTemplate(jppid);
			// script = WebStr.encode(request, script);
			if (script != null) {
				String key = StringUtils.substringBetween(script, "$:", "{");
				if (key != null && !key.equals("")) {
					script = StringUtils.replace(script, "$:" + key + "{", "$:"
							+ jppid + "{");
				}
			}
			return script;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new RuntimeException();
	}

	public static void addHidden(String columnId, String valueis,
			DynaUIForm dyform) {
		Hidden hiddenx = new Hidden();
		hiddenx.setName(columnId);
		hiddenx.setValue(valueis);
		dyform.addControl(hiddenx);
	}

	public static void addDatetimeRow(Table table, String title,
			String htmltypes, String columnId, String valuelist, boolean musk,
			boolean readonly, boolean ifQuery) {

		DateTime datetime = new DateTime();
		datetime.setType(htmltypes);
		datetime.setName(columnId);

		if (!ifQuery) {
			if (valuelist != null) {
				datetime.setValue(valuelist);
			} else {
				datetime.setValue(new Timestamp(System.currentTimeMillis())
						.toString().substring(0, 19));
			}

			datetime.setRequire(musk);
			datetime.setReadOnly(readonly);
			if (readonly) {
				datetime.setStyle("background-color:#CCCCCC");
			}

		}

		addRow(title, table, datetime, columnId);
	}

	public static void addResource(String rootpath, Table table,
			String columnId, String treeid, String title, boolean readonly) {

		Div div = new Div();
		// 创建附件管理按钮
		Button butBack = new Button();
		butBack.setName("butupfile");
		butBack.setValue("选择资源");

		Input input = new Input();
		input.setName(columnId);
		div.addUI(input);

		if (readonly) {
			butBack.setDisabled(true);
			input.setStyle("background-color:#CCCCCC");
			input.setReadOnly(true);
		}

		String value = StringUtils.substringBetween(treeid, "[", "]");

		String urlReal = "window.open('" + rootpath
				+ "/PagelistRightSvl?pagename=dylist&appname=" + value + "')";
		butBack.setOnClick("javascript:setCurResource('" + columnId + "');"
				+ urlReal);
		div.addUI(butBack);

		addRow(title, table, div, columnId);
	}

	/**
	 * 在树上单选择
	 * 
	 * @param rootpath
	 * @param table
	 * @param columnId
	 * @param title
	 * @param value
	 * @param valuelist
	 */
	public static void addResourceWithSingle(String rootpath, Table table,
			String columnId, String title, String value, String valuelist,
			boolean radonly) {
		
		String url = "/SSelectSvl?pagename=datalist&appname=";
		String name = "选择";
		addResourceCore(rootpath, table, columnId, title, value, valuelist,
				url, name, radonly);

	}

	/**
	 * 在树上多选择
	 * 
	 * @param rootpath
	 * @param table
	 * @param columnId
	 * @param title
	 * @param value
	 * @param valuelist
	 */
	public static void addResourceWithMulti(String rootpath, Table table,
			String columnId, String title, String value, String valuelist,
			boolean readonly) {
		String url = "/MSelectSvl?pagename=datalist&appname=";
		String name = "选择*";
		addResourceCore(rootpath, table, columnId, title, value, valuelist,
				url, name, readonly);

	}
	
	/**
	 * 在树上单选择
	 * 
	 * @param rootpath
	 * @param table
	 * @param columnId
	 * @param title
	 * @param value
	 * @param valuelist
	 */
	public static void addResourceWithSingleHuman(String rootpath, Table table,
			String columnId, String title, String value, String valuelist,
			boolean radonly) {
		
		String url = "/SSelectSvl?pagename=datalisthuman&appname=";
		String name = "选择人员";
		addResourceCore(rootpath, table, columnId, title, value, valuelist,
				url, name, radonly);

	}

	/**
	 * 在树上多选择
	 * 
	 * @param rootpath
	 * @param table
	 * @param columnId
	 * @param title
	 * @param value
	 * @param valuelist
	 */
	public static void addResourceWithMultiHuman(String rootpath, Table table,
			String columnId, String title, String value, String valuelist,
			boolean readonly) {
		String url = "/MSelectSvl?pagename=datalisthuman&appname=";
		String name = "选择人员*";
		addResourceCore(rootpath, table, columnId, title, value, valuelist,
				url, name, readonly);

	}

	public static void addResourceCore(String rootpath, Table table,
			String columnId, String title, String value, String valuelist,
			String url, String name, boolean readonly) {

		try {
			if (value == null || value.equals("")) {// 维度为其写入默认值，默认值是数据源中的根元素
				// xxx.xxx.root
				// 如果数据源没有根元素，那么取数据源程序元素xxx.xxx
				ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
				String appname = StringUtils.substringBetween(valuelist, "[",
						"]");
				String dnroot = "";
				if (StringUtils.contains(appname, ".")) {
					dnroot = appname;
				} else {
					dnroot = appname + "." + appname;
				}

				UmsProtectedobject upo = rmi.loadResourceByNatural(dnroot);
				String id = upo.getId();
				List list = rmi.subResource(id);
				String valuedim = valuelist;
				if (list.size() > 0) {
					UmsProtectedobject upoNow = (UmsProtectedobject) list
							.get(0);
					valuedim = upoNow.getName() + "[" + upoNow.getNaturalname()
							+ "]";
				} else {
					valuedim = valuelist.replace(appname, dnroot);
				}
				value = valuedim;
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Div div = new Div();
		// 创建附件管理按钮
		Button butBack = new Button();
		butBack.setName("butupfile");
		butBack.setValue(name);

		Input input = new Input();
		input.setName(columnId);
		input.setValue(value);
		div.addUI(input);

		if (readonly) {
			butBack.setDisabled(true);
			input.setStyle("background-color:#CCCCCC");
			input.setReadOnly(true);
		}

		String appname = "";
		if (valuelist != null && valuelist.length() > 0) {
			appname = StringUtils.substringBetween(valuelist, "[", "]");
		}
		String urlReal = "window.open('"
				+ rootpath
				+ url
				+ appname
				+ "','_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');";
		butBack.setOnClick("javascript:setCurResource('" + columnId + "');"
				+ urlReal);
		div.addUI(butBack);

		addRow(title, table, div, columnId);
	}

	public static void addResourceView(String rootpath, Table table,
			String columnId, String treeid, String title) {

		String value = StringUtils.substringBetween(treeid, "[", "]");
		String name = StringUtils.substringBefore(treeid, "[");
		Link link = new Link();
		link.setHref(rootpath + "/DownloadSvl?fileid=" + value);
		link.setTitle("[" + name + "]");

		addRow(title, table, link, columnId);
	}

	public static void addSelectRow(Table table, String title,
			String htmltypes, String columnId, String valuelist,
			String valueis, boolean musk, boolean readonly, boolean ifQuery,
			String spiltmark, boolean hidden) {

		if (spiltmark == null || spiltmark.equals("")) {
			spiltmark = "-";
		}

		Select select = new Select();
		select.setName(columnId+ "\" title=");
		if (hidden) {
			select.setId(columnId + "\" style='display:none' title=");

		} else {
			select.setId(columnId );
		}

		String[] selectValuelist = null;
		if (valuelist != null && !valuelist.equals("")) {
			if (ColumnExtendInfo._HTML_TYPE_SELECT.equals(htmltypes)) {
				selectValuelist = StringUtils.split(valuelist, ",");
				for (int i = 0; i < selectValuelist.length; i++) {
					selectValuelist[i] = selectValuelist[i] + spiltmark
							+ selectValuelist[i];
				}
			} else {
				selectValuelist = StringUtils.split(valuelist, ",");
			}
		}
		if (ifQuery) {
			select.options().addOption("", "--请选择--");
		} else {
			select.setReadOnly(readonly);
			select.setRequire(musk);
			if (readonly) {
				if (valueis == null) {
					if (ColumnExtendInfo._HTML_TYPE_SELECT_KV.equals(htmltypes)) {
						valueis = StringUtils.substringBefore(valuelist, "-");
					} else {
						valueis = StringUtils.substringBefore(valuelist, ",");
					}

				}
				select.setOnChange("this.value='" + valueis + "'");
				select.setStyle("background-color:#CCCCCC;");
			}
		}
		if (selectValuelist != null) {
			for (int i = 0; i < selectValuelist.length; i++) {
				if (selectValuelist[i] != null && !selectValuelist.equals("")) {
					String[] valueTmp = StringUtils.split(selectValuelist[i],
							spiltmark);
					if (valueTmp.length == 1) {
						select.options().addOption(valueTmp[0], "");
					} else {
						select.options().addOption(valueTmp[0], valueTmp[1]);
					}
				}
			}
		}
		if (valueis != null && !valueis.equals("")) {
			select.setValue(valueis);
		}

		addRow(title, table, select, columnId);
	}

	public static void addRow(String title, Table table, HtmlUI ui,
			String columnid) {

		TableRow row = table.insertRow();// 创建新的一行
		row.setId(columnid + "tr");
		TableColumn colTitle = row.insertCol();// 创建新的一列

		Label label = new Label();
		label.setValue(title);
		colTitle.setUI(label);

		TableColumn colValue = row.insertCol();// 创建新的一列
		colValue.setUI(ui);
	}

	public static void addRow2Space(String title, Table table, HtmlUI ui,
			String columnid) {

		TableRow row = table.insertRow();// 创建新的一行
		row.setId(columnid + "tr");
		TableColumn colTitle = row.insertCol();// 创建新的一列
		colTitle.setColspan("2");

		colTitle.setUI(ui);
		colTitle.setTitle(title);
	}

	public static void addRow(String title, Table table, HtmlUI[] ui) {

		TableRow row = table.insertRow();// 创建新的一行

		TableColumn colTitle = row.insertCol();// 创建新的一列

		Label label = new Label();
		label.setValue(title);
		colTitle.setUI(label);

		// ---modify by Robanco 处理一个表格只能设置一个Html元素的BUG,用DIV来处理---
		TableColumn colValue = row.insertCol();// 创建新的一列
		Div div = new Div();
		for (int i = 0; i < ui.length; i++) {
			div.addUI(ui[i]);
		}
		colValue.setUI(div);
		// ----------------------------------

		// for (int i = 0; i < ui.length; i++) {
		//
		// TableColumn colValue = row.insertCol();// 创建新的一列
		// colValue.setUI(ui[i]);
		// }

	}

	public static void addViewInfoRow(Table table, HtmlUI ui) {

		TableRow row1 = table.insertRow();// 创建新的一行
		TableColumn colValue = row1.insertCol();// 创建新的一列

		colValue.setUI(ui);

	}

}
