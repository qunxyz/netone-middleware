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

		<title>���ҳ</title>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--����»- 2009-2-8-�޸�/�ķ���  -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<script type="text/javascript" src="include/js/calendarSec.js"></script>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/checkrs.js"></SCRIPT>
		<script type="text/javascript">
		function todo(){
		
		var number=/^(-\+)?([0-9]+)\.?([0-9]*)$/;
		
	 	//var len=$('len').value;   ԭʼ����  2009-2-08 ����»
	     var len=document.getElementById("len").value; //�ĺ����   2009-2-08  ����»
	   
	   
	 	//var wid=$('wid').value;   ԭʼ����   2009-2-08  ����»
	      var wid=document.getElementById("wid").value;  //�ĺ����   2009-2-08  ����»
	 
	 	//var name=$('name').value;    ԭʼ����   2009-2-08  ����»
	 	var name=document.getElementById("name").value;  //�ĺ����   2009-2-08  ����»
	 	
	 	//var naturalname=$('naturalname').value;  ԭʼ����   2009-2-08  ����»
	 	var naturalname=document.getElementById("naturalname").value;  //�ĺ����   2009-2-08 2009-2-08  ����»
	 	
	 	if(name==''){
	 		alert('ȱ�ٱ���');
	 		return;
	 	}
		var rs=checkNaturalname(naturalname);
		if(rs!=''){
				alert(rs);
				return;
			}
	
	 	if(!len.match(number)){
	 		alert('���ȷ�����');
	 		return;
	 	}
	 	if(!wid.match(number)){
	 		alert('��ȷ�����');
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
							����ҳ
						</h4>
			<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="4" WIDTH="100%">
				<tr>
					<td width='15%'>
						����
					</td>
					<td>
					<!--����»- 2009-2-8-����id  -->
						<INPUT type='text' name="naturalname" id="naturalname" value="">[��ʽ����ĸ��ͷ�����ĸ�����ֻ��»���]
					</td>
				</tr>

				<tr>
					<td>
						��������
					</td>
					<td>
					<!--����»- 2009-2-8-����id  -->
						<INPUT type='text' name="name" id="name" value="">
					</td>
				</tr>
				<tr>
					<td>
						�߶�
					</td>
					<td>
					<!--����»- 2009-2-8-����id  -->
						<INPUT type='text' name="len" id="len" value="80">
					</td>
				</tr>
				<tr>
					<td>
						���
					</td>
					<td>
					<!--����»- 2009-2-8-����id  -->
						<INPUT type='text' name="wid" id="wid" value="200">
					</td>
				</tr>
				<tr>
					<td>
						ͷ����
					</td>
					<td>
					<!--����»- 2009-2-8-����id  -->
						<INPUT type='text' name="linkx" id="linkx" value="">[����#URL]
					</td>
				</tr>
				<tr>
					<td>
						����
					</td>
					<td>
					<!--����»- 2009-2-8-����id  -->
						<INPUT type='radio' name="types" id="types" value="1" checked>
						��ͨҳ
						<!--����»- 2009-2-8-����id  -->
						<INPUT type='radio' name="types"  id="types" value="2">
						�ޱ���
						<!--����»- 2009-2-8-����id  -->
						<INPUT type='radio' name="types" id="types"  value="3">
						�ޱ߿�
						<!--����»- 2009-2-8-����id  -->
						<INPUT type='radio' name="types"  id="types" value="4">
						�ޱ����ޱ߿�
					</td>
				</tr>
			</TABLE>
           <!--����»- 2009-2-8-����id  -->
			<input type='hidden' name="modelid" id="modelid"
				value='<%=request.getParameter("modelid")%>'>
			<br>
			<hr>
			<!--����»- 2009-2-8-����id  -->
			&nbsp;�Ƿ�ʹ�ø��ٻ���:
			<INPUT type='checkbox' name="cache" id="cache" value="1">
			<br>
			<br>
			&nbsp;������������:
			<!--����»- 2009-2-8-����id  -->
			<input type='text' name='cachecycle' od="cache" value='12'>
			&nbsp;Сʱ
			<br>
			<br>
			&nbsp;ҳ����Ч�ڴ�:
			<!--����»- 2009-2-8-����id  -->
			<input type="text" name="availablefrom" id="availablefrom" value="" class="textinput_td"
				onfocus="calendar()">
			<br>
			<br>
			&nbsp;ҳ����Ч����:
			<!--����»- 2009-2-8-����id  -->
			<input type="text" name="availableto" id="availableto" value="" class="textinput_td"
				onfocus="calendar()">

			<br>
			<br>
			&nbsp;
			<INPUT type='button' value='�ύ' onClick='todo()'>
		</FORM>
	</body>
</html>
