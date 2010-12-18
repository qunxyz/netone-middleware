<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@taglib uri="newland:security:permission:1.0" prefix="p"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

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
    
    
	 	<p:noPermission application="统一权限系统" action="用户管理" resource="1">
	 		<c:set var="readonly" value="readonly='readonly'" scope="request"/>
	 	</p:noPermission>
 	
 	
 	<input type="text" >
 	
 	<p:noPermission application="统一权限系统" action="用户管理" resource="1">
 		
 	</p:noPermission>
 	
 	<p:button resource="" application="" action="" style="" cssclass=""></p:button>
 	
    <input type="button" value="test" onclick="checkPagePermission('统一权限系统','用户管理','7')">
  </body>
</html>
