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

		<title>活动流程实例列表</title>

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
				<td width="200" align="center">
					流程活动ID
				</td>
				<td width="200" align="center" nowrap="nowrap">
					活动节点实例ID
				</td>
				<td width="150" align="center" nowrap="nowrap">
					节点名称
				</td>
				<td>
					节点类型
				</td>
				<td width="200" align="center" nowrap="nowrap">
					流程开始时间
				</td>
				<td width="300" align="center" nowrap="nowrap">
					流程结束时间
				</td>
				<td width="150" align="center" nowrap="nowrap">
					流程状态
				</td>
			</tr>
			<td>
				<c:forEach items="${WorkListinfo}" var="dataInfo">
					<tr>
						<td align="center" nowrap="nowrap">
							${dataInfo.activityid}
						</td>
						<td align="center" nowrap="nowrap">
							${dataInfo.workcode}
						</td>
						<td align="center" nowrap="nowrap">
							${dataInfo.nameExt}
						</td>
						<td align="center" nowrap="nowrap">
							${dataInfo.types}
						</td>
						<td align="center" nowrap="nowrap">
							${dataInfo.starttime}
						</td>
						<td align="center">
							${dataInfo.donetime}&nbsp
						</td>
						<td align="center">
							${dataInfo.executestatus}
						</td>
					</tr>
				</c:forEach>
			</td>
		</table>
	</body>
</html>
