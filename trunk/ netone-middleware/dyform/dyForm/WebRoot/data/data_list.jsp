<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="DyForm" />
		<h:javascript src="/include/js/data/data.js" />
		<%
		String pagestyle=request.getParameter("pagestyle");
		if(pagestyle!=null){
		 %>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<%}else{ %>
			<h:css src="/include/css/oe.css" />
		<%}	%>
		<script type="text/javascript">
			function selecthis(lsh,formcode){
				if(opener.opener.selectfatherlsh==undefined){
					alert('��ѡ��״̬');
					return;
				}
				opener.opener.selectfatherlsh(lsh,formcode);
				window.close();
				opener.close();
			}
		</script>
	</head>

	<body>
		<h:messageDialog />

		<h:form method="post" action="/data/data/list">
			<h:hidden property="formcode"  />
			<h:hidden property="fatherlsh" />

			<h:radioValidate for="lsh" message="��ѡ��һ����¼" var="selectone" />
			<table>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<c:datagrid ui="datagrid" width="100%" border="1" cellpadding="2"
							bordercolorlight="#999999" bordercolordark="#FFFFFF"
							styleClass="tableclass" event="enList" eventArgument="en"
							pageSize="16" headStyle="tdheadline" overLineColor="#FFFFFF">
							<c:datarow>
								<c:checkcolumn ui="col" field="lsh" width="40" align="left"
									radio="true" headText="ѡ��" />
								<c:datacolumn ui="col0" field="a" align="left" headText="a" />
								<c:datacolumn ui="col1" field="a" align="left" headText="a" />
								<c:datacolumn ui="col2" field="a" align="left" headText="a" />
								<c:datacolumn ui="col3" field="a" align="left" headText="a" />
								<c:datacolumn ui="col4" field="a" align="left" headText="a" />
								<c:datacolumn ui="col5" field="a" align="left" headText="a" />
								<c:datacolumn ui="col6" field="a" align="left" headText="a" />
								<c:datacolumn ui="col7" field="a" align="left" headText="a" />
								<c:datacolumn  field="fatherlsh" align="left" headText="����" />
							</c:datarow>
						</c:datagrid>
					</td>
				</tr>
			</table>
			<table width="100" border="0" cellspacing="0" cellpadding="1"
				align="right">
				<tr>
					<!-- <td>
						<h:submit  value="����" action="/data/data/upmove.do" target="_self" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit value="����" action="/data/data/downmove.do" target="_self" styleClass="buttonOnTable" />
					</td>-->
					<td>
						<h:submit ui="queIc" value="��ѯ"
							action="/data/showdata/queryview.do" target="_blank"
							styleClass="buttonOnTable" />
					</td>

					<td>
						<h:button ui="dispIc" value="��ʾ"
							action="/data/showdata/display.do" target="_blank"
							validate="selectone" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:button ui="createIc" value="����" onclick="createdo()"
							target="_blank" styleClass="buttonOnTable" />
					</td>
					
					
					<td>
						<h:button ui="modifyIc" value="�޸�" target="_blank" onclick="modifydo()"
							 styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit ui="deleteIc" value="ɾ��" action="/data/data/dropope.do"
							target="_blank" validate="deleteConfig" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:button ui="sublistIc" value="�Ӽ�¼�б�" validate="selectone"
							styleClass="buttonOnTable"
							onclick="document.getElementById('subinfo').style.display=''" />
					</td>
					<td id='subinfo' style='display:none'>
						<h:select property="subform">
							<h:option collection="subform" property="formcode"
								labelProperty="formname" />
						</h:select>
						<h:submit value="����" action="/data/data/sublist.do" target="_self"
							validate="selectone" styleClass="buttonOnTable" />
					</td>

				</tr>

			</table>
		</h:form>
	</body>
</html>



