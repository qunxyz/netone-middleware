package oesee.teach.java.three;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 解析字符串 构造Human信息
 * 
 * @author chen.jia.xun <br>
 *         email:chenjx@fjycit.com<br>
 *         mobile:15860836998
 * 
 */
public class StringOfHuman {

	final static String _HUMEN_LIST_INFO = "[name=mike][age=4q][name=jim][age=40]";

	public static void main(String[] args) {
		// 暂存人员信息字符串
		String tmpInfo = _HUMEN_LIST_INFO;
		// 先取得名字 ，因为根据分析字符串的组成结构得知，name的信息一定在 age之前
		String firstName = StringUtils.substringBetween(tmpInfo, "[", "]");
		// 构建动态数组，保存所有的人员信息
		List list = new ArrayList();
		boolean success = true;
		// 依次处理所有的人员信息
		while (firstName != null) {
			String[] nameArr = firstName.split("=");
			if (nameArr[0].equals("age")) {
				System.err.print("无效表达式:" + _HUMEN_LIST_INFO + "错误原因:"
						+ firstName + " 没有与之对应的 Name信息  ");
				success = false;
				break;
			}
			// 取得当前获得的姓名之后的字符串
			tmpInfo = StringUtils.substringAfter(tmpInfo, "]");
			// 取得当前获得的姓名之后的字符串中的第一个位于[]间的年龄信息
			String firstAge = StringUtils.substringBetween(tmpInfo, "[", "]");
			// 构造一个 Human对象存储 当前解析出来的人员信息
			Human human = new Human();
			// 取得姓名，根据字符串格式得知姓名位于 符号"="之后
			String name = StringUtils.substringAfter(firstName, "=");
			human.setName(name);
			// 取得姓名，根据字符串格式得知年龄位于 符号"="之后
			String[] ageArr = StringUtils.split(firstAge,"=");
			if (ageArr[0].equals("age")) {
				if (ageArr[1].matches("\\d+")) {
					// 转换 类型，因为 Human中的年龄是整型
					Integer age = Integer.valueOf(ageArr[1]);
					human.setAge(age);
				}
			} else {
				System.err.print("无效表达式:" + _HUMEN_LIST_INFO + "错误原因:" + name
						+ " 没有与之对应的 Age信息 ");
				success = false;
				break;
			}

			// 往动态数组中加入 解析构建出的Human信息
			list.add(human);
			// 继续取子串，为解析下一个Human信息做准备
			tmpInfo = StringUtils.substringAfter(tmpInfo, "]");
			// 先取得下一个 Human的名字 ，因为根据分析字符串的组成结构得知，name的信息一定在 age之前
			firstName = StringUtils.substringBetween(tmpInfo, "[", "]");
		}
		if (success) {
			// 遍历出所有的 Human信息
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Human object = (Human) iterator.next();
				System.out.println(object.getName() + ":" + object.getAge());
			}
		}

	}
}
