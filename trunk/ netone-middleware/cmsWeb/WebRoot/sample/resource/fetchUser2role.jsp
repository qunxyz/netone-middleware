
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
		<title>fetchUser2role</title>
	</head>
	<body>
	
	<h3>获得所有符合条件的用户角色关系</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:fetchUser2role/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			code：用户隶属于<br>
			condition：查询条件<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
		测试用户名称中包含a的所有用户的所有角色<br>
		注意：如果code不填写，默认获得当前登录用户的code<br>
	  测试使用的查询条件是：and naturalname = 'a'
   <br>
   <hr>
		<c:set var="codition" value="and userid like '%a%'"></c:set>
		<resource:fetchUser2role dataname="list" code="0000" condition="${codition }"></resource:fetchUser2role>
		
		<c:forEach items="${list }" var="user2role">
		用户ID：<c:out value="${user2role.userid }"></c:out><br>
		角色ID:<c:out value="${user2role.roleid }"></c:out><br>
		<hr>
		</c:forEach>

	</body>
</html>

