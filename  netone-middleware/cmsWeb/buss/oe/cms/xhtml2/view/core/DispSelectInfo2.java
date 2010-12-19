package oe.cms.xhtml2.view.core;



/**
 * 
 * ��Ѷ�б�ѡ��2��<br>
 * ʹ�÷����� mc2.xhtmlinfo(Object arg0, Object arg1);<br>
 * ���� arg0 ��Ѷ��Ϣ���� <br>
 * ���� arg1 ����Ѷ��Ϣ��Ӧ������˵�����飬������������Ԫ��
 * arr[0]�б����һ��ѡ����Ϣ�б���Ϣ���ö��Ÿ�������arr[1]�б������ѡ����Ϣ�б�<br>
 * ע���: �����б��Ӧ��arg0����ĸ��� = һ������ * ��������
 * 
 * 
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public final class DispSelectInfo2 {
	
	/**
	 * ѡ����ʾ����
	 * 
	 * @param arg0
	 *            ��Ҫѡ����ʾ��Html��Ϣ���� String[]
	 * @param arg1
	 *            ��ѡ���������Ϣ String[]
	 */
	public static String xhtmlinfo(String[] info, String[] name1, String[] name2) {

		int lenAll = info.length;
		int len1 = name1.length;
		int len2 = name2.length;
		if (lenAll != len1 * len2) {
			return "����:��Ϣ�����ѡ���б�Ĵ�����һ��";
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
			return "û�п�ѡ�����Ϣ";
		}
		// �����ʾ��Ϣ��ID
		String divAheadName = "disp" + System.currentTimeMillis();
		// ѡ��
		String divAheadName1 = divAheadName + "1";
		String divAheadName2 = divAheadName + "2";

		String selinfo = fetchSelectTarget(divAheadName1, divAheadName2,
				dispinfo.length, dispselectName2, dispselectName1.length,
				dispselectName2.length);

		String sele1 = fetchFirstSelect(divAheadName1, dispselectName1, selinfo);

		String sele2 = fetchSecondSelect(divAheadName2, dispselectName2,
				selinfo);
		// ȫ����ʾ��Ϣ
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
