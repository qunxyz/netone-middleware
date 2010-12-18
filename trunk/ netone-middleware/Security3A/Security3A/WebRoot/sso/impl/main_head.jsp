<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page
	import="oe.security3a.sso.onlineuser.OnlineUserMgr" />
<jsp:directive.page
	import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
	String loginname = olmgr.getOnlineUser(request).getLoginname();
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户权限管理系统</title>
		<link href="css/css.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<script type="text/javascript">
	function changeframe(url){
		parent.document.frames["mainFrame"].location=url ;
	}
</script>
	</head>
	
</html>
