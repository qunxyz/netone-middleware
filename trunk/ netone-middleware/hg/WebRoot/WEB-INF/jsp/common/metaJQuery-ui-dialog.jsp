<%
	String jqueryUIScriptPath = request.getContextPath()+"/script";
	String jqueryUIScriptPluginPath = jqueryUIScriptPath+"/jquery-plugin";
%>
<link type="text/css" href="<%=jqueryUIScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.theme.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUIScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.core.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUIScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.resizable.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUIScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.selectable.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUIScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.dialog.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.core.min.js"></script>
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.draggable.min.js"></script>
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.resizable.min.js"></script>
<script type="text/javascript" src="<%=jqueryUIScriptPluginPath%>/jquery-ui/ui/minified/ui.dialog.min.js"></script>
<div id="_openDiv"><div id="_innerHtml"></div></div>
<script type="text/javascript" src="<%=jqueryUIScriptPath%>/jquery-window-op.js"></script>
<style>
	.ui-widget-header { border: 1px solid #D2E0F1; background: #D2E0F1 50% 50% repeat-x; color: #ffffff; font-weight: bold; }
</style>