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
    
    <title>Load Clerk</title>

  </head>
  
  <body>
  
  
	<h3>装载人员</h3>
		<b>标签名称</b><br>
			<c:out value="<resource:loadClerk/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			code：用户隶属于(可以不填写，但是要声明，比如可以写成code="")
			username：用户名<br>
			dataname:返回信息<br>
		<b>测试例子</b><br>
		注意：如果code不填写，默认获得当前登录用户的code<br>
		<hr>
		
    <resource:loadClerk dataname="clerk" code="0000" username="adminx"/>
   用户名： <c:out value="${clerk.description }"></c:out><br>
   密码： <c:out value="${clerk.password }"></c:out><br>
   部门：<c:out value="${clerk.deptment }"></c:out>
  </body>
</html>
