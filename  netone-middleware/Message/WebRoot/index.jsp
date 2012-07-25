<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="rsinclude/css/css.css" rel="stylesheet" type="text/css">
	</head>
	<body  LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0>
		<table width="1003" height="100%" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td height="10" colspan="3" valign="top" bgcolor="#FFFFFF">
					<iframe id="InfoPage1" marginwidth=0 marginheight=0
						src="main_head.jsp" valign="middle" frameborder=0 width="100%"
						height="100%" scrolling="no" resize="no"></iframe>
				</td>
			</tr>
			<tr>
				<td width="214" valign="top" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">

						<tr>
							<td>
								<table width="60%" border="0" align="center" cellpadding="0"
									cellspacing="3">
									<tr>

										<td>
										<img src='images/iconTask.gif'>
											<a
												href="javascript: InfoPage1.changeframe('<%=path%>/bussform/bussMessage.do'); undefined;">个人消息</a>
										</td>
									</tr>
									<tr>

										<td>
										<img src='images/iconJob.gif'>
											<a
												href="javascript: InfoPage1.changeframe('<%=path%>/bussform/MsgSend.do'); undefined;">消息管理</a>
										</td>
									</tr>
									<tr>

										<td>
										<img src='images/iconReport.gif'>
											<a
												href="javascript: InfoPage1.changeframe('<%=path%>/bussform/SendMessage.do'); undefined;">发送消息</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				
				
				
				<td width="7" valign="top">
					<img src="images/goleft.gif" width="7" height="48">
				</td>
				<td align="center" valign="top" width="80%">

					<table width="98%" height="95%" border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<iframe name="mainFrame" marginwidth=0 marginheight=0 src=""
									valign="middle" frameborder=0 width="100%" height="100%"
									scrolling="yes" resize="auto"></iframe>
							</td>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
