
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
		<title>fetchUser2role</title>
	</head>
	<body>
	
	<h3>������з����������û���ɫ��ϵ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:fetchUser2role/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			code���û�������<br>
			condition����ѯ����<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
		�����û������а���a�������û������н�ɫ<br>
		ע�⣺���code����д��Ĭ�ϻ�õ�ǰ��¼�û���code<br>
	  ����ʹ�õĲ�ѯ�����ǣ�and naturalname = 'a'
   <br>
   <hr>
		<c:set var="codition" value="and userid like '%a%'"></c:set>
		<resource:fetchUser2role dataname="list" code="0000" condition="${codition }"></resource:fetchUser2role>
		
		<c:forEach items="${list }" var="user2role">
		�û�ID��<c:out value="${user2role.userid }"></c:out><br>
		��ɫID:<c:out value="${user2role.roleid }"></c:out><br>
		<hr>
		</c:forEach>

	</body>
</html>

