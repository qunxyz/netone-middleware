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
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">

		<h2>
			应用Oesee Netone标签结合JSTL来访问 NetOne平台中面向业务SOA过程所定制的业务组件服务beanService
		</h2>
		<!-- 实现一个例子关于 调用后台服务的 -->
		<rs:bean usename="mytest" beanname="beansample">
		name:mike;
		age:25;
		sex:true;
		</rs:bean>
		AAA:${mytest.values.types}
	</BODY>
</HTML>
