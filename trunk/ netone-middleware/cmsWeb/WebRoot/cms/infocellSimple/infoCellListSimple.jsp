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
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=path%>/include/js/cell/cellToolsSample.js"></script>
		<script type="text/javascript" src="<%=path%>/include/js/prototype.js"></script>

	</head>

	<body onLoad='listCell();' BGCOLOR=#C0C0C0 LEFTMARGIN=0 TOPMARGIN=0
		MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">

		<table width='100%'>
			<tr bgcolor='000000'>
				<td colspan='3'>

					<font color='ffffff'> <strong> OESEE J++ 脚本设计 </strong> </font>
					<em> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font
						color='ffffff'> <strong> 帮助: </strong> 技术交流互动区 &nbsp; OESEE
							J++ 快速入门 &nbsp; OESEE J++ 黑带水平教材 </font> </em>
				</td>

			</tr>
			<tr>
				<td id='NextElementJpp' nowrap>
				</td>
				<td id='JppElementDo' style='display:none' nowrap>
					<input type='text' value='' size='12' name='iddisp'>
					<INPUT class='butt' type='button' value='添加到布局界面'
						onClick='toFace();'>
				</td>


				<td nowrap>
					<INPUT class='butt' type='button' value='新增脚本' onClick='addJPP()'>
					<INPUT class='butt' type='button' value='修改脚本' onClick='editJPP()'>
					<INPUT class='butt' type='button' value='删除脚本' onClick='delEle();'>
				</td>


			</tr>
		</table>

	</body>
</html>
