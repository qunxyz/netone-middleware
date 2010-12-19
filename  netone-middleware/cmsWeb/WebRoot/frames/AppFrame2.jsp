<%@ page language="java" pageEncoding="gbk"%>
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
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<body BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0>
		<iframe id="head"
			src="/fck/PagelistViewSvl?pagename=simplefcklist&chkid=${param.fckid}"
			scrolling="no" resize="no" height="${param.height}" width="100%"
			frameborder='0'></iframe>
		<iframe id="proletleft" src="frames.do?listPath=${param.rs}&type=menu&initurl=${param.initurl}"
			scrolling="no" resize="no" height="100%" width="100%" frameborder='0'></iframe>


	</body>
</html>
