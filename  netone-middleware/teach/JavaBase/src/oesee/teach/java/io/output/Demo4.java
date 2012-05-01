package oesee.teach.java.io.output;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.Reader;

/**
 * Reader几个相关的子类
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		demo1();

	}

	/**
	 * 演示FileReader
	 * 
	 */
	public static void demo1() {
		Reader input = null;
		try {
			input = new FileReader("src/IOTest.java");
			int read = 0;
			while ((read = input.read()) != -1) {
				System.out.print((char) read);
			}

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

}
