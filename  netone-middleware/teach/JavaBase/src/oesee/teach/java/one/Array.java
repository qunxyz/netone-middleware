package oesee.teach.java.one;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sample4: 数组和链表
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Array {
	public static void main(String[] args) {
		// -------对于原始类型--------
		// 已经知数组的元素
		int[] value0 = { 1, 2, 3 };
		// 不知数组的元素,但知道元素的总数
		int[] value01 = new int[3];
		// 既不知道数组的元素,也不知道数组的个数
		int num = 0;
		int[] value02 = new int[num];

		// -------对象类型-------

		String[] value1 = { new String("1"), new String("2"), new String("3") };
		String[] value11 = new String[3];
		int num1 = 0;
		String[] value12 = new String[num1];

		// ------自定义类型-----------

		// 既不知道数组的元素,也不知道数组的个数
		List list = new ArrayList();
		list.add(1);
		list.remove(0);
		
		


	}
}
