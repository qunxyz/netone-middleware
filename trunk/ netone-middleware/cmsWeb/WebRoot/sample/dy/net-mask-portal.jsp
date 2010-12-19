<!-- sssss-->
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
	</head>
	<body>
		<dy:dydata dataname="mydata" fatherlsh="1"
			formcode="1bcfa746ba0111dda932b9bba8510e29_" />
		<table width='200'>
			<tr>
				<td>
					网元
				</td>
				<td>
					监测巡检任务
				</td>
				<td>
					监测巡检方案
				</td>
			</tr>
			<c:forEach items="${mydata}" var="data">
				<tr>
					<td>
						<c:out value="${data.url}" />
					</td>
					<td>
					  <a  href="http://www.163.com?act=<c:out value='${data.url}'/>">fasdfasd</a>
					</td>
					<td>

					</td>
				</tr>

			</c:forEach>
		</table>
	</body>
</html>