<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="BaseRoot.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";		

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>huamnindex</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link type="text/css" rel="stylesheet" href="css/css.css">
		<script type="text/javascript" src="include/js/page.js"></script>
		<script type="text/javascript">
			var rootpath='${rootpath}';
		</script>
		<script type="text/javascript" src="include/js/human/humanindex.js"></script>
	</head>
	
	<body style="margin: 22px">
		${paramMap.alertMsg}
		<form action="<%=path%>/humanIndex.do" name="form1" method="post">
			<input type="hidden" name="id" value="${paramMap.id}">
			<input type="hidden" name="task" value="${paramMap.task}">
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr>
					<td align='right'>
						����
					</td>
					<td width="100" align="center">
						<input type="text" name="sname" value="${paramMap.sname}"
							class="textinput_td">
					</td>
					<td align='right'>
						����
					</td>
					<td width="100" align="center">
						<input type="text" name="sdepartment"
							value="${paramMap.sdepartment}" class="textinput_td"
							readonly="readonly">
						<input type="hidden" name="sdepartment2"
							value="${paramMap.sdepartment2}">
					</td>
					<td align='center'>
						<a href='javascript:deptselect();'><font color='blue'>ѡ
								��</font> </a>&nbsp;
						<a
							href="javascript:document.all.sdepartment.value=' ';document.all.sdepartment2.value='';undefined;"><font
							color='blue'>�� ��</font>
					</td>
					<td width="100" align='right'>
						<input type="button" value="�� ѯ" onclick="doSearch();"
							class="butt">
					</td>
				</tr>
			</table>
			<br>
			<table id="hang_table" width="96%" border="0" align="center"
				cellpadding="0" cellspacing="1">
				<tr>
					<td width="70" class="td_titt_bg">
						<input type="checkbox" name="checkall" onclick="docheck();">
						ѡ��
					</td>
					<td class="td_titt_bg">
						����
					</td>
					<td class="td_titt_bg">
						�ʺ�
					</td>
					<td class="td_titt_bg">
						����
					</td>
					<td class="td_titt_bg">
						״̬
					</td>
					<td class="td_titt_bg">
						ϸĿ
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr>
						<td>
							<input type="checkbox" name="chkid" value="${getCol.description}">
						</td>
						<td height="21">
							${getCol.name}
						</td>
						<td height="21">
							${getCol.description}
						</td>
						<td height="21">
							${getCol.faxNO}
						</td>
						<td height="21">
								<c:if test="${getCol.password == '9$9$'||getCol.password=='8846D8C4493431BDF845BF8CCB5F23FA'}">
                                                                 ����
                                                                </c:if>
								<c:if test="${getCol.password!= '9$9$'&&getCol.password!='8846D8C4493431BDF845BF8CCB5F23FA'}">
                                                                 ����
                                                                </c:if>
						</td>
						<td height="21">
							<a href=javascript:link("${getCol.description}")>�鿴1</a>
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
			<div align="center">

				&nbsp;
				<input type="button" value="�� ��" onclick="humanadd();" class="butt">
				&nbsp;
				<input type="button" value="�� ��" onclick="humanmodify();"
					class="butt">
				&nbsp;
				<input type="button" value="ɾ ��" onclick="humandel();" class="butt">
				&nbsp;
				<input type="button" value="�� ��" onclick="humancopy();" class="butt">
				&nbsp;
				<input type="button" value="�� ��" onclick="humanfileinput();"
					class="butt">
				&nbsp;
				<input type="button" value="�� ��" onclick="humanfileoutput();"
					class="butt">
				&nbsp;
				<input type="button" value="��������" onclick="humanpass();"
					class="butt">
			</div>
		</form>
	</body>
</html>
