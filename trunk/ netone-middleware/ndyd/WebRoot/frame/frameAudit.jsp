﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String cssURL = request.getContextPath()
			+ "/script/theme/main/blue/images";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/jquery-plugin/easyui/easyloader.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script>
			var defaultyijian = ['已阅。','同意。','通过。','表单已阅。'];
			$(function() {
				if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()){
			        $("html").css("overflowY","scroll");
			    }
			   	
				loadyijian();
				loadWFYiJian();
				var filteractiveids = '${param.filteractiveids_}';
				if (filteractiveids!=null && filteractiveids!=''){
					var filteractiveidss = filteractiveids.split(',');
					for(i=0;i<filteractiveidss.length;i++){
						var x = filteractiveidss[i];
						if (x!=null && x!=''){
							$("input[type=radio][value='"+x+"']").attr('checked',true);
						}
					} 
				}
			});
			
			function inputInfo(){
				var opinionChanage = document.getElementById('opinionChanage');
				document.getElementById('yijian').innerHTML=$("#opinionChanage").find("option:selected").text();
			}
		
		 	function insertyijian(){
		 		var yijian = document.getElementById('yijian').value;
		 		if (yijian==''){
					 Ext.MessageBox.alert('提示','意见不能为空!');
					 return;
		 		}
		 		for(var i=0;i<defaultyijian.length;i++){
		 			if (yijian==defaultyijian[i]){
		 				Ext.MessageBox.alert('提示','已经存在此意见!');
		 				return;
		 			}
		 		}
		 		
				var vUrl = '<c:url value="/common/yijian.do?method=insert"/>';
				Ext.Ajax.request({
			        url: vUrl,
			        form:'form1',
			        // 请求的服务器地址
			        // 指定要提交的表单id
			        method: 'POST',
			        success: function (response, options) {
			            var result = Ext.util.JSON.decode(response.responseText);
			            if (result.error != null) {
			                Ext.MessageBox.alert('提示', result.tip);
			            } else {
			            	loadyijian();
			                Ext.ux.Toast.msg("", result.tip);
			            }
			        },
			        failure: function (response, options) {
			            checkAjaxStatus(response);
			            var result = Ext.util.JSON.decode(response.responseText);
			            Ext.MessageBox.alert('提示', result.tip);
			        }
			    });
		 	}
		 	function loadWFYiJian(){//加载流程意见
		 		var yijian = document.getElementById('yijian');
		 		var workcode = document.getElementById('workcode').value;
		 		var vUrl = '<c:url value="/frame.do?method=onLoadYijian&workcode="/>'+workcode;
				Ext.Ajax.request({
			        url: vUrl,
			        form:'form1',
			        method: 'POST',
			        success: function (response, options) {
			            var result = Ext.util.JSON.decode(response.responseText);
			            if (result.error != null) {
			                Ext.MessageBox.alert('提示', result.tip);
			            } else {
			            	if(result.yinjian!="" && result.yinjian!=null && result.yinjian != 'undefined'){
			            		yijian.innerHTML=result.yinjian;
			            	}
			            }
			        },
			        failure: function (response, options) {
			            checkAjaxStatus(response);
			            var result = Ext.util.JSON.decode(response.responseText);
			            Ext.MessageBox.alert('提示', result.tip);
			        }
			    });
		 	}
		 	function deleteyijian(){
		 		var cuyijian_unid = document.getElementById('opinionChanage').value;
		 		if (cuyijian_unid==''){
					 Ext.MessageBox.alert('提示','默认意见不能删除!');
					 return;
		 		}
		
				var vUrl = '<c:url value="/common/yijian.do?method=delete"/>'+'&unid='+cuyijian_unid;
				Ext.Ajax.request({
			        url: vUrl,
			        // 请求的服务器地址
			        // 指定要提交的表单id
			        method: 'POST',
			        success: function (response, options) {
			            var result = Ext.util.JSON.decode(response.responseText);
			            if (result.error != null) {
			                Ext.MessageBox.alert('提示', result.tip);
			            } else {
			            	loadyijian();
			                Ext.ux.Toast.msg("", result.tip);
			            }
			        },
			        failure: function (response, options) {
			            checkAjaxStatus(response);
			            var result = Ext.util.JSON.decode(response.responseText);
			            Ext.MessageBox.alert('提示', result.tip);
			        }
			    });
		
		 	}
		 	
			function loadyijian(){
			var yijianSelect = document.getElementById('opinionChanage');//选择框架ID
			yijianSelect.options.length=0;
			yijianSelect.options.add(new Option('--- 请选择 ---',''));
			for(var i=0;i<defaultyijian.length;i++){
				yijianSelect.options.add(new Option(defaultyijian[i],''));
			}
		
			 var userid = '';//为空 表示自动以当前登录者ID
			 var vUrl = '<c:url value="/common/yijian.do?method=load"/>'+'&userid='+userid;
			 Ext.Ajax.request({
			 url: vUrl,
			 success: function(response, options){
				 var responseArray = Ext.util.JSON.decode(response.responseText);
				 for(var i=0; i< responseArray.length; i++){
				 yijianSelect.options.add(new Option(responseArray[i].yijian,responseArray[i].unid));
				 }
				 },
				 failure: function (response, options) {
				 checkAjaxStatus(response);
				 }
			 });
			
			}
			var chooseResult = 0;
			function up_4(){
				saveyijian();
				Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        closable:false,
			        msg: '正在跳转请稍候......'
			    });
				var lsh = document.getElementById('lsh').value;
				var workcode = document.getElementById('workcode').value;
				$disabledall();
				//$hideall();
				//$show('audit_1');
				var url = "<%=path%>/frame.do?method=onEditViewMain&commiter=${param.commiter}&naturalname=${param.naturalname}&runtimeid=${param.runtimeid}&lsh=${param.lsh}&workcode="+workcode+"&chooseresult=${param.chooseresult}&operatemode=${param.operatemode}&filteractiveids_=${param.filteractiveids_}";
				window.location.href=url+'&page=audit_1&pagenew=${param.pagenew}';
			}
			
			function saveyijian(){//保存意见
				Ext.Ajax.request({
			        url: "<c:url value='/frame.do?method=onSaveYijian' />",
			        // 请求的服务器地址
			        // 指定要提交的表单id
			        method: 'POST',
			        form:'form1',
			        sync:true,
			        success: function (response, options) {
			            var result = Ext.util.JSON.decode(response.responseText);
			            if (result.error != null) {
			                alert(result.tip);
			                window.open("","_self");window.opener=null;
							window.close();
			            } else {
			                Ext.ux.Toast.msg("", result.tip);
			            }
			        },
			        failure: function (response, options) {
			            $enableall();
			            checkAjaxStatus(response);
			            var result = Ext.util.JSON.decode(response.responseText);
			            Ext.MessageBox.alert('提示', result.tip);
			        }
			    });
			}
			
			function next_4(){
				var yijian = document.getElementById('yijian').value;
				yijian = yijian.trim();
				var filteractiveids= '';
				var split ='';
				var j = 0 ;
		  		$('input[type="radio"]').each(
		        function()   
		        {   
		           	if($(this).attr('checked')){
		           		filteractiveids += split+$(this).val();
					  	split=',';
					  	j++;
		           	}
		        }); 
		  		
		  		$('#filteractiveids').val(filteractiveids);
		  		
		  		if (j<=0){
		  			Ext.MessageBox.alert('提示', '请选择下一处理环节');
		  			return;
		  		}
				if (yijian==''){
					Ext.MessageBox.alert('提示','请填写意见!');
					return;			
				}
		  		
				var msgTip = Ext.MessageBox.show({
					        title: '提示',
					        width: 250,
					        closable:false,
					        msg: '正在执行操作请稍候......'
					    });
				$disabledall();	    
				Ext.Ajax.request({
						        url: "<c:url value='/frame.do?method=onSaveYijian' />",
						        // 请求的服务器地址
						        // 指定要提交的表单id
						        method: 'POST',
						        form:'form1',
						        success: function (response, options) {
						            //msgTip.hide();
						            var result = Ext.util.JSON.decode(response.responseText);
						            if (result.error != null) {
						            	$enableall();
						                alert(result.tip);
						                window.open("","_self");window.opener=null;
										window.close();
						            } else {
						                Ext.ux.Toast.msg("", result.tip);
						                var lsh = document.getElementById('lsh').value;
										var workcode = document.getElementById('workcode').value;
										
										var isend = false;
										$('input[type="radio"]').each(
								        function()   
								        {   
								           	if($(this).attr('checked')){
								           		if ($(this).val()=='trackActionSpecialTypeEND'){
								           			isend = true;
								           		}
								           	}
								        }); 
										
								  		var operatemode = '${param.operatemode}';
								  		if (isend){//选择归档节点 进行归档
								  			if (!confirm('确认要归档?')){$enableall();Ext.MessageBox.hide();return;}
								  			auditEnd();
								  		} else {
								  			if (operatemode=='03'){
								  				if (!confirm('确认要抄阅?')){$enableall();Ext.MessageBox.hide();return;}
								  				auditEnd();
								  			} else if (operatemode=='04'){
								  				if (!confirm('确认要阶段性回复?')){$enableall();Ext.MessageBox.hide();return;}
								  				auditEnd();
								  			} else {
								  				var url ='<%=path%>/frame.do?method=onShowView&commiter=${param.commiter}&naturalname=${param.naturalname}&chooseresult=${param.chooseresult}'+"&lsh="+lsh+"&workcode="+workcode+"&operatemode=${param.operatemode}"+'&filteractiveids='+filteractiveids;
												//$hideall();
												//$show('audit_3_3');
												window.location.href=url+'&page=audit_3_3';
								  			}
								  		}
										
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
			
			function next_4_end(){//归档
				if (!confirm('确认要归档?')){$enableall();Ext.MessageBox.hide();return;}
				var yijian = document.getElementById('yijian').value;
				yijian = yijian.trim();
				if (yijian==''){
					Ext.MessageBox.alert('提示','请填写意见!');
					return;			
				}
				document.getElementById('operatemode').value='end';
				
				var msgTip = Ext.MessageBox.show({
					        title: '提示',
					        width: 250,
					        closable:false,
					        msg: '正在执行操作请稍候......'
				});
				$disabledall();
				Ext.Ajax.request({
						        url: "<c:url value='/frame.do?method=onSaveYijian' />",
						        // 请求的服务器地址
						        // 指定要提交的表单id
						        method: 'POST',
						        form:'form1',
						        success: function (response, options) {
						            msgTip.hide();
						            var result = Ext.util.JSON.decode(response.responseText);
						            if (result.error != null) {
						            	$enableall();
						                alert(result.tip);
						                window.open("","_self");window.opener=null;
										window.close();
						            } else {
						                Ext.ux.Toast.msg("", result.tip);
						                var lsh = document.getElementById('lsh').value;
										var workcode = document.getElementById('workcode').value;
										auditEnd();
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
			
			function auditEnd(){//抄阅 归档
				var notice = 1;//不发送
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
			
			function _refreshOpenerWin() {
				if (opener && opener.location) {
					if (opener.document.getElementById('openerWinIdx')){
						opener.$delnode(document.getElementById('lsh').value);
					}
			    }
			}
		</script>			
		<!-- 样式文件 -->	
		${linkcss}	
		<title><c:choose><c:when test="${!empty htmltitleinfo}">${htmltitleinfo} - ${activityName}</c:when><c:otherwise>${activityName}</c:otherwise></c:choose></title>
	</head>
	<body>
		<center>
		<jsp:include page="template.jsp"></jsp:include>
		<div align="center" style="width:1000px;">
			<div>
				<table width="100%" >
					<tr>
						<td width="12px" align="left">
							<div id="top_nd-header" style="background: url(<%=cssURL%>/first_top.gif) ;background-repeat: no-repeat;height: 11px;width: 12px;">
							</div>
						</td>
						<td id="steps_nd" align="left">
							步骤:第 <span id="stepNum">2</span> 步,共 3 步。 
							&nbsp;<span style="font-size: 16px;font-weight: bold;color: #386BA4;">${htmltitleinfo}</span>
						</td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td width="28px" align="left">
							<div id="top_nd-title"></div>
						</td>
						<c:choose>
						<c:when test="${param.chooseresult==3}">
						<td id="title_nd" align="left">转办<span id="helpInfo_nd">帮助提示: 请选择转办节点,然后填写意见, 确认无误后请点击下一步按钮。</span>
						</td>
						</c:when>
						<c:when test="${param.chooseresult==1}">
						<td id="title_nd" align="left">退办/特送<span id="helpInfo_nd">帮助提示: 请选择退办/特送节点,然后填写意见, 确认无误请点击下一步按钮。</span>
						</td>
						</c:when>
						<c:otherwise>
						<td id="title_nd" align="left">审核<span id="helpInfo_nd">帮助提示: 请选择下一个环节的流程节点,然后填写意见, 确认无误请点击下一步按钮。</span>
						</td>
						</c:otherwise>
						</c:choose>
					</tr>
				</table>
				<hr id="hr_nd">
			</div>
				<form id="form1" style="text-align: left">
					<center>
					<div id="processcontentbox_" align="left" >
						<input type="hidden" id="lsh" name="lsh"
							value="${param.lsh}">
						<input type="hidden" id="workcode" name="workcode"
							value="${param.workcode}">
						<input type="hidden" id="operatemode" name="operatemode"
							value="${param.operatemode}">
						<input type="hidden" id="filteractiveids" name="filteractiveids"
							value="">	
							<table align="left">
								<tr>
								<td valign="top">
									<div id="contenttitle" align="left">
										<table width="100%">
											<tr>
												<td width="13px">
													<div id="top_nd_title_2">
													</div>
												</td>
												<td id="_audit">
													处理节点(请选择)
												</td>
											</tr>
										</table>
									</div>
									<div id="chooseapproval" >
										<c:if test="${param.operatemode!='03'}">
											<c:if test="${isAndBranchMode=='true'}">
												<c:forEach var="processList" items="${processList}" varStatus="step">
												<div>
												<span id="approvalitem">
												<input id="specialCondition" type="radio" value="${processList.activeids}" checked="checked" />${processList.name}</span>
												</div>
												</c:forEach>
											</c:if>
											<c:if test="${isAndBranchMode=='false'}">
												<c:forEach var="processList" items="${processList}" varStatus="step">
												<div>
												<span id="approvalitem">
												<input id="specialCondition" name="specialCondition" type="radio" value="${processList.activeids}"
												  />${processList.name}</span>
												</div>
												</c:forEach>
												 <c:if test="${fn:length(processList)==1}">
												 <!-- 一个节点默认选择 --> 
												 <script>
												$("input[type=radio]").attr('checked',true);
												</script>
												</c:if> 
											</c:if>
										</c:if>
										<c:if test="${param.operatemode=='03'}">
										<div>
												<span id="approvalitem">
												<input id="specialCondition" name="specialCondition" type="radio" checked="checked" />抄阅</span>
												</div>
										</c:if>
									</div>
								</td>
								<td>
									<div style="padding:20px; vertical-align:middle; border-left:1px dashed #E3E3E3;background:none;font-size:0px; height: 200px;;"></div> 
								</td>
								<td>
								<div id="contenttitle">
									<table>
										<tr>
											<td width="13px">
												<div id="top_nd_title_2">
												</div>
											</td>
											<td id="audit_2_yijian">
												填写意见
											</td>
										</tr>
									</table>
								</div>
								<div id="opinionbox">
									<span>&nbsp;&nbsp;意见选择：</span>
									<span> <select name="opinionChanage" id="opinionChanage"
											onchange="inputInfo();" style="width: 350px;">
		
										</select> </span>
									
								</div>
								<div id="opiniontext">
									<textarea rows="10" style="width:100%;" id="yijian" name="yijian"></textarea>
								</div>
								<div id="addOptionByMe">
									<input type="button"
											value="删除个人意见" onclick="deleteyijian()" class="nd_btn_yj_3" />
									<input type="button" value="加为我的常用意见" onclick="insertyijian()"
											class="nd_btn_yj_2" /> 
								</div>
								</td>
							</tr>
						</table>
					</div>
					</center>
				</form>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
		</center>
	</body>
</html>
