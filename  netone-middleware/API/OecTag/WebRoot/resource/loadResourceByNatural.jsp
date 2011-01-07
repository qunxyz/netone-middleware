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
		<title>loadResourceByNatural</title>
	</head>
	<body>
	<h3>根据资源的naturalname来装载资源</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:loadResourceByNatural/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			naturalname：资源naturalname<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
	测试使用的资源的naturalname为PMS.PMS.INSTANCES.B.RS.PERMISSION.USERMAN.USERMAN<br>
		<hr>
		
		<resource:loadResourceByNatural naturalname="PMS.PMS.INSTANCES.B.RS.PERMISSION.USERMAN.USERMAN" dataname="upo"></resource:loadResourceByNatural>
		资源的ID为<c:out value="${upo.id }"></c:out><br>
		资源的创建日期为：<c:out value="${upo.created }"></c:out><br>
	</body>
</html>