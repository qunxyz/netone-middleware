package oesee.teach.java.three;

/**
 * ���ܴ���Ա�
 * 
 * @author chen.jia.xun <br>
 *         email:chenjx@fjycit.com<br>
 *         mobile:15860836998
 * 
 */
public class Performance {

	public static void main(String[] args) {

	}

	/**
	 * String��������ʱ
	 */
	public static void useStringMode() {
		long starttime = System.currentTimeMillis();
		String str = "i love ";
		for (int i = 0; i < 9999; i++) {
			str += i;
		}
		System.out.println("use time:" + str);

	}

	/**
	 * StringBuffer ����ʱ
	 */
	public static void useStringBufferMode() {
		long starttime = System.currentTimeMillis();
		StringBuffer str = new StringBuffer("i love ");
		for (int i = 0; i < 9999; i++) {
			str.append(i);
		}
		System.out.println("use time:" + str);

	}

}
