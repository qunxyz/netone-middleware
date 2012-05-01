package oesee.teach.java.io.other;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * InputStream流的几个相关子类
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class InputSubDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		demo4();

	}

	/**
	 * 演示FileInputStream
	 * 
	 */
	public static void demo1() {
		InputStream input = null;
		try {
			input = new FileInputStream("IOTest.java");
			int read = 0;
			while ((read = input.read()) != -1) {
				System.out.print((char) read);
			}

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

	/**
	 * 演示FileInputStream
	 * 
	 */
	public static void demo1x() {
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

	/**
	 * 演示ByteArrayInputStream <br>
	 * 配合Demo1种的注释部分说明一下 reset()
	 * 
	 */
	public static void demo2() {
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

	/**
	 * 演示Buffer
	 * 
	 */
	public static void demo3() {
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

	public static void demo4() {
		String info = "if(a==4) a=0;\n";
		byte[] buf = info.getBytes();
		InputStream in = new ByteArrayInputStream(buf);
		PushbackInputStream f = new PushbackInputStream(in);
		int c;
		try {
			while ((c = f.read()) != -1) {
				switch (c) {
				case '=':
					if ((c = f.read()) == '=') {
						System.out.print(".eq.");
					} else {
						System.out.print("<-");
						f.unread(c);
					}
					break;

				default:
					System.out.print((char) c);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
