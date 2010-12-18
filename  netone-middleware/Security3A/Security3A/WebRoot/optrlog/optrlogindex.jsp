<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
		<title>��־��ѯ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link type="text/css" rel="stylesheet" href="css/css.css">
		<script type="text/javascript" src="include/js/page.js"></script>
		<script type="text/javascript" src="include/js/calendar.js"></script>
	</head>

	<body style="margin: 10px;font-size: 12px">
		<form action="optrlog/optrLogAction.do" name="form1" method="post">
			<br>
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="1" id="lie_table">
				<tr>
					<td width="7%" align="right">
						��¼��:
					</td>
					<td width="15%">
						<input type="text" name="userid" value="${paramMap.userid}"
							class="textinput_td">
					</td>
					<td width="7%" align="right">
						��ʼʱ��:
					</td>
					<td width="15%">
						<input type="text" name="beginTime" value="${paramMap.beginTime}"
							onfocus="calendar()" class="textinput_td">
					<td width="7%" align="right">
						����ʱ��:
					</td>
					<td width="15%">
						<input type="text" name="endTime" value="${paramMap.endTime}"
							onfocus="calendar()" class="textinput_td">
					</td>
				</tr>
				<tr>
					<td width="7%" align="right">
						������:
					</td>
					<td width="15%">
						<input type="text" name="operationid"
							value="${paramMap.operationid}" class="textinput_td">
					</td>
					<td width="7%" align="right">
						�������:
					</td>
					<td width="15%">
						<select name="resultinfo" class="table_select_page">
							<option value="">
								-----��ѡ��-----
							</option>
							<option value="success" ${paramMap.resultinfo=='success'?"selected":""}>
								�ɹ�
							</option>
							<option value="error" ${paramMap.resultinfo=='error'?"selected":""}>
								ʧ��
							</option>
						</select>
					</td>
					<td width="7%" align="right">
					</td>
					<td width="15%">

					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<input type="submit" value="�� ѯ" class="butt" />
			</div>
			<br>
			<table width="90%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="line_hang">
				<tr>
					<td></td>
				</tr>
			</table>
			<br>
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="1" id="hang_table">
				<tr>
					<td class="td_titt_bg">
						��¼��
					</td>
					<td class="td_titt_bg">
						����ʱ��
					</td>
					<td class="td_titt_bg">
						������
					</td>
					<td class="td_titt_bg">
						�������
					</td>
					<td class="td_titt_bg">
						��ע
					</td>
				</tr>
				<c:forEach items="${rslist}" var="rs">
					<tr>
						<td height="21">
							${rs.userid }
						</td>
						<td height="21">
							${rs.operatetime}
						</td>
						<td height="21">
							${rs.operationid}
						</td>
						<td height="21">
							<c:if test="${rs.resultinfo == 'success'}">�ɹ�</c:if>
							<c:if test="${rs.resultinfo == 'error'}">ʧ��</c:if>
						</td>
						<td height="21" width="300">
							${rs.remark}
						</td>
					</tr>
				</c:forEach>
			</table>
			<script type="text/javascript">
			var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
			pginfo.write();
		</script>
		</form>
	</body>
</html>
