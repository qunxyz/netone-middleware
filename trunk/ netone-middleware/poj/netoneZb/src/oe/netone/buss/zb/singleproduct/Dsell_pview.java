package oe.netone.buss.zb.singleproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ß^³Ì±í†ÎSOAÄ_±¾
 * @author robanco
 *
 */
public class Dsell_pview {
	
	public Object test1(){
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
