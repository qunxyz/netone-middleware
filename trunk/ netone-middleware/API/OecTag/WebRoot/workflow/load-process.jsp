<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>װ������ example</title>
	</head>
	<body>
	<h3>װ������</h3>
		<b>��ǩ����</b><br>
			<c:out value="<wf:loadProcess/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			processid�����̾�̬ID,��������String<br>
			outVar:���ؽ��,��������WorkflowProcess<br>
		<b>��������</b><br>
		<br>
		<hr>
		<wf:loadProcess processid="BUSSWF.BUSSWF.MYFLOW" outVar="process"></wf:loadProcess>
		<table width="753" border="1">
			<tr>
				<td width="130">
					ID
				</td>
				<td width="138">
					����
				</td>
				<td width="171">
					����
				</td>
			</tr>

			<tr>
				<td>
					${process.id}
				</td>
				<td>
					${process.name}
				</td>
				<td>
					${process.description}
				</td>
			</tr>

		</table>
	</body>
</html>