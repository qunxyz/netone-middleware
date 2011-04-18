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

		<title>修改页属性</title>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript">
		function todo(){
		var number=/^(-\+)?([0-9]+)\.?([0-9]*)$/;
	 	var len=$('len').value;

	 	var wid=$('wid').value;
	 	
	 	var name=$('name').value;
	 	var naturalname=$('naturalname').value;
	 	
	 	if(name==''){
	 		alert('缺少标题');
	 		return;
	 	}
	 	
	 	if(naturalname==''){
	 		alert('显示名');
	 		return;
	 	}
	
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
		<FORM
			action="/cmsWeb/servlet/AjaxAddPageSvl?pagepath=<%=request.getParameter("pagepath")%>"
			METHOD="POST">
			<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="4" WIDTH="90%">
				<tr>
					<td>
						名称
					</td>
					<td>
						<INPUT type='text' name="naturalname" value="">
					</td>
				</tr>

				<tr>
					<td>
						中文名称
					</td>
					<td>
						<INPUT type='text' name="name" value="">
					</td>
				</tr>
				<tr>
					<td>
						高度
					</td>
					<td>
						<INPUT type='text' name="len" value="80">
					</td>
				</tr>
				<tr>
					<td>
						宽度
					</td>
					<td>
						<INPUT type='text' name="wid" value="200">
					</td>
				</tr>
				<tr>
					<td>
						头链接[名称:URL]
					</td>
					<td>
						<INPUT type='text' name="linkx" value="200">
					</td>
				</tr>
								
				
			</TABLE>

			<input type='hidden' name="modelid"
				value='<%=request.getParameter("modelid")%>'>
			<br>
			<hr>
			&nbsp;是否使用高速缓存:
			<INPUT type='checkbox' name="cache" value="1">
			<br><br>
			&nbsp;缓存生命周期:
			<input type='cacheCycle' value='12'>
			&nbsp;小时
			<br><br>
			&nbsp;页面有效期从:
			<input type="text" name="begintime" value="" class="textinput_td"
				onfocus="calendar()">
			<br><br>
			&nbsp;页面有效期至:
			<input type="text" name="endtime" value="" class="textinput_td"
				onfocus="calendar()">


			<br>
			<br>
			&nbsp;<INPUT type='button' value='提交' onClick='todo()'>
		</FORM>
	</body>
</html>
