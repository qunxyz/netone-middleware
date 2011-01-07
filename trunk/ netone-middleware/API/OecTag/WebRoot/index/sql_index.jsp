<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sql_index.jsp' starting page</title>
   
  </head>
  
  <body>
   <h4><a href="<%=basePath%>sql/sql.jsp" target="main">sql</a></h4>
   <h4><a href="<%=basePath%>sql/sql-network-portal.jsp" target="main">sql(·ÖÒ³)1</a></h4>
   <h4><a href="<%=basePath%>sql/sql-task-portal.jsp?paramName=" target="main">sql(·ÖÒ³)2</a></h4>
  </body>
</html>
