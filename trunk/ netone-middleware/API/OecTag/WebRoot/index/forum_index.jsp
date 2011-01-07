<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>论坛</title>
   

  </head>
  
  <body>
    <h4><a href="<%=basePath%>forum/hottopic.jsp" target="main">获得热门主题</a></h4>
    <h4><a href="<%=basePath%>forum/recenttopic.jsp" target="main">获得最新主题</a></h4>
    <h4><a href="<%=basePath%>forum/view_forum_model.jsp" target="main">查看版块</a></h4>
    <h4><a href="<%=basePath%>forum/view_forum_post.jsp" target="main">查看相关版块帖子</a></h4>
  </body>
</html>
