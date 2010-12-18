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
	
	String cssURL = request.getContextPath() + "/images/erplogin";


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
		<title>µÇÂ¼Ò³Ãæ Hello</title>
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
	//¼üÅÌ°´¼üÊÂ¼þ
 	document.onkeypress=function(){keyCodeEvent()}
 	function keyCodeEvent(){//°´ÏÂ»Ø³µµÇÂ¼
			if(event.keyCode==13){
				submitBox();
			} 
	}
	</script>
<body bgcolor="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="<%=submitpath%>" method="post" id="loginForm">
			<input type="hidden" name="gotourl" value="${gotourl}">
			<input type="hidden" name="todo" value="${todo}">
			<input name="code" type="hidden" style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=4 maxlength="4">
			<input type="hidden" value="×¢²á" class='butt'
							onClick="window.open('<%=basePath%>human/HumanRegeditSelf.jsp','_blank','height=650, width=650, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');" />
	
			<input type="hidden" value="ÖØÖÃ" class='butt' />
<!-- ImageReady Slices (zhangyu.gif) -->
<table id="__01" width="1398" height="447" border="0" cellpadding="0" cellspacing="0" style="margin-top:5% ">
	<tr>
		<td rowspan="11">
			<img src="<%=cssURL%>/zhangyu_01.gif" width="97" height="438" alt=""></td>
		<td colspan="7">
			<img src="<%=cssURL%>/zhangyu_02.gif" width="1300" height="98" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="98" alt=""></td>
	</tr>
	<tr>
		<td rowspan="4">
			<img src="<%=cssURL%>/zhangyu_03.gif" width="301" height="153" alt=""></td>
		<td colspan="5">
			<img src="<%=cssURL%>/zhangyu_04.gif" width="556" height="117" alt=""></td>
		<td rowspan="11">
			<img src="<%=cssURL%>/zhangyu_05.gif" width="443" height="348" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="117" alt=""></td>
	</tr>
	<tr>
		<td colspan="5">
			<img src="<%=cssURL%>/zhangyu_06.gif" width="556" height="18" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="18" alt=""></td>
	</tr>
	<tr>
		<td rowspan="9">
			<img src="<%=cssURL%>/zhangyu_07.gif" width="14" height="213" alt=""></td>
		<td colspan="4">
			<img src="<%=cssURL%>/zhangyu_08.gif" width="542" height="9" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="9" alt=""></td>
	</tr>
	<tr>
		<td rowspan="2">
			<img src="<%=cssURL%>/zhangyu_09.gif" width="72" height="29" alt=""></td>
		<td colspan="3" rowspan="2" style=" background-image:url(<%=cssURL%>/zhangyu_10.gif)">
			<input name="username" type="text" onKeyup="nocn()"  onblur="nocn();"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=1>                    
							
							<font color='red'><%=errorinfo%></font>
						</td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="9" alt=""></td>
	</tr>
	<tr>
		<td rowspan="7">
			<img src="<%=cssURL%>/zhangyu_11.gif" width="301" height="195" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="20" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="<%=cssURL%>/zhangyu_12.gif" width="72" height="32" alt=""></td>
		<td colspan="3" style="background-image:url(<%=cssURL%>/zhangyu_13.gif) ">
		<input name="password" type="password"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex=2>
	  </td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="32" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="<%=cssURL%>/zhangyu_14.gif" width="72" height="30" alt=""></td>
		<td colspan="3" style="background-image:url(<%=cssURL%>/zhangyu_15.gif); ">
			<input type="text" name="imagecode" value="" maxlength="4"
							style="BORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; WIDTH: 110px; BORDER-BOTTOM: #000000 1px solid; HEIGHT: 19px;"
							tabindex="3" />         
							<img src="ImageCodeSvl" name="imgs" onClick="fresh()" title="µ¥»÷Í¼Æ¬Ë¢ÐÂ" height="20px"></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="30" alt=""></td>
	</tr>
	<tr>
		<td colspan="4">
			<img src="<%=cssURL%>/zhangyu_16.gif" width="542" height="10" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="10" alt=""></td>
	</tr>
	<tr>
		<td colspan="2" style="background-image:url(<%=cssURL%>/zhangyu_17.gif); " align="center">
		<font style="font-size:12px; color:#FFCC00 ">³õÊ¼ÃÜÂë:admin@1234</font>
	  </td>
		<td>
			<img src="<%=cssURL%>/zhangyu_18.gif" alt="" width="84" height="34" border="0" usemap="#Map" style="cursor: hand;"></td>
		<td rowspan="3">
			<img src="<%=cssURL%>/zhangyu_19.gif" width="266" height="103" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="34" alt=""></td>
	</tr>
	<tr>
		<td colspan="3" rowspan="2">
			<img src="<%=cssURL%>/zhangyu_20.gif" width="276" height="69" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="61" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="<%=cssURL%>/zhangyu_21.gif" width="97" height="8" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="1" height="8" alt=""></td>
	</tr>
	<tr>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="97" height="1" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="301" height="1" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="14" height="1" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="72" height="1" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="120" height="1" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="84" height="1" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="266" height="1" alt=""></td>
		<td>
			<img src="<%=cssURL%>/·Ö¸ô·û.gif" width="443" height="1" alt=""></td>
		<td></td>
	</tr>
</table>
</form>
<!-- End ImageReady Slices -->
<map name="Map">
  <area shape="rect" coords="2,1,91,39" onClick="submitBox();">
</map>
</body>
</html>