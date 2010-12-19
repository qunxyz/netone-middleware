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
	 	var cellid=$('cellid').value;
	 	if(cellid==''){
	 		alert('未选择');
	 		return;
	 	}
	
  		this.document.forms[0].submit();
        }
        
        function todo2(){
        	var selectvalue=document.getElementById('mode').value;
        	if(selectvalue=='disp'){
        		disp.style.display='';
        	}else{
        		disp.style.display='none';
        	}
        }
		</script>
	</head>

	<body>
		<FORM action="servlet/AjaxPageItemSvl?objtype=pagelink" METHOD="POST">

			<input type='hidden' name='id' value='<%=id%>' />
			名 称:
			<INPUT type='text' name="naturalname" value="" width='200'>
			<br>
			<br>
			显示名称:
			<INPUT type='text' name="name" value="" width='200'>
			<br>
			<br>
			选择页
			<INPUT type='text' name="cellid" value="" width='200'> <input type='button' value='选择'>
			<br>
			<br>
			访问模式:
			<select name='mode' onChange='todo2()'>
				<option value='link'>
					链接
				</option>
				<option value='disp'>
					嵌入显示
				</option>
			</select>

			<div id='disp' style='display:none'>
				<br>
				<br>
				Html样式:
				<INPUT type='text' name="styleinfo" value="width:100%" width='200'>
			</div>
			<br>
			<br>
			描 述:
			<textarea rows="5" cols="30" name='description'></textarea>
			<br>
			<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
				<TR>
					<TD ALIGN="RIGHT" valign="top" width="100%">
						<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
							<TR>
								<TD>
									<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
										onClick="javascript:history.go(-1)">
								</TD>
								<TD>
									<INPUT type='button' value='完成' onClick='todo()'>
								</TD>
								<TD>
									<INPUT TYPE="button" NAME="p_request" VALUE="取消"
										onClick="javascript:window.close();">
								</TD>
							</TR>
						</TABLE>
			</FORM>
	</body>
</html>
