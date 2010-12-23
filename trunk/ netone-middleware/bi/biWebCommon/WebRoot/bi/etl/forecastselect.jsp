<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="oe.bi.analysis.BiForcast"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>预测分析条件选择框</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/include/css/css.css">
		<style type="text/css">
	 BODY {
	        BORDER-TOP-WIDTH: 4px; 
			SCROLLBAR-FACE-COLOR: #EDF0F6; 
			BORDER-LEFT-WIDTH: 4px; 
			FONT-SIZE: 9pt; 
			BORDER-BOTTOM-WIDTH: 4px; 
			SCROLLBAR-HIGHLIGHT-COLOR: #ffffff; 
			SCROLLBAR-SHADOW-COLOR: #B3CDEA; 
			SCROLLBAR-3DLIGHT-COLOR: #B3CDEA; 
			SCROLLBAR-ARROW-COLOR: #B0C7E1; 
			SCROLLBAR-TRACK-COLOR: #F7FBFF; 
			SCROLLBAR-DARKSHADOW-COLOR: #ffffff; 
			SCROLLBAR-BASE-COLOR: #EEF7FF; 
			BORDER-RIGHT-WIDTH: 4px;
	}
	</style>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/etl/etlmain.js"></SCRIPT>
		<script type="text/javascript" src="<%=path%>/include/js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/biutil.js"></SCRIPT>
		<SCRIPT type="text/javascript">
		var contextpath = "<%=path%>" ;
		var _SRC_DATAMODELID = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_DATAMODELID%>";
		var _SRC_DIMENSIONID = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_DIMENSIONID%>";
		var _SRC_DIMENSIONVALUE = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_DIMENSIONVALUE%>";
		var _SRC_ANALYSIS_TYPE = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_ANALYSIS_TYPE%>";
		var _ONLY_ONE_TIMEDATA = "<%=BiForcast._ONLY_ONE_TIMEDATA%>";
	</SCRIPT>
		<script type="text/javascript">
    
        function el(i) {
  			return document.getElementById(i);
		}
		
		
		function setdiv(){
			var par = self.opener.parent;
    		var pardoc = par.document;
    		var divhtml = pardoc.getElementById("forecastdiv").innerHTML;
    		var div = el("forecastdiv");
    		div.innerHTML = divhtml;
    		
    		
    		//设置显示的维度
    		
    		var cbdims = pardoc.getElementsByName("cbdim");
    		for(i=0 ; i<cbdims.length ; i++){
    			var dimid = cbdims[i].id;
    			if(cbdims[i].checked){
    				el("fc_"+dimid).style.display="block";
    			}
    			else{
    				el("fc_"+dimid).style.display="none";
    			}
    		}
    		
    		//设置指标显示
    		/**
    		var cbtgs = pardoc.getElementsByName("cbtg");
    		for(i=0 ; i<cbtgs.length ; i++){
    			var tgid  = cbtgs[i].id;
    			if(cbtgs[i].checked){
    				el("fc_"+tgid).style.display="block";
    			}
    			else{
    				el("fc_"+tgid).style.display="none";
    			}
    		}
    		**/
    		
		}
		
		
    	
    	function submitforecast(){
    		var cbdims = document.getElementsByName("fc_dim_cbx");
    		var restr = "";
    		for(i=0 ; i<cbdims.length ; i++){
    			var dimid = cbdims[i].value;
    			if(cbdims[i].checked){
    				restr += dimid+",";
    			}
    		}
    		if(restr){
    			self.opener.doforecast(restr);
    		}
    		window.close();
    	}
		
		/*****************************/
		function checkseldim(){
    		var sels = document.getElementsByName("seldim");
    		var nosel = 0
    		for(i=0 ;i<sels.length ; i++){
    			if(sels[i].value == "-1"){
    				nosel++;
    				if(sels[i].options.length == 2){
    					alert(_ONLY_ONE_TIMEDATA);
    					return false;
    				}
    			}
    		}
    		
    		if(nosel!=1){
    			alert("必须有且只有一个时间维度可以被设置成未选择！");
    			return false;
    		}
    		return true ;
    	}
		
		
		function setdimtree(){
			if(checkseldim()){
		      	  var seldims = document.getElementsByName("seldim");
		      	  var offsetdim ;
		      	  var str="";

		      	  for(i=0 ;i<seldims.length ; i++){
		      	  	 if(seldims[i].value == "-1"){
		      	  	 	offsetdim=seldims[i].id;
		      	  	 }
		      	  	 else{
		      	  	 	str += seldims[i].id+"="+seldims[i].value +",";
		      	  	 }
		      	  }
		      	  
		      	  
		      	  //alert(str);
		      	  //alert("offsetdim:"+offsetdim)
		      	  
		      	  //level为时间的level
		      	  if(document.all.flowpage.value==""){
		      	  	 var level  =  self.opener.parent.getselTimelevel();
		      	  } else {
		      	  	 var level  =  document.all.timelevel.value;
		      	  }
		      	  
		      	 // alert(level);
		      	  
		      	  //alert("level:"+level);
		      	  if(document.all.flowpage.value==""){
		      	  	 var datamodelid = self.opener.parent.document.all.datamodelid.value;
		      	  } else {
		      	  	 var datamodelid = document.all.dataModelid.value;
		      	  }
		      	 
		      	  //alert("datamodelid:"+datamodelid);
					
				  var offdimsel =  el(offsetdim);
				  var l = offdimsel.length;	
				  var offdimstr = ""
				  //第一个值不要
				  for(i=1 ;i<l ; i++){
				  	offdimstr += offdimsel.options[i].value+",";
				  }
		      	  
		      	  tree.setText(el(offsetdim).previousSibling.previousSibling.innerText);
		      	  tree.src = "";
		      	  tree.setSrc("<%=path%>/servlet/EtlTreeSvl?"+_SRC_DIMENSIONID+"="+offsetdim+"&"+"fc_level="+level+"&"+_SRC_DATAMODELID+"="+datamodelid+"&fc_otherdim="+str+"&fc_offdimstr="+offdimstr+"&"+_SRC_DIMENSIONVALUE+"=root&"+_SRC_ANALYSIS_TYPE+"=1");
    			            
    			  tree.expand();
    	
		      	  
				  //删除以设置的值。
				  document.all.selvalue.value = "";
				  
					
			}
		}
		
		
		function nodeAction(id,nodelevel) {
			var node = tree.getSelected();
    		//设置复选框的变化
    		var ischecked = changeCbxChecked(node);
    		
    		//选择时设置值;
    		var value = document.all.selvalue.value;
    		if(ischecked){
    			document.all.selvalue.value += id+",";
    		}
    		else{
    			var i = value.indexOf(id+",");
    			if(i!=-1){
		    			var j = value.indexOf(",",i);
		    			document.all.selvalue.value = value.substring(0,i)+value.substring(j+1);
    			}
    		}
    		
    		//alert(document.all.selvalue.value);
		}
		
		
		function treesonload(node){
		
		}
		
		
		function submitSel(){
			if(checkseldim()){
		      	  var seldims = document.getElementsByName("seldim");
		      	  var offsetdim ;
		      	  var str = "";
		      	  for(i=0 ;i<seldims.length ; i++){
		      	  	 if(seldims[i].value == "-1"){
		      	  	 	offsetdim=seldims[i].id;
		      	  	 }
		      	  	 else{
		      	  	 	str += seldims[i].id+"="+seldims[i].value +",";
		      	  	 }
		      	  }
		      	  
		      	  //alert("offsetdim:"+offsetdim)
		      	  
		      	 if(document.all.flowpage.value==""){
		      	  	 var level  =  self.opener.parent.getselTimelevel();
		      	  } else {
		      	  	 var level  =  document.all.timelevel.value;
		      	  }
		      	  
		      	  
		      	  //alert("level:"+level);
		      	  
		      	 if(document.all.flowpage.value==""){
		      	  	 var datamodelid = self.opener.parent.document.all.datamodelid.value;
		      	  } else {
		      	  	 var datamodelid = document.all.dataModelid.value;
		      	  }
		      	  
		      	  //alert("datamodelid:"+datamodelid);
					
				  var offdimsel =  el(offsetdim);
				  var l = offdimsel.length;	
				  var offdimstr = ""
				  //第一个值不要
				  for(i=1 ;i<l ; i++){
				  	offdimstr += offdimsel.options[i].value+",";
				  }
		      	
		      	document.all.selvalue.value = selectedTimeList.join("#") ;  
				
				var form = self.opener.document.vmcreateform;
				//submitvalue的值为: 预测算法;预测维度;预测值;预测值等级;模型id;其他维度值;选择的维度的当前维度值;
				var submitvalue = document.all.art.value+";"
								+ offsetdim + ";"
								+ document.all.selvalue.value + ";"
								+ level+ ";"
								+ datamodelid + ";"
								+ str + ";" 
								+ offdimstr +";";
				form.forecastvalue.value = submitvalue;
				
				var modifyvalue='';
				var number = /^(-\+)?([0-9]+)\.?([0-9]*)$/;
				<c:forEach items="${tarnameid}" var="idname">
				    if(document.getElementById('${idname[0]}').value!=''){
				    if(!document.getElementById('${idname[0]}').value.match(number)){
				    	alert('预测修正系数必须是数字,如果不使用修正系数请清空输入框的值');
				    	return;
				    }
				    	modifyvalue+="${idname[0]}:" + document.getElementById('${idname[0]}').value+";";
				    }
					
				</c:forEach>
				form.modifyvalue.value=modifyvalue;

				form.submit();
				window.close();
			}
		}
		
		function submitForcastTime(){
			
			if(document.all.flowpage.value==""){
	      	  	var indexdoc = self.opener.parent.document;
	      	  	
	      	  	var time1=document.all.selecttime1.value;
	      	  	var time2=document.all.selecttime2.value;
	      	  	
	      	  	if(time2==''){
	      	  		alert('请选择预测的结束时间点');
	      	  		return ;
	      	  	}

	      	  	if(time2.substring(11,13)!=time1.substring(11,13)){
	      	  		alert('选择时间段不正确,结束时段必须与开始时段一致');
	      	  		return;
	      	  	}
	      	  	if(time2.substring(0,13)==time1.substring(0,13)){
	      	  		alert('无法预测,结束时间与开始时间不可以相同');
	      	  		return;
	      	  	}	      	  	
				var regS = new RegExp("-","gi");
	      	  	if(parseInt(time1.replace(regS,'')) > parseInt(time2.replace(regS,''))){
					alert("结束时间不能小于分析的起始时间！");
					return ;
				}
				document.all.timeSelectType.value = indexdoc.all.timeSelectType.value ;
				document.all.hour.value = indexdoc.all.hour.value ;
	      	  } else {
	      	  	 if(document.all.selecttime1.value < document.all.endTime.value){
					alert("起始时间不能小于分析的结束时间！");
					return ;
				 }
				 document.all.timeSelectType.value = "0";
				 document.all.hour.value = document.all.hours.value ;
	      	  }
	      	 
			
			btnAction_submitCalendar()
		}
		
		function changes(value){
			var ajax = xmlhttp("forecastSelect.do?task=ajax&seldim="+value);
			var resp = ajax.responseText;
			document.all.endTime.value=resp;
			document.all.selecttime1.value=resp;
		}
	</script>

	</head>

	<body>

		<script type="text/javascript">
  		var closestr = "${closesmg}";
  		if(closestr){
  			alert(closestr);
  			window.close();
  		}
  	</script>

		<input type="hidden" name="flowpage" value="${flowpage}">
		<input type="hidden" name="endTime" value="${endTime}">
		<input type="hidden" name="timelevel" value="${timelevel}">
		<input type="hidden" name="dataModelid" value="${dataModelid}">
		<input type="hidden" name="hours" value="${hour}">

		<input type="hidden" value="${timedimstr}" name="timedimstr">

		<div id="forecastdiv">
		</div>


		请选择预测分析定制条件：
		<table width="100%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#A9C0E5" align="center">

			<tr class="td-bg01">
				<td bgcolor="#FFFFFF" align="left">
					步骤一:过滤时间维度(请给所有无须预测的时间维度选择值)
				</td>
			</tr>

			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="left">
					<c:forEach items="${timedimlist}" var="dm">
						<div style="float:left ; margin-right: 5px">
							<span>${dm[1]}</span>
							<select id="${dm[0]}" name="seldim"${timeseldisabled}>
								<OPTION value="-1">
									未选择
								</OPTION>
								<c:forTokens items="${dm[2]}" delims="," var="dmv">
									<option value="${dmv}">
										${dmv}
									</option>
								</c:forTokens>
							</select>
							&nbsp;
						</div>
					</c:forEach>

					<!-- 
  					<input type="button" value="确定" class="butt" onclick="checkseldim()">
  					-->
				</td>
			</tr>


			<tr class="td-bg01">
				<td bgcolor="#FFFFFF" align="left">
					步骤二:确定其他维度值
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="left">
					<c:forEach items="${otherdimlist}" var="dm">
						<div style="float:left ; margin-right: 5px">
							<span>${dm[1]}</span>
							<select id="${dm[0]}" name="seldim" onchange="changes(this.value);">
								<c:forTokens items="${dm[2]}" delims="," var="dmv">
									<option value="${dmv}">
										${dmv}
									</option>
								</c:forTokens>
							</select>
							&nbsp;
						</div>
					</c:forEach>
					<!-- 
  					<input type="button" value="确定" class="butt" onclick="setdimtree();">
  					-->
				</td>
			</tr>
			<tr class="td-bg01">
				<td bgcolor="#FFFFFF" align="left">
					步骤三:选择未来的时间点
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" height="200px">
					<div style="height:100% ; width:100% ; display:none" align="left">
						<script type="text/javascript">
	  						
						</script>
					</div>

					<div style="height:100% ; width:100%" align="left">
						<table width="100%" height="100%" border="0" cellpadding="0"
							cellspacing="1" align="center">
							<tr>
								<td valign="top">
									<div id="timeTreeDiv"
										style="width:100%;height:100%;overflow:auto;display=none">
										
									</div>

									<div id="calendardiv" style="width:100%">

										<!-- 时间粒度选择：-->
										<select id="selpoint1" style="display:none ">
											<option value="1">
												年
											</option>
											<option value="2">
												月
											</option>
											<option value="3">
												日
											</option>
											<option value="4" selected>
												时
											</option>
											<option value="5">
												分
											</option>
											<option value="6">
												秒
											</option>
										</select>
										<div style="margin-bottom: 5;display:none">
											时间选择：
											<select id="timeSelectType"
												onchange="selectAction_timeSelectType()">
												<option value="0">
													分段时间选择
												</option>
												<option value="1">
													连续时间选择
												</option>
											</select>
										</div>
										起始
										<input type="text" name="selecttime1"
											style="width:120" readonly>
										<br>
										结束
										<input type="text" name="selecttime2" onFocus="calendar();"
											style="width:120">
										<input type="button" class="butt" value="确定"
											onclick="submitForcastTime()">

										<div style="margin-top: 5;display:none">
											分时段（小时为单位）
											<select id="hour">
												<script type="text/javascript">
															for(var i=0 ; i<24 ; i++){
																document.writeln("<option value='"+i+"'>"+i+"</option>");
															}
														</script>
											</select>
										</div>
										<!-- 间隔类型：增长类型-->
										<select id="selpoint2" style="display:none ">
											<option value="1">
												年
											</option>
											<option value="2">
												月
											</option>
											<option value="3" selected>
												日
											</option>
											<option value="4">
												时
											</option>
											<option value="5">
												分
											</option>
											<option value="6">
												秒
											</option>
										</select>
										<!-- 连续 -->
										<input name="growtype" type="radio" checked
											onclick="changegrowtype(1);" style="display:none ">
										<!-- 分段 -->
										<input name="growtype" type="radio"
											onclick="changegrowtype(2);" style="display:none ">
										<div style="display:none" id="intervaldiv">
											步长：
											<input type="text" value="1" id="grownum" style="width:50px">
										</div>


									</div>

								</td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td>
												<div style="margin-top: 8;width: 200">
													选择的结果：&nbsp;&nbsp;&nbsp;
													<input type="button" class="butt" value="删除选定"
														onclick="btnAction_delSelTime()" style='display:none'>
													<br>
													<select size="8" id="timeResult" style="width: 95%"
														multiple="multiple">
													</select>
												</div>
											</td>

											<td>
												预测结果修正系数:
												<table>
													<c:forEach items="${tarnameid}" var="idname">
														<tr>
															<td>
																${idname[1]}
															</td>
															<td>
																<input type='text' value='' name='${idname[0]}' />
															</td>
														</tr>
													</c:forEach>
												</table>
											</td>
										</tr>
									</table>

								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr class="td-bg01">
				<td bgcolor="#FFFFFF" align="left">
					步骤四: 选择预测算法
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="left">
					<select name="art">
						<c:forEach items="${arts}" var="art">
							<option value="${art[0]}">
								${art[1]}
							</option>
						</c:forEach>
					</select>
					&nbsp;
					<input type="button" value="执行预测分析" onclick="submitSel();"
						class="butt">
					<input type="button" value="关闭" onclick="window.close();"
						class="butt">
				</td>
			</tr>
		</TABLE>


		<input type="hidden" value="" name="selvalue">

		<script type="text/javascript">
    		//设置起始时间为主页的结束时间。
    		if(document.all.flowpage.value==""){
    			document.all.selecttime1.value=self.opener.parent.document.all.selecttime2.value;
    		} else {
    			document.all.selecttime1.value=document.all.endTime.value;
    		}
    		
    	</script>

	</body>
</html>
