<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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
		<title>Ö°ÎñÑ¡Ôñ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/xtree2.css">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/xtree2.js"></script>
		<script type="text/javascript" src="include/js/xloadtree2.js"></script>
		<script type="text/javascript" src="include/js/util.js"></script>
		<script type="text/javascript" src="include/js/human/humanadd.js"></script>
		<script type="text/javascript">
			function nodeAction(id,ou){
				var name = functree.getSelected().getText();
				self.parent.opener.addSelectedDuty(name,id);
				self.parent.close();
			}
		</script>
	</head>

	<body>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="6" id="table" style="table-layout: fixed">
			<tr>
				<td align="center" valign="top" bgcolor="#fafdff">
					<br>
					<table border="0" width="90%" height="80%"
						style="table-layout: fixed">
						<tr>
							<td width="30%" valign="top" align="left">
								<c:if test="${root != null}">
									<div style="width:98%;height:98%;overflow: auto">
										<script type="text/javascript">
					  					var functree = new WebFXLoadTree("${root.name}","servlet/XMLFuncTreeSvl?parentid=${root.id}&source=${paramMap.source}","javascript:nodeAction('${root.id}','${root.ou}')");
					  					functree.write();
					  					functree.expand();
					  					functree._setSelected(true);
				  					</script>
									</div>
								</c:if>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
