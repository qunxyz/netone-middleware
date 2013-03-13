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
		
		function $todo(thisObj,formcode,jsonStr,lsh){
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
				lsh : lsh,
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
		function $todo2(thisObj,formcode,jsonStr){
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
			var json___ = Ext.util.JSON2.encode(w);
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
						 
						  	$(this).find('tr.table_tr_content').each(function(){jsonStr=$todo($(this),formcode,jsonStr,null);});
						  	//集成展示 子表单

							var ids = jQuery("table#"+formcode).jqGrid('getDataIDs');   
						    for(var i=0;i < ids.length;i++){   
						        var cl = ids[i];   
						        $(this).each(function(){jsonStr=$todo($('#'+cl),formcode,jsonStr,cl);});
						    }

						  $(this).filter(".table_form").each(function(){jsonStr=$todo($(this),formcode,jsonStr,null);});
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
					//alert(jsonStr);
				    Ext.Ajax.request({
				        url: "<c:url value='/frame.do?method=onSavaOrUpdate' />",
				        // 请求的服务器地址
				        form: '_FRAME_FORM_ID_',
				        // 指定要提交的表单id
				        method: 'POST',
				        isUpload: false,
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
									$.getJSON('/scm/reportx18.do?method=query&lsh='+result.lsh, 
									 function(jsonx){
									});
									**/
									//window.open('/scm/reportx18.do?method=query&lsh='+result.lsh);
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
			
				$.getJSON("/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETFCLIENTBYUSER&sr_participant=<rs:logininfo />", 
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
				//制单人
				var ope = $('table#8a606025a84f11e19b54fb13b166e993_').find('#column5');
				if(ope.val()=='') ope.val('<rs:logininfo />');
				//售货员
				//$('table#8a606025a84f11e19b54fb13b166e993_').find('#column10').val('<rs:logininfo />');
			}//end if

//添加计算按钮		
			$('#1dde2f9fa81711e19b54fb13b166e993_btn').find('.table_td_title').append('<input class="btn" type="button" value="计算" onclick="tototodo();" /> ');
			
			//手工费计算

			$("table#1dde2f9fa81711e19b54fb13b166e993_").find('input').live('change',function(){ 
			var ooo= $(this);			
			/*** 销售*/ 				
			var jsonStr1___ = '';
			var ids1 = jQuery("table#1dde2f9fa81711e19b54fb13b166e993_").jqGrid('getDataIDs');   
			var split1='';
		    for(var i=0;i < ids1.length;i++){   
		        var cl = ids1[i];   
		        jsonStr1___+=split1+$todo2($('#'+cl),'1dde2f9fa81711e19b54fb13b166e993_','');
				split1=',';
		    }

			
			/***  回收2　*/
			var jsonStr2__2 = '';
			var ids = jQuery("table#e17cb211a84911e19b54fb13b166e993_").jqGrid('getDataIDs');   
			var split='';
		    for(var i=0;i < ids.length;i++){   
		        var cl = ids[i];   
		        jsonStr2__2+=split+$todo2($('#'+cl),'e17cb211a84911e19b54fb13b166e993_','');
				split=',';
		    }
			
			jsonStr1___ = '[' + jsonStr1___ + ']';
			jsonStr2__2 = '[' + jsonStr2__2 + ']';			

/**
			Ext.Ajax.request({
							        url: "/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETSHOUGONGFEI",
							        // 请求的服务器地址
							        //form: '_FRAME_FORM_ID_',
							        // 指定要提交的表单id
							        method: 'POST',
							        sync: true,
							        params: {
							            sr_selljson: jsonStr1___,
								    	sr_rejson: jsonStr2__2
							        },
							        success: function (response, options) {
							            var result = Ext.util.JSON.decode(response.responseText);
								    
							           var xx = parseFloat(result.price);
								    	if(isNaN(xx)) xx=0;
							            $('table#8b6b6947a81411e19b54fb13b166e993_').find('#column18').val(xx.toFixed(0));
								    
								    	
							        },
							        failure: function (response, options) {
							            
							        }
							    });
				**/			    
			reTodoPrice();	//修改销售  再次计算应收金额			    
							    
							    
			});
$("table#e17cb211a84911e19b54fb13b166e993_").find('#column8').live('change',function(){ 
			var ooo= $(this);
			ooo.parent().parent().find('#column12').val('');
			var iscompany = ooo.parent().parent().find('#column28').val();
			var jz = parseFloat(ooo.parent().parent().find('#column29').val());
			if (isNaN(jz)) jz=0;
			var bigcate = ooo.parent().parent().find('#column8').val()
			if ((bigcate=='dl006' && iscompany==0) ||  (bigcate=='dl007') ){
			$("#damage option[value=\'"+bigcate+"\']").attr("selected", true);
			var da = parseFloat($("#damage option[value=\'"+bigcate+"\']").text());
			if (isNaN(da)) da=0;
			ooo.parent().parent().find('#column12').val(da*jz);
			} else {
			ooo.parent().parent().find('#column12').val('');
			}
});
$("table#e17cb211a84911e19b54fb13b166e993_").find('#column28').live('change',function(){ 
			var ooo= $(this);
			ooo.parent().parent().find('#column12').val('');
			var iscompany = ooo.parent().parent().find('#column28').val();
			var jz = parseFloat(ooo.parent().parent().find('#column29').val());
			if (isNaN(jz)) jz=0;
			var bigcate = ooo.parent().parent().find('#column8').val()
			if ((bigcate=='dl006' && iscompany==0) ||  (bigcate=='dl007') ){
			$("#damage option[value=\'"+bigcate+"\']").attr("selected", true);
			var da = parseFloat($("#damage option[value=\'"+bigcate+"\']").text());
			if (isNaN(da)) da=0;
			ooo.parent().parent().find('#column12').val(da*jz);
			} else {
			ooo.parent().parent().find('#column12').val('');
			}
});
$("table#e17cb211a84911e19b54fb13b166e993_").find('#column29').live('change',function(){ 
			var ooo= $(this);
			ooo.parent().parent().find('#column12').val('');
			var iscompany = ooo.parent().parent().find('#column28').val();
			var jz = parseFloat(ooo.parent().parent().find('#column29').val());
			if (isNaN(jz)) jz=0;
			var bigcate = ooo.parent().parent().find('#column8').val()
			if ((bigcate=='dl006' && iscompany==0) ||  (bigcate=='dl007') ){
			$("#damage option[value=\'"+bigcate+"\']").attr("selected", true);
			var da = parseFloat($("#damage option[value=\'"+bigcate+"\']").text());
			if (isNaN(da)) da=0;
			ooo.parent().parent().find('#column12').val(da*jz);
			} else {
			ooo.parent().parent().find('#column12').val('');
			}
});


			$("table#e17cb211a84911e19b54fb13b166e993_").find('input').live('change',function(){ 
			var ooo= $(this);
			/*** 销售*/ 				
			var jsonStr1___ = '';
			/**
			$("table#1dde2f9fa81711e19b54fb13b166e993_").each(function(){   
			 jsonStr1___+=$todo2($(this),'1dde2f9fa81711e19b54fb13b166e993_',jsonStr1___);
			});
			**/ 
			var ids1 = jQuery("table#1dde2f9fa81711e19b54fb13b166e993_").jqGrid('getDataIDs');   
			var split1='';
		    for(var i=0;i < ids1.length;i++){   
		        var cl = ids1[i];   
		        jsonStr1___+=split1+$todo2($('#'+cl),'1dde2f9fa81711e19b54fb13b166e993_','');
				split1=',';
		    }

			/***  回收　*/
			//var jsonStr2__ = '';
			//$("table#e17cb211a84911e19b54fb13b166e993_").each(function(){   
			// jsonStr2__=$todo2($(this).parent().parent(),'e17cb211a84911e19b54fb13b166e993_',jsonStr2__);
			//});
			/***  回收2　*/
			var jsonStr2__2 = '';
			/**
			$("table#e17cb211a84911e19b54fb13b166e993_").each(function(){   
			 jsonStr2__2+=$todo2($(this),'e17cb211a84911e19b54fb13b166e993_',jsonStr2__2)
			});
			**/
			var ids = jQuery("table#e17cb211a84911e19b54fb13b166e993_").jqGrid('getDataIDs');   
			var split='';
		    for(var i=0;i < ids.length;i++){   
		        var cl = ids[i];   
		        jsonStr2__2+=split+$todo2($('#'+cl),'e17cb211a84911e19b54fb13b166e993_','');
				split=',';
		    }
			
			jsonStr1___ = '[' + jsonStr1___ + ']';
			jsonStr2__2 = '[' + jsonStr2__2 + ']';			
			
			Ext.Ajax.request({
							        url: "/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETSHOUGONGFEI",
							        // 请求的服务器地址
							        //form: '_FRAME_FORM_ID_',
							        // 指定要提交的表单id
							        method: 'POST',
							        sync: true,
							        params: {
							            sr_selljson: jsonStr1___,
								    	sr_rejson: jsonStr2__2
							        },
							        success: function (response, options) {
							            var result = Ext.util.JSON.decode(response.responseText);
								    var xx = parseFloat(result.price);
								    if(isNaN(xx)) xx=0;
							            $('table#8b6b6947a81411e19b54fb13b166e993_').find('#column18').val(xx.toFixed(0));
								    
								    var reprice = parseFloat(result.reprice);//工费金额
								    if(isNaN(reprice)) reprice=0;
								    ooo.parent().parent().find('#column21').val(reprice.toFixed(2));
								    
								    var xx2=parseFloat(result.rejgStr);
								    if(isNaN(xx2)) xx2=0;
								    ooo.parent().parent().find('#column26').val(xx2.toFixed(3));//写净重
								    
							        },
							        failure: function (response, options) {
							            
							        }
							    });
					var p1 = parseFloat(ooo.parent().parent().find('#column20').val());
					if (isNaN(p1)) p1=0;
					var p2 = parseFloat(ooo.parent().parent().find('#column26').val());
					if (isNaN(p2)) p2=0;
					//ooo.parent().parent().find('#column21').val((p1*p2).toFixed(2));
			
			});
			
			
			//破损
			$.getJSON("/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETDAMAGE", 
				 function(jsonx){
				  if(jsonx!=null){
				  	var obj=$('#damage');
				  	$.each(jsonx, function(ii,itemx){
				  		//破损率
				  		obj.append('<option value=\"'+itemx.column3+'\">'+itemx.column4+'</option>');
				  	});
				  }
				});
			
			
			
		});
		
		function tototodo(){
			$("table#1dde2f9fa81711e19b54fb13b166e993_").find('#column3').each(function(){ 
				//$(this).trigger('change');
				$(this).autocomplete('http://192.168.1.107:83/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETPRODUCTBYFXJH'+'&sr_clientid='+$('table#8a606025a84f11e19b54fb13b166e993_').find('#column8').val(), {  multiple: false,  dataType: "json",  autoFill: true,  mustMatch: true,  matchContains: false,  scrollHeight: 220,  width:300,  parse: function(data) {   return $.map(data, function(row) {    return {     data: row,     value: row.column4,     result: row.column4    }   });  },  formatItem: function(item) {   return  item.column4+' '+item.column7;  },  formatResult: function(item) {   return item.column4;  }  }) .result(function(e, item) {      var o = $(this).parent().parent();      o.find('#column36').empty();    o.find('#column36').append("<option value=\"01\">积分</option>");    o.find('#column36').append("<option value=\"00\">不积分</option>");         if (item!=null){        $.getJSON("http://192.168.1.107:83/scm/Soasvl?datatype=json&sr_pcodecol=column3&naturalname=SOASCRIPT.SOASCRIPT.ZB.CHECKPRODUCTCODEISREPEAT&sr_lsh="+$('#lsh').val()+'&sr_table=DY_371337952339238'+'&sr_pcode='+$(this).val(),        function(jsonx2){         if (jsonx2!=null){         if (jsonx2.count>0){     Ext.ux.Toast.msg("", "条形码已存在！");     return;    }   }   });  /*** 产品大类 */o.find('#column19').val(item.bigcate);    o.find('#column19_tmp').val(item.bigcate);   /***精品工费***/ o.find('#column6').val(item.column89); /***工费单价***/ o.find('#column9').val(item.column59);     /** 产品名称  */  o.find('#column4').val(item.column7);     o.find('#column25').val(item.column27);    o.find('#column26').val(item.jinzhong);         /**售价  */  o.find('#column11').val(item.sellprice);       if(item.bigcate=='dl006' || item.bigcate=='dl007'){tempzk=98;}   o.find('#column28').val(item.column52);cs=o.find('#column28');o.find('#column28_tmp').val(item.column52);  if(item.bigcate=='dl006' || item.bigcate=='dl007' || item.bigcate=='dl013'){     o.find('#column33').attr("disabled","disabled");    }     /**款号 */  o.find('#column20').val(item.column11);      /** 证书号 */  o.find('#column21').val(item.column12);    /** 主石重 */  o.find('#column24').val(item.column37);    /** 副石数量 */  o.find('#column37').val(item.column96);    /** 寓意 */  o.find('#column29').val(item.column20);    /** 手寸 */  o.find('#column30').val(item.column24);    /** 颜色 */  o.find('#column31').val(item.column16);    /** 证书号 */  o.find('#column32').val(item.column17);     o.find('#column23').val(item.huohao);     o.find('#column27').val(item.zhushihao);      /***折扣信息**/  $.getJSON("http://192.168.1.107:83/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETSPECIALOFFERSINFOBYLEVEL&q="+$('table#8a606025a84f11e19b54fb13b166e993_').find('#column17').val(),    function(jsonx2){        if (jsonx2!=null){          var vs1 = Number(jsonx2.column10);          if(isNaN(vs1)){      vs1=0;    }         o.find('#column12').val(tempzk+'%');           var vs2 = Number(jsonx2.column9);          if(isNaN(vs2)){      vs2=10;    }          o.find('#column13').val((vs2/10*100)+'%');       o.find('#column14').val((vs2/10*tempzk)+'%');      o.find('#column15').val(((vs2*tempzk/100/10)*item.sellprice).toFixed(2));      $.getJSON("http://192.168.1.107:83/scm/dyinfo? lsh=&model=0&ext=&formcode=c3a1be02978f11e19d04e1f804e88ddd_&fatherlsh=ed05832cfb3346949deda75659f144a9",   function(json){  $.each(json, function(i,item_1){          if(item_1.column4==item.bigcate){              o.find('#column35').val(((vs2*tempzk/100/10)*item.sellprice*item_1.column5).toFixed(2));          }        });      });    } else {                 o.find('#column12').val(tempzk+'%');    o.find('#column13').val('100%'); o.find('#column14').val(tempzk+'%');o.find('#column15').val((tempzk/100*item.sellprice).toFixed(2));$.getJSON("http://192.168.1.107:83/scm/dyinfo? lsh=&model=0&ext=&formcode=c3a1be02978f11e19d04e1f804e88ddd_&fatherlsh=ed05832cfb3346949deda75659f144a9",   function(json){  $.each(json, function(i,item_1){   if(item_1.column4==item.bigcate){      o.find('#column35').val((tempzk/100*item.sellprice*item_1.column5).toFixed(2));   }  });  });}  }); } else {   o.find('#column3').val('');    o.find('#column4').val('');    o.find('#column5').val(0);        o.find('#column7').val(0);    o.find('#column9').val(0);    o.find('#column10').val(0);    o.find('#column11').val(0);    o.find('#column12').val(tempzk+'%');    o.find('#column13').val('100%');    o.find('#column14').val(tempzk+'%');    o.find('#column15').val(0);    o.find('#column16').val('');    o.find('#column19').val('');    o.find('#column20').val('');    o.find('#column21').val('');    o.find('#column22').val('');    o.find('#column23').val('');    o.find('#column24').val(0);    o.find('#column26').val(0);    o.find('#column27').val('');    o.find('#column28').val('');    o.find('#column29').val('');    o.find('#column30').val('');    o.find('#column31').val('');    o.find('#column32').val('');    o.find('#column33').val(0);    o.find('#column34').val(0);    o.find('#column35').val(0);    o.find('#column36').val('');    o.find('#column37').val(0);  } });$(this).unbind("focus");setTimeout(function(){$('table#8b6b6947a81411e19b54fb13b166e993_').find('#column3').trigger('change');try{cs.trigger('change');}catch(e){}}, 1000);
			}); 
			reTodoPrice();
		}
		
		//修改销售  再次计算应收金额
		function reTodoPrice(){
		
			/*** 销售*/ 				
			var jsonStr1___ = '';
			/**
			$("table#1dde2f9fa81711e19b54fb13b166e993_").each(function(){   
			 jsonStr1___+=$todo2($(this),'1dde2f9fa81711e19b54fb13b166e993_',jsonStr1___);
			}); 
			**/
			var ids1 = jQuery("table#1dde2f9fa81711e19b54fb13b166e993_").jqGrid('getDataIDs');   
			var split1='';
		    for(var i=0;i < ids1.length;i++){   
		        var cl = ids1[i];   
		        jsonStr1___+=split1+$todo2($('#'+cl),'1dde2f9fa81711e19b54fb13b166e993_','');
				split1=',';
		    }
			
			
			/***  回收　*/
			//var jsonStr2__ = '';
			//$("table#e17cb211a84911e19b54fb13b166e993_").each(function(){   
			// jsonStr2__=$todo2($(this).parent().parent(),'e17cb211a84911e19b54fb13b166e993_',jsonStr2__);
			//});
			/***  回收2　*/
			var jsonStr2__2 = '';
			/**
			$("table#e17cb211a84911e19b54fb13b166e993_").each(function(){   
			 jsonStr2__2+=$todo2($(this),'e17cb211a84911e19b54fb13b166e993_',jsonStr2__2)
			});
			**/
			var ids = jQuery("table#e17cb211a84911e19b54fb13b166e993_").jqGrid('getDataIDs');   
			var split='';
		    for(var i=0;i < ids.length;i++){   
		        var cl = ids[i];   
		        jsonStr2__2+=split+$todo2($('#'+cl),'e17cb211a84911e19b54fb13b166e993_','');
				split=',';
		    }
			
			jsonStr1___ = '[' + jsonStr1___ + ']';
			jsonStr2__2 = '[' + jsonStr2__2 + ']';			
			
			Ext.Ajax.request({
							        url: "/scm/Soasvl?datatype=json&naturalname=SOASCRIPT.SOASCRIPT.ZB.GETSHOUGONGFEI",
							        // 请求的服务器地址
							        //form: '_FRAME_FORM_ID_',
							        // 指定要提交的表单id
							        method: 'POST',
							        sync: true,
							        params: {
							            sr_selljson: jsonStr1___,
								    	sr_rejson: jsonStr2__2
							        },
							        success: function (response, options) {
							            var result = Ext.util.JSON.decode(response.responseText);
								    var xx = parseFloat(result.price);
								    if(isNaN(xx)) xx=0;
							            $('table#8b6b6947a81411e19b54fb13b166e993_').find('#column18').val(xx.toFixed(0));
								    
							        },
							        failure: function (response, options) {
							            
							        }
							    });
		}
		

		function todojson(formcode){
			var jsonStr2__2 = '';
			$("table#"+formcode+"").each(function(){   
			 jsonStr2__2+=$todo($(this),formcode,jsonStr2__2);
			});
			return jsonStr2__2;
		}
		
		
		function _import(url){
		    var lsh = document.getElementById('lsh').value;
			if (lsh!='') window.open(url+'&parentid='+lsh);
			else {alert('请先执行保存操作，再导入！');}
		}
		
		function $buildNullData(formcode){
		 var html = $.ajax({
		  url: "<%=path%>/frame.do?method=buildNullData&url=${param.url}&formcode="+formcode,
		  async: false,cache:false
		 }).responseText;
		 return eval("("+html+")");
		}
		</script>
		<title>${htmltitleinfo}</title>
	</head>
	<body>
		<select id="damage" style="display: none">
			<option value=""></option>
		</select>
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
