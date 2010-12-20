package oe.cav.web.data.dyform;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;

import oe.cav.bean.logic.form.TCsForm;
import oe.cav.web.data.dyform.utils.DefaultElementAdder;
import oe.cav.web.data.dyform.utils.DynamicFormElementAdder;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Button;
import com.rongji.webframework.ui.html.Div;
import com.rongji.webframework.ui.html.Image;
import com.rongji.webframework.ui.html.Label;
import com.rongji.webframework.ui.html.Select;
import com.rongji.webframework.ui.html.Table;
import com.rongji.webframework.ui.html.TableColumn;

public class DynamicFormDisplay {

	static DyFormService dysc = null;

	static {

		if (dysc == null) {
			try {
				dysc = (DyFormService) RmiEntry.iv("dyhandle");
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
		}
	}

	/**
	 * 显示表单
	 * 
	 * 类似修改表单,只不过去掉了 提交按钮 和 校验JS
	 * 
	 * @param columns
	 * @param dyform
	 * @param ae
	 * @param bus
	 */
	public static void generateDispView(ActionEvent ae, TCsBus bus,
			List columns, DynaUIForm dyform, Map columnAccess, Table table,
			String[] but1, String[] but2, List subForm) {
		String rootpath = ae.getRequest().getContextPath();
		String lsh = bus.getLsh();
		StringBuffer cellid = new StringBuffer();
		// DefaultElementAdder.addHideColumn(dyform, "extendattribute", bus
		// .getExtendattribute());

		Label label = new Label();
		String create = bus.getCreated();

		if ("1".equals(bus.getFatherlsh())) {
			create = bus.getCreated().substring(0, 10);
		}

		label.setValue(bus.getParticipant() + " " + create);

		TableColumn colx = table.insertRow().insertCol();
		colx.insertUI(label);

		boolean checkFirst = true;
		int loopTime = 1;
		for (Iterator itr = columns.iterator(); itr.hasNext();) {

			TCsColumn busEach = (TCsColumn) itr.next();
			String htmltypes = busEach.getHtmltype();
			String columnName = busEach.getColumname();
			String columnId = busEach.getColumncode();

			String accessmode = ColumnExtendInfo._ACCESS_TYPE_W;

			if (!busEach.isUseable()) {
				DefaultElementAdder.addHideColumn(dyform,
						busEach.getColumnid(), "");
				continue;
			}

			String valuelist = busEach.getValuelist();

			String valueis = null;
			try {
				valueis = (String) BeanUtils.getProperty(bus, columnId);
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
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkFirst);
				checkFirst = false;
			} else if (ColumnExtendInfo._HTML_TYPE_SELECT.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_SELECT_KV.equals(htmltypes)) {
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkFirst);
				checkFirst = false;
			} else if (ColumnExtendInfo._HTML_TYPE_TEXTAREA.equals(htmltypes)) {
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkFirst);
				checkFirst = false;
			} else if (ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltypes)) {
				DynamicFormElementAdder.addResourceView(rootpath, table,
						columnId, valueis, columnName);
				// 这些全部不用了,表单中只需要显示一些基本信息,最终的展现应用由portal来做
			} else if (ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltypes)) {
				// 这些全部不用了,表单中只需要显示一些基本信息,最终的展现应用由portal来做
			} else if (ColumnExtendInfo._HTML_TYPE_BUT.equals(htmltypes)) {
				// 这些全部不用了,表单中只需要显示一些基本信息,最终的展现应用由portal来做
			} else if (ColumnExtendInfo._HTML_TYPE_TREE.equals(htmltypes)) {
				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkFirst);
				checkFirst = false;
			} else if (ColumnExtendInfo._HTML_TYPE_PORTAL_ITEM
					.equals(htmltypes)) {
				// 这块目前不是文件了,而是统一的资源包括:文件,图片,树,按扭等非字符形式的资源,它与ColumnExtendInfo._HTML_TYPE_FILE的区别在于它有目录
				// DynamicFormElementAdder.addPortalPage(bus, table, columnId,
				// columnName, valuelist);
				// checkFirst = false;

				if (bus.getLsh() != null) {// 只有记录创建后才能加 Portal页
					String cellidPre = DynamicFormElementAdder.addPortalPage(
							bus, table, columnId, columnName, valuelist);
					cellid.append("," + cellidPre);
				}

			} else if (ColumnExtendInfo._HTML_TYPE_FCK_ITEM.equals(htmltypes)) {
				if (bus.getLsh() != null) {// 只有记录创建后才能加 Portal页
					// String cellidPre = DynamicFormElementAdder.addFck(
					// bus, table, columnId, columnName, valuelist);
					// cellid.append("," + cellidPre);
					boolean onview=true;
					DynamicFormElementAdder.addFckRow(ae, bus, table,
							columnName, columnId, valuelist, onview);
				}

			} else {
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkFirst);
				checkFirst = false;
			}
		}

		TableColumn col = table.insertRow().insertCol();
		// // 创建附件管理按钮
		// Button butDetail = new Button();
		// butDetail.setName("but1");
		// if (but1 != null && but1.length == 3) {
		// butDetail.setValue(but1[2]);
		// butDetail.setVisible("1".equals(but1[1]));
		// } else {
		// butDetail.setValue("进入子表单");
		// }
		String subEnter = "subinfo" + loopTime++;

		Select select = new Select();
		select.setName(subEnter);
		if (subForm != null) {
			for (Iterator itr = subForm.iterator(); itr.hasNext();) {
				TCsForm form = (TCsForm) itr.next();
				if (form != null) {
					select.options().addOption(form.getFormcode(),
							form.getFormname());
				}
			}
		}

		Button butEnter = new Button();
		butEnter.setValue("进入");
		butEnter.setOnClick("listsubview('" + lsh + "','" + subEnter + "')");
		butEnter.setName(subEnter + "_but");

		// butDetail.setOnClick("listview('" + lsh + "','" + participant + "','"
		// + bus.getFormcode() + "')");

		Div div = new Div();
		div.addUI(butEnter);
		div.addUI(select);

		// // 创建附件管理按钮
		// Button backFather = new Button();
		// backFather.setName("but2");
		// if (but2 != null && but2.length == 3) {
		// backFather.setValue(but2[2]);
		// backFather.setVisible("1".equals(but2[1]));
		// } else {
		// backFather.setValue("返回上一级");
		// }
		//
		// String fatherlsh = bus.getFatherlsh();
		// if (!"1".equals(fatherlsh)) {
		// BussDao bussDao = (BussDao) FormEntry.fetchBean("bussDao");
		// TCsBus busFather = bussDao.loadObject(formcode, fatherlsh);
		//
		// backFather.setOnClick("fathergo('" + busFather.getFatherlsh()
		// + "','" + busFather.getFormcode() + "')");
		// } else {
		// backFather.setOnClick("alert('已到最顶级');return;");
		// }
		// div.addUI(backFather);
		col.insertUI(div);

		Image image = new Image();
		image.setHeight("3");
		image.setWidth("100%");
		image.setSrc("tp01.gif");
		col.insertUI(image);

		if (cellid != null && cellid.length() > 0) {
			ae.setAttribute("cellid", cellid.substring(1));
		} else {
			ae.setAttribute("cellid", "");
		}

	}

	/**
	 * 显示表单
	 * 
	 * 类似修改表单,只不过去掉了 提交按钮 和 校验JS
	 * 
	 * @param columns
	 * @param dyform
	 * @param ae
	 * @param bus
	 */
	public static String generateDispViewLink(String rootpath, TCsBus bus,
			List columns, DynaUIForm dyform, Map columnAccess) {
		String formcode = bus.getFormcode();
		String lsh = bus.getLsh();
		// DefaultElementAdder.addHideColumn(dyform, "extendattribute", bus
		// .getExtendattribute());

		Iterator itr = columns.iterator();

		TCsColumn busEach = (TCsColumn) itr.next();

		String columnId = busEach.getColumnid();

		String valueis = null;
		try {
			valueis = (String) BeanUtils.getProperty(bus, columnId);
			int length = valueis.length();
			if (valueis != null && valueis.length() > 60) {
				valueis = StringUtils.substring(valueis, 0, 60) + "......";
			}
			valueis = "<font size='2'>" + valueis + "</font>";
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
		//
		// String clickEvent = rootpath + "/data/showdata/sublistview.do?lsh="
		// + lsh + "&formcode=" + formcode;

		String clickEvent = rootpath + "/fm/" + bus.getParticipant() + "_"
				+ formcode + "_" + bus.getLsh() + ".htm";

		return DynamicFormElementAdder.addViewInfoLink(valueis, bus
				.getCreated(), bus.getParticipant(), clickEvent, bus
				.getExtendattribute());
	}

	private static String getNextFormSubform(String subFormcode) {

		TCsForm busFormFormSub = null;
		try {
			busFormFormSub = dysc.loadForm(subFormcode);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String nextSubformcode = busFormFormSub.getSubform();
		return nextSubformcode = nextSubformcode == null ? "" : nextSubformcode;
	}
}
