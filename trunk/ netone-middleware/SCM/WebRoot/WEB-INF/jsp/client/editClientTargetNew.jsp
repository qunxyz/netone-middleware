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
		<script type="text/javascript">
			var tmp = null;
 	 		function mselected(options){
			    var humans='';
			    for(var i=0;i<options.length;i++){
			       var human=options[i].text+'['+options[i].value+'],';
			       var reg=options[i].text+'\\['+options[i].value+'\\],';
			       var pat=new RegExp(reg);
			       humans+=human;
			    }
			    tmp.value=humans;
	}
	
	function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
		document.all.sellDepartment.value = name+'['+naturalname+'],';
	}

		</script>
		<title>::编辑窗口::</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%;' >
			<div id="div1" style='height: 600px; overflow: auto;'>
				<form id="pcform"  method="post">
					<input type="hidden" id="sellTargetId" name="sellTargetId">
					<table class="main">
						<tr>
							<td class="label">
								用户编号:
							</td>
							<td>
								<input type="text" name=clientLoginName id="clientLoginName"
									readonly="readonly" value="<%=new String(request.getParameter("clientLoginName").getBytes("ISO-8859-1"), "UTF-8") %>"
									style="width: 150px" />
							</td>
						</tr>
						<c:set var="DataCount" value="0"></c:set>
						<c:set var="codeStr"></c:set>
						<c:if test="${param.flag=='add'}">
							<c:forEach items="${productCagegories}" var="list">
								<tr>
									<td class="label">
										${list.categoriesName}指标:
									</td>
									<td>
										<input type="text" name="${list.categoriesId}"
											style="ime-mode:disabled; width: 150px" value="0" id="${list.categoriesId}"
											onkeypress="event.returnValue=isDigit();" onchange="countMoney();"/>
									</td>
								</tr>
								<c:set var="codeStr" value="${codeStr},${list.categoriesId}" ></c:set>
							</c:forEach>
						</c:if>
						<c:if test="${param.flag=='edit'}">
							<c:forEach items="${productCagegories}" var="list">
								<tr>
									<td class="label">
										${list.productCategories.categoriesName}指标:
									</td>
									<td>
									<input type="text"
											name="${list.productCategories.categoriesId}"
											style="ime-mode:disabled; width: 150px" value="<fmt:formatNumber value="${list.sellTargetValue}" pattern="####"/>"
											id="${list.productCategories.categoriesId}"
											onkeypress="event.returnValue=isDigit();" onchange="countMoney();"/>
									</td>
									<c:set var="DataCount" value="${DataCount+list.sellTargetValue}"></c:set>
									<c:set var="codeStr" value="${codeStr},${list.productCategories.categoriesId}" ></c:set>
								</tr>
							</c:forEach>
						</c:if>
						<tr>
							<td class="label">
								销售指标年限:
							</td>
							<td>
								<c:if test="${param.flag=='add'}">
									<input type="text" name="sellYear" style="width: 150px"
										id="sellyear" value="${year}"  />
								</c:if>
								<c:if test="${param.flag=='edit'}">
									<input type="text" name="sellYear" style="width: 150px"
										id="sellyear" value="${param.sellYear}" readonly="readonly" />
								</c:if>
							</td>
						</tr>
						<tr>
									<td class="label">
										指标合计数:
									</td>
									<td>
									<input type="text"
											name="count"
											style="ime-mode:disabled; width: 150px" value="<fmt:formatNumber value="${DataCount}" pattern="####"/>" id="count"
											 readonly="readonly"  />
									<input type="hidden"
											name="codeStr1"
											style="ime-mode:disabled; width: 150px" value="${codeStr}" id="codeStr1"/>
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
   function onSaveButton(){//保存
   var flag='${param.flag}';
	     Ext.Ajax.request({
				url :"<c:url value='/client/sellTargetNew.do?method=onSavaOrUpdate' />",//请求的服务器地址
				form : 'pcform',//指定要提交的表单id
				params : {flag :flag },
				method : 'POST',
				success : function(response,options){
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.tip=="already"){
					Ext.MessageBox.alert('提示','该客户今年的销售指标已存在');
					}else{
					//Ext.MessageBox.alert('提示',result.tip);
					Ext.get('sellTargetId').dom.value=result.sellTargetId;
					onCancelButton();
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
    }
  
   function countMoney(){
    var count=0;
    var codeStr=document.getElementById('codeStr1').value;
    var array=codeStr.split(',');
    if(array.length>0){
      for(var i=1;i< array.length;i++){
      	var data=document.getElementById(array[i]).value;
         count=count+parseFloat(data);
      }
    }
    document.getElementById('count').value=count;
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
     function onProductChoice(){//产品选择事件
	 	var eUrl = '<c:url value="/client/sellTarget.do?method=onSelect"/>';
	 	openWinCenter('产品编号选择',eUrl, '400', '450', 'true');
	 } 
	function onCancelButton(){//重置
	   var clientLoginName=document.getElementById("clientLoginName").value;
	    var sellyear=document.getElementById("sellyear").value;  	
        resetForm(Ext.get('pcform').dom);
        document.getElementById("clientLoginName").value=clientLoginName;
        document.getElementById("sellyear").value=sellyear;
    }
</script>
