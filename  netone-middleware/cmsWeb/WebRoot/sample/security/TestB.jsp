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
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
	
		<!-- 过滤器没有配置这里的过滤,所以需要手工在这里加入<p:login/>来要求该页面做访问控制 -->
		我是
		<rs:logininfo />

		,对于 资源 <strong><font color='red'>BUSSWF.BUSSWF.MYFLOW</font></strong>
		<br><br>
		访问资格呢?
		<rs:permission action="1" resource="BUSSWF.BUSSWF.MYFLOW">YES</rs:permission>
		<rs:noPermission action="1" resource="BUSSWF.BUSSWF.MYFLOW">NO</rs:noPermission>
		<br><br>
		有权限呢?
		<rs:permission action="3" resource="BUSSWF.BUSSWF.MYFLOW">YES</rs:permission>
		<rs:noPermission action="3" resource="BUSSWF.BUSSWF.MYFLOW">NO</rs:noPermission>
		<br><br>
		有最高权限吗?

		<rs:permission action="7" resource="BUSSWF.BUSSWF.MYFLOW">YES</rs:permission>
		<rs:noPermission action="7" resource="BUSSWF.BUSSWF.MYFLOW">NO</rs:noPermission>
		<br>
	
	
	</BODY>
</HTML>
