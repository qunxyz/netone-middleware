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

		<title>Fetch Clerk</title>


	</head>

	<body>
	<h3>根据查询条件，获得符合条件的人员</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:fetchClerk/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			condition：查询人员的条件，sql语句<br>
			code：用户隶属于
			dataname:返回结果<br>
		<b>测试例子</b><br>
		查询隶属于为0000，且人员的名字中包含c的所有人员。<br>
		注意：如果隶属于不填写，那么默认取当前登陆的用户的隶属于。<br>
		
		<c:set var="condition" value="and name like '%c%'"></c:set>
		查询条件是:<c:out value="${condition }"></c:out><br>
		<hr>
		<resource:fetchClerk dataname="clerkList" condition="${condition }" code="0000"></resource:fetchClerk>
		<c:forEach items="${clerkList }" var="clerk">
			<c:out value="${clerk.description }"></c:out>
			<br>
		</c:forEach>
	</body>
</html>