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
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="Oesee tools">
		<script src="<%=path%>/include/js/prototype.js"></script>

		<script type="text/javascript"
			src="<%=path%>/include/js/cell/cellModelDesign.js"></script>

		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">

	</head>

	<body onLoad="dispJppScriptDemoSelf();" LEFTMARGIN=0
		TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
		<table>
			<tr>
				<td id='JppScriptDemoDIY'></td>
				<td>
					<input class='butt' id='addJppScript' type='button' value='´´½¨'
						onClick='add()'>
					<input class='butt' id='addJppScript' type='button' value='ÐÞ¸Ä'
						onClick='edit()'>
					<input class='butt' id='addJppScript' type='button' value='É¾³ý'
						onClick='dele()'>
				</td>
			</tr>
		</table>
	</body>
</html>
