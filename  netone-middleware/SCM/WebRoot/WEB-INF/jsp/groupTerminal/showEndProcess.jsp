<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../common/metaExt.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
	<script type="text/javascript">
	
		function up_Next_1(){
			parent.$disabledall();
			var groupTerminalId = document.getElementById('groupTerminalId').value;
			var url = "<%=path%>/groupTerminal/groupTerminal.do?method=onEditView&groupTerminalId="+groupTerminalId;
			parent.document.getElementById('flowMainFrame').src=url;
			parent.$hideall();
			parent.$show('new_1');
		}
	
		function init(){
		}
		
		function showperson(value,activeids,singleselect,usercode,username){//多选
			document.getElementById('choosepersonbox').style.display="block";
			if (usercode==null || usercode=='undefiend') usercode='';
			if (username==null || usercode=='undefiend') username='';
			document.getElementById('tmp_usercode').value=usercode;
			document.getElementById('tmp_username').value=username;
			document.getElementById('personframe').src="<%=basePath%>department/user.do?method=onMultiSelectUserX&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+activeids+"&usercode="+usercode+"&username="+username;
		}
		
		function showperson2(value,activeids,singleselect,usercode,username){//单选
			document.getElementById('choosepersonbox').style.display="block";
			if (usercode==null || usercode=='undefiend') usercode='';
			if (username==null || usercode=='undefiend') username='';
			document.getElementById('tmp_usercode').value=usercode;
			document.getElementById('tmp_username').value=username;
			document.getElementById('personframe').src="<%=basePath%>department/user.do?method=onMultiSelectUserX&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+activeids+"&usercode="+usercode+"&username="+username;
		}
		
		function up_5(){
			var groupTerminalId = document.getElementById('groupTerminalId').value;
			var workcode = document.getElementById('workcode').value;
			var chooseresult = document.getElementById('chooseresult').value;
			var url ="<%=path%>/groupTerminal/groupTerminal.do?method=onAuditView&chooseresult="+ chooseresult+"&groupTerminalId="+groupTerminalId+"&workcode="+workcode;
			parent.document.getElementById('flowMainFrame').src=url;
			parent.$hideall();
			parent.$show('audit_2');
			parent.$disabledall();
		}
		
		function end(){
			<c:choose>
				<c:when test="${param.chooseresult==0}">
					auditEnd();
				</c:when>
				<c:otherwise>
					speicalAuditEnd();
				</c:otherwise>
			</c:choose>
		}
		
		function auditEnd(){
				
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/groupTerminal/groupTerminal.do?method=onNewEnd' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	parent.$disabledall();
				            	parent.parent.window.opener=null;
				            	parent.parent.window.close();
				            	//parent.window.close();
				                Ext.ux.Toast.msg("", result.tip);
				            }
				        },
				        failure: function (response, options) {
				            msgTip.hide();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			}
		
		function speicalAuditEnd(){
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/groupTerminal/groupTerminal.do?method=onAuditEnd' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	parent.$disabledall();
				            	parent.parent.window.opener=null;
				            	parent.parent.window.close();
				            	//parent.window.close();
				                Ext.ux.Toast.msg("", result.tip);
				            }
				        },
				        failure: function (response, options) {
				            msgTip.hide();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			}
		
	</script>


  </head>
  
  <body onload="init()">
  <form id="form1" >
		<input type="hidden" id="userid" name="userid"/>
		<input type="hidden" id="usercode" name="usercode" />
		<input type="hidden" id="username" name="username" />
		<input type="hidden" id="activityid" name="activityid" />
		<input type="hidden" id="limittime" name="limittime" />
		<input type="hidden" id="workcode" name="workcode" value="${param.workcode}" />
		<input type="hidden" id="runtimeid" name="runtimeid" value="${param.runtimeid}" />
		
		<input type="hidden" id="tmp_usercode" name="tmp_usercode" value="${processList[0].usercode}" />
		<input type="hidden" id="tmp_username" name="tmp_username" value="${processList[0].username}" />
		<input type="hidden" id="processlen" name="processlen" value="${fn:length(processList)}"/>
		
		<input type="hidden" id="issync" name="issync" value="0" />
		
		<input type="hidden" id="operatemode" name="operatemode" value="${param.operatemode}" />
	</form>

	<input type="hidden" id="groupTerminalId" name="groupTerminalId" value="${param.groupTerminalId}">
	<input type="hidden" id="chooseresult" name="chooseresult" value="${param.chooseresult}">
  

			<div>
			<table width="100%" >
					<tr>
						<td width="12px">
							<img src="<%=path%>/script/theme/main/blue/images/first_top.gif"/>
						</td>
						<td id="steps_nd">
							步骤:第
							<c:choose>
								<c:when test="${param.flowppage==2}">
							 		<span id="stepNum">2</span> 步,共 3 步。
							 	</c:when>
								<c:otherwise>
									<span id="stepNum">3</span> 步,共 3 步。
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td width="28px">
							<div id="top_nd-title">
							</div>
						</td>
						<td id="title_nd">
							${processTitle} 
							<span id="helpInfo_nd">${helpTip}</span>
						</td>
					</tr>
				</table>
				<hr id="hr_nd">
			</div>
		<div id="box">
			<span id="helpInfo_nd"><font style="font-size: 14px;font-weight: bold;">${processEndTip}</font></span>
	    </div>
  </body>
</html>
