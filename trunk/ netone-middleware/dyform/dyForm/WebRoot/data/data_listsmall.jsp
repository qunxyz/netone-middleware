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
					alert('非选择状态');
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

		<h:form method="post" action="/data/data/listShare">
			<h:hidden property="formcode" />
			<h:hidden property="fatherlsh" />
			<input type='hidden' name='mode' value='${param.mode}'/>
			<h:radioValidate for="lsh" message="请选择一条记录" var="selectone" />
			
			<h:button ui="createIc" value="创建" onclick="createdo()"
							target="_blank" styleClass="buttonOnTable" />
			<h:submit ui="queIc" value="查询"
							action="/data/showdata/queryview.do" target="_blank"
							styleClass="buttonOnTable" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<c:datagrid ui="datagrid" width="100%" border="1" cellpadding="2"
							bordercolorlight="#999999" bordercolordark="#FFFFFF"
							styleClass="tableclass" event="enList" eventArgument="en"
							pageSize="16" headStyle="tdheadline" overLineColor="#FFFFFF">
							<c:datarow>
								<c:datacolumn ui="col0" field="a" align="left" headText="a" />
								<c:datacolumn ui="col1" field="a" align="left" headText="a" />
								<c:datacolumn ui="col2" field="a" align="left" headText="a" />
								<c:datacolumn ui="col3" field="a" align="left" headText="a" />
								<c:datacolumn ui="col4" field="a" align="left" headText="a" />
								<c:datacolumn ui="col5" field="a" align="left" headText="a" />
								<c:datacolumn ui="col6" field="a" align="left" headText="a" />
								<c:datacolumn ui="col7" field="a" align="left" headText="a" />
								<c:datacolumn  field="column30" align="left" headText="操作" />

								
							</c:datarow>
						</c:datagrid>
					</td>
				</tr>
			</table>
		</h:form>
	</body>
</html>



