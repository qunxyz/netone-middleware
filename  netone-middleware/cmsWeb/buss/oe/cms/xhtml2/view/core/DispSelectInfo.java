package oe.cms.xhtml2.view.core;

/**
 * 
 * 咨讯列表选择<br>
 * 使用方法： mc.xhtmlinfo(Object arg0, Object arg1);<br>
 * 参数 arg0 咨讯信息数组 <br>
 * 参数 arg1 与咨讯信息对应的中文说明数组<br>
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public final class DispSelectInfo {

	public static String getSelectFunc(String divAheadName, String selectname,
			int dispnum) {

		String info = "var obj=document.getElementById('" + divAheadName
				+ "').value;" + "for (var i=0;i<" + dispnum + ";i++){"
				+ "var id='" + divAheadName + "'+i;"
				+ "var s=document.getElementById(id);" + "if(id==obj){"
				+ "s.style.display  =''  ;" + "}else{"
				+ "s.style.display  =  'none';" + "}}";
		return info;
	}

	public static String fetchSelectWithInfo(String[] dispinfo,
			String[] dispselectName) {
		if (dispinfo == null || dispselectName == null) {
			return "";
		}
		String divAheadName = "disp" + System.currentTimeMillis();
		String selectName = divAheadName + "()";

		String jsinfo = getSelectFunc(divAheadName, selectName, dispinfo.length);

		String ahead = "<select name=\"" + divAheadName + "\" onChange=\""
				+ jsinfo + "\">";

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < dispselectName.length; i++) {
			String namevalue = dispselectName[i];
			String divid = divAheadName + i;
			buf.append("<option value=\"" + divid + "\">" + namevalue
					+ "</option>");
		}
		String selectinfo = ahead + buf + "</select><br>";

		StringBuffer divinfo = new StringBuffer();
		for (int i = 0; i < dispinfo.length; i++) {
			String divid = divAheadName + i;
			String divinfoPre = null;
			if (i != 0) {
				divinfoPre = "<div id=\""
						+ divid
						+ "\" style=\"top:60px; position:static; display='none';\">"
						+ dispinfo[i] + "</div>";
			} else {
				divinfoPre = "<div id=\"" + divid
						+ "\" style=\"top:60px; position:static; \">"
						+ dispinfo[i] + "</div>";
			}
			divinfo.append(divinfoPre);
		}

		return selectinfo + divinfo;
	}

	/**
	 * 选择显示内容
	 * 
	 * @param arg0
	 *            需要选择显示的Html信息数组 String[]
	 * @param arg1
	 *            可选择的内容信息 String[]
	 */
	public static String xhtmlinfo(String[] info, String[] name) {
		return fetchSelectWithInfo(info, name);
	}

}
