<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
				//����ѡ��ҳ��
		function searchtree() {
			window.open("SelectSvl?appname=MAPP.MAPP&pagename="+document.all.pagename.value,'_blank',' top=100, left=100, toolbar=no,height=400, width=550');
		}
		
		function sselected(text,value){
		   	document.getElementById("objecttype").value=text+"["+value+"]";
		}
		</script>
	</head>

	<body style="font-size: 12px;margin: 22px">
		<c:if test="${result == 'y'}">
			<script type="text/javascript">
				alert("�½��ɹ���")
				opener.search();
		</script>
		</c:if>
		<c:if test="${result == 'n'}">
			<script type="text/javascript">
				alert("${errorinfo}")
		</script>
		</c:if>
		<form action="" method="post" name="form1"
			enctype="multipart/form-data">
			<input type="hidden" name="pagename" value="${pagename}" />
			<input type="hidden" name="dirid" value="${dirid}">
			<input type="hidden" name="appid" value="${appid}">
			<input type="hidden" name="filename" value="${filename}">
			<table width="96%" border="1" align="center" cellpadding="0"
				cellspacing="0" id="lie_table">
				<tr>
					<td width="15%">
						��&nbsp;&nbsp;&nbsp;&nbsp;Ч
					</td>
					<td>
						<input type="checkbox" name="active" value="1" checked />
					</td>
				</tr>
				<tr>
					<td>
						ͼ&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td width="80%">
						<input type="file" onchange="autofile();" name="files">
					</td>
				</tr>
				<tr>
					<td>
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td>
						<input type="text" name="naturalname" value=""
							class="textinput_td">
					</td>
				</tr>
				<tr>
					<td>
						��������
					</td>
					<td>
						<input type="text" name="name" value="" class="textinput_td">
					</td>
				</tr>

					<tr>
						<td width="15%">
							<a href="javascript:searchtree();"><font color='blue'>ѡ����</font>
							</a>
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value=""
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<input type="text" name="actionurl" value="${upo.actionurl}"
								class="textinput_td" />
						</td>
					</tr>
				<tr  style='display:none'>
					<td width="15%">
						��չ����
					</td>
					<td>
						<textarea rows="8" cols="60" name="extendattribute"></textarea>
					</td>
				</tr>
				<tr>
					<td width="15%">
						����
					</td>
					<td>
						<textarea rows="2" cols="60" name="description"></textarea>
					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<input type="button" value="���" onclick="uploadfile();" class="butt">
				&nbsp;
				<input type="button" value="ȡ��" onclick="javascript:window.close();"
					class="butt">
			</div>
		</form>
	</body>
</html>
