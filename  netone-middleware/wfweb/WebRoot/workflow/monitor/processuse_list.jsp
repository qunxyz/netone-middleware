<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%=request.getAttribute("subflow") == null ? ""
					: request.getAttribute("subflow")%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>运行流程实例选择</title>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/workflow/design.js"></SCRIPT>
	</head>
	<body>
		<form method="post" action="">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<br>
			<font class='Text1'>当前流程:${processname}</font>&nbsp;
			<input type="button" value="创建流程"
				onclick="useprocess('${param.processid}','new','');" class="butt" />

			<hr>
			<table width='100%' cellspacing="0" border="1" cellpadding="2"
				bordercolordark="#999999" bordercolorlight="#FFFFFF">
				<tr class='tdheadline'>

					<td nowrap>
						流程ID
					</td>

					<td nowrap>
						状态
					</td>
					<td nowrap>
						类型
					</td>
					<td nowrap>
						启动时间
					</td>
					<td nowrap>
						结束时间
					</td>
					<td nowrap>
						控制
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="left">

						<td height="21">
							${getCol.runtimeid}
						</td>

						<td height="21" bgcolor="#FFFFFF">
							<c:if test="${getCol.statusnow=='00'}">
								<font color='blue'>
							</c:if>
							<c:if test="${getCol.statusnow=='01'}">
								<font color='green'>
							</c:if>
							<c:if test="${getCol.statusnow=='04'}">
								<font color='yellow'>
							</c:if>
							<c:if test="${getCol.statusnow=='03'}">
								<font color='red'></c:if>
							${getCol.statusExt}
						</td>
						<td height="21">
							${getCol.typeExt}
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.starttime}
						</td>
						<td height="21">
							${getCol.endtime}-
						</td>
						<td height="21">

							<a href="javascript:useprocess('','run','${getCol.runtimeid}')">[启动]</a>
							<a href="javascript:useprocess('','init','${getCol.runtimeid}')">[初始]</a>
							<a href="javascript:useprocessview('','${getCol.runtimeid}')">[监控]</a>
							<a
								href="javascript:useprocessview('subflow','${getCol.runtimeid}')">[子流程]</a>
							<a href="javascript:checkthis('${getCol.runtimeid}')">[选择]</a>
							<a href="javascript:worklistinfo('${getCol.runtimeid}')">[活动流程实例列表]</a>
							<a href="javascript:Relevantvarinfo('${getCol.runtimeid}')">[相关数据列表]</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<script type="text/javascript">
					//单选择 需要根据应用的需要来订制
				function checkthis(id) {
				if(opener.opener.selecthis==undefined){
					alert('非选择状态');
					return;
				}
					opener.opener.selecthis(id);
					window.close();
					opener.close();
				}
			</script>

		</form>
	</body>
</html>
