<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	*{
		font-size:12px;
		padding:0;
		margin:0 auto;
		font-family:"arial","微软雅黑","宋体";
	}
	img {
		border:0;
	}
	ol,ul{
		list-style:none;
		margin:0;
		padding:0;
	}
	table{
		border-collapse:collapse;
		border-spacing:0;
	}
	li{
		display:block;
		line-height:27px;
		border-bottom:1px dashed #ddd;
	}
	.whitea:link,.whitea:visited
	{
		color:#fff;
		text-decoration:none;
	}
	.ga:link,.ga:visited
	{
		color:#aaa;
		text-decoration:none;
	}
	#top
	{
		position:relative;
	}
	#mymenu
	{
		position:absolute;
		top:100px;
		left:140px;
		color:#fff;
	}
	#logout
	{
		position:absolute;
		top:8px;
		left:920px;
	}
	</style>
  </head>
  
  <body style="text-align:center">
    <div id="top">
    	<img src="workflow/ndyd/images/menu.png" style=""/>
    	<div id="mymenu"><a class="whitea" href="<portal:envget envkey='WEBSER_CMSWEB'/>/extportal.do?id=1306850554025&portalmode=5&hide=1" target="myframe" >首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    					我的内容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    					<a class="whitea" href="<portal:envget envkey='WEBSER_CMSWEB'/>/extframes.do?listPath=FRAMEPG.FRAMEPG"  target="myframe" >我的流程</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    					党群信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    					企业文化&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    					重点频道
    	</div>
    	<div id="logout">
    					<span>欢迎您! <rs:logininfo />&nbsp;&nbsp;&nbsp;&nbsp;</span>
    					<a class="ga" href="<rs:loginout/>" >退出</a>
    	</div>
    </div>
    <div id="portalet"><iframe name="myframe" id="myframe" src="<portal:envget envkey='WEBSER_CMSWEB'/>/frames/AppFrame1.jsp?rs=FRAMEPG.FRAMEPG.BUSSFLOW&layout=left&needhidden=y&height=0&fckid=&initurl=" height="800" width="1200" frameborder="0"></iframe></div>
  </body>
</html>
