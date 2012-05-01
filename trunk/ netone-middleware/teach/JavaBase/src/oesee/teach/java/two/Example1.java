package oesee.teach.java.two;

import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Example1 {

	public static void main(String[] args) throws IOException {
		
//		Integer.MAX_VALUE;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("please entry info:");
		// 创建一个动态数组,暂存输入的数据
		List list = new ArrayList();

		String line = br.readLine();
		while (!line.equals(";")) {
			list.add(line);
			line = br.readLine();

		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			System.out.println(object);
		}
	}

}
