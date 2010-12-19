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
	sqlCount += "  from `inis_report` t1 , `inis_solutioninstance` t2,`inis_task` t3,";
	sqlCount += "  `inis_metatask` t4, `inis_runner` t5,`inis_funcifc` t6,`inis_sol_instance_report` t7";
	sqlCount += "where t1.`solutioninstanceid` = t7.`solInstanceGuid` and t1.`taskid`= t3.`guid` and t1.`solInstanceRepGuid` = t7.`guid` ";
	sqlCount += " and t3.`metaTaskGuid`= t4.`guid` and t4.`runnerGuid` = t5.`guid`";
	sqlCount += " and t5.`funcIfcGuid` = t6.`guid`";
	sqlCount +=" and t6.`paramStr` like '%"+paramName+"%'";

	String sql = "select t7.`guid` ,t7.`solInstanceGuid` ,t2.`solutionName` ,t7.`invokeDate` as createDate , t7.`statusName`, t6.paramStr";
	sql += " from `inis_report` t1 , `inis_solutioninstance` t2,`inis_task` t3,";
	sql += " `inis_metatask` t4, `inis_runner` t5,`inis_funcifc` t6,`inis_sol_instance_report` t7";
	sql += " where t1.`solutioninstanceid` = t7.`solInstanceGuid` and t1.`taskid`= t3.`guid` and t1.`solInstanceRepGuid` = t7.`guid` ";
	sql += " and t3.`metaTaskGuid`= t4.`guid` and t4.`runnerGuid` = t5.`guid`";
	sql += " and t5.`funcIfcGuid` = t6.`guid`";
    sql += " and t6.`paramStr` like '%"+paramName+"%'  limit #startIndex, #pageSize";
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
		<h3>���ñ�ǩ��ҳ�����sql������ʹ������Դ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<rs:sql/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			ds������Դ����Netoneƽ̨�϶��������Դ��������д����Դ������<br>
			sqlcount:���ݼ�¼����������ʹ��sql��������ݼ�¼��������<br>
			sql:���β���ִ�е�sql���<br>
			prepage:ÿҳ��ʾ��������������Ĭ��Ϊ10��
			dataname:���ؽ��<br>
			ע�⣺������û��ʹ�÷�ҳ
		<b>��������</b><br>
		ʹ�õ�����Դ����ΪDATASOURCE.DATASOURCE.INIS
		<br>
		<hr>
		
			<!-- ������Դ�� SAMPLE -->
			<rs:sql ds="DATASOURCE.DATASOURCE.INIS " sqlcount="${sqlCount}"
				sql="${sql}" dataname="intanceSet" prepage="10"></rs:sql>

			<table width="774" border="1">
				<tr style="text-align: center">
				<td width="201">
						Ѳ�췽��ʵ����־ID
					</td><td width="201">
						Ѳ�췽��ʵ��ID
					</td>
					<td width="201">
						Ѳ�췽��ʵ������
					</td>
				
					<td width="250">
						Ѳ�췽������
					</td>
					<td width="250">
						����ʱ��
					</td>
					<td width="108">
						ִ�н��
					</td>
					<td width="108">
						����
					</td>
				</tr>
				<c:forEach items="${intanceSet}" var="instance">
					<tr>
						<td>
							${instance.guid}
						</td>
						<td>
							${instance.solInstanceGuid}
						</td>
						<td>
							${instance.name}
						</td>
						<td>
						    ${instance.solutionName}
						</td>
						<td>
							${instance.createDate}
						</td>
						<td>
							${instance.statusName}
						</td>
						<td>
							${instance.paramStr}
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- ��ҳ���� -->
			<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
	     </script>
		</form>
	</BODY>
</HTML>
