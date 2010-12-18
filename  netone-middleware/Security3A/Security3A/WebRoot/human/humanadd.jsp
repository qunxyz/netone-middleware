<%@ page language="java" pageEncoding="gbk"%>
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
		<title>人员添加</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link type="text/css" rel="stylesheet" href="css/css.css">
		<script type="text/javascript" src="include/js/human/humanadd.js"></script>
		<script type="text/javascript" src="include/js/human/humanmodify.js"></script>
		<script type="text/javascript" src="include/js/human/humancopy.js"></script>
		<script type="text/javascript" src="include/js/util.js"></script>
	</head>

	<body style="margin: 0px">
		${paramMap.alertMsg}
		<c:if test="${result == 'y'}">
			<script type="text/javascript">
				if(window.opener && !window.opener.closed){
					if(opener.document.forms[0]){
						opener.document.forms[0].submit();
					}
				}
				window.close();
			</script>
		</c:if>
		<c:if test="${result == 'n'}">
			<script type="text/javascript">
				window.close();
			</script>
		</c:if>
		<form action="<%=path%>/humanAdd.do" name="form1" method="post">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="6" id="table">
				<tr>
					<td align="center" valign="top" bgcolor="#fafdff">
						<table width="96%" height="100%" border="0" cellpadding="0"
							cellspacing="6">
							<tr>
								<td align="center" valign="top">
									<div id="div_tab1" style="width: 100%">
										<jsp:include flush="true" page="humaninfo.jsp"></jsp:include>
									</div>
									<br>
									<div id="div_tab2" style="width: 100%">
										<jsp:include flush="true" page="humanrole.jsp"></jsp:include>
									</div>
									<br>
									<table id="tablebutton">
										<tr>
											<td>
												<c:if test="${flag == 'add'}">
													<input type="button" value="保 存" onclick="humanaddsave();"
														class="butt">
												</c:if>
												<c:if test="${flag == 'modify'}">
													<input type="button" value="保 存"
														onclick="humanmodifysave();" class="butt">
												</c:if>
												<c:if test="${flag == 'copy'}">
													<input type="button" value="保 存" onclick="humancopysave();"
														class="butt">
												</c:if>
												<input type="button" value="取 消"
													onclick="javascript:window.close();" class="butt">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>

		<c:if test="${state == 'y'}">
			<script type="text/javascript">
				changeTab(document.all.tab2);
			</script>
		</c:if>

	</body>
</html>
