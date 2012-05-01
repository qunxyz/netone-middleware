package oesee.teach.java.three;

/**
 * 性能处理对比
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
	 * String操作消耗时
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
	 * StringBuffer 消耗时
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
