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

		<title>loadResourceById</title>


	</head>

	<body>
	
	<h3>������Դ��id��װ����Դ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:loadResourceById/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			id����ԴID<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
		����ʹ�õ���Դ��idΪ402882831d52dc11011d56ed1b490059<br>
   
		<br>
		<hr>
		<resource:loadResourceById dataname="upo"
			id="402882831d52dc11011d56ed1b490059"></resource:loadResourceById>
		��Դ��naturalnameΪ
		<c:out value="${upo.naturalname }"></c:out>
		<br>
		��Դ�Ĵ�������Ϊ��
		<c:out value="${upo.created }"></c:out>
		<br>
	</body>
</html>