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
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='width: 95%; height: 400px'>
				<form id="pcform" action="" method="post">
					<input type="hidden" id="sellTargetId" name="sellTargetId"
						value="${sellTargetInfo.sellTargetId}">
					<table class="main">
						<tr>
							<td class="label">
								用户编号:
							</td>
							<td>
								<input type="text" name=clientLoginName id="clientLoginName"
									readonly="readonly" value="${param.clientLoginName}"
									style="width: 150px" />
							</td>
							<td class="label">
								酒窖销售指标:
							</td>
							<td>
								<input type="text" name="bodegaselltarget" style="width: 150px"
									value="${sellTargetInfo.bodegaselltarget}"
									id="bodegaselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>

							<td class="label">
								馆藏销售指标:
							</td>
							<td>
								<input type="text" name="gcselltarget" style="width: 150px"
									value="${sellTargetInfo.gcselltarget}" id="gcselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">
								爱斐堡酒庄销售指标:
							</td>
							<td>
								<input type="text" name="afbjzselltarget" style="width: 150px"
									value="${sellTargetInfo.afbjzselltarget}" id="afbjzselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>

							<td class="label">
								烟台爱斐堡酒庄销售指标:
							</td>
							<td>
								<input type="text" name="ytafbjzselltarget" style="width: 150px"
									value="${sellTargetInfo.ytafbjzselltarget}"
									id="ytafbjzselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">
								冰酒销售指标:
							</td>
							<td>
								<input type="text" name="bjselltarget" style="width: 150px"
									value="${sellTargetInfo.bjselltarget}" id="bjselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">
								卡斯特酒庄销售指标:
							</td>
							<td>
								<input type="text" name="kstjzselltarget" style="width: 150px"
									value="${sellTargetInfo.kstjzselltarget}" id="kstjzselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">
								解干类销售指标:
							</td>
							<td>
								<input type="text" name="xglselltarget" style="width: 150px"
									value="${sellTargetInfo.xglselltarget}" id="xglselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>

							<td class="label">
								普通干酒销售指标:
							</td>
							<td>
								<input type="text" name="ptgjselltarget" style="width: 150px"
									value="${sellTargetInfo.ptgjselltarget}" id="ptgjselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">
								甜酒起泡酒销售指标:
							</td>
							<td>
								<input type="text" name="tjqpjselltarget" style="width: 150px"
									value="${sellTargetInfo.tjqpjselltarget}" id="tjqpjselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">
								保健酒销售指标:
							</td>
							<td>
								<input type="text" name="bjjselltarget" style="width: 150px"
									value="${sellTargetInfo.bjjselltarget}" id="bjjselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">
								洋酒销售指标:
							</td>
							<td>
								<input type="text" name="yjselltarget" style="width: 150px"
									value="${sellTargetInfo.yjselltarget}" id="yjselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">
								白兰地销售指标:
							</td>
							<td>
								<input type="text" name="aquavitselltarget" style="width: 150px"
									value="${sellTargetInfo.aquavitselltarget}"
									id="aquavitselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
							<td class="label">
								夜场产品销售指标:
							</td>
							<td>
								<input type="text" name="ycproductselltarget"
									style="width: 150px"
									value="${sellTargetInfo.ycproductselltarget}"
									id="ycproductselltarget"
									onkeypress="event.returnValue=isDigitEvent();" />
							</td>
						</tr>
						<tr>
							<td class="label">
								销售指标年限:
							</td>
							<td>
								<c:if test="${sellTargetInfo.sellTargetId==null}">
									<input type="text" name="sellyear" style="width: 150px"
										id="sellyear" value="${year}" />
								</c:if>
								<c:if test="${sellTargetInfo.sellTargetId !=null}">
									<input type="text" name="sellyear" style="width: 150px"
										id="sellyear" value="${sellTargetInfo.sellyear} " />
								</c:if>
							</td>
							<!-- 
							<td class="label">
								销售指标合计:
							</td>
							<td>
								<input type="text" name="selltargetcount" style="width: 150px"
									value="${sellTargetInfo.selltargetcount}" id="selltargetcount"
									readonly="readonly" />
							</td>
							 -->
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
   		var msgTip = Ext.MessageBox.show({
	
						title:'系统提示',
						width : 200,
						msg:'正在保存信息请稍后......'
	  		}); 
	     Ext.Ajax.request({
				url :"<c:url value='/client/sellTarget.do?method=onSavaOrUpdate' />",//请求的服务器地址
				form : 'pcform',//指定要提交的表单id
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.tip=='already'){
					Ext.MessageBox.alert('提示','该客户今年的销售指标已存在');
					}else{
					//Ext.MessageBox.alert('提示',result.tip);
					
					Ext.get('sellTargetId').dom.value=result.sellTargetId;
					}
					if(opener){//防止出现没有权限"的js错误对话框
						 if(typeof(window.opener.document)=='unknown'||typeof(window.opener.document) == 'undefined'){ //父窗口已关闭 
							//do nothing
						 }else{ //父窗口没有关闭
							opener.refresh();
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
</script>
