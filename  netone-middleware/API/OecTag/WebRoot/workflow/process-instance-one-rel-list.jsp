<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>���һ����������ʵ����������ݶ��� example</title>

	</head>
	<body>
	<h3>���һ����������ʵ����������ݶ���</h3>
		<b>��ǩ����</b><br>
			<c:out value="<wf:listRelevartvarInstance/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			runtimeId:����ʵ��ID,��������String<br>
			dataid:���ݶ���ID,��������String<br>
			outVar:���ؽ��,��������TWfRelevantvar<br>
		<b>��������</b><br>
		<br>
		<hr>
		<wf:listRelevartvarInstance
			runtimeId="402882e61dbe28c1011dbedb44aa0076" dataid="rev1" outVar="relVar"></wf:listRelevartvarInstance>


		<table width="753" border="1">
			<tr>
				<td width="130">
					����ʵ��ID
				</td>
				<td width="138">
					������ݱ���
				</td>
				<td width="158">
					�������ֵ
				</td>
				<td width="122">
					�����������
				</td>
			</tr>
			
				<tr>
					<td>
						${relVar.runtimeid}
					</td>
					<td>
						${relVar.varcode}
					</td>
					<td>
						${relVar.valuenow}
					</td>
					<td>
						${relVar.datafieldid}
					</td>
				</tr>
			
		</table>
	</body>
</html>