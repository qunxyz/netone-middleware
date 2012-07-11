<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/metaUnitsUtil.jsp" />
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script type="text/javascript" src="<%=path%>/tiny_mce/jquery.tinymce.js"></script>	
		
		<!-- 样式文件 -->	
		${linkcss}
		<!-- 时间控件脚本 -->
		${datecompFunc}
		<title><c:choose><c:when test="${!empty htmltitleinfo}">${htmltitleinfo} - ${activityName}</c:when><c:otherwise>${activityName}</c:otherwise></c:choose></title>
	</head>
	<body>
		<center>
		<input type="hidden" id="runtimeid" name="runtimeid" value="${param.runtimeid}"/>
		<input type="hidden" id="workcode" name="workcode" value="${param.workcode}"/>
		<jsp:include page="template-APPFRAME.APPFRAME.HGMY.11111111.jsp"></jsp:include>
		<div align="center" style="width:1000px;">
			<div>
			<table width="100%" >
				<tr>
					<td width="12px" align="left">
						<div id="top_nd-header">
						</div>
					</td>
					<td id="steps_nd" align="left">
						<c:choose>
							<c:when test="${!empty param.workcode}">
								步骤:第 <span id="stepNum">1</span> 步,共 3 步。
							</c:when>
							<c:otherwise>
								步骤:第 <span id="stepNum">1</span> 步,共 2 步。 
							</c:otherwise>
						</c:choose>
						&nbsp;<span style="font-size: 16px;font-weight: bold;color: #386BA4;">${htmltitleinfo}</span>
					</td>
				</tr>
			</table>
			<table width="100%">
				<tr>
					<td width="28px">
						<div id="top_nd-title">
						</div>
					</td>
					<td  id="title_nd" align="left">
						<c:choose>
							<c:when test="${!empty param.workcode}">
								审核
								<span id="helpInfo_nd">帮助提示: 请认真审核表单,确认无误后点击下一步按钮。如需退办或者特送请点击退办特送按钮,转办请点击转办按钮。</span>
								
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
					<a href="#tabs-1" onclick="loadInfo1();loadinfo3();">流程跟踪</a>
				</li>
				<li>
					<a href="#tabs-2" onclick="loadInfo2();">审批意见</a>
				</li>
				<li>
					<a href="#tabs-3" class="ui-tabs-selected">流程办理</a>
				</li>
			</ul>
			<div id="tabs-1">
				<table class="main_nd" width="${AvailWidth}">
					<tr>
						<td id="active" colspan="5">无</td>
					</tr>
				</table>
				<table class="main_nd" width="${AvailWidth}">
					<tr>
						<td nowrap="nowrap" class="table_td_title" width="100%">
							当前文档流程状态图
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="label_nd_2" width="100%" height="450px">
						<iframe name="myframe" id="myframe"
							width="100%" height="450px" frameborder="0" scrolling="auto"></iframe>
						</td>
					</tr>
				</table>
			</div>
			<div id="tabs-2" style="height: 100%">
				<table class="main_nd" width="${AvailWidth}">
					<tr>
						<td nowrap="nowrap" class="table_td_title" width="100%">
							审批意见
						</td>
					</tr>
					<tr>
						<td class='label_nd_2' align="center" id="mytip">
							无
						</td>
					</tr>
				</table>
			</div>

			<div id="tabs-3">
					
				<!-- 动态表单 start-->
					${form}
				<!-- 动态表单 end-->
				<!-- 
				<table class="main_nd" width="${AvailWidth}px" cellspacing="10">
					<tr>
						<td colspan="5" align="left">
						表单详细要求请点击 <a style="font-weight: bold;color: #386BA4;" href="<c:url value='/file.do?method=onPublicMainView&d_unid=public' />" target="_blank">下载</a>
						</td>
					</tr>
				</table>
				 -->
				<iframe id="fileMainFrame" name="fileMainFrame"
					src="<c:url value='/file.do?method=onMainView&d_unid=${param.lsh}&readonly=${param.readonly}&workcode=${param.workcode}&naturalname=${param.naturalname}' />"
					scrolling="auto" frameborder="0"
					style="width:${AvailWidth}px;">
				</iframe>
				<table class="main_nd" width="${AvailWidth}px" bgcolor="white" cellspacing="0" cellpadding="0" align="center">
					<tr>
						<td nowrap="nowrap" class="table_td_title" width="100%">
							审批意见
						</td>
					</tr>
					<tr>
						<td class='label_nd_2' align="center" id="mytip_">
							无
						</td>
					</tr>
				</table>
				<table class="main_nd" width="${AvailWidth}px">
					<tr>
						<td id="active_" colspan="5">无</td>
					</tr>
				</table>
				<input type="hidden" id="chooseresult" name="chooseresult" value="${param.chooseresult}">
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

	$(function() {
		
		<c:if test="${!empty permission && permission=='false'}">
			$disabledall();
			$hideall();
			/**
			Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '对不起！您无权创建。'
		    });
		    **/
			alert('该业务无权创建该工单');
			window.open("","_self");window.opener=null;
			window.close();
			$disabledall();
			$hideall();
		</c:if>
		
		$("#tabs").tabs();
		$('#tabs').tabs('select', "tabs-3");
		
	    if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()){
	        $("html").css("overflowY","scroll");
	    }
	    loadInfo2();
		loadinfo3();
	});
	
	var selectObjVar = null;//全局变量 存放需要选择资源返回值的对象
	function $select(o,url){
		selectObjVar=o;
		openWinCenter("选择",encodeURI(encodeURI(url)),800,420,true);
	}
	//加载流程图
	function loadInfo1()
	{	<c:choose>
			<c:when test="${runtimeid!='' && !empty runtimeid}">
			$('#myframe').attr('src',"<portal:envget envkey='WEBSER_WFWEB' />/listRuntimeprocess.do?runtimeid=${runtimeid}");
			</c:when>
			<c:when test="${param.runtimeid!='' && !empty param.runtimeid}">
			$('#myframe').attr('src',"<portal:envget envkey='WEBSER_WFWEB' />/listRuntimeprocess.do?runtimeid=${param.runtimeid}");
			</c:when>
			<c:otherwise>
			$('#myframe').attr('src',"<portal:envget envkey='WEBSER_WFWEB' />/viewreadonlyprocess.do?processid=${processid}");
			</c:otherwise>
		</c:choose>
	}
	
	//加载审批意见
	function loadInfo2()
	{	
		if ($('#lsh')) {if ($('#lsh').val()=='') return;} else {return;}
		var workcode = '${param.workcode}';
		var runtimeid = '${param.runtimeid}';
		Ext.Ajax.request({ 
  			url: "<c:url value='/frame.do?method=bussTipListView' />"+"&workcode="+workcode+"&runtimeid="+runtimeid,
  			method: "post", 
  			success: function(response, config){ 
		    	document.getElementById('mytip').innerHTML = response.responseText;
		    	document.getElementById('mytip_').innerHTML = response.responseText;
  			}, 
		  	failure: function(){ 
		    	Ext.MessageBox.alert("result", "请求失败"); 
		  	}
		}); 
	}
	
	function loadinfo3(){
		Ext.Ajax.request({ 
  			url: "<c:url value='/frame.do?method=queryActive&workcode=${param.workcode}&runtimeid=${param.runtimeid}' />",
  			 method: "post", 
  			success: function(response, config){ 
		    	document.getElementById('active').innerHTML = response.responseText;
		    	document.getElementById('active_').innerHTML = response.responseText;
  			}, 
			failure: function(){ 
			  Ext.MessageBox.alert("result", "请求失败"); 
			}
		});
	}

