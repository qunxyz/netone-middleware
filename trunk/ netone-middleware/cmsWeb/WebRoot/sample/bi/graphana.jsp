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
	<h3>����ͼ������ƣ���ý���ͼ�����������</h3>
		<b>��ǩ����</b><br>
			&lt;bi:chartActUrl/&gt;<br>
		<b>��ǩ����</b><br>
			charname��ͼ������<br>
		<b>��������</b><br>
		��ý���ͼ������ΪCHART.CHART.CC�ķ���ҳ������ӵ�ַ<br>
	<hr>
		
		<a href='<bi:chartActUrl  charname="CHART.CHART.CC"/>' target='_blank'>�������</a>

		<br>
	</body>
</html>
