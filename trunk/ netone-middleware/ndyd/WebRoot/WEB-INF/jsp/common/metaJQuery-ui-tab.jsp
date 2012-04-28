<%
	String jqueryUITabScriptPath = request.getContextPath()+"/script";
	String jqueryUITabScriptPluginPath = jqueryUITabScriptPath+"/jquery-plugin";
%>
<!-- <link type="text/css" href="<%=jqueryUITabScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.theme.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUITabScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.core.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUITabScriptPluginPath%>/jquery-ui/themes/ui-lightness/ui.tabs.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jqueryUITabScriptPluginPath%>/jquery-ui/ui/minified/ui.core.min.js"></script>
<script type="text/javascript" src="<%=jqueryUITabScriptPluginPath%>/jquery-ui/ui/minified/ui.tabs.min.js"></script>
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- blue -->
<link type="text/css" href="<%=jqueryUITabScriptPluginPath%>/jquery-ui/themes/cupertino/ui.theme.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUITabScriptPluginPath%>/jquery-ui/themes/cupertino/ui.core.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryUITabScriptPluginPath%>/jquery-ui/themes/cupertino/ui.tabs.css" rel="stylesheet" />
 
<!-- red
<link type="text/css" href="<%=jqueryUITabScriptPluginPath%>/jquery-ui/themes/ui-lightness/jquery-ui-1.7.3.custom.css" rel="stylesheet" />
-->
<script type="text/javascript" src="<%=jqueryUITabScriptPluginPath%>/jquery-ui/ui/minified/ui.core.min.js"></script>
<script type="text/javascript" src="<%=jqueryUITabScriptPluginPath%>/jquery-ui/ui/minified/ui.tabs.min.js"></script>

<style type="text/css">
	/*demo page css*/
	
	.demoHeaders { margin-top: 2em; }
	#dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
	#dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
	ul#icons {margin: 0; padding: 0;}
	ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
	ul#icons span.ui-icon {float: left; margin: 0 4px;}
</style>