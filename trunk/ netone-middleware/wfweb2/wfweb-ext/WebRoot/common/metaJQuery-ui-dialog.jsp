<%
	String jqueryUIScriptPath = request.getContextPath()+"/script";
	String jqueryUIScriptPluginPath = jqueryUIScriptPath+"/jquery-plugin";
%>
<link type="text/css" href="<%=jqueryUIScriptPluginPath%>/jquery-ui/themes/custom/jquery-ui-1.7.2.custom.css" rel="stylesheet" />
<script src="<%=jqueryUIScriptPath%>/jquery-1.3.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.core.min.js"></script>
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.draggable.min.js"></script>
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.resizable.min.js"></script>
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.dialog.min.js"></script>
