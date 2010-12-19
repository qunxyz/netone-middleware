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
	<h3>使用服务器上的bean</h3>
		<b>标签名称</b><br>
			<c:out value="<portal:beanTag/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			beanname：服务器上bean的名称<br>
			varname：返回值<br>
			可以动态设置值<br>
		<b>测试例子</b><br>
		测试服务器上的bean，bean的名称为BUSSBEAN.BUSSBEAN.BEANSAMPLE1<br>
		<hr>
		
		<portal:beanTag beanname="BUSSBEAN.BUSSBEAN.BEANSAMPLE1" varname="aa"
			age="50" name="mike" />

		${aa.values}
	</body>
</html>