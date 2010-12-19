
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

		<title>FetchExtendedRole</title>


	</head>

	<body>
	<h3>根据ID查询某个角色的父角色</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:fetchExtendedRole/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			roleId：角色ID
			dataname:返回结果<br>
		<b>测试例子</b><br>
		获得roleId为39的角色的父角色，因为是单重继承，所以父角色只有一个，如果没有父角色则为空<br>
	
	
		<resource:fetchExtendedRole dataname="umsRole" roleId="39"></resource:fetchExtendedRole>

		所要查询的角色是<br>
		<hr>
		角色名称：<c:out value="${umsRole.name }"></c:out><br>
		角色ID:<c:out value="${umsRole.id }"></c:out>

	</body>
</html>

