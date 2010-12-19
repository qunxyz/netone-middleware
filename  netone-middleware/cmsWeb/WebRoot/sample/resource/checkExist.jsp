
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

		<title>check Exist</title>


	</head>
		
	<body>
	<h3>根据资源的naturalname来判断资源是否存在</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:checkExist/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			naturalname：资源的naturalname<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
		判断naturalname为PMS.PMS.INSTANCES.ORACLE的资源是否存在
		<hr>
		
		判断结果是<br>
		<resource:checkExist naturalname="PMS.PMS.INSTANCES.ORACLE" dataname="result"></resource:checkExist>
		<c:if test="${result =='y' }" var="exist" />
	${exist }


	</body>
</html>

