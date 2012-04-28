<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
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
		<script language="javascript" type="text/javascript" src="<%=path%>/script/jquery-plugin/easyui/easyloader.js" ></script>
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

/** 公单标题表格TR */
.table_tr_title
{

}
/** 公单标题表格TD */
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
/** 公单字段表格TR */
.table_tr_header{

}
/** 公单字段表格TD */
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

/** 公单内容表格TD */
.table_tr_content{

}
/** 公单内容表格TD */
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
	width:100px;	
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
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script>
			$(function() {
				$("#tabs").tabs();
				$('#tabs').tabs('select', "tabs-2");
			});
		</script>
		
		<title>效能督办</title>
	</head>
	<body>
		<center>
		<jsp:include page="template.jsp"></jsp:include>
		<div align="center" style="width:1000px;">
			<table width="100%">
				<tr>
					<td width="12px" align="left">
						<div id="top_nd-header">
						</div>
					</td>
					<td id="steps_nd" align="left">
						<c:choose>
							<c:when test="${!empty param.unid}">
								步骤:第 1 步,共 3 步。
							</c:when>
							<c:otherwise>
								步骤:第 1 步,共 2 步。
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
			<div>
			<table width="100%">
				<tr>
					<td width="28px">
						<div id="top_nd-title">
						</div>
					</td>
					<td  id="title_nd" align="left">
						<c:choose>
							<c:when test="${!empty param.unid}">
								审核
								<span id="helpInfo_nd">帮助提示: 请认真审核表单,确认无误后点击下一步按钮。</span>
								
							</c:when>
							<c:otherwise>
								新建
								<span id="helpInfo_nd">帮助提示: 请填写表单,确认无误后请点击下一步按钮,若要作废,请点击作废按钮。</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
			<hr id="hr_nd">
		</div>
		<div id="tabs" style="height: 100%;">
			<ul>
				<li>
					<a href="#tabs-1">流程跟踪</a>
				</li>
				<li>
					<a href="#tabs-2" class="ui-tabs-selected">流程办理</a>
				</li>
			</ul>
			<div id="tabs-1">
				<div id="framecontent"></div>
			</div>

			<div id="tabs-2">
			<form id="censorshipFrom" action="" method="post">
				<input type="hidden" id="unid" name="unid" value="${CensorShip.unid}" />
				<FONT face="宋体">
						<TABLE class="tablestyle" id="Table_Info" cellSpacing="0"
							cellPadding="0" width="98%" border="0">
							<TR>
								<TD align="right" class="form_td">
									<div class="form_fieldcontent">
									<div class="form_fieldcaption">
										督办标题<font color="red">*</font>:
									</div>
									<div class="form_fieldinput">
									<input name="subject" id="subject" type="text"
										style="WIDTH: 100%; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc;"
										<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
										value="${CensorShip.subject}"  <c:if test="${!empty CensorShip.unid && param.page!='new_1'}"> readonly="readonly" </c:if> />
									</div>
									</div>
								</TD>
							</TR>
							<TR>
								<TD align="right" class="form_td">
									<div class="form_fieldcontent">
									<div class="form_fieldcaption">
										发起人<font color="red">*</font>:
									</div>
									<div class="form_fieldinput">
										<input name="handler" id="handler" type="text" readonly="readonly"
										<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
										style="WIDTH:300px; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc; "
										value="${CensorShip.handler}" <c:if test="${!empty CensorShip.unid}"> readonly="readonly" </c:if> />
									</div>
									
									<div class="form_fieldcaption">
										发起部门<font color="red">*</font>:
									</div>
									<div class="form_fieldinput">
									<input name="chargedept" id="chargedept" type="text" readonly="readonly"
										<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
										style="WIDTH: 300px; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc; "
										value="${CensorShip.chargedept}" <c:if test="${!empty CensorShip.unid && param.page!='new_1'}"> readonly="readonly" </c:if> />
									</div>
									
									</div>
								</TD>
							</TR>
							<TR>
								<TD align="right" class="form_td">
									<div class="form_fieldcontent">
									<div class="form_fieldcaption">督办发起时间:</div>
									<div class="form_fieldinput">
									<input type="text"
										<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
										style="WIDTH:300px; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc; "
										id="newtime" name="newtime" readonly="readonly"
										value="<fmt:formatDate value='${CensorShip.newtime}' pattern='yyyy-MM-dd HH:mm:ss' />" />
									</div>
									
									<div class="form_fieldcaption">督办来源:</div>
									<div class="form_fieldinput">
									<select style="WIDTH:300px; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc; " id="frome" name="frome" <c:if test="${!empty CensorShip.unid && param.page!='new_1'}"> disabled="disabled" readonly="readonly" </c:if> ></select>
									</div>
									
									</div>
								</TD>
							</TR>
							
							<TR>
								<TD align="right" class="form_td">
									<div class="form_fieldcontent">
									<div class="form_fieldcaption">
										要求完成时间<font color="red">*</font>:
									</div>
									<div class="form_fieldinput">
										<input name="duetime" id="duetime" type="text"  class="Wdate"
										<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
										style="WIDTH: 300px; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc;"
										value="<fmt:formatDate value='${CensorShip.duetime}' pattern='yyyy-MM-dd' />"
										<c:if test="${empty CensorShip.unid || param.page=='new_1'}">onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"</c:if> <c:if test="${!empty CensorShip.unid && param.page!='new_1'}"> readonly="readonly" </c:if> />
									</div>
									
									<div class="form_fieldcaption">
										完成时间:
									</div>
									<div class="form_fieldinput">
									<input readonly="readonly" type="text" 
										<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
										style="WIDTH: 300px; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc; color: #999999;"
										id="donetime" name="donetime" value="<fmt:formatDate value='${CensorShip.donetime}' pattern='yyyy-MM-dd HH:mm:ss' />" />
									</div>
									
									</div>
								</TD>
							</TR>
							
							<TR>
								<TD align="right" class="form_td">
									<div class="form_fieldcontent">
									
									<div class="form_fieldcaption">办理部门:</div>
									<div class="form_fieldinput" >
									<input d="transdept" name="transdept" readonly="readonly"
										<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
										value="${CensorShip.transdept}" type="text"
										style="WIDTH: 100%; COLOR: #000000; border: 0px; border-bottom: 1px solid #cccccc; " />
									</div>
									
									</div>
								</TD>
							</TR>
							
							<TR>
								<TD class="table_td_title" align="right">
									<div class="form_fieldcontent">
										&nbsp;&nbsp;说明:
									</div>
								</TD>
							</TR>
							<TR>
								<TD >
									<textarea name="memo" id="memo" <c:if test="${!empty CensorShip.unid && param.page!='new_1'}"> readonly="readonly" </c:if>
									<c:if test="${param.readonly==true}"> disabled="disabled" </c:if>
									style="width: 99%; height: 130px; TEXT-ALIGN: left;overflow:hidden;"
									rows="2" cols="20">${CensorShip.memo}</textarea>
								</TD>
							</TR>
							<TR>
								<TD colSpan="4" style="background-color: #ffffff;">
									<iframe id="fileMainFrame" name="fileMainFrame" 
										src="<c:url value='/file.do?method=onMainView&d_unid=${CensorShip.unid}&readonly=${param.readonly}' />"
										scrolling="auto" frameborder="0"
										style="width: 100%;">
									</iframe>
								</TD>
							</TR>
						</TABLE> </FONT>
					</div>
				</div>
				
				<!--  -->
								
			</div>
		</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
		</center>
	</body>
