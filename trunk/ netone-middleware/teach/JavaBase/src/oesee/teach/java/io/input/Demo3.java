package oesee.teach.java.io.input;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class Demo3 {
	/**
	 * 演示ByteArrayInputStream <br>
	 * 配合Demo1种的注释部分说明一下 reset()
	 * 
	 */
	public static void todo() {
		InputStream input = null;
		String inputValue = "abcdefg";
		try {
			input = new ByteArrayInputStream(inputValue.getBytes());
			int size = input.available();
			for (int i = 0; i < size; i++) {
				System.out.print((char) input.read());
			}
			System.out.println();
			input.reset();
			for (int i = 0; i < size; i++) {
				System.out.print((char) input.read());
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
