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
			应用Oesee Netone标签结合JSTL来控制页面访问权限
		</h2>
	
		<!-- 过滤器没有配置这里的过滤,所以需要手工在这里加入<p:login/>来要求该页面做访问控制 -->
		我是
		<rs:logininfo />

		,对于 资源RSSAMPLE.RSSAMPLE.DIRA
		<br><br>
		我有高级权限吗?
		<br>
		<rs:permission action="7" resource="RSSAMPLE.RSSAMPLE.DIRA">YES</rs:permission>
		<rs:noPermission action="7" resource="RSSAMPLE.RSSAMPLE.DIRA">NO</rs:noPermission>
		<br>
		中级权限呢?
		<br>
		<rs:permission action="3" resource="RSSAMPLE.RSSAMPLE.DIRA">YES</rs:permission>
		<rs:noPermission action="3" resource="RSSAMPLE.RSSAMPLE.DIRA">NO</rs:noPermission>
		<br>
		访问资格呢?
		<br>
		<rs:permission action="1" resource="RSSAMPLE.RSSAMPLE.DIRA">YES</rs:permission>
		<rs:noPermission action="1" resource="RSSAMPLE.RSSAMPLE.DIRA">NO</rs:noPermission>
	</BODY>
</HTML>
