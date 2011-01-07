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
    <h4><a href="<%=basePath%>dy/DyTag.jsp" target="main">获得动态表单数据</a></h4>
     <h4><a href="<%=basePath%>dy/DyDesignTag.jsp" target="main">获得动态表单字段</a></h4>
    <h4><a href="<%=basePath%>dy/DyTagTurnpage.jsp" target="main">动态表单(分页)</a></h4>
  </body>
</html>
