<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/resource" prefix="resource"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>FetchUserByroleId</title>
	</head>
	<body>
	
	
	<h3>通过角色ID，获得该角色的所有用户</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:fetchUserByroleId/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			code：用户隶属于
			roleId：角色ID<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
		角色ID为48，获得所有拥有该角色的用户<br>
		注意：如果code不填写，默认获得当前登录用户的code<br>
   <hr>
		拥有该角色的所有用户有：
		<hr>
		<resource:fetchUserByroleId dataname="clerkList" roleId="48" code=""></resource:fetchUserByroleId>
		<c:forEach items="${clerkList }" var="clerk">
			用户名：<c:out value="${clerk.description }"></c:out>
			<br>
		</c:forEach>
	</body>
</html>