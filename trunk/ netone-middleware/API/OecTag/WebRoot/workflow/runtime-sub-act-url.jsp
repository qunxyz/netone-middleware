<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>返回指定流程的子流程列表页面地址 example</title>
	</head>
	<body>
		<h3>
			返回指定流程的子流程列表页面地址
		</h3>
		<b>标签名称</b>
		<br>
		<c:out value="<wf:runtimeSubActUrlTag/>" escapeXml="true"></c:out>
		<br>
		<b>标签参数</b>
		<br>
		runtimeId:流程实例ID,数据类型String
		<br>
		<b>测试例子</b>
		<br>
		<br>
		<hr>

		<a
			href="<wf:runtimeSubActUrlTag runtimeid="402882e61db25d96011db9ced7e30a93"/>"
			target="_blank">查看流程</a>

	</body>
</html>