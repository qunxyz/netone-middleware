package oesee.teach.java.io.superu;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class IfcSample {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

	}
	
	public static void todo(String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Map map=null;
		if(name.equals("HashMap")){
			map=new HashMap();
		} else if(name.equals("TreeMap")){
			map=new TreeMap();
		}
				
		
		Map map1=(Map)Class.forName("org.apache.commons.collections.SequencedHashMap").newInstance();
		
		map.put("abc", 1);
	}

}
