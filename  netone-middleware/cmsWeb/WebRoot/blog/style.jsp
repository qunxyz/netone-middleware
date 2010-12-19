<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<script type="text/javascript">
		function todo(){
	 	var url=$('url').value;
	 	if(url==''){
	 		alert('缺少URL地址');
	 		return;
	 	}
	
  		this.document.forms[0].submit();
        }
		</script>
	</head>

	<body>
		<FORM action="/cmsWeb/servlet/AjaxUrlSvl" METHOD="POST">
			<input type='hidden' name='id' value='<%=id%>' />
			<br>
			系统栏样式:
			<INPUT type='text' name="url" value="" width='200'>
			<br>
			<br>
			Html样式:
			<INPUT type='text' name="styleinfo" value="width:100%" width='200'>


			<INPUT type='button' value='提交' onClick='todo()'>
		</FORM>
	</body>
</html>
