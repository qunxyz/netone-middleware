<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String done = (String) request.getAttribute("done");

%>
<html>
	<head>
		<title>业务元 MetaBuss</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
	</head>

	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0 style="font:14px">
		<html:form action="cellModelDIY.do?pathname=${param.pathname}">
			<html:hidden property="jppmid" />
			<html:hidden property="editindex" />
			<table align="center" style="width:100%;">
				<tr>
					<td>
						命名:
						<br>
						<c:if test="${ismodify}">
						<html:text property="naturalname" size="40" readonly="true"></html:text>
						</c:if>
						<c:if test="${!ismodify}">
						<html:text property="naturalname" size="40"></html:text>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						显示名:
						<br>
						<html:text property="jppmidname" size="40"></html:text>
						&nbsp;是否共享:
						<html:checkbox property="accesstype" value='1'></html:checkbox>
					</td>
				</tr>
				<tr>
					<td>
						MetaBuss 声明:
						<br>
						<html:textarea property="jppmidscriptapi" cols='80' rows='4'></html:textarea>
					</td>
				</tr>
				<tr>
					<td>
						MetaBuss 定义:
						<br>
						<html:textarea property="jppmidscript" cols='80' rows='15'></html:textarea>

						<br>
						<input type="submit" class="butt" value="   保存   " />
					</td>
				</tr>
				<html:hidden property="extendattribute" />
			</table>

		</html:form>

	</body>
</html>
