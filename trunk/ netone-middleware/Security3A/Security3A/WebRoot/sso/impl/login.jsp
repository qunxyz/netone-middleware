<%@ page language="java" contentType="text/html; charset=GBK"
	import="java.util.*" pageEncoding="GBK"%>
<%@page import="oe.security3a.sso.LoginInfo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page import="oe.security3a.sso.SecurityConfig" />

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String submitpath = SecurityConfig.getInstance()
			.getLoginSubmitPath();
			
	
    String tip =(String)request.getParameter("errormsg");
    

	request.setAttribute("gotourl", request.getParameter("gotourl"));
	request.setAttribute("todo", request.getParameter("todo"));
	
	request.setAttribute("errormsg", tip);
	String cssURL="";
       String gotourl=request.getParameter("gotourl");
gotourl=gotourl==null?"":gotourl;
	//if(gotourl.indexOf("iss")!=-1){ 
			//cssURL = request.getContextPath() + "/images/erp_blue";
	//}else if(gotourl.indexOf("pms")!=-1){ 
			//cssURL = request.getContextPath() + "/images/pms_blue";
	//}else{
			//cssURL = request.getContextPath() + "/images/mid_blue";
	//}

     
	cssURL = request.getContextPath() + "/images/erp_red";


	String errormsg = request.getParameter("errormsg");

	String errorinfo = "";
	if (LoginInfo._ERROR_1[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_1[1];
	}else if(LoginInfo._ERROR_2[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_2[1];
	}else if(LoginInfo._ERROR_3[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_3[1];
	}else if(LoginInfo._ERROR_4[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_4[1];
	}else if(LoginInfo._ERROR_5[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_5[1];
	}else if(LoginInfo._ERROR_6[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_6[1];
	}else if(LoginInfo._ERROR_7[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_7[1];
	}	else if(LoginInfo._ERROR_8[0].equals(errormsg)) {
		errorinfo = LoginInfo._ERROR_8[1];
	}

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
		function nocn(){
			for(i=0;i<document.getElementsByName("username")[0].value.length;i++){
				var c = document.getElementsByName("username")[0].value.substr(i,1);
				var ts = escape(c);
				if(ts.substring(0,2) == "%u"){
					document.getElementsByName("username")[0].value = "";
				}
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
						
							<font color="red"><%=errorinfo%></font>
						
					</td>
				</tr>
				<tr>
					<td align="right">
						登录名：
					</td>
					<td>
						<input name="username" type="text" onKeyup="nocn()"  onblur="nocn();"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=1>
					</td>
					<td>

						<input type="button" value="注册" class='butt'
							onClick="window.open('<%=basePath%>human/HumanRegeditSelf.jsp','_blank','height=650, width=650, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');" />

					</td>
				</tr>
				<tr>
					<td align="right">
						密&nbsp;&nbsp;码：
					</td>
					<td>
						<input name="password" type="password"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=2>
					</td>
					<td>
						<input type="submit" value="登陆" class='butt' tabindex="5" />
						<input type="reset" value="重置" class='butt' />
					</td>
				</tr>

				<tr>
					<td align="right">
						验证码：
					</td>
					<td>
						<input type="text" name="imagecode" value="" maxlength="4"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex="3" />
					</td>
					<td>
						<img src="ImageCodeSvl" name="imgs" onClick="fresh()"
							title="单击图片刷新">
					</td>
				</tr>



				<tr style="display:none">
					<td align="right">
						四位代码：
					</td>
					<td>
						<input name="code" type="text" 
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=4 maxlength="4">
					</td>
					<td>

					</td>
				</tr>

				<tr style="display:none">
					<td colspan='3'>
						初始密码:admin@1234
					</td>
				</tr>
			</table>
<%if(gotourl!=null&&gotourl.endsWith("/cmsWeb/index.jsp")){%>

<iframe src='extinfo.jsp' frameborder="0" width="100%" height="60%"></iframe>

<%}%>
 
		</form>
	</BODY>
</html>
