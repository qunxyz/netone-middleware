package oesee.teach.java.three;

import org.apache.commons.lang.StringUtils;

/**
 * 字符分割
 * 
 * @author chen.jia.xun <br>
 *         email:chenjx@fjycit.com<br>
 *         mobile:15860836998
 * 
 */
public class AboutSplit {

	public static void main(String[] args) {
		// 第一种方式（需要转意处理特殊正则表达式片段符号 ）
		String str = "abc[efg[cdf";
		String[] strArr = str.split("\\["); // 注意转意
		for (int i = 0; i < strArr.length; i++) {
			System.out.println(strArr[i]);
		}

		// 第二种方式 (使用StringUtils工具包)
		String str1 = "abc[efg[cdf";
		String[] strArr1 = StringUtils.split(str1, "[");
		for (int i = 0; i < strArr.length; i++) {
			System.out.println(strArr[i]);// 输出
		}
	}

}
