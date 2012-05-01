package oesee.teach.java.three.reg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 习题参考答案
 * 
 * 存在这个一个字符串 String abc=“ [name=mike][age=20] [name=jim][age=22]
 * [name=tomi][age=23] ” 程序根据这个字符串,来创建Human对象,并且遍利输出所有的human对象
 * 
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class HomeWork {

	public static void main(String[] args) throws IOException {
		// 测试数据
		String abc = "[name=mike][age=20] [name=jim][age=22] [name=tomi][age=23]";
		// 针对测试数据,所定义的正则表达式
		String _REG = "\\[name=\\w+\\]\\s*\\[age=[0-9]{1,2}\\]";
		Pattern pat = Pattern.compile(_REG);
		Matcher mat = pat.matcher(abc);

		// 识别 类似 [name=mike] 这种字符串的正则表达式, [name=mike]将匹配到 =mike
		String _REG_NAME = "=\\w+";
		Pattern pat_name = Pattern.compile(_REG_NAME);
		// 识别 类似 [age=20] 这种字符串的正则表达式,[age=20]将匹配到 =20
		String _REG_AGE = "=[0-9]{1,2}";
		Pattern pat_age = Pattern.compile(_REG_AGE);

		// 创建动态数组保存Human对象
		List list = new ArrayList();
		while (mat.find()) {
			// 找到第一个完整的Human信息
			String findElement = mat.group();

			// 从Human信息中获得名字信息
			Matcher mat_name = pat_name.matcher(findElement);
			Human human = new Human();
			if (mat_name.find()) {
				human.setName(mat_name.group().substring(1));
			}
			// 从Human信息中获得年龄信息
			Matcher mat_age = pat_age.matcher(findElement);
			if (mat_age.find()) {
				human.setAge(Integer.parseInt(mat_age.group().substring(1)));
			}
			// 将找到的Human对象加入动态数组
			list.add(human);
		}

		// 遍历输出所有的人员
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Human human = (Human) itr.next();
			System.out.println(human.getName() + "," + human.getAge());
		}

	}
}
