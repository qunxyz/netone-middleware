<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>���ָ�����̵����е�������ݶ���</title>
	</head>
	<body>
	<h3>���ָ�����̵����е�������ݶ���</h3>
		<b>��ǩ����</b><br>
			<c:out value="<wf:listRelevantvar/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			processid:����ID,��������String<br>
			outVar:���ؽ��,��������List<br>
		<b>��������</b><br>
		<br>
		<hr>
		<wf:listRelevantvar processid="BUSSWF.BUSSWF.ak"
			outVar="dataVarSet"></wf:listRelevantvar>
		<table width="753" border="1">
			<tr>
				<td width="130">
					�����������
				</td>
				<td width="138">
					�����������
				</td>
				<td width="158">
					������ݳ�ʼֵ
				</td>
				<td width="122">
					������ݳ���
				</td>
			</tr>
			<c:forEach items="${dataVarSet}" var="dataVar">
				<tr>
					<td>
						${dataVar.name}
					</td>
					<td>
						${dataVar.dataType.type}
					</td>
					<td>
						${dataVar.initialValue}
					</td>
					<td>
						${dataVar.length}
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>