<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>数据变量列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<table width='100%' cellspacing="0" border="1" cellpadding="2"
			bordercolordark="#999999" bordercolorlight="#FFFFFF">
			<tr>
				<td align="center" nowrap="nowrap">
					变量ID
				</td>
				<td align="center" nowrap="nowrap">
					变量名称
				</td>
				<td align="center" nowrap="nowrap">
					变量值
				</td>
				<td align="center" nowrap="nowrap">
					变量实例ID
				</td>
			</tr>
			<c:forEach items="${relevantvarResltInfo}" var="datainfo">
				<tr>
					<td align="center" nowrap="nowrap">
						${datainfo.datafieldid}
					</td>
					<td align="center" nowrap="nowrap">
						${datainfo.nameExt}
					</td>
					<td align="center" nowrap="nowrap">
						${datainfo.valuenow}
					</td>
					<td align="center" nowrap="nowrap">
						${datainfo.varcode }
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
