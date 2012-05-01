package oesee.teach.java.four;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 
 * @author chen.jia.xun
 * 
 */
public class ExceptionMode {

	/**
	 * 获得持久化数据
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 */
	public static Object demo1(String filename) {

		ObjectInputStream in = null;
		Object obj = null;

		try {// 程序逻辑中,没有异常处理语句(1)
			in = new ObjectInputStream(new FileInputStream(filename));
			obj = in.readObject();
			in.close();
		} catch (FileNotFoundException e) {// 系统预先考虑了,并且是强制的(2)
			e.printStackTrace();

		} catch (IOException e) {// 系统预先考虑了,并且是强制的(2)
			e.printStackTrace();

		} catch (ClassNotFoundException e) {// 系统预先考虑了,并且是强制的(2)
			e.printStackTrace();
		} catch (Exception e) {// 处理无法预知的异常
			e.printStackTrace();

		}

		return obj;
	}

	public static void demo2(String args[]) {
		char c;
		int a, b = 0;
		int[] array = new int[7];
		String s = "Hello";
		try {
			a = 1 / b;
			array[8] = 0;
			c = s.charAt(8);
		} catch (ArithmeticException ae) {
			System.out.println("Catch" + ae);
		} catch (ArrayIndexOutOfBoundsException ai) {
			System.out.println("Catch" + ai);
		} catch (StringIndexOutOfBoundsException se) {
			System.out.println("Catch" + se);
		}
	}

	/**
	 * 获得持久化数据
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 */
	public static Object demo3(String filename) {

		ObjectInputStream in = null;

		try {// 程序逻辑中,没有异常处理语句(1)
			in = new ObjectInputStream(new FileInputStream(filename));
			return in.readObject();
		} catch (FileNotFoundException e) {// 系统预先考虑了,并且是强制的(2)
			e.printStackTrace();
		} catch (IOException e) {// 系统预先考虑了,并且是强制的(2)
			e.printStackTrace();
		} catch (ClassNotFoundException e) {// 系统预先考虑了,并且是强制的(2)
			e.printStackTrace();
		} catch (Exception e) {// 可处理无法预料的异常(3)
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();// 打印异常堆栈信息
			}
		}
		return null;
	}

	/**
	 * 获得持久化数据
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Object demo4(String filename) throws ClassNotFoundException {

		ObjectInputStream in = null;
		Object obj = null;
		try {// 程序逻辑中,没有异常处理语句(1)
			in = new ObjectInputStream(new FileInputStream(filename));
			obj = in.readObject();
			in.close();

		} catch (IOException e) {// 系统预先考虑了,并且是强制的(2)
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public static void demo5() {
		try {
			throw new ArithmeticException();
		} catch (ArithmeticException ae) {
			System.out.println(ae);
		}
	}

	public static void main(String[] args) {
		demo2(null);
	}
}
