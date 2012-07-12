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
		<div id="panel" style='width: 100%; height: 100%;' >
			<div id="div1" style='height: 600px; overflow: auto;'>
				<form id="form1"  method="post">
					<input type="hidden" id="departmentId" name="departmentId" value="${sellTargetInfo['departmentId']}">
					<input type="hidden" id="s" name="s" value="${param.s}"><!-- 是否是新增 -->
					<table class="main">
						<tr>
							<td class="label">
								编号:
							</td>
							<td>
								<input type="text" name="departmentCode" id="departmentCode" readonly="readonly"
									 value="${sellTargetInfo['departmentCode']}"
									style="width: 150px" />
							</td>
							<td class="label">
								名称:
							</td>
							<td>
								<input type="text" name="departmentName" id="departmentName" readonly="readonly"
									 value="${sellTargetInfo['departmentName']}"
									style="width: 150px" />
							</td>
						</tr>				
						<c:set var="DataCount" value="0"></c:set>
						<c:set var="codeStr"></c:set>
						<c:set var="number" value="0"></c:set>
						
						<c:if test="${param.s == 'add'}">
							<c:forEach items="${productCategoriesInfo}" var="list">
									<td class="label">
										${list.categoriesName}指标:
									</td>
									<td>
										<input type="text" name="${list.categoriesId}"
											style="ime-mode:disabled; width: 150px" value="0" id="${list.categoriesId}"
											onkeypress="event.returnValue=isDigit();" onchange="countMoney();"/>
									</td>
									<c:set var="number" value="${number+1}"></c:set>
									<c:if test="${number%2==0}">
										<tr></tr>
									</c:if>
								<c:set var="codeStr" value="${codeStr},${list.categoriesId}" ></c:set>
							</c:forEach>
						</c:if>
						<c:if test="${param.s != 'add'}">
							<c:forEach items="${productCategoriesInfo}" var="list">
									<td class="label">
										${list.categoriesName}指标:
									</td>
									<td>
									<input type="text"
											name="${list.categoriesId}" id="${list.categoriesId}"
											style="ime-mode:disabled; width: 150px" value="<fmt:formatNumber value="${sellTargetInfo[list.categoriesId]}" pattern="####"/>"
											onkeypress="event.returnValue=isDigit();" onchange="countMoney();"/>
									</td>
									<c:set var="number" value="${number+1}"></c:set>
									<c:if test="${number%2==0}">
										<tr></tr>
									</c:if>
									<c:set var="DataCount" value="${DataCount+sellTargetInfo[list.categoriesId]}"></c:set>
									<c:set var="codeStr" value="${codeStr},${list.categoriesId}" ></c:set>
							</c:forEach>
						</c:if>
						<tr>
							<td class="label">
								销售指标年限:
							</td>
							<td>
								<c:if test="${param.s == 'add'}">
									<input type="text" name="sellYear" style="width: 150px"
										id="sellyear" value="${year}"  />
								</c:if>
								<c:if test="${param.s != 'add'}">
									<input type="text" name="sellYear" style="width: 150px"
										id="sellyear" value="${sellTargetInfo.sellYear}" readonly="readonly" />
								</c:if>
							</td>
							<td class="label">
								指标合计数:
							</td>
							<td>
							<input type="text"
									name="count" id="count"
									style="ime-mode:disabled; width: 150px" value="<fmt:formatNumber value="${DataCount}" pattern="####"/>" 
									 readonly="readonly"  />
							<input type="hidden"
									name="codeStr1" id="codeStr1"
									style="ime-mode:disabled; width: 150px" value="${codeStr}" />
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
          border:false});
	});
   function onSaveButton(){//保存
	     Ext.Ajax.request({
				url :"<c:url value='/client/sellTarget.do?method=saveOrUpdateSellTarget' />",//请求的服务器地址
				form : 'form1',//指定要提交的表单id
				method : 'POST',
				success : function(response,options){
					var result = Ext.util.JSON.decode(response.responseText);
					if (result.error!=null){
						Ext.MessageBox.alert('提示',result.tip);
					} else {
						Ext.ux.Toast.msg("", result.tip);
						parent.refresh();
					}
				},
				failure : function(response,options){
					checkAjaxStatus(response);
					var result = Ext.util.JSON.decode(response.responseText);
					Ext.MessageBox.alert('提示',result.tip);
				}
	　});
   }
  
   function countMoney(){
    var count=0;
    var codeStr=document.getElementById('codeStr1').value;
    var array=codeStr.split(',');
    if(array.length>0){
      for(var i=1;i< array.length;i++){
      	var data=document.getElementById(array[i]).value;
         count+=parseInt(data);
      }
    }
    document.getElementById('count').value=count;
   }	
     function onProductChoice(){//产品选择事件
	 	var eUrl = '<c:url value="/client/sellTarget.do?method=onSelect"/>';
	 	openWinCenter('产品编号选择',eUrl, '400', '450', 'true');
	 } 
</script>
