<%@ page contentType="text/html; charset=GBK"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUser"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String flag = request.getParameter("flag");
	String id = request.getParameter("id");
	String chkid = request.getParameter("chkid");
	OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
	OnlineUser oluser = olmgr.getOnlineUser(request);
	String code = oluser.getBelongto();
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>�˻�ͬ��</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
	</head>
	<body>
		&nbsp;
		<script type="text/javascript">
			function f(){
				if(document.all.address.value==""){
					alert("Sevlet��ַ������Ϊ��!");
					return false;
				}
				form2.action=document.all.address.value;
				form2.submit();
			}
		</script>
		<form action="" method="post" name="form2">
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="1" id="lie_table">
				<tr>
					<td>
						ͬ������:
						<input type="hidden" name="flag" value="<%=flag%>">
						<input type="hidden" name="id" value="<%=id%>">
						<input type="hidden" name="chkid" value="<%=chkid%>">
						<input type="hidden" name="code" value="<%=code %>">
					</td>
					<td>
						<c:set scope="request" var="kkk" value="<%=flag%>"></c:set>
						<c:if test="${kkk=='byDept'}">����</c:if>
						<c:if test="${kkk=='byHuman'}">��Ա</c:if>
					</td>
				</tr>
				<tr>
					<td width="150">
						
						<select onchange="document.all.address.value=this.value">
							<option value="http://ip:port/pms/TaskSynSvl?code=<%=code %>">
								����ϵͳ
							</option>
							<option value="http://ip:port/Message/MessageUserSynSvl?code=<%=code %>">
								�ʼ�ϵͳ
							</option>
						</select>
					</td>
					<td>
						<input type="text" name="address"
							value="http://ip:port/pms/TaskSynSvl?code=<%=code %>"
							class="textinput_td" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" name="btns" value="ͬ ��" onclick="f();"
							class="butt">
						<input type="button" name="cancel" value="ȡ ��"
							onclick="window.close();" class="butt">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
