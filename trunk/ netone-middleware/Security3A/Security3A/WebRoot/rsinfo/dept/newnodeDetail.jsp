<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page import="oe.frame.web.form.RequestUtil" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>�½��ӽڵ�</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/rsinfo/dept/right.js"></script>
	</head>
	<body style="font-size: 12px; margin: 22px">
		${paramMap.alertMsg}
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				alert("�½��ɹ���")
				opener.afterCreate();
			</script>
		</c:if>
		<c:if test="${CreateSuccess == 'n'}">
			<script type="text/javascript">
				alert("�½�Ŀ¼ʧ�ܣ�")
			</script>
		</c:if>
		<div style="width: 100%; height: 100%">
			<form action="rsinfo/dept/DepartmentAdd.do?task=addsave"
				method="post">
				<input type="hidden" name="id" value="${paramMap.id}" />
				<input type="hidden" name="appid" value="${paramMap.appid}" />
				<input type="hidden" name="ou" value="${paramMap.ou}" />
				<div align="left">
					<input type="button" name="btnnew" value="�� ��" onclick="newoper();"
						class="butt">
					<input type="button" name="btncancel" value="ȡ ��"
						onclick="javascript:window.close();" class="butt">
				</div>
				<br>
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							����ƴ��
						</td>
						<td>
							<input type="text" name="naturalname" value=""
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>
						<td>
							<input type="text" name="name" value="" class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							���ű���
						</td>
						<td>
							<input type="text" name="actionurl" value="" class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>  
						<td>  
							<input type="hidden" name='objecttype' value='yxbm' > Ӫ������
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
