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
		<title>loadResourceByNatural</title>
	</head>
	<body>
	<h3>������Դ��naturalname��װ����Դ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:loadResourceByNatural/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			naturalname����Դnaturalname<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
	����ʹ�õ���Դ��naturalnameΪPMS.PMS.INSTANCES.B.RS.PERMISSION.USERMAN.USERMAN<br>
		<hr>
		
		<resource:loadResourceByNatural naturalname="PMS.PMS.INSTANCES.B.RS.PERMISSION.USERMAN.USERMAN" dataname="upo"></resource:loadResourceByNatural>
		��Դ��IDΪ<c:out value="${upo.id }"></c:out><br>
		��Դ�Ĵ�������Ϊ��<c:out value="${upo.created }"></c:out><br>
	</body>
</html>