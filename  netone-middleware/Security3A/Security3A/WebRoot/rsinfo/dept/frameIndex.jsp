<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="oe.frame.web.form.RequestUtil"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
RequestUtil.setParamMapToRequest(request);
String root=request.getParameter("naturalname");
if(root==null||root.equals("")){
   root="DEPT.DEPT";
   }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>frameindex</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head> 
  <frameset cols="200,*" frameborder=0>
	 <frame src="rsinfo/dept/DepartmentTree.do?naturalname=<%=root%>" name="left" scrolling="auto" marginwidth="0" marginheight="0">
	 <frame src="" name="right" id="right" scrolling="auto" marginwidth="0" marginheight="0" >
  </frameset>
</html>
