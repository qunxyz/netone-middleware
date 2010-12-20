<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<html>
	<head>
		<h:meta title="OESEE 动态表单" />
		<h:css src="/include/css/oe.css" />
		<h:javascript src="/include/js/data/data.js" />
	</head>

	<body>
		<h:messageDialog />

		<h:form method="post" action="/data/data/list">
			<h:hidden property="formcode" />
			<h:hidden property="fatherlsh" />
			<h:radioValidate for="lsh" message="请选择一条记录" var="selectone" />
			<table>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<c:datagrid ui="datagrid" width="100%" border="1" cellpadding="2" bordercolorlight="#000000" bordercolordark="#FFFFFF" styleClass="tableclass" event="enList" eventArgument="en" pageSize="10" headStyle="tdheadline" overLineColor="#FFFFCC">
							<c:datarow>
								<c:checkcolumn ui="col" field="lsh" width="40" align="left" radio="true" headText="选择" />
								<c:datacolumn ui="col0" field="a" align="left" headText="a" />
								<c:datacolumn ui="col1" field="a" align="left" headText="a" />
								<c:datacolumn ui="col2" field="a" align="left" headText="a" />
								<c:datacolumn ui="col3" field="a" align="left" headText="a" />
								<c:datacolumn ui="col4" field="a" align="left" headText="a" />
								<c:datacolumn ui="col5" field="a" align="left" headText="a" />
								<c:datacolumn ui="col6" field="a" align="left" headText="a" />
								<c:datacolumn ui="col7" field="a" align="left" headText="a" />
								<c:datacolumn field="participant" align="left" headText="作者" />
								<c:datacolumn field="created" align="left" headText="日期" />
							</c:datarow>
						</c:datagrid>
					</td>
				</tr>
			</table>
			<table width="100" border="0" cellspacing="0" cellpadding="1" align="right">
				<tr>
					<td>
						<h:submit ui="queIc" value="查询" action="/data/showdata/queryview.do" target="_self" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit ui="expIc" value="导出" action="/data/showdata/exportview.do" target="_self" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:button ui="dispIc" value="显示" action="/data/showdata/display.do" target="_blank" validate="selectone" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:button ui="createIc" value="创建" onclick="createdo()" target="_self" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit ui="modifyIc" value="修改" action="/data/showdata/modifyview.do" target="_self" validate="selectone" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit ui="deleteIc" value="删除" action="/data/data/dropope.do" target="_self" validate="selectone" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit ui="sublistIc" value="子记录列表" action="/data/data/sublist.do" target="_self" validate="selectone" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit ui="backIc" value="返回上一级" action="/data/data/backtofather.do" target="_self" styleClass="buttonOnTable" />
					</td>
				</tr>

			</table>
		</h:form>
	</body>
</html>



