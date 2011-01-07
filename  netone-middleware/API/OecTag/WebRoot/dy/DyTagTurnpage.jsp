<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
	</head>
	<body>
	<body>
	<h3>为动态表单数据进行分页显示</h3>
		需要配合page.js使用，另外在页面上引用分页处理的javascript<br>
		<hr>n
		<form action="" name="form1" method="post">
			<dy:dydata dataname="mydata" fatherlsh="1"
				formcode="731d715eb77c11ddade82b3142add25d_" prepage="3" />
			<table border='1'>
				<c:forEach items="${mydata}" var="data">
					<tr>
						<td>
							${data.belongx}
						</td>
						<td>
							${data.timex}
						</td>
						<td>
							${data.column3}
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
	</body>
</html>