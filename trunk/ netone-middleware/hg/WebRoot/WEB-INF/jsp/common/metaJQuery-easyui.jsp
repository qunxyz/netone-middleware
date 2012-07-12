<%
	String jqueryEasyUIScriptPath = request.getContextPath()+"/script";
	String jqueryEasyUIScriptPluginPath = jqueryEasyUIScriptPath+"/jquery-plugin";
%>
<link type="text/css" href="<%=jqueryEasyUIScriptPluginPath%>/easyui/themes/default/easyui.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryEasyUIScriptPluginPath%>/easyui/themes/icon.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jqueryEasyUIScriptPluginPath%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=jqueryEasyUIScriptPluginPath%>/easyui/locale/easyui-lang-zh_CN.js"></script>
<link type="text/css" href="<%=jqueryEasyUIScriptPath%>/theme/jtheme/brown/style.css" rel="stylesheet" /> 
<link rel="stylesheet" href="<%=jqueryEasyUIScriptPath%>/theme/main/brown/style.css" type="text/css" />
<jsp:include page="metaJQuery-ui-dialog.jsp"/>