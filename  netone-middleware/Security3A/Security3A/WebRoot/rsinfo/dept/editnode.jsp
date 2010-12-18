<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page import="oe.frame.web.form.RequestUtil" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../human/BaseRoot.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	RequestUtil.setParamMapToRequest(request);
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>�޸��ӽ��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/prototype.js"></script>
		<script type="text/javascript" src="include/js/rsinfo/dept/right.js"></script>

		<script type="text/javascript">
		    var checktypex='${upo.objecttype}';
			var rootpath='${rootpath}';
		</script>
		
				<script type="text/javascript"
			src="include/js/rsinfo/dept/humanindex.js"></script>
	</head>
	<body style="font-size: 12px; margin: 22px"
		onload="editview('${upo.extendattribute}','${upo.objecttype}');">
		${paramMap.alertMsg}
		
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				opener.afterCreate();
			</script>
		</c:if>		
		<c:if test="${ModifySuccess == 'y'}">
			<script type="text/javascript">
				alert("�޸ĳɹ���")
				opener.location.reload();
				opener.afterModify('${upo.name}');
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�")
			</script>
		</c:if>
		<div style="width: 100%; height: 100%">
			<form action="rsinfo/dept/DepartmentModify.do?task=editsave"
				method="post" name="form1">
				<div align="left">
					<input type="button" name="btnnew" value="�½�" class="butt"
						onClick='opener.changeToCreateModel()'>
					<input type="button" name="btnnew" value="����" class="butt"
						onClick='updatehumanx()'>
			    <!-- �����ⲿϵͳ���� -->
				</div>
				<br>
				<input type="hidden" name="id" value="${upo.id}" />
				<input type="hidden" name="appid" value="${upo.appid}" />
				<input type="hidden" name="ou" value="${upo.ou}" />
				<input type="hidden" name="parentdir" value="${upo.parentdir}" />
				<input type="hidden" name="inclusion" value="${upo.inclusion}" />
				<input type="hidden" name="extendattribute"
					value="${upo.extendattribute}" />
				<input type="hidden" name="aggregation" value="${upo.aggregation}" />
				<input type="hidden" name="created" value="${upo.created}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr id='pingying'>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="naturalname" value="${upo.naturalname}"
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>
						<td>
							<input type="text" name="name" value="${upo.name}"
								class="textinput_td" />
						</td>
					</tr>
					<tr id='codeline'>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="actionurl" value="${upo.actionurl}"  id='actionurl'  
								class="textinput_td"/>
						</td>
					</tr>
                    <!-- �����ⲿϵͳ���� -->

				</table>
				<br>

			</form>
		</div>
	</body>
</html>
