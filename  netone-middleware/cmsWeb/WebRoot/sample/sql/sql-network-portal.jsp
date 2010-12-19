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
           
           function onTaskView(network){//�鿴��Ԫ��Ӧ�ļ������
              var url = 'sql-task-portal.jsp?paramName='+network;
              openWinCenter('taskWin', url, 700, 400, true);
           }
		</script>
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
			ע�⣺��Ҫʹ��page.js������ҳ�������ø�js<br>
		<b>��������</b><br>
		ʹ�õ�����Դ����ΪDATASOURCE.DATASOURCE.INIS,
		
		<p>˵���� ��������ӣ������õ���Դ�����е�sql�������</p>
		<br>
		<hr>
			<!-- ������Դ�� SAMPLE -->
			<resource:loadResourceByNatural naturalname="SQL.SQL.INIS.NETMASK.SQLCOUNT" dataname="sqlCount"/>
			<resource:loadResourceByNatural naturalname="SQL.SQL.INIS.NETMASK.SQL" dataname="sql"/>
			         
		
			<rs:sql ds="DATASOURCE.DATASOURCE.INIS " sqlcount="${sqlCount.extendattribute}"
				sql="${sql.extendattribute}" dataname="netWorkSet" prepage="10"></rs:sql>

			<table width="774" border="1">
				<tr style="text-align: center">
					<td width="201">
						��Ԫ����
					</td>
					<td width="194">
						��Ԫ����
					</td>
					<td width="250">
						���Ѳ������
					</td>
					<td width="250">
						���Ѳ�췽��
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
							<a href="sql-task-portal.jsp?paramName=${netWork.name}">�鿴</a>
							<!-- <label onClick="javascript:onTaskView('${netWork.name}');" style="cursor:hand;color:#0066FF">�鿴</label>-->
						</td>
						<td>
							<a href="sql-solution-instance-portal.jsp?paramName=${netWork.name}">�鿴</a>
						</td>
						<td>
							${task.status}
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
