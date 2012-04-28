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
		<title>::回执单填写::</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%'>
			<div id="div1" style='width: 95%; height: 400px'>
				<form id="pcform" action="" method="post">
					<input type="hidden" id="outStorageId" name="outStorageId"
						value="${param.outStorageId}">
					<input type="hidden" id="runTimeId" name="runTimeId"
						value="${param.runtimeid}">
					<table width="900" height="191" cellPadding="0" border="0"
						borderColor="#9fd6ff" style="border-collapse: collapse;">
						<tr>
							<td height="90" valign="top">
								<table width="100%" cellPadding="0" border="1"
									borderColor="#9fd6ff" style="border-collapse: collapse;">
									<tr height="25px">
										<td width="10%" align="right">
											出库单号&nbsp;
										</td>
										<td width="39%">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="hidden" id="ckNumber" name="ckNumber"
												value="${clientStorage.storageInfo.ckNumber}">
											${clientStorage.storageInfo.ckNumber}
										</td>
										<td width="9%" align="right">
											提单号&nbsp;
										</td>
										<td width="42%">
											<input type="hidden" id="allocateCargoCode"
												name="allocateCargoCode"
												value="	${clientStorage.storageInfo.tdNumber}">
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.storageInfo.tdNumber}
										</td>
									</tr>
									<tr height="25px">
										<td align="right">
											联&nbsp;系&nbsp;人&nbsp;
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.clientInfo.linkman}
										</td>
										<td align="right">
											联系电话&nbsp;
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.clientInfo.telphone}
										</td>
									</tr>

									<tr height="25px">
										<td align="right">
											邮&nbsp;&nbsp;箱&nbsp;
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.clientInfo.email}
										</td>
										<td align="right">
											手&nbsp;&nbsp;机&nbsp;
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.clientInfo.mobile}
										</td>
									</tr>
									<tr height="25px">
										<td align="right">
											制单时间&nbsp;
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<fmt:formatDate
												value='${clientStorage.storageInfo.operateTime}'
												pattern="yyyy-MM-dd" />
										</td>
										<td align="right">
											制&nbsp;单&nbsp;人&nbsp;
										</td>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.storageInfo.operate}
										</td>
									</tr>
									<tr height="25px">
										<td align="right">
											送&nbsp;达&nbsp;站&nbsp;
										</td>
										<td colspan="3">
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.clientInfo.servicestation}
										</td>
									</tr>
									<tr height="25px">
										<td align="right">
											送货地址&nbsp;
										</td>
										<td colspan="3">
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.clientInfo.deliverGoodsAddress}
										</td>
									</tr>
									<tr height="25px">
										<td align="right">
											备&nbsp;&nbsp;注&nbsp;
										</td>
										<td colspan="3">
											&nbsp;&nbsp;&nbsp;&nbsp;${clientStorage.storageInfo.note}
										</td>

									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="73" valign="top">
								<table width="900" height="66" cellPadding="0" border="1"
									borderColor="#9fd6ff" style="border-collapse: collapse;">
									<tr height="35px">
										<td height="17" colspan="4">
											<font size="2px">泉州邮政物流公司填写</font>
										</td>
										<td colspan="4">
											<font size="2px">收货人填写</font>
										</td>
									</tr>
									<tr height="25px">
										<td width="109" height="17" align="center">
											货物代码
										</td>
										<td width="170" align="center">
											货品名称
										</td>
										<td width="80" align="center">
											规格
										</td>
										<td width="80" align="center">
											发货数量
										</td>
										<!--  
										<td width="100" align="center">
											破损数
										</td>-->
										<td width="100" align="center">
											短少数
										</td>
										<td width="100" align="center">
											实收数量
										</td>
										<td width="100" align="center">
											操作
										</td>
									</tr>
									<c:forEach items="${productInfo}" var="list" varStatus="num">
										<tr>
											<td width="81" height="25" align="center">
												<input id="productId${num.index }"
													name="productId${num.index }" type="hidden"
													value="${list.productInfo.productId}" style="width: 70px">
												${list.productInfo.productCode }
											</td>

											<td height="25" align="left">
												${list.productInfo.productName }
											</td>

											<td width="81" height="25" align="center">
												${list.productInfo.specifications }
											</td>
											<td width="81" height="25" align="center">
												<input id="consignmentCount${num.index }"
													name="consignmentCount${num.index }" type="text"
													value="${list.storageDetail.consignmentCount}"
													style="width: 70px" readonly="readonly">

											</td>
											<input id="damagedCount${num.index }"
												name="damagedCount${num.index }" type="hidden" value="0"
												style="width: 80px"
												onkeypress="event.returnValue=isDigit();">
											<td width="81" height="25" align="center">
												<c:if test="${list.storageDetail.shortBoxCount==null}">
													<input id="shortCount${num.index }"
														name="shortCount${num.index }" type="text" value="0"
														style="width: 80px"
														onkeypress="event.returnValue=isDigit();" readonly="readonly">
												</c:if>
												<c:if test="${list.storageDetail.shortBoxCount!=null}" >
													<input id="shortCount${num.index }"
														name="shortCount${num.index }" type="text"
														value="${list.storageDetail.shortBoxCount}"
														style="width: 80px"
														onkeypress="event.returnValue=isDigit();" readonly="readonly">
												</c:if>
											</td>
											<td width="81" height="25" align="center">
												<input id="factCount${num.index }"
													name="factCount${num.index }" type="text"
													value="${list.storageDetail.factBoxtCount}" style="width: 80px"
													readonly="readonly">
											</td>
											<td width="97" height="25" align="center">
												<c:if test="${list.storageDetail.factBoxtCount==null}">
													<a href="javaScript:void(0)"
														onclick="updateInfo('${list.storageDetail.outStorageDetailId}','damagedCount${num.index }','shortCount${num.index }','consignmentCount${num.index }','factCount${num.index }','productId${num.index }')">确认回执</a>
												</c:if>
												<c:if test="${list.storageDetail.factBoxtCount!=null}">
													已回执
												</c:if>
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td>
											意见填写:
										</td>
										<td colspan="7">
											<textarea rows="4%" cols="100%" id="idea" name="idea"></textarea>
										</td>
									</tr>
									<tr>
										<td colspan="8" align="center">
											<input type="button" id="submit" name="submit" value="确认"
												onclick="submitAuditingClient();">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
