<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="95%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan='2'>群组管理</td>
	</tr>
	<tr>
		<td width="50%">
			<select name="humanteam" class="table_select_page" size="5"
				multiple="multiple">
			</select>
		</td>
		<td width="20%">
			<input type="hidden" name="teams" value="">
			<input type="button" value="添 加" onclick="humanteamadd('SYSTEAM.SYSTEAM');"
				class="butt">

			<br><br>
			<input type="button" value="删 除" onclick="humanteamdrop();"
				class="butt">

			<br><br> <!--
			<input type="button" value="权 限"
				onclick="window.open('servlet/PurviewTreeXmlSvl?userid='+document.all.description.value);" class="butt">
                          -->		
</td>
	</tr>
</table>

<c:if test="${teamlist != null}">
	<script type="text/javascript">
   		var humanteam = document.all.humanteam;
   		<c:forEach items="${teamlist}" var="Umsgroup">
  			{
  				var option = new Option('${Umsgroup.name}', '${Umsgroup.naturalname}');
  				humanteam.add(option);
  			}
   		</c:forEach>
   		setteams();
   	</script>
</c:if>
