
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

		<title>getUserRoles</title>


	</head>

	<body>
	
	<h3>���ĳ���������µ�һ���û����еĽ�ɫ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:getUserRoles/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			code���û�������<br>
			roleId����ɫID<br>
			username:�û���<br>
		<b>��������</b><br>
		���һ���û��Ľ�ɫ�����û���������Ϊ0000���û���Ϊchenf<br>
		ע�⣺���code����д��Ĭ�ϻ�õ�ǰ��¼�û���code<br>
		<hr>
		<resource:getUserRoles dataname="roles" code="0000" username="chenf"></resource:getUserRoles>
		�û����еĽ�ɫ��<br>
		<c:forEach items="${roles }" var="role">
		<c:out value="${role.name }"></c:out><br>
			
		</c:forEach>

	</body>
</html>

