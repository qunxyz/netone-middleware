package oesee.teach.java.one;

public class Debug {

	public static void main(String[] args) {
		int[] arrs = { 1, 2, 5, 7, 8 };
		for (int i = 0; i < arrs.length; i++) {
			todo(arrs, i);
		}
	}

	public static void todo(int[] arrs, int index) {
		System.out.print(index++);
		System.out.print(" ");
		System.out.println(arrs[index]);
	}

}
