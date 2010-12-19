package oe.cms.xhtml2.portal.core;

import org.apache.commons.lang.StringUtils;

public final class PortaletDiv {

	static final String _DIV_CON = "<table width='99%' border='1' ><tr><td bgcolor='#7F7fdsfFAA1'>7F7fdsfFAAY</td>"
			+ "</tr><tr><td  bgcolor='#7F7fdsfFAA3'>7F7fdsfFAAX</td></tr></table>";

	static final String _TITLE_BGCOLOR = "#7F7fdsfFAA1";

	static final String _BODY_COLOR = "#7F7fdsfFAA3";

	static final String _BODY_VALUE = "7F7fdsfFAAX";

	static final String _TITLE_VALUE = "7F7fdsfFAAY";

	static final String _TABLEINFO = "<table width='99%' border='1' >";

	public static String fetchDiv(String tableinfo, String titleinfo,
			String titlebgcolor, String bodyinfo, String bodybgcolor) {

		String info = StringUtils
				.replaceOnce(_DIV_CON, _TITLE_VALUE, titleinfo);

		if (tableinfo != null && !tableinfo.equals("")) {
			info = StringUtils.replaceOnce(info, _TABLEINFO, tableinfo);
		}

		if (titlebgcolor == null || titlebgcolor.equals("")) {
			titlebgcolor = "#C6C6C6";
		}
		info = StringUtils.replaceOnce(info, _TITLE_BGCOLOR, titlebgcolor);

		if (bodybgcolor == null || bodybgcolor.equals("")) {
			bodybgcolor = "#FFFFFF";
		}
		info = StringUtils.replaceOnce(info, _BODY_COLOR, bodybgcolor);

		info = StringUtils.replaceOnce(info, _BODY_VALUE, bodyinfo);

		return info;

	}

}
