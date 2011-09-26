<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-html.tld" prefix="h"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-ctrl.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/strutsframe-script.tld" prefix="s"%>
<%=request.getAttribute("tip") == null ? "" : request
					.getAttribute("tip")%>
<html>
	<head>
		<h:meta title="表单字段" />
		<h:css src="/include/css/oe.css" />
	</head>
	<script>
	function goback(){
		var f=document.forms[0];
		location.href='/design/system/list.do?systemid='+f['systemid'].value;
	}
	function checkBoxCheck(){
  	}
  	checkBoxCheck.prototype.isValid=function(){
  	 	 return confirm("真的要删除该字段吗?");
    }
   //js方法注入
   win.validates['deleteConfig']=new checkBoxCheck();
	</script>

	<body>
		<h:messageDialog />
		<h:form method="post" action="/design/system/column/list">
			<h:hidden property="systemid" />
			<h:hidden property="formcode" />
			<h:radioValidate for="columncode" message="请选择一条记录" var="selectone" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<c:datagrid ui="datagrid" width="100%" border="1" cellpadding="2"
							bordercolorlight="#000000" bordercolordark="#FFFFFF"
							styleClass="tableclass" event="enList" eventArgument="en"
							pageSize="40" headStyle="tdheadline" overLineColor="#FFFFCC">
							<c:datarow>
								<c:checkcolumn field="columncode" width="40" align="center"
									radio="true" headText="选择" />
								<c:datacolumn field="columnid" align="center" headText="字段ID" />
								<c:datacolumn field="columname" align="center" headText="字段名" />
								<c:datacolumn field="indexvalue" align="center" headText="索引号" />
								<c:datacolumn field="ext9viewtype" align="center" headText="类型" />
								<c:datacolumn field="ext9opemode" align="center" headText="只读吗" />
								<c:datacolumn field="useable" align="center" headText="是否显示" />
							</c:datarow>
						</c:datagrid>
					</td>
				</tr>
			</table>
			<table width="100" border="0" cellspacing="0" cellpadding="0"
				align="right">
				<tr>
	<!-- 
					<td>
						<h:submit value="下移字段" action="/design/system/column/moveup.do"
							validate="selectone" target="_self" styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit value="上移字段" action="/design/system/column/movedown.do"
							validate="selectone" target="_self" styleClass="buttonOnTable" />
					</td>
					 -->
					<td>
						&nbsp;&nbsp;&nbsp;
					</td>
					<td>
						<h:submit value="创建字段"
							action="/design/system/column/createview.do" target="_blank"
							styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit value="修改字段"
							action="/design/system/column/modifyview.do" target="_blank"
							styleClass="buttonOnTable" />
					</td>
					<td>
						<h:submit value="删除字段" validate="deleteConfig"
							validate="deleteConfig" action="/design/system/column/dropope.do"
							target="_blank" styleClass="buttonOnTable" />
					</td>

				</tr>
			</table>
		</h:form>
	</body>
</html>



