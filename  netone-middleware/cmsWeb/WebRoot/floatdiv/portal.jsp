<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String sessionid = request.getRequestedSessionId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>Oesee NetOne Portal</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<SCRIPT type="text/javascript">
    	var contextpath = "<%=path%>";
    	var sessionid = "<%=sessionid%>";
    	var modelid = "${model.modelid}";
    </SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/setInnerHTML.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/floatdiv/floatdiv.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/floatdiv/resizediv.js"></SCRIPT>
		<script type="text/javascript"
			src="<%=path%>/include/js/cell/cellToolsSample.js"></script>
		<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/DownloadSvl2?name=${model.styleinfo}" rel="stylesheet"
			type="text/css">
		<TITLE>Oesee NetOne Portal</TITLE>
	</head>

	<BODY>
	
	
	
		<div id="templatediv" name="templatediv" class="floatdiv"
			style="display:none ; float:left;  height: 50px; overflow: visible; border: 0; margin: 1px; word-wrap: break-word; ">
			loading...
		</div>

		<div id="tmpboxdiv" class="boxdiv"
			style="display:none;width:10;height:100%;float:left;overflow-x: auto;overflow-y: visible">
		</div>

		<div style="width:${modelWidth}; height:${modelHeight}; overflow: visible;">
			<div id="container"
				style="width:${modelWidth}; height: 95%; overflow: visible;">
				<c:forEach items="${listmap}" var="entry" varStatus="vs">
					<div id="${entry.key}" class="boxdiv"
						style="width:${widthlist[vs.index]};height:100%;float:left;overflow-x: auto;overflow-y: visible">
						<c:forEach items="${entry.value}" var="fdiv">
							<script type="text/javascript">
							
		  						addDivPortal("${fdiv.infoCellid}","${idnamemap[fdiv.infoCellid]}","${entry.key}","${portalmode}");
		  					</script>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
			<c:forEach items="${listmap}" var="entry" varStatus="vs">
				<script type="text/javascript">
					Resize.setResizeAble("${entry.key}",getNextSibling("${entry.key}"));
				</script>
			</c:forEach>
		</div>
	</body>
</html>
