<%@ page contentType="text/html; charset=GBK"%>
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
		<title>�޸��ӽ��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<c:if test="${ModifySuccess == 'y'}">
			<script type="text/javascript">
				alert("�޸ĳɹ���")
				afterEdit();
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�")
			</script>
		</c:if>
		<div style="width: 100%;height: 100%">
			<form action="ApplistModifySvl?task=editsave" method="post"
				name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${app.id}">
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;Ч
						</td>
						<td>

							<input type="checkbox" name="active" value="1"
								<c:if test="${app.active=='1'}">checked</c:if> />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="naturalname" value="${app.naturalname}"
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>
						<td>
							<input type="text" name="name" value="${app.name}"
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<select name="apptype" value="${app.apptype}">
								<c:forEach items="${selType}" var="list">
									<option value='${list[0]}' <c:if test="${app.apptype==list[0]}">selected</c:if>>
										${list[1]}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>

					<tr>
						<td width="15%">
							��չ����
						</td>
						<td>
							<textarea rows="8" cols="60" name="extendattribute">${app.extendattribute}</textarea>
						</td>
					</tr>
					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<textarea rows="4" cols="60" name="description">${app.description}</textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="submit" name="btnnew" value="�� ��" class="butt">
					<input type="button" name="btncancel" value="ȡ ��"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
