
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
	<h3>������Դ��naturalname���ж���Դ�Ƿ����</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:checkExist/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			naturalname����Դ��naturalname<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
		�ж�naturalnameΪPMS.PMS.INSTANCES.ORACLE����Դ�Ƿ����
		<hr>
		
		�жϽ����<br>
		<resource:checkExist naturalname="PMS.PMS.INSTANCES.ORACLE" dataname="result"></resource:checkExist>
		<c:if test="${result =='y' }" var="exist" />
	${exist }


	</body>
</html>

