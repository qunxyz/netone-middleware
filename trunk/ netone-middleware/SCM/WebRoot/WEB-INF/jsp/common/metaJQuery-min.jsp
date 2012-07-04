<%
	String jqueryScriptPath = request.getContextPath()+"/script";
%>
<script src="<%=jqueryScriptPath%>/jquery-1.5.2.min.js" type="text/javascript"></script>
<!-- dateformat -->
<script src="<%=jqueryScriptPath%>/dateformat.js" type="text/javascript"></script>

<!--
<LINK rel=stylesheet type=text/css href="<%=jqueryScriptPath%>/jquery-plugin/flexigrid/css/flexigrid.pack.css">
<script src="<%=jqueryScriptPath%>/jquery-plugin/flexigrid/flexigrid.js" type="text/javascript"></script>
-->


<link type="text/css" href="<%=jqueryScriptPath%>/jquery-plugin/autocomplete/jquery.autocomplete.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/autocomplete/jquery.autocomplete.pack.js"></script>

<script>
function makeUUID() {
    var S4 = function () {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    };
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}
</script>

<!-- multiselect -->
<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/jquery-multiselect/i18n/jquery.multiselect.filter.zh-cn.js"></script>
<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/jquery-multiselect/i18n/jquery.multiselect.zh-cn.js"></script>
<link type="text/css" href="<%=jqueryScriptPath%>/jquery-plugin/jquery-multiselect/jquery.multiselect.css" rel="stylesheet" />
<link type="text/css" href="<%=jqueryScriptPath%>/jquery-plugin/jquery-multiselect/jquery.multiselect.filter.css" rel="stylesheet" />
<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/jquery-multiselect/jquery.multiselect.min.js"></script>
<script type="text/javascript" src="<%=jqueryScriptPath%>/jquery-plugin/jquery-multiselect/jquery.multiselect.filter.min.js"></script>
