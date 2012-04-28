<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/script/keyNumber.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<jsp:include page="../common/metaJQuery-scrollSlider.jsp"></jsp:include>

<title>经销商销售分析查询窗口</title>
<script type="text/javascript">
	 var array1 = ['salesDept','operationDirector','clientId','clientType','marketType'];
	 var array2 = ['code'];
	 function disableOtherCondition(str){//固定只能输入一个文本框
	 		
		 	var objStr = document.getElementById(str).value;
		 	document.getElementById(str+'Checkbox').checked=true;
		 	/**
		 	if (objStr!=''){
		 		for(var i=0;i<array1.length;i++){
		 			
			 		if (array1[i]!=str){
			 			document.getElementById(array1[i]).disabled='disabled';
			 			document.getElementById(array1[i]+'Select').disabled='disabled';
			 			document.getElementById(array1[i]+'Checkbox').checked=false;
			 		} else {
			 			document.getElementById(array1[i]+'Checkbox').checked=true;
			 		}
			 	}
		 	} else {*/
		 		for(var i=0;i<array1.length;i++){
			 		document.getElementById(array1[i]).disabled='';
			 		document.getElementById(array1[i]+'Select').disabled='';
			 		if (array1[i]!=str){
			 			document.getElementById(array1[i]+'Checkbox').checked=false;
			 		}
			 	}
		 	//}
	 } 
	 function disableOtherCondition2(str){//固定只能输入一个文本框
	 		
		 	var objStr = document.getElementById(str).value;
		 	document.getElementById(str+'Checkbox').checked=true;
		 	
	 		for(var i=0;i<array2.length;i++){
		 		if (array2[i]!=str){
		 			document.getElementById(array2[i]+'Checkbox').checked=false;
		 		}
		 	}
	 }

	
	var tmp = null;
	var tmp2 = null;//单选窗口1
	var str = '';
 	function mselected(options){
			    var humans='';
			    var humansCode='';
			    var aa='';
			    var code='';
			    var split = '';
			    for(var i=0;i<options.length;i++){
			    	aa = options[i].text;
			    	code=options[i].value;
			       var human=options[i].text+'['+options[i].value+'],';
			       var human=split+options[i].text;
			       var code=split+code;
			       var reg=options[i].text+'\\['+options[i].value+'\\],';
			       var pat=new RegExp(reg);
			       split = ',';
			       humans+=human;
			       humansCode+=code;
			    }
			    if(tmp2!=null){
			    tmp2.value=humansCode;
			    }
			    tmp.value=humans;//取名字
	}
	
	var tmp1 = null;//单选窗口1
 	function sselected(text,value){
 		tmp1.value=text;
	}
	
	function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
		document.all.salesDept.value = name;
	}
	function onQueryBtn(){
	    var queryForm = document.getElementById('queryForm');
	    var selectedStr =getSelectedValueStr(document.getElementById('unselect'))
	    
	    var str = '';
	    var index = 0;
   		for(var i=0;i<array1.length;i++){
	 		var obj = document.getElementById(array1[i]+'Checkbox');
	 		if (obj.checked){
	 			//str = obj.id.replace('Checkbox','');
	 			index = i+1;
	 		}
	 	}
	    //var inputValue =  document.getElementById(str).value;
		queryForm.action = '<c:url value="/client/sellTargetNew.do?method=onTargetAnalysis"/>'+'&selectedCol='+selectedStr+'&index='+index;
		queryForm.submit();
		document.getElementById('code').value='';
   }
  //客户类型选择窗口
	 function openClientType(){
	   var eUrl = '<c:url value="/products/systemConfect.do?method=onMSelectView"/>'+'&type=03';
	 	openWinCenter('客户类型择',eUrl, '400', '500', 'true');
	 }
	 function openMarketType(){
	  var eUrl = '<c:url value="/products/systemConfect.do?method=onMSelectView"/>'+'&type=04';
	 	openWinCenter('客户类型择',eUrl, '400', '500', 'true');
	 }
	 

  //***********************查询条件 按回车显示客户名称，销售部门名称,业务主任名称 开始位处**********************/
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
	  function onKeyUpEvent2(e){//监听键盘事件--查询部门
				switch(e.keyCode)
				{
					case 13:
						getDeptInfo();
						break;//回车  
					default:
					break;
				}
	}
	  function onKeyUpEvent1(e){//监听键盘事件--查询业务主任
				switch(e.keyCode)
				{
					case 13:
						getOperationDirectorInfo();
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
									}
									//判断如果返回的是空值的话，则保留原来值
									if(result.clientName=='' ){
									document.getElementById('clientId').value=clientId;
									}
						},
						failure : function(response,options){
							checkAjaxStatus(response);
						}
	  			});
	  		}
	 //查询所有部门的信息
	function getDeptInfo(){
			var salesDept=document.getElementById('salesDept').value;
					Ext.Ajax.request({
	  					url : '<c:url value="/client/client.do?method=onGetDeptInfo" />',
	  					params : {sellDepartment:salesDept},
	  					method : 'POST',
	  					sync:true,
	  					success : function(response,options){
								var result = Ext.util.JSON.decode(response.responseText);
									if(salesDept!=''){
										document.getElementById('salesDept').value=result.departmentDeptName;
										document.getElementById('deptCode').value=result.departmentDeptCode;
									}
									//判断如果返回的是空值的话，则保留原来值
									if(result.departmentDeptName==''){
									document.getElementById('salesDept').value=salesDept;
									document.getElementById('deptCode').value='';
									}
						},
						failure : function(response,options){
							checkAjaxStatus(response);
						}
	  			});
	  		}
	  		
	  	//查询业务员的信息
	function getOperationDirectorInfo(){
			var operationDirector = document.getElementById('operationDirector').value;
					Ext.Ajax.request({
	  					url : '<c:url value="/client/client.do?method=onGetOperationDirectorInfo" />',
	  					params : {operationDirector:operationDirector},
	  					method : 'POST',
	  					sync:true,
	  					success : function(response,options){
								var result = Ext.util.JSON.decode(response.responseText);
									if(operationDirector!=''){
										document.getElementById('operationDirector').value=result.operationDirector;
									}
									//判断如果返回的是空值的话，则保留原来值
									
									if(result.operationDirector==''){
									document.getElementById('operationDirector').value=operationDirector;
									}
						},
						failure : function(response,options){
							checkAjaxStatus(response);
						}
	  			});
	  		}
 //***********************查询条件 按回车显示客户名称，销售部门名称,业务主任名称 结束位处**********************/
	
	  //**--------------------选择框选择事件 添加,移除,单双号显示及选择,全部选择功能 开始-------------------------------
	  function moveSelected(){//添加
	     var oSourceSel=document.getElementById('unselect');
	     var oTargetSel=document.getElementById('select');
	     var arrSelValue = new Array();//建立存储value和text的缓存数组
	     var arrSelText = new Array();
	     var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
	     var index = 0;//用来辅助建立缓存数组
	     for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
	         if(oSourceSel.options[i].selected){
	             arrSelValue[index] = oSourceSel.options[i].value;//存储
	             arrSelText[index] = oSourceSel.options[i].text;
	             arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
	             index++;
	         }
	     }
	     for(var i=0; i<arrSelText.length; i++){  //增加缓存的数据到目的列表框中，并删除源列表框中的对应项
			 oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
	         oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
	     }
	 }
	 function moveSelected1(){//移除 
	   	 var oSourceSel=document.getElementById('select');
	     var oTargetSel=document.getElementById('unselect');
	     var arrSelValue = new Array();//建立存储value和text的缓存数组
	     var arrSelText = new Array();
	     var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
	     var index = 0;//用来辅助建立缓存数组
	     for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
	         if(oSourceSel.options[i].selected){
	             arrSelValue[index] = oSourceSel.options[i].value; //存储
	             arrSelText[index] = oSourceSel.options[i].text;
	             arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
	             index++;
	         }
	     }
	     for(var i=0; i<arrSelText.length; i++){//增加缓存的数据到目的列表框中，并删除源列表框中的对应项
			 oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
	         oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
	     }
	 }

	function getSelectedValueStr(obj){//将options中的对象以逗号分隔,学生编号
			 var arrValueText = new Array();
			
		     var str1 = '', str2 = '';
		     var deliter='',deliter1='';
		     
		     var options = obj.options;
			 for(var i=0; i < options.length; i++){
			      str1 += deliter + options[i].value;
			      deliter=',';
			      //str2 += deliter1 + options[i].text;
			      //deliter1='\|';
			 }
			 arrValueText[0] = str1;
			 //arrValueText[1] = str2;
			 return arrValueText; 
	 }
	 
	 function filterFindFunction() {//根据选择 过滤精确查询按钮
	 	var selectedValueStr = getSelectedValueStr(document.getElementById('unselect'));
	 	var codeDivObj = document.getElementById('codeDiv');

	 	if (selectedValueStr==''){
	 		codeDivObj.style.display='block';
	 	} else {
		 	var _selectedValueStr = selectedValueStr.toString().split(',');
		 	var hasCode =  false;//是否有单据编号
		 	for (var i=0 ; i< _selectedValueStr.length;i++){
		 		if (_selectedValueStr[i]=='col5'){
		 			codeDivObj.style.display='block';
		 			hasCode=true;
		 		} 
		 	}
		 	if (!hasCode) codeDivObj.style.display='none';
	 	}
	 }
	
	function onProductCategoriesChoice(){//产品类别选择事件
	 	var eUrl = '<c:url value="/finance/report3.do?method=onProductCategoriesMultiSelect"/>';
	 	openWinCenter('产品类别选择窗口',eUrl, '400', '500', 'true');
	}
		</script>
	</head>
	<body>
		<form id="queryForm"	method="post" target="_blank">
		<input type="hidden" id="sgs" name="sgs" value="${sgs}" /><!-- 权限 -->
	    <input type="hidden" id="queryKey" name="queryKey" value="${queryKey}"/><!--关键字 key -->
		<div id="wrapper">
	        <div id="slider">    
	            <ul class="navigation">
	                <li> <a href="#sites">查询条件</a></li>
	                <li><a href="#files">查询依据</a></li>
	                <li><a href="#editor" onclick="filterFindFunction()">精确查询</a></li>
	            </ul>
	
	            <div class="scroll" style="height:200px;width:500px">
	                <div class="scrollContainer" style="text-align: center;">
	                <!-- 查询条件 -->
	                <div class="panel" id="sites" style="text-align: center;" style="width:500px">
						<p><font color="red">日期范围:</font>
	                      <input type="text" name="beginDate" id="beginDate"
											value="${beginTime}" class="Wdate" style="width:90px"
											onFocus="WdatePicker({dateFmt:'yyyy-MM'});" />
									--
						  <input type="text" name="endDate" id="endDate" value="${endTime}"
											style="width: 90px" class="Wdate"
											onFocus="WdatePicker({dateFmt:'yyyy-MM'});" />
	                 <c:choose>
	                 										<c:when test="${sgs=='zgs'}">
										<input type="hidden" name="deptCode" id="deptCode" value="${zgs}"/>
										<p>
												<input type="radio" id="salesDeptCheckbox" checked="checked"
													onclick="disableOtherCondition('salesDept')" />
												销售部门:
												<input type="text" name="salesDept" id="salesDept"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent2(event)" onchange="getDeptInfo();"  value="${zgsn}" readonly="readonly"/>
												<input id="salesDeptSelect" type="button"
													onClick="javascript:openWinCenter('销售部选择','../MSelectSvl?appname=${sgsNutrual}&pagename=deptlist', 650,400,true);tmp2=document.getElementById('deptCode');tmp=document.getElementById('salesDept');str='salesDept';disableOtherCondition('salesDept');"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
											</p>
											<p>
												<input type="radio" id="operationDirectorCheckbox"
													onclick="disableOtherCondition('operationDirector')" />
												业务主任:
												<input type="text" name="operationDirector"
													id="operationDirector" style="width: 177px"
													onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent1(event)" onchange="getOperationDirectorInfo();" />
												<input id="operationDirectorSelect" type="button"
													onClick="javascript:openWinCenter('业务主任选择窗口','../MSelectSvl?appname=${sgsNutrual}&pagename=human',650,500,true);tmp2='';tmp=document.getElementById('operationDirector');str='operationDirector';disableOtherCondition('operationDirector');"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
											</p>
											<p>
												<input type="radio" id="clientIdCheckbox" value="2"
													onclick="disableOtherCondition('clientId')" />
												客户名称:
												<input type="text" name="clientId" id="clientId"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent(event)" onchange="getClientInfo();" />
												<input id="clientIdSelect" type="button"
													onClick="javascript:openWinCenter('客户选择窗口','../MSelectSvl?appname=${sgsNutrual}&pagename=deptlist',650,500,true);tmp2='';tmp=document.getElementById('clientId');str='clientId';disableOtherCondition('clientId');"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
										</p>									
									</c:when>
										<c:when test="${sgs=='zgs'}">
											<p>
												<input type="radio" id="salesDeptCheckbox" checked="checked"
													onclick="disableOtherCondition('salesDept')" />
												销售部门:
												<input type="text" name="salesDept" id="salesDept"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent2(event)" onchange="getDeptInfo();" value="${zgsn }" readonly="readonly"/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="salesDeptSelect" type="button"
													onClick="javascript:openWinCenter('销售部选择','../MSelectSvl?appname=${zgsNutrual }&pagename=deptlist', 650,400,true);tmp=document.getElementById('salesDept');str='salesDept';disableOtherCondition('salesDept')"
													value="..." class="btn" style="margin-left: -5px;display: none;"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" disabled="disabled" />
											</p>
											<p style="display: none;">
												<input type="radio" id="operationDirectorCheckbox"
													onclick="disableOtherCondition('operationDirector')" />
												业务主任:
												<input type="text" name="operationDirector"
													id="operationDirector" style="width: 177px"
													onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent1(event)" onchange="getOperationDirectorInfo();" />
												<input id="operationDirectorSelect" type="button"
													onClick="javascript:openWinCenter('业务主任选择窗口','../MSelectSvl?appname=${zgsNutrual }&pagename=human',650,500,true);tmp=document.getElementById('operationDirector');str='operationDirector';disableOtherCondition('operationDirector')"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
											</p>
											<p>
												<input type="radio" id="clientIdCheckbox" value="2"
													onclick="disableOtherCondition('clientId')" />
												客户名称:
												<input type="text" name="clientId" id="clientId"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent(event)" onchange="getClientInfo();" />
												<input id="clientIdSelect" type="button"
													onClick="javascript:openWinCenter('客户选择窗口','../MSelectSvl?appname=${zgsNutrual}&pagename=deptlist',650,500,true);tmp=document.getElementById('clientId');str='clientId';disableOtherCondition('clientId')"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
											</p>
										</c:when>
									<c:when test="${sgs=='kh'}">
										<p style="display: none;">
												<input type="radio" id="salesDeptCheckbox" checked="checked"
													onclick="disableOtherCondition('salesDept')" />
												销售部门:
												<input type="text" name="salesDept" id="salesDept"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent2(event)" onchange="getDeptInfo();" readonly="readonly" />
												<input id="salesDeptSelect" type="button"
													onClick="javascript:openWinCenter('销售部选择','../MSelectSvl?appname=DEPT.DEPT&pagename=deptlist', 650,400,true);tmp=document.getElementById('salesDept');str='salesDept';disableOtherCondition('salesDept')"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" readonly="readonly" />
											</p>
											<p style="display: none;">
												<input type="radio" id="operationDirectorCheckbox"
													onclick="disableOtherCondition('operationDirector')" />
												业务主任:
												<input type="text" name="operationDirector"
													id="operationDirector" style="width: 177px"
													onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent1(event)" onchange="getOperationDirectorInfo();" readonly="readonly"/>
												<input id="operationDirectorSelect" type="button"
													onClick="javascript:openWinCenter('业务主任选择窗口','../MSelectSvl?appname=DEPT.DEPT&pagename=human',650,500,true);tmp=document.getElementById('operationDirector');str='operationDirector';disableOtherCondition('operationDirector')"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" disabled="disabled"/>
											</p>
											<p>
												<input type="radio" id="clientIdCheckbox" value="2" checked="checked"
													onclick="disableOtherCondition('clientId')" />
												客户名称:
												<input type="text" name="clientId" id="clientId"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent(event)" onchange="getClientInfo();" value="${khDeptName}" readonly="readonly"/>
												<input id="clientIdSelect" type="button"
													onClick="javascript:openWinCenter('客户选择窗口','../MSelectSvl?appname=DEPT.DEPT&pagename=human',650,500,true);tmp=document.getElementById('clientId');str='clientId';disableOtherCondition('clientId')"
													value="..." class="btn" style="margin-left: -5px;display: none;"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" disabled="disabled"/>
											</p>
									</c:when>
									<c:otherwise>
										<p>
										         <input type="hidden" name="deptCode" id="deptCode"/>
												<input type="radio" id="salesDeptCheckbox" checked="checked"
													onclick="disableOtherCondition('salesDept')" />
												销售部门:
												<input type="text" name="salesDept" id="salesDept"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent2(event)" onchange="getDeptInfo();" />
												<input id="salesDeptSelect" type="button"
													onClick="javascript:openWinCenter('销售部选择','../MSelectSvl?appname=${sgsNutrual}&pagename=deptlist', 650,400,true);tmp2=document.getElementById('deptCode');tmp=document.getElementById('salesDept');str='salesDept';disableOtherCondition('salesDept')"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
											</p>
											<p>
												<input type="radio" id="operationDirectorCheckbox"
													onclick="disableOtherCondition('operationDirector')" />
												业务主任:
												<input type="text" name="operationDirector"
													id="operationDirector" style="width: 177px"
													onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent1(event)" onchange="getOperationDirectorInfo();" />
												<input id="operationDirectorSelect" type="button"
													onClick="javascript:openWinCenter('业务主任选择窗口','../MSelectSvl?appname=${sgsNutrual}&pagename=human',650,500,true);tmp=document.getElementById('operationDirector');str='operationDirector';disableOtherCondition('operationDirector')"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
											</p>
											<p>
												<input type="radio" id="clientIdCheckbox" value="2"
													onclick="disableOtherCondition('clientId')" />
												客户名称:
												<input type="text" name="clientId" id="clientId"
													style="width: 177px" onclick="disableOtherCondition(this.id)"
													onkeyup="onKeyUpEvent(event)" onchange="getClientInfo();" />
												<input id="clientIdSelect" type="button"
													onClick="javascript:openWinCenter('客户选择窗口','../MSelectSvl?appname=${sgsNutrual}&pagename=deptlist',650,500,true);tmp=document.getElementById('clientId');str='clientId';disableOtherCondition('clientId')"
													value="..." class="btn" style="margin-left: -5px"
													onmouseover="this.className='btn_mouseover'"
													onMouseOut="this.className='btn'"
													onmousedown="this.className='btn_mousedown'"
													onMouseUp="this.className='btn'" />
										</p>									
									</c:otherwise>
									</c:choose>
								   <p <c:if test="${sgs=='kh'}">style="display: none;"</c:if>>
									<input type="radio" id="clientTypeCheckbox"
										onclick="disableOtherCondition('clientType')" />
									客户类型:
									<input type="text" name="clientType" id="clientType"
										style="width: 177px" onclick="disableOtherCondition(this.id)"
										onkeyup="onKeyUpEvent(event)" onchange="getClientInfo();" />
									<input id="clientTypeSelect" type="button" onClick="openClientType();disableOtherCondition('clientType')"
										value="..." class="btn" style="margin-left: -5px;"
										onmouseover="this.className='btn_mouseover'"
										onMouseOut="this.className='btn'"
										onmousedown="this.className='btn_mousedown'"
										onMouseUp="this.className='btn'" />
								</p>
								
								<p <c:if test="${sgs=='kh'}">style="display: none;"</c:if>>
									<input type="radio" id="marketTypeCheckbox"
										onclick="disableOtherCondition('marketType')" />
									市场类型:
									<input type="text" name="marketType" id="marketType"
										style="width: 177px" onclick="disableOtherCondition(this.id)"
										onkeyup="onKeyUpEvent(event)" onchange="getClientInfo();" />
									<input id="marketTypeSelect" type="button" onClick="openMarketType();disableOtherCondition('marketType')"
										value="..." class="btn" style="margin-left: -5px;"
										onmouseover="this.className='btn_mouseover'"
										onMouseOut="this.className='btn'"
										onmousedown="this.className='btn_mousedown'"
										onMouseUp="this.className='btn'" />
								</p>
	                </div>
	                <!-- 查询依据 -->
	                <div class="panel" id="files" style="text-align: center;" style="width:500px">
						<table>
							<tr>
								<td>
									<fieldset>
										<select id="select" name="select" multiple="multiple" size="20"
											style="width: 180px;height: 180px;" ondblclick="moveSelected1();">
											<option  value="col1">营销管理公司</option>
											<option  value="col2">销售部门</option>
											<option  value="col3">业务主任</option>
											<option  value="col4">客户</option>
											<option  value="col5">产品类别</option>
										</select>
									</fieldset>
								</td>
								<td width="">
									<div>
										<table>
											<tr>
												<td>
													<input type="button" name="add" id="add" value="←"
														style="width: 60px" onClick="moveSelected();" />
												</td>
											</tr>
											<tr>
												<td>
													<input style="width: 60px" name="delete" id="delete"
														type="button" value="→" onClick="moveSelected1();" />
												</td>
											</tr>
										</table>
									</div>
								</td>
								<td>
									<fieldset>
										<select id="unselect" name="unselect" multiple="multiple"
											size="20" style="width: 180px;height: 180px;" ondblclick="moveSelected();">
										</select>
									</fieldset>
								</td>
							</tr>
						</table>
	                </div>
	                
	                <!-- 精确查询 -->
	                <div class="panel" id="editor" style="width:500px">
	                  <div id="codeDiv" style="display: none;text-align: center;">
	                  <p>产品类别:
	                      <input type="text" id="code" name="code" />
	                      <input id="productSelect" type="button" onClick="onProductCategoriesChoice();" value="..."
								class="btn" style="margin-left: -5px"
								onmouseover="this.className='btn_mouseover'"
								onMouseOut="this.className='btn'"
								onmousedown="this.className='btn_mousedown'"
								onMouseUp="this.className='btn'" />
	                  </p>
	                  </div>
	                </div>
	             </div>
	            </div><!-- END slider -->
        </div><!-- END wrapper -->
        <div style="text-align: center;idth:500px">
        
        <input id="queryBtn" type="button" value=" 确 认 " class="btn" onclick="onQueryBtn()"/>
        </div>
    </form>
	</body>
</html>


