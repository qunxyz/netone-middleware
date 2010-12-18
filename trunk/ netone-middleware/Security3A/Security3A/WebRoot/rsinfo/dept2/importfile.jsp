<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page import="oe.frame.web.form.RequestUtil" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	RequestUtil.setParamMapToRequest(request);
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>导入</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/rsinfo/dept2/right.js"></script>

	</head>
	<body style="margin: 30px">
		${paramMap.alertMsg}
		<c:if test="${ImportSuccess == 'y'}">
			<script type="text/javascript">
				alert("导入成功！")
				window.close();
			</script>
		</c:if>
		<c:if test="${ImportSuccess == 'n'}">
			<script type="text/javascript">
				alert("导入失败！")
				window.close();
			</script>
		</c:if>
		<html:form action="/DepartmentInput?task=uploadfile" method="post" enctype="multipart/form-data" styleId="form1">
			<input type="hidden" name="id" value="${paramMap.id}" />
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr>
					<td align="center">
						<html:file property="formFile" styleId="upfile"></html:file>
						<input type="button" value="导 入" onclick="filecreate();"
							class="butt">
						<input type="button" value="取 消" onclick="javascript:window.close();"
							class="butt">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
