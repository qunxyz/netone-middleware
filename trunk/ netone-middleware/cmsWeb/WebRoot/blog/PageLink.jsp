<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'Article.jsp' starting page</title>


		<meta http-equiv="description" content="This is my page">

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
		<script type="text/javascript">
	function addSelectedResource(name,id){
		document.getElementById('valueinfo').value=id;
		document.getElementById('valuedisp').value=name;
	}
	
	function todo(){
	   this.document.forms[0].submit();
	}

	function preview(){
	   preview.innerHTML=
	}	
	</script>
	</head>

	<body>
		<FORM action="servlet/AjaxPageItemSvl?objtype=pagelink" METHOD="POST" name='form1'>
			<input type='hidden' name='id'
				value='<%=request.getParameter("id")%>'>
			<input type='hidden' name='valueinfo' value=''>
			<input type='text' name='cellid' value=''>

			<input type='button' value='选择' 
				onClick="window.open('http://10.1.5.241:8080/Newland3A/rsinfo/resource/ResourceSelect.do')">

			<input type='button' value='创建管理'
				onClick="window.open('http://10.1.5.202:8080/Newland3AS/rsinfo/resource/ResourceSelect.do')">

			<INPUT type='button' value='提交' onClick='todo()'>
			
			<br>
			<input type='button' value='预览' onClick='preview()'/>
			<div id='preview'>
		</FORM>
	</body>
</html>
