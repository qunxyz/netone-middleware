<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��̳</title>
   

  </head>
  
  <body>
    <h4><a href="<%=basePath%>forum/hottopic.jsp" target="main">�����������</a></h4>
    <h4><a href="<%=basePath%>forum/recenttopic.jsp" target="main">�����������</a></h4>
    <h4><a href="<%=basePath%>forum/view_forum_model.jsp" target="main">�鿴���</a></h4>
    <h4><a href="<%=basePath%>forum/view_forum_post.jsp" target="main">�鿴��ذ������</a></h4>
  </body>
</html>