function validateForm(){//验证
${ValidateScript}
//return true;
}

function $todo(thisObj,formcode,jsonStr){
	var deliter = '';
	if (jsonStr!='')  deliter = ',';
	var column1,column2,column3,column4,column5,column6,column7,column8,column9,column10;
	var column11,column12,column13,column14,column15,column16,column17,column18,column19,column20;
	var column21,column22,column23,column24,column25,column26,column27,column28,column29,column30;
	var column31,column32,column33,column34,column35,column36,column37,column38,column39,column40;
	var column41,column42,column43,column44,column45,column46,column47,column48,column49,column50;
 	
 	var len = 0;
	for(var i=1;i<=50;i++){
		var _o = thisObj.find('#column'+i);
		if (_o){
			var $val_ =  _o.val();
			if ($val_=='undefined') $val_=null;
			if ($val_!=null && $val_!=''){
				eval("column" + i + " = '"+HTMLEncode($val_)+"';");
			} else {
				$isnull = true;len=len+1;
			}
		} else {
			len=len+1;
		}
	}
	    
	 var w = {
		formcode : formcode,
		column1 : column1,column2 : column2,column3 : column3,column4 : column4,column5 : column5,
		column6 : column6,column7 : column7,column8 : column8,column9 : column9,column10 : column10,
		column11 : column11,column12 : column12,column13 : column13,column14 : column14,column15 : column15,
		column16 : column16,column17 : column17,column18 : column18,column19 : column19,column20 : column20,
		column21 : column21,column22 : column22,column23 : column23,column24 : column24,column25 : column25,
		column26 : column26,column27 : column27,column28 : column28,column29 : column29,column30 : column30,
		column31 : column31,column32 : column32,column33 : column33,column34 : column34,column35 : column35,
		column36 : column36,column37 : column37,column38 : column38,column39 : column39,column40 : column40,
		column41 : column41,column42 : column42,column43 : column43,column44 : column44,column45 : column45,
		column46 : column46,column47 : column47,column48 : column48,column49 : column49,column50 : column50
	};
	var json___ = Ext.util.JSON.encode(w);
	if (json___.indexOf('column')>0 && len!=50){
		jsonStr += deliter + json___;
	    deliter = ',';
	}
	return jsonStr;
}

