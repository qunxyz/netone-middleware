<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="java.util.Iterator"/>

<%		
		String need=request.getParameter("need");
		
		if(need==null){
			need="10";
		}
		//构造动态菜单的html代码
		StringBuffer but = new StringBuffer();
		for(int i=Integer.parseInt(need);i>0;i--){
					but.append("<option value='" + i + "'>"
					+ i + "</option>");
		}
		out.print("<select id='selJs'>" + but + "</select>");
%>