<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>流程文件列表</title>
		<link href="<%=path%>/include/css/wf.css" rel="stylesheet"
			type="text/css">
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/workflowtrack/action/newSubflowExtend.js"></SCRIPT>

	</head>
	<body>
		${tip}
		<form action="" name="form1" method="post">
			<input type="hidden" name="systemid" />
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<table align="center" style="width: 100%;">
				<tr>
					<td bgcolor="#CCCCFF">
						选择
					</td>
					<td bgcolor="#CCCCFF">
						流程名
					</td>
					<td bgcolor="#CCCCFF">
						中文名
					</td>
					<td bgcolor="#CCCCFF">
						创建时间
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					
						<tr class="td-02" align="center">
							<td>

								<input type="radio" name="processid" id='processid'
									value="${getCol.extendattribute}">
								<input type="hidden" name="processname" value="${getCol.name}">
							</td>
							<td height="21">
								${getCol.naturalname}
							</td>
							<td height="21" bgcolor="#FFFFFF">
								${getCol.name}
							</td>
							<td height="21" bgcolor="#FFFFFF">
								${getCol.created}
							</td>
						</tr>
					
				</c:forEach>
			</table>
			<table align="center" style="width: 100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width: 100%">
						同步子流程
						<input type="checkbox" name="choiceSync" value="1" />
						<input type="button" value="选择流程"
							onclick="(new event()).submitSubflow();" />
					</td>
				</tr>
			</TABLE>
		</form>
	</body>

</html>
