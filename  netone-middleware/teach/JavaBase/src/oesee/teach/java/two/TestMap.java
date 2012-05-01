package oesee.teach.java.two;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.SequencedHashMap;

public class TestMap {


	public static void main(String[] args) {

		Map map = new HashMap();

		map.put(1, "a");
		map.put(2, "b");
		// System.out.println(map.size());

		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry type = (Map.Entry) iterator.next();
			System.out.println(type.getKey());
		}

		// Map b = new HashMap();
		// b.put("b", 2);
		// b.put("c", 3);
		// b.put("a", 1);
		//
		// Map b1 = new TreeMap();
		// b1.put("b", 2);
		// b1.put("c", 3);
		// b1.put("a", 1);
		//
		// Map b2 = new Hashtable();
		// b2.put("b", 2);
		// b2.put("c", 3);
		// b2.put("a", 1);
		//
		// Map b3 = new SequencedHashMap();
		//
		// for (Iterator itr = b.keySet().iterator(); itr.hasNext();) {
		// System.out.print(itr.next());
		// }
		// System.out.println();
		//
		// for (Iterator itr = b1.keySet().iterator(); itr.hasNext();) {
		// System.out.print(itr.next());
		// }
		// System.out.println();
		// for (Iterator itr = b2.keySet().iterator(); itr.hasNext();) {
		// System.out.print(itr.next());
		// }
		// System.out.println();

	}

}
