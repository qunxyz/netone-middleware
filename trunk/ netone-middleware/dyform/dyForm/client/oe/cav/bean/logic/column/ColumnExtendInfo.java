package oe.cav.bean.logic.column;

import java.util.Map;

import oe.frame.web.util.Cacheobj;

import org.apache.commons.collections.SequencedHashMap;

public class ColumnExtendInfo {
	// /////字段基础类型/////////////
	public static String[][] _STATUS = { { "00", "正常" }, { "01", "失效" } };

	public static String _STATUS_NORMAL = "00";

	public static String _STATUS_LOSE = "01";

	// /////字段操作权限/////////////
	public static String[][] _ACCESS_TYPE = { { "0", "只读" }, { "1", "写" },
			{ "2", "隐蔽" } };

	public static String _ACCESS_TYPE_R = "0";

	public static String _ACCESS_TYPE_W = "1";

	public static String _ACCESS_TYPE_H = "2";

	// ///逻辑比较运算符号////////
	public static String[][] LOGIC_LIST = { { ">", "大于>" }, { "<", "小于<" },
			{ ">=", "大于等于>=" }, { "<=", "小于等于<=" }, { "=", "等于=" },
			{ "!=", "不等于!=" } };

	// ////逻辑与或符号//////////
	public static String[][] LOGIC_AND_OR = { { "or", "或者or" },
			{ "and", "并且and" } };

	public static String LOGIC_AND_SYMBOL = "and";

	public static String LOGIC_OR_SYMBOL = "or";

	// //////字段的默认属性//////////
	public static String _COLUMN = "column";

	// //////布尔值参考/////////
	public static String[][] _BOOLEAN = { { "1", "是" }, { "0", "否" } };

	public static String _BOOLEAN_TRUE = "1";

	public static String _BOOLEAN_FALSE = "0";

	// ////// 字段类型信息参考 ///////////////
	public static String[][] _TYPE_LIST = { { "00", "通用" }, { "01", "数字/金额" },
			{ "02", "时间hh:mm:ss" }, { "03", "日期YYYY-MM-DD" },
			{ "04", "日期时间YYYY-MM-DD hh:mm:ss" }, { "05", "真假" },
			{ "06", "邮件地址" }, { "10", "列表信息" }, { "11", "列表信息K-V" },
			{ "12", "IP地址" }, { "13", "大文本" }, {"14", "文件" }, {"15", "图片" }, {"16", "按钮" }, { "17", "单资源选择" },
			{ "18", "多资源选择" }, { "20", "PORTAL项" }, { "21", "多彩文档" },
			{ "22", "组织人员" }, { "23", "组织人员多选" }, { "24", "当前用户" },
			{ "25", "所属部门" }, { "26", "多选组" }, { "27", "组织机构单选" },
			{ "28", "组织机构多选" } , {"29", "URL" } ,  {"30", "单选组" } ,{"31","真假radio"}};
			//url类型用于和 php内容管理系统的文章做整合用的

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

	// ///////字段 校验类型 参考//////////
	public static String _CHECK_TYPE_NO = "";

	public static String _CHECK_TYPE_NUMBER = "number";

	public static String _CHECK_TYPE_MAIL = "mail";

	public static String _CHECK_TYPE_IP = "ip";

	// //////字段的真实Html类型参考//////////
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
