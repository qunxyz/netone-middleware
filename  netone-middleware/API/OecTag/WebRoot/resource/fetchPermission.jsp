
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

		<title>check Exist</title>


	</head>

	<body>
	
	<h3>获得一个角色的所有授权</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:fetchExtendedRole/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			roleId：角色ID
			dataname:返回结果<br>
		<b>测试例子</b><br>
		测试角色id为48的角色的所有授权<br>
		<br>
		<hr>
		<resource:fetchPermission dataname="rolePermissions" roleId="48"></resource:fetchPermission>
		<c:forEach items="${rolePermissions }" var="rolePermission">
		对资源--><c:out value="${rolePermission.comments }"></c:out>的访问权限是
			<c:out value="${rolePermission.operationid }"></c:out><br>
		</c:forEach>

	</body>
</html>

