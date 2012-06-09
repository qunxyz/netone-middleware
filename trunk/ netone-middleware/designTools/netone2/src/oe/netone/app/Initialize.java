package oe.netone.app;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

public class Initialize {
	public String initialize(String id){
     String str=null;
    System.out.println(id);
	ResourceRmi resourceRmi = null;
	try {

	resourceRmi = (ResourceRmi) RmiEntry.iv("resource");

	resourceRmi.dropAllSubResource(id);
	str="初始化成功";
	} catch (Exception e) {
	
		str="初始化失败";
	}
	return str;
	}
}
