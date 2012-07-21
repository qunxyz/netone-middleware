<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>查询</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script type="text/javascript"
			src="<%=path%>/tiny_mce/jquery.tinymce.js"></script>
		<!-- 样式文件 -->
		<!-- 时间控件脚本 -->
		<script>function $WdatePicker(t){if (t==1){		WdatePicker({dateFmt:"yyyy-MM-dd HH:mm:ss"});	} else if(t==2){		WdatePicker({dateFmt:"yyyy-MM-dd"});	} else if(t=3){		WdatePicker({dateFmt:"HH:mm:ss"});	}}</script>

		<script type="text/javascript">
		var selectObjVar = null;//全局变量 存放需要选择资源返回值的对象
	function $select(o,url){
		selectObjVar=o;
		openWinCenter("选择",encodeURI(encodeURI(url)),800,420,true);
	}
	</script>

		<style type="text/css">
.whitefont,.whitefont a:hover,.whitefont a:visited,.whitefont a:link {
	color: #FFF;
	font: 10px "宋体";
}

body {
	font-size: 9pt;
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
	color: #3E3C20;
	height: 100%
}

A {
	color: #006699;
	text-decoration: none;
	font-size: 9pt;
}

A:hover {
	color: #D95F40;
	text-decoration: none
}

A:active {
	color: #928BA4;
	text-decoration: none
}

A:visited {
	color: #928BA4;
}

p {
	text-indent: 2em;
}

form {
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}

input {
	font-size: 9pt;
}

select {
	font-size: 9pt
}

table {
	font-size: 9pt;
}

div {
	font-size: 9pt;
}

td {
	font-size: 9pt;
}

span {
	font-size: 9pt;
}

nobr {
	font-size: 9pt;
}

html.VIE7 {
	font-family: 微软雅黑
}

html.VIE7 A {
	color: #006699;
	text-decoration: none;
	cursor: hand;
	font-size: 12pt;
	font-family: 微软雅黑
}

html.VIE7 A:hover {
	color: #D95F40;
	text-decoration: none
}

html.VIE7 A:active {
	color: #928BA4;
	text-decoration: none
}

html.VIE7 A:visited {
	color: #928BA4;
}

html.VIE7 input {
	font-size: 9pt;
	font-family: 微软雅黑;
	padding: 0px;
}

html.VIE7 textarea {
	font-size: 9pt;
	font-family: 微软雅黑;
	padding: 0px
}

html.VIE7 select {
	font-size: 9pt;
	font-family: 微软雅黑;
	padding: 0px
}

html.VIE7 table {
	font-size: 9pt;
	font-family: 微软雅黑
}

html.VIE7 div {
	font-size: 9pt;
	font-family: 微软雅黑
}

html.VIE7 td {
	font-size: 9pt;
	font-family: 微软雅黑
}

html.VIE7 span {
	font-size: 9pt;
	font-family: 微软雅黑
}

html.VIE7 nobr {
	font-size: 9pt;
	font-family: 微软雅黑
}

.VIE7td,.IE7td,.FFtd,#VIE7td,#IE7td,#FFtd {
	background-attachment: fixed;
	background-position: top center;
	background-repeat: repeat-x
}

/**表单*/
.form_td {
	line-height: 25px;
	height: 30px;
	border-bottom: 1px dashed #ddd;
}

.form_tr {
	
}

/** 公单标题表格TR */
.table_tr_title {
	
}

