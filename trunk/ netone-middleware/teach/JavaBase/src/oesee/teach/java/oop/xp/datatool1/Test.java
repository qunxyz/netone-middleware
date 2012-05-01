package oesee.teach.java.oop.xp.datatool1;

import java.util.Iterator;
import java.util.List;

import oesee.teach.java.oop.xp.Men;


public class Test {
	
	public static void main(String[] args) {
		Men hum=new Men();
		hum.setAge(12);
		
		DataPools.create(hum);
		DataPools.create(hum);
		
		List list=DataPools.query();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Men object = (Men) iterator.next();
			System.out.println(object.getAge());
		}
	}

}
