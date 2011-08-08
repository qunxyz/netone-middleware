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
			
		<style type="text/css">
			#queryprocess{
				border-bottom-color: #cccccc;
				border-bottom-width: 2px;
				border-top-color: #cccccc;
				border-top-width: 2px;
				height: 50px;
				width: 100%;
			}
		</style>
		
		<script type="text/javascript">
		
			function onready(){

			}
		
			function queryProcess(){

				var pname = document.getElementById('pname').value;
				var url = "";

				if(pname != "" && pname != null){
					url = "/wfweb/listUseprocess.do?processid="+pname+"&operate=t";
					document.forms[0].action = "/wfweb/listUseprocess.do?processid="+pname+"&operate=t";
				}else{
					url = "/wfweb/listUseprocess.do?processid="+'param.processid';
					document.forms[0].action = "/wfweb/listUseprocess.do?processid="+'${param.processid}'+pname+"&operate=t";
				}
				document.forms[0].submit();
			}
		</script>
	</head>
	<body onload="onready()">
		<form method="post" action="/wfweb/listUseprocess.do">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<br>
			<font class='Text1'>当前流程:${processname}</font>&nbsp;
			<input type="button" value="创建流程"
				onclick="useprocess('${param.processid}','new','');" class="butt" />
			<div id="queryprocess"></br>
				<div>查询</div>
				<label for="pname">流程名称：</label><input id="pname" name="pname" type="text"/>
				&nbsp;&nbsp;
					<label for="pstate">状态：</label>
					<select name="pstate">
						<option selected="selected" value="10">请选择</option>
						<option value="00">准备中</option>
						<option value="01">执行中</option>
						<option value="02">完毕</option>
						<option value="03">异常</option>
						<option value="04">撤销</option>
					</select>&nbsp;&nbsp;
				<label for="starttime">起始时间：</label><input type="text" name="starttime" />&nbsp;&nbsp;
				<label for="endtime">结束时间：</label><input type="text" name="endtime" />&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;
			<input type="button" value="查询" onclick="queryProcess()"/>

			</div>
			<table width='100%' cellspacing="0" border="1" cellpadding="2"
				bordercolordark="#999999" bordercolorlight="#FFFFFF">
				<tr class='tdheadline'>

					<td nowrap>
						流程名
					</td>
					<td nowrap>
						显示名
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
							${getCol.PROCESSID}
						</td>
						
						<td height="21">
							${pname }
						</td>

						<td height="21" bgcolor="#FFFFFF">
							<c:if test="${getCol.STATUSNOW=='00'}">
								<font color='blue'>准备中
							</c:if>
							<c:if test="${getCol.STATUSNOW=='01'}">
								<font color='green'>执行中
							</c:if>
							<c:if test="${getCol.STATUSNOW=='04'}">
								<font color='black'>撤销
							</c:if>
							<c:if test="${getCol.STATUSNOW=='03'}">
								<font color='yellow'>异常
							</c:if>
							<c:if test="${getCol.STATUSNOW=='02'}">
								<font color='red'>完毕</c:if>
							
						</td>
						<td height="21">
							<c:if test="${getCol.KIND=='01'}">
								主流程
							</c:if>
							<c:if test="${getCol.KIND=='02'}">
								分支流程
							</c:if>
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.STARTTIME}
						</td>
						<td height="21">
							${getCol.ENDTIME}-
						</td>
						<td height="21">

							<a href="javascript:useprocess('','run','${getCol.RUNTIMEID}')">[启动]</a>
							<a href="javascript:useprocess('','init','${getCol.RUNTIMEID}')">[初始]</a>
							<a href="javascript:useprocessview('','${getCol.RUNTIMEID}')">[监控]</a>
							<a
								href="javascript:useprocessview('subflow','${getCol.RUNTIMEID}')">[子流程]</a>
							<a href="javascript:checkthis('${getCol.RUNTIMEID}')">[选择]</a>
							<a href="javascript:worklistinfo('${getCol.RUNTIMEID}')">[活动流程实例列表]</a>
							<a href="javascript:Relevantvarinfo('${getCol.RUNTIMEID}')">[相关数据列表]</a>
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
