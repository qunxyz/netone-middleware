<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>portalet</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=path%>/include/js/cell/cellSimple.js"></script>
		<script type="text/javascript" src="<%=path%>/include/js/prototype.js"></script>

	</head>

	<body BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0 style="font:14px">

		<html:form action="infocellnewSimple.do?newFlag=new&simple=yes">
			<table align="center" style="width:100%;">
				<html:hidden property="belongto" />
				<tr>
					<td>
						J++�ű�����
						<html:text property="cellname" value="δ�����ű�"></html:text>
						&nbsp;

						<html:checkbox property="intime">
						</html:checkbox>

						����(ʹ�û������Ż�����ҳ��Ԫ�صķ���Ч��)

					</td>
				</tr>

				<tr>
					<td>

						<html:textarea property="body" cols="110" rows="25"></html:textarea>
						<INPUT type="hidden" name="bodyTemp" />
					</td>
				</tr>
				<tr height="23px" align="center">
					<td>
						<input type="button" class="butt" value="     �� ��   "
							onClick='addjs()' />
					</td>
				</tr>
			</table>
		</html:form>
		<IFRAME src="cms/infocellSimple/infoCellToolsSimple.jsp"
			style="width:100%;height:120px;"></IFRAME>
	</body>
</html>
