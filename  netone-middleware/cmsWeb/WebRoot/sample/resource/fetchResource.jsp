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
    <title>fetchResource</title>
  </head>
  
  <body>
  
  
  <body>
	<h3>���ݲ�ѯ�����������Դ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<resource:fetchResource/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			condition����ѯ����<br>
			dataname:���ؽ��<br>
		<b>��������</b><br>
		��ѯ��Դ������BUSSENV��������Դ<br>
	  ����ʹ�õĲ�ѯ�����ǣ�and objectType like 'BUSSENV.BUSSENV%'
   <br>
   <hr>
   <c:set var="condition" value="and objectType like 'BUSSENV.BUSSENV%'"></c:set>
   <resource:fetchResource dataname="list" condition="${condition }"></resource:fetchResource>
   <c:forEach items="${list }" var="upo">
   		id��<c:out value="${upo.id}"></c:out><br>
   		naturanlname:<c:out value="${upo.naturalname }"></c:out><br>
   		����ʱ��:<c:out value="${upo.created }"></c:out>
   		<hr>
   </c:forEach>

  </body>
</html>
