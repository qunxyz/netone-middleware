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
		我叫
		<rs:logininfo></rs:logininfo>
		,我已经成功进入了
		<br>
		<br>
		<br>
		<INPUT type='button' value='访问安全后台'
			onClick="window.open('<rs:accessSecurity/>')" class='butt' />
		<a href='<rs:accesslog/>' target='_blank'>访问日志</a>
		<a href='<rs:changepassword/>' target='_blank'>修改个人密码</a>
		<a href='<rs:userinfo/>' target='_blank'>个人信息</a>

		<a href='<rs:rolemanage/>' target='_blank'>角色管理</a>
		<a href='<rs:rolemanageTree/>' target='_blank'>角色管理树</a>
		<a href='<rs:usermanageTree/>' target='_blank'>用户管理树</a>
		<a href='<rs:usermanage/>' target='_blank'>用户管理</a>
		<a href='<rs:loginout/>'>注销</a>


	</BODY>
</HTML>
