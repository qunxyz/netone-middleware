<% String cssURL = request.getContextPath() + "/styles";
   String scriptsURL = request.getContextPath() + "/script";
   String imgURL = request.getContextPath() + "/image/default";
%>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="stylesheet" href="<%= cssURL %>/styles.css" type="text/css" />

<SCRIPT language='javascript' src="<%= scriptsURL %>/prototype.js"></SCRIPT>
<SCRIPT language='javascript' src="<%= scriptsURL %>/element-op.js"></SCRIPT>
<SCRIPT language='javascript' src="<%= scriptsURL %>/table-op.js"></SCRIPT>
<SCRIPT language='javascript' src="<%= scriptsURL %>/window-op.js"></SCRIPT>
<SCRIPT language='javascript' src="<%= scriptsURL %>/page.js"></SCRIPT>
<SCRIPT language='javascript' src="<%= scriptsURL %>/validator.js"></SCRIPT>





