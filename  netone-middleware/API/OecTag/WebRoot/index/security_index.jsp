<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'security_index.jsp' starting page</title>
    

  </head>
  
  <body>
   <h4><a href="<%=basePath%>security/TestA.jsp" target="_blank">��ȫ��ǩ</a></h4>
    <h4><a href="<%=basePath%>security/TestB.jsp" target="main">Ȩ�޼��</a></h4>
     <h4><a href="<%=basePath%>security/TestC.jsp" target="main">Ȩ�޼��1</a></h4>
  </body>
</html>
