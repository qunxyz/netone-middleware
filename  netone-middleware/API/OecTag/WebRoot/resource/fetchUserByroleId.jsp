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
		<title>FetchUserByroleId</title>
	</head>
	<body>
	
	
	<h3>ͨ����ɫID����øý�ɫ�������û�</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:fetchUserByroleId/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			code���û�������
			roleId����ɫID<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
		��ɫIDΪ48���������ӵ�иý�ɫ���û�<br>
		ע�⣺���code����д��Ĭ�ϻ�õ�ǰ��¼�û���code<br>
   <hr>
		ӵ�иý�ɫ�������û��У�
		<hr>
		<resource:fetchUserByroleId dataname="clerkList" roleId="48" code=""></resource:fetchUserByroleId>
		<c:forEach items="${clerkList }" var="clerk">
			�û�����<c:out value="${clerk.description }"></c:out>
			<br>
		</c:forEach>
	</body>
</html>