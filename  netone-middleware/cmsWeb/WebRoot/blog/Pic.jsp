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
	
	function previewpic(){
	   var picobj=this.document.getElementById('previewpic');
	   picobj.src=document.getElementById('realurl');
	   picobj.style=document.getElementById('styleinfo');
	}
	
	</script>
	</head>

	<body>
		<FORM action="servlet/AjaxPageItemSvl?objtype=pic" METHOD="POST"
			name='form1'>
			<input type='hidden' name='id'
				value='<%=request.getParameter("id")%>'>

			�� ��:
			<INPUT type='text' name="naturalname" value="" width='200'>
			<br>
			<br>
			��ʾ����:
			<INPUT type='text' name="name" value="" width='200'>
			<br>
			<br>
			<input type='hidden' name='valueinfo' value=''>
			<input type='hidden' name='realurl' value=''>
			<input type='text' name='valuedisp' value=''>
			<input type='button' value='ѡ��ͼƬ'
				onClick="window.open('http://10.1.5.241:8080/Newland3A/rsinfo/resource/ResourceSelect.do')">

			<input type='button' value='��������'
				onClick="window.open('http://10.1.5.202:8080/Newland3AS/rsinfo/resource/ResourceSelect.do')">
			<br>
			<br>
			<input name='styleinfo' type='text' value='ͼƬ��ʽ'>
			<input type='button' value='Ԥ��' onClick='preview()'>
			<hr>
			<img name='previewpic' src='' style=''>
			<br>
			<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
				<TR>
					<TD ALIGN="RIGHT" valign="top" width="100%">
						<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
							<TR>
								<TD>
									<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;��һ��"
										onClick="javascript:history.go(-1)">
								</TD>
								<TD>
									<INPUT type='button' value='���' onClick='todo()'>
								</TD>
								<TD>
									<INPUT TYPE="button" NAME="p_request" VALUE="ȡ��"
										onClick="javascript:window.close();">
								</TD>
							</TR>
						</TABLE>
			</FORM>
	</body>
</html>
