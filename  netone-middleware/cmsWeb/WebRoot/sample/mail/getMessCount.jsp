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
  <h3>��ӡ��ǰ�ж��ٷ�δ���ʼ�</h3>
		<b>��ǩ����</b><br>
			&lt;mail:getMailLinkUrl /&gt;<br>
		<b>��ǩ����</b><br>
			����Ҫ�������Զ���ȡ��½�û���δ���ʼ���Ŀ<br>
		<b>��������</b><br>
		��ӡ��ǰ�ж��ٷ�δ���ʼ�<br>
		<hr>
    <a href='<mail:getMailLinkUrl />'  target='_blank' /> <mail:getMailCountTag />���ʼ�</a>
  
  </body>
</html>
