
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
	<h3>根据资源的父节点ID获得所有的子节点</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:subResource/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			parentId：父节点的ID<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
	测试的父节点ID是402882831d52dc11011d56ed1bf5005b<br>
		子节点有:<br>
		<hr>
		
		
		<resource:subResource dataname="upos" parentId="402882831d52dc11011d56ed1bf5005b"></resource:subResource>
		<c:forEach items="${upos }" var="upo">
		<c:out value="${upo.name }"></c:out><br>
			
		</c:forEach>

	</body>
</html>

