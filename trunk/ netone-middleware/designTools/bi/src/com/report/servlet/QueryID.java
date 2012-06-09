package com.report.servlet;

import java.util.List;
import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;
 public class QueryID {
     public String QueryID(String naturalname,String ID){
    	 List listy=null;
		try {
			listy = SecurityEntry.iv().listRsInDir(naturalname, "", 0, 100, "");
			for (int j = 0; j < listy.size(); j++) {
	 			Resource resource = (Resource) listy.get(j);
	 			String str=resource.getResourcecode();
	 			if(str.equals(naturalname+"."+ID)){
	 				return "0";
	 			}
	 		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return "1";
     }
}
