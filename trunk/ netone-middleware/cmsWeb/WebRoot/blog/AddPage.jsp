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

		<title>添加页</title>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--戴新禄- 2009-2-8-修改/的方向  -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<script type="text/javascript" src="include/js/calendarSec.js"></script>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/checkrs.js"></SCRIPT>
		<script type="text/javascript">
		function todo(){
		
		var number=/^(-\+)?([0-9]+)\.?([0-9]*)$/;
		
	 	//var len=$('len').value;   原始代码  2009-2-08 戴新禄
	     var len=document.getElementById("len").value; //改后代码   2009-2-08  戴新禄
	   
	   
	 	//var wid=$('wid').value;   原始代码   2009-2-08  戴新禄
	      var wid=document.getElementById("wid").value;  //改后代码   2009-2-08  戴新禄
	 
	 	//var name=$('name').value;    原始代码   2009-2-08  戴新禄
	 	var name=document.getElementById("name").value;  //改后代码   2009-2-08  戴新禄
	 	
	 	//var naturalname=$('naturalname').value;  原始代码   2009-2-08  戴新禄
	 	var naturalname=document.getElementById("naturalname").value;  //改后代码   2009-2-08 2009-2-08  戴新禄
	 	
	 	if(name==''){
	 		alert('缺少标题');
	 		return;
	 	}
		var rs=checkNaturalname(naturalname);
		if(rs!=''){
				alert(rs);
				return;
			}
	
	 	if(!len.match(number)){
	 		alert('长度非数字');
	 		return;
	 	}
	 	if(!wid.match(number)){
	 		alert('宽度非数字');
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
		
									<h4>
							新增页
						</h4>
			<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="4" WIDTH="100%">
				<tr>
					<td width='15%'>
						名称
					</td>
					<td>
					<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='text' name="naturalname" id="naturalname" value="">[格式：字母开头后接字母或数字或下划线]
					</td>
				</tr>

				<tr>
					<td>
						中文名称
					</td>
					<td>
					<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='text' name="name" id="name" value="">
					</td>
				</tr>
				<tr>
					<td>
						高度
					</td>
					<td>
					<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='text' name="len" id="len" value="80">
					</td>
				</tr>
				<tr>
					<td>
						宽度
					</td>
					<td>
					<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='text' name="wid" id="wid" value="200">
					</td>
				</tr>
				<tr>
					<td>
						头链接
					</td>
					<td>
					<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='text' name="linkx" id="linkx" value="">[名称#URL]
					</td>
				</tr>
				<tr>
					<td>
						类型
					</td>
					<td>
					<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='radio' name="types" id="types" value="1" checked>
						普通页
						<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='radio' name="types"  id="types" value="2">
						无标题
						<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='radio' name="types" id="types"  value="3">
						无边框
						<!--戴新禄- 2009-2-8-增加id  -->
						<INPUT type='radio' name="types"  id="types" value="4">
						无标题无边框
					</td>
				</tr>
			</TABLE>
           <!--戴新禄- 2009-2-8-增加id  -->
			<input type='hidden' name="modelid" id="modelid"
				value='<%=request.getParameter("modelid")%>'>
			<br>
			<hr>
			<!--戴新禄- 2009-2-8-增加id  -->
			&nbsp;是否使用高速缓存:
			<INPUT type='checkbox' name="cache" id="cache" value="1">
			<br>
			<br>
			&nbsp;缓存生命周期:
			<!--戴新禄- 2009-2-8-增加id  -->
			<input type='text' name='cachecycle' od="cache" value='12'>
			&nbsp;小时
			<br>
			<br>
			&nbsp;页面有效期从:
			<!--戴新禄- 2009-2-8-增加id  -->
			<input type="text" name="availablefrom" id="availablefrom" value="" class="textinput_td"
				onfocus="calendar()">
			<br>
			<br>
			&nbsp;页面有效期至:
			<!--戴新禄- 2009-2-8-增加id  -->
			<input type="text" name="availableto" id="availableto" value="" class="textinput_td"
				onfocus="calendar()">

			<br>
			<br>
			&nbsp;
			<INPUT type='button' value='提交' onClick='todo()'>
		</FORM>
	</body>
</html>
