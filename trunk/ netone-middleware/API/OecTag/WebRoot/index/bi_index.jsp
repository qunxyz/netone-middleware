<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>BI��ǩ����</title>
  </head>
  
  <body>
  <h4><a href="<%=basePath%>bi/tableView.jsp" target="main">����</a></h4>
  <h4><a href="<%=basePath%>bi/graphView.jsp"  target="main">ͼ����ʾ</a></h4>
  <h4><a href="<%=basePath%>bi/graphana.jsp"  target="main">ͼ�����</a></h4>
  </body>
</html>
