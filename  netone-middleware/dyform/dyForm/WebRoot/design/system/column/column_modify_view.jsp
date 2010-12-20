
<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<html>
	<head>
		<h:meta title="�ֶ����" />
		<h:css src="/include/css/oe.css" />
	</head>
	<script type="text/javascript" src="/dyForm/include/js/prototype.js"></script>
	<script type="text/javascript"
		src="/dyForm/include/js/design/column/column.js"></script>
	<script type="text/javascript"
		src="/dyForm/include/js/design/column/cellToolsSample.js"></script>

	<body onLoad="hideFilebutton();">

		<h:messageDialog />
		<h:form action="/design/system/column/list" method="post">
			<TABLE width="100%" height="100%" border="1" cellpadding="0"
				cellspacing="0" bordercolorlight="#000000" bordercolordark="#FFFFFF">
				<h:hidden property="formcode" />
				<h:hidden property="systemid" />
				<h:hidden property="columnid" />
				<h:hidden property="columncode" />
				<h:hidden property="statusinfo" />

				<tr>
					<td width="20%" class="tdheaddes">
						&nbsp;λ�ñ��
					</td>
					<td width="50%" class="btext">
						<h:text property="indexvalue" readonly="true" require="false"
							styleClass="btext" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;�ֶ���
					</td>
					<td class="btext">
						<h:text property="columname" require="false" styleClass="btext" />
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;�ֶ�����
					</td>
					<td class="bsel">
						<h:select property="viewtype" onchange="choiceFile()"
							styleClass="bsel">
							<h:option collection="typeinfo" property="value"
								labelProperty="label" />
						</h:select>
					</td>
				</tr>
				<tr id='treemantr' style='display:none'>
					<td class="tdheaddes">
						&nbsp;ѡ����ͼ
					</td>
					<td id='treeman'>
						<input type='text' name='dispvalue' readonly>
						<input type='button' value='ѡ��' onClick="openTree()">
					</td>
				</tr>

				<tr id='scriptinfo' style='display:none'>
					<td class="tdheaddes">
						&nbsp;Ӧ�ýű�
					</td>
					<td>
						<table>
							<tr>
								<td>
									<input id='userother' class='butt' type='button' value='ˢ��'
										onClick="flashScrpit('')">
								</td>
								<td id='JppScriptDemoMore'></td>
								<td>
									&nbsp;
									<input id='userother' class='butt' type='button' value='  ���  '
										onClick='designJppDemoMore()'>

								</td>
							</tr>
						</table>

					</td>
				</tr>

				<tr id='bkvalue'>
					<td class="tdheaddes">
						&nbsp;��ѡֵ<a href='tipColumnValue.html' target='_blank'><font color='red'>[����]</font></a>
					</td>
					<td class="btext">
						<h:text property="valuelist" require="false" styleClass="btext"
							size='60' />
					</td>

				</tr>

				<tr>
					<td class="tdheaddes">
						&nbsp;ֻ����
					</td>
					<td class="bsel">
						<h:select property="opemode" styleClass="bsel">
							<h:option collection="booleaninfo" property="value"
								labelProperty="label" />
						</h:select>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;�Ƿ����
					</td>
					<td class="bsel">
						<h:select property="musk" styleClass="bsel">
							<h:option collection="booleaninfo" property="value"
								labelProperty="label" />
						</h:select>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;�Ƿ�����
					</td>
					<td class="bsel">
						<h:select property="useable" styleClass="bsel">
							<h:option collection="booleaninfo" property="value"
								labelProperty="label" />
						</h:select>
					</td>
				</tr>
				<tr>
					<td class="tdheaddes">
						&nbsp;��չ����
						<a href='tip.html' target='_blank'><font color='red'>[����]</font></a>
					</td>
					<td class="btext">
						<h:textarea property="extendattribute" require="false" cols="60"
							rows="5" />
						<h:textarea property="tip" require="false" cols="30" rows="5" />
						
					</td>

				</tr>
				<tr>
					<td align='left'>
						<h:submit property='fjsc' value="�ϴ�����"
							action="/design/system/column/upload.do" target="add"
							dialog="true" dialogResizable="true" dialogHeight="88"
							dialogWidth="500" styleClass="buttonOnTable" />
						<h:submit property='tpsc' value="�ϴ�ͼƬ"
							action="/design/system/column/upload.do" target="add"
							dialog="true" dialogResizable="true" dialogHeight="88"
							dialogWidth="500" styleClass="buttonOnTable" />
					</td>
					<td colspan="1">
						<h:submit value="�޸��ֶ�" action="/design/system/column/modifyope.do"
							target="_self" styleClass="buttonOnTable" />
						<input type="reset" name="Submit2" value="����"
							style="buttonOnTable">
						<h:button value="����" onclick="goback()" target="_self"
							styleClass="buttonOnTable" />
					</td>
				</tr>

				<script>

				window.setTimeout("choiceFileCore('${columnForm.valuelist}','${columnForm.viewtype}')", 100);  
		
				</script>
			</TABLE>

		</h:form>
	</body>
</html>
