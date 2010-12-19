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

		<title>validationUser</title>


	</head>
	
	<body>
	
		<h3>登陆验证</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:validationUser/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			code:用户隶属于<br>
			name：用户名<br>
			pass:密码<br>
			dataname:返回结果<br>	
	
		<br>测试例子1<br>
		用户名：adminx<br>
		密码：123<br>
		隶属于：0000<br>
		<resource:validationUser dataname="clerk" name="adminx" pass="123" code="0000"></resource:validationUser>
		验证结果是:
		<b><c:out value="${clerk.operationinfo }"></c:out></b>
		<br>
		<br>测试例子2<br>
		用户名：adminx<br>
		密码：admin@1234<br>
		隶属于：0000<br>
		<resource:validationUser dataname="clerk" name="adminx" pass="admin@1234" code="0000"></resource:validationUser>
		验证结果是:
		<b><c:out value="${clerk.operationinfo }"></c:out></b>
		<br>
		<br>测试例子3<br>
		用户名：adminx<br>
		密码：admin@1234<br>
		隶属于：9527<br>
		<resource:validationUser dataname="clerk" name="adminx" pass="admin@1234" code="9527"></resource:validationUser>
		验证结果是:
		<b><c:out value="${clerk.operationinfo }"></c:out></b>
	</body>
</html>