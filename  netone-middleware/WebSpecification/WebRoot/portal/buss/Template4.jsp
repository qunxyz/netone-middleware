<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/style.css" rel="stylesheet"
			type="text/css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<!-- 实现一个例子关于 调用后台服务的 -->
		<rs:bean usename="mytest" beanname="beansample">
		name:mike;
		age:25;
		sex:true;
		</rs:bean>
		AAA:${mytest.values.types}
	</BODY>
</HTML>
