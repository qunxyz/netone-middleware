<%@ page contentType="text/html; charset=GBK"%>

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
		<title>数据列表</title>
		<LINK REL=Stylesheet TYPE="text/css"
		HREF="<%=basePath%>/include/css/oe.css">
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
		<script type="text/javascript" src="<%=path%>/turnpageori/buss.js"></script>
	</head>
	<body>

		<form action="<%=basePath%>/turnpageori/list" name="form1" method="post">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<table align="center" style="width: 100%;" border="1" cellpadding="2">
				<tr align="center" bgcolor='#cccccc'>
					<td class="tdheadline" width='50'>
						选择
					</td>
					<td class="tdheadline">
						STUID
					</td>
					<td class="tdheadline">
						STUNAME
					</td>
					<td class="tdheadline">
						DESCRIPTION
					</td>

				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="left">
						<td>
							<input type="checkbox" name="chkid" value="${getCol.stuid}"
								size="10">
						</td>
						<td height="21">
							<a href="<%=basePath%>/turnpageori/update?stuid=${getCol.stuid}" target='_blank'>${getCol.stuid}</a>
						</td>
						<td height="21">
							${getCol.stuname}
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
			<table>
				<tr>
					<td>
						STUNAME:
						<input type='text' name='stuname' value='${stuname}'>
						<input type="button" value="查询" onclick="search()"  />
					</td>
					<td>
						<input type="button" value="创建" onclick="create('<%=basePath%>turnpageori/create')" />
						<input type="button" value="修改" onclick="update('<%=basePath%>turnpageori/update')" />
						<input type="button" value="删除" onclick="dele('<%=basePath%>turnpageori/delete')" />
					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
