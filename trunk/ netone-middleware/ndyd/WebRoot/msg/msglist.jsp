<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-dialog.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp"/>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<link type="text/css" href="<%=path%>/msg/msg.css" rel="stylesheet" />	
		<script type="text/javascript">
		var arrPerson=new Array();
		var arrPerson2=new Array();
		var arrPersonAll=new Array();
		var arrFile=new Array();
		/**
		* 从对象数组中删除属性为objPropery，值为objValue元素的对象
		* @param Array arrPerson 数组对象
		* @param String objPropery 对象的属性
		* @param String objPropery 对象的值
		* @return Array 过滤后数组
		*/
		function remove(arrPerson,objPropery,objValue)
		{
		   return $.grep(arrPerson, function(cur,i){
		          return cur[objPropery]!=objValue;
		       });
		}
		/**
		* 从对象数组中获取属性为objPropery，值为objValue元素的对象
		* @param Array arrPerson 数组对象
		* @param String objPropery 对象的属性
		* @param String objPropery 对象的值
		* @return Array 过滤后的数组
		*/
		function get(arrPerson,objPropery,objValue)
		{
		   return $.grep(arrPerson, function(cur,i){
		          return cur[objPropery]==objValue;
		       });
		}
		
		function $deleteMsgone(unid){
				var IDSTR='';
				var msgTip = Ext.MessageBox.show({
					title:'系统提示',
					width : 250,
					msg:'正在删除,请稍后......'
				});
				if(unid!="undefined"&&unid!=null&&unid.trim()!=""){
				  	Ext.Ajax.request({
							url:"<%=request.getContextPath()%>/file.do?method=delete"+"&unid="+unid,//请求的服务器地址
							method:'POST',
							success : function(response,options){
								msgTip.hide();
								var result = Ext.util.JSON.decode(response.responseText);
								if (result.error != null) {
					                Ext.MessageBox.alert('提示', result.tip);
					            } else {
			            			$('#f_type'+IDSTR).val('');
			            			$('#filename'+IDSTR).val('');
			            			$('#filetext'+unid).html('');
			            			arrFile=remove(arrFile,'name',unid);
					                Ext.ux.Toast.msg("", result.tip);
					            }
							},
							failure : function(response,options){
								msgTip.hide();
								checkAjaxStatus(response);
							}
					});
				}else{
					Ext.MessageBox.alert('提示','对不起，您必须先选中一个文件才能够执行相应的操作！');
					return;
				}
		}

		function $uploadMsgone(f_type){//上传
		    var id='';
			var filepath = document.getElementById("files"+id).value; 
			if(filepath==""){
				Ext.MessageBox.alert('提示', '附加文件为空!');
				return;
			}
			if (f_type=='image'){
				if (filepath.match(/.+\.png$|.+\.gif$|.+\.jpg$|.+\.bmp$/ig)) {
				} else {
					alert('对不起，你选择的图片格式不对\n图片格式应为*.png、*.jpg、*.gif、*.bmp');
			  	    return;
				}
		    }
			if (f_type=='file'){
				if (filepath.match(/.+\.exe$|.+\.bat$|.+\.com$|.+\.pif$|.+\.vbs$|.+\.js$|.+\.inf$|.+\.vb$|.+\.hta$/ig)) {
					Ext.MessageBox.alert('警告提示','由于可能会带来安全隐患，不能上传可执行类型的文件！');
					filepath.outerHTML=filepath.outerHTML;  //请空文件选择内容
					return ;
				}
			}
			
			var d_unid = '';;
			var filename = encodeURI(encodeURI(document.getElementById("filename"+id).value));
			var filenamestr=document.getElementById("filename"+id).value;
			var msgTip = Ext.MessageBox.show({
					        title: '提示',
					        width: 250,
					        closable:false,
					        msg: '正在保存信息请稍候......'
					    });
			 $.ajaxFileUpload({
	              url:"<%=request.getContextPath()%>/file.do?method=onUploadFile&ext=files"+id+"&id="+d_unid+"&filename="+filename+"&f_type="+f_type,
	              secureuri:false,
	              fileElementId:'files'+id,//与页面处理代码中file相对应的ID值
	              dataType: 'text',//返回数据类型:text，xml，json，html,scritp,jsonp五种
	              success: function (data, status){
	              		msgTip.hide();
						if (data==null || data==''){
							Ext.MessageBox.alert('提示', '上传文件失败');
						} else {
							var str = data;
							var arr= str.split('(~|~|~)');
		            			$('#f_type'+id).val(f_type);
		            			$('#filename'+id).val(filenamestr);
		            			//var btn = '<input type="button" onclick="$deleteone(\''+arr[0]+'\',\''+id+'\')" value="删除">';
		            			var btn = '<a class="" onclick="$deleteMsgone(\''+arr[0]+'\')" title="删除" href="javascript:void(0);">[删除]</a>';
		            			$('#filetext'+id).html($('#filetext'+id).html()+'<span id="filetext'+arr[0]+'">'+filenamestr+btn+'</span>');
		            			$('#filetext'+id).show();
		            			
		            			var f=new Object();
								f.name=arr[0];
								arrFile.push(f);
							Ext.ux.Toast.msg("", '上传附件成功');
						}
	              },
	              error: function (data, status, e){
	              	  msgTip.hide();
	                  Ext.MessageBox.alert('提示', '上传附件失败');
	              }
	          });
		}
		
		function $removeallMemeber(){
			arrPerson = new Array();
			$('#msg').val();
			$('#memebertabs').find('input').each(function(){
				  	$(this).attr('value','添加');
		    });
		}
		
		function $removeallMemeber2(){
			arrPerson2 = new Array();
			$('#msg2').val();
			$('#memebertabs2').find('input').each(function(){
				  	$(this).attr('value','添加');
		    });
		}
		
		$(function() {
		    $("#memebertabs").tabs();
		    $('#memebertabs').find('input').each(function(){
		    	$(this).toggle(
				  function(){
				  	//$(this).attr('disabled',true);
				  	//alert('已添加');
				  	var person=new Object();
					person.name=$(this).attr('v');
				  	arrPerson.push(person);
				  	$(this).attr('value','取消添加');
				  },
				  function(){
				  	//$(this).attr('disabled',false);
				  	//alert('取消添加');
				  	arrPerson=remove(arrPerson,'name',$(this).attr('v'));
				  	$(this).attr('value','添加');
				  }
				);
		    });
		    
		    $("#memebertabs2").tabs();
		    $('#memebertabs2').find('input').each(function(){
		    	$(this).toggle(
				  function(){
				  	//$(this).attr('disabled',true);
				  	//alert('已添加');
				  	var person=new Object();
					person.name=$(this).attr('v');
				  	arrPerson2.push(person);
				  	$(this).attr('value','取消添加');
				  },
				  function(){
				  	//$(this).attr('disabled',false);
				  	//alert('取消添加');
				  	arrPerson2=remove(arrPerson2,'name',$(this).attr('v'));
				  	$(this).attr('value','添加');
				  }
				);
		    });
		});
		
		function openForwardWin(uuid){
			$("#forwardDialog").show();
		    $('#forwardDialog').dialog({
				title : '转发',
				'bgiframe': true,
				'modal': true,
				resizable: false,
				draggable: true,
				width: 400,
				minHeight: 250,
				position: 'center',
				buttons: {
				"关闭": function() {
		          $( this ).dialog( "close" );
		        },
		        "转发": function() {
		        	$forword(uuid);
		            //$( this ).dialog( "close" );
		        }
		        
		       },
				beforeclose: function(event, ui) {
					//alert('close')
				},
				close: function(event, ui) {
					$('#forwardDialog').dialog("destroy");
				}
			});
		    
		}
		
		function openCommentWin(uuid){
			$("#commentDialog").show();
		    $('#commentDialog').dialog({
				title : '评论',
				'bgiframe': true,
				'modal': true,
				resizable: false,
				draggable: true,
				width: 400,
				height: 200,
				position: 'center',
				buttons: {
					"关闭": function() {
			          $( this ).dialog( "close" );
			        },
			        "评论": function() {
			        	$comment(uuid);
			            //$( this ).dialog( "close" );
			        }
		        },
				beforeclose: function(event, ui) {
					//alert('close')
				},
				close: function(event, ui) {
					$('#commentDialog').dialog("destroy");
				}
			});
		    
		}
		
		function openMemeberSelectWin(){
			$("#memberDialog").show();
		    $('#memberDialog').dialog({
				title : '选择',
				'bgiframe': true,
				'modal': true,
				resizable: false,
				draggable: true,
				width: 400,
				autoheight: true,
				position: 'center',
				buttons: {
					"关闭": function() {
			          $( this ).dialog( "close" );
			        },
			        "选择": function() {
			            var split = '';
			            var str = '';
			            $.each(arrPerson,function(key,val){  
						    str+=split+val.name;
						    split = ',';
						});
						$('#msgto').val(str);
						$( this ).dialog( "close" );
			        }
		        },
				beforeclose: function(event, ui) {
					//alert('close')
				},
				close: function(event, ui) {
					$('#memberDialog').dialog("destroy");
				}
			});
		    
		}
		
		function $publish(){
			if ($('#msgbody').val()==''){
				alert('请填写消息内容！');
				$('#msgbody').focus();
				return;
			}
			var split = '';
            var filestr = '';
            $.each(arrFile,function(key,val){  
			    filestr+=split+val.name;
			    split = ',';
			});
			
			
			if ($('#msgto').val()==''){
				var split0 = '';
	            var filestr0 = '';
	            $.each(arrPersonAll,function(key,val){  
				    filestr0+=split0+val.name;
				    split0 = ',';
				});
				$('#msgto').val(filestr0);
			}
			
			var msgTip = Ext.MessageBox.show({
				        title: '提示',
				        width: 250,
				        closable:false,
				        msg: '正在保存信息请稍候......'
				    });
			    Ext.Ajax.request({
				        url: "<c:url value='/msg.do?method=newMsg' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        sync: false,
				        params: {
				            msgto:$('#msgto').val(),
				            //msgto:val.name,
				            msgbody:$('#msgbody').val(),
				            comment:1,
				            fileids:filestr
				        },
				        success: function (response, options) {
				        	msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	Ext.ux.Toast.msg("", "发布成功");
				            	$removeallMemeber();
				            	//window.location.reload();
								$('#msgbody').val('');
								$('#msgto').val('');
								Ext.Ajax.request({
							        url: "<c:url value='/msg.do?method=msgdata&type=${param.type}' />",
							        // 请求的服务器地址
							        // 指定要提交的表单id
							        method: 'POST',
							        sync: false,
							        success: function (response, options) {
							        	$('#talkList').html(response.responseText);
							        }
						        });
				            }
				        },
				        failure: function (response, options) {
				        	msgTip.hide();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			arrPerson = new Array();
		}
		
		function $forword(unid){
			if ($('#msgbody2').val()==''){
				alert('请填写消息内容！');
				$('#msgbody2').focus();
				return;
			}
			
			var split = '';
            var str = '';
            $.each(arrPerson2,function(key,val){  
			    str+=split+val.name;
			    split = ',';
			});
			$('#msgto2').val(str);
			if ($('#msgto2').val()=='' || $('#msgto2').val()=='undefined'){
				alert('请选择转发人员！');
				return;
			} 
			
			var msgTip = Ext.MessageBox.show({
				        title: '提示',
				        width: 250,
				        closable:false,
				        msg: '正在保存信息请稍候......'
				    });
			    Ext.Ajax.request({
				        url: "<c:url value='/msg.do?method=forwardMsg' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        sync: false,
				        params: {
				            msgto:$('#msgto2').val(),
				            //msgto:val.name,
				            msgbody:$('#msgbody2').val(),
				            source:unid
				        },
				        success: function (response, options) {
				        	msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
							
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	Ext.ux.Toast.msg("", "转发成功");
				            	$('#msgbody2').val('');
								$('#msgto2').val('');
								$removeallMemeber2();
				            }
				            $('#forwardDialog').dialog("close");
				        },
				        failure: function (response, options) {
				        	msgTip.hide();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			arrPerson2 = new Array();
		}
		
		function $del(unid){
			var type = '${param.type}';
			if (type=='05'){
				$delComment(unid);
			} else {
				$delMsg(unid);
			}
		}
		
		function $delMsg(unid){
			    Ext.Ajax.request({
				        url: "<c:url value='/msg.do?method=delMsg' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        sync: false,
				        params: {
				           lsh:unid
				        },
				        success: function (response, options) {
				            var result = Ext.util.JSON.decode(response.responseText);
				
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	Ext.ux.Toast.msg("", "删除消息成功");
				            }
				            Ext.Ajax.request({
							        url: "<c:url value='/msg.do?method=msgdata&type=${param.type}' />",
							        // 请求的服务器地址
							        // 指定要提交的表单id
							        method: 'POST',
							        sync: false,
							        success: function (response, options) {
							        	$('#talkList').html(response.responseText);
							        }
						        });
				        },
				        failure: function (response, options) {
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
		}
		
		function $delComment(unid){
			    Ext.Ajax.request({
				        url: "<c:url value='/msg.do?method=delComment' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        sync: false,
				        params: {
				           lsh:unid
				        },
				        success: function (response, options) {
				            var result = Ext.util.JSON.decode(response.responseText);
				
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	Ext.ux.Toast.msg("", "删除评论成功");
				            }
				            Ext.Ajax.request({
							        url: "<c:url value='/msg.do?method=msgdata&type=${param.type}' />",
							        // 请求的服务器地址
							        // 指定要提交的表单id
							        method: 'POST',
							        sync: false,
							        success: function (response, options) {
							        	$('#talkList').html(response.responseText);
							        }
						        });
				        },
				        failure: function (response, options) {
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
		}
		
		function $comment(unid){
			if ($('#msgbody3').val()==''){
				alert('请填写消息内容！');
				$('#msgbody3').focus();
				return;
			}
			
			var msgTip = Ext.MessageBox.show({
				        title: '提示',
				        width: 250,
				        closable:false,
				        msg: '正在保存信息请稍候......'
				    });
			    Ext.Ajax.request({
				        url: "<c:url value='/msg.do?method=commentMsg' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        sync: false,
				        params: {
				            msgbody:$('#msgbody3').val(),
				            source:unid
				        },
				        success: function (response, options) {
				        	msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	Ext.ux.Toast.msg("", "评论成功");
				            }
				            $('#msgbody3').val('');
				            $('#commentDialog').dialog("close");
				        },
				        failure: function (response, options) {
				        	msgTip.hide();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
		}
		
		function $uploadWin(type){
			$("#fileDialog").show();
		    $('#fileDialog').dialog({
				title : '上传',
				'bgiframe': true,
				'modal': true,
				resizable: false,
				draggable: true,
				width: 400,
				autoheight: true,
				position: 'center',
				buttons: {
					"关闭": function() {
			          $( this ).dialog( "close" );
			        },
			        "上传": function() {
			            var s=$uploadMsgone(type);
						$( this ).dialog( "close" );
			        }
		        },
				beforeclose: function(event, ui) {
					//alert('close')
				},
				close: function(event, ui) {
					$('#fileDialog').dialog("destroy");
				}
			});
		    
		}
		</script>
		<title>消息</title>
	</head>
	<body>
	<input type="hidden" id="sourceid" name="sourceid" />
	<div id="fileDialog" style="display: none">
		<span id="fileform">
			<input type="file" id="files" name="files" onchange="$getFileFullName('');" />
			<input type="hidden" id="filename" name="filename" />
			<input type="hidden" id="f_type" name="f_type" />
		</span>
	</div>
	
	<div id="memberDialog" style="display: none">
		<div id="memebertabs">
			  <ul>
			  <c:forEach var="list" items="${memeberlist}" varStatus="s">
				<li>
			    <li id="${list.groupid}"><a href="#tabs-${s.index}">${list.groupname}</a></li>
			  </c:forEach>  
			  </ul>
			  <c:forEach var="list" items="${memeberlist}" varStatus="s">
			  <div id="tabs-${s.index}">
			    <ul>
				<c:forEach var="list2" items="${list.people}">
					<li>${list2.name}<input type="button" value="添加" v="${list2.id}" /> </li>
				</c:forEach>
				</ul>
			  </div>
			  </c:forEach>
		</div>
	</div>
	
	
	<div id="forwardDialog" style="display: none">
		<input type="hidden"  style="width: 80px;" id="msgto2" name="msgto2" title="请选择人员/组织" />
		<textarea id="msgbody2" name="msgbody2" title="输入框"></textarea>
	  	<div id="memebertabs2">
			  <ul>
			  <c:forEach var="listx" items="${memeberlist}" varStatus="s">
				<li>
			    <li id="${listx.groupid}"><a href="#xtabs-${s.index}">${listx.groupname}</a></li>
			  </c:forEach>  
			  </ul>
			  <c:forEach var="listx" items="${memeberlist}" varStatus="s">
			  <div id="xtabs-${s.index}">
			    <ul>
				<c:forEach var="listx2" items="${listx.people}">
					<script>
					var person0=new Object();
					person0.name='${listx2.id}';
				  	arrPersonAll.push(person0);
					</script>
					<li>${listx2.name}<input type="button" value="添加" v="${listx2.id}" /> </li>
				</c:forEach>
				</ul>
			  </div>
			  </c:forEach>
		</div>
	</div>
	
	<div id="commentDialog" style="display: none">
	  <textarea id="msgbody3" name="msgbody3" title="输入框"></textarea>
	</div>
	
	<div class="W_miniblog"><div class="W_miniblog_fb">
	
	<div class="W_main" >
		<div style="width:700px;float: left;">
		
			<c:if test="${param.type=='01' || param.type=='03' || empty param.type}">
				<div class="send_weibo clearfix " node-type="wrap" >
				
					<div node-type="wrap" class="send_weibo clearfix ">
					  <div class="title_area clearfix">
					    <div node-type="publishTitle" class="title">有什么新鲜事想告诉大家？</div></div>
					  </div>
					  <div node-type="textElDiv" class="input">
					    <textarea id="msgbody" name="msgbody" title="输入框"></textarea>
					    <div node-type="successTip" style="display: none" class="send_succpic"></div>
					    <span class="arrow"></span>
					  </div>
					  <div class="func_area clearfix">
					    <div class="func">
					    	<input type="text"  style="width: 80px;" id="msgto" name="msgto" title="请选择人员/组织" />
					    	<a title="选择" onclick="openMemeberSelectWin()" node-type="publishBtn" diss-data="module=stissue" class="send_btn disable" href="javascript:void(0);" tabindex="2">选择</a>
					    	<a title="发布按钮" onclick="$publish();" node-type="publishBtn" diss-data="module=stissue" class="send_btn disable" href="javascript:void(0);" tabindex="2">发布</a>
					    </div>
					    <div node-data="number=4" node-type="widget" class="kind S_txt3">
						    <span class="kind_detail">
						    <a title="图片" onclick="$uploadWin('image')" href="javascript:void(0);" tabindex="3">图片</a>
						    <a title="附件" onclick="$uploadWin('file')" href="javascript:void(0);" tabindex="3">附件</a>
						    </span>
						    <div id="filetext"></div>
						</div>
						<div style="color: #999999">温馨提示：未选择人员默认发送所有人员</div>
					  </div>
			
			  </div>
			</c:if>
		
			<ul id="talkList" class="LC">
			<c:forEach var="list" items="${msglist}">
				<li>
					<div>${list.sendername}</div>
					<div>${list.context}</div>
					<div>
						<c:if test="${!empty list.atturl}">
							<a href="${list.atturl}" target="_blank" title="打开原图">
							<img width="160" height="160" src="${list.atturl}" />
							</a>
						</c:if>
						<BR>
						<c:if test="${!empty list.atturlzip}">
	                	<a href="${list.atturlzip}">[附件下载]</a>
	                	</c:if>
					</div>
					<div class="pubInfo">
	                        <span style="float: left">
	                        ${list.timex}
	                        </span>
	                        <div  style="float: right">
	                        <c:if test="${param.type!='05'}">
	                        <a num="0" class="relay" href="javascript:void(0)" onclick="openForwardWin('${list.lsh}')">转发</a><span>|</span>
	                        <a num="0" class="comt" href="javascript:void(0)" onclick="openCommentWin('${list.lsh}')">评论</a><span>|</span>
	                        </c:if>
	                        <c:if test="${empty param.type || param.type=='01' || param.type=='05'}">
	                        <a num="0" class="comt" href="javascript:void(0)" onclick="$del('${list.lsh}')">删除</a><span>|</span>
	                        </c:if>
	                        </div>
	                </div>
				</li>
			</c:forEach>
			</ul>
			
		</div>
		<!-- 右边 -->
		<div style="width:270px;float: right;margin-left: 6px;">
			<ul>
				<li><a href="<c:url value='/msg.do?method=msgListView' />">我的首页</a></li>
				<li><a href="<c:url value='/msg.do?method=msgListView&type=03' />">我的消息</a></li>
				<li><a href="<c:url value='/msg.do?method=msgListView&type=04' />">提到我的</a></li>
				<li><a href="<c:url value='/msg.do?method=msgListView&type=05' />">我的评论</a></li>
				<li><a href="javascript:void(0)">群组设置</a></li>
			</ul>
		</div>
		<div class="clearfix"></div>
	</div>
	</div></div>
			
	</body>
</html>
