<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/bi" prefix="bi"%>
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

		<title>My JSP 'graphView.jsp' starting page</title>


	</head>

	<body>
	<h3>根据图表的名称，显示图表</h3>
		<b>标签名称</b><br>
		&lt;bi:chart/&gt;<br>
		<b>标签参数</b><br>
			charname：图表名称<br>
			style:图表样式（非必须参数）<br>
		<b>测试例子</b><br>
		显示图表名称为CHART.CHART.CC的图表<br>
	<hr>
		<bi:chart charname="CHART.CHART.SALEPIE" style="width='600'"/>
		<br>
	</body>   
</html>
