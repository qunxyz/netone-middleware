<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.jl.common.message.Message"%>
<%@page import="oe.frame.web.util.WebStr"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mobile = request.getParameter("mobile");
	String context = request.getParameter("context");
	context=WebStr.encode(request,context);
	if(context!=null&&!context.equals("")){
		out.print(Message.toMessageCore(mobile, context));
	}
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'MessageTest.jsp' starting page</title>

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
		<form action="">
		    mobile number:<input type='text' value='' id='mobile' name='mobile'/> <br>
			<textarea rows="8" cols="80" id='context' name='context'></textarea>
			<input type="submit">
		</form>
	</body>
</html>
