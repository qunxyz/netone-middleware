<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="oe.bi.datasource.DimensionAct" />
<jsp:directive.page import="oe.bi.BiEntry" />
<%@page import="oe.bi.wizard.WizardDao"%>
<%@page import="oe.rmi.client.RmiEntry"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");

	
	WizardDao wd = (WizardDao) RmiEntry.iv("bihandle");
	
	String[][] dims = wd.listTree();
	request.setAttribute("dims", dims);
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>添加项4</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<script type="text/javascript" src="<%=path%>/include/js/xtree2.js"></script>
		<script type="text/javascript"
			src="<%=path%>/include/js/xloadtree2.js"></script>
		<script type="text/javascript" src="<%=path%>/include/js/extxtree2.js"></script>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>

		<SCRIPT TYPE="text/javascript">
			//--Modify-START---可自定义修改结合您的业务需求来修订------------
			//上一步脚本
			function forward(){
				//history.go(-1);
				document.additem.action="<%=path%>/NextAnalyse.do?task=Step3";
				document.additem.submit();
			}
			//下一步脚本
			function nextsubmit(){
				//往下一个页面传递参数(基础参数parentid和pagepath是必须的)
				var param="?parentid=<%=parentid%>&pagepath=<%=pagepath%>";
				
				var dimResult = $("dimResult");
				
				var choice = document.all.choice.value;
				if(choice!=""){
					var tmplist = new List();
					for(var i=0;i<selectedDimList.size();i++){
						var node = selectedDimList.get(i);
						node.put("levelname",choice);
						tmplist.add(node);
					}
					selectedDimList=tmplist;
				}
				
				var index = dimResult.selectedIndex;
				if(index == -1){
					alert("没有选择维度数据!如果直接选择业务维度请任意选择一个维度数据");
					return ;
				} else{
					var dimResultValue = "";
					if(dimResult.options.length >0 ){
				    	for(var i=0 ; i<dimResult.options.length ; i++){
				    		if(i==0){
				    			dimResultValue = dimResult.options[i].value + "," + dimResult.options[i].text;
				    		} else {
				    			dimResultValue = dimResultValue + ";" + dimResult.options[i].value + "," + dimResult.options[i].text;
				    		}
				    	}
			    	}
			    	document.all.dimResultValue.value = dimResultValue;
					var nodeobj = selectedDimList.get(document.all.dimResult.selectedIndex);
					document.all.preps.value = nodeobj.toString();
				}
				
				if(selectedDimList.size() > 0){
					var levelname = selectedDimList.get(0).get("levelname");
					document.all.levelname.value=levelname;
				}
				
				var selectedDimValue = selectedDimList.join("#");
			
				document.all.selectedDimValue.value=selectedDimValue;
			
				document.all.seldimvalue.value = selectedDimValue;
			
				document.additem.submit();
			}
			//--Modify-END---可自定义修改结合您的业务需求来修订------------
			
			//取消脚本
			function cancelsubmit(){
				window.close();
			}
			
			//选择的维度，存放nodeobj
			var selectedDimList = new List();
			//选择的时间，存放nodeobj
			var selectedTimeList = new List();
			function selectAction_dimTreeList(){
			    clearDim(); //清除维度
				var dimTreeList = $("dimTreeList");
				if(dimTreeList.value == -1){
					//复位树节点
					dimTree.setText("维度");
			    	dimTree.src = "";
					while (dimTree.childNodes.length > 0) {
						dimTree.remove(dimTree.childNodes[dimTree.childNodes.length - 1]);
					}
				}
				else{
				    var timeLevel=$("selpoint1").value;
				    var selectDimText=dimTreeList.options[dimTreeList.selectedIndex].text;
				    var selectDim=dimTreeList.value;
				    var appinfo='';
				    var appinfoTest='';
	
					dimTree.setText(selectDimText+appinfoTest);
			    	dimTree.src = "";
			    	dimTree.setSrc("<%=basePath%>servlet/EtlTreeSvl?treeModelId="+selectDim+appinfo);
			    	dimTree.expand();
			    	clearDimSelect();
				}
			}
			function  clearDim(){
				var dimResult = $("dimResult");
				dimResult.options.length = 0 ;
				selectedDimList.items.length = 0 ;
			}
			
			function clearDimSelect(){
				var dimResult = $("dimResult");
				dimResult.options.length=0 ;
				selectedDimList.items.length = 0 ;
			}
			
			function nodeAction(nodeid , nodelevel){
				dimTreeNodeAction(nodeid,nodelevel);
			}
			
			function dimTreeNodeAction(nodeid , nodelevel){
				var node = dimTree.getSelected();
				//设置复选框变化
				changeCbxChecked(node);
				var nodeinfo = getNodeObjInfo(node);
				
				var dimResult = $("dimResult");
			    var value = nodeinfo.get("treeModelId")+"_"+nodeinfo.get("nodeid");
			    
			     var flag=isSelectAll();
					if(flag){
					 clearDim();
					}
			    if(isNodeCbxSelected(node)){
			    	//判断是否在同一等级
					if(selectedDimList.size() > 0){
						var treemodelid = nodeinfo.get("treeModelId");
						for(var k = 0 ; k<selectedDimList.size() ; k++){
							if(treemodelid == selectedDimList.get(k).get("treeModelId")){
								if(nodeinfo.get("levelname") != selectedDimList.get(k).get("levelname")){
									alert("不是同一等级的节点，无法添加到选择框中!");
									changeCbxChecked(node);
									return ;
								}else{
									break ;
								}
							}
						}
					}    	
			    	var dimTreeList = $("dimTreeList");
				    var text1 = dimTreeList.options[dimTreeList.selectedIndex].text;
				    var text2 = node.getText();
				    var text = text1+"->"+text2;
				    var option = new Option(text,value);
				     //判断重复
				    if(dimResult.options.length >0 ){
				    	var b = true;
				    	for(var i=0 ; i<dimResult.options.length ; i++){
				    		if(dimResult.options[i].value == value){
								alert("该值已存在!");
				    			b = false;
				    			break;
				    		}
				    	}
				    	if(b){
				    	    dimResult.add(option);
						    option.selected = true ;
						    selectedDimList.add(nodeinfo);
				    	}
			    	} else {
			    	 	dimResult.add(option);
					    option.selected = true ;
					    selectedDimList.add(nodeinfo);
			    	}
			    }
			    else{
				    //从下拉框中删除
				    if(dimResult.options.length >0 ){
				    	for(var i=0 ; i<dimResult.options.length ; i++){
				    		if(dimResult.options[i].value == value){
				    			do_delSelectedDim(i);
				    			break ;
				    		}
				    	}
			    	}
			    }
			}
			function treesonload(){
	
			}
			function getNodeObjInfo(node){
				var src = node.src ;
				if(src){
					var str = src.substring(src.indexOf("?")+1);
					str = str.replace(/&/g,",");
					var prepMap = new Map();
					prepMap.load(str);
					return prepMap ;
				}
			}
			
			function  isSelectAll(){    
				if(selectedDimList.size() > 0){
					for(var k = 0 ; k<selectedDimList.size() ; k++){
						if(selectedDimList.get(k).get("levelnameOld")&&selectedDimList.get(k).get("levelnameOld")!=""){				  
							return true;
						}
					}
			 	}
			 	return false;
			}
			
			
			//删除选定的维度选择值
			function btnAction_delSelDim(){
				var dimResult = $("dimResult");
				if(dimResult.selectedIndex != -1){
					do_delSelectedDim(dimResult.selectedIndex);
				}
			}
			
			function do_delSelectedDim(index){
				if(index != -1){
					var dimResult = $("dimResult");
					dimResult.options.remove(index);
					//删除selectedDimList
					selectedDimList.remove(index);
					if(dimResult.options.length==0){  //删除指标和指标组
						
					}else{
						dimResult.selectedIndex = index-1<0?0:index-1; //回到上一个维度
					}
				}
			}
	
			function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
				document.all.choice.value = name+'['+naturalname+']';
			}
			
			function selected(){
			    //if(selectedDimList.size() <= 0){
			    //	alert('请选择维度节点');
				//   return;
			    //}
				//var levelname = selectedDimList.get(0).get("levelname");

				//var pagepath = levelname.substring(levelname.indexOf("[")+1,levelname.indexOf("]"));
				window.open('<%=basePath%>SSelectSvl?pagename=datalist&appname=BUSSENV.BUSSENV.DYSER.BUSSLEVEL','_blank', 'height=380, width=600, top=0, left=0,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
				
			}
			
		function sselected(text,naturalname){
			document.all.choice.value = text+"["+naturalname+"]";
		}	
			
		function checkDim(){
				if(document.all.dynamicDim.checked){
					document.all.dynamicDim.value="true";
				} else {
					document.all.dynamicDim.value="false";
				}
			}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextAnalyse.do?task=Step" METHOD="POST"
			name="additem">
			<input type="hidden" name="parentid" value="${fo.parentid}">
			<input type="hidden" name="pagepath" value="${fo.pagepath}">
			<!-- Zero.jsp -->
			<input type="hidden" id="name" name="name" value="${fo.name}">
			<input type="hidden" id="naturalname" name="naturalname" value="${fo.naturalname}">
			<!-- BeforeFrist.jsp -->
			<input type="hidden" name="timetype" value="${fo.timetype}">
			<input type="hidden" name="selpoint1" value="${fo.selpoint1}">
			<input type="hidden" name="timelevelinfo" value="${fo.timelevelinfo}">
			<input type="hidden" name="selpoint1Text" value="${fo.selpoint1Text}">
			<!-- Frist.jsp -->
			<input type="hidden" name="preps" value="${fo.preps}">
			<input type="hidden" name="selectedDimValue"
				value="${fo.selectedDimValue}">
			<input type="hidden" name="levelname" value="${fo.levelname}">
			<input type="hidden" name="dimResultValue"
				value="${fo.dimResultValue}">
			<!-- Step.jsp -->
			<input type="hidden" name="tgids" value="${fo.tgids}">
			<input type="hidden" name="tgnames" value="${fo.tgnames}">
			<input type="hidden" name="seltgvalue" value="${fo.seltgvalue}">
			<input type="hidden" name="tgGroup" value="${fo.tgGroup}">
			<input type="hidden" name="tglistdiv" value="${fo.tglistdiv}">
			<input type="hidden" name="targetResult" value="${fo.targetResult}">
			<!-- Step1.jsp -->
			<input type="hidden" name="filtertype" value="${fo.filtertype}">
			<!-- Step2.jsp -->
			<input type="hidden" name="allchkid" value="${fo.allchkid}">
			<input type="hidden" name="alltop" value="${fo.alltop}">
			<input type="hidden" name="alltopvalue" value="${fo.alltopvalue}">
			<input type="hidden" name="allalarm" value="${fo.allalarm}">
			<input type="hidden" name="alltxtalarm" value="${fo.alltxtalarm}">
			<input type="hidden" name="tgfiltSqlvalue"
				value="${fo.tgfiltSqlvalue}">
			<!-- Step3.jsp -->
			<input type="hidden" name="exterValue" value="${fo.exterValue}">
			<input type="hidden" name="seldimvalue" value="${fo.seldimvalue}">
			<input type="hidden" name="timelevel" value="${fo.timelevel}">
			<input type="hidden" name="selpoint2" value="${fo.selpoint2}">
			<input type="hidden" name="hour" value="${fo.hour}">
			<input type="hidden" name="grownum" value="${fo.grownum}">
			<input type="hidden" name="selecttime1" value="${fo.selecttime1}">
			<input type="hidden" name="selecttime2" value="${fo.selecttime2}">
			<input type="hidden" name="unit" value="${fo.unit}">
			<input type="hidden" name="timeResult" value="${fo.timeResult}">
			<input type="hidden" name="timeResults" value="${fo.timeResults}">
			<!-- Step4.jsp -->
			<input type="hidden" name="active" value="${fo.active}">
			<!-- Step5.jsp -->
			<input type="hidden" name="showactive" value="${fo.showactive}">
			<input type="hidden" name="selcharttype" value="${fo.selcharttype}">
			<input type="hidden" name="seldatatype" value="${fo.seldatatype}">
			<input type="hidden" name="seldim" value="${fo.seldim}">
			<input type="hidden" name="seltg" value="${fo.seltg}">
			<input type="hidden" name="str" value="${fo.str}">
			<input type="hidden" name="tgstr" value="${fo.tgstr}">
			<input type="hidden" name="multichart" value="${fo.multichart}">
			<input type="hidden" name="maxvalue" value="${fo.maxvalue}">
			<input type="hidden" name="pngwidth" value="${fo.pngwidth}">
			<input type="hidden" name="showvalue" value="${fo.showvalue}">
			<input type="hidden" name="pictitle" value="${fo.pictitle}">
			<input type="hidden" name="piccolor" value="${fo.piccolor}">
			<input type="hidden" name="xqingxie" value="${fo.xqingxie}">
			<!-- Done.jsp -->
			<input type="hidden" name="act" value="${fo.act}">
			<input type="hidden" name="timespecial" value="${fo.timespecial}">
			<input type="hidden" name="sqlview" value="${fo.sqlview}">
			<input type="hidden" name="allchkid2" value="${fo.allchkid2}">
			<input type="hidden" name="allorder" value="${fo.allorder}">
			<input type="hidden" name="tgfiltvalue" value="${fo.tgfiltvalue}">
			<!-- Done2.jsp -->
			<input type="hidden" name="actcondition" value="${fo.actcondition}">
			<!-- Done3.jsp -->
			<input type="hidden" name="acturl" value="${fo.acturl}">
			<!-- Finish.jsp -->
			<input type="hidden" name="lsh" value="${fo.lsh}">
			<input type="hidden" name="tgothername" value="${fo.tgothername}">

			<%@ include file="/include/page/PageHeadInfo.jsp"%>

			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD>
							<TABLE WIDTH="100%" BORDER="0" CELLPADDING="1" CELLSPACING="0">
								<TR CLASS="OecBgColorDark">
									<TD>
										&nbsp;
									</TD>
									<TD WIDTH="100%" valign="center">
										<FONT CLASS="OecGlobalPageTitle">内容主题</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG
												SRC="<%=basePath%>/include/image/helpsb.gif" ALT="帮助"
												BORDER="0"> </A>
									</TD>
									<TD>
										&nbsp;
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%">
								<TR>
									<TD ROWSPAN="2" VALIGN="TOP">
										<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
											<TR>
												<TD VALIGN="TOP" CLASS="OecBgColorDark">

												</TD>
											</TR>
										</TABLE>
									</TD>
									<TD ROWSPAN="2" VALIGN="TOP">
										&nbsp;&nbsp;&nbsp;
									</TD>
									<TD ALIGN="RIGHT" valign="top" width="100%">
										<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
											<TR>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
														onClick="javascript:forward()">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
														onClick="javascript:nextsubmit()">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="取消"
														onClick="javascript:cancelsubmit()">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 4 步, 共 10 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>



			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">

				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						分析维度选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示:分析维度包含业务维度和时间维度，其中业务维度选择有2中方式，第一种通过维度数据的选择来间接选择业务维度，选中的维度数据将作为过滤条件来过滤最终的报表数据。第二种直接选择业务维度
				需要注意的是如果直接选择业务维度，那么可任意选择以一个维度数据，并且将任意值选中(选中任意值将在最终报表中忽略维度数据，展示出所有的业务数据)。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>

				<!-- Start--Body---- 功能内容定义  --->
				<div style="width: 100%; height: 100%; border: 1 solid #A9C0E5;">
					<!-- 维度值选择 -->
					<div
						style="width: 99%; height: 55%; margin: 0px; margin-top: 0px; border: 0 solid #A9C0E5">
						维度数据选择:
						<select name="dimTreeList" style="width: 90px"
							onchange="selectAction_dimTreeList();">
							<option value="-1">
								---请选择---
							</option>
							<c:forEach items="${dims}" var="dim">
								<option value="${dim[0]}" ${fo.dimTreeList==dim[0]?"selected":""}>
									${dim[1]}
								</option>
							</c:forEach>
						</select>
						<!-- 时间粒度选择：-->
						<select name="selpoint1" disabled="disabled">
							<option value="1" ${fo.selpoint1==1?"selected":""}>
								年
							</option>
							<option value="2" ${fo.selpoint1==2?"selected":""}>
								月
							</option>
							<option value="3" ${fo.selpoint1==3?"selected":""}>
								日
							</option>
							<option value="4" ${fo.selpoint1==4? "selected":fo.selpoint1==''?"selected":""}>
								时
							</option>
						</select>
						<br>
						<!-- 选择模式: -->
						<select name="type" style="width: 90px; display: none">
							<option value="0"}>
								选择任意节点
							</option>
							<option value="1"}>
								选择层次节点
							</option>
						</select>
						<br>
						<table width="100%" height="90%" cellpadding="0" cellspacing="1"
							align="center" id="table1">
							<tr>
								<td valign="top">
									<div id="dimTreeDiv" style="width: 100%; height: 100%">
										<script type="text/javascript">
											var dimTree = new WebFXLoadTree('维度');
											dimTree.write();
										</script>
									</div>
								</td>
								<td height="100%">
									已选择的维度数据： &nbsp;&nbsp;&nbsp;
									<input type="button" class="butt" value="删除选定"
										onclick="btnAction_delSelDim()">
									<br>
									<select size="5" name="dimResult" style="width: 95%">
									</select>
								</td>
							</tr>
							<tr>
								<td>
								</td>
								<td>
									<input type="text" name="choice" value="${fo.choice}">
									<input type="button" class="butt" value="选择业务维度"
										onclick="selected();">
									<input type="checkbox" name="dynamicDim" value="${fo.dynamicDim}" onclick="checkDim();" ${fo.dynamicDim=="true"?"checked":""}>
									动态值
								</td>
							</tr>
						</table>
					</div>
					<!-- End--Body---- 功能内容定义  --->

				</DIV>

				<CENTER>
					<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
						<TR>
							<TD ALIGN="RIGHT" valign="top" width="100%">
								<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
									<TR>
										<TD>
											<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
												onClick="javascript:forward()">
										</TD>
										<TD>
											<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
												onClick="javascript:nextsubmit()">
										</TD>
										<TD>
											<INPUT TYPE="button" NAME="p_request" VALUE="取消"
												onClick="javascript:cancelsubmit()">
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD>
								<%@ include file="/include/page/PageEndInfo.html"%>
							</TD>
						</TR>
					</TABLE>
				</CENTER>
				<script type="text/javascript">
					if(document.all.dimTreeList.value!='-1'){
						selectAction_dimTreeList();
						
						var dimResult = $("dimResult");
						var dimResultValue = document.all.dimResultValue.value;
						if(dimResultValue!=""){
							dimResultValue = dimResultValue.split(";");
							for(var i = 0;i<dimResultValue.length;i++){
								var d = dimResultValue[i].split(",");
								var option = new Option(d[1],d[0]);
			    				dimResult.add(option);
			    				option.selected = true ;
							}
						}
						var selectedDimValue = document.all.selectedDimValue.value;
						var selectedDimList = new List();
						if(selectedDimValue!=""){
							selectedDimValue = selectedDimValue.split("#");
							for(var i = 0;i<selectedDimValue.length;i++){
								var prepMap = new Map();
								prepMap.load(selectedDimValue[i]);
								selectedDimList.add(prepMap);
							}
						}
					}
				</script>
		</FORM>
	</BODY>
</HTML>
