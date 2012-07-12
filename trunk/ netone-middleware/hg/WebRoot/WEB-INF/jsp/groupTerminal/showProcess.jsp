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
	</script>

	<script type="text/javascript">
		
		function showTrackActionSpecialType1(){
			var o = $('#trackActionSpecialType1_div');
			var btn = $('#trackActionSpecialType1_btn');
			if (o.css('display')=='none'){
				btn.val('隐藏');
				o.show();
			} else {
				btn.val('显示');
				o.hide();
			}
			if (o.html()==''){
				o.html('<iframe id="personframe" width="600" height="300" src="'+$('#personiframe1').val()+'" />');
			}
		}
		
		function showTrackActionSpecialType2(){
			var o = $('#trackActionSpecialType2_div');
			var btn = $('#trackActionSpecialType12_btn');
			if (o.css('display')=='none'){
				btn.val('隐藏');
				o.show();
			} else {
				btn.val('显示');
				o.hide();
			}
			if (o.html()==''){
				o.html('<iframe id="personframe" width="600" height="300" src="'+$('#personiframe2').val()+'" />');
			}
		}
			
	
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
		
		function returnx(){
			$('#processcontent').show();
			$('#leftprocesscontent').show();
			$('#personselect').html('');
			$('#personselect').hide();
		}
		
		function showperson(name,value,activeids,singleman,autoroute,usercode,username,special,pmode){//人员选择
			if (usercode==null || usercode=='undefiend') usercode='';
			if (username==null || usercode=='undefiend') username='';

			var personframe = "";
			var singleselect = 0;
			if (autoroute==true){
				personframe="<%=path%>/frame.do?method=onAutorouteView";
			} else {
				if (singleman==true || singleman=='true'){
					singleselect = 1;
					personframe="<%=path%>/department/user.do?method=onSingleSelectUserX&pmode="+pmode+"&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+activeids+"&usercode="+usercode+"&username="+username;
				} else {
					singleselect = 0;
					personframe="<%=path%>/department/user.do?method=onMultiSelectUserX&pmode="+pmode+"&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+activeids+"&usercode="+usercode+"&username="+username;
				}
			}
			
			if (special==1 || special==2){
				$('#personselect').html('');
				$('#personselect').append('<div align="left" style="text-align: left;">'+name+'</div><iframe id="personframe" width="600" height="300" src="'+personframe+'"/>');
				singleselect=0;
				usercode='';
				username='';
				name='抄送';
				value='0';
				activeids='trackActionSpecialType1';
				singleman=false;
				autoroute=true;
				personframe="<%=path%>/department/user.do?method=onMultiSelectUserX&pmode="+pmode+"&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+activeids+"&usercode="+usercode+"&username="+username;
				var btn1= '<input id=\"trackActionSpecialType1_btn\" type=\"button\" value="显示" onclick=\"showTrackActionSpecialType1();\"  />'
				$('#personselect').append('<div align="left" style="text-align: left;">'+name+"&nbsp;"+btn1+'</div>');
				$('#personselect').append('<div id=\"trackActionSpecialType1_div\" style=\"display:none\"></div>');
				$('#personiframe1').val(personframe);
				
				name='抄阅';
				activeids='trackActionSpecialType2';
				
				var btn2= '<input id=\"trackActionSpecialType2_btn\" type=\"button\" value="显示" onclick=\"showTrackActionSpecialType2();\"  />'
				$('#personselect').append('<div align="left" style="text-align: left;">'+name+"&nbsp;"+btn2+'</div>');
				$('#personselect').append('<div id=\"trackActionSpecialType2_div\" style=\"display:none\"></div>');
				$('#personiframe2').val(personframe);
				
				if (special==2){//有返回按钮
					$('#personselect').prepend('<div align="left" style="text-align: left;"><input type="button" value="返回" onclick="javascript:returnx()" /></div>');
				}
				$('#leftprocesscontent').hide();
				$('#processcontent').hide();
				$('#userid').val('');
				$('#usercode').val('');
				$('#username').val('');
				$('#activityid').val('');
				$('#limittime').val('');
			} else {
				$('#leftprocesscontent').show();
				//$('#processcontent').show();
				$('#personselect').append('<input type=\"hidden\" id=\"'+activeids+'_usercode\" value=\"'+usercode+'\" />');
				$('#personselect').append('<input type=\"hidden\" id=\"'+activeids+'_username\" value=\"'+username+'\" />');
				$('#personselect').append('<div align="left" style="text-align: left;">'+name+'</div><iframe id="personframe" width="600" height="300" src="'+personframe+'" />');
			}
			$('#personselect').show();
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
				 var notice = 0;
			  	 if($("#notice").attr("checked")==true){
			  	 	notice = document.getElementById('notice').value;
			  	 }
				
				var userid = document.getElementById('usercode').value;
				if (userid==''){
					return '未选择,请选择人员或部门提交!';
				}
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/groupTerminal/groupTerminal.do?method=onNewEnd' />&notice="+notice,
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
				var notice = 0;
			  	 if($("#notice").attr("checked")==true){
			  	 	notice = document.getElementById('notice').value;
			  	 }
				var userid = document.getElementById('usercode').value;
				if (userid==''){
					return '未选择,请选择人员或部门提交!';
				}
				
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/groupTerminal/groupTerminal.do?method=onAuditEnd' />&notice="+notice,
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
  
  <body>
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
		<input type="hidden" id="processlen" name="processlen" value="${fn:length(processList)+2}"/>
		
		<input type="hidden" id="issync" name="issync" value="0" />
		
		<input type="hidden" id="operatemode" name="operatemode" value="${param.operatemode}" />
	</form>
  	<input type="hidden" id="personiframe1" name="personiframe1"  />
  	<input type="hidden" id="personiframe2" name="personiframe2"  />
    <div id="box">

			<div>
				<table width="100%" >
					<tr>
						<td width="12px">
							<img src="<%=path%>/script/theme/main/blue/images/first_top.gif"/>
						</td>
						<td id="steps_nd">
							<c:choose>
								<c:when test="${param.flowppage==2}">
									步骤:第 <span id="stepNum">2</span> 步,共 2 步。
								</c:when>
								<c:otherwise>
									步骤:第 <span id="stepNum">3</span> 步,共 3 步。
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
			<div id="noticeBox">
			<input name="notice" id="notice" type="checkbox" value="1" />流转状态变化时不发送短信
			</div>
			<div id="processcontentbox">
				<input type="hidden" id="lsh" name="lsh"
						value="${param.lsh}">
				<input type="hidden" id="chooseresult" name="chooseresult"
						value="${param.chooseresult}">
				<div id="personselect" align="left" style="text-align: left;"></div>		
				<div >
						
						
				<c:forEach var="processlist" items="${processList}" varStatus="step">
					<c:choose>
					<c:when test="${param.chooseresult=='0'}">
					<script>
					showperson('${processlist.name}','${processlist.value}','${processlist.activeids}','${processlist.singleman}','${processlist.autoroute}','${processlist.usercode}','${processlist.username}',1,'${processlist.pmode}');
					</script>
					</c:when>
					<c:otherwise>
					
						<c:choose>
						<c:when test="${fn:length(processList)==1}">
						<script>
						showperson('${processlist.name}','${processlist.value}','${processlist.activeids}','${processlist.singleman}','${processlist.autoroute}','${processlist.usercode}','${processlist.username}',1,'${processlist.pmode}');
						</script>
						</c:when>
						<c:otherwise>
						<div id="processlist">
						    <div id="leftprocesscontent" style="float:left;display: none;" ><img src="<%=path%>/script/theme/main/blue/images/process_bg_left.gif"/></div>
							<div id="listdatabox" onmouseover="this.style.color='#ffffff'" onmouseout="this.style.color='#396099'"
							onclick="showperson('${processlist.name}','${processlist.value}','${processlist.activeids}','${processlist.singleman}','${processlist.autoroute}','${processlist.usercode}','${processlist.username}',2,'${processlist.pmode}')"
							>${processlist.name}</div>
						</div>
						</c:otherwise>
						</c:choose>
					
					</c:otherwise>
					</c:choose>
				</c:forEach>
				  		
				</div>
			</div>
    </div>
  </body>
</html>
