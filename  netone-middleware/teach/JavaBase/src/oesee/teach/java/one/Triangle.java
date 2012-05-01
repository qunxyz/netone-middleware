package oesee.teach.java.one;

/**
 * 在控制台实现三角输出
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class Triangle {
	
	
	private int cycle;

	public Triangle(int i) {
		cycle = i;
	}

	public int recursion(int i, int j) {
		if (j == 0) {
			return 1;
		} else {
			int each = (i - j + 1) / j;
			return each * recursion(1, j - 1);
		}
	}

	public static void main(String[] args) {
		int cycle = 9;
		Triangle triangle = new Triangle(cycle);
		for (int i = 0; i <= cycle; i++) {
			for (int k = 0; k < cycle; k++) {
				System.out.print(" ");
			}
			for (int j = 0; j <= i; j++) {
				System.out.print(triangle.recursion(i, j));
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}