<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
		<script type="text/javascript">
		function todo(){
			var modelid=document.getElementById('modeltemplate').value;
			document.getElementById('dispinfo').innerHTML=document.getElementById(modelid).innerHTML;
		}
		</script>
	</head>
	<body>
		<form action="<%=path%>/templateModelDone.do">
			<table width='100%'>
				<tr>
					<td>
						请选择您的网站模板:
						<select name="modeltemplate" onChange='todo()'>
							<c:forEach items="${templatelist}" var="type">
								<option value="${type[0]}">
									${type[1]}
								</option>
							</c:forEach>
						</select>
						<c:if test="${'hide'!=butview}">
							<input type='submit' value='确定' />
						</c:if>

						<br>
						<hr>
						<c:forEach items="${templatelist}" var="type">
							<div id="${type[0]}" style='display:none'>
								${type[2]}
							</div>
						</c:forEach>
						<div id='dispinfo'>
							<c:out value="${defaultvalue}" default="###" />
						</div>
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
