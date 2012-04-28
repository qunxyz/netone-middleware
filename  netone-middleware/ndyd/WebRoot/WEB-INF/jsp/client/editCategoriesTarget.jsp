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
		<title>::编辑窗口::</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='width: 95%; height: 400px'>
				<form id="pcform" action="" method="post">
					<input type="hidden" id="categoriesId" name="categoriesId"
						value="${param.categoriesId}">
					<input type="hidden" id="id" name="id"
						value="${categoriesSellTarget.id}">
					<table class="main">
						<tr>
							<td class="label">一月:</td>
							<td>
								<input type="ime-mode:disabled; text" name="targetScale1"
									id="targetScale1"
									value="${categoriesSellTarget.targetScale1==null?0:categoriesSellTarget.targetScale1}%"
									style="width: 150px"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">二月:</td>
							<td>
								<input type="text" name="targetScale2"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale2==null?0:categoriesSellTarget.targetScale2}%"
									id="targetScale2"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">三月:</td>
							<td>
								<input type="text" name="targetScale3"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale3==null?0:categoriesSellTarget.targetScale3}%"
									id="targetScale3"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">四月:</td>
							<td>
								<input type="text" name="targetScale4"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale4==null?0:categoriesSellTarget.targetScale4}%"
									id="targetScale4"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>

							<td class="label">五月:</td>
							<td>
								<input type="text" name="targetScale5"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale5==null?0:categoriesSellTarget.targetScale5}%"
									id="targetScale5"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">六月:</td>
							<td>
								<input type="text" name="targetScale6"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale6==null?0:categoriesSellTarget.targetScale6}%"
									id="targetScale6"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">七月:</td>
							<td>
								<input type="text" name="targetScale7"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale7==null?0:categoriesSellTarget.targetScale7}%"
									id="targetScale7"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">八月:</td>
							<td>
								<input type="text" name="targetScale8"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale8==null?0:categoriesSellTarget.targetScale8}%"
									id="targetScale8"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>

							<td class="label">九月:</td>
							<td>
								<input type="text" name="targetScale9"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale9==null?0:categoriesSellTarget.targetScale9}%"
									id="targetScale9"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">十月:</td>
							<td>
								<input type="text" name="targetScale10"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale10==null?0:categoriesSellTarget.targetScale10}%"
									id="targetScale10"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">十一月:</td>
							<td>
								<input type="text" name="targetScale11"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale11==null?0:categoriesSellTarget.targetScale11}%"
									id="targetScale11"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">十二月:</td>
							<td>
								<input type="text" name="targetScale12"
									style="ime-mode: disabled; width: 150px"
									value="${categoriesSellTarget.targetScale12==null?0:categoriesSellTarget.targetScale12}%"
									id="targetScale12"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">销售年限:</td>
							<td colspan="3">
								<input type="text" name="year" style="width: 350px" id="year"
									value="${categoriesSellTarget.year==null?year:categoriesSellTarget.year}" />
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
 		 {text : '保存 ',iconCls:'saveIcon',handler:onSaveButton},
		 {text : '退出 ',iconCls:'exitIcon',handler:onExitButton}
	]);
	
 Ext.onReady(function(){
    var panel = new Ext.Panel({
	      tbar:toolbar,
          contentEl :'div1',//加载本地资源	        
          applyTo :'panel',
          border:false});
	});
  function validateForm(){
     var success=true;
     var january=document.getElementById("targetScale1").value.split('%');
     var february=document.getElementById("targetScale2").value.split('%');
     var march=document.getElementById("targetScale3").value.split('%');
     var april=document.getElementById("targetScale4").value.split('%');
     var may=document.getElementById("targetScale5").value.split('%');
     var june=document.getElementById("targetScale6").value.split('%');
     var july=document.getElementById("targetScale7").value.split('%');
     var august=document.getElementById("targetScale8").value.split('%');
     var september=document.getElementById("targetScale9").value.split('%');
     var october=document.getElementById("targetScale10").value.split('%');
     var november=document.getElementById("targetScale11").value.split('%');
     var december=document.getElementById("targetScale12").value.split('%');
     var countSellTarget=parseFloat(january[0])+parseFloat(february[0])+parseFloat(march[0])+parseFloat(april[0])+parseFloat(may[0])+parseFloat(june[0])+parseFloat(july[0])+parseFloat(august[0])+parseFloat(september[0])+parseFloat(october[0])+parseFloat(november[0])+parseFloat(december[0])
     if(countSellTarget!=100){
     	success=false;
     } 
     return success;
  }
   function onSaveButton(){//保存
   var categoriesId='${param.categoriesId}';
   if(validateForm()){
             Ext.Ajax.request({
				url :"<c:url value='/client/sellTarget.do?method=onSaveOrUpdateCategoriesSellTarget' />",//请求的服务器地址
				form : 'pcform',//指定要提交的表单id
				method : 'POST',
				success : function(response,options){
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.tip=="already"){
						Ext.MessageBox.alert('提示','该客户今年的销售指标已存在');
					}else{
					 	if(parent){
					 	 parent.refresh();
					     parent._jwinClose(); 
					   }
					}
				},
				failure : function(response,options){
					checkAjaxStatus(response);
					var result = Ext.util.JSON.decode(response.responseText);
					Ext.MessageBox.alert('提示',result.tip);
				}
	　		});
       }else{
        Ext.MessageBox.alert('提示','每月该产品分类的销售指标之和必须为100%');
       }
    }		
   function onExitButton(){//退出
	  if(parent){
	  	 parent.refresh();
	     parent._jwinClose(); 
	   }
    }
</script>
