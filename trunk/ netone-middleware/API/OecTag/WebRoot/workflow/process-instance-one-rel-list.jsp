<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>获得一个流程运行实例的相关数据对象 example</title>

	</head>
	<body>
	<h3>获得一个流程运行实例的相关数据对象</h3>
		<b>标签名称</b><br>
			<c:out value="<wf:listRelevartvarInstance/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			runtimeId:流程实例ID,数据类型String<br>
			dataid:数据对象ID,数据类型String<br>
			outVar:返回结果,数据类型TWfRelevantvar<br>
		<b>测试例子</b><br>
		<br>
		<hr>
		<wf:listRelevartvarInstance
			runtimeId="402882e61dbe28c1011dbedb44aa0076" dataid="rev1" outVar="relVar"></wf:listRelevartvarInstance>


		<table width="753" border="1">
			<tr>
				<td width="130">
					流程实例ID
				</td>
				<td width="138">
					相关数据编码
				</td>
				<td width="158">
					相关数据值
				</td>
				<td width="122">
					相关数据名称
				</td>
			</tr>
			
				<tr>
					<td>
						${relVar.runtimeid}
					</td>
					<td>
						${relVar.varcode}
					</td>
					<td>
						${relVar.valuenow}
					</td>
					<td>
						${relVar.datafieldid}
					</td>
				</tr>
			
		</table>
	</body>
</html>