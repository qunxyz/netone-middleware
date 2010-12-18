<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table width="95%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			хкт╠╫ги╚
		</td>
	</tr>
	<tr>
		<td width="40%">
			<select name="humanrole" class="table_select_page" size="5">
				<c:forEach items="${userroles}" var="role">
					<option>
						${role.name }
					</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