function _save(){
	 if(validateForm()){	
			var msgTip = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在保存信息请稍候......'
		    });
		    
		    
		    var $isnull = false;
		     var jsonStr = '';
		    $("body").find("table").each(function(){   
				if ($(this).attr('id')!='${formcode}')
				{
				  var formcode = $(this).attr('id');
				  $(this).find('tr.table_tr_content').each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
				  //集成展示 子表单
				  if ($(this).attr("class")=='table_form')
				  $(this).each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
				  
				}//end if
			});
			if ($isnull == true) {
		        //Ext.MessageBox.alert('提示', '子表单不能为空!');
		        //return;
		    }
		    /**
		     * - 保存信息及明细
		     */
		    jsonStr = '[' + jsonStr + ']';
		    Ext.Ajax.request({
		        url: "<c:url value='/frame.do?method=onSavaOrUpdate' />",
		        // 请求的服务器地址
		        form: '_FRAME_FORM_ID_',
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        params: {
		            subform: jsonStr
		        },
		        success: function (response, options) {
		            msgTip.hide();
		            var result = Ext.util.JSON.decode(response.responseText);
		            var auditing_ = true;
		
		            if (result.error != null) {
		            	$enableall();
		                Ext.MessageBox.alert('提示', result.tip);
		            } else {
		            	if (result.lsh!=null){
		            		//激活流程
			            	Ext.Ajax.request({
						        url: "<c:url value='/frame.do?method=onGetWfNode' />",
						        // 请求的服务器地址
						        form: '_FRAME_FORM_ID_',
						        params:{
						        	lsh:result.lsh,
						        	runtimeid:document.getElementById('runtimeid').value
						        },
						        // 指定要提交的表单id
						        method: 'POST',
						        sync: true,
						        success: function (response, options) {
						            var result_ = Ext.util.JSON.decode(response.responseText);
						            if (result_.error != null) {
						                Ext.MessageBox.alert('提示', result_.tip);
						            } else {
						            	document.getElementById('runtimeid').value=result_.runtimeid;
						            }
						        },
						        failure: function (response, options) {
						            checkAjaxStatus(response);
						            var result_ = Ext.util.JSON.decode(response.responseText);
						            Ext.MessageBox.alert('提示', result_.tip);
						        }
						        
						     
						    });
		            	    //激活作废按钮
		            		document.getElementById('lsh').value=result.lsh;
		            		document.getElementById('unid').value=result.lsh;
		            		document.getElementById('fileMainFrame').contentWindow.updateFile(result.lsh);

			            }
		            	
		            	var paramlsh = result.lsh;
		                Ext.ux.Toast.msg("", result.tip);
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
}
function _auditNext_1(){
	if(validateForm()){	
			var msgTip = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在跳转请稍候......'
		    });
		    $disabledall();
		    var $isnull = false;
		     var jsonStr = '';
		    $("body").find("table").each(function(){   
				if ($(this).attr('id')!='${formcode}')
				{
				  var formcode = $(this).attr('id');
				  $(this).find('tr.table_tr_content').each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
				  //集成展示 子表单
				  if ($(this).attr("class")=='table_form')
				  $(this).each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
				}//end if
			});
			if ($isnull == true) {
				//$enableall();
		        //Ext.MessageBox.alert('提示', '子表单不能为空!');
		        //return;
		    }
		    /**
		     * - 保存信息及明细
		     */
		    jsonStr = '[' + jsonStr + ']';
		    Ext.Ajax.request({
		        url: "<c:url value='/frame.do?method=onSavaOrUpdate' />",
		        // 请求的服务器地址
		        form: '_FRAME_FORM_ID_',
		        // 指定要提交的表单id
		        method: 'POST',
		        sync: true,
		        params: {
		            subform: jsonStr
		        },
		        success: function (response, options) {
		            //msgTip.hide();
		            var result = Ext.util.JSON.decode(response.responseText);
		            var auditing_ = true;
		
		            if (result.error != null) {
		            	$enableall();
		                Ext.MessageBox.alert('提示', result.tip);
		            } else {
		            	if (result.lsh!=null){
		            		//激活作废按钮
		            		document.getElementById('lsh').value=result.lsh;
		            		document.getElementById('unid').value=result.lsh;
		            		document.getElementById('fileMainFrame').contentWindow.updateFile(result.lsh);
			            }
			            
			            var paramlsh = result.lsh;
		                //Ext.ux.Toast.msg("", result.tip);
		                /** 获取部门值 */
		            	Ext.Ajax.request({
					        url: "<c:url value='/frame.do?method=onGetWfNode' />",
					        // 请求的服务器地址
					        form: '_FRAME_FORM_ID_',
					        params:{
					        	lsh:paramlsh,
					        	runtimeid:'${param.runtimeid}'
					        },
					        // 指定要提交的表单id
					        method: 'POST',
					        //sync: true,
					        success: function (response, options) {
					            msgTip.hide();
					            var result_ = Ext.util.JSON.decode(response.responseText);
					            if (result_.error != null) {
					            	$enableall();
					                Ext.MessageBox.alert('提示', result_.tip);
					            } else {
									//$hideall();
									//$show('new_2');
									document.getElementById('runtimeid').value=result_.runtimeid;
									document.getElementById('workcode').value=result_.workcode;
									
									if (result_.ismultinode==true || result_.ismultinode=='true'){
										var url="<%=path%>/frame.do?method=onAuditView&commiter="+result_.commiter+"&naturalname=${param.naturalname}&lsh="+paramlsh+"&workcode="+result_.workcode+"&operatemode=${param.operatemode}&chooseresult=0&filteractiveids_=${param.filteractiveids_}";
										window.location.href=url+'&page=audit_2&pagenew=1';
									} else {
										var url_ ='<%=path%>/frame.do?method=onShowView&naturalname=${param.naturalname}&chooseresult=0'+"&lsh="+paramlsh+"&workcode="+result_.workcode+"&runtimeid="+result_.runtimeid+"&flowppage=2&filteractiveids_=${param.filteractiveids_}";
										window.location.href=url_+'&page=new_2';
									}
					            }
					        },
					        failure: function (response, options) {
					            msgTip.hide();
					            $enableall();
					            checkAjaxStatus(response);
					            var result_ = Ext.util.JSON.decode(response.responseText);
					            Ext.MessageBox.alert('提示', result_.tip);
					        }
					    });
		               	
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
}

function _delete(t){
		var url = "<c:url value='/frame.do?method=onDelete' />";
		if (t!=null && t==0){
			url = "<c:url value='/frame.do?method=onLogicDelete' />";
			if (!confirm('确认要归档?')){return;};
		} else {
			if (!confirm('确认要作废?')){return;};
		}
		var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        closable:false,
	        msg: '正在执行操作请稍候......'
	    });
		 Ext.Ajax.request({
	        url: url,
	        // 请求的服务器地址
	        form: '_FRAME_FORM_ID_',
	        // 指定要提交的表单id
	        method: 'POST',
	        params:{workcode:'${param.workcode}',lsh:document.getElementById('lsh').value},
	        success: function (response, options) {
	            msgTip.hide();
	            var result = Ext.util.JSON.decode(response.responseText);
	            if (result.error != null) {
	                Ext.MessageBox.alert('提示', result.tip);
	            } else {
	            	document.getElementById('lsh').value='';
	            	document.getElementById('unid').value='';
	                Ext.ux.Toast.msg("", result.tip);
	            	if (t!=null && t==0){//归档
	            		$disabledall();
	            		_refreshOpenerWin();
		            	window.open("","_self");
		            	window.opener=null;
		            	//window.open('','_top');
		            	window.close();
	            	}else{
	            		document.getElementById('fileMainFrame').contentWindow.deleteFileByUnidAndD_unid(document.getElementById('unid').value);
	            		_refreshOpenerWin();
		            	window.open("","_self");window.opener=null;
		            	//window.open('','_top');
		            	window.close();
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

function _refreshOpenerWin() {
	if (opener && opener.location) {
		if (opener.document.getElementById('openerWinIdx')){
			opener.$delnode(document.getElementById('lsh').value);
		}
    }
}

function onAuditNext(chooseresult){
	<c:choose>
		<c:when test="true">
		var $pass = validateForm();
		if (chooseresult=='3' || chooseresult=='1') $pass=true;
		if($pass){	
					var msgTip = Ext.MessageBox.show({
				        title: '提示',
				        width: 250,
				        closable:false,
				        msg: '正在跳转请稍候......'
				    });
				    
				    
				    var $isnull = false;
				     var jsonStr = '';
				    $("body").find("table").each(function(){   
						if ($(this).attr('id')!='${formcode}')
						{
						  var formcode = $(this).attr('id');
						  $(this).find('tr.table_tr_content').each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
						  //集成展示 子表单
						  if ($(this).attr("class")=='table_form')
						  $(this).each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
						  
						}//end if
					});
					if ($isnull == true) {
				        //Ext.MessageBox.alert('提示', '子表单不能为空!');
				        //return;
				    }
				    /**
				     * - 保存信息及明细
				     */
				    jsonStr = '[' + jsonStr + ']';
				    Ext.Ajax.request({
				        url: "<c:url value='/frame.do?method=onSavaOrUpdate' />",
				        // 请求的服务器地址
				        form: '_FRAME_FORM_ID_',
				        // 指定要提交的表单id
				        method: 'POST',
				        sync: true,
				        params: {
				            subform: jsonStr
				        },
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            var auditing_ = true;
				
				            if (result.error != null) {
				            	$enableall();
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	$disabledall();
								//$hideall();
								//$show('audit_2');
								var url="<%=path%>/frame.do?method=onAuditView&commiter=${param.commiter}&naturalname=${param.naturalname}&lsh=${param.lsh}&workcode=${param.workcode}&operatemode=${param.operatemode}&chooseresult="+ chooseresult+"&filteractiveids_=${param.filteractiveids_}";
								window.location.href=url+'&page=audit_2&pagenew=${param.pagenew}';
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
		</c:when>
		<c:otherwise>
			var $tips = Ext.MessageBox.show({
		        title: '提示',
		        width: 250,
		        closable:false,
		        msg: '正在跳转请稍候......'
		    });
			$disabledall();
			//$hideall();
			//$show('audit_2');
			var url="<%=path%>/frame.do?method=onAuditView&commiter=${param.commiter}&naturalname=${param.naturalname}&lsh=${param.lsh}&workcode=${param.workcode}&operatemode=${param.operatemode}&chooseresult="+ chooseresult+"&filteractiveids_=${param.filteractiveids_}";
			window.location.href=url+'&page=audit_2';
		</c:otherwise>
	</c:choose>

}

function HTMLEncode(html) 
{ 
var temp = document.createElement ("div"); 
(temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html); 
var output = temp.innerHTML; 
temp = null; 
return output; 
} 

/*** 开始脚本 */
$(function() {

/** 促销公司方案编号生成 初始化 */
var t7806a31e97f811e1b01667a74cfdf95c_column3 = $('table#7806a31e97f811e1b01667a74cfdf95c_').find('#column3');
if (t7806a31e97f811e1b01667a74cfdf95c_column3.val()==''){
	$.getJSON("http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.GETCUXIAO", 
	 function(jsonx){
		if (jsonx!=null){
			t7806a31e97f811e1b01667a74cfdf95c_column3.val(jsonx.code);
		}
	});
} 

/** 促销网点名称 联想组件 */ 
$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column5").autocomplete('http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.CLIENT', {
	multiple: false,
	dataType: "json",
	autoFill: true,
	mustMatch: true,
	matchContains: false,
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
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column6").val(item.FAddress);
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column7").val(item.FContact);
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column9").attr("value",item.F_104);
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column8").val(item.FPhone);
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column10").val(item.FItemID);
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column11").val(item.FTypeID);
	} else {
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column6").val('');
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column7").val('');
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column9").attr("value",'');
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column8").val('');
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column10").val('');
	$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column11").val('');
	}
});

/** 促销网点结款方式 初始化*/
$.getJSON("http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.SETTLEMENTPERIOD", 
 function(jsonx){
  $.each(jsonx, function(ii,itemx){
  	if (itemx!=null){
  		var column9_val = '';
  		var column9_obj = $('table#7806a31e97f811e1b01667a74cfdf95c_').find('#column9_hidden');
  		if (column9_obj) column9_val=column9_obj.val();
  		var selected = '';
  		if (column9_val==itemx.FItemID) selected = " selected=\'selected\' ";
		$('table#7806a31e97f811e1b01667a74cfdf95c_').find('#column9').append('<option value=\"'+itemx.FItemID+'\" '+selected+'>'+itemx.FName+'</option>');
	}
  });

});

/** 促销子表单操作*/
$("table#5fe85cb097f911e1b01667a74cfdf95c_").find('#column3').live('focus',function(){ 

 $(this).autocomplete('http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.PRODUCT', {
	multiple: false,
	dataType: "json",
	autoFill: true,
	mustMatch: true,
	matchContains: false,
	scrollHeight: 220,
	width:300,
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
 if (item!=null){
  var ccc = $('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column10").val();
 if (ccc=='' && $(this).val()!=''){
 	alert('请先输入促销网点名称!');
 	return;
 }
 
 	var fitemid = item.FItemID;
	$(this).parent().parent().find('#column4').val(item.FModel);
	$(this).parent().parent().find('#column19').val(fitemid);
	var thisobj18 = $(this).parent().parent().find('#column18');
	var thisobj5 = $(this).parent().parent().find('#column5'); 
	var FUnitID = null;
	$.getJSON("http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.QUERYUNITBYUNIDGROUPID&q="+item.FUnitGroupID, 
	 function(jsonx){
	  thisobj18.empty();
	  $.each(jsonx, function(ii,itemx){
	  		if(ii==0) {
	  			FUnitID=itemx.FMeasureUnitID;
				var sc_FTypeID = $('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column11").val();
				var sc_FUnitID = FUnitID;
				$.getJSON("http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.GETPRICEBYFITEMID&q="+fitemid+"|"+sc_FTypeID+"|"+sc_FUnitID, 
				 function(itemxg){
						if(itemxg!=null && itemxg[0]!=null) thisobj5.val(itemxg[0].FPrice);
				});
	  		}
			if(itemx!=null) thisobj18.append('<option value=\"'+itemx.FMeasureUnitID+'\">'+itemx.FName+'</option>');
	  });
	
	});

 } else {
 	$(this).parent().parent().find('#column4').val('');
 	$(this).parent().parent().find('#column19').val('');
 	$(this).parent().parent().find('#column5').val('');
 	$(this).parent().parent().find('#column18').val('');
 }
	
});
$(this).unbind("focus");

});


/** 单位change事件 */
$('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column11").change(function(){

	var thisobj5 = $(this).parent().parent().find('#column5');
	var sc_FTypeID = $('table#7806a31e97f811e1b01667a74cfdf95c_').find("#column11").val();
	var sc_FUnitID = $(this).val();
	var fitemid = $(this).parent().parent().find('#column19').val();
	$.getJSON("http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.GETPRICEBYFITEMID&q="+fitemid+"|"+sc_FTypeID+"|"+sc_FUnitID, 
	 function(itemxg){
			if(itemxg!=null) thisobj5.val(itemxg[0].FPrice);
	});

});
/** 初始化加载单位*/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column19').each(function(){
 var thisobj18 = $(this).parent().parent().find('#column18');
 var thisobj18_val ='';
 var thisobj18_obj = $(this).parent().parent().find('#column18_hidden');
 if (thisobj18_obj) thisobj18_val=thisobj18_obj.val();
 if ($(this).val()!=''){
	$.getJSON("http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.GETPRODUCTBYID&q="+$(this).val(), 
	 function(itemxg){
	 	if (itemxg!=null){
			$.getJSON("http://hg.fzjunling.com:91/ndyd/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.HG.QUERYUNITBYUNIDGROUPID&q="+itemxg[0].FUnitGroupID, 
			 function(jsonx){
			  thisobj18.empty();
			  $.each(jsonx, function(ii,itemx){
			  	if (itemx!=null){
			  		var selected = '';
			  		if (thisobj18_val==itemx.FMeasureUnitID) selected = " selected=\'selected\' ";
					thisobj18.append('<option value=\"'+itemx.FMeasureUnitID+'\" '+selected+'>'+itemx.FName+'</option>');
				}
			  });
			
			});
		}
	});
 }	
});



/** 访问子表单每行指定列字段的值 */
/**
总计现折
公司折让现折*/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column6').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column3');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column6').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column10').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }	
});
$sum=$sum.toFixed(2);
c.val($sum);

});

/**厂家折让现折**/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column10').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column3');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column10').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column6').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }
});
$sum=$sum.toFixed(2);
c.val($sum);

});

