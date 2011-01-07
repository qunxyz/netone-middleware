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

		<title>loadResourceById</title>


	</head>

	<body>
	
	<h3>根据资源的id来装载资源</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:loadResourceById/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			id：资源ID<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
		测试使用的资源的id为402882831d52dc11011d56ed1b490059<br>
   
		<br>
		<hr>
		<resource:loadResourceById dataname="upo"
			id="402882831d52dc11011d56ed1b490059"></resource:loadResourceById>
		资源的naturalname为
		<c:out value="${upo.naturalname }"></c:out>
		<br>
		资源的创建日期为：
		<c:out value="${upo.created }"></c:out>
		<br>
	</body>
</html>