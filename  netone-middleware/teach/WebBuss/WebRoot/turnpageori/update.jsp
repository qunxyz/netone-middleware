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
		<title>更新</title>
	</head>
	<body>
		<c:if test="${ope == 'done'}">
			<script type="text/javascript">
				alert("更新成功！")
				window.close();
				opener.search();
			</script>
		</c:if>

		<form action="<%=basePath%>/turnpageori/update?ope=done" name="form1"
			method="post">
			<input type="hidden" name='lsh' value='${stu.lsh}'>
			<br>
			<table border='1' align='left'>
				<tr>
					<td class="tdheadline">
						name
					</td>
					<td>
						<input type="text" name="name" value='${stu.name}'>
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						types
					</td>
					<td>
						<select name="types">
							<option id='Car' <c:if test="${stu.types=='Car'}">SELECTED</c:if>>
								Car
							</option>
							<option id='House' <c:if test="${stu.types=='House'}">SELECTED</c:if>>
								House
							</option>
							<option id='Antique' <c:if test="${stu.types=='Antique'}">SELECTED</c:if>>
								Antique
							</option>
							<option id='Stock' <c:if test="${stu.types=='Stock'}">SELECTED</c:if>>
								Stock
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						oriprice
					</td>
					<td>
						<input type="text" name="oriprice"  value="${stu.oriprice}"/>
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						curprice
					</td>
					<td>
						<input type="text" name="curprice" value="${stu.curprice}"/>
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						description
					</td>
					<td>
						<input type="text" name="description"  value="${stu.description}"/>
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

