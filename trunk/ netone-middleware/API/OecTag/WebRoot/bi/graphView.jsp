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
	<h3>����ͼ������ƣ���ʾͼ��</h3>
		<b>��ǩ����</b><br>
		&lt;bi:chart/&gt;<br>
		<b>��ǩ����</b><br>
			charname��ͼ������<br>
			style:ͼ����ʽ���Ǳ��������<br>
		<b>��������</b><br>
		��ʾͼ������ΪCHART.CHART.CC��ͼ��<br>
	<hr>
		<bi:chart charname="CHART.CHART.SALEPIE" style="width='600'"/>
		<br>
	</body>   
</html>
