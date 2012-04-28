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
    
    <title>My JSP 'left.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="workflow/ndyd/main_left.css" rel="stylesheet" type="text/css" />

	
	<script type="text/javascript">
		var pTitle = "";
		function init(){
			var processStr = '${sessionScope.processStr}';
			var processArr = processStr.split(";");
			var selectProcess = document.getElementById('selectProcess');
			var option1 = new Option("------请选择------",0);  
    			selectProcess.options.add(option1);  
			for( var i = 0; i < processArr.length-1; i++){
				var subProcess = processArr[i].split(",");
				var option1 = new Option(subProcess[1],subProcess[0]);  
				//document.getElementById('flowsearch').innerHTML += subProcess[0];
    			selectProcess.options.add(option1);  

			}
		}
		
		function loadWorkListCount(){
			document.forms[0].submit();
		}
		
		function titleSwitch(selectTitle){
			var baseSituation = document.getElementById("baseSituation");
			var datarankings = document.getElementById("datarankings");
			var basetitle = document.getElementById("basetitle");
			var datatitle = document.getElementById("datatitle");
			
			if(selectTitle.innerHTML == "基本情况"){	
				baseSituation.style.display="block";
				datarankings.style.display="none";
				basetitle.style.color = "#104892";
				datatitle.style.color = "#000000";
			}else{
				baseSituation.style.display="none";
				datarankings.style.display="block";
				basetitle.style.color = "#000000";
				datatitle.style.color = "#104892";
			}
		}
	</script>
  </head>
  
