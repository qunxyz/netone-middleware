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
	<h3>根据图表的名称，获得进入图表分析的链接</h3>
		<b>标签名称</b><br>
			&lt;bi:chartActUrl/&gt;<br>
		<b>标签参数</b><br>
			charname：图表名称<br>
		<b>测试例子</b><br>
		获得进入图表名称为CHART.CHART.CC的分析页面的链接地址<br>
	<hr>
		
		<a href='<bi:chartActUrl  charname="CHART.CHART.CC"/>' target='_blank'>进入分析</a>

		<br>
	</body>
</html>
