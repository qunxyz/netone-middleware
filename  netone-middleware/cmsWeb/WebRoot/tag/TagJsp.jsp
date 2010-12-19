
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="my" uri="xhtml"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			String[] dim = { "1月", "2月", "3月", "4月", "5月", "6月", "yuy7月", "1月",
					"12月", "13月", "14月", "15月", "ds16月", "yuu17月", "yuy21月", "22月", "23月", "24月",
					"5c月", "6x月", "7d月", "1a月", "2d月", "c3月", "4d月", "5d月", "f6月", "7f月",
					"1月c", "2月ewr", "3月wer", "4月", "5月", "612esdf月", "71111月", "ffsdf1月", "sdf2月", "sfds3月",
					"4月c", "5月ewr", "6月wer", "7月", "dfds1月", "2fff月", "3月ffdf", "4dsfsd月", "dsf5月", "yuy6月",
					"7月d", "1月erew", "2wer", "3月", "4月", "5月dsfds", "6fffffu月", "7月", "1月", "yuy2月",
					"3月h", "4月r", "5月fdsfds", "6月dsfd", "7yuy月", "1uuuy月", "sdf2月", "dfdsf3月", "uyu4月", "dsf月",
					"6月j", "7月r", "1月fdsfsd", "2csd月", "fv3月", "4fcsd月", "dfds5月", "csdf6月", "fcsd7月" };
			String[] tar1 = { "10", "50", "15", "30", "100", "14", "1", "10",
					"50", "15", "30", "100", "14", "1", "10", "50", "15", "30",
					"100", "14", "1", "10", "50", "15", "30", "100", "14", "1",
					"10", "50", "15", "30", "100", "14", "1", "10", "50", "15",
					"30", "100", "14", "1", "10", "50", "15", "30", "100",
					"14", "1", "10", "50", "15", "30", "100", "14", "1", "10",
					"50", "15", "30", "100", "14", "1", "10", "50", "15", "30",
					"100", "14", "1", "10", "50", "15", "30", "100", "14", "1" };
			String[] tar2 = { "10", "50", "15", "30", "100", "14", "1", "10",
					"50", "15", "30", "100", "14", "1", "10", "50", "15", "30",
					"100", "14", "1", "10", "50", "15", "30", "100", "14", "1",
					"10", "50", "15", "30", "100", "14", "1", "10", "50", "15",
					"30", "100", "14", "1", "10", "50", "15", "30", "100",
					"14", "1", "10", "50", "15", "30", "100", "14", "1", "10",
					"50", "15", "30", "100", "14", "1", "10", "50", "15", "30",
					"100", "14", "1", "10", "50", "15", "30", "100", "14", "1" };

			String[][] target = { tar1, tar2 };
			String dimname = "月份";
			String[] targetname = { "销售成本", "销售量" };
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>
			My JSP 'TagJsp.jsp' starting page
		</title>

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
		<table border='1'>
			<tr>
				<td>
				
					<my:graph dimvaluelist="<%=dim%>" dimName="<%=dimname%>" targetvaluelist="<%=target%>" targetname="<%=targetname%>" charttype="CombinedBarLin" title="销售成本趋势图" graphwidth="400" graphheight="300" />
				</td>
			</tr>
		</table>

	</body>
</html>