<body onload="init()">

	<!-- <div align="center" id="left_main">
		<div id="sysmsg_box">
			<div align="left" id="msg_bt">
				<img src="workflow/ndyd/images/systemmsg_bt.gif" width="64" height="20"/>
			</div>
			<div align="left" id="msgcontent">
				<div id="textformat">&nbsp;某某你好，您截至2010年12月27日8：23为止</div>
				<div id="textformat"><img src="workflow/ndyd/images/sanjiao.gif" width="6" height="6"/>
						&nbsp;&nbsp;代办工单(15)件；</div>
					<div id="textformat" style="text-indent:16mm;">新订阅内容(7)件;</div>
					<div id="textformat" style="text-indent:16mm;">新推荐内容(8)件;</div>
				<div id="textformat"><img src="workflow/ndyd/images/sanjiao.gif" width="6" height="6"/>
						&nbsp;&nbsp;新消息(4)件;</div>
			</div>
		</div> -->
		<div id="boxview_box">
			<!-- <div align="left" id="bossview_bt">
				<img src="workflow/ndyd/images/bossview_bt.gif"/>
			</div> -->
			<div align="center" id="bossviewcontent">
				<div id="bossbasetext"><span id="basetitle" style="font-weight:bold; color: #104892; cursor: pointer;" onmouseover="titleSwitch(this)">基本情况</span>&nbsp;|&nbsp;<span id="datatitle" style="font-weight:bold; cursor: pointer;" onmouseover="titleSwitch(this)">数据排名</span></div>
				<div id="baseSituation">
					<form action="workList.do?method=onShowBaseTable" method="get">
						<input type="hidden" name="method" value="onShowBaseTable"  />
						<div id="flowsearch">
							流程名：
							<select name="selectProcess" id="selectProcess">
								
							</select>
							&nbsp;&nbsp;<img src="workflow/ndyd/images/flowsearch_bt.gif" width="43" height="20" style="cursor:pointer;" onclick="loadWorkListCount()"/>
						</div>
	
					</form>
					<div id="gongdantitail">处理中工单：<span style="color:#0033CC">${requestScope.subListCount }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总工单：<span style="color:#0033CC">${requestScope.allListCount }</span></div>
	
					<table width="280" height="auto" cellspacing="0" id="bossviewtab">
					  <tr align="center">
					    <td width="91" height="28" class="bossviewtab_tr">部门</td>
					    <td width="52" class="bossviewtab_tr">本周内</td>
					    <td width="52" class="bossviewtab_tr">处理中</td>
					    <td width="70" class="bossviewtab_tr">超48小时</td>
					  </tr>

					  <c:forEach var="basedatalist" items="${requestScope.baseDataList}">
					  	<tr align="center">
						    <td height="28">${basedatalist.deptname }</td>
						    <td>${basedatalist.week }</td>
						    <td>${basedatalist.dealing }</td>
						    <td>${basedatalist.after28 }</td>
					  	</tr>
					  </c:forEach>
					  <!--
					  <tr align="center">
					    <td height="28">综合办公室</td>
					    <td>15</td>
					    <td>6</td>
					    <td>3</td>
					  </tr>
					  <tr align="center">
					    <td>客户相应中心</td>
					    <td>21</td>
					    <td>4</td>
					    <td>6</td>
					  </tr>
					  <tr align="center">
					    <td>福鼎分公司</td>
					    <td>6</td>
					    <td>3</td>
					    <td>7</td>
					  </tr>
					  <tr align="center">
					    <td>人力资源部</td>
					    <td>7</td>
					    <td>4</td>
					    <td>7</td>
					  </tr>
					  <tr align="center">
					    <td>领导</td>
					    <td>4</td>
					    <td>0</td>
					    <td>3</td>
					  </tr>
					  <tr align="center">
					    <td>市场经销部</td>
					    <td>9</td>
					    <td>3</td>
					    <td>4</td>
					  </tr>
					  <tr align="center">
					    <td>客户相应中心</td>
					    <td>21</td>
					    <td>4</td>
					    <td>6</td>
					  </tr>
					  <tr align="center">
					    <td>福鼎分公司</td>
					    <td>6</td>
					    <td>3</td>
					    <td>7</td>
					  </tr>
					  <tr align="center">
					    <td>领导</td>
					    <td>4</td>
					    <td>0</td>
					    <td>3</td>
					  </tr>
					  <tr align="center">
					    <td>综合办公室</td>
					    <td>15</td>
					    <td>6</td>
					    <td>3</td>
					  </tr>
					  <tr align="center">
					    <td>市场经销部</td>
					    <td>9</td>
					    <td>3</td>
					    <td>4</td>
					  </tr>
					  <tr align="center">
					    <td>客户响应中心</td>
					    <td>21</td>
					    <td>4</td>
					    <td>6</td>
					  </tr>
					  <tr align="center">
					    <td>福鼎分公司</td>
					    <td>6</td>
					    <td>3</td>
					    <td>7</td>
					  </tr>
					  <tr align="center">
					    <td>人力资源部</td>
					    <td>7</td>
					    <td>4</td>
					    <td>7</td>
					  </tr> -->
					</table>
				</div>
				
				<div id="datarankings">
					<div id="agentDepartment" class="agentbox">
						<div id="demtitle" class="rankingstitle">代办部门TOP5</div>
						<div id="demcontent" class="rankingscontent">
							<table name="agenttable" cellspacing="2" class="rankingstable">
								<tr>
									<td width="72%">部门</td>
									<td>代办工单数</td>
								</tr>
								<c:forEach var="departmentlist" items="${sessionScope.deptList}">
									<tr>
										<td>${departmentlist.deptname }</td>
										<td>${departmentlist.workordercount }</td>
									</tr>									
								</c:forEach>

							</table>
						</div>
					</div>
					<div id="agentperson" class="agentbox">
						<div id="pertitle" class="rankingstitle">代办人员TOP5</div>
						<div id="percontent" class="rankingscontent">
							<table name="agenttable" cellspacing="2" class="rankingstable">
								<tr>
									<td width="72%">人员</td>
									<td>代办工单数</td>
								</tr>
								<c:forEach var="personlist" items="${sessionScope.userList}">
									<tr>
										<td>${personlist.personname }</td>
										<td>${personlist.workordercount }</td>
									</tr>									
								</c:forEach>
							</table>
						</div>
					</div>
					<div id="agentprocess" class="agentbox">
						<div id="protitle" class="rankingstitle">代办流程TOP5</div>
						<div id="procontent" class="rankingscontent">
							<table name="agenttable" cellspacing="2" class="rankingstable">
								<tr>
									<td width="72%">流程名</td>
									<td>代办工单数</td>
								</tr>
								<c:forEach var="processlist" items="${sessionScope.processList}">
									<tr>
										<td>${processlist.processname }</td>
										<td>${processlist.workordercount }</td>
									</tr>									
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	<!-- </div> -->
</body>
</html>
