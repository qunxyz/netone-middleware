
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/resource" prefix="resource"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>fetRole</title>
	</head>

	<body>
	
	<h3>根据查询条件，查询角色</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:fetctRole/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			condition：查询条件<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
		查询角色的名称中包含a的所有角色<br>
	 	 测试使用的查询条件是：and naturalname like '%a%'
   <br>
   <hr>
		<c:set var="codition" value="and naturalname like '%a%'"></c:set>
		<resource:fetctRole dataname="list" condition="${codition }"></resource:fetctRole>
		
		<c:forEach items="${list }" var="role">
		角色ID：<c:out value="${role.id }"></c:out><br>
		角色名称:<c:out value="${role.name }"></c:out><br>
		<hr>
		</c:forEach>

	</body>
</html>

