package oesee.teach.java.three.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegex {

	public static void main(String[] args) {

	}

	/**
	 * 简单Matches处理
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
	 * 标准的Mather处理
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
	 * 处理FIND逻辑
	 */
	public static void testFind() {
		Pattern patx = Pattern.compile("a*b");
		Matcher matx = patx.matcher("aaaaabababbaab");

		while (matx.find()) {
			String matchSubString = matx.group();
			int stratIndex = matx.start();
			int endIndex = matx.end();
			System.out.println("找到子串:" + matchSubString + " 在索引开始位置:"
					+ stratIndex + ",结束位置:" + endIndex);
		}
	}

}
