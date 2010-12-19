<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'mail_index.jsp' starting page</title>
   

  </head>
  
  <body>
    <h4><a href="<%=basePath%>sample/mail/getMessCount.jsp" target="main">Î´¶ÁÓÊ¼şÊı</a></h4>
  </body>
</html>
