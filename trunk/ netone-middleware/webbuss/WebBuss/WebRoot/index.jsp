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

		<title>��ҳ-�ʲ�����</title>

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
		<rs:bar uri='barcategro' ds="barcategro" title="���ʲ�����TOPN"
			xtitle="�ʲ����" ytitle="���" />
		<rs:line uri='linemonth20000linemonth3' ds="linemonth20000linemonth3"
			title="���ʲ�������0000���ʲ��ձ仯����" xtitle="���ʲ�ֵ" ytitle="ʱ��" />
		<rs:line uri="linemonth4" ds="linemonth4" title="���ʲ��ձ仯����"
			xtitle="���ʲ�ֵ" ytitle="ʱ��" zcolumn="level3" />


		<rs:pie uri='piecategro' ds="piecategro" title="�ʲ�����" />







		<rs:bar uri='barcategroz' ds="barcategroz" title="���ʲ�����TOPN"
			xtitle="�ʲ����" ytitle="���" />

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
