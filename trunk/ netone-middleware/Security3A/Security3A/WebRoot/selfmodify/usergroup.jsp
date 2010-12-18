<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${task==null }">
<script>
	window.location.href="../selfmodify/selfModify.do?task=permission";
</script>
</c:if>
<table width="95%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			权限列表
		</td>
	</tr>
	<tr>
		<td>
			<select name="humangroup" class="table_select_page" size="10">
				<c:forEach items="${usergroups}" var="group">
					<option>
						${group}
					</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
