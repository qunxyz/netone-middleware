package oesee.teach.java.io.output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oesee.teach.java.two.Human;

/**
 * 
 * @author chen.jia.xun
 * 
 */
public class Demo3 {
	static List list = new ArrayList();

	public static void main(String[] args) throws Exception {
		recoverObject();
	}

	public static void serilzerObject() throws Exception {
		// 构造内存数据

		List list = new ArrayList();
		Human abc = new Human();
		abc.setName("mike");
		abc.setAge(10);
		list.add(abc);

		OutputStream out = new FileOutputStream("d:/abc1.data");
		ObjectOutputStream outObj = new ObjectOutputStream(out);

		outObj.writeObject(list);

	}

	public static void recoverObject() throws IOException,
			ClassNotFoundException {
		InputStream in = new FileInputStream("d:/abc1.data");
		ObjectInputStream inObj = new ObjectInputStream(in);
		List mapRecover = (List) inObj.readObject();
		for (Iterator iterator = mapRecover.iterator(); iterator.hasNext();) {
			Human object = (Human) iterator.next();
			System.out.println(object.getName() + "," + object.getAge() + ",");
		}
	}

}
