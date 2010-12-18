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
	if(gotourl.indexOf("iss")!=-1){ 
			cssURL = request.getContextPath() + "/images/erp_blue";
	}else if(gotourl.indexOf("pms")!=-1){ 
			cssURL = request.getContextPath() + "/images/pms_blue";
	}else{
			cssURL = request.getContextPath() + "/images/mid_blue";
	}

     
	//String cssURL = request.getContextPath() + "/images/erp_blue";


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
		function submitBox(){
			var form = document.getElementById('loginForm');
			form.submit();
		}
	//键盘按键事件
 	document.onkeypress=function(){keyCodeEvent()}
 	function keyCodeEvent(){//按下回车登录
			if(event.keyCode==13){
				submitBox();
			} 
	}
	</script>
<body bgcolor="#5291D8" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="<%=submitpath%>" method="post" id="loginForm">
			<input type="hidden" name="gotourl" value="${gotourl}">
			<input type="hidden" name="todo" value="${todo}">
			<input name="code" type="hidden" style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=4 maxlength="4">
			<input type="hidden" value="注册" class='butt'
							onClick="window.open('<%=basePath%>human/HumanRegeditSelf.jsp','_blank','height=650, width=650, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');" />
	
			<input type="hidden" value="重置" class='butt' />
<!-- ImageReady Slices (未标题-1) -->
<table id="__01" width="1440" height="633" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="7">
			<img src="<%=cssURL%>/erplogin2_01.gif" width="1440" height="295" alt=""></td>
	</tr>
	<tr>
		<td rowspan="8">
			<img src="<%=cssURL%>/erplogin2_02.gif" width="749" height="338" alt=""></td>
		<td>
			<img src="<%=cssURL%>/erplogin2_03.gif" width="69" height="29" alt=""></td>
		<td colspan="4" style=" background-image:url(<%=cssURL%>/erplogin2_04.gif)">
		<input name="username" type="text" onKeyup="nocn()"  onblur="nocn();"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=1>                    
							
			<font color='red'><%=errorinfo%></font>
		</td>
		<td rowspan="8">
			<img src="<%=cssURL%>/erplogin2_05.gif" width="287" height="338" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="<%=cssURL%>/erplogin2_06.gif" width="69" height="30" alt=""></td>
		<td colspan="4" style="background-image:url(<%=cssURL%>/erplogin2_07.gif) ">
			<input name="password" type="password"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=2>
		</td>
	</tr>
	<tr>
		<td>
			<img src="<%=cssURL%>/erplogin2_08.gif" width="69" height="29" alt=""></td>
		<td colspan="4" style="background-image:url(<%=cssURL%>/erplogin2_09.gif); ">
		<input type="text" name="imagecode" value="" maxlength="4"
							style="margin-bottom:3;BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex="3" />         
							<img src="ImageCodeSvl" name="imgs" onClick="fresh()" title="单击图片刷新" height="23px">
	  </td>
	</tr>
	<tr>
		<td colspan="5">
			<img src="<%=cssURL%>/erplogin2_10.gif" width="404" height="8" alt=""></td>
	</tr>
	<tr>
		<td rowspan="4">
			<img src="<%=cssURL%>/erplogin2_11.gif" width="69" height="242" alt=""></td>
		<td colspan="3" style="background-image:url(<%=cssURL%>/erplogin2_12.gif); height:31">
			<font style="font-size:12px; color:#FFCC00 ">初始密码:admin@1234</font></td>
		<td rowspan="4">
			<img src="<%=cssURL%>/erplogin2_13.gif" width="53" height="242" alt=""></td>
	</tr>
	<tr>
		<td colspan="3">
			<img src="<%=cssURL%>/erplogin2_14.gif" width="282" height="13" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="<%=cssURL%>/erplogin2_15.gif" width="22" height="198" alt=""></td>
		<td>
			<img src="<%=cssURL%>/erplogin2_16.gif" alt="" width="68" height="23" border="0" usemap="#Map" style="cursor: hand;"></td>
		<td rowspan="2" style="background-image:url(<%=cssURL%>/erplogin2_17.gif); " width="192" height="198">
			<font style="font-size:15px; color:#FFCC00 ">(提示:建议在IE7上使用)</font></td>
	</tr>
	<tr>
		<td>
			<img src="<%=cssURL%>/erplogin2_18.gif" width="68" height="175" alt=""></td>
	</tr>
</table>
</form>
<!-- End ImageReady Slices -->
<map name="Map">
  <area shape="rect" coords="2,1,70,23" onClick="submitBox();">
</map>
</body>
</html>