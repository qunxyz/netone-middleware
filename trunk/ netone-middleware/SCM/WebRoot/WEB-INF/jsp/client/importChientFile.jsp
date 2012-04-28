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
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>经销商仓库三级码导入</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%;' align="center">
			<div id="ChildClientImport_div" style='width: 95%; height: 100%'>
				<form id="importForm" method="post" 
					enctype="multipart/form-data">
					<table class="main" width="500px" style="margin-top: 20px">
						<tr>
							<td height="40px" class="labeltitle">
								<h2>
									请选择要导入数据文件：
								</h2>
							</td>
						</tr>
						<tr>
							<td class="labeltitle_td">
								<table>
									<tr>
										<td>
											客户名称:
										</td>
										<td colspan="2" align="left">
										<input id="hiddenText" type="text" style="display:none" />
										   <input type="text" name="clientId" id="clientId"
													style="width: 177px" onclick=""
													onkeyup="onKeyUpEvent(event)" onchange="getClientInfo();" />
											<input type="hidden" name="clientLoginName" 
												id="clientLoginName" style="width: 177px;" />
											<input id="clientIdSelect" type="button"
												onClick="javascript:openWinCenter('客户选择窗口','../SSelectSvl?appname=DEPT.DEPT&pagename=deptlist',650,500,true);tmp1=document.getElementById('clientId');"
												value="..." class="btn" style="margin-left: -5px"
												onmouseover="this.className='btn_mouseover'"
												onMouseOut="this.className='btn'"
												onmousedown="this.className='btn_mousedown'"
												onMouseUp="this.className='btn'" />
										</td>
									</tr>
									<tr>
										<td>
											附件上传:
										</td>
										<td align="left">
											<input type="file" name="files" style="height: 23px;width: 270px;" />
										</td>
										<td>
											<input type="button" onclick="uploadfile();" value="确 定">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="tip" style="color: #FF0000; text-align: center;">
		</div>
	</body>
</html>
<script>
	var tmp = null;
	var str = '';
 	function mselected(options){
			    var humans='';
			    var aa='';
			    var split = '';
			    for(var i=0;i<options.length;i++){
			    	aa = options[i].text;
			       var human=options[i].text+'['+options[i].value+'],';
			       var human=split+options[i].text;
			       var reg=options[i].text+'\\['+options[i].value+'\\],';
			       var pat=new RegExp(reg);
			       split = ',';
			       humans+=human;
			    }
			    tmp.value=humans;//取名字
	}
	
	var tmp1 = null;//单选窗口1
 	function sselected(text,value){
 		tmp1.value=text;
 		document.getElementById("clientLoginName").value=value;
	}
	
	function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
		document.all.salesDept.value = name;
	}
  function onKeyUpEvent(e){//监听键盘事件--查询人员
				switch(e.keyCode)
				{
					case 13:
						getClientInfo();
						break;//回车  
					default:
					break;
				}
	}
	//查询所有人员的信息
	function getClientInfo(){
			var clientId = document.getElementById('clientId').value;
					Ext.Ajax.request({
	  					url : '<c:url value="/client/client.do?method=onGetClientInfo" />',
	  					params : {clientCode:clientId},
	  					method : 'POST',
	  					sync:true,
	  					success : function(response,options){
								var result = Ext.util.JSON.decode(response.responseText);
								    if(clientId!=''){
										document.getElementById('clientId').value=result.clientName;
										document.getElementById('clientLoginName').value=clientId;
									}
									//判断如果返回的是空值的话，则保留原来值
									if(result.clientName=='' ){
									document.getElementById('clientId').value=clientId;
									document.getElementById('clientLoginName').value="";
									}
						},
						failure : function(response,options){
							checkAjaxStatus(response);
						}
	  			});
	  		}
  var toolbar = new Ext.Toolbar([
			// {text : '确定',iconCls:'saveIcon',handler: onSaveButton}
		]);
   function uploadfile(){
	  var importFileObj = document.getElementById('files');
	  var clientLoginName=document.getElementById("clientLoginName").value;
	   var clinetId=document.getElementById("clientId").value;
	   if(clinetId == '' || clientLoginName==''){
	      Ext.MessageBox.alert('系统提示','请选择要导入的客户！');
		 return  ;
	  }
	  if(importFileObj.value == ''){
	      Ext.MessageBox.alert('系统提示','请选择要导入的信息文件！');
		 return  ;
	  }
	  Ext.Ajax.request({
					url :'<c:url value="/client/importFile.do?method=onImportClientdFile"/>'+'&clientLoginName='+clientLoginName,//请求的服务器地址
					form : 'importForm',//指定要提交的表单id
					method : 'POST',
					sync:true,
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						Ext.get('tip').dom.innerText=result.tip;
						document.getElementById("clientLoginName").value="";
	                    document.getElementById("clientId").value="";
					},
					failure : function(response,options){
						checkAjaxStatus(response);
						var result = Ext.util.JSON.decode(response.responseText);
						Ext.get('tip').dom.value=result.tip;
					}
		　	});
   }
 Ext.onReady(function(){
		var panel = new Ext.Panel({
			  tbar:toolbar,
			  contentEl :'ChildClientImport_div',//加载本地资源	        
			  applyTo :'panel',
			  border:false     
		});
  });
</script>