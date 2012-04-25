package oe.cav.bean.logic.column;

import java.util.Map;

import oe.frame.web.util.Cacheobj;

import org.apache.commons.collections.SequencedHashMap;

public class ColumnExtendInfo {
	// /////�ֶλ�������/////////////
	public static String[][] _STATUS = { { "00", "����" }, { "01", "ʧЧ" } };

	public static String _STATUS_NORMAL = "00";

	public static String _STATUS_LOSE = "01";

	// /////�ֶβ���Ȩ��/////////////
	public static String[][] _ACCESS_TYPE = { { "0", "ֻ��" }, { "1", "д" },
			{ "2", "����" } };

	public static String _ACCESS_TYPE_R = "0";

	public static String _ACCESS_TYPE_W = "1";

	public static String _ACCESS_TYPE_H = "2";

	// ///�߼��Ƚ��������////////
	public static String[][] LOGIC_LIST = { { ">", "����>" }, { "<", "С��<" },
			{ ">=", "���ڵ���>=" }, { "<=", "С�ڵ���<=" }, { "=", "����=" },
			{ "!=", "������!=" } };

	// ////�߼�������//////////
	public static String[][] LOGIC_AND_OR = { { "or", "����or" },
			{ "and", "����and" } };

	public static String LOGIC_AND_SYMBOL = "and";

	public static String LOGIC_OR_SYMBOL = "or";

	// //////�ֶε�Ĭ������//////////
	public static String _COLUMN = "column";

	// //////����ֵ�ο�/////////
	public static String[][] _BOOLEAN = { { "1", "��" }, { "0", "��" } };

	public static String _BOOLEAN_TRUE = "1";

	public static String _BOOLEAN_FALSE = "0";

	// ////// �ֶ�������Ϣ�ο� ///////////////
	public static String[][] _TYPE_LIST = { { "00", "ͨ��" }, { "01", "����/���" },
			{ "02", "ʱ��hh:mm:ss" }, { "03", "����YYYY-MM-DD" },
			{ "04", "����ʱ��YYYY-MM-DD hh:mm:ss" }, { "05", "���" },
			{ "06", "�ʼ���ַ" }, { "10", "�б���Ϣ" }, { "11", "�б���ϢK-V" },
			{ "12", "IP��ַ" }, { "13", "���ı�" }, {"14", "�ļ�" }, {"15", "ͼƬ" }, {"16", "��ť" }, { "17", "����Դѡ��" },
			{ "18", "����Դѡ��" }, { "20", "PORTAL��" }, { "21", "����ĵ�" },
			{ "22", "��֯��Ա" }, { "23", "��֯��Ա��ѡ" }, { "24", "��ǰ�û�" },
			{ "25", "��������" }, { "26", "��ѡ��" }, { "27", "��֯������ѡ" },
			{ "28", "��֯������ѡ" } , {"29", "URL" } ,  {"30", "��ѡ��" } ,{"31","���radio"}};
			//url�������ں� php���ݹ���ϵͳ�������������õ�

	public static String _TYPE_NORMAL = "00";

	public static String _TYPE_NUMBER = "01";

	public static String _TYPE_DATE = "03";

	public static String _TYPE_TIME = "02";

	public static String _TYPE_DATETIME = "04";

	public static String _TYPE_BOOLEAN = "05";

	public static String _TYPE_MAIL = "06";

	public static String _TYPE_LISTINFO = "10";

	public static String _TYPE_LISTINFO_KV = "11";

	public static String _TYPE_IP = "12";

	public static String _TYPE_TEXTAREA = "13";

	public static String _TYPE_FILE = "14";

	public static String _TYPE_IMAGE = "15";

	public static String _TYPE_BUT = "16";

	public static String _TYPE_TREE = "17";

	public static String _TYPE_TREE2 = "18";

	public static String _TYPE_SCRIPT = "19";

	public static String _TYPE_PORRAL_ITEM = "20";

	public static String _TYPE_FCK_ITEM = "21";

	public static String _TYPE_HUMAN_ITEM = "22";

	public static String _TYPE_HUMAN_ITEM2 = "23";

	// ///////�ֶ� У������ �ο�//////////
	public static String _CHECK_TYPE_NO = "";

	public static String _CHECK_TYPE_NUMBER = "number";

	public static String _CHECK_TYPE_MAIL = "mail";

	public static String _CHECK_TYPE_IP = "ip";

	// //////�ֶε���ʵHtml���Ͳο�//////////
	public static String _HTML_TYPE_TEXT = "text";

	public static String _HTML_TYPE_TIME = "time";

	public static String _HTML_TYPE_DATE = "date";

	public static String _HTML_TYPE_DATETIME = "datetime";

	public static String _HTML_TYPE_CHECKBOX = "checkbox";

	public static String _HTML_TYPE_SELECT = "select";

	public static String _HTML_TYPE_SELECT_KV = "selectkv";

	public static String _HTML_TYPE_TEXTAREA = "textarea";

	public static String _HTML_TYPE_FILE = "file";

	public static String _HTML_TYPE_IMAGE = "image";

	public static String _HTML_TYPE_BUT = "but";

	public static String _HTML_TYPE_TREE = "tree";

	public static String _HTML_TYPE_TREE2 = "tree2";

	public static String _HTML_TYPE_HUMAN = "human";

	public static String _HTML_TYPE_HUMAN2 = "human2";

	public static String _HTML_TYPE_PORTAL_ITEM = "pitem";

	public static String _HTML_TYPE_FCK_ITEM = "fck";

	public static String _HTML_TYPE_PORTAL_APP = "papp";

	public static String _HTML_TYPE_SCRIPT = "script";

	public static Map _TYPE_INFO_LIST = new SequencedHashMap();

	public static Map _BOOLEAN_INFO_LIST = new SequencedHashMap();
	static {

		for (int i = 0; i < _TYPE_LIST.length; i++) {
			_TYPE_INFO_LIST.put(_TYPE_LIST[i][0], new Cacheobj(
					_TYPE_LIST[i][1], _TYPE_LIST[i][0]));
		}

		for (int i = _BOOLEAN.length - 1; i >= 0; i--) {
			_BOOLEAN_INFO_LIST.put(_BOOLEAN[i][0], new Cacheobj(_BOOLEAN[i][1],
					_BOOLEAN[i][0]));
		}

	}

}
