<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʾ���е�����ʵ�� example</title>
	</head>
	<body>
	<h3>��ʾ���е�����ʵ��</h3>
		<b>��ǩ����</b><br>
			<c:out value="<wf:listInstance/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			processid:����ID,��������String<br>
			outVar:���ؽ��,��������List<br>
		<b>��������</b><br>
		<br>
		<hr>
		<wf:listInstance processid="BUSSWF.BUSSWF.b" outVar="processInstanceSet" ></wf:listInstance>
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
			<c:forEach items="${processInstanceSet}" var="runtime">
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
			</c:forEach>
		</table>
	</body>
</html>