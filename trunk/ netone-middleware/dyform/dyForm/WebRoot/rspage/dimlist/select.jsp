<%@ page language="java"  pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Õ‚≤ø—°‘Ò111</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link rel="stylesheet" type="text/css" href="rsinclude/css/xtree2.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">

		<script type="text/javascript" src="rsinclude/xtree2.js"></script>
		<script type="text/javascript" src="rsinclude/xloadtree2.js"></script>
		<script type="text/javascript" src="rsinclude/select.js"></script>
	</head>

	<body style="margin: 0px">
		<form action="" method="post" name="form1">
			<table style="width:100%; height:100%" border="0">
				<tr>
					<td valign="top">
						<div>
							<select name="application" onchange="submitform();"
								class="table_select_page" style="display:none">
								<c:forEach items="${applist}" var="app">
									<option value="${app.id}"
										${app.naturalname==appnaturalname?"selected":"" }>
										${app.name }
									</option>
								</c:forEach>
							</select>
							<input type="hidden" name="pagename" value="${pagename}" />
						</div>
						<div style="width: 100%;height: 100%;overflow: auto;">
							<c:if test="${root != null}">
								<script type="text/javascript">
				  					var functree = new WebFXLoadTree("${root.name}","XMLFuncTreeSvl?parentid=${root.id}","javascript:nodeAction('${root.id}','${root.appid}','${root.ou}','${root.naturalname}','${root.parentdir}','${root.actionurl}')");
				  					functree.write();
				  					functree.expand();
				  					functree._setSelected(true);
		  						</script>
							</c:if>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
