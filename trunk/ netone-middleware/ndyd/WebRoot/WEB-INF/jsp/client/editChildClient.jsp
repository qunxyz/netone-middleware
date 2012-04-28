<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-ui-dialog.jsp" />
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/AppUtil.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>:: 编辑 分销商信息 ::</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='width: 95%; height: 400px'>
				<form id="form1" action="" method="post">
					<input type="hidden" id="clientId" name="clientId"
						value="${clientInfo.clientId}">
					<table class="main">
						<tr>
							<td class="label">
								隶属于:
							</td>
							<td colspan="3">
								<input type="text" name="belngToName" style="width: 449px"
									id="belngToName" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td class="label">
								客户编码:
							</td>
							<td>
								<input type="text" id="clientCode" name="clientCode"
									style="width: 186px" readonly="readonly"
									value="${clientInfo.clientCode}" />
							</td>
							<td class="label">
								客户名称:
							</td>
							<td>
								<input type="text" name="clientName" id="clientName"
									value="${clientInfo.clientName}" style="width: 186px" />
							</td>
						</tr>
						<tr>
							<td class="label">
								业务主任:
							</td>
							<td>
								<input type="hidden" name="operationDirector"
									id="operationDirector" value="${clientInfo.operationDirector}" />
								<input type="hidden" name="operationDirectorCode"
									id="operationDirectorCode" />
								<input type="text" name="operationDirectorName"
									style="width: 172px" id="operationDirectorName"
									readonly="readonly" />
								<input type="text" value="…"
									style="width: 10px; margin-left: -5px" class="btn"
									onclick="_choiceOperationDirector()" />
							</td>
							<td class="label">
								汇款账号:
							</td>
							<td>
								<input type="text" name="remitAccounts" style="width: 186px"
									value="${clientInfo.remitAccounts}" id="remitAccounts" />
							</td>
						</tr>
						<tr>
							<td class="label">
								客户类型:
							</td>
							<td>
								<select id="clientType" name="clientType" style="width: 110px">
									<option value="">
										---请选择---
									</option>
								</select>
								<a href="javaScript:void(0)" onclick="configClientType()">[订制]</a>&nbsp;&nbsp;<a href="javaScript:void(0)" onclick="refreshClientType()">[刷新]</a>
							</td>
							<td class="label">
								市场类型:
							</td>
							<td>
								<select id="marketType" name="marketType" style="width: 110px">
									<option value="">
										---请选择---
									</option>
								</select>
								<a href="javaScript:void(0)" onclick="configMarketType()">[订制]</a>&nbsp;&nbsp;<a href="javaScript:void(0)" onclick="refreshMarketType()">[刷新]</a>
							</td>
						</tr>
						<tr>
							<td class="label">
								履约保证金金额:
							</td>
							<td>
								<input type="text" name="lyBailMonery" style="width: 186px"
									value="${clientInfo.lyBailMonery}" id="lyBailMonery"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">
								汇款人:
							</td>
							<td>
								<input type="text" name="remitPersion" style="width: 186px"
									value="${clientInfo.remitPersion}" id="remitPersion" />
							</td>
						</tr>
						<tr>

							<td class="label">
								汇款时间:
							</td>
							<td>
								<c:if test="${clientInfo.remitTime==null}">
									<input type="text" name="remitTime" id="remitTime"
										class="Wdate" style="width: 187px"
										onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"
										value="<fmt:formatDate value='${now}' pattern="yyyy-MM-dd"  /> " />
								</c:if>
								<c:if test="${clientInfo.remitTime!=null}">
									<input type="text" name="remitTime" id="remitTime"
										class="Wdate" style="width: 186px"
										onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});"
										value="<fmt:formatDate value="${clientInfo.remitTime}" pattern="yyyy-MM-dd"  />" />
								</c:if>
							</td>
							<td class="label">
								纳税类型:
							</td>
							<td>
								<input type="text" name="ratepayingType" style="width: 186px"
									value="${clientInfo.ratepayingType}" id="ratepayingType" />
							</td>
						</tr>
						<tr>
							<td class="label">
								开户行:
							</td>
							<td>
								<input type="text" name="openAccountTitle" style="width: 186px"
									value="${clientInfo.openAccountTitle}" id="openAccountTitle" />
							</td>
							<td class="label">
								联系人:
							</td>
							<td>
								<input type="text" name="linkman" style="width: 186px"
									value="${clientInfo.linkman}" id="linkman" />
							</td>
						</tr>
						<tr>
							<td class="label">
								联系电话:
							</td>
							<td>
								<input type="text" name="telphone" style="width: 186px"
									value="${clientInfo.telphone}" id="telphone" />
							</td>
							<td class="label">
								手机号码:
							</td>
							<td>
								<input type="text" name="mobile" id="mobile"
									style="width: 186px" value="${clientInfo.mobile}" />
							</td>
						</tr>
						<tr>
							<td class="label">
								邮箱地址:
							</td>
							<td colspan="3">
								<input type="text" name="email" id="email" style="width: 186px"
									value="${clientInfo.email}" />
							</td>
						</tr>
						<tr>
							<td class="label">
								公司地址:
							</td>
							<td colspan="3">
								<input type="text" name="companyAddress" style="width: 465px"
									value="${clientInfo.companyAddress}" id="companyAddress" />
							</td>

						</tr>
						<tr>
							<td class="label">
								送达站:
							</td>

							<td colspan="3">
								<input type="text" name="servicestation" style="width: 465px"
									value="${clientInfo.servicestation}" id="servicestation" />
							</td>
						</tr>
						<tr>
							<td class="label">
								送货地址:
							</td>
							<td colspan="3">
								<input type="text" name="deliverGoodsAddress"
									style="width: 465px" value="${clientInfo.deliverGoodsAddress}"
									id="deliverGoodsAddress" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>
