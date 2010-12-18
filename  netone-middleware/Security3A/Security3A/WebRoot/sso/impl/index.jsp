<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="css/css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path %>/include/js/prototype.js"></script>
		<script type="text/javascript">
			function changeFrame(url){
			$('mainFrame').src = url;
			}
		</script>
	</head>
	<body BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0>
		<table width="1003" height="100%" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td height="50" colspan="3"  bgcolor="#FFFFFF" width="50%" align='left'>
                                   
                                   &nbsp;&nbsp;&nbsp;&nbsp;<strong><font color='red' size='5'>NETONE 

SECURITY4A</font></strong>
<hr>
				</td>
			</tr>
			<tr>
				<td width="214" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="60%" border="0" align="center" cellpadding="0"
									cellspacing="3">

									<tr>
										<td>
											<img src="images/icon_01_user_add.gif" width="16" height="16">
										</td>
										<td>
											<a
												href="javascript:void(0);" onClick="changeFrame('<%=path%>/humanIndex.do?id=');">
												用户管理</a>
										</td>
									</tr>
									<tr>
										<td>
											<img src="images/icon_12_user information.gif" width="16"
												height="16">
										</td>
										<td>
											<a
												href="javascript:void(0);" onClick="changeFrame('<%=path%>/selfmodify/selfModify.do?flag=me');">个人信息</a>
										</td>
									</tr>
									<tr>
										<td>
											<img src="images/icon_03_user edit.gif" width="16"
												height="16">
										</td>
										<td>
											<a
												href="javascript:void(0);" onClick="changeFrame('<%=path%>/selfmodify/selfModify.do?flag=pass');">密码修改</a>
										</td>
									</tr>

									<tr>
										<td>
											<img src="images/icon_07_department admin.gif" width="16"
												height="16">
										</td>
										<td>
											<a
												href="javascript:void(0);" onClick="changeFrame('<%=path%>/rsinfo/dept/frameIndex.jsp');">部门人员</a>
										</td>
									</tr>

									<tr>
										<td>
											<img src="images/item.gif" width="16" height="16">
										</td>
										<td>
											<a
												href="javascript:void(0);" onClick="changeFrame('<%=path%>/rsinfo/dept2/frameIndex.jsp');">部门角色</a>
										</td>
									</tr>


									<tr>
										<td>
											<img src="images/icon_08_role admin.gif" width="16"
												height="16">
										</td>
										<td>
											<a
												href="javascript:void(0);" onClick="changeFrame('<%=path%>/rbac/rbacIndex.do?appid=1&source=');">角色管理</a>
										</td>
									</tr>

									<tr>
										<td>
											<img src="images/icon_11_system record.gif" width="16"
												height="16">
										</td>
										<td>
											<a
												href="javascript:void(0);" onClick="changeFrame('<%=path%>/optrlog/index.jsp');">系统日志</a>
										</td>
									</tr>

									<tr>
										<td>
											<img src="images/icon_tuichu.gif" width="16" height="16">
										</td>
										<td>
											<a href="<%=path%>/sso/impl/Logout.do">注销</a>
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
								<iframe name="mainFrame" id="mainFrame" marginwidth=0 marginheight=0 src="<%=path%>/humanIndex.do?id="
									valign="middle" frameborder=0 width="100%" height="100%"
									scrolling="yes" resize="auto"></iframe>
							</td>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