/** 公单标题表格TD */
.table_td_title {
	FILTER: progid :                                        
		DXImageTransform .   
		     
		                 
		             Microsoft .              
		     
		  
		 
		
		   
		
		
		
		
		    
		
		Gradient(GradientType =                                             0,
		StartColorStr =       
		
		    
		              
		                #D9ECF9, EndColorStr = 
		                                 
		     #62BBE8);
	background: -webkit-gradient(linear, left top, left bottom, from(#D9ECF9),
		to(#62BBE8) ); /* for webkit browsers */
	background: -moz-linear-gradient(top, #D9ECF9, #62BBE8);
	/* for firefox 3.6+ */
	color: #000066;
	text-align: left;
	padding-left: 10px;
	padding-right: 10px;
	font-style: normal;
	border: 1px solid #86B4E5;
	line-height: 20px;
}

/** 公单字段表格TR */
.table_tr_header {
	
}

/** 公单字段表格TD */
.table_td_header {
	FILTER: progid :                                        
		DXImageTransform .   
		     
		                 
		             Microsoft .              
		     
		  
		 
		
		   
		
		
		
		
		    
		
		Gradient(GradientType =                                             0,
		StartColorStr =       
		
		    
		              
		                #FEF9FF, EndColorStr = 
		                                 
		     #DBDCDE);
	background: -webkit-gradient(linear, left top, left bottom, from(#FEF9FF),
		to(#DBDCDE) ); /* for webkit browsers */
	background: -moz-linear-gradient(top, #FEF9FF, #DBDCDE);
	/* for firefox 3.6+ */
	color: #000000;
	text-align: center;
	padding-left: 10px;
	padding-right: 10px;
	font-style: normal;
	border: 1px solid #D9DBDC;
	line-height: 20px;
}

/** 公单内容表格TD */
.table_tr_content {
	
}

/** 公单内容表格TD */
.table_td_content {
	line-height: 25px;
	border: 1px dashed #ddd;
}

.form_fieldcontent {
	float: left;
	overflow: hidden;
	padding: 2px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}

html.VIE7 .form_fieldcontent {
	float: left;
	overflow: hidden;
	padding: 2px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}

.form_fieldinput {
	float: left;
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
	overflow: hidden;
}

html.VIE7 .form_fieldinput {
	float: left;
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
	overflow: hidden;
}

.form_fieldinput_read {
	float: left;
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
	overflow: hidden;
	border-bottom: 1px solid #ddd;
}

html.VIE7 .form_fieldinput_read {
	float: left;
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
	overflow: hidden;
	border-bottom: 1px solid #ddd;
}

.form_fieldcontent {
	float: left;
	overflow: hidden;
	padding: 2px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}

html.VIE7 .form_fieldcontent {
	float: left;
	overflow: hidden;
	padding: 2px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}

.form_fieldcaption {
	height: 18px;
	font: 12px "宋体";
	color: #001a9b;
	float: left;
	padding: 4px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
	overflow: hidden;
	border-collapse: collapse;
	background-attachment: fixed;
	background-position: top center;
	background-repeat: repeat-x;
}

.form_fieldcaption2 {
	FILTER: progid :                                        
		DXImageTransform .   
		     
		                 
		             Microsoft .              
		     
		  
		 
		
		   
		
		
		
		
		    
		
		Gradient(GradientType =                                             0,
		StartColorStr =       
		
		    
		              
		                #D9ECF9, EndColorStr = 
		                                 
		     #62BBE8);
	background: -webkit-gradient(linear, left top, left bottom, from(#D9ECF9),
		to(#62BBE8) ); /* for webkit browsers */
	background: -moz-linear-gradient(top, #D9ECF9, #62BBE8);
	/* for firefox 3.6+ */
	color: #000066;
	text-align: left;
	padding-left: 10px;
	padding-right: 10px;
	font-style: normal;
	border: 1px solid #86B4E5;
	line-height: 20px;
}

.form_fieldinput {
	float: left;
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
	overflow: hidden;
}

html.VIE7 .form_fieldinput {
	float: left;
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
	overflow: hidden;
}

/** */
#top_nd-header {
	background: url(/scm/script/theme/main/blue/images/first_top.gif);
	background-repeat: no-repeat;
	height: 11px;
	width: 12px;
	vertical-align: middle;
}

#steps_nd {
	color: #386BA4;
	margin: 7px;
	padding-top: 10px;
	padding-bottom: 10px;
	font-size: 12px;
	vertical-align: middle;
}

#top_nd-title {
	background: url(/scm/script/theme/main/blue/images/title.gif);
	background-repeat: no-repeat;
	width: 28px;
	height: 11px;
}

#helpInfo_nd {
	color: #386BA4;
	margin: 5px;
	padding: 5px;
	font-size: 12px;
	padding-left: 30px;
	font-weight: normal;
}

#title_nd {
	color: #386BA4;
	font-size: 16px;
	font-weight: bold;
}

#hr_nd {
	height: 1px;
	border: none;
	border-top: 1px dashed #E3E3E3;
}
</style>
		<script type="text/javascript">
		var format = "excel";
		function query(){
		/***
			var msgTip = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在搜索请稍候......'
		    });
		    **/
		    
		    
		    var formatstr = "&format="+$('#format').val();
		    var form1 = document.getElementById('_xreport_form');
			form1.action="<c:url value='/reportx8.do?method=query' />"+formatstr;
			form1.target="_blank";
			form1.method="POST";
			form1.submit();
			/***
			Ext.Ajax.request({
		        url: "<c:url value='/xreport.do?method=query' />"+formatstr,
		        // 请求的服务器地址
		        form: '_xreport_form',
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        success: function (response, options) {
		            //msgTip.hide();
		        },
		        failure: function (response, options) {
		            //msgTip.hide();
		            //checkAjaxStatus(response);
		        }
		    });
		    **/
		}
		
		$(function() {
			$("#tabs").tabs();
			$('#tabs').tabs('select', "tabs");
		});
		
	</script>
	</head>

	<body>
		<center>

			<!-- S HEADER -->
			<jsp:include page="header.jsp"></jsp:include>
			<!-- E HEADER -->

			<!-- S CENTER -->

			<div align="center" style="width: 440px">
				<div id="tabs" style="height: 100%;">
					<ul>
						<li>
							<a href="#tabs-1" onclick="" class="ui-tabs-selected">查询依据</a>
						</li>
						<li>
							<a href="#tabs-2" onclick="">报表查询</a>
						</li>
					</ul>
					<div id="tabs-1">
						<form id="_xreport_form">
							<input type="hidden" id="naturalname" name="naturalname"
								value="REPORTQ.REPORTQ.CXZD.FXJHMXB" unselectable="off"
								onFocus="this.select()" />
							<table id="xreport" name="xreport" class="table_form" width="392"
								bgcolor="white" cellspacing="0" cellpadding="0" align="center"
								border="0" unselectable="off" onFocus="this.select()">
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="统计条件"
												style="width: 60px;">
												统计条件:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<input type="hidden" id="repselect1_OPE"
													name="repselect1_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect1_hidden"
													name="repselect1_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect1" name="repselect1"
													style="width: 295.0px">
													<option value="" selected="selected">
														请选择
													</option>
													<option value="发货明细1">
														发货明细1
													</option>
													<option value="发货明细2">
														发货明细2
													</option>
												</select>
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="条形码"
												style="width: 48px;">
												条形码:
											</div>
											<div class="form_fieldinput" style="width: 344px;"
												align="left">
												<input type="hidden" id="repstrcompare1"
													name="repstrcompare1" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repstrcompare1_OPE"
													name="repstrcompare1_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<script type="text/javascript">$(function() {if ($('#repstrcompare1_START')) $('#repstrcompare1_START').blur(function(){if ($('#repstrcompare1')) $('#repstrcompare1').val($('#repstrcompare1_START').val()+'$_$'+$('#repstrcompare1_END').val())});if ($('#repstrcompare1_END')) $('#repstrcompare1_END').blur(function(){if ($('#repstrcompare1')) $('#repstrcompare1').val($('#repstrcompare1_START').val()+'$_$'+$('#repstrcompare1_END').val())});});</script>
												<input type="text" id="repstrcompare1_START"
													name="repstrcompare1_START" style="width: 143.5px"
													unselectable="off" onFocus="this.select()" />
												至
												<input type="text" id="repstrcompare1_END"
													name="repstrcompare1_END" style="width: 143.5px"
													unselectable="off" onFocus="this.select()" />
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="进货单号"
												style="width: 60px;">
												进货单号:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<input type="hidden" id="repstrcompare2"
													name="repstrcompare2" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repstrcompare2_OPE"
													name="repstrcompare2_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<script type="text/javascript">$(function() {if ($('#repstrcompare2_START')) $('#repstrcompare2_START').blur(function(){if ($('#repstrcompare2')) $('#repstrcompare2').val($('#repstrcompare2_START').val()+'$_$'+$('#repstrcompare2_END').val())});if ($('#repstrcompare2_END')) $('#repstrcompare2_END').blur(function(){if ($('#repstrcompare2')) $('#repstrcompare2').val($('#repstrcompare2_START').val()+'$_$'+$('#repstrcompare2_END').val())});});</script>
												<input type="text" id="repstrcompare2_START"
													name="repstrcompare2_START" style="width: 137.5px"
													unselectable="off" onFocus="this.select()" />
												至
												<input type="text" id="repstrcompare2_END"
													name="repstrcompare2_END" style="width: 137.5px"
													unselectable="off" onFocus="this.select()" />
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="进货日期"
												style="width: 60px;">
												进货日期:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<input type="hidden" id="reptimes1" name="reptimes1"
													unselectable="off" onFocus="this.select()" />
												<input type="hidden" id="reptimes1_OPE" name="reptimes1_OPE"
													value="=" unselectable="off" onFocus="this.select()" />
												<script type="text/javascript">$(function() {if ($('#reptimes1_START')) $('#reptimes1_START').blur(function(){if ($('#reptimes1')) $('#reptimes1').val($('#reptimes1_START').val()+'$_$'+$('#reptimes1_END').val())});if ($('#reptimes1_END')) $('#reptimes1_END').blur(function(){if ($('#reptimes1')) $('#reptimes1').val($('#reptimes1_START').val()+'$_$'+$('#reptimes1_END').val())});});</script>
												<input type="text" id="reptimes1_START"
													name="reptimes1_START" style="width: 137.5px" class="Wdate"
													onFocus="$WdatePicker(2);this.blur();" unselectable="off"
													onFocus="this.select()" />
												至
												<input type="text" id="reptimes1_END" name="reptimes1_END"
													style="width: 137.5px" class="Wdate"
													onFocus="$WdatePicker(2);this.blur();" unselectable="off"
													onFocus="this.select()" />
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="分销商"
												style="width: 48px;">
												分销商:
											</div>
											<div class="form_fieldinput" style="width: 344px;"
												align="left">
												<input type="hidden" id="repselect2_OPE"
													name="repselect2_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect2_hidden"
													name="repselect2_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect2" name="repselect2"
													style="width: 307.0px">
													<c:forEach var="list" items="${list_FClient}">
														<option value="${list.column4}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="供应商"
												style="width: 48px;">
												供应商:
											</div>
											<div class="form_fieldinput" style="width: 344px;"
												align="left">
												<input type="hidden" id="repselect3_OPE"
													name="repselect3_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect3_hidden"
													name="repselect3_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect3" name="repselect3"
													style="width: 307.0px">
													<c:forEach var="list" items="${list_GClient}">
														<option value="${list.column4}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="经营品牌"
												style="width: 60px;">
												经营品牌:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<input type="hidden" id="repselect4_OPE"
													name="repselect4_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect4_hidden"
													name="repselect4_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect4" name="repselect4"
													style="width: 295.0px">
													<c:forEach var="list" items="${list_JingYingPingPai}">
														<option value="${list.column8}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="产品大类"
												style="width: 60px;">
												产品大类:
											</div>
											<div class="form_fieldinput" style="width: 136px;"
												align="left">
												<input type="hidden" id="repselect5_OPE"
													name="repselect5_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect5_hidden"
													name="repselect5_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect5" name="repselect5"
													style="width: 99.0px">
													<c:forEach var="list" items="${list_ChangPingDaLei}">
														<option value="${list.column8}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="自定大类"
												style="width: 60px;">
												自定大类:
											</div>
											<div class="form_fieldinput" style="width: 136px;"
												align="left">
												<input type="hidden" id="repselect6_OPE"
													name="repselect6_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect6_hidden"
													name="repselect6_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect6" name="repselect6"
													style="width: 99.0px">
													<c:forEach var="list" items="${list_ZiDingDaLei}">
														<option value="${list.column8}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="成色"
												style="width: 36px;">
												成色:
											</div>
											<div class="form_fieldinput" style="width: 160px;"
												align="left">
												<input type="hidden" id="repselect7_OPE"
													name="repselect7_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect7_hidden"
													name="repselect7_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect7" name="repselect7"
													style="width: 123.0px">
													<c:forEach var="list" items="${list_ChengSe}">
														<option value="${list.column9}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="宝石"
												style="width: 36px;">
												宝石:
											</div>
											<div class="form_fieldinput" style="width: 160px;"
												align="left">
												<input type="hidden" id="repselect8_OPE"
													name="repselect8_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect8_hidden"
													name="repselect8_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect8" name="repselect8"
													style="width: 123.0px">
													<c:forEach var="list" items="${list_BaoShi}">
														<option value="${list.column10}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="品名"
												style="width: 36px;">
												品名:
											</div>
											<div class="form_fieldinput" style="width: 160px;"
												align="left">
												<input type="hidden" id="repselect9_OPE"
													name="repselect9_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect9_hidden"
													name="repselect9_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect9" name="repselect9"
													style="width: 123.0px">
													<c:forEach var="list" items="${list_PingMing}">
														<option value="${list.column9}">
															${list.column3}
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="进货方式"
												style="width: 60px;">
												进货方式:
											</div>
											<div class="form_fieldinput" style="width: 136px;"
												align="left">
												<input type="hidden" id="repselect10_OPE"
													name="repselect10_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="hidden" id="repselect10_hidden"
													name="repselect10_hidden" unselectable="off"
													onFocus="this.select()" />
												<select id="repselect10" name="repselect10"
													style="width: 99.0px">
													<option value="" selected="selected">
														请选择
													</option>
												</select>
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="left" title="备注"
												style="width: 36px;">
												备注:
											</div>
											<div class="form_fieldinput" style="width: 356px;"
												align="left">
												<input type="hidden" id="repstr3_OPE" name="repstr3_OPE"
													value="=" unselectable="off" onFocus="this.select()" />
												<input type="text" id="repstr3" name="repstr3"
													style="width: 319.0px" unselectable="off"
													onFocus="this.select()" />
											</div>
										</div>
									</td>
								</tr>
							</table>
							<input type="hidden" id="_REPORTID" name="_REPORTID"
								unselectable="off" onFocus="this.select()" />
						</form>
						<div align='center'>
							<BR>
							<input type="button" value="查询" onclick="query();" class="btn">
						</div>
					</div>
					<div id="tabs-2">

						<table border="0" align="center">
							<tr>
								<td>
									<input type="radio" id="_REPORTIDKEY" name="_REPORTIDKEY"
										onchange=javascript:$(
										'#_REPORTID').val('REPORTDS.REPORTDS.FXGL.FXTTMXB'); unselectable="off"
										onFocus="this.select()" checked="checked" />
									&nbsp;分销进货明细表
								</td>
							</tr>
							<tr>
								<td>
									报表输出格式:
									<select id="format" name="format">
										<option value="excel" selected="selected">
											excel
										</option>
										<option value="html">
											网页
										</option>
										<option value="pdf">
											pdf
										</option>
									</select>
								</td>
							</tr>
						</table>


						<div align='center'>
							<BR>
							<input type="button" value="查询" onclick="query();" class="btn">
						</div>
					</div>



				</div>
			</div>

			<!-- E CENTER -->

			<!-- S FOOTER -->
			<jsp:include page="footer.jsp"></jsp:include>
			<!-- E FOOTER -->

		</center>
	</body>
</html>
