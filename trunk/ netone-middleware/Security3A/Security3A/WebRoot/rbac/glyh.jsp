<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" name="roleusers" value="">

<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" id="lie_table">
	<tr>
		<td align="left" width="80%">
			授予用户:
			<select name="select_roleusers" style="width: 80%" size="5" multiple="multiple">
				<c:forEach items="${roleuserlist}" var="clerk">
					<option value="${clerk.description}">
						${clerk.name}
					</option>
				</c:forEach>
			</select>
		</td>
		<td width="20%">
			<input type="button" value="添加用户" class="butt"
				onclick="openuserselect('${extendattribute}');">
			<br>
			<br>
			<input type="button" value="删除用户" onclick="selectdrop();"
				class="butt">
		</td>
	</tr>
</table>
