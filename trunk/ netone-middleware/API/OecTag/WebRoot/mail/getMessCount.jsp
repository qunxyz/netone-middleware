<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/mail" prefix="mail"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>getMessCount</title>
  </head>
  <body>
  <h3>打印当前有多少封未读邮件</h3>
		<b>标签名称</b><br>
			&lt;mail:getMailLinkUrl /&gt;<br>
		<b>标签参数</b><br>
			不需要参数，自动获取登陆用户的未读邮件数目<br>
		<b>测试例子</b><br>
		打印当前有多少封未读邮件<br>
		<hr>
    <a href='<mail:getMailLinkUrl />'  target='_blank' /> <mail:getMailCountTag />封邮件</a>
  
  </body>
</html>
