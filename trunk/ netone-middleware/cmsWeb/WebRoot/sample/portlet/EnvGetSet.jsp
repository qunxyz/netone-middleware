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
		<h3>环境变量的设置和获取</h3>
		环境变量存放在RMI服务器中。<br>
		<b>标签名称</b><br>
			<c:out value="<portal:envset/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			envkey：要设置的环境变量的key<br>
			value：要设置的环境变量的value<br>
			----------------------<br>
		<b>标签名称</b><br>
		<c:out value="<portal:envget/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			envkey：要获取的环境变量的key<br>
		<b>测试例子</b><br>
		在RMI服务器设置一个环境变量mikeee,值为1.并获取该变量值<br>
		<hr>
	<portal:envset envkey="mikeee" value="1"/>
	
	<portal:envget envkey="mikeee"/>
	</body>
</html>