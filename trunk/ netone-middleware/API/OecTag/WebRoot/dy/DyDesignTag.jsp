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
	<h3>��ö�̬���е��ֶ�</h3>
		<b>��ǩ����</b><br>
			<c:out value="<dy:dydesign/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			dataname����ѯ���ص����ݣ���������List<br>
			formcode������fromcode<br>
		<b>��������</b><br>
		��ʾ����formcodeΪ731d715eb77c11ddade82b3142add25d_�ı��е�����<br>
		<hr>
		
	Table column:<br>
	<dy:dydesign dataname="mydatads" formcode="b6f31936c52911df841ee9183550a94f_"/>
		<c:forEach items="${mydatads}" var="column">
		    ${column.columname},${column.columnid} <br>
		</c:forEach>
	</body>
</html>