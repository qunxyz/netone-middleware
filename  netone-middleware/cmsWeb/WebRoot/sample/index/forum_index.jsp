<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'dy_index.jsp' starting page</title>
   

  </head>
  
  <body>
    <h4><a href="<%=basePath%>sample/forum/hottopic.jsp" target="main">获得热门主题</a></h4>
  </body>
</html>
