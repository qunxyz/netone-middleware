
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
	
	<h3>���ݲ�ѯ��������ѯ��ɫ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:fetctRole/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			condition����ѯ����<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
		��ѯ��ɫ�������а���a�����н�ɫ<br>
	 	 ����ʹ�õĲ�ѯ�����ǣ�and naturalname like '%a%'
   <br>
   <hr>
		<c:set var="codition" value="and naturalname like '%a%'"></c:set>
		<resource:fetctRole dataname="list" condition="${codition }"></resource:fetctRole>
		
		<c:forEach items="${list }" var="role">
		��ɫID��<c:out value="${role.id }"></c:out><br>
		��ɫ����:<c:out value="${role.name }"></c:out><br>
		<hr>
		</c:forEach>

	</body>
</html>

