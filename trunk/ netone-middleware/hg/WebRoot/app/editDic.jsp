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
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/AppUtil.js"></script>
		<title>::编辑窗口::</title>
	</head>
	<body onload="loadFocus();">
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='width: 95%; height: 400px'>
				<form id="form1" action="" method="post">
					<input type="hidden" id="sid" name="sid" value="${sysconfig.sid}">
					<table class="main">
						<tr>
							<td class="label">类型:</td>
							<td>
								<select name="type_" id="type_" style="width: 186px;">
									<option value="productBussCate">产品业务分类</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label">编码:</td>
							<td>
								<input type="text" name="code" style="width: 186px"
									value="${sysconfig.code}" id="code" />
							</td>
						</tr>
						<tr>
							<td class="label">名称:</td>
							<td>
								<input type="text" name="name" style="width: 186px"
									value="${sysconfig.name}" id="name" />
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
	]);
	
   Ext.onReady(function(){
	    var panel = new Ext.Panel({
		      tbar:toolbar,
	          contentEl :'div1',//加载本地资源	        
	          applyTo :'panel',
	          border:false
	    });
	
	});
   function onSaveButton(){//保存
   	var type_ = document.getElementById('type_').value;
   		if(validateForm()){
   	           var msgTip = Ext.MessageBox.show({
								title:'系统提示',
								width : 200,
								msg:'正在保存信息请稍后......'
	  		         });
              Ext.Ajax.request({
						url :"<c:url value='/dic.do?method=onSavaOrUpdate' />"+"&type="+type_,//请求的服务器地址
						form : 'form1',//指定要提交的表单id
						method : 'POST',
						success : function(response,options){
							msgTip.hide();
							var result = Ext.util.JSON.decode(response.responseText);
							if(result.error==null){
								Ext.ux.Toast.msg("", result.tip);
								parent.refresh();
								onClearForm();
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
    }
    
    
    /**---------- 其它事件 ----------*/
   function validateForm(){ //弹出出错提示  验证
		var str = '保存信息失败!出错提示如下:<br />';
		var i=1;
		var blank = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
		var code = Ext.get('code').dom.value;
		if(code == ''){
		   str+= blank+ i+ '、此编码为空。<br />';
		   i++;
		}
		var name = Ext.get('name').dom.value;
		if(name == ''){
		   str+= blank+ i+ '、此名称为空。<br />';
		   i++;
		}
		if(i >1){
		   Ext.MessageBox.alert('错误提示',str);
		   return false;
		}
		return true;
   }
   function onClearForm(){//重置
			var sid = document.getElementById('sid').value;        	
        	resetForm(Ext.get('form1').dom);
        	sid.value = '';
    }
   function loadFocus(){
  	document.getElementById('name').focus();
   }
</script>
