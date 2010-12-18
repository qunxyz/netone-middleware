<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" name="org.apache.struts.taglib.html.TOKEN"
	value="${sessionScope['org.apache.struts.action.TOKEN']}" />

<input type="hidden" name="chkid" value="${paramMap.chkid}" />
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="1" id="lie_table">
	<tr>
		<td width="15%">
			角色名称
		</td>
		<td>
			<c:if test="${flag == 'modify'}">
				<input type="text" name="naturalname" value="${umsRole.naturalname}"
					class="textinput_td" readonly="readonly">
			</c:if>
			<c:if test="${flag == 'add'}">
				<input type="text" name="naturalname" value="${umsRole.naturalname}"
					class="textinput_td">
			</c:if>
		</td>
	</tr>
	<tr>
		<td>
			中文名称
		</td>
		<td>
			<input type="text" name="name" value="${umsRole.name}"
				class="textinput_td">
		</td>
	</tr>
	<tr>
		<td>
			角色分类
		</td>
		<td>
			<c:forEach items="${list}" var="belong">
				<c:if test="${belong.naturalname == umsRole.belongingness}">
					<c:set var="belonging_name" value="${belong.name}"></c:set>
				</c:if>
			</c:forEach>
			
			<input type="text" name="belonging_name" readonly="readonly"
				class="textinput_td" value="${belonging_name}"> <a href='javascript:belongadd()' ><font color='blue'>选 择</font></a>
		
			<input type="hidden" name="belonging"
				value="${umsRole.belongingness  }">
			<input type="hidden" name="oldbelonging"
				value="${umsRole.belongingness  }">
		</td>
	</tr>
	<tr>
		<td>
			角色描述
		</td>
		<td>
			<input type="text" name="description" value="${umsRole.description}"
				class="textinput_td">
		</td>

	</tr>
</table>
