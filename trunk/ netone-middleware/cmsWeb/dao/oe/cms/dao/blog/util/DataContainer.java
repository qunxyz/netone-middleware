package oe.cms.dao.blog.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * ����־û�
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:xanvaxp@hotmail.com<br>
 *         belong to:www.oesee.org
 * @version 1.0 <br>
 * 
 * ״̬: <br>
 * finally done by chen.jia.xun(Robanco) 2006-11-2 <br>
 * 
 */

public class DataContainer {
	/**
	 * �־û�����
	 * 
	 * @param obj
	 *            �־û�����
	 * @param name
	 *            �־û��ļ���
	 */
	public static void serial(Object obj, String name) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(name));
			out.writeObject(obj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ó־û�����
	 * 
	 * @param filename
	 *            �ļ���
	 * @return
	 */
	public static Object fetchData(String filename) {

		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			return in.readObject();
		} catch (Exception e) {
			System.err.println("û�ļ�");
			return new ArrayList();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
