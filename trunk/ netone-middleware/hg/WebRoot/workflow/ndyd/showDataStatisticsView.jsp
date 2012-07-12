<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../WEB-INF/jsp/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'showDataStatisticsView.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="workflow/styles/dataStatistics.css">

	<script type="text/javascript">
		function init(){
			var processStr = '${requestScope.processStr}';
			var processArr = processStr.split(";");
			var selectProcess = document.getElementById('selectProcess');
			var option1 = new Option("------请选择------",0);  
    			selectProcess.options.add(option1);  
			for( var i = 0; i < processArr.length-1; i++){
				var subProcess = processArr[i].split(",");
				var option1 = new Option(subProcess[1],subProcess[0]);  
    			selectProcess.options.add(option1);  

			}
		}
		
		function titleSwitch(selectTitle){
			document.getElementById('departmentDetailViewBox').style.display="none";
			document.getElementById('linkDetailViewBox').style.display="none";
			document.getElementById('processDetailViewBox').style.display="none";
			document.getElementById('subProcessTotalViewBox').style.display="none";
			document.getElementById(selectTitle).style.display="block";
		}
	</script>
	
  </head>
  
  <body onload="init()">
    <div id="box">
		<center>
		<!-------部门详细查询视图--------->
    		<div id="alltablebox">
				<table width="890" cellspacing="0">
				  <tr align="center">
					<td width="25%" height="32"><a href="javascript:titleSwitch('departmentDetailViewBox')">部门详细查询视图</a></td>
					<td width="25%"><a href="javascript:titleSwitch('linkDetailViewBox')">环节详细查询视图</a></td>
					<td width="25%"><a href="javascript:titleSwitch('processDetailViewBox')">流程详细查询视图</a></td>
					<td width="25%"><a href="javascript:titleSwitch('subProcessTotalViewBox')">分流程统计视图</a></td>
				  </tr>
				  <tr>
					<td colspan="4" height="25">&nbsp;
						<div id="processOperate">
							<div id="processSelect">
								<span>请选择流程：</span>
								<select id="selectProcess">
									
								</select>
							</div>
							<div id="queryTime">
								<form action="">
									<span>
										工单建立时间范围：&nbsp;<input type="text" />&nbsp;至&nbsp;<input type="text" />
										&nbsp;<input type="submit" value="查询"/>
									</span>
								</form>
							</div>
						</div>
					</td>
					
				  </tr>
				</table>
				<div id="departmentDetailViewBox">
					<table width="890" border="0" cellspacing="0" cellpadding="0">
					  <tr align="center">
						<td width="150">部门</td>
						<td width="90">平均代办历时</td>
						<td width="90">平均处理历时</td>
						<td width="90">本周新建</td>
						<td width="90">本周处理完成</td>
						<td width="90">本周结算工单</td>
						<td width="90">办理中</td>
						<td width="90">代办超48小时</td>
						<td width="110">代办超一个月工单</td>
					  </tr>
	
					  <c:forEach var="statisticslist" items="${requestScope.statisticsList}">
						  <tr align="center">
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
						  </tr>
					  </c:forEach>
					  
					  <tr align="center">
							<td>领导</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>综合办公室</td>
							<td>8812</td>
							<td>408</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>5</td>
							<td>5</td>
							<td>5</td>
					  </tr>
					  <tr align="center">
							<td>市场经营部</td>
							<td>8861</td>
							<td>449</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>6</td>
							<td>6</td>
							<td>6</td>
					  </tr>
					  <tr align="center">
							<td>|--产品运营室</td>
							<td>0</td>
							<td>222</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--市场营销室</td>
							<td>8787</td>
							<td>607</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
					  </tr>
					  <tr align="center">
							<td>|--销售服务室</td>
							<td>8835</td>
							<td>397</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
					  </tr>
					  <tr align="center">
							<td>|--宽带运营室</td>
							<td>0</td>
							<td>105</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--综合业务室</td>
							<td>8886</td>
							<td>424</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>4</td>
							<td>4</td>
							<td>4</td>
					  </tr>
					  <tr align="center">
							<td>集团大客户部</td>
							<td>10168</td>
							<td>504</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>4</td>
							<td>4</td>
							<td>4</td>
					  </tr>
					  <tr align="center">
							<td>|--客服服务室</td>
							<td>10834</td>
							<td>929</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>2</td>
							<td>2</td>
							<td>2</td>
					  </tr>
					  <tr align="center">
							<td>|--行业应用室</td>
							<td>8835</td>
							<td>265</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
					  </tr>
					  <tr align="center">
							<td>|--营销服务室</td>
							<td>0</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>客户响应中心</td>
							<td>8807</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--服务管理室</td>
							<td>0</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--客响运营室</td>
							<td>0</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					</table>
	
				</div>
		<!-------------------------------->
			
				
		<!-------环节详细查询视图--------->
				<div id="linkDetailViewBox">
					<table width="890" cellspacing="0">
					  <tr align="center">
						<td width="25%" height="32">流程环节</td>
						<td width="25%">平均代办历时</td>
						<td width="25%">工单数</td>
						<td width="25%">需处理人数</td>
					  </tr>
					  <c:forEach var="statisticslist" items="${requestScope.statisticsList}">
					  	<tr>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
					  	</tr>
					  </c:forEach>
					  <tr>
							<td>信息发布</td>
							<td>11302</td>
							<td>2</td>
							<td>2</td>
					  </tr>
					  <tr>
							<td>县市公司预处理</td>
							<td>9486</td>
							<td>7</td>
							<td>7</td>
					  </tr>
					  <tr>
							<td>结果反馈</td>
							<td>9177</td>
							<td>2</td>
							<td>2</td>
					  </tr>
					  <tr>
							<td>归档</td>
							<td>9334</td>
							<td>2</td>
							<td>2</td>
					  </tr>
					  <tr>
							<td>客响中心预处理</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr>
							<td>责任部门预处理</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr>
							<td>责任人处理</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr>
							<td>县市公司负责人处理</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr height="30">
					  	<td>已归档</td>
					  	<td colspan="3">${requestScope.archiveCount }件工单</td>
					  </tr>
					  <tr height="30">
					  	<td>共计</td>
					  	<td colspan="3">${requestScope.totalCount }件工单</td>
					  </tr>
					</table>
				</div>
		<!-------------------------------->
		
				
		<!-------流程详细查询视图--------->
				<div id="processDetailViewBox">
					<table width="890" cellspacing="0">
					  <tr align="center">
						<td width="290" height="32">工单主题</td>
						<td width="150">创建时间</td>
						<td width="150">当前节点</td>
						<td width="150">当前处理人</td>
						<td width="150">总历时（小时/天数）</td>
					  </tr>
					  <c:forEach var="statisticslist" items="${requestScope.statisticsList}">
					  	<tr>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
					  	</tr>
					  </c:forEach>

					</table>
				</div>
		<!-------------------------------->
						
				
		<!-------分流程统计视图--------->
				<div id="subProcessTotalViewBox">
					<table width="890" cellspacing="0">
					  <tr align="center">
						<td width="170" height="32">部门</td>
						<td width="60">平均代办历时</td>
						<td width="60">平均工单总历时</td>
						<td width="60">发起工单总数</td>
						<td width="60">已归档</td>
						<td width="60">所有代办</td>
						<td width="60">代办超48小时</td>
						<td width="60">代办超一周</td>
						<td width="60">代办超一个月</td>
						<td width="60">总历时超48小时</td>
						<td width="60">总历时超一周</td>
						<td width="60">总历时超一个月</td>
						<td width="60">总历时超三个月</td>
					  </tr>
					  <c:forEach var="statisticslist" items="${requestScope.statisticsList}">
					  	<tr>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
							<td>${statisticslist. }</td>
					  	</tr>
					  </c:forEach>
					  
					  <tr align="center">
							<td>领导</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>综合办公室</td>
							<td>8812</td>
							<td>408</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>5</td>
							<td>5</td>
							<td>5</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>市场经营部</td>
							<td>8861</td>
							<td>449</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>6</td>
							<td>6</td>
							<td>6</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--产品运营室</td>
							<td>0</td>
							<td>222</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--市场营销室</td>
							<td>8787</td>
							<td>607</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--销售服务室</td>
							<td>8835</td>
							<td>397</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1</td>
							<td>1</td>
							<td>1</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--宽带运营室</td>
							<td>0</td>
							<td>105</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--综合业务室</td>
							<td>8886</td>
							<td>424</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>4</td>
							<td>4</td>
							<td>4</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>集团大客户部</td>
							<td>10168</td>
							<td>504</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>4</td>
							<td>4</td>
							<td>4</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--客服服务室</td>
							<td>10834</td>
							<td>929</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>2</td>
							<td>2</td>
							<td>2</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--行业应用室</td>
							<td>8835</td>
							<td>265</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>1</td>
							<td>1</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  
					  <tr align="center">
							<td>|--营销服务室</td>
							<td>0</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>客户响应中心</td>
							<td>8807</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--服务管理室</td>
							<td>0</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>
					  <tr align="center">
							<td>|--客响运营室</td>
							<td>0</td>
							<td>269</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
					  </tr>

					</table>
				</div>
				
		<!-------------------------------->
			</div>
		</center>
    </div>
  </body>
</html>
