
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
	
	
	
	<h3>���ݽ�ɫID��װ�ؽ�ɫ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:loadRole/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			roleId����ɫID<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
	���ԵĽ�ɫID��48<br>
		<hr>
		
		<resource:loadRole dataname="role" roleId="48"></resource:loadRole>
		��ɫ����Ϣ��
		<br>
		<hr>
		��ɫname��
		<c:out value="${role.name }"></c:out>
		<br>
		��ɫnaturalname��
		<c:out value="${role.naturalname }"></c:out>

	</body>
</html>

