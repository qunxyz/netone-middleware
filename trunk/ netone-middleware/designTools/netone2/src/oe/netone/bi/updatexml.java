package oe.netone.bi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.jl.common.resource.Resource;
import com.jl.common.workflow.DbTools;

import oe.netone.dy.ResourceInfo;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class updatexml {
	
	public static void rexml(String xmlid, String cname,
			String extendattribute) throws Exception {
		Serializable idcreated=null;
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		UmsProtectedobject upo=rs.loadResourceById(xmlid);
		upo.setName(cname);
		upo.setActionurl("");
		upo.setExtendattribute(extendattribute);
		upo.setObjecttype("");
		upo.setDescription("");
		idcreated = rs.updateResource(upo);
		
	}
	public static void dropxml(String xmlid) throws Exception{
		Serializable idcreated1=null;
		ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
		idcreated1=rs.dropResource(xmlid);
	}
    public static List selectDMlv(String idString) throws Exception
    {
    	ArrayList<ResourceInfo> aList = new ArrayList();
        String sqlString = "select * from netone.ums_protectedobject  where  ID='"+idString+"'";
   
       
 	    List list=DbTools.queryData(sqlString);
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
 			Map object = (Map)iterator.next();
 			 ResourceInfo rInfo = new ResourceInfo();
 			rInfo.rname =(String)object.get("NAME");
 			rInfo.objecttype =(String)object.get("objectType");
 			rInfo.rcode =(String)object.get("NATURALNAME");
 			rInfo.FID =(String)object.get("ID");
 			
// 			rInfo.dateTime= ((Timestamp)object.get("created")).toString();
 			rInfo.extendattribute=(String)object.get("extendattribute");
 			aList.add(rInfo);
           }
        for (int j = 0; j < aList.size(); j++) {
        	  // System.out.println(((ResourceInfo)aList.get(j)).getFID());
		}
     
        return aList;
    }
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//updatexml.rexml("4028f8e832b2ca800132b30ff6c60006", "哈哈1111", "ami");
		System.out.println("?"+updatexml.selectDMlv("b76b8821e58a11e0a453bd1661cc7dd1_"));
		
	}

}
