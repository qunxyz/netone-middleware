<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
	</head>
	<body>
		<h3>
			下载列表
		</h3>
		URL参数  pagenum
		<br>

		<br>
<c:if test="${param.pagenum==''}">6</c:if>
<c:if test="${param.pagenum!=''}">${param.pagenum}</c:if>
		<hr>
		<dy:dydata dataname="mydata" fatherlsh="1"
			formcode="875bb303bdca11dd9c573f3e864d2bf2_"
			prepage="${param.pagenum}"  />
		<table border='0' width='800' height='220'>
			<tr>
				<td>
					<table width='250'>
						<c:forEach items="${mydata}" var="data">
							<tr>
								<td>
									<strong><font size='2'>${data.belongx}</font> </strong>
									[资源数:${data.column3}] &nbsp;
									<strong> <a href='note4.jsp?fatherlsh=${data.lsh}'
										target='_blank'><font color='#0066CC' size='2'> 进入
										</font> </a> </strong>
								</td>
							</tr>

						</c:forEach>

					</table>
				</td>
			</tr>
		</table>

	</body>
</html>