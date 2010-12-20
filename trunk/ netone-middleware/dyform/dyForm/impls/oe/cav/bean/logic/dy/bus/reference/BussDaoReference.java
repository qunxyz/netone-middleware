package oe.cav.bean.logic.dy.bus.reference;

import oe.frame.orm.QueryInfo;

public class BussDaoReference {
	public static String STRING = "string";

	public static String DATE = "date";

	public static String NUMBER = "number";

	public static String _EXT = "_ext_";

	public static String _BLANK = " ";

	public static String _FlAG = "?";
	
	public static String _SELECT = " select *  ";
	
	public static String _SELECTCOUNT = " select count(*) total  ";
	
	public static String _INSERT = " insert into ";
	
	public static String _UPDATE = " update ";
	
	public static String _SET = " set ";
	
	public static String _DELETE= " delete ";

	public static String _AND = " and ";

	public static String _FROM = " from ";

	public static String _WHERE = " where ";

	public static String _LIKELEFT = "%";

	public static String _LIKERIGHT = "%";

	public static String _PRE_CONDITION = " 1=1 ";

	public static String buildConditionColumn(String columnid, QueryInfo qi) {
		// if (qi == null) {
		// return _BLANK + _AND + columnid + QueryInfo._EQUAL + _FlAG
		// + _BLANK;
		// } else if (qi.getLikecolumn()!=null &&
		// qi.getLikecolumn().equals(columnid)) {
		// return _BLANK + _AND + columnid + QueryInfo._LIKE + _FlAG + _BLANK;
		// } else if (qi.getLargecolumn()!=null &&
		// qi.getLargecolumn().equals(columnid)) {
		// return _BLANK + _AND + columnid + QueryInfo._LARGE + _FlAG
		// + _BLANK;
		// } else if (qi.getLargeequalcolumn()!=null &&
		// qi.getLargeequalcolumn().equals(columnid)) {
		// return _BLANK + _AND + columnid + QueryInfo._LARGE_EQUAL + _FlAG
		// + _BLANK;
		// } else if (qi.getLesscolumn()!=null &&
		// qi.getLesscolumn().equals(columnid)) {
		// return _BLANK + _AND + columnid + QueryInfo._LESS + _FlAG + _BLANK;
		// } else if (qi.getLessequalcolumn()!=null &&
		// qi.getLessequalcolumn().equals(columnid)) {
		// return _BLANK + _AND + columnid + QueryInfo._LESS + _FlAG + _BLANK;
		// } else if (qi.getUnequaslcolumn()!=null &&
		// qi.getUnequaslcolumn().equals(columnid)) {
		// return _BLANK + _AND + columnid + QueryInfo._UNEQUAL + _FlAG
		//					+ _BLANK;
		//		}
		return _BLANK + _AND + columnid + QueryInfo._EQUAL + _FlAG + _BLANK;
	}
}