</html>
<script>
/*
Auto-growing textareas; technique ripped from Facebook
(Textarea need set style "overflow:hidden" under IE)
*/
(function($) {
function times(string, number) {
  for (var i = 0, r = ''; i < number; i ++) r += string;
  return r;
}

$.fn.autogrow = function(options) {
  this.filter('textarea').each(function() {
    this.timeoutId = null;
    var $this = $(this), minHeight = $this.height();
    var shadow = $('<div></div>').css({
      position:   'absolute',
      wordWrap:   'break-word',
      top:        0,
      left:       -9999,
      display:    'none',
      width:      $this.width(),
      fontSize:   $this.css('fontSize'),
      fontFamily: $this.css('fontFamily'),
      lineHeight: $this.css('lineHeight')
    }).appendTo(document.body);

    var update = function() {
      var val = this.value.replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/&/g, '&amp;')
        .replace(/\n$/, '<br/>&nbsp;')
        .replace(/\n/g, '<br/>')
        .replace(/ {2,}/g, function(space) { return times('&nbsp;', space.length -1) + ' ' });
      shadow.html(val);
      $(this).css('height', Math.max(shadow.height(), minHeight));
    }
    
    var updateTimeout = function() {
      clearTimeout(this.timeoutId);
      var that = this;
      this.timeoutId = setTimeout(function(){ update.apply(that); }, 100);
    };

    $(this).change(update).keyup(updateTimeout).keydown(updateTimeout);
    update.apply(this);
  });
  return this;
}
})(jQuery);



$(function(){
	refreshFrame();
	easyloader.base = '<%=path%>/script/jquery-plugin/easyui/';    // set the easyui base directory
	easyloader.locale = 'zh_CN';
	$("textarea#memo").autogrow();
});

function refreshFrame(){
	var id = document.getElementById('unid').value;
	var url = "<c:url value='/censorship/censorship.do?method=onLoadLog' />"+"&unid="+id;
	Ext.Ajax.request({
		url:url,//请求的服务器地址
		method:'POST',
		success : function(response,options){
			//alert(response.responseText)
			document.getElementById('framecontent').innerHTML=response.responseText;
		},
		failure : function(response,options){
			msgTip.hide();
			checkAjaxStatus(response);
		}
	});
	
	//document.getElementById('framecontent').src=url+'&unid='+id+"&r="+(Math.floor(Math.random()*999999+1));
}