<script type="text/javascript">

   //创建工具栏组件
   var toolbar = new Ext.Toolbar([
 		 {text : '保存 ',iconCls:'saveIcon',handler:onSaveButton}
		 //,{text : '退出 ',iconCls:'exitIcon',handler:onExitButton}
	]);
	
   Ext.onReady(function(){
	    var panel = new Ext.Panel({
		      tbar:toolbar,
	          contentEl :'div1',//加载本地资源	        
	          applyTo :'panel',
	          border:false
	    });
		loadClientType("${clientInfo.clientType}");//客户类型
 		loadMarketType("${clientInfo.marketType}");//市场类型	    
	    var id="${clientInfo.clientId}";
		if(id!=''){//不为空时加载客户信息
			getClientInfo();
		}
		
   });
   
   function _SingleChoiceClientInfo(record){
   		if (record!=null){
	   		var yxManagerCompany = record.get('departmentName1');
	   		var sellDepartmentName = record.get('departmentName3');
	   		var belongToCode = record.get('departmentCode4');
	   		var belongToName = record.get('departmentName4');
	   		var operationDirectorCode = record.get('departmentCodex');
	   		var operationDirectorName = record.get('departmentNamex');
	   		document.getElementById("belngToName").value=belongToCode+" "+belongToName;
			document.getElementById('operationDirectorCode').value=operationDirectorCode;
			document.getElementById('operationDirectorName').value=operationDirectorName;
   		}else{
   			document.getElementById("clientCode").value="";
   		}
  }
   function onSaveButton(){//保存
   	var msgTip = Ext.MessageBox.show({
						title:'系统提示',
						width : 200,
						msg:'正在保存信息请稍后......'
	  		}); 
          if(validateForm()){
                     Ext.Ajax.request({
							url :"<c:url value='/client/client.do?method=onSaveOrUpdateClientInfo' />",//请求的服务器地址
							form : 'form1',//指定要提交的表单id
							method : 'POST',
							success : function(response,options){
									msgTip.hide();
								var result = Ext.util.JSON.decode(response.responseText);
								if(result.error!=null){
									Ext.MessageBox.alert('提示',result.tip);
								} else {
									Ext.ux.Toast.msg("", result.tip);
								}
							},
							failure : function(response,options){
								msgTip.hide();
								checkAjaxStatus(response);
								var result = Ext.util.JSON.decode(response.responseText);
								Ext.MessageBox.alert('提示',result.tip);
							}
				　});
          }
    }
    
     function validateForm(){ //弹出出错提示  验证
 		  var patrn=/(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$)/;
          var patrn1=/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
          var patrn2=/(^\d{4}-\d{7}$|^\d{4}-\d{8}$|^\d{3}-\d{8}$)/;
			var str = '保存分经销商失败!出错提示如下:<br />';
			var i=1;
			var blank = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			if(document.getElementById("clientName").value=='' || document.getElementById("clientName").value.length > 40 ){
			   str+= blank+ i+ '、此客户名称为空 或 客户名称的长度超过40个字符。<br />';
			   i++;
			}
			if(document.getElementById("operationDirector").value==''){
			   str+= blank+ i+ '、此业务主任为空。<br />';
			   i++;
			}
				if(document.getElementById("remitAccounts").value==''){
			   str+= blank+ i+ '、此汇款账号为空。<br />';
			   i++;
			}
			if(document.getElementById("remitPersion").value==''){
			   str+= blank+ i+ '、此汇款人为空。<br />';
			   i++;
			}
			if(document.getElementById("lyBailMonery").value==''){
			   str+= blank+ i+ '、此履约保证金金额为空。<br />';
			   i++;
			}
			if(document.getElementById("remitTime").value==''){
			   str+= blank+ i+ '、此汇款时间为空。<br />';
			   i++;
			}
			if(document.getElementById("ratepayingType").value==''){
			   str+= blank+ i+ '、此纳税类型为空。<br />';
			   i++;
			}
			if(document.getElementById("openAccountTitle").value==''){
			   str+= blank+ i+ '、此开户行为空。<br />';
			   i++;
			}
			if(document.getElementById("linkman").value==''){
			   str+= blank+ i+ '、此联系人为空。<br />';
			   i++;
			}
		   if(document.getElementById("companyAddress").value==''){
			  str+= blank+ i+ '、此公司地址为空。<br />';
			  i++;
			}
			if(document.getElementById("servicestation").value==''){
			   str+= blank+ i+ '、此送达站为空。<br />';
			   i++;
			}
			if(document.getElementById("deliverGoodsAddress").value==''){
			   str+= blank+ i+ '、此送货地址为空。<br />';
			   i++;
			}
			if(document.getElementById("belngToName").value==''){
			   str+= blank+ i+ '、此分销商隶属于为空。<br />';
			   i++;
			}
			if(i > 1){
			   Ext.MessageBox.alert('错误提示',str);
			   return false;
			}
			
			return true;
		} 
    
   function onExitButton(){//退出
	  window.close();
    }
    
    function onCancelButton(){//重置
			var clientId = document.getElementById('clientId').value;        	
        	resetForm(Ext.get('pcform').dom);
        	clientId.value = '';
    }
    
    function getClientInfo(){//取得人员信息
			var clientId = clientId=Ext.get('clientId').dom.value;
			AppChoiceUtils.getSingleClientInfo('<c:url value="/client/client.do?method=onGetClientInfo" />',clientId);
	}
    /**业务主任选择**/
	function _choiceOperationDirector(){
		openModalDialogWindow('选择','<c:url value="/department/user.do?method=onSingleSelectUser"/>',450,250);
	}
	function _choiceUser(ids,values,texts){//业务主任选择
		document.getElementById('operationDirector').value=ids;
		document.getElementById('operationDirectorCode').value=values;
		document.getElementById('operationDirectorName').value=texts;
	}

	//客户类型的配制
	function configClientType(){
		openModalDialogWindow('新增客户类型','<c:url value='/products/systemConfig.do?method=onMainView' />'+'&type=clientType', 400,250);
	}
	//客户类型的刷新
	 function refreshClientType(){
        var  clientTypeSelect = document.getElementById('clientType').options;
	 
	    clientTypeSelect.length=0;
		loadClientType("${clientInfo.clientType}");//客户类型
	}
	//市场类型的配制
	function configMarketType(){
		openModalDialogWindow('新增市场类型','<c:url value='/products/systemConfig.do?method=onMainView' />'+'&type=marketType', 400,250);
	}
	//客户类型的刷新
	function refreshMarketType(){
	  var  marketTypeSelect = document.getElementById('marketType').options;
	  marketTypeSelect.length=0;
	  loadMarketType("${clientInfo.marketType}");//市场类型
	}
	
     function loadClientType(idvalue){//客户类型
   			
			 var vUrl = '<c:url value="/products/systemConfig.do?method=onGetSystemConfig"/>'+'&type=clientType';
			Ext.Ajax.request({
			   url:  vUrl,
			   success: function(response, options){
							  var responseArray = Ext.util.JSON.decode(response.responseText);     
							  var clientTypeSelect = document.getElementById('clientType');
							
							    for(var i=0; i< responseArray.length; i++){
								 var text = responseArray[i].name ;
				                 var value = responseArray[i].sid;
								 clientTypeSelect.options.add(new Option(text,value));
						  		}
						  	  clientTypeSelect.value=idvalue;
			  			},
				failure: function (response, options) {
				    checkAjaxStatus(response);
				}
			});
  		}
  		
  		function loadMarketType(idvalue){//市场类型
   			
			var vUrl = '<c:url value="/products/systemConfig.do?method=onGetSystemConfig"/>'+'&type=marketType';
			Ext.Ajax.request({
			   url:  vUrl,
			   success: function(response, options){
							  var responseArray = Ext.util.JSON.decode(response.responseText);     
							  var marketTypeSelect = document.getElementById('marketType');
							 
							  for(var i=0; i< responseArray.length; i++){
								 var text = responseArray[i].name ;
				                 var value = responseArray[i].sid;
								 marketTypeSelect.options.add(new Option(text,value));
							  } 
							  marketTypeSelect.value=idvalue;
			  			},
				failure: function (response, options) {
				    checkAjaxStatus(response);
				}
			  			
			});
  		}	
</script>
