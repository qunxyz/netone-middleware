<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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

		<title>������Ϣ</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="rinclude/page.js"></script>
		<script type="text/javascript">
		function sendmessage(){
			if(!document.all.title.value){
				alert("��������⣡");
				return ;
			}
			if(!document.all.content.value){
				alert("����������!");
				return ;
			}
			if(!document.all.recevier.value){
				alert("��ѡ�������!");
				return ;
			}
			if(document.all.ext.value!=""){
				var ext = document.all.ext;
				var ids = "";
				for(var i=0;i<ext.options.length;i++){
					if(i==0){
						ids = ext.options[i].value+"="+ext.options[i].text;
					} else {
						ids = ids+","+ext.options[i].value+"="+ext.options[i].text;
					}
				}
				document.all.extattr3.value = ids;
			}
			document.all.task.value= "sendmessage";
			document.forms[0].submit();
		}
		function addSelectedFile(options){
			var ext = document.all.ext;
			if(options){
				for(var i=0;i<options.length;i++){
					var op = new Option(options[i].text,options[i].value);
					ext.add(op);
				}
			}
		}
		function delfile(){
			var ext = document.all.ext;
			var i = ext.selectedIndex;
			if(i != -1){
				ext.remove(i);
			}
		}
		var selectmode='human';
		function openuserselect(){
			window.open('<%=path%>/MSelectSvl?appname=DEPT&pagename=human','��Աѡ��')
		    selectmode='human';
		}
		function openfileselect(){
			window.open('<%=path%>/MSelectSvl?appname=PIC,FILE&pagename=file','����ѡ��')
		    selectmode='file';
		}
		function mselected(options){
			if(options){
				var users = "";
				var userids = "";
				for(var i=0;i<options.length;i++){
					userids += options[i].value + "[" +options[i].text+ "],";
				}
				if(selectmode=='human'){
				  document.all.recevier.value = userids  ;
				}else{
				  addSelectedFile(options);
				}
			}
		}
		function manageresourse(){
		    window.open('<%=path%>/ApplistRightSvl?pagename=applist','��Դ����');
		}
	</script>
	</head>

	<body>
		<br>
		${paramMap.alertMsg }
		<form action="bussform/SendMessage.do" method="post" name="form1">
			<input type="hidden" name="task" value="">

			<table width="96%" border="0" align="center" cellpadding="3"
				cellspacing="1">
				<tr>
					<td width="50" nowrap>
						����
					</td>
					<td align="left">
						<input type="text" name="title" value="${title}"
							class="textinput_td">
						<input type="hidden" name="workid" value="${workid}">
					</td>
				</tr>
				<tr>
					<td width="50" nowrap>
						������
					</td>
					<td align="left">
						<input type="text" name="recevier" value="${recevier}"
							class="textinput_td">
						&nbsp;
						<a href='javascript:openuserselect()'>ѡ ��</a>


					</td>
				</tr>
				<tr>
					<td width="50" nowrap>
						����
					</td>
					<td align="left">
						<select name="ext">

						</select>
						<a href='javascript:openfileselect()'>ѡ ��</a>
						&nbsp;
						<a href='javascript:delfile()'>ɾ ��</a>
						&nbsp;&nbsp;
						<a href='javascript:manageresourse()'>��Դ����</a>
						<input type="hidden" name="extattr3" value="${extattr3}">
					</td>
				</tr>
				<tr>
					<td width="50" nowrap>
						����
					</td>
					<td align="left">
						<textarea rows="5" cols="60" name="content">${content}</textarea>
					</td>
				</tr>

				<tr style='display: none'>
					<td width="50" nowrap>
						����
					</td>
					<td align="left">
						<input type="hidden" name="extattr4" value="${extattr4}"
							class="textinput_td">
					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<input type="button" value="�� ��" class="butt"
					onclick="sendmessage();">
			</div>
			<script type="text/javascript">
				var extattr3 = document.all.extattr3.value;
				var ext = document.all.ext;
				if(extattr3!=""){
					extattr3 = extattr3.split(",");
					for(var i=0;i<extattr3.length;i++){
						var files = extattr3[i].split("=");
						if(files[1]!=""){
							var option = new Option(files[1],files[0]);
							ext.add(option);
						}
					}
				}
			</script>
		</form>
	</body>
</html>
