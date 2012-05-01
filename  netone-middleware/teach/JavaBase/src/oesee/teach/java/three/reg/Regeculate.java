package oesee.teach.java.three.reg;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 关于正则表达式
 * 
 * @author chen.jia.xun(Robanc) <br>
 *         www.oesee.org
 * 
 */
public class Regeculate {

	public static void main(String[] arg) {

		// demo1();
		// demo2();
		// demo3();
		// demo3();
		demo3();
	}

	public static void demos1() {
		System.out.println("wUke".matches("[A-z]+"));
		System.out.println("你好，".matches("[\u0391-\uFFE5]+"));
	}

	/**
	 * 正则表达式中的[]
	 * 
	 */
	public static void demox() {

		String x = "select a,b,c from abc";
		String y = "SELECT\\s+(\\w+,?)+\\s+FROM\\s+[A-Z]+";
		boolean rs1 = x.toUpperCase().matches(y);
		// System.out.println(rs1);

		String rs2 = StringUtils.substringBetween(x.toUpperCase(), "SELECT",
				"FROM");
		rs2 = rs2.trim();
		int rs22 = StringUtils.lastIndexOf(rs2, ",");

		boolean rs3 = rs22 == rs2.length();

		System.out.println(rs1 && rs3);

	}

	/**
	 * 正则表达式中的.
	 * 
	 */
	public static void demo1() {
		Pattern pat = Pattern.compile("t.*n");
		Matcher mat = pat.matcher("tan ten tdfn ton t#n tpn t n");
		while (mat.find()) {
			System.out.println("find :" + mat.group());
		}
	}

	/**
	 * 正则表达式中的[]
	 * 
	 */
	public static void demo2() {
		Pattern pat = Pattern.compile("t[abcdfg]n");
		Matcher mat = pat.matcher("tan ten tdfn ton t#n tpn t n taan");
		while (mat.find()) {
			System.out.println("find :" + mat.group());
		}
	}

	/**
	 * 用正则表达式处理来解析DDL的create语句
	 * 
	 * @return
	 */
	public static List demo3() {

		String create = "CREATE TABLE TABLEX  {COLUMN1,COLUMN2,COLUMN3,PRIMARY KEY COLUMN1}";
		// DDL的Create语句的正则表达式
		String regexModel = "CREATE\\s+TABLE\\s+\\w+\\s*\\{(\\w+\\s*,)+\\s*PRIMARY\\s*KEY\\s*\\w+\\s*\\}";
		// 检查DDL的Create语句的语法是否正确
		boolean match = create.matches(regexModel);
		if (match) {
			System.out.println("语法正确");
		} else {
			System.out.println("语法错误");
		}
		// 获得create语句中的字段
		String regexColumnSetModel = "\\w+\\s*,";
		String substr = StringUtils.substringBetween(create, "{", "}");

		List listColumns = new ArrayList();

		Pattern pat2 = Pattern.compile(regexColumnSetModel);
		Matcher mat2 = pat2.matcher(substr);
		while (mat2.find()) {
			listColumns.add(mat2.group());
		}
		// 打印所有的字段
		for (Iterator iterator = listColumns.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			System.out.println("columns has: " + object);
		}

		String primaryFromRegex = "PRIMARY\\s+KEY\\s+";
		Pattern pat3 = Pattern.compile(primaryFromRegex);
		Matcher mat3 = pat3.matcher(substr);
		while (mat3.find()) {
			String preinfo = mat3.group();
			System.out.println("PRIMARY KEY IS "
					+ StringUtils.substringAfter(substr, preinfo));
		}

		return listColumns;

	}

	/**
	 * String 中match方法和 使用pattern+matcher的细微区别
	 * 
	 */
	public static void demo4() {

		String REG = "[A-z],[0-9]{2}";
		// 测试字符串
		String test1 = "a,11";
		String test2 = "e,123";
		// 使用String 的match方法来分析匹配
		System.out.println(test1 + " 匹配结果:" + test1.matches(REG));
		System.out.println(test2 + " 匹配结果:" + test2.matches(REG));
		// 使用pattern+matcher来分析匹配
		Pattern pat1 = Pattern.compile(REG);
		Matcher mat1 = pat1.matcher(test1);
		Matcher mat2 = pat1.matcher(test2);
		System.out.println(test1 + " 匹配结果:" + mat1.find());
		System.out.println(test2 + " 匹配结果:" + mat2.find());

	}

}
