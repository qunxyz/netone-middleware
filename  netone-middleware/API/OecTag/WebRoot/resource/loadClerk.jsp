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
  
  
	<h3>װ����Ա</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:loadClerk/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			code���û�������(���Բ���д������Ҫ�������������д��code="")
			username���û���<br>
			dataname:������Ϣ<br>
		<b>��������</b><br>
		ע�⣺���code����д��Ĭ�ϻ�õ�ǰ��¼�û���code<br>
		<hr>
		
    <resource:loadClerk dataname="clerk" code="0000" username="adminx"/>
   �û����� <c:out value="${clerk.description }"></c:out><br>
   ���룺 <c:out value="${clerk.password }"></c:out><br>
   ���ţ�<c:out value="${clerk.deptment }"></c:out>
  </body>
</html>
