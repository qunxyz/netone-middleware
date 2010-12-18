<%@ page language="java" pageEncoding="gbk"%>
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

		<title>rbacsearch.jsp</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link type="text/css" rel="stylesheet" href="css/css.css">
		<script type="text/javascript" src="include/js/page.js"></script>
		<script type="text/javascript" src="include/js/rbac/rbacsearch.js"></script>
	</head>

	<body>
		<form action="" name="form1" method="post">
			<input type="hidden" name="appid" value="${paramMap.appid}" />
			<table id="table1" width="100%">
				<tr>
					<td>
						角色名
					</td>
					<td>
						<input type="text" name="name" value="">
					</td>
				</tr>
				<tr>
					<td>
						分类
					</td>
					<td>
						<select name="belongingness" style="width: 130px">
							<c:forEach items="${list}" var="belong">
								<option value="${belong.id}"}>
									${belong.name }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						描述
					</td>
					<td>
						<input type="text" name="description" value="">
					</td>
				</tr>
				<tr>
					<td>
						<input type="button" value="查询" onclick="rbacsearch();" class="butt">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
