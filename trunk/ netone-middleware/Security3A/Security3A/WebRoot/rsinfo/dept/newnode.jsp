<%@ page contentType="text/html; charset=GBK"%>
<jsp:directive.page import="oe.frame.web.form.RequestUtil" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../human/BaseRoot.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>新建子节点</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/rsinfo/dept/right.js"></script>

		<script type="text/javascript" src="include/js/prototype.js"></script>
		<script type="text/javascript">
		    var checktypex='qy';
			var rootpath='${rootpath}';
		</script>
		<script type="text/javascript"
			src="include/js/rsinfo/dept/humanindex.js"></script>
	</head>
	<body style="font-size: 12px; margin: 22px" >
		${paramMap.alertMsg}
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				alert("新建成功！")
				opener.afterCreate();
			</script>
		</c:if>
		<c:if test="${CreateSuccess == 'n'}">
			<script type="text/javascript">
				alert("新建失败！")
			</script>
		</c:if>
		<div style="width: 100%; height: 100%">
			<form action="rsinfo/dept/DepartmentAdd.do?task=addsave"
				method="post">
				<input type="hidden" name="id" value="${paramMap.id}" />
				<input type="hidden" name="appid" value="${paramMap.appid}" />
				<input type="hidden" name="ou" value="${paramMap.ou}" />
				<input type="hidden" name="extendattribute" value="" />
				<div align="left">
					<input type="button" name="btnnew" value="新 建"
						onclick="newhumanx();" class="butt">

				</div>
				<br>
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr id='pingying'>
						<td width="15%">
							拼音
						</td>
						<td>
							<input type="text" name="naturalname" value="" 
								class="textinput_td"/>
						</td>
					</tr>
					<tr>
						<td width="15%">
							中文名称
						</td>
						<td>
							<input type="text" name="name" value="" class="textinput_td" />
						</td>
					</tr>
					<tr id='codeline'>
						<td width="15%">
							部门编码
						</td>
						<td>
							<input type="text" name="actionurl" id='actionurl' value="" class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							部门类型
						</td>
						<td>
						
							<input type="text" name='objecttype' value='' class="textinput_td">
						</td>
						</tr>
 					 <!-- 集成外部系统属性 -->
				</table>

			</form>
		</div>
	</body>
</html>
