<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

		<title>�޸�ҳ</title>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<script type="text/javascript" src="include/js/calendarSec.js"></script>
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
			action="/cmsWeb/servlet/AjaxUpdatePageSvl?pagepath=<%=request.getParameter("pagepath")%>"
			METHOD="POST">
			<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="4" WIDTH="90%">
				<input type='hidden' name='cellid' value="${cell.cellid}">
				<input type='hidden' name='ope' value="done">
				<tr>
					<td>
						����
					</td>
					<td>
						<INPUT type='text' name="naturalname" value="${cell.naturalname}"
							readonly>
					</td>
				</tr>

				<tr>
					<td>
						��������
					</td>
					<td>
						<INPUT type='text' name="name" value="${cell.cellname}">
					</td>
				</tr>
				<tr>
					<td>
						�߶�
					</td>
					<td>
						<INPUT type='text' name="len" value="${cell.height}">
					</td>
				</tr>
				<tr>
					<td>
						���
					</td>
					<td>
						<INPUT type='text' name="wid" value="${cell.width}">
					</td>
				</tr>
								<tr>
					<td>
						ͷ����
					</td>
					<td>
						<INPUT type='text' name="linkx" value="${cell.extendattribute}">[����#URL]
					</td>
				</tr>
				<tr>
					<td>
						����
					</td>
					<td>
						<INPUT type='radio' name="types" value="1" <c:if test="${cell.types==1}">checked</c:if>>
						��ͨҳ
						<INPUT type='radio' name="types" value="2" <c:if test="${cell.types==2}">checked</c:if>>
						�ޱ���
						<INPUT type='radio' name="types" value="3" <c:if test="${cell.types==3}">checked</c:if>>
						�ޱ߿�
						<INPUT type='radio' name="types" value="4" <c:if test="${cell.types==4}">checked</c:if>>
						�ޱ����ޱ߿�
					</td>
				</tr>
			</TABLE>

			<input type='hidden' name="modelid"
				value='<%=request.getParameter("modelid")%>'>
			<br>
			<hr>
			&nbsp;�Ƿ�ʹ�ø��ٻ���:
			<INPUT type='checkbox' name="cache" value="1"
				<c:if test="${cell.intime==1}">checked</c:if>>
			<br>
			<br>
			&nbsp;������������:
			<input type='text' name='cachecycle' value='${cell.cachecycle}'>
			&nbsp;Сʱ
			<br>
			<br>
			&nbsp;ҳ����Ч�ڴ�:
			<input type="text" name="availablefrom" value="${fn:substringBefore(cell.availablefrom,'.')}" class="textinput_td"
				onfocus="calendar()">
			<br>
			<br>
			&nbsp;ҳ����Ч����:
			<input type="text" name="availableto" value="${fn:substringBefore(cell.availableto,'.')}" class="textinput_td"
				onfocus="calendar()">
			<br>
			<br>

			&nbsp;
			<INPUT type='button' value='�ύ' onClick='todo()'>
		</FORM>
	</body>
</html>
