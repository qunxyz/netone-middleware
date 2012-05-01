package oesee.teach.java.io.input;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo4 {
	/**
	 * 演示Buffer
	 * 
	 */
	public static void todo() {
		InputStream input = null;
		try {
			input = new FileInputStream("IOTest.java");
			BufferedInputStream inputBuf = new BufferedInputStream(input);
			int size = inputBuf.available();
			System.out.println("文件大小:" + size);

			for (int i = 0; i < size; i++) {
				System.out.print((char) inputBuf.read());
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
