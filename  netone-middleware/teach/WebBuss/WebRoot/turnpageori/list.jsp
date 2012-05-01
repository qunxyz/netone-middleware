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
		<script type="text/javascript" src="<%=path%>/include/js/page2.js"></script>
		<script type="text/javascript" src="<%=path%>/turnpageori/buss.js"></script>
	</head>
	<body>

		<form action="<%=basePath%>/turnpageori/list" name="form1"
			method="post">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<table align="center" style="width: 100%;" border="1" cellpadding="2">
				<tr align="center" bgcolor='#cccccc'>
					<td class="tdheadline" width='50'>
						选择
					</td>

					<td class="tdheadline">
						资产名 
					</td>
					<td class="tdheadline">
						资产类型
					</td>
					<td class="tdheadline">
						原始价格 
					</td>
					<td class="tdheadline">
						当前价格 
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="left">
						<td>
							<input type="checkbox" name="chkid" value="${getCol.lsh}"
								size="10">
						</td>
						<td height="21">
						<a href="<%=basePath%>/turnpageori/update?lsh=${getCol.lsh}"
								target='_blank'>${getCol.name}&nbsp;</a>
						
							
						</td>
						<td height="21">
							${getCol.types} &nbsp;
						</td>
						<td height="21">
							${getCol.oriprice}&nbsp;
						</td>
						<td height='21'>
							${getCol.curprice}&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
			<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
				document.getElementById("page_selpagex").value='${param.page_selpagex}';
			</script>
			<table>
				<tr>
					<td>
						资产名:
						<input type='text' name='name' value='${name}'>
						<input type="button" value="查询" onclick="search()" />
					</td>
					<td>
						<input type="button" value="创建"
							onclick="create('<%=basePath%>turnpageori/create')" />
						<input type="button" value="修改"
							onclick="update('<%=basePath%>turnpageori/update')" />
						<input type="button" value="删除"
							onclick="dele('<%=basePath%>turnpageori/delete')" />
					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
