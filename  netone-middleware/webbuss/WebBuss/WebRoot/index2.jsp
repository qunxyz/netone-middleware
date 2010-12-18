<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://www.oesee.com/chart" prefix="rs"%>
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

		<title>首页-资产管理</title>

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

		<rs:line uri='linemonth6' ds="linemonth6" title="总资产年变化曲线"
			xtitle="总资产值" ytitle="时间" zcolumn="level3"/>			
		
		<img src='${linemonth6}'>

	</body>
</html>
