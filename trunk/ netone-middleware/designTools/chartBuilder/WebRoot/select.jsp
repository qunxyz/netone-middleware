<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="test.*" %>
<%@ page import="java.io.*" %>

<html>
<head>
</head>
<body bgcolor="#ffffff">
<!--servlet/test.ShowReportServlet showReport.jsp-->
<form action="servlet" method="post" target="_blank">
  <hr>
  ѡ�񱨱��ʽ��<select name="format">
    <option value="html">html
    <option value="excel">excel
    <option value="pdf">pdf
    <option value="csv">csv
  </select>
  <input type="submit" value="�鿴����">
  <hr>
</form>
</body>
</html>
