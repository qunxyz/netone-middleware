
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

		<title>subResource</title>


	</head>

	<body>
	<h3>������Դ�ĸ��ڵ�ID������е��ӽڵ�</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:subResource/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			parentId�����ڵ��ID<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
	���Եĸ��ڵ�ID��402882831d52dc11011d56ed1bf5005b<br>
		�ӽڵ���:<br>
		<hr>
		
		
		<resource:subResource dataname="upos" parentId="402882831d52dc11011d56ed1bf5005b"></resource:subResource>
		<c:forEach items="${upos }" var="upo">
		<c:out value="${upo.name }"></c:out><br>
			
		</c:forEach>

	</body>
</html>

