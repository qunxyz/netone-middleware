<%@ page language="java" pageEncoding="GBK"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
	OnlineUser oluser = olmgr.getOnlineUser(request);
	String loginname = "";
	if (oluser != null) {
		loginname = oluser.getLoginname();
	}
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
		<h5>
			从首页登陆(首页通常不需要登陆),然后点登陆进入登陆页，然后再返回首页看到登陆信息
		</h5>
		<%
			if (loginname.equals("")) {
		%>
		<input type='button'
			onclick="window.open('LoginHelper.jsp','_blank');" value='登陆' />
		<%
			} else {
		%>
		欢迎阁下:<%=loginname%>
		<%
			}
		%>
		<br>
		<br>
		<br>
		<font color='#999999' size='2'>你好太阳</font>
		<br>
		<font color='#998999' size='3'>你好太阳</font>
		<br>
		<font color='#997999' size='4'>你好太阳</font>
		<br>
		<font color='#996999' size='5'>你好太阳</font>
		<br>
		<font color='#995999' size='6'>你好太阳</font>
		<br>
		<font color='#994999' size='7'>你好太阳</font>
		<br>
		<font color='#993999' size='8'>你好太阳</font>
		<br>
	</BODY>
</HTML>
