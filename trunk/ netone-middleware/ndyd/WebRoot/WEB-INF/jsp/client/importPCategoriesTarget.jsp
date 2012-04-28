<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>产品分类指标导入</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%;' align="center">
			<div id="pcSelltargetImport_div" style='width: 95%; height: 100%'>
				<form id="importForm"  method="POST" enctype="multipart/form-data">
					<table class="main" width="500px" style="margin-top: 20px">
						<tr>
							<td height="40px" class="labeltitle">
								<h2>
									请选择要导入产品分类指标文件：
								</h2>
							</td>
						</tr>
						<tr>
							<td class="labeltitle_td">
								<input type="file" name="importFile" id="importFile" style="height: 23px;" />
								&nbsp;&nbsp;
								<input type="button" onclick="onSaveButton()" value="确 定"
									class="btn">
								&nbsp;&nbsp; &nbsp;&nbsp;
								<input type="button" onclick="dowanLoad()" value="模版下载"
									class="btn">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="tip"
			style="color: #FF0000; margin: auto; width: 500px; height: 100%;">
		</div>
	</body>
</html>
<script>

   function onSaveButton(){
	  var importFileObj = document.getElementById('importFile');
	   if(importFileObj.value == ""){
	      Ext.MessageBox.alert('系统提示','请选择要导入产品分类指标文件！');
		 return  ;
	   }
	   var arrayList=importFileObj.value.split(".");
	   if(arrayList[1]!='xls'){
	       Ext.MessageBox.alert('系统提示','您导入的文件不是EXCEL文件！');
		   return  ;
	   }
	  	Ext.Ajax.request({
					url :'<c:url value="/client/import.do?method=onImportPCTargetFile"/>',//请求的服务器地址
					form : 'importForm',//指定要提交的表单id
					method : 'POST',
					sync:true,
					success : function(response,options){
						Ext.get('tip').dom.innerHTML=response.responseText
					},
					failure : function(response,options){
						checkAjaxStatus(response);
						Ext.get('tip').dom.innerHTML=response.responseText;
					}
		　	});
   }
   function dowanLoad(){
       openWinCenter('下载','<c:url value="/client/import.do?method=onDownLoadClientFile"/>&type=pcategoriesTag', 500,250,true);
   } 
 Ext.onReady(function(){
		var panel = new Ext.Panel({
			  //tbar:toolbar,
			  contentEl :'pcSelltargetImport_div',//加载本地资源	        
			  applyTo :'panel',
			  border:false     
		});
  });
</script>