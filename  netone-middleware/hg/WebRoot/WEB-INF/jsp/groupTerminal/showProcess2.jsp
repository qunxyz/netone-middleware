<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'showProcessList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../common/metaExt.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
	<script language="javascript" type="text/javascript"
		src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312">
		

	</script>

	<script type="text/javascript">
	
		function init(){
			document.getElementById("typechoosebt1").style.backgroundImage = "url(script/theme/main/blue/images/processtylechoose_bt2.gif)";
		}
		
		function showperson(value,list){
			if('${processTitle }' != "退办"){
			
				document.getElementById('choosepersonbox').style.display="block";
				document.getElementById('personframe').style.display="block";
				

			}else{
				alert(list);
				document.getElementById('choosepersonbox').style.display="block";
				document.getElementById('personbox').style.display="block";
				document.getElementById('personframe').style.display="none";
			}
			document.getElementById('personframe').src="<%=basePath%>department/user.do?method=onMultiSelectUserX&hiddendept=1&includedept=0&node="+value;
			
		}
		
		function showframe(){
			document.getElementById('personbox').style.display="none";
			document.getElementById('personframe').style.display="block";
		}
		
	</script>


  </head>
  
  <body onload="init()">
    <div id="box">
    	<div id="operationmenu">
			
				<div id="operationbt" onclick="window.location='<%=basePath%>groupTerminal/groupTerminal.do?method=onChooseView'">上一步</div>
				<span id="operationbt">完成</span>
				<span id="operationbt" onclick="window.close()">取消</span>
			
		</div>
    	<div id="steptext">步骤：&nbsp;第&nbsp;<span style="font-weight: bold; font-size: 14px;">3</span>&nbsp;步，共3部</div>
    	<div id="titlebox"><img src="script/theme/main/blue/images/title.gif"/>&nbsp;${processTitle }</div>
    	<div id="helptips">${helpTip }</div>
    	<center>
			<div id="processcontentbox">
				<div id="typechoosebox">
					<div id="typechoosebt1" class="typechoosebt" name="bt1"><div style="padding-top: 15px;">图形展示</div></div>
					<div id="typechoosebt2" class="typechoosebt" name="bt2"><div style="padding-top: 15px;">列表展示</div></div>
				</div>
				<div id="processcontent">
					<div style="float:left;"><img src="script/theme/main/blue/images/process_bg_left.gif"/></div>
					<div id="processlist" >
					
						<c:forEach var="processlist" items="${requestScope.processList}">
							<div id="listdatabox" onmouseover="this.style.color='#ffffff'" onmouseout="this.style.color='#396099'" onclick="showperson('${processlist.value}','${processlist.humenList}')">${processlist.name}</div>
						</c:forEach>
						<!-- <div id="listdatabox" onmouseover="this.style.color='#ffffff'" onmouseout="this.style.color='#396099'">客户经理申请</div>
						<div id="listdatabox" onmouseover="this.style.color='#ffffff'" onmouseout="this.style.color='#396099'">集团经理复核</div>
						<div id="listdatabox" onmouseover="this.style.color='#ffffff'" onmouseout="this.style.color='#396099'">分公司领导审批</div> -->
			  		</div>
					<div style="float:left;"><img src="script/theme/main/blue/images/process_bg_right.gif"/></div>
					<div id="choosepersonbox">

						<div id="">
							<div id="personbox">
								
								<div id="personname" onmouseover="this.style.color='#6AA4D2'" onmouseout="this.style.color='#4B6E94'">少波</div>
								<div id="personname" onmouseover="this.style.color='#6AA4D2'" onmouseout="this.style.color='#4B6E94'">少波</div>
								<div id="personname" onmouseover="this.style.color='#6AA4D2'" onmouseout="this.style.color='#4B6E94'">少波</div>
								<div id="otherpersonbt" onclick="showframe()"><img src="script/theme/main/blue/images/otherperson_bt.gif"/></div>
							</div>
							<iframe style="display:none;" id="personframe" width="400" height="270" src=""/>
						</div>
						
					</div>
				</div>
			</div>
		</center>
    </div>
  </body>
</html>
