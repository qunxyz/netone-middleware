<%
	String jqueryScriptPath = request.getContextPath()+"/script";
	String jqueryScriptPluginPath = jqueryScriptPath+"/jquery-plugin";
%>

<script src="<%=jqueryScriptPath%>/jquery-1.3.2.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=jqueryScriptPluginPath%>/dataTables/themes/1/css/demo_page.css" type="text/css" />
<link rel="stylesheet" href="<%=jqueryScriptPluginPath%>/dataTables/themes/1/css/demo_table.css" type="text/css" />
<style>
<!--
div.dataTables_info {
	padding-bottom: 10px;
}
.toolbar {
	float: right;
}
-->
</style>
<script src="<%=jqueryScriptPluginPath%>/dataTables/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="<%=jqueryScriptPluginPath%>/dataTables/jquery.scrollabletable.min.js" type="text/javascript"></script>
