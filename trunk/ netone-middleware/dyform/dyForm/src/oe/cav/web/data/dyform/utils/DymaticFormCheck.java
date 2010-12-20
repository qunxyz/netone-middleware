package oe.cav.web.data.dyform.utils;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;

import com.rongji.webframework.ui.html.Table;

public class DymaticFormCheck {

	static Log log = LogFactory.getLog(DymaticFormCheck.class);

	final static String CHECKRULE = "checkrule:";

	final static String CHECKUNPASSTIP = "checktip:";

	final static String SPLITMARK = "#kqx#";

	/**
	 * ��ӱ�У����Ϣ
	 * 
	 * @param table
	 * @param idlist
	 * @param namelist
	 * @param maskList
	 */
	public static void addCheckInfo(List columns, Table table) {

		StringBuffer idlist = new StringBuffer();// У��ο���Ϣ,�ֶ�ID-�������
		StringBuffer namelist = new StringBuffer();// У��ο���Ϣ,�ֶ�ID-�ֶ���
		StringBuffer maskList = new StringBuffer();// У��ο���Ϣ,�ֶ�ID-�Ƿ����
		StringBuffer selfList = new StringBuffer();// ������չ���Ե�����У��,�ֶ�ID-�Ƿ����
		StringBuffer selfListtip = new StringBuffer();// ������չ���Ե�����У����ʾ��,�ֶ�ID-�Ƿ����

		for (Iterator itr = columns.iterator(); itr.hasNext();) {
			TCsColumn busEach = (TCsColumn) itr.next();
			String htmltypes = busEach.getHtmltype();
			String columnName = busEach.getColumname();
			String columnId = busEach.getColumnid();
			String musk = ColumnExtendInfo._BOOLEAN_TRUE.equals(busEach
					.getMusk()) ? "1" : "0";
			String checktypes = busEach.getChecktype();
			String ext = busEach.getExtendattribute();
			String rule = "";
			String ruletip = "";
			if (ext != null && !ext.equals("")) {
				rule = StringUtils.substringBetween(ext, CHECKRULE, ";");
				ruletip = StringUtils
						.substringBetween(ext, CHECKUNPASSTIP, ";");
			}

			if (!ColumnExtendInfo._HTML_TYPE_CHECKBOX.equals(htmltypes)
					&& !ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltypes)
					&& !ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltypes)) {
				if (checktypes == null || checktypes.equals("")) {
					idlist.append("," + columnId + SPLITMARK + "no");
				} else {
					idlist.append("," + columnId + SPLITMARK + checktypes);
				}
				log.debug("dyform_check_column:" + columnId);
				namelist.append("," + columnId + SPLITMARK + columnName);
				maskList.append("," + columnId + SPLITMARK + musk);
				selfList.append("," + columnId + SPLITMARK + rule);
				selfListtip.append("," + columnId + SPLITMARK + ruletip);
			}
		}

		addCheckInfoCore(table, idlist.toString(), namelist.toString(),
				maskList.toString(), selfList.toString(), selfListtip
						.toString());

	}

	private static void addCheckInfoCore(Table table, String idlist,
			String namelist, String maskList, String selList, String selListtip) {
		if (idlist == null || idlist.equals("")) {
			return;
		}

		// ����б��ֶ�Id-����
		String valuexList = selList;
		valuexList = valuexList == null ? "," : valuexList;
		valuexList = valuexList.substring(1, valuexList.length());
		DynamicFormElementAdder.addSelectRow(table, "",
				ColumnExtendInfo._HTML_TYPE_SELECT_KV, "checkinXC", valuexList,
				"", false, false, false, SPLITMARK,true);

		String valuexList1 = selListtip;
		valuexList1 = valuexList1 == null ? "," : valuexList1;
		valuexList1 = valuexList1.substring(1, valuexList1.length());
		DynamicFormElementAdder.addSelectRow(table, "",
				ColumnExtendInfo._HTML_TYPE_SELECT_KV, "checkinXCNAME",
				valuexList1, "", false, false, false, SPLITMARK,true);

		// ����б��ֶ�ID-�������
		String valueIdlist = idlist;
		valueIdlist = valueIdlist == null ? "," : valueIdlist;
		valueIdlist = valueIdlist.substring(1, valueIdlist.length());
		DynamicFormElementAdder.addSelectRow(table, "",
				ColumnExtendInfo._HTML_TYPE_SELECT_KV, "checkinSl",
				valueIdlist, "", false, false, false, SPLITMARK,true);
		// ����б��ֶ�ID-�ֶ���
		String valueNamelist = namelist;
		valueNamelist = valueNamelist == null ? "," : valueNamelist;
		valueNamelist = valueNamelist.substring(1, valueNamelist.length());
		DynamicFormElementAdder.addSelectRow(table, "",
				ColumnExtendInfo._HTML_TYPE_SELECT_KV, "checkinNl",
				valueNamelist, "", false, false, false, SPLITMARK,true);
		// ����б��ֶ�Id-����
		String valuemaskList = maskList;
		valuemaskList = valuemaskList == null ? "," : valuemaskList;
		valuemaskList = valuemaskList.substring(1, valuemaskList.length());
		log.debug("marklist:" + valuemaskList);
		DynamicFormElementAdder.addSelectRow(table, "",
				ColumnExtendInfo._HTML_TYPE_SELECT_KV, "checkinMl",
				valuemaskList, "", false, false, false, SPLITMARK,true);
	}
}
