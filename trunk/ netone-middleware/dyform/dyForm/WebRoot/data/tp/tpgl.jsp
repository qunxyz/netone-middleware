<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="htm"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<h:meta title="图片上传" />
		<h:css src="/include/css/wf.css" />
		<h:javascript src="/include/js/tpgl.js" />
		<s:script>
			<s:pageReload var="upload" target="opener" />
			<s:closeHandler handler="upload" />
		</s:script>
	</head>

	<body onLoad="init();">
		<h:messageDialog />
		<htm:form action="/data/datatp" enctype="multipart/form-data">
			<h:hidden property="systemid" />
			<h:hidden property="lsh" />
			<table width="100%" class="tableclass" border="1" cellspacing="0" cellpadding="2" bordercolordark="#FFFFFF" bordercolorlight="#000000" align="center">

				<tr>
					<td width="40%" height="25" class="tdtitle">
						&nbsp;选择图片
					</td>
					<td width="60%">
						<input type="file" name="theFile" value="" onFocus="setFileName(theFile.value)" class="btext">
					</td>
				</tr>
				<tr>
					<td width="40%" class="tdtitle">
						&nbsp;图片名
					</td>
					<td width="60%">
						<h:text property="tpmc" styleClass="btext" />
						<h:button value="上 传" onclick="uploaddo()" target="_self" styleClass="buttonOnTable" />
					</td>
				</tr>
				<tr>
					<td width="100%" colspan="2">
						&nbsp;图片列表(
						<h:label name="numOfFj" />
						）
						<h:select property="tplist" styleClass="bsel" onchange="choicePicList()">
							<h:option collection="tplist" property="tpid" labelProperty="tpmc" />
						</h:select>
						<h:select property="tplink" styleClass="bsel">
							<h:option collection="tplink" property="tpid" labelProperty="linkurl" />
						</h:select>
						<h:select property="tpsize" styleClass="bsel">
							<h:option collection="tpsize" property="tpid" labelProperty="dispsize" />
						</h:select>
					</td>
				</tr>
				<tr>
					<td width="100%" colspan="2" class="tdtitle">
						<h:button value="删 除" onclick="deletedo()" target="_self" styleClass="buttonOnTable" />
						<h:button value="另 存" onclick="savedo()" target="_self" styleClass="buttonOnTable" />
					</td>
				</tr>

				<tr>
					<td width="100%" colspan="2" class="tdtitle">
						修改图片的其他属性
					</td>
				</tr>
				<tr>

					<td width="100%" colspan="2">
						&nbsp;图片链接地址
						<h:text property="linkurl" styleClass="btext" />
					</td>
				</tr>
				<tr>
					<td width="100%" colspan="2">
						&nbsp;图片显示大小
						<h:text property="dispsize" styleClass="btext" />
						<h:button value="保 存" onclick="saveInfodo()" target="_self" styleClass="buttonOnTable" />
					</td>
				</tr>
			</table>

		</htm:form>
	</body>
</html>
