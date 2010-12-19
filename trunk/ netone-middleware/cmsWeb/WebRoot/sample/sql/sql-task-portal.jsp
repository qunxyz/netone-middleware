<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String paramName = null;
    paramName = request.getParameter("paramName").toString();
	String sqlCount = "select count(*) count";
	sqlCount += " from `inis_report` t1 , `inis_solutioninstance` t2, ";
	sqlCount += " `inis_task` t3, `inis_metatask` t4, `inis_runner` t5,`inis_funcifc` t6";
	sqlCount += " where t1.`solutioninstanceid` = t2.`guid` and t1.`taskid`= t3.`guid` ";
	sqlCount += " and t3.`metaTaskGuid`= t4.`guid` and t4.`runnerGuid` = t5.`guid`";
	sqlCount += " and t5.`funcIfcGuid` = t6.`guid`";
	sqlCount += " and t6.`paramStr` like '%"+paramName+"%'";

	String sql = "select t2.`solutionName` , t3.`name` ,t1.`created` as createDate ,t1.`endDate` as a , t1.`status`, t6.paramStr";
	sql += " from `inis_report` t1 , `inis_solutioninstance` t2, ";
	sql += " `inis_task` t3, `inis_metatask` t4, `inis_runner` t5,`inis_funcifc` t6";
	sql += " where t1.`solutioninstanceid` = t2.`guid` and t1.`taskid`= t3.`guid` ";
	sql += " and t3.`metaTaskGuid`= t4.`guid` and t4.`runnerGuid` = t5.`guid`";
	sql += " and t5.`funcIfcGuid` = t6.`guid` ";
    sql += " and t6.`paramStr` like '%"+paramName+"%' limit #startIndex, #pageSize";

    System.out.print(sql);
	request.setAttribute("sqlCount", sqlCount);
	request.setAttribute("sql", sql);
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
	
		<form action="" name="form1" method="post">
		<h3>利用标签在页面进行sql操作，使用数据源</h3>
		<b>标签名称</b><br>
			<c:out value="<rs:sql/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			ds：数据源，在Netone平台上定义的数据源，这里填写数据源的名称<br>
			sqlcount:数据记录的总条数，使用sql语句获得数据记录的总条数<br>
			sql:本次操作执行的sql语句<br>
			prepage:每页显示的条数，不设置默认为10条
			dataname:返回结果<br>
		<b>测试例子</b><br>
		使用的数据源名称为DATASOURCE.DATASOURCE.INIS
		<br>
		<hr>
		
			<!-- 带数据源的 SAMPLE -->
			<rs:sql ds="DATASOURCE.DATASOURCE.INIS " sqlcount="${sqlCount}"
				sql="${sql}" dataname="taskSet" prepage="2"></rs:sql>

			<table width="774" border="1">
				<tr style="text-align: center">
					<td width="201">
						巡检方案实例名称
					</td>
					<td width="194">
						巡检任务名称
					</td>
					<td width="250">
						开始时间
					</td>
					<td width="250">
						结束时间
					</td>
					<td width="108">
						执行状态
					</td>
				</tr>
				<c:forEach items="${taskSet}" var="task">
					<tr>
						<td>
							${task.solutionName}
						</td>
						<td>
							${task.name}
						</td>
						<td>
							${task.createDate}
						</td>
						<td>
						    ${task.a}
						</td>
						<td>
							${task.status}
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- 分页处理 -->
			<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
	     </script>
		</form>
	</BODY>
</HTML>
