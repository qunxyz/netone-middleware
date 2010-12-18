<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@taglib uri="newland:security:permission:1.0" prefix="p"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<p:login></p:login>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>index</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="css/css.css">
	<script type="text/javascript" src="include/js/util.js"></script>
	<script type="text/javascript" src="include/js/permission.js"></script>
  </head>
  
  <body>
    This is my JSP page. <br> 
    <p:button application="统一权限系统" resource="用户管理" action="7" name="test"  value="确定" cssclass="butt" style="width: 150px" />	
    <input type="button" value="test" onclick="getCookie()">
  </body>
</html>
