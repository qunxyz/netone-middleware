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
		<rs:bar uri='barcategro' ds="barcategro" title="总资产排名TOPN"
			xtitle="资产类别" ytitle="金额" />
		<rs:line uri='linemonth20000linemonth3' ds="linemonth20000linemonth3"
			title="总资产日曲线0000总资产日变化曲线" xtitle="总资产值" ytitle="时间" />
		<rs:line uri="linemonth4" ds="linemonth4" title="总资产日变化曲线"
			xtitle="总资产值" ytitle="时间" zcolumn="level3" />


		<rs:pie uri='piecategro' ds="piecategro" title="资产比例" />







		<rs:bar uri='barcategroz' ds="barcategroz" title="总资产排名TOPN"
			xtitle="资产类别" ytitle="金额" />

		<img src='${barcategroz}'>
		<img src='${barcategro}'>
		<table width='80%'>
			<tr>
				<td>
					<iframe src='data1.jsp' frameborder="0"></iframe>
				</td>
				<td>
					<iframe src='data2.jsp' frameborder="0"></iframe>
				</td>
				<td>
					<iframe src='data3.jsp' frameborder="0"></iframe>
				</td>


			</tr>
		</table>

		<img src='${linemonth20000linemonth3}'>
		<img src='${piecategro}'>

		<img src='${linemonth4}'>

	</body>
</html>
