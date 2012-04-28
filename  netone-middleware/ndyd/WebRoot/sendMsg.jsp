<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.jl.common.message.Message"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="oe.frame.web.util.WebTip"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sendMsg.jsp' starting page</title>
    
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
    <form action="<%=basePath %>/servlet/MessageSvl" method="post">
    手机号码多条手机号码用逗号分割:<br>
    <textarea name='mobile' id='mobile' cols="60" rows="3"></textarea><br>
    发送的内容：<br>
     <textarea name='info' id='info' cols="60" rows="3"></textarea>
     <input type='submit' value='send' class='btn'>
    </form>
  </body>
</html>
