<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
String cssURL = request.getContextPath()
+ "/script/theme/main/blue/images";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
		<link rel="stylesheet" type="text/css" href="default.css">
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/jquery-plugin/easyui/easyloader.js"></script>
		<style type="text/css">
		.whitefont,.whitefont a:hover,.whitefont a:visited,.whitefont a:link{color:#FFF; font:10px "宋体";}
body{font-size:9pt;margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;color:#3E3C20;height:100%}
A{color: #006699; text-decoration: none;font-size:9pt;} 
A:hover { color: #D95F40; text-decoration: none }
A:active {color: #928BA4; text-decoration: none}
A:visited {color: #928BA4;} 
p{text-indent: 2em;}
form{padding: 0px 0px 0px 0px;margin: 0px 0px 0px 0px;}
input{font-size:9pt;}
select{font-size:9pt}
table{font-size:9pt;}
div{font-size:9pt;}
td{font-size:9pt;}
span{font-size:9pt;}
nobr{font-size:9pt;}

html.VIE7{font-family:微软雅黑}

html.VIE7 A{color: #006699;text-decoration: none;cursor:hand;font-size:12pt;font-family:微软雅黑} 
html.VIE7 A:hover {color: #D95F40; text-decoration: none}
html.VIE7 A:active {color: #928BA4; text-decoration: none}
html.VIE7 A:visited {color: #928BA4;} 
html.VIE7 input{font-size:9pt;font-family:微软雅黑;padding:0px;}
html.VIE7 textarea{font-size:9pt;font-family:微软雅黑;padding:0px}
html.VIE7 select{font-size:9pt;font-family:微软雅黑;padding:0px}
html.VIE7 table{font-size:9pt;font-family:微软雅黑}
html.VIE7 div{font-size:9pt;font-family:微软雅黑}
html.VIE7 td{font-size:9pt;font-family:微软雅黑}
html.VIE7 span{font-size:9pt;font-family:微软雅黑}
html.VIE7 nobr{font-size:9pt;font-family:微软雅黑}

.VIE7td,.IE7td,.FFtd,#VIE7td,#IE7td,#FFtd{background-attachment:fixed;background-position: top center;background-repeat:repeat-x}


/**表单*/
.form_td{
	line-height:25px;height:30px;border-bottom:1px dashed #ddd;
}
.form_tr{
}

/** 工单标题表格TR */
.table_tr_title
{

}
/** 工单标题表格TD */
.table_td_title{
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType =   0, StartColorStr =   #D9ECF9, EndColorStr =#62BBE8);
	background: -webkit-gradient(linear, left top, left bottom, from(#D9ECF9), to(#62BBE8)); /* for webkit browsers */
	background: -moz-linear-gradient(top,  #D9ECF9,  #62BBE8); /* for firefox 3.6+ */
	color: #000066;
	text-align: left;
	padding-left: 10px;
	padding-right: 10px;
	font-style: normal;
	border: 1px solid #86B4E5;
	line-height: 20px;
}
/** 工单字段表格TR */
.table_tr_header{

}
/** 工单字段表格TD */
.table_td_header{
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType =   0, StartColorStr =   #FEF9FF, EndColorStr =#DBDCDE);
	background: -webkit-gradient(linear, left top, left bottom, from(#FEF9FF), to(#DBDCDE)); /* for webkit browsers */
	background: -moz-linear-gradient(top,  #FEF9FF,  #DBDCDE); /* for firefox 3.6+ */
	color: #000000;
	text-align: center;
	padding-left: 10px;
	padding-right: 10px;
	font-style: normal;
	border: 1px solid #D9DBDC;
	line-height: 20px;
}

/** 工单内容表格TD */
.table_tr_content{

}
/** 工单内容表格TD */
.table_td_content{
	line-height:25px;border:1px dashed #ddd;
}

.form_fieldcontent
{
	float:left;
	overflow:hidden;
	padding:2px 0px 0px 0px;
	margin:0px 0px 0px 0px;
}

html.VIE7 .form_fieldcontent{
	float:left;
	overflow:hidden;
	padding:2px 0px 0px 0px;
	margin:0px 0px 0px 0px;
}

.form_fieldinput
{
	float:left;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
}

html.VIE7 .form_fieldinput
{
	float:left;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
}

.form_fieldinput_read
{
	float:left;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
	border-bottom:1px solid #ddd;
}
html.VIE7 .form_fieldinput_read
{
	float:left;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
	border-bottom:1px solid #ddd;
}


.form_fieldcontent
{
	float:left;
	overflow:hidden;
	padding:2px 0px 0px 0px;
	margin:0px 0px 0px 0px;
}

html.VIE7 .form_fieldcontent{
	float:left;
	overflow:hidden;
	padding:2px 0px 0px 0px;
	margin:0px 0px 0px 0px;
}

.form_fieldcaption
{
	width:80px;	
	height:22px;
	font:12px "宋体";
	color:#001a9b;
	float:left;
	padding:4px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
	border-collapse:collapse;
	background-attachment:fixed;
	background-position: top center;
	background-repeat:repeat-x;	
}

.form_fieldcaption2
{
	height:22px;
	font:12px "宋体";
	float:left;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType =   0, StartColorStr =   #D9ECF9, EndColorStr =#62BBE8);
	background: -webkit-gradient(linear, left top, left bottom, from(#D9ECF9), to(#62BBE8)); /* for webkit browsers */
	background: -moz-linear-gradient(top,  #D9ECF9,  #62BBE8); /* for firefox 3.6+ */
	color: #000066;
	text-align: left;
	font-style: normal;
	border: 1px solid #86B4E5;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
}

.form_fieldinput
{
	float:left;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
}
html.VIE7 .form_fieldinput
{
	float:left;
	padding:0px 0px 0px 0px;
	margin:0px 0px 0px 0px;
	overflow:hidden;
}


/** */
#top_nd-header {
	background: url(<%=path%>/script/theme/main/blue/images/first_top.gif) ;
	background-repeat: no-repeat;
	height: 11px;
	width: 12px;
	vertical-align:middle; 
}
#steps_nd{
	color: #386BA4;
	margin: 7px;
	padding-top: 10px;
	padding-bottom: 10px;
	font-size: 12px;
	vertical-align:middle; 
}
#top_nd-title {
	background: url(<%=path%>/script/theme/main/blue/images/title.gif) ;
	background-repeat: no-repeat;
	width: 28px;
	height: 11px;
}
#helpInfo_nd{
	color: #386BA4;
	margin: 5px;
	padding:5px;
	font-size: 12px;
	padding-left: 30px;
	font-weight: normal;
}
#title_nd{
	color: #386BA4; font-size: 16px;
	font-weight: bold;
}
#hr_nd{
	height: 1px; border: none;
	border-top: 1px dashed #E3E3E3;
}
		
		</style>	
		<script>
			$(function() {
			
			});
			function inputInfo(){
				var opinionChanage = document.getElementById('opinionChanage');
				document.getElementById('yijian').innerHTML=$("#opinionChanage").find("option:selected").text();
			}
		</script>
		<title>效能督办</title>
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
						</td>
					</tr>
				</table>
				<table width="100%">
					<tr>
						<td width="28px" align="left">
							<div id="top_nd-title"></div>
						</td>
						<td id="title_nd" align="left">审核<span id="helpInfo_nd">帮助提示: 请选择下一个环节的流程节点,然后填写意见, 确认无误请点击下一步按钮。</span>
						</td>
					</tr>
				</table>
				<hr id="hr_nd">
			</div>
				<form id="form1" style="text-align: left">
					<center>
					<div id="processcontentbox_" align="left" >
						<input type="hidden" id="unid" name="unid"
						value="${param.unid}">
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
										<span id="approvalitem" <c:if test="${ispack==true}"> style="display: none" </c:if> <c:if test="${isend==true}"> style="display: none" </c:if> ><input type="radio"
												id="specialCondition" name="specialCondition" value="1"
												onchange="chooseResult=1" <c:if test="${param.chooseresult==1}">checked="checked"</c:if>/>交办</span>
										<span id="approvalitem" <c:if test="${ispack==true}"> style="display: none" </c:if>><input type="radio"
												id="specialCondition" name="specialCondition" value="2"
												onchange="chooseResult=2" 
												<c:if test="${param.chooseresult==2||param.chooseresult==''||param.chooseresult==null}">checked="checked"</c:if>
												<c:if test="${isend==true}">checked="checked"</c:if>
												/>办理完毕</span>
										<c:if test="${ispack==true}">
										<span id="approvalitem"><input type="radio"
												id="specialCondition" name="specialCondition" value="3"
												onchange="chooseResult=3" checked="checked"
												/>归档</span>
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
<script>

	var defaultyijian = ['已阅。','同意。','通过。','表单已阅。'];
	$(function() {
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()){
	        $("html").css("overflowY","scroll");
	    }
	   	
		loadyijian();
		loadWFYiJian();
	});

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
 		var vUrl = '<c:url value="/censorship/censorship.do?method=onLoadYijian"/>';
		Ext.Ajax.request({
	        url: vUrl,
	        form:'form1',
	        method: 'POST',
	        success: function (response, options) {
	            var result = Ext.util.JSON.decode(response.responseText);
	            if (result.error != null) {
	                Ext.MessageBox.alert('提示', result.tip);
	            } else {
	            	if(result.yijian!="" && result.yijian!=null && result.yijian != 'undefined'){
	            		yijian.innerHTML=result.yijian;
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

	function up_4(){
		var unid = document.getElementById('unid').value;
		var url = "<%=path%>/censorship/censorship.do?method=onEditX&unid="+unid;
		$disabledall();
		$hideall();
		$show('audit_1');
		window.location.href=url+'&page=audit_1&t=${param.t}';
	}
	
	var chooseResult = 2;
	<c:if test="${ispack==true}">
	chooseResult = 3;
	</c:if>
	
	function next_4(){
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
				        url: "<c:url value='/censorship/censorship.do?method=onSaveyijian' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        params: {
			            	unid : document.getElementById('unid').value,
			            	yijian : document.getElementById('yijian').value
			        	},
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				                Ext.ux.Toast.msg("", result.tip);
				                var unid = document.getElementById('unid').value;
				                
				                if (chooseResult==2){
				               		if (!confirm('确认要办理完毕?')){$enableall();Ext.MessageBox.hide();return;}
								  	auditEnd2();
				                } else if (chooseResult==3){
				                	if (!confirm('确认要归档?')){$enableall();Ext.MessageBox.hide();return;}
								  	auditEnd3();
				                } else {
					                $disabledall();
									var url ='<%=path%>/censorship/censorship.do?method=onShowView&t=${param.t}&chooseresult='+ chooseResult+"&unid="+unid;
									$hideall();
									$show('audit_3_3');
									window.location.href=url+'&page=audit_3_3';
				                }
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
	
	function auditEnd2(){//办理完毕
		var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        msg: '正在执行操作请稍后......'
	    });
		Ext.Ajax.request({
		        url: "<c:url value='/censorship/censorship.do?method=onAudit' />",
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
		            	$disabledall();
		            	alert(result.tip);
		            	window.opener=null;
		            	window.close();
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
	
	function auditEnd3(){//归档
		var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        msg: '正在执行操作请稍后......'
	    });
		Ext.Ajax.request({
		        url: "<c:url value='/censorship/censorship.do?method=onPack' />",
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
		            	alert(result.tip);
		                _refreshOpenerWin();
		            	$disabledall();
		            	window.opener=null;
		            	window.close();
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
	
	function _refreshOpenerWin() {
		if (opener && opener.location) {
			if (opener.document.getElementById('openerWinIdFDL')){
				opener.refresh();
			}
			if (opener.document.getElementById('openerWinIdx')){
	    }
	}
</script>