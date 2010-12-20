package oe.cav.web.data.dyform;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.web.data.dyform.utils.DefaultElementAdder;
import oe.cav.web.data.dyform.utils.DynamicFormElementAdder;

import org.apache.commons.beanutils.BeanUtils;

import com.rongji.webframework.struts.DynaUIForm;
import com.rongji.webframework.ui.html.Table;

public class DynamicFormSeeDetail {
	public static void generateDispView(String rootpath, TCsBus bus,
			List columns, DynaUIForm dyform, Map columnAccess) {
		String formcode = bus.getFormcode();
		String lsh = bus.getLsh();
		DefaultElementAdder.addHideColumn(dyform, "formcode", formcode);
		DefaultElementAdder.addHideColumn(dyform, "created", bus.getCreated());
		DefaultElementAdder.addHideColumn(dyform, "participant", bus
				.getParticipant());
		DefaultElementAdder.addHideColumn(dyform, "extendattribute", bus
				.getExtendattribute());
		DefaultElementAdder.addHideColumn(dyform, "lsh", lsh);
		DefaultElementAdder.addHideColumn(dyform, "statusinfo", bus
				.getStatusinfo());
		Table table = new Table();
		boolean checkfirst = true;
		for (Iterator itr = columns.iterator(); itr.hasNext();) {

			TCsColumn busEach = (TCsColumn) itr.next();
			String htmltypes = busEach.getHtmltype();
			String columnName = busEach.getColumname();
			String columnId = busEach.getColumnid();

			String accessmode = ColumnExtendInfo._ACCESS_TYPE_W;

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
						valueis, checkfirst);
				checkfirst = false;
			} else if (ColumnExtendInfo._HTML_TYPE_SELECT.equals(htmltypes)
					|| ColumnExtendInfo._HTML_TYPE_SELECT_KV.equals(htmltypes)) {
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkfirst);
				checkfirst = false;
			} else if (ColumnExtendInfo._HTML_TYPE_TEXTAREA.equals(htmltypes)) {
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkfirst);
				checkfirst = false;
			} else if (ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltypes)) {

		
			} else if (ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltypes)) {


			} else {
				DynamicFormElementAdder.addViewInfoRow(table, columnName,
						valueis, checkfirst);
				checkfirst = false;
			}
		}

	}
}
