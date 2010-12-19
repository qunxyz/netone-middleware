<%@ page contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();
			String login = (String) request.getAttribute("nologin");
			String topinfo = path + "/configureTop.jsp";
			String maininfo = "/cmsWeb/showFloatDiv.do?modelid=1&opr=design";
			String toolinfo = path + "/infocelltool.do";
			String systetool = path + "/systemTop.jsp";
			if (!"2".equals(login)) {
				topinfo = "";
				maininfo = "";
				toolinfo = "";
				systetool="";
			}
%>
<html lang="true">
	<head>

		<title>
			Oesee 软件产品：NetOne 系统快速设计平台
		</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">

	</head>

	<frameset rows="45,*" cols="*" framespacing="0" frameborder="YES" border="0">
		<frame src="<%=systetool%>" name="topFrame" scrolling="NO" noresize>
		<frameset rows="*,0" cols="*" frameborder="YES" border="0" framespacing="0">
			<frameset rows="*" cols="125,*" framespacing="0" frameborder="NO" border="0">
				<frame name="configureTopFrame" src="<%=topinfo%>" scrolling="no" noResize>
				<frame name="configureContent" src="<%=maininfo%>" scrolling="auto" noResize>
			</frameset>
		</frameset>

		<noframes>
			<body>
			</body>
		</noframes>
</html>
