<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>装载流程实例 example</title>
	</head>
	<body>
	<h3>装载流程实例</h3>
		<b>标签名称</b><br>
			<c:out value="<wf:loadRuntime/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			runtimeid:流程运行实例ID,数据类型String<br>
			outVar:返回结果,数据类型TWfRuntime<br>
		<b>测试例子</b><br>
		<br>
		<hr>
		<wf:loadRuntime runtimeid="402882e61db25d96011db9ced7e30a93"
			outVar="runtime"></wf:loadRuntime>
		<table width="753" border="1">
			<tr>
				<td width="130">
					流程实例ID
				</td>
				<td width="138">
					流程类型
				</td>
				<td width="171">
					启动时间
				</td>
				<td width="158">
					结束时间
				</td>
				<td width="122">
					状态
				</td>
			</tr>
			<tr>
				<td>
					${runtime.runtimeid}
				</td>
				<td>
					${runtime.kind}
				</td>
				<td>
					${runtime.starttime}
				</td>
				<td>
					${runtime.endtime}
				</td>
				<td>
					${runtime.statusnow}
				</td>
			</tr>
		</table>
	</body>
</html>