<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();
%>
<html>
	<head>
		<title>
			portalet
		</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/cell/cell.js"></script>
	</head>

	<body onload='initvalue();' BGCOLOR=#C0C0C0 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">

		<html:form action="infocelltool.do">
			<font size='2px'>
				&nbsp;ϵͳ��ƿ��ѡ��
			</font>
			<html:select property="select" onchange="selecttool();">

				<logic:present name="selectall">
					<html:options collection="selectall" labelProperty="label" property="value" />
				</logic:present>
			</html:select>

			<INPUT type="hidden" name="path" value='<%=path%>' />
			<table align="center" style="width:100%;" border='1'>
				<tr id='td2'>
					<td NOWRAP>
						ѡ������
						<html:select property="wfid" onchange="javascript:document.forms[0].wfi.value=document.forms[0].wfid.value;">
							<logic:present name="wflist">
								<html:options collection="wflist" labelProperty="label" property="value" />
							</logic:present>
						</html:select>

						����ID:
						<INPUT type="text" name="wfi" size='60'>

					</td>
				</tr>
				<tr id='td1'>
					<td NOWRAP>
						ѡ���
						<html:select property="formid" onchange="javascript:document.forms[0].formi.value=document.forms[0].formid.value;">
							<logic:present name="formlist">
								<html:options collection="formlist" labelProperty="label" property="value" />
							</logic:present>
						</html:select>

						��ID:
						<INPUT type="text" name="formi" size='60'>
					</td>
				</tr>
				<tr id='td3'>
					<td NOWRAP>
						ѡ�������
						<html:select property="cellgroup" onchange="changeCellGroup();">
							<logic:present name="groupList">
								<html:options collection="groupList" labelProperty="label" property="value" />
							</logic:present>
						</html:select>

						ѡ�����Ԫ��
						<html:select property="cellTool" onchange="javascript:document.forms[0].cellToolCopy.value=document.forms[0].cellTool.value;">
							<logic:present name="cellToolList">
								<html:options collection="cellToolList" labelProperty="label" property="value" />
							</logic:present>
						</html:select>


						����Ԫ��ID:
						<INPUT type="text" name="cellToolCopy">
					</td>
				</tr>
				<tr id='td4'>
					<td NOWRAP>
						ѡ�񸽼���
						<html:select property="fiId" onchange="changefi();">
							<logic:present name="fiList">
								<html:options collection="fiList" labelProperty="label" property="value" />
							</logic:present>
						</html:select>

						ѡ�񸽼�
						<html:select property="fiColumnId" onchange="javascript:document.forms[0].fiCopy.value=document.forms[0].fiColumnId.value;document.forms[0].fiName.value=document.forms[0].fiColumnId.options[document.forms[0].fiColumnId.selectedIndex].text;">
							<logic:present name="fiColumnList">
								<html:options collection="fiColumnList" labelProperty="label" property="value" />
							</logic:present>
						</html:select>

						����Ԫ��ID:
						<INPUT type="text" name="fiCopy">
						����Ԫ��Name:
						<INPUT type="text" name="fiName">
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
