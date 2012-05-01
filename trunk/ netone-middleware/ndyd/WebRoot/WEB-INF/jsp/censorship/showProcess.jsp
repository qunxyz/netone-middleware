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
	<link rel="stylesheet" type="text/css" href="default.css">
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
	
	<script type="text/javascript">
	
		function up_Next_1(){
			$disabledall();
			var unid = document.getElementById('unid').value;
			var url = "<%=path%>/censorship/censorship.do?method=onEditX&unid="+unid;
			$hideall();
			$show('new_1');
			window.location.href=url+'&page=new_1&t=${param.t}';
		}
	
		function init(){
		
		}
		
		function up_5(){
			var unid = document.getElementById('unid').value;
			var chooseresult = document.getElementById('chooseresult').value;
			var url ="<%=path%>/censorship/censorship.do?method=onAuditView&chooseresult="+ chooseresult+"&unid="+unid;
			$hideall();
			$show('audit_2');
			$disabledall();
			window.location.href=url+'&page=audit_2&t=${param.t}';
		}
		
		function end(){
			<c:choose>
				<c:when test="${param.chooseresult==0}">
					auditEnd0();
				</c:when>
				<c:when test="${param.chooseresult==1}">
					auditEnd1();
				</c:when>
				<c:when test="${param.chooseresult==2}">
					auditEnd2();
				</c:when>
				<c:when test="${param.chooseresult==3}">
					auditEnd3();
				</c:when>
				<c:otherwise>
					auditEnd0();
				</c:otherwise>
			</c:choose>
		}
		function chooseBoxNotice(){
			var notice = 0;
		  	 if($("#notice").attr("checked")==true){
		  	 	notice = document.getElementById('notice').value;
		  	 }
		  	 return notice;
		}
		function auditEnd0(){//
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
				        url: "<c:url value='/censorship/censorship.do?method=onAuditNext' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        params: {
			            	perunid: document.getElementById('usercode').value,
			            	tname : document.getElementById('username').value,
			            	unid : document.getElementById('unid').value,
			            	parentunid: document.getElementById('unid').value,
			            	notice:chooseBoxNotice()
			        	},
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
		
		function auditEnd1(){//交办
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
				        url: "<c:url value='/censorship/censorship.do?method=onAssign' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        params: {
			            	perunid: document.getElementById('usercode').value,
			            	tname : document.getElementById('username').value,
			            	unid : document.getElementById('unid').value,
			            	parentunid: document.getElementById('unid').value,
			            	notice:chooseBoxNotice()
			        	},
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
						opener.$delnode(document.getElementById('unid').value);
					}
			    }
			}
		
	</script>


  </head>
  
  <body>
  <form id="form1" >
		<input type="hidden" id="userid" name="userid"/>
		<input type="hidden" id="usercode" name="usercode" />
		<input type="hidden" id="username" name="username" />
		
		<input type="hidden" id="perunid" name="perunid" />
		<input type="hidden" id="tname" name="tname" />
		<input type="hidden" id="unid" name="unid" value="${param.unid}" />
	</form>
  	<center>
	<jsp:include page="template.jsp"></jsp:include>
    <div id="box" style="width:1000px;">
    	<div>
    		<table width="100%">
				<tr>
					<td width="12px" align="left">
						<img src="<%=path%>/script/theme/main/blue/images/first_top.gif"/>
					</td>
					<td id="steps_nd" align="left">
						<c:choose>
							<c:when test="${param.flowppage==2}">
							步骤:第 2 步,共 2 步。
							</c:when>
							<c:otherwise>步骤:第 3 步,共 3 步。</c:otherwise>
						</c:choose>
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
		<div>	
    	<center>
			<div id="processcontentbox">
				<input type="hidden" id="unid" name="unid"
						value="${param.unid}">
				<input type="hidden" id="chooseresult" name="chooseresult"
						value="${param.chooseresult}">
				<div id="processcontent">
					<iframe id="personframe" width="800" height="370" src="<c:url value='/department/user.do?method=onMultiSelectUserX&hiddendept=0&includedept=0&singleselect=0&node=${node}'></c:url>"/>
				</div>
			</div>
		</center>
		<div id="noticeBox" align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="notice" id="notice" type="checkbox" value="1" />流转状态变化时不发送短信
		</div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
	</center>
  </body>
</html>
