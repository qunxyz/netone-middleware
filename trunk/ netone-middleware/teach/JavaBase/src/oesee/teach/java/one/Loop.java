package oesee.teach.java.one;

/**
 * Sample5: 循环
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Loop {

	public static void main(String[] args) {
		// 已经知数组的元素
		int[] value = { 1, 2, 3 };

		// FOR循环
		for (int i = 0; i < value.length; i++) {
			System.out.println(value[i]);
		}
		// While循环
		int i = 0;
		while (i < value.length) {
			System.out.println(value[i]);
			i++;
		}
		// Do While循环
		int i1 = 0;
		do {
			System.out.println(value[i1]);
			i1++;
		} while (i1 < value.length);
	}
}
