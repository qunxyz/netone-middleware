<%@ page language="java" contentType="text/html; charset=GBK" import="java.util.*" pageEncoding="GBK"%>
<jsp:directive.page import="oe.security3a.sso.util.StringUtil" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page import="oe.security3a.sso.SecurityConfig" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String submitpath = SecurityConfig.getInstance().getLoginSubmitPath();

	request.setAttribute("gotourl", request.getParameter("gotourl"));
	request.setAttribute("todo", request.getParameter("todo"));
	request.setAttribute("errormsg", StringUtil.iso8859toGBK(request.getParameter("errormsg")));

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>登录页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link type="text/css" rel="stylesheet" href="css/css.css">
		<script type="text/javascript" src="include/js/util.js"></script>
	</head>
	<script type="text/javascript">
		function fresh(){
			document.all.imgs.src="ImageCodeSvl?tmp="+Math.random();
		}	
		function check(){
			if(document.all.username.value==""){
				alert("用户名不能为空!");
				return;
			}
			var ajax = xmlhttp("SecondCheckSvl?username="+document.all.username.value+"&sss="+Math.random());
			var restr = ajax.responseText;
			if(restr!=null && restr!=""){
				alert(restr);
			}
		}
	</script>
	<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
		<form action="<%=submitpath%>" method="post">
			<input type="hidden" name="gotourl" value="${gotourl}">
			<input type="hidden" name="todo" value="${todo}">

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
							登录名：
						</td>
						<td>
							<input name="username" type="text"
								style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
								tabindex=1>
						</td>
						<td>
						
								<input type="button" value="注册" class='butt'
									onClick="window.open('<%=basePath%>human/HumanRegeditSelf.jsp','_blank','height=400, width=650, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');" />

						</td>
					</tr>
					<tr>
						<td align="right">
							密&nbsp;&nbsp;码：
						</td>
						<td>
							<input name="password" type="password"
								style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
								tabindex=1>
						</td>
						<td>
							<input type="submit" value="登陆" class='butt' />
							<input type="reset" value="重置" class='butt' />
						</td>
					</tr>

						<tr>
							<td align="right">
								验证码：
							</td>
							<td>
								<input type="text" name="imagecode" value=""
									style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;" />
							</td>
							<td>
								<img src="ImageCodeSvl" name="imgs" onclick="fresh()" title="单击图片刷新">
							</td>
						</tr>


						<tr>
							<td align="right">
								二次认证：
							</td>
							<td>
								<input type="text" name="secondcheck" value=""
									style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;" />
							</td>
							<td>

							</td>
						</tr>
						<tr>
							<td align="center" colspan="3">
								<input type="button" name="btn" value="获取认证码" onclick="check();" class='butt'>
							</td>
						</tr>
				</table>

		</form>
	</BODY>
</html>
