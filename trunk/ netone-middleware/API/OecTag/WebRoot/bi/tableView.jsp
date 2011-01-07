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

		<title>表格显示</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link href="<%=path%>/include/css/css.css" rel="stylesheet"
			type="text/css">
	</head>

	<body>
		<h3>根据分析主题的名称，显示该分析主题下的报表中的数据</h3>
		<b>标签名称</b><br>
		<c:out value="<bi:tableview/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			dataname：返回报表中的数据，数据类型List<br>
			begin:数据的起始行，从0开始<br>
			end：数据的终止行<br>
			ananame：分析主题的名称<br>
		<b>测试例子</b><br>
		显示分析主题名称为ETL.ETL.POJTASKANA的表格中从第0行到100行的数据<br>
			<hr>
			
			
			
						
			
		<bi:tableview dataname="data" begin="0" end="100" ananame="ETL.ETL.NOTEBOOKSALE.SALETOTAL"/>
		
		<table width="100%" align="center" border="1">
			<tr align="center">
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
