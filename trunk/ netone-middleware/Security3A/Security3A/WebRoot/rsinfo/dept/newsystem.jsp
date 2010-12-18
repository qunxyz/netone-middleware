<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>新建系统</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/rsinfo/dept/left.js"></script>

	</head>
	<body>
		<c:if test="${newapp == 'y'}">
			<script type="text/javascript">
				var frame = opener.window.parent.frames["left"];
				frame.location.href="DepartmentTree.do";
				window.close();
			</script>
		</c:if>
		<form action="" name="form1" method="post">
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="1" id="lie_table">
				<tr style='display:none'>
					<td width="15%">
						名称
					</td>
					<td>
						<input type="text" name="appnaturalname" value=""
							class="textinput_td" />
					</td>
				</tr>
				<tr>
					<td width="15%">
						中文
					</td>
					<td>
						<input type="text" name="appname" value="" class="textinput_td" />
					</td>
				</tr>
				<tr>
					<td width="15%">
						描述
					</td>
					<td>
						<input type="text" name="description" value=""
							class="textinput_td" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" value="确定" onclick="doaddapp();" class="butt" />
				<input type="button" value="取消" class="butt"
					onclick="javascript:window.close();" />
			</div>
		</form>
	</body>
</html>
