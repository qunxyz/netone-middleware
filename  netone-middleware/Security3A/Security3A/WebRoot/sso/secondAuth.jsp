<%@ page language="java" contentType="text/html; charset=GBK"
	import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>二次认证页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link type="text/css" rel="stylesheet" href="css/css.css">
		<script type="text/javascript" src="include/js/util.js"></script>
	</head>
	<script type="text/javascript">
		function check(){
			var ajax = xmlhttp("SecondCheckSvl?sss="+Math.random());
			var restr = ajax.responseText;
			if(restr!=null && restr!=""){
				alert(restr);
			}
		}
	</script>
	<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
		<form action="SecondCheckSvl?task=check" method="post">
			<input type="hidden" name="gotourl" value="${param.gotourl2}">
			<input type="hidden" name="todo" value="${param.todo}">

			<table cellpadding="2" cellspacing="2">
				<tr>
					<td colspan="3">
						<c:if test="${errormsg != null}">
							<font color="red">${errormsg }</font>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="right">
						二次认证码：
					</td>
					<td>
						<input type="text" name="secondcheck" value=""
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;" />
					</td>
					<td>
						<input type="button" name="btn" value="获得二次认证码" onclick="check();">
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="提交">
					</td>
				</tr>
			</table>

		</form>
	</BODY>
</html>
