
<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String sessionid = request.getRequestedSessionId();
	String title = (String) request.getAttribute("title");
	String description = (String) request.getAttribute("description");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="description" content="<%=description %>">
		<SCRIPT type="text/javascript">
    	var contextpath = "<%=path%>";
    	var sessionid = "<%=sessionid%>";
    	var modelid = "${modelid}";
   	    </SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\util.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\modelshow\showmodel.js"></SCRIPT>

		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">

		<title><%=title%>
		</title>

	</head>

	<body>

		<div id="templatediv" name="templatediv" class="floatdiv"
			style="display:none ; float:left;  height: 50px; overflow: visible; border: 0; margin: 1px; word-wrap: break-word; ">
			loading...
		</div>

		<div style="width:100% ; height: 100%; overflow: visible;">
			<div id="container"
				style="width:100% ; height: 95%; overflow: visible;">
				<c:forEach items="${listmap}" var="entry" varStatus="vs">
					<div id="${entry.key}" class="boxdiv"
						style="width:${widthlist[vs.index]};height:100%;float:left;overflow-x: auto;overflow-y: visible">
						<c:forEach items="${entry.value}" var="fdiv">
							<div id="${fdiv.infoCellid}" class="floatdiv"
								style="display:block ; float:left;  height: 50px; overflow: visible; border: 0; margin: 1px; word-wrap: break-word; ">
								${fdiv.extendattribute}
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</div>
	</body>
</html>