/**总计期折
公司折让期折**/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column7').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column4');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column7').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column11').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }	
});
$sum=$sum.toFixed(2);
c.val($sum);

});

/**厂家折让期折**/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column11').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column4');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column11').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column7').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }
});
$sum=$sum.toFixed(2);
c.val($sum);

});

/**总计搭赠
公司折让搭赠**/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column8').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column5');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column8').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column12').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }
});
$sum=$sum.toFixed(2);
c.val($sum);

});

/**厂家折让搭赠**/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column12').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column5');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column12').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column8').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }
});
$sum=$sum.toFixed(2);
c.val($sum);

});

/**总计其他
公司折让其他**/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column9').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column6');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column9').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column20').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }	
});
$sum=$sum.toFixed(2);
c.val($sum);

});

/**厂家折让其他**/
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column20').live("change",function(){

var c = $('table#0bbd9982980c11e1b01667a74cfdf95c_').find('#column6');
$sum=0;
$('table#5fe85cb097f911e1b01667a74cfdf95c_').find('#column20').each(function(){
 if ($(this).parent().parent().find('#column19').val()!=''){
	var v=parseFloat($(this).val());
	if(isNaN(v)){
			v=0;
	}
	var v2 = parseFloat($(this).parent().parent().find('#column9').val());
	if(isNaN(v2)){
			v2=0;
	}
	$sum+=v+v2;
 }	
});
$sum=$sum.toFixed(2);
c.val($sum);

});


});
/*** 结束脚本 */
</script>