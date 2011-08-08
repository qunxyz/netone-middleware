<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>运行流程实例选择</title>
		<link href="<%=path%>/include/css/wf.css" rel="stylesheet"
			type="text/css">
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/workflow/runtime.js"></SCRIPT>
	</head>
	<body>
		<form method="post" action="">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<table align="center" style="width: 100%;">
				<tr>
					<td class="tdheadline">
						选择
					</td>
					<td class="tdheadline">
						流程名
					</td>
					<td class="tdheadline">
						启动时间
					</td>
					<td class="tdheadline">
						结束时间
					</td>
					<td class="tdheadline">
						状态
					</td>
					<td class="tdheadline">
						类型
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="center">
						<td>
							<input type="radio" name="radioid" value="${getCol.runtimeid}"
								size="10">
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.nameExt}
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.starttime}
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.endtime}
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.kind}
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.statusExt}
						</td>
					</tr>
				</c:forEach>
			</table>
			<table width="100" border="0" cellpadding="0" cellspacing="0"
				align="right">
				<tr>
					<Td width="24">
						<input type="button" value="流程调试" onclick="choiceprocess();"
							class="butt" />
					</Td>
				</tr>
			</table>
		</form>
	</body>
</html>
