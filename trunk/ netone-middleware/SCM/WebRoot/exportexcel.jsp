<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
//response.setHeader("Content-Type","application/force-download");
String name = request.getParameter("name");
if (name!=null){
	name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
} else {
	name = "report";
}
response.setHeader("Content-Type","application/vnd.ms-excel");
long timemillis = System.currentTimeMillis();
response.setHeader("Content-Disposition","attachment;filename="+name+timemillis+".xls");
out.print(request.getParameter("exportContent")); 
%> 

