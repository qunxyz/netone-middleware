<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>����������ݵ�ID���������ݶ���ʵ�� example</title>
	</head>
	<body>
	<h3>����������ݵ�ID���������ݶ���ʵ��</h3>
		<b>��ǩ����</b><br>
			<c:out value="<wf:loadRelevantvar/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			varcode��������ݵ�ID,��������String<br>
			outVar:���ؽ��,��������TWfRelevantvar<br>
		<b>��������</b><br>
		<br>
		<hr>
		
		<wf:loadRelevantvar varcode="402882e61dbe28c1011dbedf16340082"
			outVar="relevantvar"></wf:loadRelevantvar>
		<table width="753" border="1">
			<tr>
				<td width="130">
					�������ID
				</td>
				<td width="150">
					�����������
				</td>
				<td width="150">
					����������
				</td>
				<td width="158">
					������
				</td>
				<td width="180">
					�������ֵ
				</td>
				<td width="180">
					����ID
				</td>
				<td width="130">
					����ʵ��ID
				</td>

			</tr>

			<tr>
				<td>
					${relevantvar.varcode}
				</td>
				<td>
					${relevantvar.datafieldid}
				</td>
				<td>
					${relevantvar.nameExt}
				</td>
				<td>
					${relevantvar.participant}
				</td>
				<td>
					${relevantvar.valuenow}
				</td>
				<td>
					${relevantvar.processid}
				</td>
				<td>
					${relevantvar.runtimeid}
				</td>
			</tr>
		</table>
	</body>
</html>
