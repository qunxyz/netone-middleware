
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="my" uri="xhtml"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			String[] dim = { "1��", "2��", "3��", "4��", "5��", "6��", "yuy7��", "1��",
					"12��", "13��", "14��", "15��", "ds16��", "yuu17��", "yuy21��", "22��", "23��", "24��",
					"5c��", "6x��", "7d��", "1a��", "2d��", "c3��", "4d��", "5d��", "f6��", "7f��",
					"1��c", "2��ewr", "3��wer", "4��", "5��", "612esdf��", "71111��", "ffsdf1��", "sdf2��", "sfds3��",
					"4��c", "5��ewr", "6��wer", "7��", "dfds1��", "2fff��", "3��ffdf", "4dsfsd��", "dsf5��", "yuy6��",
					"7��d", "1��erew", "2wer", "3��", "4��", "5��dsfds", "6fffffu��", "7��", "1��", "yuy2��",
					"3��h", "4��r", "5��fdsfds", "6��dsfd", "7yuy��", "1uuuy��", "sdf2��", "dfdsf3��", "uyu4��", "dsf��",
					"6��j", "7��r", "1��fdsfsd", "2csd��", "fv3��", "4fcsd��", "dfds5��", "csdf6��", "fcsd7��" };
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
			String dimname = "�·�";
			String[] targetname = { "���۳ɱ�", "������" };
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
				
					<my:graph dimvaluelist="<%=dim%>" dimName="<%=dimname%>" targetvaluelist="<%=target%>" targetname="<%=targetname%>" charttype="CombinedBarLin" title="���۳ɱ�����ͼ" graphwidth="400" graphheight="300" />
				</td>
			</tr>
		</table>

	</body>
</html>
