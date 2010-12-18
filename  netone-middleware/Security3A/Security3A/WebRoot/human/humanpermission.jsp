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

		<title>权限树图</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<link rel="stylesheet" type="text/css" href="css/dhtmlXTree.css">
		<script type="text/javascript" src="include/js/dhtmlXCommon.js"></script>
		<script type="text/javascript" src="include/js/dhtmlXTree.js"></script>
		<script type="text/javascript" src="include/js/util.js"></script>
		<script type="text/javascript">
		
		</script>
	</head>

	<body>
		<form action="" method="post">
			<table width="95%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						权限列表
					</td>
				</tr>
				<tr>
					<td>
						<select name="humangroup" class="table_select_page" size="40">
							<c:forEach items="${usergroups}" var="group">
								<option>
									${group}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
