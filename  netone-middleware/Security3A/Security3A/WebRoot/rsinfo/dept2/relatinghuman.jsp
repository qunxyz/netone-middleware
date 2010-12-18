<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>关联用户</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/rsinfo/dept2/right.js"></script>
	</head>

	<body>
		<form action="" name="form1" method="post">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="6" id="table">
				<tr>
					<td align="center" valign="top" bgcolor="#fafdff">
						<br>
						<table width="90%" border="0" align="center" cellpadding="0"
							cellspacing="1" id="lie_table">
							<tr>
								<td>
									关联用户
								</td>
							</tr>
							<tr>
								<td>
									<select name="users" multiple="multiple" size="10"
										style="width:100%" ondblclick="lookuser();">
										<c:forEach items="${listinfo}" var="clerk">
											<option value="${clerk.description}">
												${clerk.name}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
