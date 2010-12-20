<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="ISO-8859-1"%>
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
	<dy:dydata dataname="mydata" fatherlsh="1" formcode="fb157bf18f6911dd9b96f377d312a5aa_"/>
	<dy:dydesign dataname="mydatads" formcode="fb157bf18f6911dd9b96f377d312a5aa_"/>
			<c:forEach items="${mydatads}" var="column">
		    ${column.columname}<br>
		</c:forEach>
	<c:forEach items="${mydata}" var="data">
		${data.column3}<br>
	</c:forEach>
	</body>
</html>