<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<base href="<%=basePath%>">

	<title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/css.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="include/js/rsinfo/dept2/right.js"></script>
</head>

<body>
	${paramMap.alertMsg}
	<c:if test="${result == 'y'}">
		<script type="text/javascript">
			opener.afterCreate();
			window.close();
		</script>
	</c:if>
	<html:form action="/RSUpdateLoad" method="post" enctype="multipart/form-data" styleId="form1">
		<input type="hidden" name="dirid" value="${paramMap.dirid}">
		<input type="hidden" name="appid" value="${paramMap.appid}">
		<table width="96%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="lie_table">
			<tr>
				<td>
					上传附件
				</td>
				<td width="80%">
					<html:file property="formFile" styleId="files" onchange="changefile();"></html:file>
				</td>
			</tr>
			<tr>
				<td>
					filename
				</td>
				<td>
					<input type="text" name="filename" value="" class="textinput_td">
				</td>
			</tr>
			<tr>
				<td>
					naturalname
				</td>
				<td>
					<input type="text" name="naturalname" value="" class="textinput_td">
				</td>
			</tr>
			<tr>
				<td>
					description
				</td>
				<td>
					<input type="text" name="description" value="" class="textinput_td">
				</td>
			</tr>
		</table>
		<br>
		<div align="center">
			<input type="button" value="添加并上传" onclick="uploadfile();"
				class="butt">
			&nbsp;
			<input type="button" value="取消" onclick="javascript:window.close();"
				class="butt">
		</div>
	</html:form>
</body>
</html:html>
