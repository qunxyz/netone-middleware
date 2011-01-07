<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>装载流程 example</title>
	</head>
	<body>
	<h3>装载流程</h3>
		<b>标签名称</b><br>
			<c:out value="<wf:loadProcess/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			processid：流程静态ID,数据类型String<br>
			outVar:返回结果,数据类型WorkflowProcess<br>
		<b>测试例子</b><br>
		<br>
		<hr>
		<wf:loadProcess processid="BUSSWF.BUSSWF.MYFLOW" outVar="process"></wf:loadProcess>
		<table width="753" border="1">
			<tr>
				<td width="130">
					ID
				</td>
				<td width="138">
					名称
				</td>
				<td width="171">
					描述
				</td>
			</tr>

			<tr>
				<td>
					${process.id}
				</td>
				<td>
					${process.name}
				</td>
				<td>
					${process.description}
				</td>
			</tr>

		</table>
	</body>
</html>