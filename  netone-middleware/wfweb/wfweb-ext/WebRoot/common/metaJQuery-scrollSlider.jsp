<%
	String jqueryScriptPath = request.getContextPath()+"/script";
	String jqueryScriptPluginPath = jqueryScriptPath+"/jquery-plugin";
%>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<script src="<%=jqueryScriptPath%>/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=jqueryScriptPluginPath%>/scroll/jquery.scrollTo-1.3.3.js" type="text/javascript"></script>
<script src="<%=jqueryScriptPluginPath%>/scroll/jquery.localscroll-1.2.5.js" type="text/javascript" charset="gbk"></script>
<script src="<%=jqueryScriptPluginPath%>/scroll/jquery.serialScroll-1.2.1.js" type="text/javascript" charset="gbk"></script>
<script src="<%=jqueryScriptPluginPath%>/slider/coda-slider.js" type="text/javascript" charset="gbk"></script>
