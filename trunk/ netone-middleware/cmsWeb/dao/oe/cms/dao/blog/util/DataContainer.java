package oe.cms.dao.blog.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 对象持久化
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:xanvaxp@hotmail.com<br>
 *         belong to:www.oesee.org
 * @version 1.0 <br>
 * 
 * 状态: <br>
 * finally done by chen.jia.xun(Robanco) 2006-11-2 <br>
 * 
 */

public class DataContainer {
	/**
	 * 持久化数据
	 * 
	 * @param obj
	 *            持久化对象
	 * @param name
	 *            持久化文件名
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
	 * 获得持久化数据
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 */
	public static Object fetchData(String filename) {

		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			return in.readObject();
		} catch (Exception e) {
			System.err.println("没文件");
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
