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

		<title>��������</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/rsinfo/dept2/right.js"></script>
		<script type="text/javascript"
			src="include/js/rsinfo/dept2/rbacindex.js"></script>
		<script type="text/javascript" src="include/js/page.js"></script>
	</head>
	<body style="font-size: 12px; margin: 22px">
		${paramMap.alertMsg}
		<div style="width: 100%; height: 100%">
			<form action="" method="post" name="form1">
				<input type="hidden" name="jsppath" value="<%=path%>">
				<input type="hidden" name="parentdir" value="${upo.parentdir}" />
				<input type="hidden" name="appid" value="${upo.appid}" />
				<input type="hidden" name="ou" value="${upo.ou}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<input type="hidden" name="inclusion" value="${upo.inclusion}" />
				<input type="hidden" name="extendattribute"
					value="${upo.extendattribute}" />
				<input type="hidden" name="aggregation" value="${upo.aggregation}" />
				<input type="hidden" name="created" value="${upo.created}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td colspan='2'>
							
							<input type="button" name="btnmodify" value="�� ��"
								onclick="doModify();">
							&nbsp;
							<input type="button" name="btndelete" value="ɾ ��"
								onclick="doDelete();">
							&nbsp;
							<input type="button" name="btncreate" value="�½���ɫĿ¼"
								onclick="changeToCreateModel();">
						</td>
					</tr>
					<tr style='display:none'>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="naturalname" style="width: 350"
								value="${upo.naturalname}" class="textinput_td"
								readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>
						<td>
							<input type="text" name="name" style="width: 350"
								value="${upo.name}" class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="actionurl" style="width: 350"
								value="${upo.actionurl}" class="textinput_td" />
						</td>
					</tr>

				</table>
			
									&nbsp;
					<input type="button" value="�� ��" onclick="rbacadd();" >
					&nbsp;
					<input type="button" value="�� ��" onclick="rbacmodify();"
						>
					&nbsp;
					<input type="button" value="ɾ ��" onclick="rbacdel();" >
				<br>
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="hang_table">
					<tr>
						<td width="70" class="td_titt_bg">
							<input type="checkbox" name="checkall" onclick="docheck();">
							ѡ��
						</td>
						<td class="td_titt_bg" nowrap>
							��ɫ����
						</td>
						<td class="td_titt_bg" nowrap>
							��������
						</td>
						<td class="td_titt_bg" nowrap>
							��ɫ����
						</td>
						<td class="td_titt_bg">
							��ɫ����
						</td>
					</tr>
					<c:forEach items="${listinfo}" var="getCol">
						<tr>
							<td>
								<input type="checkbox" name="chkid" value="${getCol.id}"${checkedMap[getCol.id]}>
							</td>
							<td height="21">
								<a href=javascript:link("${getCol.id}")>${getCol.naturalname}</a>
							</td>
							<td height="21">
								${getCol.name}
							</td>
							<td height="21">
								${getCol.belongingness}
							</td>
							<td height="21">
								${getCol.description}
							</td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
			</script>
				<br>
				<table width="90%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="line_hang">
					<tr>
						<td></td>
					</tr>
				</table>
				<br>
			</form>
		</div>
		<c:if test="${ModifySuccess == 'y'}">
			<script type="text/javascript">
				afterModify();
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�");
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'y'}">
			<script type="text/javascript">
				afterDelete();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("����ɾ��ʧ��,����ò������Ƿ��н�ɫ�����Ƿ����ӽڵ�!");
			</script>
		</c:if>
		<c:if test="${DelSuccess == 'y'}">
			<script type="text/javascript">
				afterDel();
			</script>
		</c:if>
		<c:if test="${MoveSuccess == 'y'}">
			<script type="text/javascript">
				afterMove();
			</script>
		</c:if>
	</body>
</html>
