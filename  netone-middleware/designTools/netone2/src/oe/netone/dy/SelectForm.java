package oe.netone.dy;

import java.util.ArrayList;
import java.util.List;

import com.jl.common.resource.Resource;
import com.jl.common.security3a.SecurityEntry;

public class SelectForm {

	 public List selectDirRs() throws Exception
     {       
 	     ArrayList<ResourceInfo> aList=new ArrayList();
		
         List listx = SecurityEntry.iv().listDirRs("BUSSFORM.BUSSFORM");
         int len=listx.size();
	        for (int i = 0; i <len; i++) 
	        {   
	        	 ResourceInfo rInfo=new ResourceInfo();
	        	 Resource  resource=(Resource)listx.get(i);
	             rInfo.setRname(resource.getResourcename());
	             rInfo.setRcode(resource.getResourcecode());
	             aList.add(i, rInfo);
	         }
	     
		 return aList;
		}
}
