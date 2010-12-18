<%@ page language="java" contentType="text/html; charset=GBK"
	import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page import="oe.security3a.sso.util.StringUtil" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page import="oe.security3a.sso.SecurityConfig" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String submitpath = SecurityConfig.getInstance()
			.getLoginSubmitPath();

	request.setAttribute("gotourl", request.getParameter("gotourl"));
	request.setAttribute("todo", request.getParameter("todo"));
	request.setAttribute("errormsg", StringUtil.iso8859toGBK(request
			.getParameter("errormsg")));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>µÇÂ¼Ò³Ãæ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>
	<BODY>
		<form action="<%=submitpath%>" method="post">
			<input type="hidden" name="gotourl" value="${gotourl}">
			<input type="hidden" name="todo" value="${todo}">

			<table width="300" border="0" align="right" cellpadding="0"
				cellspacing="0" style="font-size:9pt">
				<tr>
					<td colspan="3">
						<c:if test="${errormsg != null}">
							<font color="#CCFF66">${errormsg }</font>
						</c:if>
					</td>
				</tr>
				<tr>
					<td width="23%" height="35" align="right">
						ÕÊ»§Ãû£º
					</td>
					<td width="42%">
						<input name="username" type="text"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=1>
					</td>
					<td width="35%" align="center">
						<input type="submit" value="µÇÂ½" />

					</td>
					<td>
						<input type="button" value="×¢²á" target='_blank' disabled
							onClick="window.open('http://www.oesee.com:8080/Security3A/human/HumanRegeditSelf.jsp');"
							style="width:57px;height:20px;" />

					</td>
				</tr>
				<tr>
					<td height="35" align="right">
						ÃÜ&nbsp;&nbsp;Âë£º
					</td>
					<td>
						<input name="password" type="password"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px; "
							tabindex=1>
					</td>
					<td align="center">
						<input type="reset" value="È¡Ïû"
							style="background-image:url(width:57px;height:20px;" />
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan='4'>
						<br>
						Test Account:
						<font color='blue'>oec</font> password:
						<font color='blue'>oec</font>
					</td>
				</tr>
			</table>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<table>
				<tr>
					<td>
						<img src='sso/portal.jpg'>
					</td>
				</tr>
			</table>

		</form>
	</BODY>
</html>
