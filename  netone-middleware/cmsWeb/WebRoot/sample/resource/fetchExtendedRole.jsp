
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

		<title>FetchExtendedRole</title>


	</head>

	<body>
	<h3>����ID��ѯĳ����ɫ�ĸ���ɫ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:fetchExtendedRole/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			roleId����ɫID
			dataname:���ؽ��<br>
		<b>��������</b><br>
		���roleIdΪ39�Ľ�ɫ�ĸ���ɫ����Ϊ�ǵ��ؼ̳У����Ը���ɫֻ��һ�������û�и���ɫ��Ϊ��<br>
	
	
		<resource:fetchExtendedRole dataname="umsRole" roleId="39"></resource:fetchExtendedRole>

		��Ҫ��ѯ�Ľ�ɫ��<br>
		<hr>
		��ɫ���ƣ�<c:out value="${umsRole.name }"></c:out><br>
		��ɫID:<c:out value="${umsRole.id }"></c:out>

	</body>
</html>

