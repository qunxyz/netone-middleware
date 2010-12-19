package oe.cms.xhtml2.view.core;

/**
 * 
 * ��Ѷ�б�ѡ��<br>
 * ʹ�÷����� mc.xhtmlinfo(Object arg0, Object arg1);<br>
 * ���� arg0 ��Ѷ��Ϣ���� <br>
 * ���� arg1 ����Ѷ��Ϣ��Ӧ������˵������<br>
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
	 * ѡ����ʾ����
	 * 
	 * @param arg0
	 *            ��Ҫѡ����ʾ��Html��Ϣ���� String[]
	 * @param arg1
	 *            ��ѡ���������Ϣ String[]
	 */
	public static String xhtmlinfo(String[] info, String[] name) {
		return fetchSelectWithInfo(info, name);
	}

}
