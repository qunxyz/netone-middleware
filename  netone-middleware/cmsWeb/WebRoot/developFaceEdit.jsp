<%@ page contentType="text/html; charset=GBK"%>
<%
 String param=request.getParameter("id");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>OESEE J++ 设计平台</title>

</head>

<frameset rows="30,*" frameborder="NO" border="0" framespacing="0">
  <frame src="/cmsWeb/cms/infocellSimple/infoCellListSimple.jsp" name="JpptopFrame" scrolling="NO" noresize>
  <frameset rows="*,80" cols="*" framespacing="0" frameborder="NO" border="0">
		<frame src="/cmsWeb/infocelleditSimple.do?id=<%=param%>" name="JppmainFrame">
		<frame src="/cmsWeb/cms/infocellSimple/infoCellToolsSimple.jsp" name="JppbottomFrame" scrolling="NO" noresize>
	</frameset>
</frameset>
</html>
