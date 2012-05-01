package oesee.teach.java.two;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListSample {

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("a");
		list.add("b");
		list.add("c");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			System.out.println(object);
		}
//		for (int i = 0; i < args.length; i++) {
//			System.out.println(args[i]);
//		}
	}

}
