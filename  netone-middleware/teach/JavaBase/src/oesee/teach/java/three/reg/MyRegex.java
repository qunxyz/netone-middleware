package oesee.teach.java.three.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegex {

	public static void main(String[] args) {

	}

	/**
	 * ��Matches����
	 */
	public static void withOutCompile() {
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < 999999; i++) {
			boolean rs = Pattern.matches("a*b.*", "aaaaab" + i);
		}
		long endtime = System.currentTimeMillis();
		System.out.println(endtime - starttime);
	}

	/**
	 * ��׼��Mather����
	 */
	public static void withCompile() {

		long starttime = System.currentTimeMillis();
		Pattern p = Pattern.compile("a*b.*");
		for (int i = 0; i < 999999; i++) {
			Matcher m = p.matcher("aaaaab" + i);
			boolean b = m.matches();
		}
		long endtime = System.currentTimeMillis();
		System.out.println(endtime - starttime);
	}

	/**
	 * ����FIND�߼�
	 */
	public static void testFind() {
		Pattern patx = Pattern.compile("a*b");
		Matcher matx = patx.matcher("aaaaabababbaab");

		while (matx.find()) {
			String matchSubString = matx.group();
			int stratIndex = matx.start();
			int endIndex = matx.end();
			System.out.println("�ҵ��Ӵ�:" + matchSubString + " ��������ʼλ��:"
					+ stratIndex + ",����λ��:" + endIndex);
		}
	}

}
