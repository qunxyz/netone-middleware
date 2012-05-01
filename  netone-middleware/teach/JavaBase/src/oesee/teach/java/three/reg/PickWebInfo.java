package oesee.teach.java.three.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 爬虫程序，从web上抽取信息
 * 
 * @author ibm
 * 
 */
public class PickWebInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 获得某个栏目信息的规则
		String rule1 = "<!--技术动态显示区  1-->[^わ]+<!--技术动态显示区  2-->";
		// 解析出所有的URL地址的规则
		String rule2 = "<\\s*a\\s+href\\s*=\\s*\".*</a>";
		// 解析
		PickWebInfo.pickWebInfo("http://java.ccidnet.com/", "gb2312", rule1,
				rule2);

	}

	public static void pickWebInfo(String url, String encode, String rule1,
			String rule2) {

		String info = ReadNetInfo.netParser(url, encode);
		Pattern pat = Pattern.compile(rule1);
		Matcher mat = pat.matcher(info);
		if (mat.find()) {
			String infonext = mat.group();

			Pattern patx = Pattern.compile(rule2);
			Matcher matx = patx.matcher(infonext);
			while (matx.find()) {
				System.out.println(matx.group());
			}
		}
	}

}
