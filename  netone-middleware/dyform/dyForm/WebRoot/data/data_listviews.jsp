<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="OESEE 动态表单管理" />
		<h:css src="/include/css/oe.css" />

		<h:javascript src="/include/js/data/view.js" />
	</head>

	<body>
		<h:messageDialog />

		<h:form method="post" action="/data/showdata/listviews">

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<c:datagrid ui="datagrid" width="100%" border="0" cellpadding="2" bordercolorlight="#000000" bordercolordark="#FFFFFF" styleClass="tableclass" event="enList" eventArgument="en" pageSize="10" headStyle="tdheadline">
							<c:datarow>
								<c:datacolumn field="column1" align="left" headText="" />
							</c:datarow>
						</c:datagrid>
					</td>
				</tr>
			</table>

		</h:form>
	</body>
</html>



