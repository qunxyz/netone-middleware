<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʾ���еĻ�ڵ� example</title>
	</head>
	<body>
	<h3>��ʾ���еĻ�ڵ�</h3>
		<b>��ǩ����</b><br>
			<c:out value="<wf:listWorklist/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			runtimeId:����ʵ��ID,��������String<br>
			outVar:���ؽ��,��������TWfWorklist<br>
		<b>��������</b><br>
		<br>
		<hr>
		<wf:listWorklist runtimeId="402882e61dbe28c1011dbedb44aa0076"
			outVar="workListSet"></wf:listWorklist>
		<table width="753" border="1">
			<tr>
				<td width="130">
					����ʵ��ID
				</td>
				<td width="138">
					����������
				</td>
				<td width="171">
					���������
				</td>
				<td width="158">
					������
				</td>
				<td width="122">
					����
				</td>
			</tr>
			<c:forEach items="${workListSet}" var="workList">
				<tr>
					<td>
						${workList.runtimeid}
					</td>
					<td>
						${workList.nameExt}
					</td>
					<td>
						${workList.workcode}
					</td>
					<td>
						${workList.participant}
					</td>
					<td>
						${workList.types}
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>