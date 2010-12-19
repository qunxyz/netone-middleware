<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>根据相关数据的ID获得相关数据对象实例 example</title>
	</head>
	<body>
	<h3>根据相关数据的ID获得相关数据对象实例</h3>
		<b>标签名称</b><br>
			<c:out value="<wf:loadRelevantvar/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			varcode：相关数据的ID,数据类型String<br>
			outVar:返回结果,数据类型TWfRelevantvar<br>
		<b>测试例子</b><br>
		<br>
		<hr>
		
		<wf:loadRelevantvar varcode="402882e61dbe28c1011dbedf16340082"
			outVar="relevantvar"></wf:loadRelevantvar>
		<table width="753" border="1">
			<tr>
				<td width="130">
					相关数据ID
				</td>
				<td width="150">
					相关数据名称
				</td>
				<td width="150">
					工作项名称
				</td>
				<td width="158">
					参与者
				</td>
				<td width="180">
					相关数据值
				</td>
				<td width="180">
					流程ID
				</td>
				<td width="130">
					流程实例ID
				</td>

			</tr>

			<tr>
				<td>
					${relevantvar.varcode}
				</td>
				<td>
					${relevantvar.datafieldid}
				</td>
				<td>
					${relevantvar.nameExt}
				</td>
				<td>
					${relevantvar.participant}
				</td>
				<td>
					${relevantvar.valuenow}
				</td>
				<td>
					${relevantvar.processid}
				</td>
				<td>
					${relevantvar.runtimeid}
				</td>
			</tr>
		</table>
	</body>
</html>
