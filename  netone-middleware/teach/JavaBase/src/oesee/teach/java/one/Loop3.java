package oesee.teach.java.one;

/**
 * Sample7: Ñ­»·¸³Öµ
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Loop3 {

	public static void main(String[] args) {
		int[] value = new int[3];
		for (int i = 0; i < value.length; i++) {
			value[i] = i;
		}
		for (int i = 0; i < value.length; i++) {
			System.out.println(value[i]);
		}

		Integer[] valuex = new Integer[3];
		for (int i = 0; i < valuex.length; i++) {
			valuex[i] = i;
		}
		for (int i = 0; i < valuex.length; i++) {
			System.out.println(valuex[i]);
		}

		String[] valuey = new String[3];
		for (int i = 0; i < valuey.length; i++) {
			valuey[i] = String.valueOf(i);
		}
		for (int i = 0; i < valuey.length; i++) {
			System.out.println(valuey[i]);
		}
	}

}
