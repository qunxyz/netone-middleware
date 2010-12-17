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

		<title>公告全部</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/oe.css">
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
		<script type="text/javascript">
		//查询逻辑可以自定义扩展条件,SQL的子条件,日期属性 created 有效属性 active 1/0 扩展属性 extendattribute
		function search(){
			//默认的一个扩展条件,只能显示active!='0'的记录
			document.getElementById('ext').value="and active='1' and apptype='publicinfo'";
			form1.action="ApplistRightSvl";
			form1.submit();
		}
		</script>

	</head>
	<body style="font-size: 12px;margin: 22px">
		<div style="width: 100%;height: 100%">

			<form action="" method="post" name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="ext" value="" />

				<table width="90%" border="0">
					<tr>
						<td nowrap>
							&nbsp;&nbsp;名称
						</td>
						<td width='100'>
							<input type="text" name="naturalname" value="${naturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							标题
						</td>
						<td width='100'>
							<input type="text" name="name" value="${name}"
								class="textinput_td">
						</td>
						<td>
							<input type="button" name="searchbtn" value="查 询"
								onclick="search();" class="butt">
						</td>
					</tr>
				</table>

				<br>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td class="td_titt_bg" width="70" nowrap>
							创建日期
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							标题
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							内容
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
						<tr>
							<td nowrap>
								${list.created}
							</td>
							<td nowrap>
								${list.name}
							</td>
							<td nowrap>
								<font color='${list.extendattribute}'>${list.description}</font>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<script type="text/javascript">
					var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
					pginfo.write();
				</script>
			</form>
		</div>

	</body>
</html>
