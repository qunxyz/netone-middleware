<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" name="department0" value="${clerk.deptment}">
<table width="95%" border="0" align="center" cellpadding="0"
	cellspacing="1" id="lie_table">
	<tr>
		<td width="15%">
			����
		</td>
		<td>
			<input type="text" name="name" value="${clerk.name}"
				readonly="readonly" style="color: #999999" class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="15%">
			�ʺ�
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
			��֯����
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
			�� ��
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
			������ &nbsp;&nbsp;

			<select name="province" disabled="disabled">
				<c:forEach items="${itemList}" var="team">
					<option value="${team.naturalname}"
						${clerk.province==team.naturalname?"selected":""}>
						${team.name}
					</option>
				</c:forEach>
			</select>
			��Ŀ��
		</td>
	</tr>


	<tr>
		<td width="15%">
			�ƶ��绰
		</td>
		<td>
			<input type="text" name="phoneNO" value="${clerk.phoneNO}"
				class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="15%">
			�ʼ�
		</td>
		<td>
			<input type="text" name="email" value="${clerk.email}"
				class="textinput_td">
		</td>
	</tr>
	<tr>
		<td width="15%">
			��ע
		</td>
		<td>
			<input type="text" name="remark" value="${clerk.remark}"
				class="textinput_tdy">
		</td>
	</tr>
</table>
