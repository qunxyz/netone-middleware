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
		<h3>�������������úͻ�ȡ</h3>
		�������������RMI�������С�<br>
		<b>��ǩ����</b><br>
			<c:out value="<portal:envset/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			envkey��Ҫ���õĻ���������key<br>
			value��Ҫ���õĻ���������value<br>
			----------------------<br>
		<b>��ǩ����</b><br>
		<c:out value="<portal:envget/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			envkey��Ҫ��ȡ�Ļ���������key<br>
		<b>��������</b><br>
		��RMI����������һ����������mikeee,ֵΪ1.����ȡ�ñ���ֵ<br>
		<hr>
	<portal:envset envkey="mikeee" value="1"/>
	
	<portal:envget envkey="mikeee"/>
	</body>
</html>