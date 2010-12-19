<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>BI标签索引</title>
  </head>
  
  <body>
  <h4><a href="<%=basePath%>sample/bi/tableView.jsp" target="main">报表</a></h4>
  <h4><a href="<%=basePath%>sample/bi/graphView.jsp"  target="main">图表显示</a></h4>
  <h4><a href="<%=basePath%>sample/bi/graphana.jsp"  target="main">图表分析</a></h4>
  </body>
</html>
