<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/bi" prefix="bi"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
	</head>

	<body>
		<bi:tableview dataname="data" begin="0" end="100"
			ananame="${param.name}" />

		<table width="100%" class='table' cellspacing="0" border="1" cellpadding="2" bordercolordark="#999999" bordercolorlight="#FFFFFF">
			<tr class='tdheadline'>
				<!-- 打印表格中维度列名-->
				<c:forEach items="${data.dimName}" var="dimname">
					<td>
						${dimname}
					</td>
				</c:forEach>

				<!-- 打印表格中指标列名-->
				<c:forEach items="${data.targetName}" var="tgname">
					<td>
						${tgname}
					</td>
				</c:forEach>
			</tr>
			<!-- 打印表格数据-->
			<c:forEach items="${data.dimValue}" var="dimv" varStatus="vs">
				<tr>
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
