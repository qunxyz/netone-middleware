package oesee.teach.java.one;

/**
 * Sample5: ѭ��
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Loop {

	public static void main(String[] args) {
		// �Ѿ�֪�����Ԫ��
		int[] value = { 1, 2, 3 };

		// FORѭ��
		for (int i = 0; i < value.length; i++) {
			System.out.println(value[i]);
		}
		// Whileѭ��
		int i = 0;
		while (i < value.length) {
			System.out.println(value[i]);
			i++;
		}
		// Do Whileѭ��
		int i1 = 0;
		do {
			System.out.println(value[i1]);
			i1++;
		} while (i1 < value.length);
	}
}
