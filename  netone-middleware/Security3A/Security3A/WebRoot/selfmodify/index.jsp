<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
		<title>个人信息修改</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript"
			src="include/js/selfmodify/selfmodify.js"></script>
		<script type="text/javascript" src="include/js/util.js"></script>
	</head>
	<body>

		${paramMap.alertMsg}

		<form action="selfmodify/selfModify.do" method="post" name="form1">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="6">
				<tr>
					<td align="center" valign="top">
						<c:if test="${flag=='me'}">
							<div id="div_tab1" style="width: 100%">
								<jsp:include flush="true" page="userinfo.jsp"></jsp:include>
							</div>
							<br>
							<div id="div_tab2" style="width: 100%">
								<jsp:include flush="true" page="userrole.jsp"></jsp:include>
							</div>
							<br>
							<div id="div_tab3" style="width: 100%">
								<input type="button" value="查看权限" onclick="window.open('usergroup.jsp');" class="butt">
							</div>
							<br>
						</c:if>
						<c:if test="${flag=='pass' || flag==''}">
							<div id="div_tab4" style="width: 100%">
								<jsp:include flush="true" page="userpassword.jsp"></jsp:include>
							</div>
							<div id="buttons" style="width: 100%;margin-top: 15px"
								align="center">
								<input type="hidden" name="task" value="">
								<input type="button" value="提 交" onclick="modify();"
									class="butt">
								<input type="button" value="导 出" onclick="exports();"
									class="butt" style="display:none">
							</div>
						</c:if>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
