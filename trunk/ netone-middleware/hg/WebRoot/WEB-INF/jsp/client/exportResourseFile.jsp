<%@ page language="java"
	contentType="application/vnd.ms-excel; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	//response.setContentType("application/vnd.ms-excel");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="application/vnd.ms-excel; charset=utf-8" />
		<title>资源导出报表</title>
	</head>
	<body>
		<table border="1" cellPadding="0" borderColor="#000000"
			style="border-collapse: collapse;">
			<tr>
				<td  align="center" nowrap="nowrap">
					中文名称
				</td>
				<td  align="center" nowrap="nowrap">
					名称
				</td>
				<td  align="center" nowrap="nowrap">
					菜单URL
				</td>
				<td  align="center" nowrap="nowrap">
					父节点名称
				</td>
			</tr>
			<c:forEach items="${list}" var="detail1">
				<tr>
					<td  align="left"nowrap="nowrap">
						${detail1['name']}
					</td>
					<td  align="left" nowrap="nowrap">
						${detail1['naturalName']}
					</td>
					<td  align="left" nowrap="nowrap">
						1
					</td>
					<td  align="left" nowrap="nowrap">
						${detail1['prNaturalName']}
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
