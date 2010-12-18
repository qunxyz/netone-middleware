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
		<title>日志查询</title>
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
						登录名:
					</td>
					<td width="15%">
						<input type="text" name="userid" value="${paramMap.userid}"
							class="textinput_td">
					</td>
					<td width="7%" align="right">
						起始时间:
					</td>
					<td width="15%">
						<input type="text" name="beginTime" value="${paramMap.beginTime}"
							onfocus="calendar()" class="textinput_td">
					<td width="7%" align="right">
						结束时间:
					</td>
					<td width="15%">
						<input type="text" name="endTime" value="${paramMap.endTime}"
							onfocus="calendar()" class="textinput_td">
					</td>
				</tr>
				<tr>
					<td width="7%" align="right">
						操作项:
					</td>
					<td width="15%">
						<input type="text" name="operationid"
							value="${paramMap.operationid}" class="textinput_td">
					</td>
					<td width="7%" align="right">
						操作结果:
					</td>
					<td width="15%">
						<select name="resultinfo" class="table_select_page">
							<option value="">
								-----请选择-----
							</option>
							<option value="success" ${paramMap.resultinfo=='success'?"selected":""}>
								成功
							</option>
							<option value="error" ${paramMap.resultinfo=='error'?"selected":""}>
								失败
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
				<input type="submit" value="查 询" class="butt" />
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
						登录名
					</td>
					<td class="td_titt_bg">
						操作时间
					</td>
					<td class="td_titt_bg">
						操作项
					</td>
					<td class="td_titt_bg">
						操作结果
					</td>
					<td class="td_titt_bg">
						备注
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
							<c:if test="${rs.resultinfo == 'success'}">成功</c:if>
							<c:if test="${rs.resultinfo == 'error'}">失败</c:if>
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
