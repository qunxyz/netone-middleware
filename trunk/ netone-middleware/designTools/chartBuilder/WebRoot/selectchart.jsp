<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="test.*" %>
<%@ page import="java.io.*" %>

<html>
<head>
</head>
<body bgcolor="#ffffff">
<!--servlet/test.ShowReportServlet showReport.jsp-->
<form action="chartservlet" method="post" target="_blank">
  <hr>
  ѡ��ͼ���ʽ��<select id="format" name="format">
    <option value="line">��ͼ
    <option value="line3D">��ͼ3D
    <option value="bar">��ͼ
    <option value="bar3D">��ͼ3D
    <option value="pie">��ͼ
    <option value="pie3D">��ͼ3D
  </select>
  <input type="submit" value="�鿴ͼ��">
  <hr>
</form>
</body>
</html>
