package oesee.teach.java.io.other;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oesee.teach.java.two.Human;

public class MySerilzer {

	public static void main(String[] args) throws IOException {
		// 构造内存数据
		List list = new ArrayList();
		Human abc=new Human();
		abc.setName("mike");
		abc.setAge(10);
		list.add(abc);


		// 持久化动态数组
		toSerilizer(list);
	}

	public static void toSerilizer(List list) throws IOException {
		// name:namevalue,age:agevalue;
		StringBuffer but = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			but.append("name:" + object.get("name"));
			but.append(",");
			but.append("age:" + object.get("age"));
			but.append(";");
		}
		byte[] byteall = but.toString().getBytes();
		OutputStream out = new FileOutputStream("d:/seril.ser");
		out.write(byteall);
		out.close();
	}

}
