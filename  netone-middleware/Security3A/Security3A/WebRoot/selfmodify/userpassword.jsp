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
	<td colspan='2'> ���ã�<font color='red'><%=loginname%></font> ����IPΪ<%= ip%> ��ǰ���ʵ�ϵͳʱ��Ϊ<%=time%> Ϊ�˱������ʻ��İ�ȫ�뼰ʱ���������룡</td>
	</tr>
	<tr>
	<td colspan='2'> &nbsp;</td>
	</tr>
	<tr>
		<td width="20%">
			��½�ʺ�
		</td>
		<td>
			<input type="text" name="name" value="<%=loginname%>" disabled="disabled"  class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="20%">
			�ǳƣ����֣�
		</td>
		<td>
			<input type="text" name="truenamex" value="${requestScope.truenamex }" class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="20%">
			�ⲿ����
		</td>
		<td>
			<input type="text" name="outemail" value="${requestScope.outemail }" class="textinput_td">
		</td>
	</tr>

	<tr>
		<td width="20%">
			������
		</td>
		<td>
			<input type="password" name="oldpass" value="" class="textinput_td">
		</td>
	</tr>
	<tr>
		<td>
			������
		</td>
		<td>
			<input type="password" name="newpass" value="" class="textinput_td"
				onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value)>
		</td>
	</tr>
	<tr>
		<td>
			�ٴ�����������
		</td>
		<td>
			<input type="password" name="pass" value="" class="textinput_td">
		</td>
	</tr>
		<tr>
		<td width="20%">
			��������
		</td>
		<td>
			<input type="checkbox" name="types" value='1' <c:if test="${clerk.types==1}">checked</c:if>/> ����Ҫ��������
		</td>
	</tr>
</table>
<table width="217" border="1" cellspacing="0" cellpadding="1"
	bordercolor="#cccccc" height="23" style='display:inline'>
	<tr align="center" bgcolor="#eeeeee">
		<td width="33%" id="strength_L">
			��
		</td>
		<td width="33%" id="strength_M">
			��
		</td>
		<td width="33%" id="strength_H">
			ǿ
		</td>
	</tr>
</table>
