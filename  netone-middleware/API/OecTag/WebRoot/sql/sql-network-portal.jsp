<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@ taglib uri="http://www.oesee.com/netone/resource" prefix="resource"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	//String sqlCount = "select count(*) count from `inis_paraminfo` t";

	//String sql = "select t.`guid`, t.`name`, t.`naturalname` ,t.`url` from `inis_paraminfo` t";
	//sql += " limit #startIndex, #pageSize";

	//request.setAttribute("sqlCount", sqlCount);
	//request.setAttribute("sql", sql);

	//request.setAttribute("sqlCount", sqlCount);
	//request.setAttribute("sql", sql);
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
		<script type="text/javascript">
		   var winId ;
           function openWinCenter(name,url,winWidth,winHeight,allowScroll) {
			   var  left = (screen.width-winWidth)/2;
			   var  top = (screen.height-winHeight)/2-10;
			    if(left<0){
			        left = 5;
			    }
			    if(top<0){
			         top = 5;
			    }
			
				var par = ' toolbar=no,location=no,status=no,menubar=no,resizable=yes,';
				par += ' width=' + winWidth + ', height=' + winHeight + ', left=' + left + ', top='+ top + ',';
				par += ' scrollbars= ' + allowScroll ;
			
				if(winId) {
			    	if(!winId.closed){
				  		winId.close();
			     	}
			     	winId = window.open(url,name,par);	
			  	} else {	
			    	winId = window.open(url,name,par);
			  	}
           }
           
           function onTaskView(network){//查看网元对应的监测任务
              var url = 'sql-task-portal.jsp?paramName='+network;
              openWinCenter('taskWin', url, 700, 400, true);
           }
		</script>
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
			注意：需要使用page.js，并在页面中引用该js<br>
		<b>测试例子</b><br>
		使用的数据源名称为DATASOURCE.DATASOURCE.INIS,
		
		<p>说明： 在这个例子，我们用到资源管理中的sql配置语句</p>
		<br>
		<hr>
			<!-- 带数据源的 SAMPLE -->
			<resource:loadResourceByNatural naturalname="SQL.SQL.INIS.NETMASK.SQLCOUNT" dataname="sqlCount"/>
			<resource:loadResourceByNatural naturalname="SQL.SQL.INIS.NETMASK.SQL" dataname="sql"/>
			         
		
			<rs:sql ds="DATASOURCE.DATASOURCE.INIS " sqlcount="${sqlCount.extendattribute}"
				sql="${sql.extendattribute}" dataname="netWorkSet" prepage="10"></rs:sql>

			<table width="774" border="1">
				<tr style="text-align: center">
					<td width="201">
						网元编码
					</td>
					<td width="194">
						网元名称
					</td>
					<td width="250">
						监测巡检任务
					</td>
					<td width="250">
						检测巡检方案
					</td>
				</tr>
				<c:forEach items="${netWorkSet}" var="netWork">
					<tr>
						<td>
							${netWork.name}
						</td>
						<td>
							${netWork.naturalname}
						</td>
						<td>
							<a href="sql-task-portal.jsp?paramName=${netWork.name}">查看</a>
							<!-- <label onClick="javascript:onTaskView('${netWork.name}');" style="cursor:hand;color:#0066FF">查看</label>-->
						</td>
						<td>
							<a href="sql-solution-instance-portal.jsp?paramName=${netWork.name}">查看</a>
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
