<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>装载活动 example</title>
	</head>
	<body>
	<h3>装载活动</h3>
		<b>标签名称</b><br>
			<c:out value="<wf:loadWorklist/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			workcode:活动节点实例ID,数据类型String<br>
			outVar:返回结果,数据类型TWfWorklist<br>
		<b>测试例子</b><br>
		<br>
		<hr>
		<wf:loadWorklist workcode="402882e61dbe28c1011dbedf20940085"
			outVar="worklist"></wf:loadWorklist>
		<table width="753" border="1">
			<tr>
				<td width="130">
					流程实例ID
				</td>
				<td width="138">
					工作项名称
				</td>
				<td width="171">
					工作项编码
				</td>
				<td width="158">
					参与者
				</td>
				<td width="122">
					类型
				</td>
			</tr>
			<tr>
				<td>
					${worklist.runtimeid}
				</td>
				<td>
					${worklist.nameExt}
				</td>
				<td>
					${worklist.workcode}
				</td>
				<td>
					${worklist.participant}
				</td>
				<td>
					${worklist.types}
				</td>
			</tr>
		</table>
	</body>
</html>