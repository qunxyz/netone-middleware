<%@ page contentType="text/html; charset=GB2312"%>
<jsp:directive.page import="oe.rmi.client.RmiEntry" />
<jsp:directive.page import="oe.env.client.EnvService" />

<%
    String fatherlsh=request.getParameter("fatherlsh");
	String context = request.getContextPath();
	String url0 ="";
	String url1 ="";
	try{
	EnvService env = (EnvService) RmiEntry.iv("envinfo");
	url0 = env.fetchEnvValue("%{dyformSer}");
	url1 = env.fetchEnvValue("%{cssx}");
	}catch(Exception e){
		e.printStackTrace();
	}
%>

<html>
	<head>
		<title>OESEE 动态表单</title>
		<!-- System properties -->
		<base href="<%=url0%>/data/" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<meta name="keyword" content="oes" />
		<meta name="description" content="" />
		<meta name="author" content="robanco" />
		<meta name="copyright" content="" />
		<script language="javascript"
			src="<%=url1%>/scripts/web/common/Common.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript">
			win.ContextPath='<%=context%>';
		</script>
		<script language="javascript"
			src="<%=url1%>/scripts/web/validate/Validate.js"
			type="text/javascript"></script>
		<link href="<%=url1%>/css/oe.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=url1%>/prototype.js"></script>
		<script language="javascript"
			src="<%=url1%>/js/data/dataCheck.js" type="text/javascript">
		</script>
		<!-- System properties -->
	</head>