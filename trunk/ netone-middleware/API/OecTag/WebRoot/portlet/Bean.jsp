<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
	</head>
	<body>
	<h3>ʹ�÷������ϵ�bean</h3>
		<b>��ǩ����</b><br>
			<c:out value="<portal:beanTag/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			beanname����������bean������<br>
			varname������ֵ<br>
			���Զ�̬����ֵ<br>
		<b>��������</b><br>
		���Է������ϵ�bean��bean������ΪBUSSBEAN.BUSSBEAN.BEANSAMPLE1<br>
		<hr>
		
		<portal:beanTag beanname="BUSSBEAN.BUSSBEAN.BEANSAMPLE1" varname="aa"
			age="50" name="mike" />

		${aa.values}
	</body>
</html>