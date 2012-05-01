package oesee.teach.java.io.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 演示FileInputStream
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo1 {

	/**
	 * 演示FileInputStream
	 * 
	 */
	public static void todo() {
		InputStream input = null;
		try {
			input = new FileInputStream("src/IOTest.java");
//			int read = 0;
//			while ((read = input.read()) != -1) {
//				System.out.print((char) read);
//			}
			

			// byte []bytearr=new byte[input.available()];
			// input.read(bytearr);
			// System.out.println(new String(bytearr));
			// //FileInputStream不支持 reset方法
			// input.reset();
			// while ((read = input.read()) != -1) {
			// System.out.print((char) read);
			// }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		todo();
	}

}
