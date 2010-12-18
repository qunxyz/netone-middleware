<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>rbacindex.jsp</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link type="text/css" rel="stylesheet" href="css/css.css">
		<script type="text/javascript" src="include/js/page.js"></script>
		<script type="text/javascript" src="include/js/rbac/rbacindex.js"></script>
	</head>

	<body style="margin: 22px">
		<form action="rbac/rbacIndex.do" name="form1" method="post">
			<input type="hidden" name="task" value="${paramMap.task}">
			<input type="hidden" name="belongingness"
				value="${paramMap.belongingness}" />
			<input type="hidden" name="appid" value="${paramMap.appid}" />
			<input type="hidden" name="source" value="${paramMap.source}">
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="5">
				<tr>
					<td width="10%" align="right">
						��ɫ����:
					</td>
					<td width="20%">
						<input type="text" name="sname" value="${paramMap.sname}"
							class="textinput_td">
					</td>
					<td width="10%" align="right">
						��ɫ����:
					</td>
					<td width="20%">
						<input type="text" name="sclass" value="${paramMap.sclass}"
							class="textinput_td" readonly="readonly">
						<input type="hidden" name="sclass2" value="${paramMap.sclass2}">
					</td>
					<td>
						<a href='javascript:deptselect();'><font color='blue'>ѡ
								��</font> </a>&nbsp;
						<a
							href="javascript:document.all.sclass.value=' ';document.all.sclass2.value='';undefined;"><font
							color='blue'>�� ��</font>
					</td>
					<td>
						<input type="button" value="�� ѯ" onclick="rbacsearch();"
							class="butt">
					</td>
				</tr>
			</table>
			<br>

			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="1" id="hang_table">
				<tr>
					<td width="70" class="td_titt_bg">
						<input type="checkbox" name="checkall" onclick="docheck();">
						ѡ��
					</td>
					<td class="td_titt_bg">
						��ɫ����
					</td>
					<td class="td_titt_bg">
						��������
					</td>
					<td class="td_titt_bg">
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
			<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
			</script>
		</form>
		<c:if test="${result != null}">
			<script type="text/javascript">
				alert("ѡ��Ľ�ɫ���й����û�����,������ɾ��!");
				//if(confirm("ѡ��Ľ�ɫ���й����û�����,�Ƿ�ɾ��?")){
				//	document.all.task.value = "";
				//	form1.action = "rbac/rbacDel.do";
				//	form1.submit();
				//}
			</script>
		</c:if>
		<br>
		<table width="90%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="line_hang">
			<tr>
				<td></td>
			</tr>
		</table>
		<br>
		<div align="center">

			&nbsp;
			<input type="button" value="�� ��" onclick="rbacadd();" class="butt">
			&nbsp;
			<input type="button" value="�� ��" onclick="rbacmodify();" class="butt">
			&nbsp;
			<input type="button" value="ɾ ��" onclick="rbacdel();" class="butt">
		</div>
	</body>
</html>
