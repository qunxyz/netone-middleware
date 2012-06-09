package oe.jl.common.config;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import com.jl.common.netone.UmsProtecte;
import com.jl.common.workflow.DbTools;

import org.apache.commons.lang.StringUtils;

public class Resourcejudge {
	
	//获取到上一级资源的类型
	public String  judgetype(String naturalname){
        UmsProtecte up=new UmsProtecte();
       UmsProtectedobject upobj=up.loadUmsProtecteNaturalname(naturalname);
       String type=upobj.getObjecttype();
       if(StringUtils.isNotEmpty(type)){
    	   if(type.equals("wg")){
    		   type="1";
    	   }
    	   if(type.equals("wy")){
    		   type="2";
    	   }
    	   if(type.equals("sjl")){
    		   type="3";
    	   }if(type.equals("wc")){
    		   type="4";
    	   }
	       }else{
	    	     type="0"; 
	       }
		return type;
	}
	
	//某一级别上是否存在两个启用
	public String judgeavailable(String naturalname){
	       List list=null;
		   UmsProtecte up=new UmsProtecte();
	       UmsProtectedobject upobj=up.loadUmsProtecteNaturalname(naturalname);
	       String sqlStr= 
	    	   "SELECT * FROM netone.ums_protectedobject  WHERE PARENTDIR='"+ upobj.getId()+"'";
	       list = DbTools.queryData(sqlStr);
	       for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			   if(map.get("ACTIVE").equals("1")){
				   return "1";
			   }
		    }
		return "0";
	}
	
	public static void main(String[] args) {
		Resourcejudge rj=new Resourcejudge();
		System.out.println(rj.judgeavailable("PHONE.PHONE"));
	}
}
