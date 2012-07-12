<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

		<title>My JSP 'treeLevelrel.jsp' starting page</title>

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
		<form action="">
			一级码显示
			<input type='checkbox'>
			<input type='text' value=''>
			<input type='button' value='查询' onClick=''>
			<table>
				<tr>
					<td>
						序号
					</td>
					<td>
						一级码
					</td>
					<td>
						二级码
					</td>
					<td>
						三级码
					</td>
					<td>
						经销商
					</td>
					<td>
						分销商
					</td>
					<td>
						物料名
					</td>
					<td>
						生产日期
					</td>
					<td>
						出库日期
					</td>
					<td>
						经销商出库日期
					</td>
					<td>
						一级码状态
					</td>
				</tr>
			</table>
			<c:forEach items="${value2}" var="each" varStatus="cur">
				<tr>
				<td>${each[cur.index]}</td>
				</tr>
			</c:forEach>

		</form>
	</body>
</html>
