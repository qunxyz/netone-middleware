package oesee.teach.java.one;

/**
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class SanJiao {

	public static void main(String[] args) {
		// �����������
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		System.out.println("�����������:");

		for (int i = 10; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}

}
