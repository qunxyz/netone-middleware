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
		<script>function $WdatePicker(t){if (t==1){		WdatePicker({isShowClear:true,dateFmt:"yyyy-MM-dd HH:mm:ss"});	} else if(t==2){		WdatePicker({isShowClear:true,dateFmt:"yyyy-MM-dd"});	} else if(t=3){		WdatePicker({isShowClear:true,dateFmt:"HH:mm:ss"});	}}</script>

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
	FILTER: progid :               DXImageTransform .              
		Microsoft . 
		     
		  
		 
		
		 Gradient(GradientType =                   0, StartColorStr =
		              
		   #D9ECF9, EndColorStr =               #62BBE8);
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
	FILTER: progid :               DXImageTransform .              
		Microsoft . 
		     
		  
		 
		
		 Gradient(GradientType =                   0, StartColorStr =
		              
		   #FEF9FF, EndColorStr =               #DBDCDE);
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
	FILTER: progid :               DXImageTransform .              
		Microsoft . 
		     
		  
		 
		
		 Gradient(GradientType =                   0, StartColorStr =
		              
		   #D9ECF9, EndColorStr =               #62BBE8);
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
	background: url(<%=path%>/script/theme/main/blue/images/first_top.gif);
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
	background: url(<%=path%>/script/theme/main/blue/images/title.gif);
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
			$('#rowcolumnStrs').val(getSelectedValueStr(document.getElementById('rowselect')));
		    $('#rowcolumnNameStrs').val(getSelectedTextStr(document.getElementById('rowselect')));
		    $('#colcolumnStrs').val(getSelectedValueStr(document.getElementById('colselect')));
		    $('#functionValueStrs').val("{'"+$('#funcKey').val()+"':'"+$('#funcValue').val()+"'}");
		
			var rowcolumnStrs = $('#rowcolumnStrs').val();
			if (rowcolumnStrs==''){
				alert('数据透视表字段行区域不能为空！');
				$('#tabs').tabs('select', "tabs-2");
				return;
			}
		
			/***
			var msgTip = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在搜索请稍候......'
		    });
		    **/
		    if ($('#productName').val()==''){
					$('#productId').val('');
					$('#productName').val('');
			}
			if ($('#partName').val()==''){
					$('#partId').val('');
					$('#partName').val('');
			}
		    
		    var formatstr = "&format="+$('#format').val();
		    
		    var form1 = document.getElementById('_xreport_form');
			form1.action="<c:url value='/app.do?method=querySellPivotTable' />"+formatstr;
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
		
		function openclientselect(){
			window.open('<c:url value="/app.do?method=clientMultiSelectMain"/>');
		}
		
		function $selectUser(ids,values,texts){
			document.getElementById('partName').value=texts;
			document.getElementById('partId').value=ids;
		}
		
		function openproductselect(){
			window.open('<c:url value="/app.do?method=productMultiSelectMain"/>');
		}
		
		function $selectProduct(ids,values,texts){
			document.getElementById('productName').value=texts;
			document.getElementById('productId').value=ids;
		}
		
		$(function() {
			$("#tabs").tabs();
			$('#tabs').tabs('select', "tabs-1");
			
			$('#hiddenSelectTab').val("tabs2-1");
			$("#tabs2").tabs({
				select:function(event, ui){
					$('#hiddenSelectTab').val(ui.panel.id);
				}
			});
			$('#tabs2').tabs('select', "tabs2-1");
			
			$("#tabs3").tabs();
			$('#tabs3').tabs('select', "tabs3-1");
			
			//网点联想
			$('#partName').autocomplete('<c:url value="/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.CLIENT"/>', {
				multiple: true,
				dataType: "json",
				autoFill: false,
				mustMatch: true,
				matchContains: true,
				scrollHeight: 220,
				parse: function(data) {
					return $.map(data, function(row) {
						return {
							data: row,
							value: row.FName,
							result: row.FName
						}
					});
				},
				formatItem: function(item) {
					return  item.FShortNumber+' '+item.FName;
				},
				formatResult: function(item) {
					return item.FName;
				}
			
			})
			.result(function(e, item) {
				if(item!=null){
					$('#partId').val(item.FItemID);
					$('#partName').val(item.FName);
				} else {
					$('#partId').val('');
					$('#partName').val('');
				}
			});
			
			//产品联想
			$('#productName').autocomplete('<c:url value="/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.ALLPRODUCT"/>', {
				multiple: true,
				dataType: "json",
				autoFill: false,
				mustMatch: true,
				matchContains: true,
				scrollHeight: 220,
				parse: function(data) {
					return $.map(data, function(row) {
						return {
							data: row,
							value: row.FName,
							result: row.FName
						}
					});
				},
				formatItem: function(item) {
					return  item.FShortNumber+' '+item.FName;
				},
				formatResult: function(item) {
					return item.FName;
				}
			
			})
			.result(function(e, item) {
				if(item!=null){
					$('#productId').val(item.FItemID);
					$('#productName').val(item.FName);
				} else {
					$('#productId').val('');
					$('#productName').val('');
				}
			});
			
		});
		
		
		function moveSelected(){//添加
			var selectx = '';
			var hiddenSelectTab = $('#hiddenSelectTab').val();
			if (hiddenSelectTab=='tabs2-1'){
				selectx='rowselect';
			} else if (hiddenSelectTab=='tabs2-2'){
				selectx='colselect';
			}
		    var oSourceSel=document.getElementById('unselect');
		    var oTargetSel=document.getElementById(selectx);
		    var arrSelValue = new Array();//建立存储value和text的缓存数组
		    var arrSelText = new Array();
		    var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
		    var index = 0;//用来辅助建立缓存数组
		    for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
		        if(oSourceSel.options[i].selected){
		            arrSelValue[index] = oSourceSel.options[i].value;//存储
		            arrSelText[index] = oSourceSel.options[i].text;
		            arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
		            index++;
		        }
		    }
		    for(var i=0; i<arrSelText.length; i++){  //增加缓存的数据到目的列表框中，并删除源列表框中的对应项
		        oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
		        oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
		    }
		}
		
		function moveSelected1(){//移除 
			var selectx = '';
			var hiddenSelectTab = $('#hiddenSelectTab').val();
			if (hiddenSelectTab=='tabs2-1'){
				selectx='rowselect';
			} else if (hiddenSelectTab=='tabs2-2'){
				selectx='colselect';
			}
		  	var oSourceSel=document.getElementById(selectx);
		    var oTargetSel=document.getElementById('unselect');
		    var arrSelValue = new Array();//建立存储value和text的缓存数组
		    var arrSelText = new Array();
		    var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
		    var index = 0;//用来辅助建立缓存数组
		    for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
		        if(oSourceSel.options[i].selected){
		            arrSelValue[index] = oSourceSel.options[i].value; //存储
		            arrSelText[index] = oSourceSel.options[i].text;
		            arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
		            index++;
		        }
		    }
		    for(var i=0; i<arrSelText.length; i++){//增加缓存的数据到目的列表框中，并删除源列表框中的对应项
		        oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
		        oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
		    }
		}
		
		function loadAndClearAll(obj){//全选
			var selectx = '';
			var hiddenSelectTab = $('#hiddenSelectTab').val();
			if (hiddenSelectTab=='tabs2-1'){
				selectx='rowselect';
			} else if (hiddenSelectTab=='tabs2-2'){
				selectx='colselect';
			}
		  	var oSourceSel = '';
			var oTargetSel = '';
			if(obj.trim()=='loadAll'){//<-
			  oSourceSel=document.getElementById('unselect');
			  oTargetSel=document.getElementById(selectx);
			}
			if(obj.trim()=='clearAll'){//->
			  oSourceSel=document.getElementById(selectx);
		      oTargetSel=document.getElementById('unselect');
			}
		     var arrSelValue = new Array();//建立存储value和text的缓存数组
		     var arrSelText = new Array();
		     var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
		     var index = 0;//用来辅助建立缓存数组
		     for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
		             arrSelValue[index] = oSourceSel.options[i].value;
		             arrSelText[index] = oSourceSel.options[i].text;
		             arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i]; //建立value和选中option的对应关系
		             index++;
		     }
		     for(var i=0; i<arrSelText.length; i++){//增加缓存的数据到目的列表框中，并删除源列表框中的对应项
		         oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
		         oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);
		     }
		}
		
		function getSelectedValueStr(obj){//将options中的对象以逗号分隔
			 var arrValueText = new Array();
			
		     var str1 = '';
		     var deliter='';
		     
		     var options = obj.options;
			 for(var i=0; i < options.length; i++){
			      str1 += deliter + options[i].value;
			      deliter=',';
			 }
			 arrValueText[0] = str1;
			 return arrValueText; 
		}
		function getSelectedTextStr(obj){//将options中的对象以逗号分隔
			 var arrValueText = new Array();
			
		     var str1 = '';
		     var deliter='';
		     
		     var options = obj.options;
			 for(var i=0; i < options.length; i++){
			      str1 += deliter + options[i].text;
			      deliter=',';
			 }
			 arrValueText[0] = str1;
			 return arrValueText;
		}
		
		// 向上移动
		$(function() {
		    $("#upmove")
		            .click(
		                    function() {
		                    	var selectx = '';
								var hiddenSelectTab = $('#hiddenSelectTab').val();
								if (hiddenSelectTab=='tabs2-1'){
									selectx='rowselect';
								} else if (hiddenSelectTab=='tabs2-2'){
									selectx='colselect';
								}
		                        if ($("#"+selectx+" option:selected").length <= 0) {
		                            alert("请选择需要上移的选项");
		                            return;
		                        }
		                        var i = $('#'+selectx).get(0).selectedIndex;
		                        var j = i - 1;
		                        if (i > 0) {
		                            var text = $('#'+selectx).get(0).options[j].text;
		                            var value = $('#'+selectx).get(0).options[j].value;
		                            $('#'+selectx).get(0).options[j].text = $('#'+selectx).get(0).options[i].text;
		                            $('#'+selectx).get(0).options[j].value = $('#'+selectx).get(0).options[i].value;
		                            $('#'+selectx).get(0).options[i].text = text;
		                            $('#'+selectx).get(0).options[i].value = value;
		                            $('#'+selectx).get(0).selectedIndex = j;
		                        }
		                    });
		});
		
		// 向下移动
		$(function() {
		    $("#downmove")
		            .click(
		                    function() {
		                    	var selectx = '';
								var hiddenSelectTab = $('#hiddenSelectTab').val();
								if (hiddenSelectTab=='tabs2-1'){
									selectx='rowselect';
								} else if (hiddenSelectTab=='tabs2-2'){
									selectx='colselect';
								}
		                        if ($("#"+selectx+" option:selected").length <= 0) {
		                            alert("请选择需要下移的选项");
		                            return;
		                        }
		                        var i = $('#'+selectx).get(0).selectedIndex;
		                        var j = i + 1;
		                        var max = $("#"+selectx+" option:last").attr("index");
		                        if (i < max) {
		                            var text = $('#'+selectx).get(0).options[j].text;
		                            var value = $('#'+selectx).get(0).options[j].value;
		                            $('#'+selectx).get(0).options[j].text = $('#'+selectx).get(0).options[i].text;
		                            $('#'+selectx).get(0).options[j].value = $('#'+selectx).get(0).options[i].value;
		                            $('#'+selectx).get(0).options[i].text = text;
		                            $('#'+selectx).get(0).options[i].value = value;
		                            $('#'+selectx).get(0).selectedIndex = j;
		                        }
		                    });
		});
		
	</script>
	</head>

	<body>
		<center>

			<!-- S HEADER -->
			<!-- E HEADER -->

			<!-- S CENTER -->

			<div align="center" style="width: 440px">
				<div id="tabs" style="height: 100%;">
					<ul>
						<li>
							<a href="#tabs-1" onclick="" class="ui-tabs-selected">查询条件</a>
						</li>
						<li>
							<a href="#tabs-2" onclick="">数据透视表字段</a>
						</li>
					</ul>
					<div id="tabs-1">
						<form id="_xreport_form">
							
							<input type="hidden" id="rowcolumnStrs" name="rowcolumnStrs" value=""  />
							<input type="hidden" id="rowcolumnNameStrs" name="rowcolumnNameStrs" value=""  />
							<input type="hidden" id="colcolumnStrs" name="colcolumnStrs" value=""  />
							<input type="hidden" id="functionValueStrs" name="functionValueStrs" value=""  />
							
							<input type="hidden" id="naturalname" name="naturalname"
								value="REPORTQ.REPORTQ.CXZD.FXKCTJB"  />
							<table id="xreport" name="xreport" class="table_form" width="392"
								bgcolor="white" cellspacing="0" cellpadding="0" align="center"
								border="0" >
								
								<tr class="form_tr" >
									<td class="form_td" nowrap="nowrap" align="center">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="right" title="日期"
												style="width: 60px;">
												日期:
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
											<div class="form_fieldcaption" align="right" title="购货单位"
												style="width: 60px;">
												购货单位:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<input id="partId" name="partId" type="hidden" />
												<input id="partName" name="partName" style="width: 307.0px" />
												<input type="button" value="..." onclick="openclientselect()" />
											</div>
										</div>
									</td>
								</tr>
								<tr class="form_tr" >
									<td class="form_td" nowrap="nowrap" align="center">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="right" title="产品"
												style="width: 60px;">
												产品:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<input id="productId" name="productId" type="hidden" />
												<input id="productName" name="productName" style="width: 307.0px" />
												<input type="button" value="..." onclick="openproductselect()" />
											</div>
										</div>
									</td>
								</tr>
								
								<tr class="form_tr" >
									<td class="form_td" nowrap="nowrap" align="center">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="right" title="单据编号"
												style="width: 60px;">
												单据编号:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<input id="FBillNo" name="FBillNo" style="width: 307.0px" />
											</div>
										</div>
									</td>
								</tr>
								
								<tr class="form_tr" >
									<td class="form_td" nowrap="nowrap" align="center">
										<div class="form_fieldcontent" style="width: 392px">
											<div class="form_fieldcaption" align="right" title="类别"
												style="width: 60px;">
												统计字段:
											</div>
											<div class="form_fieldinput" style="width: 332px;"
												align="left">
												<select id="funcKey" name="funcKey" >
													<option value="FMonth">月份</option>
													<option value="Fdate">日期</option>
													<option value="FBillNo">单据编号</option>
													<option value="FTypeName">系统</option>
													<option value="FSupplyIDName">购货单位</option>
													<option value="FItemName">产品名称</option>
													<option value="FItemModel">规格型号</option>
													<option value="FUnitIDName">单位</option>
													<option value="Fauxqty">实发数量</option>
													<option value="FNote">备注</option>
													<option value="FDeptIDName">部门</option>
													<option value="FEmpIDName">业务员</option>
													<option value="FBaseUnitID">基本单位</option>
													<option value="FBaseQty" selected="selected">基本单位实发数量</option>
													<option value="FCUUnitName">常用单位</option>
													<option value="FCUUnitQty">常用单位数量</option>
													<option value="FConsignPrice">销售单价</option>
													<option value="FConsignAmount">销售金额</option>
													<option value="FStockPrice">出厂单价</option>
													<option value="FStockAmount">出厂金额</option>
												</select>
												
												<select id="funcValue" name="funcValue" >
													<option value="sum">合计</option>
													<option value="count">计数</option>
													<option value="avg">平均值</option>
													<option value="max">最大值</option>
													<option value="min">最小值</option>
												</select>
												
											</div>
										</div>
									</td>
								</tr>
								
								<tr>
									<td align="center">
										报表输出格式:
										<select id="format" name="format">
											<option value="html" selected="selected">
												网页
											</option>
											<option value="excel">
												excel
											</option>
											<option value="pdf">
												pdf
											</option>
										</select>
									</td>
								</tr>
							</table>
							<input type="hidden" id="_REPORTID" name="_REPORTID"  />
						</form>
						<div align='center'>
							<BR>
							<input type="button" value="查询" onclick="query();" class="btn">
						</div>
					</div>
					
					<div id="tabs-2">
						<input type="hidden" id="hiddenSelectTab" value="" />
						<center>
							<div id="tabs2" style="height: 200px;width: 155px;float: left;">
								<ul>
									<li>
										<a href="#tabs2-1" onclick="" class="ui-tabs-selected"><span style="color: red;">行区域</span></a>
									</li>
									<li>
										<a href="#tabs2-2" onclick="">列区域</a>
									</li>
								</ul>
								<div id="tabs2-1">
									<select id="rowselect" name="rowselect" ondblclick="moveSelected1();" multiple="multiple" style="height:150px;width: 110px;">
									</select>
								</div>
								<div id="tabs2-2">
									<select id="colselect" name="colselect" ondblclick="moveSelected1();" multiple="multiple" style="height:150px;width: 110px;">
									</select>
								</div>
							</div>
							<div align='center' style="height: 200px;width: 70px;float: left;" >
								<BR>
								<BR><BR>
								
								<input type="button" value="←" onclick="moveSelected();" class="btn" />
								<input type="button" value="→" onclick="moveSelected1();" class="btn" />
								<input type="button" value="全部选择" onclick="loadAndClearAll('loadAll');" class="btn" />
								<input type="button" value="全部清空" onclick="loadAndClearAll('clearAll');" class="btn" />
								<input type="button" id="upmove" value="上移" class="btn" />
								<input type="button" id="downmove" value="下移" class="btn" />
								
							</div>
							<div id="tabs3" style="height: 200px;width: 155px;float: left;">
								<ul>
									<li>
										<a href="#tabs3-1" onclick="" class="ui-tabs-selected">未选择区域</a>
									</li>
								</ul>
								<div id="tabs3-1">
									<select id="unselect" name="unselect" ondblclick="moveSelected();" multiple="multiple" style="height:150px;width: 110px;">
										<option value="FMonth">月份</option>
										<option value="Fdate">日期</option>
										<option value="FBillNo">单据编号</option>
										<option value="FTypeName">系统</option>
										<option value="FSupplyIDName">购货单位</option>
										<option value="FItemName">产品名称</option>
										<option value="FItemModel">规格型号</option>
										<option value="FUnitIDName">单位</option>
										<option value="Fauxqty">实发数量</option>
										<option value="FNote">备注</option>
										<option value="FDeptIDName">部门</option>
										<option value="FEmpIDName">业务员</option>
										<option value="FBaseUnitID">基本单位</option>
										<option value="FBaseQty">基本单位实发数量</option>
										<option value="FCUUnitName">常用单位</option>
										<option value="FCUUnitQty">常用单位数量</option>
										<option value="FConsignPrice">销售单价</option>
										<option value="FConsignAmount">销售金额</option>
										<option value="FStockPrice">出厂单价</option>
										<option value="FStockAmount">出厂金额</option>
										
									</select>
								</div>	
							</div>
						</center>
						
						<div align='center' style="width: 200px">
							<BR>
							<input type="button" value="查询" onclick="query();" class="btn" />
						</div>
					</div>


				</div>
			</div>

			<!-- E CENTER -->

			<!-- S FOOTER -->
			<!-- E FOOTER -->

		</center>
	</body>
</html>
