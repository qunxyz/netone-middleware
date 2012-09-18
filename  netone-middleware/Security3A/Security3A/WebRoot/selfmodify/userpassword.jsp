<%@ page language="java" pageEncoding="GBK"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr"%>
<%@page import="java.sql.Timestamp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%			OnlineUserMgr oum = new DefaultOnlineUserMgr();
			String loginname = oum.getOnlineUser(request).getLoginname(); 
			String ip=request.getRemoteHost();
			String time=(new Timestamp(System.currentTimeMillis())).toString();
%>



<table width="95%" border="0" cellpadding="0" cellspacing="0"
	id="lie_table">
	<tr>
	<td colspan='2'> 您好！<font color='red'><%=loginname%></font> 您的IP为<%= ip%> 当前访问的系统时间为<%=time%> 为了保障你帐户的安全请及时设置新密码！</td>
	</tr>
	<tr>
	<td colspan='2'> &nbsp;</td>
	</tr>
	<tr>
		<td width="20%">
			登陆帐号
		</td>
		<td>
			<input type="text" name="name" value="<%=loginname%>" disabled="disabled"  class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="20%">
			昵称（名字）
		</td>
		<td>
			<input type="text" name="truenamex" value="${requestScope.truenamex }" class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="20%">
			外部邮箱
		</td>
		<td>
			<input type="text" name="outemail" value="${requestScope.outemail }" class="textinput_td">
		</td>
	</tr>

	<tr>
		<td width="20%">
			旧密码
		</td>
		<td>
			<input type="password" name="oldpass" value="" class="textinput_td">
		</td>
	</tr>
	<tr>
		<td>
			新密码
		</td>
		<td>
			<input type="password" name="newpass" value="" class="textinput_td"
				onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value)>
		</td>
	</tr>
	<tr>
		<td>
			再次输入新密码
		</td>
		<td>
			<input type="password" name="pass" value="" class="textinput_td">
		</td>
	</tr>
		<tr>
		<td width="20%">
			短信提醒
		</td>
		<td>
			<input type="checkbox" name="types" value='1' <c:if test="${clerk.types==1}">checked</c:if>/> 不需要短信提醒
		</td>
	</tr>
</table>
<table width="217" border="1" cellspacing="0" cellpadding="1"
	bordercolor="#cccccc" height="23" style='display:inline'>
	<tr align="center" bgcolor="#eeeeee">
		<td width="33%" id="strength_L">
			弱
		</td>
		<td width="33%" id="strength_M">
			中
		</td>
		<td width="33%" id="strength_H">
			强
		</td>
	</tr>
</table>
