<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>װ������ʵ�� example</title>
	</head>
	<body>
	<h3>װ������ʵ��</h3>
		<b>��ǩ����</b><br>
			<c:out value="<wf:loadRuntime/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			runtimeid:��������ʵ��ID,��������String<br>
			outVar:���ؽ��,��������TWfRuntime<br>
		<b>��������</b><br>
		<br>
		<hr>
		<wf:loadRuntime runtimeid="402882e61db25d96011db9ced7e30a93"
			outVar="runtime"></wf:loadRuntime>
		<table width="753" border="1">
			<tr>
				<td width="130">
					����ʵ��ID
				</td>
				<td width="138">
					��������
				</td>
				<td width="171">
					����ʱ��
				</td>
				<td width="158">
					����ʱ��
				</td>
				<td width="122">
					״̬
				</td>
			</tr>
			<tr>
				<td>
					${runtime.runtimeid}
				</td>
				<td>
					${runtime.kind}
				</td>
				<td>
					${runtime.starttime}
				</td>
				<td>
					${runtime.endtime}
				</td>
				<td>
					${runtime.statusnow}
				</td>
			</tr>
		</table>
	</body>
</html>