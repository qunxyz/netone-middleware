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
  选择图表格式：<select id="format" name="format">
    <option value="line">线图
    <option value="line3D">线图3D
    <option value="bar">柱图
    <option value="bar3D">柱图3D
    <option value="pie">饼图
    <option value="pie3D">饼图3D
  </select>
  <input type="submit" value="查看图表">
  <hr>
</form>
</body>
</html>
