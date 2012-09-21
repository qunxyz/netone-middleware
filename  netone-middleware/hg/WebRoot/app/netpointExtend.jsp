<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <P>品牌: <span><a href='javascript:void(0)' style="" onclick="ppclick('')">所有</a></span>
			<c:forEach var="list" items="${pplist}">
				<span> | <a href='javascript:void(0)' style="color: red;" onclick="ppclick('${list.ppname}')" >${list.ppname}</a></span>
			</c:forEach>
	</P>
	<P>		
	时间: <span><a href='javascript:void(0)' style="" onclick="timeclick('')">所有</a></span>
			<c:forEach var="list" items="${timelist}">
				<span> | <a href='javascript:void(0)' style="color: red;" onclick="timeclick('${list.times}')" >${list.times}</a></span>
			</c:forEach>
	</P>
	<P>		
	网点: <span><a href='javascript:void(0)' style="" onclick="netclick('')">所有</a></span>
			<c:forEach var="list" items="${netlist}">
				<span> | <a href='javascript:void(0)' style="color: red;" onclick="netclick('${list.netname}')" >${list.netname}</a></span>
			</c:forEach>
	</P>
  </body>
</html>
