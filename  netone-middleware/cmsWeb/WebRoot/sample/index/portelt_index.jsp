<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'portelt_index.jsp' starting page</title>
   
  </head>
  
  <body>
    <h4><a href="<%=basePath%>sample/portlet/Bean.jsp" target="main">bean</a></h4>
    <h4><a href="<%=basePath%>sample/portlet/note.jsp" target="main">��̬��+����ı�</a></h4>
    <h4><a href="<%=basePath%>sample/portlet/EnvGetSet.jsp" target="main">�������������úͻ�ȡ</a></h4>
  </body>
</html>
