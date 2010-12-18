<%@ page language="java" pageEncoding="GBK"%>
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

		<title>部门选择</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link rel="stylesheet" type="text/css" href="css/xtree2.css">
		<link rel="stylesheet" type="text/css" href="css/css.css">

		<script type="text/javascript" src="include/js/xtree2.js"></script>
		<script type="text/javascript" src="include/js/xloadtree2.js"></script>
		<script type="text/javascript" src="include/js/rsinfo/dept/left.js"></script>
		<script type="text/javascript" src="<%=path %>/include/js/prototype.js"></script>
	</head>

	<body
		onload="nodeAction('${root.id}','${root.ou}','${root.actionurl}');">
		<form action="" method="post" name="form1">
			<table style="width: 100%; height: 100%" border="0">
				<tr>
					<td valign="top">
						<select name="application" onchange="submitform();">
							<c:forEach items="${applist}" var="app">
								<option value="${app.naturalname}" ${app.naturalname==appnaturalname?"selected":"" }>
									${app.name }
								</option>
							</c:forEach>
						</select>
						<input type="hidden" name="task" value="" />
						<!-- 
							<input type="button" value="新建" onclick="addapp();" class="butt">
							<input type="button" value="修改" onclick="modapp();" class="butt">
							<input type="button" value="删除" onclick="delapp();" class="butt">
							<div id="editdiv"
								style="display: none;margin-top: 5px ; margin-bottom: 5px">
								名称
								<input type="text" name="appnaturalname"
									value="${appnaturalname}" />
								中文
								<input type="text" name="appname" value="${appname}" />
								<input type="submit" value="确定" class="butt" />
								<input type="button" value="取消" class="butt" onclick="cancle();" />
							</div>
							-->
						<c:if test="${root != null}">
							<script type="text/javascript">
			  					var functree = new WebFXLoadTree("${root.name}","XMLFuncTreeSvl?parentid=${root.id}","javascript:nodeAction('${root.id}','${root.ou}','${root.naturalname}','${root.parentdir}','${root.actionurl}')");
			  					functree.write();
			  					functree.expand();
			  					functree._setSelected(true);
	  						</script>
						</c:if>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
