<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="oe.frame.web.util.WebStr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String values = WebStr.iso8859ToGBK(request.getParameter("values"));
%>
<html>
	<head>
		<title>编辑SQL视图</title>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src='<%=path%>/include/js/prototype.js'></SCRIPT>
		<script type="text/javascript">
		function does(){
			window.opener.document.all.sqlview.value=document.all.texts.value;
			window.close();
		}
	</script>
	</head>

	<body>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					&nbsp;&nbsp;SQL内容
					<br>
					&nbsp;&nbsp;<textarea rows="30" cols="120" name="texts"><%=values%></textarea>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;&nbsp;<input type="button" name="btns" value="确定" onclick="does();"
						class="buttom">
					<input type="button" name="btn" value="取消"
						onclick="window.close();" class="buttom">
				</td>
			</tr>
		</table>
	</body>

</html>
