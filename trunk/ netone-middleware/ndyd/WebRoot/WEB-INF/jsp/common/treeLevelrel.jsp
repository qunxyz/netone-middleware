<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";   

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'treeLevelrel.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<form action="">
			һ������ʾ
			<input type='checkbox'>
			<input type='text' value=''>
			<input type='button' value='��ѯ' onClick=''>
			<table>
				<tr>
					<td>
						���
					</td>
					<td>
						һ����
					</td>
					<td>
						������
					</td>
					<td>
						������
					</td>
					<td>
						������
					</td>
					<td>
						������
					</td>
					<td>
						������
					</td>
					<td>
						��������
					</td>
					<td>
						��������
					</td>
					<td>
						�����̳�������
					</td>
					<td>
						һ����״̬
					</td>
				</tr>
			</table>
			<c:forEach items="${value2}" var="each" varStatus="cur">
				<tr>
				<td>${each[cur.index]}</td>
				</tr>
			</c:forEach>

		</form>
	</body>
</html>
