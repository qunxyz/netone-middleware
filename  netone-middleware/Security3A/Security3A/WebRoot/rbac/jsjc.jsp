<%@ page language="java" pageEncoding="GBK"
	contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="1" id="lie_table">
	<tr>
		<td width="15%">
			继承的角色
		</td>
		<td>
			<input type="hidden" name="parentId" value="${parent.id}">
			<input type="text" name="parentName" readonly="readonly"
				value="${parent.name}" class="textinput_td">
			<a href="javascript:roleselect('SYSROLE.SYSROLE');"><font color="blue">选 择</font>
			</a>&nbsp;
			<a
				href="javascript:document.all.parentId.value=' ';document.all.parentName.value='';undefined;"><font
				color='blue'>清 除</font>
		</td>
	</tr>
</table>
