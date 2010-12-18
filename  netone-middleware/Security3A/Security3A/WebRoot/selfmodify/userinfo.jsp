<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" name="department0" value="${clerk.deptment}">
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="1" id="lie_table">
	<tr>
		<td width="15%">
			姓名
		</td>
		<td>
			<input type="text" name="name" value="${clerk.name}"
				readonly="readonly" style="color: #999999" class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="15%">
			帐号
		</td>
		<td>
			<input type="text" name="description" value="${clerk.description}"
				class="textinput_td" style="color: #999999" readonly="readonly">
			<input type="hidden" name="naturalname" value="${clerk.naturalname}">
			<input type="hidden" name="password" value="${clerk.password}">
		</td>
	</tr>
	<tr>
		<td width="15%">
			组织机构
		</td>
		<td>
			<input type="text" name="faxNO" value="${clerk.faxNO}"
				class="textinput_td" style="color: #999999" readonly="readonly">
			<!-- 
			<select name="faxNO" class="table_select_page" style="display:none">
				<c:forEach items="${itemList}" var="item">
					<option value="${item.id}" ${clerk.faxNO==item.id?"selected":""}>
						${item.name}
					</option>
				</c:forEach>
			</select>
			-->
		</td>
	</tr>
	<tr>
		<td width="15%">
			组 别
		</td>
		<td>
			<select name="company" disabled="disabled">
				<c:forEach items="${deptList}" var="dept">
					<option value="${dept.naturalname}"
						${clerk.company==dept.naturalname?"selected":""}>
						${dept.name}
					</option>
				</c:forEach>
			</select>
			开发组 &nbsp;&nbsp;

			<select name="province" disabled="disabled">
				<c:forEach items="${itemList}" var="team">
					<option value="${team.naturalname}"
						${clerk.province==team.naturalname?"selected":""}>
						${team.name}
					</option>
				</c:forEach>
			</select>
			项目组
		</td>
	</tr>


	<tr>
		<td width="15%">
			移动电话
		</td>
		<td>
			<input type="text" name="phoneNO" value="${clerk.phoneNO}"
				class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="15%">
			邮件
		</td>
		<td>
			<input type="text" name="email" value="${clerk.email}"
				class="textinput_td">
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
