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
	<h3>��ö�̬���е�����</h3>
		<b>��ǩ����</b><br>
			<c:out value="<dy:dydata/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			dataname����ѯ���صı�����,��������List<br>
			fatherlsh:������ˮ��<br>
			formcode������fromcode<br>
		<b>��������</b><br>
		��ʾ����formcodeΪ731d715eb77c11ddade82b3142add25d_�ı��е�����<br>
		<hr>
	<dy:dydata dataname="mydata" fatherlsh="1" formcode="731d715eb77c11ddade82b3142add25d_" />
	<table border='1'>
	<c:forEach items="${mydata}" var="data"> 
		<tr><td>${data.belongx}</td><td>${data.timex}</td><td>${data.column3}</td></tr>
	</c:forEach>
	</table>
	<br><br>
	
	Table column:<br>
	<dy:dydesign dataname="mydatads" formcode="731d715eb77c11ddade82b3142add25d_"/>
		<c:forEach items="${mydatads}" var="column">
		    ${column.columname},${column.columnid} <br>
		</c:forEach>
	</body>
</html>