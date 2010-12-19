<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
    request.setAttribute("path", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
	</head>
	<body>
		<form action="" name="form1" method="post">
			<dy:dydata dataname="mydata" fatherlsh="1"
				formcode="74850ecaba0611dda932b9bba8510e29_" prepage="10" condition="belongx like '%ddddd%'"/>
           
			<table width="774" border="1">
				<tr style="text-align: center">
					<td width="201">
						巡检方案实例名称
					</td>
					<td width="194">
						巡检任务名称
					</td>
					<td width="122">
						开始时间
					</td>
					<td width="115">
						结束时间
					</td>
					<td width="108">
						执行状态
					</td>
				</tr>
				<c:forEach items="${mydata}" var="data">
					<tr>
						<td>
							${data.belongx}
						</td>
						<td>
							${data.column3}
						</td>
						<td>
							${data.column4}
						</td>
						<td>
							${data.column5}
						<td>
							${data.column6}
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- 分页处理 -->
			<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
			</script>
		</form>
		<br>
		<br>
	</body>
</html>