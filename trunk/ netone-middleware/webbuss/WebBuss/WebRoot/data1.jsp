<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/chart" prefix="rs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'data1.jsp' starting page</title>
    
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
  <rs:table ds="dailynew" data="data" />
 					<strong>最近记账动态</strong>
					<a href='<%=path%>/turnpageori/list' target='_blank'><font
						color='red'>[管理帐目]</font> </a>
					<br>

					<c:forEach items="${data}" var="getCol">

						<a href="<%=path%>/turnpageori/update?lsh=${getCol.lsh}"
							target='_blank'>${getCol.name}&nbsp;${getCol.value}&nbsp;${getCol.created}&nbsp;</a>
						<br>
					</c:forEach>
  </body>
</html>
