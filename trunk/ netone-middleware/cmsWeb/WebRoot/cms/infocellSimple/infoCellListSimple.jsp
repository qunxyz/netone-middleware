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

					<font color='ffffff'> <strong> OESEE J++ �ű���� </strong> </font>
					<em> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font
						color='ffffff'> <strong> ����: </strong> �������������� &nbsp; OESEE
							J++ �������� &nbsp; OESEE J++ �ڴ�ˮƽ�̲� </font> </em>
				</td>

			</tr>
			<tr>
				<td id='NextElementJpp' nowrap>
				</td>
				<td id='JppElementDo' style='display:none' nowrap>
					<input type='text' value='' size='12' name='iddisp'>
					<INPUT class='butt' type='button' value='��ӵ����ֽ���'
						onClick='toFace();'>
				</td>


				<td nowrap>
					<INPUT class='butt' type='button' value='�����ű�' onClick='addJPP()'>
					<INPUT class='butt' type='button' value='�޸Ľű�' onClick='editJPP()'>
					<INPUT class='butt' type='button' value='ɾ���ű�' onClick='delEle();'>
				</td>


			</tr>
		</table>

	</body>
</html>
