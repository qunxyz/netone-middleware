package oe.netone.dy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jl.common.workflow.DbTools;

public class QuearGongZuoLiu{
	public List selectforms() {
		List selectList = new ArrayList();
        String sqlString = "select 	* from netone.ums_protectedobject where NATURALNAME like  '%BUSSWF.BUSSWF%' and objectType='WF';";
         List list=DbTools.queryData(sqlString);
    	  for (Iterator iterator = list.iterator(); iterator.hasNext();) {
    		  Map object = (Map)iterator.next();
    		  ResourceInfo rsInfo = new ResourceInfo();
    		  rsInfo.rname =(String)object.get("NAME");
    		  rsInfo.objecttype =(String)object.get("objectType");
    		  rsInfo.rcode =(String)object.get("NATURALNAME");
    		  rsInfo.dateTime= ((Timestamp)object.get("created")).toString();
    		  rsInfo.extendattribute=(String)object.get("extendattribute");
    		  if(object.get("ACTIVE").equals("1")){
    			  rsInfo.setModel("有效");
    		  }else{
    			  rsInfo.setModel("无效");
    		  }
    		  rsInfo.setFID((String)object.get("ID"));
    		  selectList.add(rsInfo);
    	   }
    	
		return selectList;
	}
	
}