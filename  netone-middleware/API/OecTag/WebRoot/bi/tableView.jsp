<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/bi" prefix="bi"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>�����ʾ</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link href="<%=path%>/include/css/css.css" rel="stylesheet"
			type="text/css">
	</head>

	<body>
		<h3>���ݷ�����������ƣ���ʾ�÷��������µı����е�����</h3>
		<b>��ǩ����</b><br>
		<c:out value="<bi:tableview/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			dataname�����ر����е����ݣ���������List<br>
			begin:���ݵ���ʼ�У���0��ʼ<br>
			end�����ݵ���ֹ��<br>
			ananame���������������<br>
		<b>��������</b><br>
		��ʾ������������ΪETL.ETL.POJTASKANA�ı���дӵ�0�е�100�е�����<br>
			<hr>
			
			
			
						
			
		<bi:tableview dataname="data" begin="0" end="100" ananame="ETL.ETL.NOTEBOOKSALE.SALETOTAL"/>
		
		<table width="100%" align="center" border="1">
			<tr align="center">
				<!-- ��ӡ�����ά������-->
				<c:forEach items="${data.dimName}" var="dimname">
					<td>
						${dimname}
					</td>
				</c:forEach>

				<!-- ��ӡ�����ָ������-->
				<c:forEach items="${data.targetName}" var="tgname">
					<td>
						${tgname}
					</td>
				</c:forEach>
			</tr>
			<!-- ��ӡ�������-->
			<c:forEach items="${data.dimValue}" var="dimv" varStatus="vs">
				<tr class="td-02" align="center">
					<c:forEach items="${dimv}" var="celldimv">
						<td>
							${celldimv}
						</td>
					</c:forEach>
					<c:forEach items="${data.targetValue[vs.index]}" var="celltgv">
						<td>
							${celltgv}
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
