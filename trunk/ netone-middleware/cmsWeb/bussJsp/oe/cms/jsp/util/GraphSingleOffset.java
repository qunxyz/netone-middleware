package oe.cms.jsp.util;

import oe.cms.CmsEntry;
import oe.cms.xhtml2.ViewInterface;

public class GraphSingleOffset {

	public static String todo(String dimvalue, String dimname,

	String tarvalue,

	String tarname,

	String title,

	String width,

	String height, String graphType) {

		String[] dimvaluelist = dimvalue.split(",");
		String[] tarvaluelisttmp = tarvalue.split(";");
		String[] tarnamelist = tarname.split(",");

		if (dimvaluelist.length < 1 || tarvaluelisttmp.length < 1
				|| tarvaluelisttmp[0].length() < 1
				|| tarnamelist.length != tarvaluelisttmp[0].length()) {

			return "无效参数";
		}
		int valueRowLen = tarvaluelisttmp.length;
		int valueColLen = tarvaluelisttmp[0].split(",").length;
		String[][] tarvalueListAll = new String[valueRowLen][valueColLen];
		for (int i = 0; i < valueRowLen; i++) {
			String[] colValue = tarvaluelisttmp[i].split(",");
			for (int j = 0; j < valueColLen; j++) {
				tarvalueListAll[i][j] = colValue[j];
			}
		}

		ViewInterface htmlh = (ViewInterface) CmsEntry.fetchBean("vi");
		return htmlh.fetchGraph(dimvaluelist, dimname, tarvalueListAll,
				tarnamelist, graphType, title, width, height);

	}
}
