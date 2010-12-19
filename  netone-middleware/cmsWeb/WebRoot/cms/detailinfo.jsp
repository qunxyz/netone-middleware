<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="oe.cms.xhtml.core.DetailUtil"%>

<%
	String path = request.getContextPath();
	String cellid = request.getParameter("cellid");
%>
<HTML>
	<HEAD>
		<TITLE>Oesee 系统子页面</TITLE>
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>

		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/style.css" rel="stylesheet"
			type="text/css">

		<script>
	function refreshdiv(xx){
		this.location.reload();
	}
	</script>
	</HEAD>
	<body>
		<%=DetailUtil.fetchInfo(cellid, request)%>
	</BODY>
</HTML>
