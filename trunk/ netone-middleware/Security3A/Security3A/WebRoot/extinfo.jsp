<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="oe.security3a.client.rmi.ResourceRmi"%>
<%@page import="oe.rmi.client.RmiEntry"%>
<%@page import="oe.frame.web.WebCache"%>
<%@page import="oe.security3a.seucore.obj.db.UmsProtectedobject"%>
<%@page import="java.util.Date"%>

<%

String _KEY="FCK.FCK.COMMET.NETONE";
if(!WebCache.containCache(_KEY)){
    ResourceRmi rs=(ResourceRmi)RmiEntry.iv("resource");
    UmsProtectedobject upo=null;
    try{
      upo=rs.loadResourceByNatural(_KEY);
    }catch(Exception e){
    }
   
    if(upo!=null){
    	String htmlinfo=upo.getExtendattribute();
    	WebCache.setCache(_KEY,htmlinfo,new Date(System.currentTimeMillis()+3600000));
    	
    }else{
    	out.print("About Netone Description resource FCK.FCK.COMMET.NETONE is not define!");
    	WebCache.setCache(_KEY,"",new Date(System.currentTimeMillis()+3600000));
    }
}
out.print(WebCache.getCache(_KEY));

%>

