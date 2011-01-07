<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=GBK">
		<title>Insert title here</title>
	</head>
	<body>
	<h3>获得动态表单中的数据</h3>
		<b>标签名称</b><br>
			
		<b>标签参数</b><br>
			dataname：查询返回的表单数据,数据类型List<br>
			fatherlsh:父表单流水号<br>
			formcode：表单的fromcode<br>
		<b>测试例子</b><br>
		显示表单的formcode为731d715eb77c11ddade82b3142add25d_的表单中的数据<br>
		<hr>
	<dy:dydata dataname="mydata" fatherlsh="1" formcode="b6f31936c52911df841ee9183550a94f_" />
	<table border='1'>
	<tr bgcolor='#99999'><td>区域</td><td>时间</td><td>姓名</td></tr>
	<c:forEach items="${mydata}" var="data"> 
		<tr><td>${data.belongx}&nbsp;</td><td>${data.timex}&nbsp;</td><td>${data.column3}&nbsp;</td></tr>
	</c:forEach>
	</table>
	<br><br>
	

	</body>
</html>