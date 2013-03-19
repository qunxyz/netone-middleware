<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page import="oe.frame.web.form.RequestUtil"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:directive.page import="oe.security3a.seucore.permission.PermissionServiceImpl"/>
<jsp:directive.page import="oe.security3a.sso.onlineuser.OnlineUserMgr"/>
<jsp:directive.page import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
RequestUtil.setParamMapToRequest(request);

String id = request.getParameter("id");
String dn = request.getParameter("dn");

OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
String loginname = olmgr.getOnlineUser(request).getLoginname();
String logincode = olmgr.getOnlineUser(request).getUserid();
boolean b ;
if(loginname.equalsIgnoreCase("administrator")){
	b = true ;
}else{
	PermissionServiceImpl ps = new PermissionServiceImpl();
	b = ps.checkUserPermission(logincode,loginname,dn,"7");
}
request.setAttribute("edit",b);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test3.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
  </head> 
  
  <body>
  		<br><br>
  		dn: ${paramMap.dn }
  		&nbsp;&nbsp;
    	<c:if test="${edit}">
    		可以操作
    	</c:if>
    	<c:if test="${!edit}">
    		<font color="red">不可操作</font>
    	</c:if>
  </body>
</html>
