<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>创建</title>
	</head>
	<body>
		<c:if test="${ope == 'done'}">
			<script type="text/javascript">
				alert("新建成功！")
				opener.search();
			</script>
		</c:if>

		<form action="<%=basePath%>turnpage/create.do?ope=done" name="form1"
			method="post">
			<br>
			<table border='1' align='left'>
				<tr>
					<td class="tdheadline">
						STUNAME
					</td>
					<td>
						<input type="text" name="stuname" value='${stu.stuname}'>
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						DESCRIPTION
					</td>
					<td>
						<input type="text" name="description" value='${stu.description}'>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type='submit' value='  提  交  '>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

