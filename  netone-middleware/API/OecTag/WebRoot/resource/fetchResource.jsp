<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/resource"  prefix="resource"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>fetchResource</title>
  </head>
  
  <body>
  
  
  <body>
	<h3>根据查询条件，获得资源</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:fetchResource/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			condition：查询条件<br>
			dataname:返回结果<br>
		<b>测试例子</b><br>
		查询资源类型是BUSSENV的所有资源<br>
	  测试使用的查询条件是：and objectType like 'BUSSENV.BUSSENV%'
   <br>
   <hr>
   <c:set var="condition" value="and objectType like 'BUSSENV.BUSSENV%'"></c:set>
   <resource:fetchResource dataname="list" condition="${condition }"></resource:fetchResource>
   <c:forEach items="${list }" var="upo">
   		id：<c:out value="${upo.id}"></c:out><br>
   		naturanlname:<c:out value="${upo.naturalname }"></c:out><br>
   		创建时间:<c:out value="${upo.created }"></c:out>
   		<hr>
   </c:forEach>

  </body>
</html>
