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
	<h3>���ݲ�ѯ��������÷�����������Ա</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:fetchClerk/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			condition����ѯ��Ա��������sql���<br>
			code���û�������
			dataname:���ؽ��<br>
		<b>��������</b><br>
		��ѯ������Ϊ0000������Ա�������а���c��������Ա��<br>
		ע�⣺��������ڲ���д����ôĬ��ȡ��ǰ��½���û��������ڡ�<br>
		
		<c:set var="condition" value="and name like '%c%'"></c:set>
		��ѯ������:<c:out value="${condition }"></c:out><br>
		<hr>
		<resource:fetchClerk dataname="clerkList" condition="${condition }" code="0000"></resource:fetchClerk>
		<c:forEach items="${clerkList }" var="clerk">
			<c:out value="${clerk.description }"></c:out>
			<br>
		</c:forEach>
	</body>
</html>