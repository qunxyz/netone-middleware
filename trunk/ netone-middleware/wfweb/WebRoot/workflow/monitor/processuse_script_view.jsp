<%@ page contentType="text/html; charset=GB2312"%>

<%String path = request.getContextPath();
			String workflowStr = (String) request.getAttribute("rtScript");
%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		<title>
			流程应用
		</title>
		<link href="<%=path%>/include/css/workflowtrack/track.css" rel="stylesheet" type="text/css">
		<link href="<%=path%>/include/css/wf.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		function showDetail(runtimeid,activityid){
	
		}
		</script>
	</head>
	<body>
		<STYLE>
v\:* { Behavior: url(#default#VML) }
</STYLE>
		<form action="/workflow/monitor/" method="post">
			<input type="hidden" name="runtimeid" value="<%=(String)request.getAttribute("runtimeid")%>" />
			<input type="hidden" name="pathroot" value="<%=path%>" />

			<%if (workflowStr != null)
				out.print(workflowStr);%>
		</form>
	</body>
</html>
