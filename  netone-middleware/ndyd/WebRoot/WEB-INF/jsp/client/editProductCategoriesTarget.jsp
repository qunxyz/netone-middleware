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
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/keyNumber.js"></script>
		<title>::编辑窗口::</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='width: 95%; height: 400px'>
				<form id="pcform" action="" method="post">
					<input type="hidden" id="productCategoriesId"
						name="productCategoriesId" value="${param.productCategoriesId}">
					<input type="hidden" id="productCategoriesSellTargetId"
						name="productCategoriesSellTargetId"
						value="${productCategoriesSellTargetinfo.productCategoriesSellTargetId}">
					<table class="main">
						<tr>
							<td class="label">
								一月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="ime-mode:disabled; text" name=january id="january"
										value="0.0%" style="width: 150px"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="ime-mode:disabled; text" name=january id="january"
										value="${productCategoriesSellTargetinfo.january}%"
										style="width: 150px"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
							<td class="label">
								二月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="february"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="february" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="february"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.february}%"
										id="february" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="label">
								三月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="march"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="march" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="march"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.march}%" id="march"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
							<td class="label">
								四月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="april"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="april" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="april"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.april}%" id="april"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
						</tr>
						<tr>

							<td class="label">
								五月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="may"
										style="ime-mode: disabled; width: 150px" value="0.0%" id="may"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="may"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.may}%" id="may"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
							<td class="label">
								六月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="june"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="june" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="june"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.june}%" id="june"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="label">
								七月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="july"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="july" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="july"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.july}%" id="july"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
							<td class="label">
								八月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="august"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="august" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="august"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.august}%" id="august"
										onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
						</tr>
						<tr>

							<td class="label">
								九月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="september"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="september" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="september"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.september}%"
										id="september" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
							<td class="label">
								十月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="october"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="october" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="october"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.october}%"
										id="october" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="label">
								十一月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="november"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="november" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="november"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.november}%"
										id="november" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
							<td class="label">
								十二月:
							</td>
							<td>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="december"
										style="ime-mode: disabled; width: 150px" value="0.0%"
										id="december" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId!=null}">
									<input type="text" name="december"
										style="ime-mode: disabled; width: 150px"
										value="${productCategoriesSellTargetinfo.december}%"
										id="december" onkeypress="event.returnValue=isDigitEvent();" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="label">
								销售年限:
							</td>
							<td colspan="3">
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId==null}">
									<input type="text" name="year" style="width: 350px" id="year"
										value="${year}" readonly="readonly" />
								</c:if>
								<c:if
									test="${productCategoriesSellTargetinfo.productCategoriesSellTargetId !=null}">
									<input type="text" name="year" style="width: 350px" id="year"
										value="${productCategoriesSellTargetinfo.year} " readonly="readonly" />
								</c:if>
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
     var january=document.getElementById("january").value.split('%');
     var february=document.getElementById("february").value.split('%');
     var march=document.getElementById("march").value.split('%');
     var april=document.getElementById("april").value.split('%');
     var may=document.getElementById("may").value.split('%');
     var june=document.getElementById("june").value.split('%');
     var july=document.getElementById("july").value.split('%');
     var august=document.getElementById("august").value.split('%');
     var september=document.getElementById("september").value.split('%');
     var october=document.getElementById("october").value.split('%');
     var november=document.getElementById("november").value.split('%');
     var december=document.getElementById("december").value.split('%');
     var countSellTarget=parseFloat(january[0])+parseFloat(february[0])+parseFloat(march[0])+parseFloat(april[0])+parseFloat(may[0])+parseFloat(june[0])+parseFloat(july[0])+parseFloat(august[0])+parseFloat(september[0])+parseFloat(october[0])+parseFloat(november[0])+parseFloat(december[0])
     if(countSellTarget!=100){
     success=false;
     } 
     return success;                   
  }
   function onSaveButton(){//保存
   var productCategoriesId='${param.productCategoriesId}';
   if(validateForm()){
               Ext.Ajax.request({
				url :"<c:url value='/client/sellTargetNew.do?method=onSavaOrUpdateProductCategoriesSellTarget' />",//请求的服务器地址
				form : 'pcform',//指定要提交的表单id
				params : {productCategoriesId :productCategoriesId },
				method : 'POST',
				success : function(response,options){
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.tip=="already"){
					Ext.MessageBox.alert('提示','该客户今年的销售指标已存在');
					}else{
					 window.close();
					}
					if(opener){//防止出现没有权限"的js错误对话框
						 if(typeof(window.opener.document)=='unknown'||typeof(window.opener.document) == 'undefined'){ //父窗口已关闭 
							//do nothing
						 }else{ //父窗口没有关闭
							opener.location.reload();
						 }
					} else { //父窗口已关闭 
						 //do nothing
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
	  if(opener){//防止出现没有权限"的js错误对话框
		 if(typeof(window.opener.document)=='unknown'||typeof(window.opener.document) == 'undefined'){ //父窗口已关闭 
			//do nothing
		 }else{ //父窗口没有关闭
			opener.refresh();
		 }
	  } else { //父窗口已关闭 
		 //do nothing
	  }
	  window.close();
    }
</script>
