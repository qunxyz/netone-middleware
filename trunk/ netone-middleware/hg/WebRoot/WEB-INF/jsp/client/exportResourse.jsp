j<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<link rel="stylesheet" type="text/css"
			href="<c:url value='/styles/styles.css' />" />
		<title>资源导出</title>
	</head>
	<body>
		<div id="panel" style='width: 100%; height: 100%;' align="center">
			<div id="DataCheck_div" style='width: 95%; height: 100%'>
				<form id="exportForm" method="post" action="" target="_bank">
					<table class="main" width="400px;" style="margin-top: 20px" border="1">
						<tr>
							<td width="80px" class="label">
								父资源名称:
							</td>
							<td align="left">
								<input type="hidden" id="naturalName" name="naturalName" value="">
								<input type="text" name="name" id="name"
									class="text"  value="" style="width: 270px" readonly="readonly"/>
								<input id="clientSelect" type="button"
									onClick="javascript:openWinCenter('选择','<%= basePath%>SelectSvl?appname=ZYMEUN.ZYMEUN&pagename=deptlist', 650,400,true);str='in'"
									value="..." class="btn" style="margin-left: -5px"
									onmouseover="this.className='btn_mouseover'"
									onMouseOut="this.className='btn'"
									onmousedown="this.className='btn_mousedown'"
									onMouseUp="this.className='btn'" />
							</td>
						</tr>
						<tr style="width: 400px">
							<td colspan="2" class="labelbtn">
								<input type="button" onclick="onSaveButton()" value="确 定">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
<script>
  var toolbar = new Ext.Toolbar([
			// {text : '确定',iconCls:'saveIcon',handler: onSaveButton}
		]);
	var str="";
	function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
	  	 document.getElementById('naturalName').value=naturalname;
		 document.getElementById('name').value=name;
	}
  function onSaveButton(){
	var name =document.getElementById("name").value;
	if(name==''){
	   document.getElementById("naturalName").value='DEPT.DEPT';
	}
	var url='<c:url value="/client/import.do?method=onExportResourse"/>';
	var queryForm = document.getElementById('exportForm');
		queryForm.action = url;
		queryForm.submit();
  }
 Ext.onReady(function(){
		var panel = new Ext.Panel({
			  tbar:toolbar,
			  contentEl :'DataCheck_div',//加载本地资源	        
			  applyTo :'panel',
			  border:false     
		});
  });
</script>