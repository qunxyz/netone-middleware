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
		<jsp:include page="../common/metaJQuery-ui-dialog.jsp"></jsp:include>
		<jsp:include page="../common/metaUnitsUtil.jsp"></jsp:include>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>::回退::</title>
	</head>
	<style>
		table.main {
			border-collapse: collapse;
			boder-spacing:1px;
		}
		td.label_1 {
			FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType =   0, StartColorStr =   #E9F7FF, EndColorStr =#69C3FD);
			background: -webkit-gradient(linear, left top, left bottom, from(#E9F7FF), to(#69C3FD)); /* for webkit browsers */
			background: -moz-linear-gradient(top,  #E9F7FF,  #69C3FD); /* for firefox 3.6+ */
			color: #000066;
			text-align: left;
			padding-left: 10px;
			padding-right: 10px;
			font-style: normal;
			border: 1px solid #86B4E5;
			line-height: 25px;
		}
		td.label_2 {
			background-color: #fff;
			color: #000;
			text-align: left;
			font-style: normal;
			border: 1px solid #8D8D8D;
			line-height: 20px;
		}
	</style>
	
	<body >
	<div id="panel" style='width: 100%; height: 100%'>
		<div id="div1" style='width: 95%; height: 400px'>
		<form id="form1" action="" method="post">
			<input type="hidden" name="workcode" id="workcode" value="${param.workcode}" />
			<table>
				<tr>
					<td class="label_1">确定文档要退回或特送给谁:</td>
					<td class="label_2">
						<select name="rollBackInfo" id="rollBackInfo" style="width: 100%">
						</select>
					</td>
				</tr>
				<tr>
					<td class="label_1">请填写退办或特送意见:</td>
					<td class="label_2">
						<textarea rows="10" cols="30" id="idea_info" name="idea_info"></textarea>
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>
	</body>
</html>
<script type="text/javascript">
   function loadRollBackInfo(){//加载
		 var vUrl = '<c:url value="/shopping/shopping.do?method=onGetWFInfo"/>&workcode=${param.workcode}';
			Ext.Ajax.request({
			   url:  vUrl,
			   success: function(response, options){
							  var responseArray = Ext.util.JSON.decode(response.responseText);     
							  var rollBackSelect = document.getElementById('rollBackInfo');
							  for(var i=0; i< responseArray.length; i++){
							     var option = document.createElement('option');
								 option.text = responseArray[i].activeName +"\\"+responseArray[i].customer;
				                 option.value = responseArray[i].activeid;
								 
								 rollBackSelect.options.add(option);
							  }         
			  			},
		        failure: function (response, options) {
		            checkAjaxStatus(response);
		        }
			});
	}
	var toolbar = new Ext.Toolbar([
 		 {text : '完成 ',iconCls:'saveIcon',handler:onSaveButton},
 		 {text : '取消 ',iconCls:'exitIcon',handler:_exit}
	]);
	
	  Ext.onReady(function(){
	    var panel = new Ext.Panel({  
	    	  title:'退办/特送',
	    	  tbar:toolbar,
	     	  contentEl :'div1',//加载本地资源	             
	          applyTo :'panel',
	          border:false
		});
		loadRollBackInfo();
 	});
 	function onSaveButton(){
 		var msgTip = Ext.MessageBox.show({
				title : '系统提示',
				width : 250,
				msg : '正在处理信息请稍后......'
		 });
	  Ext.Ajax.request({
			url :"<c:url value='/shopping/shopping.do?method=onRollBackEvent' />&_workcode=${param.workcode}",
			form : 'form1',
			method : 'POST',
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
				msgTip.hide();
				checkAjaxStatus(response);
				var result = Ext.util.JSON.decode(response.responseText);
				Ext.MessageBox.alert('提示',result.tip);
			}
　	 });
		 
 	}
 	function _exit() {
	    window.close();
	}
</script>
