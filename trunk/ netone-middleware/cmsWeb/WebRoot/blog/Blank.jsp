
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>
			My JSP 'Article.jsp' starting page
		</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    	<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
    
		<script type="text/javascript">
		function todo(){
var number=/^(-\+)?([0-9]+)\.?([0-9]*)$/;
	 	var len=$('len').value;

	 	var wid=$('wid').value;
	
	 	if(!len.match(number)){
	 		alert('长度非数字');
	 		return;
	 	}
	 	if(!len.match(number)){
	 		alert('长度非数字');
	 		return;
	 	}
  		this.document.forms[0].submit();
}
		</script>
	</head>

	<body>
		<FORM action="/cmsWeb/servlet/AjaxBlankSvl" METHOD="POST">
			高度:
			<INPUT type='text' name="len" value="">
			<br>
			宽度:
			<INPUT type='text' name="wid" value="">
			<br>
			<INPUT type='button' value='提交' onClick='todo()'>
		</FORM>
	</body>
</html>
