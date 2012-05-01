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
	 * ��ó־û�����
	 * 
	 * @param filename
	 *            �ļ���
	 * @return
	 */
	public static Object demo1(String filename) {

		ObjectInputStream in = null;
		Object obj = null;

		try {// �����߼���,û���쳣�������(1)
			in = new ObjectInputStream(new FileInputStream(filename));
			obj = in.readObject();
			in.close();
		} catch (FileNotFoundException e) {// ϵͳԤ�ȿ�����,������ǿ�Ƶ�(2)
			e.printStackTrace();

		} catch (IOException e) {// ϵͳԤ�ȿ�����,������ǿ�Ƶ�(2)
			e.printStackTrace();

		} catch (ClassNotFoundException e) {// ϵͳԤ�ȿ�����,������ǿ�Ƶ�(2)
			e.printStackTrace();
		} catch (Exception e) {// �����޷�Ԥ֪���쳣
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
	 * ��ó־û�����
	 * 
	 * @param filename
	 *            �ļ���
	 * @return
	 */
	public static Object demo3(String filename) {

		ObjectInputStream in = null;

		try {// �����߼���,û���쳣�������(1)
			in = new ObjectInputStream(new FileInputStream(filename));
			return in.readObject();
		} catch (FileNotFoundException e) {// ϵͳԤ�ȿ�����,������ǿ�Ƶ�(2)
			e.printStackTrace();
		} catch (IOException e) {// ϵͳԤ�ȿ�����,������ǿ�Ƶ�(2)
			e.printStackTrace();
		} catch (ClassNotFoundException e) {// ϵͳԤ�ȿ�����,������ǿ�Ƶ�(2)
			e.printStackTrace();
		} catch (Exception e) {// �ɴ����޷�Ԥ�ϵ��쳣(3)
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();// ��ӡ�쳣��ջ��Ϣ
			}
		}
		return null;
	}

	/**
	 * ��ó־û�����
	 * 
	 * @param filename
	 *            �ļ���
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Object demo4(String filename) throws ClassNotFoundException {

		ObjectInputStream in = null;
		Object obj = null;
		try {// �����߼���,û���쳣�������(1)
			in = new ObjectInputStream(new FileInputStream(filename));
			obj = in.readObject();
			in.close();

		} catch (IOException e) {// ϵͳԤ�ȿ�����,������ǿ�Ƶ�(2)
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
