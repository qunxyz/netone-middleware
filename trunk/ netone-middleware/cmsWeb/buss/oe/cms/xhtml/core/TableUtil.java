package oe.cms.xhtml.core;


public class TableUtil {

	public static String fetchTable(String[][] data, String[] column) {
		if (data == null) {
			return "";
		}
		StringBuffer but = new StringBuffer("<table border=\"1\">");
		// 增加标题头
		if (column != null && column.length > 0) {
			but.append("<tr>");
			for (int k = 0; k < column.length; k++) {
				but.append("<td bgcolor=\"#EEF6FE\"><strong>" + column[k]
						+ "</strong></td>");
			}
			but.append("</tr>");
		}
		// 增加内容
		int valueLen = data[0].length;
		int preRecLen = data.length;
		for (int i = 0; i < valueLen; i++) {
			but.append("<tr>");
			for (int j = 0; j < preRecLen; j++) {
				but.append("<td>" + data[j][i] + "</td>");
			}
			but.append("</tr>");
		}
		return but.toString() + "</table>";
	}

	public static String fetchTableRow(String[][] data) {
		if (data == null) {
			return "";
		}
		StringBuffer but = new StringBuffer();

		// 增加内容
		int valueLen = data[0].length;
		int preRecLen = data.length;
		for (int i = 0; i < valueLen; i++) {
			but.append("<tr>");
			for (int j = 0; j < preRecLen; j++) {
				but.append("<td>" + data[j][i] + "</td>");
			}
			but.append("</tr>");
		}
		return but.toString();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		String[][] info = { { "1", "2", "3", "4" }, { "11", "22", "13", "24" },
//				{ "1", "32", "34", "45" }, { "15", "25", "3", "4" } };
//		String[] title = { "H1", "H2", "H3", "H4" };
//		String infox = TableUtil.fetchTable(info, title);
//		System.out.println(infox);
		

	}

}
