<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="frames/css/css.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		function turnit(ss){
			if (document.getElementById(ss).style.display=="none"){
				document.getElementById(ss).style.display="";
			} else{
				document.getElementById(ss).style.display="none"; 
			}
		}
		function link(values){
			parent.document.frames["proletright"].location="frames.do?task="+values ;
		}
	</script>
	<body style="margin: 22px">
		<form name="form1" action="" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<c:forEach items="${childrenlist}" var="getCol">
					<tr>
						<td onmouseup="turnit('${getCol.naturalname}')"
							style="CURSOR: hand" bgcolor="#d0cfb9" height=25>&nbsp;
							<IMG  src="image/allen004.gif" border=0>
							<strong>${getCol.name}</strong>
						</td>
					</tr>
					<tr>
						<td id=${getCol.naturalname } style="DISPLAY: ">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="1">
								<c:forEach items="${map}" var="map">
									<c:if test="${map.key == getCol.naturalname}">
										<c:forEach items="${map.value}" var="list">
											<tr>
												<td background="image/allen005.gif">
													&nbsp;&nbsp;&nbsp;
													<a href=javascript:link("${list.naturalname}")>${list.name}</a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
</html>
