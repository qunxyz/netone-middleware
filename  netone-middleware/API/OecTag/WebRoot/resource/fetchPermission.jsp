
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
	
	<h3>���һ����ɫ��������Ȩ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:fetchExtendedRole/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			roleId����ɫID
			dataname:���ؽ��<br>
		<b>��������</b><br>
		���Խ�ɫidΪ48�Ľ�ɫ��������Ȩ<br>
		<br>
		<hr>
		<resource:fetchPermission dataname="rolePermissions" roleId="48"></resource:fetchPermission>
		<c:forEach items="${rolePermissions }" var="rolePermission">
		����Դ--><c:out value="${rolePermission.comments }"></c:out>�ķ���Ȩ����
			<c:out value="${rolePermission.operationid }"></c:out><br>
		</c:forEach>

	</body>
</html>

