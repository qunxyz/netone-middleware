<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>下载列表</title>
	</head>

	<body>
		<table>
			<tr>
				<td width="250px">
					文件名:
				</td>
				<td width="250px">
					创建时间:
				</td>
				<td width="250px">
					操作:
				</td>
			</tr>
			<c:forEach items="${list}" var="list">
				<tr>
					<td>
						${list.name}
					</td>
					<td>
						${list.time}
					</td>
					<td>
						<a href="javaScript:void(0)" onclick="downLaod('${list.name}')">下载</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
	<script type="text/javascript">
	  function downLaod(path){
       openWinCenter('下载','<c:url value="/client/importFile.do?method=onDowanLoadFile" />'+"&fileName="+path, 500,250,true);
	}
	</script>
</html>