function updateInfo(outstorageDetailId,damage,short,consignment,factCount,product){//培训学习
           var damageCount=document.getElementById(damage).value;
           var shortCount=document.getElementById(short).value;
           var consignmentCount=document.getElementById(consignment).value;
           var ckNumber=document.getElementById("ckNumber").value;
           var allocateCargoCode=document.getElementById("allocateCargoCode").value;
           var productId=document.getElementById(product).value;
            if(shortCount=='' || shortCount > consignmentCount){
             Ext.Msg.alert('系统提示','短收数不能为空或都短收数不能大于发货数');
             return false ;
           }
           if(damageCount=='' || damageCount > consignmentCount){
             Ext.Msg.alert('系统提示','破损数不能为空或都破损数不能大于发货数');
             return false ;
           }
            var count1=shortCount-0;
            var count2=damageCount-0;
            if( count1+count2 > consignmentCount){
             Ext.Msg.alert('系统提示','短收数与破损数之和不能大于发货数');
             return false ;
           }
	  		Ext.Ajax.request({
	  					url : "<c:url value='/storage/outStorage.do?method=onUpdateDamaged' />",
	  					params : {outstorageDetailId : outstorageDetailId,damageCount:damageCount,shortCount:shortCount,consignmentCount:consignmentCount,ckNumber:ckNumber,allocateCargoCode:allocateCargoCode,productId:productId,allocateCargoId:allocateCargoId},
	  					method : 'POST',
	  					success : function(response,options){
								var result = Ext.util.JSON.decode(response.responseText);
								document.getElementById(factCount).value=result.factCount;
						        Ext.Msg.alert('系统提示',result.tip);
						        document.location.reload();
								},
						failure : function(response,options){
							checkAjaxStatus(response);
							var result = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert('系统提示',result.tip);
						}
	  		});
  }
  
  
  /*****************************流程提交*******************************************/
  function submitAuditingClient(){
    var runtimeid=document.getElementById("runtimeid").value;
    var ckNumber=document.getElementById("ckNumber").value;
    var outStorageId=document.getElementById("outStorageId").value;
 	      Ext.Ajax.request({
	  					url : "<c:url value='/storage/outStorage.do?method=onAuditingUpdateDamaged' />",
	  					params : {runtimeid : runtimeid,ckNumber:ckNumber,outStorageId:outStorageId},
	  					method : 'POST',
	  					form: 'pcform',
	  					success : function(response,options){
	  					var result = Ext.util.JSON.decode(response.responseText);
	  					document.getElementById('submit').disabled='disabled';
	  					window.close();
						//window.location.reload();
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
								Ext.Msg.alert('系统提示',"收货确认失败");
						}
	  		});
  }
  </script>