<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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

		<title>My JSP 'newApp.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript">

	</script>
	</head>

	<body>
		<form action="">
			<table>
				<tr>
					<td>
						应用描述
					</td>
					<td>
						<textarea rows="5" cols="60" name='note' id='note'></textarea>
					</td>
				</tr>
				<tr>
					<td>
						选择表单
					</td>
					<td>

					</td>
				</tr>
				<tr>
					<td>
						选择流程
					</td>
					<td>

					</td>
				</tr>
				<tr>
					<td>
						扩展脚本
					</td>
					<td>
						<textarea rows="5" cols="60" name='ext' id=''ext''></textarea>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type="submit" name="提交" />
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
