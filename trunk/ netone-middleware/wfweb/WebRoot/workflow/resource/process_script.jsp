<%@ page contentType="text/html; charset=GB2312"%>

<%String path = request.getContextPath();
			String workflowStr = (String) request.getAttribute("tScript");
%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		<title>
			Á÷³Ì²âÊÔ·ÖÎö
		</title>
		<link href="<%=path%>/include/css/workflowtrack/track.css" rel="stylesheet" type="text/css">
		<link href="<%=path%>/include/css/wf.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/workflow/view.js"></SCRIPT>
	</head>
	<body>
		<STYLE>
v\:* { Behavior: url(#default#VML) }
</STYLE>
		<form action="/workflow/monitor/" method="post">
			<input type="hidden" name="runtimeid" value="<%=(String)request.getAttribute("runtimeid")%>" />
			<input type="hidden" name="pathroot" value="<%=path%>" />

			<%if (workflowStr != null)
				out.print(workflowStr);

		%>
		</form>
	</body>
</html>
