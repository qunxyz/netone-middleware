<%@ page language="java" pageEncoding="GBK"
	contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page import="oe.frame.web.form.RequestUtil" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>角色信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css" />
		<link rel="stylesheet" type="text/css" href="css/dhtmlXTree.css">
		<script type="text/javascript" src="include/js/tab.js"></script>
		<script type="text/javascript" src="include/js/rbac/rbacadd.js"></script>
		<script type="text/javascript" src="include/js/dhtmlXCommon.js"></script>
		<script type="text/javascript" src="include/js/dhtmlXTree.js"></script>
		<script type="text/javascript" src="include/js/util.js"></script>
		<script type="text/javascript" src="include/js/prototype.js"></script>
	</head>

	<body style="margin: 0px">
		${paramMap.alertMsg}

		<c:if test="${result=='y'}">
			<script type="text/javascript">
				window.close();
				opener.document.forms[0].submit();
			</script>
		</c:if>
		<c:if test="${result=='n'}">
			<script type="text/javascript">
				window.close();
			</script>
		</c:if>
		<c:if test="${flag == 'add'}">
			<c:set var="action" value="rbac/rbacAdd.do?task=save"></c:set>
		</c:if>
		<c:if test="${flag == 'modify'}">
			<c:set var="action" value="rbac/rbacModify.do?task=save"></c:set>
		</c:if>

		<form action="${action}" method="post" name="form1">

			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="6" id="table">
				<tr>
					<td align="center" valign="top" bgcolor="#fafdff">
						<br>
						<table width="90%" border="0" align="center" cellpadding="0"
							cellspacing="1" id="">
							<tr>
								<td>
									<div id="div_tab1" style="width: 100%">
										<jsp:include flush="true" page="rbacadd.jsp"></jsp:include>
									</div>
									<br>
									<div id="div_tab2" style="width: 100%">
										<jsp:include flush="true" page="jsjc.jsp"></jsp:include>
									</div>
									<br>
									<div id="div_tab3" style="width: 100%">
										<c:set var="PurviewType" value="<%=oe.security4a.web.rbac.RbacIndexAction.PurviewType%>"></c:set>
										<c:if test="${PurviewType=='1'}"> 
											<jsp:include flush="true" page="jssq.jsp"></jsp:include>
										</c:if>
										<c:if test="${PurviewType=='2'}">
											<jsp:include flush="true" page="jssq2.jsp"></jsp:include>
										</c:if>
									</div>
									<br>
									<div id="div_tab4" style="width: 100%">
										<jsp:include flush="true" page="glyh.jsp"></jsp:include>
									</div>
									<br>
									<div style="width: 95%;margin-top: 15px" align="center">
										<c:if test="${flag == 'add'}">
											<input type="button" value="提 交" class="butt"
												onclick="submitadd();">
										</c:if>
										<c:if test="${flag == 'modify'}">
											<input type="button" value="提 交" class="butt"
												onclick="submitmodify();">
										</c:if>
										<input type="button" value="取 消"
											onclick="javascript:window.close();" class="butt">
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
