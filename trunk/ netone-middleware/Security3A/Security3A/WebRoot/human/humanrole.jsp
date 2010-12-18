<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="95%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan='2'>角色管理</td>
	</tr>
	<tr>
		<td width="50%">
			<select name="humanrole" class="table_select_page" size="6"
				multiple="multiple">
			</select>
		</td>
		<td width="20%">
			<input type="hidden" name="roles" value="">
			<input type="button" value="添 加" onclick="humanroleadd('SYSROLE.SYSROLE');"
				class="butt">

			<br><br>
			<input type="button" value="删 除" onclick="humanroledrop();"
				class="butt">

			<br><br> <!--
			<input type="button" value="权 限"
				onclick="window.open('servlet/PurviewTreeXmlSvl?userid='+document.all.description.value);" class="butt">
                          -->		
</td>
	</tr>
</table>

<c:if test="${rolelist != null}">
	<script type="text/javascript">
   		var role = document.all.humanrole;
   		<c:forEach items="${rolelist}" var="UmsRole">
  			{
  				var option = new Option('${UmsRole.name}', '${UmsRole.id}');
  				role.add(option);
  			}
   		</c:forEach>
   		setroles();
   	</script>
</c:if>
