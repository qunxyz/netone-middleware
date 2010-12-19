<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String runtimeid = (String) request.getAttribute("runtimeid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>流程相关数据</title>

		<link href="<%=path%>/include/css/wf.css" rel="stylesheet"
			type="text/css">
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/workflow/runtime.js"></SCRIPT>
	</head>
	<body>
		<form method="post" action="">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<input name="runtimeid" type="hidden" value=<%=runtimeid%>>
			<table align="center" style="width: 100%;">
				<tr>
					<td class="tdheadline">
						数据名称
					</td>
					<td class="tdheadline">
						数据标识
					</td>
					<td class="tdheadline">
						当前值
					</td>

				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="center">

						<input type="hidden" name="varcode" value="${getCol.varcode}"
							size="10">

						<td height="21" bgcolor="#FFFFFF" width="20%">
						<c:if test="${getCol.nameExt==null}">${getCol.datafieldid}</c:if>
						<c:if test="${getCol.nameExt!=null}">${getCol.nameExt}</c:if>
							
						</td>
						<td height="21" bgcolor="#FFFFFF" width="20%">
							${getCol.datafieldid}
						</td>
						<td height="21" bgcolor="#FFFFFF" align="left" width="60%">
								${getCol.extendattribute}
						</td>
					</tr>
				</c:forEach>
			</table>
			<table width="100" border="0" cellpadding="0" cellspacing="0"
				align="right">
				<tr>
					<Td width="24">
						<input type="button" value="更新数据" onclick="updateData();"
							class="butt" <c:if test="${permission<7}">disabled</c:if>/>
							
							
					</Td>
				</tr>
			</table>
		</form>
	</body>
</html>
