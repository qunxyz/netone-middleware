<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="htm"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<h:meta title="Xpdl 附件上传" />
		<h:css src="/include/css/wf.css" />
		<h:javascript src="/include/js/fjgl.js" />
		<s:script>
			<s:pageReload var="upload" target="opener" />
			<s:closeHandler handler="upload" />
		</s:script>
	</head>

	<body onLoad="this.document.forms[0].fjmc.value='';">
		<h:messageDialog />
		<htm:form action="/data/datafj" enctype="multipart/form-data">
			<h:hidden property="systemid" />
			<h:hidden property="lsh" />
			<table width="100%" class="tableclass" border="1" cellspacing="0"
				cellpadding="2" bordercolordark="#FFFFFF" bordercolorlight="#000000"
				align="center">
				<tr>
					<td width="40%" height="25" class="tdtitle">
						&nbsp;选择附件
					</td>
					<td width="60%">
						<input type="file" name="theFile" value=""
							onFocus="setFileName(theFile.value)" class="btext">
					</td>
				</tr>
				<tr>
					<td width="40%" class="tdtitle">
						&nbsp;文件名
					</td>
					<td width="60%">
						<h:text property="fjmc" styleClass="btext" />
						<h:button value="上 传" onclick="uploaddo()" target="_self"
							styleClass="buttonOnTable" />
					</td>
				</tr>
				<tr>
					<td width="40%" bgcolor="#FFFFFF" class="tdtitle" colspan="2">
						&nbsp;附件列表（
						<h:label name="numOfFj" />
						）

						<h:select property="fjlist" styleClass="bsel">
							<h:option collection="fjlist" property="fjid"
								labelProperty="fjmc" />
						</h:select>
					</td>
				</tr>
				<tr>
					<td width="60%" colspan="2">
						<h:button value="删 除" onclick="deletedo()" target="_self"
							styleClass="buttonOnTable" />
						<h:button value="另 存" onclick="savedo()" target="_self"
							styleClass="buttonOnTable" />
					</td>
				</tr>
				<tr style='display:none'>
					<td width="100%" colspan="2" class="tdtitle">
						&nbsp;修改附件属性
						<h:button value="保 存" onclick="saveInfodo()" target="_self"
							styleClass="buttonOnTable" />
					</td>
				</tr>
				<tr style='display:none'>
					<td colspan="2">
						<h:textarea property="bz" cols="50" rows="2"/>

					</td>
				</tr>
			</table>
		</htm:form>
	</body>
</html>
