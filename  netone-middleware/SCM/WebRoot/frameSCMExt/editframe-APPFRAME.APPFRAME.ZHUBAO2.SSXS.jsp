<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>	
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
		
		<link rel="stylesheet" href="<%=path%>/script/jquery-plugin/jquery-ui/themes/redmond/jquery-ui-1.7.3.custom.css" type="text/css" />
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery-ui/ui/jquery-ui-1.7.3.custom.js"></script>
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery-ui/ui/jquery.ui.widget.js"></script>
		
		<!-- multiselect -->
		<link type="text/css" href="<%=path%>/script/jquery-plugin/jquery-multiselect/jquery.multiselect.css" rel="stylesheet" />
		<link type="text/css" href="<%=path%>/script/jquery-plugin/jquery-multiselect/jquery.multiselect.filter.css" rel="stylesheet" />
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery-multiselect/jquery.multiselect.min.js"></script>
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery-multiselect/jquery.multiselect.filter.min.js"></script>
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery-multiselect/i18n/jquery.multiselect.filter.zh-cn.js"></script>
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery-multiselect/i18n/jquery.multiselect.zh-cn.js"></script>

		
		<script src="<%=path%>/script/jquery-plugin/json2.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script type="text/javascript" src="<%=path%>/tiny_mce/jquery.tinymce.js"></script>	
		
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jqgrid/i18n/grid.locale-cn.js"></script>
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jqgrid/jquery.jqGrid.src.js"></script>
		<link rel="stylesheet" href="<%=path%>/script/jquery-plugin/jqgrid/css/ui.jqgrid.css" type="text/css" media="screen" />
		
		
		<!-- 样式文件 -->	
		${linkcss}
		<!-- 时间控件脚本 -->
		${datecompFunc}
		<script>
		var onuser='<rs:logininfo />';
		/*
		Auto-growing textareas; technique ripped from Facebook
		(Textarea need set style "overflow:hidden" under IE)
		*/
		$(function() {
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
			var column51,column52,column53,column54,column55,column56,column57,column58,column59,column60;
			var column61,column62,column63,column64,column65,column66,column67,column68,column69,column70;
			var column71,column72,column73,column74,column75,column76,column77,column78,column79,column80;
			var column81,column82,column83,column84,column85,column86,column87,column88,column89,column90;
		 	var column91,column92,column93,column94,column95,column96,column97,column98,column99,column100;
		 	
		 	var len = 0;
			for(var i=1;i<=100;i++){
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
				column46 : column46,column47 : column47,column48 : column48,column49 : column49,column50 : column50,
				column51 : column51,column52 : column52,column53 : column53,column54 : column54,column55 : column55,
				column56 : column56,column57 : column57,column58 : column58,column59 : column59,column60 : column60,
				column61 : column61,column62 : column62,column63 : column63,column64 : column64,column65 : column65,
				column66 : column66,column67 : column67,column68 : column68,column69 : column69,column70 : column70,
				column71 : column71,column72 : column72,column73 : column73,column74 : column74,column75 : column75,
				column76 : column76,column77 : column77,column78 : column78,column79 : column79,column80 : column80,
				column81 : column81,column82 : column82,column83 : column83,column84 : column84,column85 : column85,
				column86 : column86,column87 : column87,column88 : column88,column89 : column89,column90 : column90,
				column91 : column91,column92 : column92,column93 : column93,column94 : column94,column95 : column95,
				column96 : column96,column97 : column97,column98 : column98,column99 : column99,column100 : column100
				
			};
			var json___ = Ext.util.JSON.encode(w);
			if (json___.indexOf('column')>0 && len!=100){
				jsonStr += deliter + json___;
			    deliter = ',';
			}
			return jsonStr;
		}
		
		function _continueAdd(){//继续创建
			window.location.href='<%=path%>/frame.do?method=onEditViewMain&naturalname=${param.naturalname}';
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
						  if (formcode!=''){
						  $(this).find('tr.form_tr').each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
						  	$(this).find('tr.table_tr_content').each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
						  	//集成展示 子表单
							var ids = jQuery("table#"+formcode).jqGrid('getDataIDs');   
						    for(var i=0;i < ids.length;i++){   
						        var cl = ids[i];   
						        $(this).each(function(){jsonStr=$todo($('#'+cl),formcode,jsonStr);});
						    }
						  
						  }
						  
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
				            	//$enableall();
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	if (result.lsh!=null){
				            	document.getElementById('lsh').value=result.lsh;
				            	document.getElementById('unid').value=result.lsh;
				            	/**
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
				            		//document.getElementById('fileMainFrame').contentWindow.updateFile(result.lsh);
								**/
									//打印销售单
									/**
									$.getJSON('http://42.120.40.204:83/scm/reportx18.do?method=query&lsh='+result.lsh, 
									 function(jsonx){
									});
									**/
									//window.open('http://42.120.40.204:83/scm/reportx18.do?method=query&lsh='+result.lsh);
					            }
				            	
				            	var paramlsh = result.lsh;
				                Ext.ux.Toast.msg("", result.tip);
				                if (window.opener && window.opener.location) {
			            			window.opener.document.getElementById('addlsh').value=result.lsh;
			            			window.opener.refresh();
			            			window.opener.refreshExtdata();
							    }
				            }
				        },
				        failure: function (response, options) {
				            msgTip.hide();
				            //$enableall();
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
				            		//document.getElementById('fileMainFrame').contentWindow.updateFile(result.lsh);
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
		
		function _print(){
			var url = "<c:url value='/frame.do?method=print' />"+"&naturalname=${param.naturalname}&lsh="+document.getElementById('lsh').value;
			window.open(url);
		}
		
		function _delete(t){
				var url = "<c:url value='/frame.do?method=onDelete' />";
				if (t!=null && t==0){
					url = "<c:url value='/frame.do?method=onLogicDelete' />";
					if (!confirm('确认要归档?')){return;};
				} else {
					if (!confirm('确认要删除?')){return;};
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
				            	window.opener=null;
				            	//window.open('','_top');
				            	window.close();
			            	}else{
			            		document.getElementById('fileMainFrame').contentWindow.deleteFileByUnidAndD_unid(document.getElementById('unid').value);
			            		_refreshOpenerWin();
				            	window.opener=null;
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
		
		$(function() {
			
			//$("#tabs").tabs();
			//$('#tabs').tabs('select', "tabs-3");
			
		    if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()){
		        //$("html").css("overflowY","scroll");
		    }
		    //loadInfo2();
			//loadinfo3();
			if ('${param.naturalname}'=='APPFRAME.APPFRAME.JEWELRY.JEWELRYAPP15'
			|| '${param.naturalname}'=='APPFRAME.APPFRAME.JEWELRY.JEWELRY.JEWELRYAPP20'
			){//宝石属性维护 销售销退属性
				if(Ext.getCmp('ext_b_delete')) Ext.getCmp('ext_b_delete').hide();
				if(Ext.getCmp('ext_b_cancel')) Ext.getCmp('ext_b_cancel').hide();
			}
			
			//自动窗口最大化
			if (window.screen) {              //判断浏览器是否支持window.screen判断浏览器是否支持screen
		    	var myw = screen.availWidth;   //定义一个myw，接受到当前全屏的宽
		    	var myh = screen.availHeight;  //定义一个myw，接受到当前全屏的高
		    	window.moveTo(0, 0);           //把window放在左上脚
		    	window.resizeTo(myw, myh);     //把当前窗体的长宽跳转为myw和myh
		    }
			
			
			//店长角色
			<rs:permission action="7" resource="BUSSENV.BUSSENV.SECURITY.ROLE.ZBROLE.ROLE1,BUSSENV.BUSSENV.SECURITY.ROLE.ZBROLE.ROLE3">
			</rs:permission>
			if ('${param.lsh}'==''){
			
				$.getJSON("http://42.120.40.204:83/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETFCLIENTBYUSER&sr_participant=<rs:logininfo />", 
				 function(jsonx){
				  if(jsonx!=null){
				  	var obj=$('table#8a606025a84f11e19b54fb13b166e993_').find('#column8');
				  	obj.empty();
				  	$.each(jsonx, function(ii,itemx){
				  		//分销商
				  		obj.append('<option value=\"'+itemx.fclientcode+'\">'+itemx.fclientname+'</option>');
				  	});
				  }
				});
				//售货员
				$('table#8a606025a84f11e19b54fb13b166e993_').find('#column10').val('<rs:logininfo />');
			}//end if
			
			
		});
		
		function _import(url){
		    var lsh = document.getElementById('lsh').value;
			if (lsh!='') window.open(url+'&parentid='+lsh);
			else {alert('请先执行保存操作，再导入！');}
		}
		</script>
		<title>${htmltitleinfo}</title>
	</head>
	<body>
		<!--  
		<div region="west" title="左边栏" style="width:150px;">
			<BR>
			内容
		</div>
		-->
		<input type="hidden" id="runtimeid" name="runtimeid" value="${param.runtimeid}"/>
		<input type="hidden" id="ext" name="ext" value="${param.ext}"/>
		<input type="hidden" id="workcode" name="workcode" value="${param.workcode}"/>
		<input type="hidden" id="htmlcache" name="htmlcache" value="" />
		<input type="hidden" id="chooseresult" name="chooseresult" value="${param.chooseresult}">
		<!-- 动态表单 start-->
		${form}
		<!-- 动态表单 end-->
				
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>
