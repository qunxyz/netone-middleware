<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://www.oesee.com/netone/wf" prefix="wf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>获得指定流程的所有的相关数据对象</title>
	</head>
	<body>
	<h3>获得指定流程的所有的相关数据对象</h3>
		<b>标签名称</b><br>
			<c:out value="<wf:listRelevantvar/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			processid:流程ID,数据类型String<br>
			outVar:返回结果,数据类型List<br>
		<b>测试例子</b><br>
		<br>
		<hr>
		<wf:listRelevantvar processid="BUSSWF.BUSSWF.ak"
			outVar="dataVarSet"></wf:listRelevantvar>
		<table width="753" border="1">
			<tr>
				<td width="130">
					相关数据名称
				</td>
				<td width="138">
					相关数据类型
				</td>
				<td width="158">
					相关数据初始值
				</td>
				<td width="122">
					相关数据长度
				</td>
			</tr>
			<c:forEach items="${dataVarSet}" var="dataVar">
				<tr>
					<td>
						${dataVar.name}
					</td>
					<td>
						${dataVar.dataType.type}
					</td>
					<td>
						${dataVar.initialValue}
					</td>
					<td>
						${dataVar.length}
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>