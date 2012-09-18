<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><c:choose><c:when test="${!empty htmltitleinfo}">${htmltitleinfo} - ${activityName}</c:when><c:otherwise>${activityName}</c:otherwise></c:choose></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
	<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery.highlight-3.js"></script>
	</script>
	<style type="text/css">
	.highlight {
		color: #0C76B6;
		font-size: 15px;
		font-weight:bold;
		}
	</style>
	<script type="text/javascript">
		$(function() {
				showIframe('0');
		});
				
		function showIframe(activeid){
			$('iframe').each(function(){
				$(this).css('display','none');
			});
			$('body #vertmenu a').removeHighlight();//取消高亮
			var i = 0;
			$('iframe').each(function(){
				var id = $(this).attr('id');
				id = id.replace('personframe','');
				var personframesrc = $('#personframe'+id).attr('src')
				$('#listdatabox'+id).css('background-color','');
				if (activeid=='0' || activeid==''){
					if (i==0){
						$(this).css('display','block');
						$('#listdatabox'+id).highlight($('#listdatabox'+id).html(), { wordsOnly: true });
						if (personframesrc=='' || personframesrc=='undefined' || personframesrc==null){
							$('#personframe'+id).attr('src',$('#'+id).val());
						}
						$('#listdatabox'+id).css('background-color','#D3E1F1');
					}
					i++;
				} else if (activeid==id){
					$(this).css('display','block');
					$('#listdatabox'+id).highlight($('#listdatabox'+id).html(), { wordsOnly: true });
					if (personframesrc=='' || personframesrc=='undefined' || personframesrc==null){
						$('#personframe'+id).attr('src',$('#'+id).val());
					}
					$('#listdatabox'+id).css('background-color','#D3E1F1');
				}
			});
		}
		
		function up_Next_1(){
			Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在跳转请稍候......'
		    });
			$disabledall();
			var lsh = document.getElementById('lsh').value;
			var url = "<%=path%>/frame.do?method=onEditViewMain&naturalname=${param.naturalname}&lsh=${param.lsh}&runtimeid=${param.runtimeid}&chooseresult=${param.chooseresult}"+'&filteractiveids_=${param.filteractiveids}';
			$hideall();
			$show('new_1');
			window.location.href=url;
		}
		
		function showperson(name,value,activeids,singleman,autoroute,usercode,username,special,pmode,needtree,needsync,needsearch){//人员选择
			if (usercode==null || usercode=='undefiend') usercode='';
			if (username==null || usercode=='undefiend') username='';
	
			var personframe = "";
			var singleselect = 0;
			var hiddendept = 0;
			if (autoroute==true){
				personframe="<%=path%>/frame.do?method=onAutorouteView";
			} else {
				if (singleman==true || singleman=='true'){
					singleselect = 1;
				} else {
					singleselect = 0;
				}
				if (needtree==true || needtree=='true'){
					hiddendept = 0;
				} else {
					hiddendept = 1;
				}
				personframe="<%=path%>/department/user.do?method=onMultiSelectUserX&pmode="+pmode+"&singleselect="+singleselect+"&hiddendept="+hiddendept+"&includedept=0&node="+value+"&activityid="+activeids+"&usercode="+usercode+"&username="+username+"&needsync="+needsync+"&needsearch="+needsearch;
			}
			
			if (activeids=='trackActionSpecialType1'){
				singleselect=0;
				usercode='';
				username='';
				name='抄送';
				value='0';
				//activeids='trackActionSpecialType1';
				singleman=false;
				autoroute=false;
				needsearch=true;
				personframe="<%=path%>/department/user.do?method=onMultiSelectUserX&pmode="+pmode+"&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+'trackActionSpecialType1'+"&usercode="+usercode+"&username="+username+"&needsearch="+needsearch;
			} else if (activeids=='trackActionSpecialType2'){
				singleselect=0;
				usercode='';
				username='';
				value='0';
				//activeids='trackActionSpecialType2';
				singleman=false;
				autoroute=false;
				name='抄阅';
				needsearch=true;
				personframe="<%=path%>/department/user.do?method=onMultiSelectUserX&pmode="+pmode+"&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+'trackActionSpecialType2'+"&usercode="+usercode+"&username="+username+"&needsearch="+needsearch;
			} else if (activeids=='trackActionSpecialType3'){
				singleselect=0;
				usercode='';
				username='';
				value='0';
				//activeids='trackActionSpecialType3';
				singleman=false;
				autoroute=false;
				name='阶段性回复';
				needsearch=true;
				personframe="<%=path%>/department/user.do?method=onMultiSelectUserX&pmode="+pmode+"&singleselect="+singleselect+"&hiddendept=0&includedept=0&node="+value+"&activityid="+'trackActionSpecialType3'+"&usercode="+usercode+"&username="+username+"&needsearch="+needsearch;
			}
			$('#'+activeids).val(personframe);
			$('#leftprocesscontent').show();
			$('#processlist').show();
			$('#personselect').show();
		}
		
		function up_5(){
			Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在跳转请稍候......'
		    });
			var lsh = document.getElementById('lsh').value;
			var workcode = document.getElementById('workcode').value;
			var chooseresult = document.getElementById('chooseresult').value;
			var url ="<%=path%>/frame.do?method=onAuditView&naturalname=${param.naturalname}&chooseresult="+ chooseresult+"&lsh="+lsh+"&workcode="+workcode+'&filteractiveids_=${param.filteractiveids}&commiter=${param.commiter}';
			//$hideall();
			//$show('audit_2');
			$disabledall();
			window.location.href=url+'&page=audit_2';
		}
		
		function end(){
			<c:choose>
				<c:when test="${param.chooseresult==0}">
					auditEnd();
				</c:when>
				<c:when test="${param.chooseresult==3}">
					assignAuditEnd();
				</c:when>
				<c:otherwise>
					speicalAuditEnd();
				</c:otherwise>
			</c:choose>
		}
		
		function _refreshOpenerWin() {
			if (opener && opener.location) {
				if (opener.document.getElementById('openerWinIdx')){
					opener.$delnode(document.getElementById('lsh').value);
				}
		    }
		}
		
		function auditEnd(){
				var userid = document.getElementById('usercode').value;
				if (userid==''){
					Ext.MessageBox.alert('提示','未选择,请选择人员或部门提交!');
					return;
				}
				 var notice = 0;
			  	 if($("#notice").attr("checked")==true){
			  	 	notice = document.getElementById('notice').value;
			  	 }
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        closable:false,
			        msg: '正在执行操作请稍候......'
			    });
			    $disabledall();
				Ext.Ajax.request({
				        url: "<c:url value='/frame.do?method=onNewEnd' />&naturalname=${param.naturalname}&lsh=${param.lsh}&notice="+notice,
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				            	$enableall();
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	alert(result.tip);
				            	_refreshOpenerWin();
				            	window.opener=null;
				            	//window.open('','_top');
				            	window.close();
				            }
				        },
				        failure: function (response, options) {
				            msgTip.hide();
				            $enableall();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			}
		
		function speicalAuditEnd(){
				var userid = document.getElementById('usercode').value;
				if (userid==''){
					Ext.MessageBox.alert('提示','未选择,请选择人员或部门提交!');
					return;
				}
				 var notice = 0;
			  	 if($("#notice").attr("checked")==true){
			  	 	notice = document.getElementById('notice').value;
			  	 }
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        closable:false,
			        msg: '正在执行操作请稍候......'
			    });
			    $disabledall();
				Ext.Ajax.request({
				        url: "<c:url value='/frame.do?method=onAuditEnd' />&naturalname=${param.naturalname}&lsh=${param.lsh}&notice="+notice,
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				            	$enableall();
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	alert(result.tip);
				            	_refreshOpenerWin();
				            	window.opener=null;
				            	//window.open('','_top');
				            	window.close();
				            }
				        },
				        failure: function (response, options) {
				            msgTip.hide();
				            $enableall();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			}
			
			function assignAuditEnd(){
				var userid = document.getElementById('usercode').value;
				if (userid==''){
					Ext.MessageBox.alert('提示','未选择,请选择人员或部门提交!');
					return;
				}
				 var notice = 0;
			  	 if($("#notice").attr("checked")==true){
			  	 	notice = document.getElementById('notice').value;
			  	 }
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        closable:false,
			        msg: '正在执行操作请稍候......'
			    });
			    $disabledall();
				Ext.Ajax.request({
				        url: "<c:url value='/frame.do?method=onAssignEnd' />&commiter=${param.commiter}&naturalname=${param.naturalname}&lsh=${param.lsh}&notice="+notice,
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				            	$enableall();
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	alert(result.tip);
				            	_refreshOpenerWin();
				            	window.opener=null;
				            	//window.open('','_top');
				            	window.close();
				            }
				        },
				        failure: function (response, options) {
				            msgTip.hide();
				            $enableall();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			}
			
	</script>	
	${linkcss}
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
		
		<input type="hidden" id="tmp_usercode" name="tmp_usercode" value="" />
		<input type="hidden" id="tmp_username" name="tmp_username" value="" />
		<input type="hidden" id="processlen" name="processlen" value="${fn:length(processList)}"/>
		
		<input type="hidden" id="issync" name="issync" value="0" />
		
		<input type="hidden" id="operatemode" name="operatemode" value="${param.operatemode}" />
		<input type="hidden" id="naturalname" name="naturalname" value="${param.naturalname}" />
		<input type="hidden" id="filteractiveids" name="filteractiveids" value="${param.filteractiveids}" />
	</form>
	<center>
	<jsp:include page="template.jsp"></jsp:include>
    <div id="box" style="width:1000px;">
			<div>
				<table width="100%" >
					<tr>
						<td width="12px" align="left">
							<img src="<%=path%>/script/theme/main/blue/images/first_top.gif"/>
						</td>
						<td id="steps_nd" align="left">
							<c:choose>
								<c:when test="${param.flowppage==2}">
									步骤:第 <span id="stepNum">2</span> 步,共 2 步。
								</c:when>
								<c:otherwise>
									步骤:第 <span id="stepNum">3</span> 步,共 3 步。
								</c:otherwise>
							</c:choose>
							&nbsp;<span style="font-size: 16px;font-weight: bold;color: #386BA4;">${htmltitleinfo}</span>
						</td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td width="28px" align="left">
							<div id="top_nd-title">
							</div>
						</td>
						<td id="title_nd" align="left">
							${processTitle}
							<span id="helpInfo_nd">${helpTip}</span>
						</td>
					</tr>
				</table>
				<hr id="hr_nd">
			</div>
			<div id="processcontentbox">
				<input type="hidden" id="lsh" name="lsh" value="${param.lsh}">
				<input type="hidden" id="chooseresult" name="chooseresult" value="${param.chooseresult}">
				<c:choose>
				<c:when test="${fn:length(processList)==1}">
					<div id="processlist" align="left" style="height: 415px;width: 100%">
					    <input type="hidden" id="${processList[0].activeids}" name="${processList[0].activeids}"  />
					    <input type="hidden" id="${processList[0].activeids}_usercode" value="${processList[0].usercode}" />
					    <input type="hidden" id="${processList[0].activeids}_username" value="${processList[0].username}" />
						<div id="vertmenu" style="width: 100%;">
						<ul >
						<li><a id="listdatabox${processList[0].activeids}" style="width: 100%;border-bottom:0px dashed #2666AE;" href="#" title="${processList[0].name}" onclick="showIframe('${processList[0].activeids}')">${processList[0].name}</a></li>
						</ul>
						</div>
					
					<iframe id="personframe${processList[0].activeids}" width="920" align="left" height="390" style="display: none;" frameborder="0"></iframe>
					<script type="text/javascript">
					showperson('${processList[0].name}','${processList[0].value}','${processList[0].activeids}','${processList[0].singleman}','${processList[0].autoroute}','${processList[0].usercode}','${processList[0].username}',2,'${processList[0].pmode}','${processList[0].needtree}','${processList[0].needsync}','${processList[0].needsearch}');
					</script>
						
					</div>
				</c:when>
				<c:otherwise>
					<div id="processlist" align="left">
					<c:forEach var="processList" items="${processList}" varStatus="step">
						    <input type="hidden" id="${processList.activeids}" name="${processList.activeids}"  />
						    <input type="hidden" id="${processList.activeids}_usercode" value="${processList.usercode}" />
						    <input type="hidden" id="${processList.activeids}_username" value="${processList.username}" />
							<div id="vertmenu" >
							<ul >
							<li><a id="listdatabox${processList.activeids}" href="#" title="${processList.name}" onclick="showIframe('${processList.activeids}')">${processList.name}</a></li>
							</ul>
							</div>
					</c:forEach>
					</div>
					<c:forEach var="processList" items="${processList}" varStatus="step">
						<iframe id="personframe${processList.activeids}" width="696" align="left" height="400" style="display: none;" frameborder="0"></iframe>
						<script type="text/javascript">
						showperson('${processList.name}','${processList.value}','${processList.activeids}','${processList.singleman}','${processList.autoroute}','${processList.usercode}','${processList.username}',2,'${processList.pmode}','${processList.needtree}','${processList.needsync}','${processList.needsearch}');
						</script>
					</c:forEach>				
				</c:otherwise>
				</c:choose>
			</div>
			<div id="noticeBox" align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="notice" id="notice" type="checkbox" value="1" checked="checked" />流转状态变化时不发送短信
			</div>
    </div>
    </center>
    <jsp:include page="footer.jsp"></jsp:include>
  </body>
</html>
