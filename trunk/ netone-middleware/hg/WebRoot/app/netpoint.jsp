<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		
		<script type="text/javascript">
		function updatePoint(lsh,point){
			var msgTip = Ext.MessageBox.show({
								title:'系统提示',
								width : 200,
								msg:'正在保存信息请稍后......'
	  		         });
              Ext.Ajax.request({
						url :"<c:url value='/app.do?method=updateNetPoint' />",//请求的服务器地址
						method : 'POST',
						params:{lsh:lsh,point:point},
						success : function(response,options){
							msgTip.hide();
							var result = Ext.util.JSON.decode(response.responseText);
							if(result.error==null){
								Ext.ux.Toast.msg("", result.tip);
							} else {
								Ext.MessageBox.alert('提示',result.tip);
							}
						},
						failure : function(response,options){
							checkAjaxStatus(response);
							var result = Ext.util.JSON.decode(response.responseText);
							Ext.MessageBox.alert('提示',result.tip);
						}
	　				});
		}
		
$(document).ready(function(){
 	
 	
    var bigpage1 = $("#table1").bigPage({position:"both",<c:if test="${!empty param.pagesize}">pageSize:${param.pagesize},</c:if>ajaxData:{url:"<c:url value='/app.do?method=queryNetpoint&rowsize=${param.rowsize}' />"},cssWidgetIds:["ajaxpageBar2"]
	    ,callback:function($table){   
		    $("a.imagex").each(function(){
		    	$(this).lightbox();
		    })
		    $table.find("td").attr("nowrap","nowrap");
		    
		    $table.find("td").each(function(){
		    	$(this).find('input[type=radio]').bind("click",function(){
		    		var obj = $(this).siblings("input[type=hidden]");
		    		var lsh = obj.attr('id');
		    		var point = obj.val();
					updatePoint(lsh,point);
				})

		    	
		    })
	    }
    
    });  
    
    
    $("#searchBtn").click(function(){
       bigpage1.search({
       	"beginDate":$("#beginDate").val(),
       	"endDate":$("#endDate").val(),
       	"netName":$("#netName").val(),
       	"createPerson":$("#createPerson").val(),
       	"ppname":$("#ppname").val(),
       	"point":$('#point').val()
       });
    });
    
})
</script>
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
	height:18px;
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
	background: url(/ndyd/script/theme/main/blue/images/first_top.gif) ;
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
	background: url(/ndyd/script/theme/main/blue/images/title.gif) ;
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
	<script>function $WdatePicker(t){if (t==1){		WdatePicker({isShowClear:true,dateFmt:"yyyy-MM-dd HH:mm:ss"});	} else if(t==2){		WdatePicker({isShowClear:true,dateFmt:"yyyy-MM-dd"});	} else if(t=3){		WdatePicker({isShowClear:true,dateFmt:"HH:mm:ss"});	}}</script>
		<title></title>
	</head>
	<body>
	<center>
	<form id="form1">
	<table id="xreport" name="xreport" class="table_form" width="392"
								bgcolor="white" cellspacing="0" cellpadding="0" align="center"
								border="0" >
								
				<tr class="form_tr" >
					<td class="form_td" nowrap="nowrap" align="center">
						<div class="form_fieldcontent" style="width: 392px">
							<div class="form_fieldcaption" align="right" title="理货日期"
								style="width: 60px;">
								理货日期:
							</div>
							<div class="form_fieldinput" style="width: 332px;"
								align="left">
								<input type="text" id="beginDate"
									name="beginDate" style="width: 137.5px" class="Wdate"
									onFocus="$WdatePicker(2);" value="${beginDate}" />
								至
								<input type="text" id="endDate" name="endDate"
									style="width: 137.5px" class="Wdate"
									onFocus="$WdatePicker(2);" value="${endDate}" />
							</div>
						</div>
					</td>
				</tr>
				
				
				<tr class="form_tr" >
					<td class="form_td" nowrap="nowrap" align="center">
						<div class="form_fieldcontent" style="width: 392px">
							<div class="form_fieldcaption" align="right" title="网点"
								style="width: 60px;">
								网点:
							</div>
							<div class="form_fieldinput" style="width: 332px;"
								align="left">
								<input id="netName" name="netName" style="width: 307.0px" />
							</div>
						</div>
					</td>
				</tr>
				<tr class="form_tr" >
					<td class="form_td" nowrap="nowrap" align="center">
						<div class="form_fieldcontent" style="width: 392px">
							<div class="form_fieldcaption" align="right" title="理货员"
								style="width: 60px;">
								理货员:
							</div>
							<div class="form_fieldinput" style="width: 332px;"
								align="left">
								<input id="createPerson" name="createPerson" style="width: 307.0px" />
							</div>
						</div>
					</td>
				</tr>
				<tr class="form_tr" >
					<td class="form_td" nowrap="nowrap" align="center">
						<div class="form_fieldcontent" style="width: 392px">
							<div class="form_fieldcaption" align="right" title="品牌"
								style="width: 60px;">
								品牌:
							</div>
							<div class="form_fieldinput" style="width: 332px;"
								align="left">
								<input id="ppname" name="ppname" style="width: 307.0px" />
							</div>
						</div>
					</td>
				</tr>
				<tr class="form_tr" >
					<td class="form_td" nowrap="nowrap" align="center">
						<div class="form_fieldcontent" style="width: 392px">
							<div class="form_fieldcaption" align="right" title="评分"
								style="width: 60px;">
								评分:
							</div>
							<div class="form_fieldinput" style="width: 332px;"
								align="left">
								<select id="point" name="point" style="width: 307.0px" >
									<option value="">所有</option>
									<option value="1">合格</option>
									<option value="0">不合格</option>
									<option value="2">无效</option>
								</select>
							</div>
						</div>
					</td>
				</tr>
				<tr class="form_tr" >
					<td class="form_td" nowrap="nowrap" align="center">
						<div class="form_fieldcontent" style="width: 392px">
							<input type="button" id="searchBtn" value="查询"/>
							<input type="button" id="resetBtn" value="重置" onclick="javascript:document.getElementById('form1').reset();" />
							<input type="button" id="hideBtn" value="隐藏" onclick="$('#form1').hide();$('#showBtn').show();" />
						</div>
					</td>
				</tr>
								
	</table>
	</form>
	<input type="button" id="showBtn" value="显示" onclick="$('#form1').show();$('#showBtn').hide();" style="display: none;"/>
	<BR>
		<table id="table1" border="0" cellspacing="5" cellpadding="5" align="center">     
		<tbody></tbody> 
		</table> 
     </center>  
	</body>
</html>
