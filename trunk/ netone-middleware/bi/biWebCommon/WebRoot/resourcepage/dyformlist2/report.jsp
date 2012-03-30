<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>


		<title>业务报表</title>
	</head>
	<body>

		<dy:dydata dataname="mydata" fatherlsh="1"
			formcode="${param.formcode}" />
		<dy:dydesign dataname="mydatads" formcode="${param.formcode}" />



		<table border='1'>
			<tr bgcolor='#99999'>
				<c:forEach items="${mydatads}" var="columnx">
					<c:if test='${fn:contains(columnx.columnid,"column")}'>
						<td>
							${columnx.columname}
						</td>
					</c:if>
				</c:forEach>
			</tr>

			<c:forEach items="${mydata}" var="data">

				<tr>
					<td>
						${data.column3}
					</td>
					<td>
						${data.column4}
					</td>
					<td>
						${data.column5}
					</td>
					<td>
						${data.column6}
					</td>
					<td>
						${data.column7}
					</td>
					<td>
						${data.column8}
					</td>
					<td>
						${data.column9}
					</td>
					<td>
						${data.column10}
					</td>
					<td>
						${data.column11}
					</td>
				</tr>

			</c:forEach>
		</table>
	</body>
</html>