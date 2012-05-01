package oesee.teach.java.io.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo2 {
	/**
	 * 演示FileInputStream 带缓存的输入
	 * 
	 */
	public static void todo() {
		InputStream input = null;
		try {
			input = new FileInputStream("IOTest.java");
			int size = input.available();
			System.out.println("文件大小:" + size);
			int n = size / 20;
			byte[] bf = new byte[30];
			int rs = 0;
			while ((rs = input.read(bf)) != -1) {
				for (int i = 0; i < rs; i++) {
					System.out.print((char) bf[i]);
				}
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
