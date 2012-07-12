<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Login page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	*{
		font-size:12px;
		padding:0;
		margin:0 auto;
		font-family:"arial","微软雅黑","宋体";
	}
	img {
		border:0;
	}
	ol,ul{
		list-style:none;
		margin:0;
		padding:0;
	}
	table{
		border-collapse:collapse;
		border-spacing:0;
	}
	body
	{
		text-align:center;
	}
	#bgimg
	{
		position:relative;
	}
	#myf
	{
		position:absolute;
		top:293px;
		left:430px;
	}
	</style>

  </head>
  
  <body>
  	<div id="bgimg">
	  	<img alt="" src="workflow/images/log_top.png" />
	    <img alt="" src="workflow/images/log_bottom.png" />
    
    <div id="myf">
    	<form action="http://127.0.0.1:81/Security3A/login" method="post">
			<input type="hidden" name="gotourl" value="http://127.0.0.1:81/issx/workflow/">
			<input type="hidden" name="todo" value="addtoken">
 
			<table cellpadding="2" cellspacing="2"  style="font-size:12px">
				<tr>
					<td colspan="3">
						
							<font color="red"></font>
						
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
							onClick="window.open('http://127.0.0.1:81/Security3A/human/HumanRegeditSelf.jsp','_blank','height=650, width=650, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');" />
 
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
 
 
		</form>
    </div>
    
    </div>
  </body>
</html>
