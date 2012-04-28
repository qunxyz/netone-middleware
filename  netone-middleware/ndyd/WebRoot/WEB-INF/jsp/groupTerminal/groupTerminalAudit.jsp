﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/jquery-plugin/easyui/easyloader.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script>
			$(function() {
					$( "#tabs" ).tabs();
			});
			function inputInfo(){
				var opinionChanage = document.getElementById('opinionChanage');
				document.getElementById('yijian').innerHTML=$("#opinionChanage").find("option:selected").text();
			}
		</script>
		<title>集团终端</title>
	</head>
	<body>
		<div>
			<div>
				<table width="100%" >
					<tr>
						<td width="12px">
							<div id="top_nd-header">
							</div>
						</td>
						<td id="steps_nd">
							步骤:第 <span id="stepNum">2</span> 步,共 3 步。
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
							审核
							<span id="helpInfo_nd">帮助提示: 这里主要是确认审核表单方式,请选择处理方式,填写意见, 确认无误请点击下一步,如需修改请点上一步,否取消。</span>
						</td>
					</tr>
				</table>
				<hr id="hr_nd">
			</div>
			<center>
				<form id="form1">
					<div id="processcontentbox_" style="padding-left: 35px;"
						align="left">
						<input type="hidden" id="groupTerminalId" name="groupTerminalId"
							value="${param.groupTerminalId}">
						<input type="hidden" id="workcode" name="workcode"
							value="${param.workcode}">
						<div id="contenttitle">
							<table width="100%">
								<tr>
									<td width="13px">
										<div id="top_nd_title_2">
										</div>
									</td>
									<td id="_audit">
										处理方式(请选择)
									</td>
								</tr>
							</table>
						</div>
						<div id="chooseapproval">
							<c:if test="${param.operatemode=='03'}">
							<span id="approvalitem">
								<input type="radio" id="specialCondition" name="specialCondition" value="0" onchange="chooseResult=0" <c:if test="${param.chooseresult==0||param.chooseresult==''||param.chooseresult==null}">checked="checked"</c:if> />抄阅</span>
							</c:if>
							<c:if test="${param.operatemode!='03'}">	
							<span id="approvalitem">
								<input type="radio" id="specialCondition" name="specialCondition" value="0" onchange="chooseResult=0" <c:if test="${param.chooseresult==0||param.chooseresult==''||param.chooseresult==null}">checked="checked"</c:if> />通过</span>
							<span id="approvalitem">
								<input type="radio" id="specialCondition" name="specialCondition" value="1" onchange="chooseResult=1" <c:if test="${param.chooseresult==1}">checked="checked"</c:if> />退办</span>
							<span id="approvalitem">
								<input type="radio" id="specialCondition" name="specialCondition" value="2" onchange="chooseResult=2" <c:if test="${param.chooseresult==2}">checked="checked"</c:if> />特送</span>
							</c:if>	
						</div>
						<div id="contenttitle">
							<table>
								<tr>
									<td width="13px">
										<div id="top_nd_title_2">
										</div>
									</td>
									<td id="audit_2">
										填写意见
									</td>
								</tr>
							</table>
						</div>
						<div id="opinionbox">
							<span>&nbsp;&nbsp;意见选择：</span>
							<span> <select name="opinionChanage" id="opinionChanage"
									onchange="inputInfo();" style="width: 250px;">

								</select> </span>
							<span id="addOptionByMe"> <input type="button"
									value="删除个人意见" onclick="deleteyijian()" class="nd_btn_yj_1" />
								<input type="button" value="加为我的常用意见" onclick="insertyijian()"
									class="nd_btn_yj_2" /> </span>
						</div>
						<div id="opiniontext">
							<textarea rows="8" style="width: 80%;" id="yijian" name="yijian"></textarea>
						</div>
					</div>
				</form>
			</center>
		</div>
	</body>
</html>
<script>
	Ext.onReady(function(){
		loadyijian();
		loadWFYiJian();
 	});
 	function insertyijian(){
 		var yijian = document.getElementById('yijian').value;
 		if (yijian==''){
			 alert('意见不能为空!');
			 return;
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
 		var vUrl = '<c:url value="/groupTerminal/groupTerminal.do?method=onLoadYijian&workcode="/>'+workcode;
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
			 alert('默认意见不能删除!');
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
	yijianSelect.options.add(new Option('已阅。',''));
	yijianSelect.options.add(new Option('同意。',''));
	yijianSelect.options.add(new Option('通过。',''));
	yijianSelect.options.add(new Option('表单已阅。',''));

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

	function up_4(){
		var groupTerminalId = document.getElementById('groupTerminalId').value;
		var workcode = document.getElementById('workcode').value;
		var url = "<%=path%>/groupTerminal/groupTerminal.do?method=onEditView&groupTerminalId="+groupTerminalId+"&workcode="+workcode;
		parent.document.getElementById('flowMainFrame').src=url;
		parent.$disabledall();
		parent.$hideall();
		parent.$show('audit_1');
	}
	
	var chooseResult = 0;
	function next_4(){
		var  obj  = document.getElementsByName('specialCondition');   
		for(i=0;i<obj.length;i++){   
			if(obj[i].checked){   
			  	chooseResult = obj[i].value;
			}   
  		}   
		var yijian = document.getElementById('yijian').value;
		yijian = yijian.trim();
		if (yijian==''){
			alert('请填写意见!');
			return;			
		}
		var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
		Ext.Ajax.request({
				        url: "<c:url value='/groupTerminal/groupTerminal.do?method=onSaveYijian' />",
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
				                Ext.ux.Toast.msg("", result.tip);
				                var groupTerminalId = document.getElementById('groupTerminalId').value;
								var workcode = document.getElementById('workcode').value;
								parent.$disabledall();
								var url ='<%=path%>/groupTerminal/groupTerminal.do?method=onShowView&chooseresult='+ chooseResult+"&groupTerminalId="+groupTerminalId+"&workcode="+workcode+"&operatemode=${param.operatemode}";
								parent.document.getElementById('flowMainFrame').src=url;
								parent.$hideall();
								parent.$show('audit_3_3');
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