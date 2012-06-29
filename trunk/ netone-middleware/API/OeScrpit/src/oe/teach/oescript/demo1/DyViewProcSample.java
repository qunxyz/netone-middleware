package oe.teach.oescript.demo1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DyViewProcSample {
	
	public static void main(String[] args) {

	}
	
	public List  todo(){
		List list=new ArrayList();
		
		Map map=new HashMap();
		map.put("column1", 100);
		map.put("column2", "mike");
		map.put("column3", "bbbc");
		
		Map map1=new HashMap();
		map1.put("column1", 1000);
		map1.put("column2", "mike1");
		map1.put("column3", "bbbc1");
		
		Map map2=new HashMap();
		map2.put("column1", 3000);
		map2.put("column2", "mike3");
		map2.put("column3", "bbbc3");
		
		System.out.println("condition:---"+"$(condition)");
		
		list.add(map);list.add(map1);list.add(map2);
		return list;
	}

}
