<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>����ָ�����̵��������б�ҳ���ַ example</title>
	</head>
	<body>
		<h3>
			����ָ�����̵��������б�ҳ���ַ
		</h3>
		<b>��ǩ����</b>
		<br>
		<c:out value="<wf:runtimeSubActUrlTag/>" escapeXml="true"></c:out>
		<br>
		<b>��ǩ����</b>
		<br>
		runtimeId:����ʵ��ID,��������String
		<br>
		<b>��������</b>
		<br>
		<br>
		<hr>

		<a
			href="<wf:runtimeSubActUrlTag runtimeid="402882e61db25d96011db9ced7e30a93"/>"
			target="_blank">�鿴����</a>

	</body>
</html>