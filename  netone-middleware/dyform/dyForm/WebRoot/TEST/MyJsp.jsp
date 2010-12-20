<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>MyJsp.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<body>
	<form name="form" action="<%=request.getContextPath()%>/Tests"
		method="post">
		<table border="1" cellpadding="3" cellspacing="1" align="center"
			width="98%">
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="确定">
					<input type="reset" value="取消">
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