function loadFromeType(){//加载督办来源
     var fromeType = '${CensorShip.frome}';
	 var vUrl = '<c:url value="/common/systemConfig.do?method=onGetSystemConfig"/>'+'&type=dubangfrome';
		Ext.Ajax.request({
		   url:  vUrl,
		   success: function(response, options){
				  var responseArray = Ext.util.JSON.decode(response.responseText);     
				  var fromeSelect = document.getElementById('frome');
				  for(var i=0; i< responseArray.length; i++){
				     var option = document.createElement('option');
					 option.text = responseArray[i].name ;
	                 option.value = responseArray[i].sid;
					 if((typeof(fromeType) != 'undefined') && (fromeType.trim() == option.value.trim())){
			     		option.setAttribute('selected', 'selected');
			 		 }
					 fromeSelect.options.add(option);
				  }
  			},
	        failure: function (response, options) {
	            checkAjaxStatus(response);
	        }
		});
}
function _save(){
	 if(validateForm()){	
			var msgTip = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        msg: '正在保存信息请稍后......'
		    });
		    
		    Ext.Ajax.request({
		        url: "<c:url value='/censorship/censorship.do?method=onSave' />",
		        // 请求的服务器地址
		        form: 'censorshipFrom',
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        success: function (response, options) {
		            msgTip.hide();
		            var result = Ext.util.JSON.decode(response.responseText);
		            var auditing_ = true;
		
		            if (result.error != null) {
		                Ext.MessageBox.alert('提示', result.tip);
		            } else {
		            	if (result.unid!=null){
		            		//激活作废按钮
		            		document.getElementById('unid').value=result.unid;
		            		document.getElementById('fileMainFrame').contentWindow.updateFile(result.unid);
			            }
			            refreshFrame();
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
}
function _auditNext(){
	if(validateForm()){	
			var msgTip = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        msg: '正在保存信息请稍后......'
		    });
		    /**
		     * - 保存信息及明细
		     */
		    Ext.Ajax.request({
		        url: "<c:url value='/censorship/censorship.do?method=onSave' />",
		        // 请求的服务器地址
		        form: 'censorshipFrom',
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        success: function (response, options) {
		            msgTip.hide();
		            var result = Ext.util.JSON.decode(response.responseText);
		            var auditing_ = true;
		
		            if (result.error != null) {
		                Ext.MessageBox.alert('提示', result.tip);
		            } else {
		            	if (result.unid!=null){
		            		//激活作废按钮
		            		document.getElementById('unid').value=result.unid;
		            		document.getElementById('fileMainFrame').contentWindow.updateFile(result.unid);
			            }
			            refreshFrame();
						var url_ ='<%=path%>/censorship/censorship.do?method=onShowView&chooseresult=0'+"&unid="+result.unid+"&flowppage=2";
						$disabledall();
						$hideall();
						$show('new_2');
						window.location.href=url_+'&page=new_2';
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
}

function _delete(){
	if (!confirm('确认要作废?')){return;};
	var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        msg: '正在执行操作请稍后......'
	    });
		 Ext.Ajax.request({
	        url: "<c:url value='/censorship/censorship.do?method=onDelete' />",
	        // 请求的服务器地址
	        form: 'censorshipFrom',
	        // 指定要提交的表单id
	        method: 'POST',
	        success: function (response, options) {
	            msgTip.hide();
	            var result = Ext.util.JSON.decode(response.responseText);
	            if (result.error != null) {
	                Ext.MessageBox.alert('提示', result.tip);
	            } else {
	            	document.getElementById('fileMainFrame').contentWindow.deleteFileByUnidAndD_unid(document.getElementById('unid').value);
	            	document.getElementById('unid').value='';
	                Ext.ux.Toast.msg("", result.tip);
	                _refreshOpenerWin();
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

function onAuditNext(){
	$disabledall();
	var url="<%=path%>/censorship/censorship.do?method=onAuditView&unid=${param.unid}&t=${param.t}";
	$hideall();
	$show('audit_2');
	window.location.href=url+'&page=audit_2';
}

function validateForm(){ //弹出出错提示  验证
	var str = '保存失败!出错提示如下:<br />';
	var i=1;
	var blank = '&nbsp;&nbsp;';
	/** 验证 */
	var subject = document.getElementById('subject').value;
    if (subject==''){
		str+= blank+ i+ '、督办标题不能为空。<br />';
	    i++;
	}
	var duetime = document.getElementById('duetime').value;
	if (duetime==''){
		str+= blank+ i+ '、要求完成时间不能为空。<br />';
	    i++;
	}
	if(i > 1){
	   Ext.MessageBox.alert('错误提示',str);
	   return false;
	}
	return true;
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

Ext.onReady(function(){
    loadFromeType();
});
 	
</script>