package oe.cms.xhtml2.view.core;



/**
 * 
 * 咨讯列表选择2级<br>
 * 使用方法： mc2.xhtmlinfo(Object arg0, Object arg1);<br>
 * 参数 arg0 咨讯信息数组 <br>
 * 参数 arg1 与咨讯信息对应的中文说明数组，数组中有两个元素
 * arr[0]中保存第一级选择信息列表（信息间用逗号隔开），arr[1]中保存二级选择信息列表<br>
 * 注意点: 二次列表对应的arg0数组的个数 = 一级个数 * 二级个数
 * 
 * 
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public final class DispSelectInfo2 {
	
	/**
	 * 选择显示内容
	 * 
	 * @param arg0
	 *            需要选择显示的Html信息数组 String[]
	 * @param arg1
	 *            可选择的内容信息 String[]
	 */
	public static String xhtmlinfo(String[] info, String[] name1, String[] name2) {

		int lenAll = info.length;
		int len1 = name1.length;
		int len2 = name2.length;
		if (lenAll != len1 * len2) {
			return "错误:信息与二次选择列表的次数不一致";
		}
		return fetchSelectWithInfo(info, name1, name2);
	}

	/**
	 * Js
	 * 
	 * @param divAheadName
	 * @param selectname
	 * @return
	 */
	private static String getSelectFunc(String divAheadName1,
			String divAheadName2, int dispnum, int stp1, int stp2) {

		String info = "var obj1=document.getElementById('"
				+ divAheadName1
				+ "').value;"
				+ "var obj2=document.getElementById('"
				+ divAheadName2
				+ "').value;var stp1=0;var stp2=-1; for(var i=0;i<"
				+ dispnum
				+ ";i++){"
				+ "stp2++; if(stp2=="
				+ stp2
				+ "){stp1++;stp2=0;} var id='"
				+ divAheadName1
				+ "'+stp1+'"
				+ divAheadName2
				+ "'+stp2;var s=document.getElementById(id);"
				+ "if(id==(obj1+obj2)){s.style.display  ='';}else{s.style.display  ='none';}}";
		return info;
	}

	public static String fetchSelectWithInfo(String[] dispinfo,
			String[] dispselectName1, String[] dispselectName2) {
		if (dispinfo == null || dispselectName1 == null
				|| dispselectName2 == null) {
			return "没有可选择的信息";
		}
		// 获得显示信息的ID
		String divAheadName = "disp" + System.currentTimeMillis();
		// 选择
		String divAheadName1 = divAheadName + "1";
		String divAheadName2 = divAheadName + "2";

		String selinfo = fetchSelectTarget(divAheadName1, divAheadName2,
				dispinfo.length, dispselectName2, dispselectName1.length,
				dispselectName2.length);

		String sele1 = fetchFirstSelect(divAheadName1, dispselectName1, selinfo);

		String sele2 = fetchSecondSelect(divAheadName2, dispselectName2,
				selinfo);
		// 全部显示信息
		String divinfo = dispView(dispinfo, divAheadName1, divAheadName2,
				dispselectName1.length, dispselectName2.length);

		return sele1 + sele2 + "<br>" + divinfo;
	}

	private static String fetchFirstSelect(String divAheadName,
			String[] dispselectName1, String jsinfo) {

		String ahead = "<select name=\"" + divAheadName + "\" onChange=\""
				+ jsinfo + "\">";

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < dispselectName1.length; i++) {
			String namevalue = dispselectName1[i];
			String divid = divAheadName + i;
			buf.append("<option value=\"" + divid + "\">" + namevalue
					+ "</option>");
		}

		String selectinfo = ahead + buf + "</select>&nbsp;";
		return selectinfo;
	}

	private static String fetchSelectTarget(String divAheadName1,
			String divAheadName2, int dispinfolen, String[] dispselectName2,
			int len1, int len2) {
		return getSelectFunc(divAheadName1, divAheadName2, dispinfolen, len1,
				len2);
	}

	private static String fetchSecondSelect(String divAheadName2,
			String[] dispselectName2, String jsinfo) {

		String ahead = "<select name=\"" + divAheadName2 + "\" onChange=\""
				+ jsinfo + "\">";

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < dispselectName2.length; i++) {
			String namevalue = dispselectName2[i];
			String divid = divAheadName2 + i;
			buf.append("<option value=\"" + divid + "\">" + namevalue
					+ "</option>");
		}

		String selectinfo = ahead + buf + "</select>";
		return selectinfo;
	}

	private static String dispView(String[] dispinfo, String divAheadName1,
			String divAheadName2, int loop1, int loop2) {
		StringBuffer divinfo = new StringBuffer();
		int loopStep1 = 0;
		int loopStep2 = -1;
		for (int i = 0; i < dispinfo.length; i++) {
			loopStep2++;
			if (loopStep2 == loop2) {
				loopStep2 = 0;
				loopStep1++;
			}
			String divid = divAheadName1 + loopStep1 + divAheadName2
					+ loopStep2;

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
		return divinfo.toString();
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
