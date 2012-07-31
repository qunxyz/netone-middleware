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
	FILTER: progid :                     DXImageTransform .                 
		   Microsoft .    
		     
		  
		 
		
		   
		Gradient(GradientType =                         0, StartColorStr =   
		              
		      #D9ECF9, EndColorStr =                     #62BBE8);
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
	FILTER: progid :                     DXImageTransform .                 
		   Microsoft .    
		     
		  
		 
		
		   
		Gradient(GradientType =                         0, StartColorStr =   
		              
		      #FEF9FF, EndColorStr =                     #DBDCDE);
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
	FILTER: progid :                     DXImageTransform .                 
		   Microsoft .    
		     
		  
		 
		
		   
		Gradient(GradientType =                         0, StartColorStr =   
		              
		      #D9ECF9, EndColorStr =                     #62BBE8);
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
			form1.action="<c:url value='/reportx19.do?method=query' />"+formatstr;
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
								value="REPORTQ.REPORTQ.SSXS.SSXSMXB" unselectable="off"
								onFocus="this.select()" />
							<table id="xreport" name="xreport" class="table_form" width="392"
								bgcolor="white" cellspacing="0" cellpadding="0" align="center"
								border="0" unselectable="off" onFocus="this.select()">
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="客户名称"
												style="width: 60px;">
												客户名称:
											</div>
											<div class="form_fieldinput" style="width: 136px;"
												align="left">
												<input type="hidden" id="repstrdim3_OPE"
													name="repstrdim3_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="text" id="repstrdim3" name="repstrdim3"
													style="width: 99.0px" unselectable="off"
													onFocus="this.select()" />
											</div>
										</div>
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="客户卡号"
												style="width: 60px;">
												客户卡号:
											</div>
											<div class="form_fieldinput" style="width: 136px;"
												align="left">
												<input type="hidden" id="repstrdim13_OPE"
													name="repstrdim13_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="text" id="repstrdim13" name="repstrdim13"
													style="width: 99.0px" unselectable="off"
													onFocus="this.select()" />
											</div>
										</div>

									</td>
								</tr>
								<tr class="form_tr" unselectable="off" onFocus="this.select()">
									<td class="form_td" unselectable="off" onFocus="this.select()"
										nowrap="nowrap">
										<div class="form_fieldcontent" style="width: 196px">
											<div class="form_fieldcaption" align="left" title="主石名称"
												style="width: 60px;">
												手机号码:
											</div>
											<div class="form_fieldinput" style="width: 136px;"
												align="left">
												<input type="hidden" id="repstrdim4_OPE"
													name="repstrdim4_OPE" value="=" unselectable="off"
													onFocus="this.select()" />
												<input type="text" id="repstrdim4" name="repstrdim4"
													style="width: 99.0px" unselectable="off"
													onFocus="this.select()" />
											</div>
										</div>
									</td>
								</tr>
							</table>
							<input type="hidden" id="_REPORTID" name="_REPORTID"
								value="REPORTDS.REPORTDS.SSXS.SSXSMXB" unselectable="off"
								onFocus="this.select()" />
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
										'
										#_REPORTID').val('REPORTDS.REPORTDS.SSXS.SSXSMXB'); unselectable="off"
										;
										onFocus="this.select()" checked="checked" />
									&nbsp;客户消费记录查询
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
