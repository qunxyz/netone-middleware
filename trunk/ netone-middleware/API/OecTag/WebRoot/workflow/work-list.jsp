<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>显示所有的活动节点 example</title>
	</head>
	<body>
	<h3>显示所有的活动节点</h3>
		<b>标签名称</b><br>
			<c:out value="<wf:listWorklist/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			runtimeId:流程实例ID,数据类型String<br>
			outVar:返回结果,数据类型TWfWorklist<br>
		<b>测试例子</b><br>
		<br>
		<hr>
		<wf:listWorklist runtimeId="402882e61dbe28c1011dbedb44aa0076"
			outVar="workListSet"></wf:listWorklist>
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
			<c:forEach items="${workListSet}" var="workList">
				<tr>
					<td>
						${workList.runtimeid}
					</td>
					<td>
						${workList.nameExt}
					</td>
					<td>
						${workList.workcode}
					</td>
					<td>
						${workList.participant}
					</td>
					<td>
						${workList.types}
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>