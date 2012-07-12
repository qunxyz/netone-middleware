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

		<title>催办</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">


		<link rel="stylesheet" type="text/css"
			href="/ndyd/script/theme/main/common.css">
		<style type="text/css">
		
body
{
	width:120px;
	height:22px;
	font:12px "宋体";
	color:#001a9b;
	float:left;
	padding:4px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
	border-collapse:collapse;
	background-attachment:fixed;
	background-position: top center;
	background-repeat:repeat-x;	
}
		</style>

		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<form action="CuibangSvl">
			<table width="100%" border="1">
				<tr>
					<td>
						<font color='#001a9b' size='2'>催办给</font>
					</td>
					<td>
						<font color='#001a9b' size='2'>${cuibangto}</font>
					</td>
				</tr>
				<tr>
					<td>
						<font color='#001a9b' size='2'>催办内容</font>
					</td>
					<td>
						<textarea rows="5" cols="55" id="context" name="context">${context}</textarea>
						<input type='hidden' id='username' name='username' value='${username}'/>
					</td>
				</tr>
				<tr>
				<td align="center" colspan='2'>			<input type='submit' value='催办' class='btn'>
			<input type='button' value='关闭' onClick='window.close();' class='btn'></td>
				</tr>

			</table>

			<input type="hidden" id='workcode' name='workcode'
				value='${workcode}'>

	

	
		</form>
	</body>
</html>
