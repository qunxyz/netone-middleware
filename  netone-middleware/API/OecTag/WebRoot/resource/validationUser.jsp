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
	
		<h3>��½��֤</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:validationUser/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			code:�û�������<br>
			name���û���<br>
			pass:����<br>
			dataname:���ؽ��<br>	
	
		<br>��������1<br>
		�û�����adminx<br>
		���룺123<br>
		�����ڣ�0000<br>
		<resource:validationUser dataname="clerk" name="adminx" pass="123" code="0000"></resource:validationUser>
		��֤�����:
		<b><c:out value="${clerk.operationinfo }"></c:out></b>
		<br>
		<br>��������2<br>
		�û�����adminx<br>
		���룺admin@1234<br>
		�����ڣ�0000<br>
		<resource:validationUser dataname="clerk" name="adminx" pass="admin@1234" code="0000"></resource:validationUser>
		��֤�����:
		<b><c:out value="${clerk.operationinfo }"></c:out></b>
		<br>
		<br>��������3<br>
		�û�����adminx<br>
		���룺admin@1234<br>
		�����ڣ�9527<br>
		<resource:validationUser dataname="clerk" name="adminx" pass="admin@1234" code="9527"></resource:validationUser>
		��֤�����:
		<b><c:out value="${clerk.operationinfo }"></c:out></b>
	</body>
</html>