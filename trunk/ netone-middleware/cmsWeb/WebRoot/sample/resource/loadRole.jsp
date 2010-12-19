
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

		<title>loadRole</title>


	</head>

	<body>
	
	
	
	<h3>根据角色ID，装载角色</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:loadRole/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			roleId：角色ID<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
	测试的角色ID是48<br>
		<hr>
		
		<resource:loadRole dataname="role" roleId="48"></resource:loadRole>
		角色的信息：
		<br>
		<hr>
		角色name：
		<c:out value="${role.name }"></c:out>
		<br>
		角色naturalname：
		<c:out value="${role.naturalname }"></c:out>

	</body>
</html>

