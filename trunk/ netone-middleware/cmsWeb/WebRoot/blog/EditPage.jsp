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

		<title>�޸�ҳ����</title>
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
	 		alert('ȱ�ٱ���');
	 		return;
	 	}
	 	
	 	if(naturalname==''){
	 		alert('��ʾ��');
	 		return;
	 	}
	
	 	if(!len.match(number)){
	 		alert('���ȷ�����');
	 		return;
	 	}
	 	if(!len.match(number)){
	 		alert('���ȷ�����');
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
						����
					</td>
					<td>
						<INPUT type='text' name="naturalname" value="">
					</td>
				</tr>

				<tr>
					<td>
						��������
					</td>
					<td>
						<INPUT type='text' name="name" value="">
					</td>
				</tr>
				<tr>
					<td>
						�߶�
					</td>
					<td>
						<INPUT type='text' name="len" value="80">
					</td>
				</tr>
				<tr>
					<td>
						���
					</td>
					<td>
						<INPUT type='text' name="wid" value="200">
					</td>
				</tr>
				<tr>
					<td>
						ͷ����[����:URL]
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
			&nbsp;�Ƿ�ʹ�ø��ٻ���:
			<INPUT type='checkbox' name="cache" value="1">
			<br><br>
			&nbsp;������������:
			<input type='cacheCycle' value='12'>
			&nbsp;Сʱ
			<br><br>
			&nbsp;ҳ����Ч�ڴ�:
			<input type="text" name="begintime" value="" class="textinput_td"
				onfocus="calendar()">
			<br><br>
			&nbsp;ҳ����Ч����:
			<input type="text" name="endtime" value="" class="textinput_td"
				onfocus="calendar()">


			<br>
			<br>
			&nbsp;<INPUT type='button' value='�ύ' onClick='todo()'>
		</FORM>
	</body>
</html>
