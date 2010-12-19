
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>±à¼­</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="include/js/cell/cellSimple.js"></script>
		<script src="include/js/prototype.js"></script>
		<script src="include/js/util.js"></script>

		<link href="cms/include/css/css.css" rel="stylesheet" type="text/css">

	</head>

	<body BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0 style="font:14px">

		<html:form action="infocelleditSimple.do?editFlag=update">
			<html:hidden property="extendattribute" />
			<logic:notEqual name="seeFlag" value="true">
				<html:hidden property="cellid" />
				<html:hidden property="belongto" />
				<table>
					<tr>
						<td>
							Oesee J++½Å±¾Ãû³Æ&nbsp;
							<html:text property="cellname"></html:text>
							&nbsp;
							<html:checkbox property="intime">
							</html:checkbox>
							»º´æ &nbsp;

						</td>
					</tr>

					<tr>
						<td>
							<html:textarea property="body" cols="110" rows="25"></html:textarea>
							<INPUT type="hidden" name="bodyTemp" />
						</td>
					</tr>
					<tr height="23px" valign="bottom" align="center">
						<td>
							<input type="button" class="butt" value="     ±£ ´æ     "
								onclick="editDone()" />
							<input type="button" class="butt" value="     ²â ÊÔ     "
								onclick="testEle();" />
						</td>
					</tr>
				</table>
			</logic:notEqual>
		</html:form>
		<IFRAME src="cms/infocellSimple/infoCellToolsSimple.jsp"
			style="width:100%;height:120px;"></IFRAME>
	</body>
</html>
