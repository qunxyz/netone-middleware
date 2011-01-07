
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
	
	<h3>获得某个隶属于下的一个用户具有的角色</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:getUserRoles/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			code：用户隶属于<br>
			roleId：角色ID<br>
			username:用户名<br>
		<b>测试例子</b><br>
		获得一个用户的角色，该用户的隶属于为0000，用户名为chenf<br>
		注意：如果code不填写，默认获得当前登录用户的code<br>
		<hr>
		<resource:getUserRoles dataname="roles" code="0000" username="chenf"></resource:getUserRoles>
		用户具有的角色有<br>
		<c:forEach items="${roles }" var="role">
		<c:out value="${role.name }"></c:out><br>
			
		</c:forEach>

	</body>
</html>

