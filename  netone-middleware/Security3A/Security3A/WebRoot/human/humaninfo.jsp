<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<input type="hidden" name="org.apache.struts.taglib.html.TOKEN"
	value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>" />
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="1" id="lie_table">
	<input type='hidden' value='${actionurl}' name='actionurl'/>
	<tr>
		<td width="15%">
			姓名
		</td>
		<td>
			<c:if test="${flag == 'copy'}">
				<input type="text" name="name" value="" class="textinput_tdx">
			</c:if>
			<c:if test="${flag == 'modify'}">
				<input type="text" name="name" value="${clerk.name}"
					class="textinput_tdx">
			</c:if>
			<c:if test="${flag == 'add'}">
				<input type="text" name="name" value="${clerk.name}"
					class="textinput_tdx">
			</c:if>
			<input type="hidden" name="extendattribute"
				value="${clerk.extendattribute}">
		</td>
	</tr>
	<tr>
		<c:if test="${flag == 'modify'}">
			<td width="15%">
				帐号
			</td>
			<td>
				<input type="text" name="description" value="${clerk.description}"
					class="textinput_tdx" readonly="readonly">
				<input type="hidden" name="naturalname" value="${clerk.naturalname}">
				<input type="hidden" name="password" value="${clerk.password}">
			</td>
		</c:if>
		<c:if test="${flag == 'copy'}">
			<td width="15%">
				用户帐号
			</td>
			<td>
				<input type="text" name="naturalname" value="" class="textinput_tdx">
			</td>
		</c:if>
		<c:if test="${flag == 'add'}">
			<td width="15%">
				用户帐号
			</td>
			<td>
				<input type="text" name="naturalname" value="${clerk.naturalname}"
					class="textinput_tdx" style="ime-mode: disabled">
				<input type="hidden" name="description" value="${clerk.description}">
		</c:if>
	</tr>
	<tr>
		<td width="15%">
			组织机构
		</td>
		<td>
			<c:if test="${flag == 'copy'}">
				<input type="text" name="faxNO" value="${clerk.faxNO}"
					class="textinput_tdx" readonly="readonly">
				<input type="hidden" name="newhiddenid" value="${clerk.deptment}">
			</c:if>
			<c:if test="${flag == 'modify'}">
				<input type="text" name="faxNO" value="${clerk.faxNO}"
					class="textinput_tdx" readonly="readonly">
				<input type="hidden" name="hiddenid" value="${clerk.deptment}">
				<input type="hidden" name="newhiddenid" value="">
			</c:if>
			<c:if test="${flag == 'add'}">
				<input type="text" name="faxNO" value="${clerk.faxNO}"
					class="textinput_tdx" readonly="readonly">
				<input type="hidden" name="newhiddenid" value="${clerk.deptment}">
			</c:if>
			<a href='javascript:humangroupadd();' style='display:none'><font color='blue'>选
					择</font> </a>
		</td>
	</tr>
	<tr style='display:none'>
		<td width="15%">
			组 别
		</td>
		<td>

			<select name="company">
				<option value=""></option>
				<c:forEach items="${deptList}" var="dept">
					<option value="${dept.naturalname}"
						${clerk.company==dept.naturalname?"selected":""}>
						${dept.name}
					</option>
				</c:forEach>
			</select>
			项目组 &nbsp;&nbsp;
			<select name="province">
				<option value=""></option>
				<c:forEach items="${itemList}" var="team">
					<option value="${team.naturalname}"
						${clerk.province==team.naturalname?"selected":""}>
						${team.name}
					</option>
				</c:forEach>
			</select>
			开发组
		</td>
	</tr>

	<tr>
		<td width="15%">
			手机
		</td>
		<td>
			<input type="text" name="phoneNO" value="${clerk.phoneNO}"
				class="textinput_tdx">
		</td>
	</tr>
	<tr>
		<td width="15%">
			邮件
		</td>
		<td>
			<input type="text" name="email" value="${clerk.email}"
				class="textinput_tdx">
		</td>
	</tr>
	<tr>
		<td width="15%">
			备注
		</td>
		<td>
			<input type="text" name="remark" value="${clerk.remark}"
				class="textinput_tdy">
		</td>
	</tr>
</table>
