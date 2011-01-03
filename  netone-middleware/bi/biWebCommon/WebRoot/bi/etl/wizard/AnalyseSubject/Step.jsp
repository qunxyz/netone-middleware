<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>添加项5</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
						<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			//history.go(-1);
			document.additem.action="<%=path%>/NextAnalyse.do?task=First";
			document.additem.submit();
		}
		//下一步脚本
		function nextsubmit(){
			//往下一个页面传递参数(基础参数parentid和pagepath是必须的)
			var param="?parentid=<%=parentid%>&pagepath=<%=pagepath%>";
			
			if(selectedTgList.size()==0){
				alert("没有选择指标!");
				return false;
			}
			if(selectedTgList.size() > 16){
				alert("选择的指标不能超过16个,请重新选择!");
				return false;
			}
			var namelist = new List();
			for(var i=0 ; i<selectedTgList.size() ; i++){
				namelist.add(tgIdNameMap.get(selectedTgList.get(i)));
			}
			document.all.seltgvalue.value=selectedTgList.toString();
			document.all.tgids.value=selectedTgList.toString();
			document.all.tgnames.value = namelist.toString();
			document.additem.submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//选择的指标，存放指标id
		var selectedTgList = new List();
		
		var selectedTgGroup = null;
		
		var tgIdNameMap = new Map();
		
		function selectAction_tgGroup(){
			do_changeTgList();
		}

		function success_do_changeTgGroup(xmlhttp){
			var restr = xmlhttp.responseText;
			var map = new Map();
			map.load(restr);
			do_updateTgGroup(map);
		}
		
		function failure_do_changeTgGroup(xmlhttp){
			alert("该节点没有定义指标组，请选择其他节点！");
		}
		
		function do_updateTgGroup(map){
			var keyarr = map.keyarr;
			var tgGroup = $("tgGroup");
			tgGroup.options.length = 0 ;
			
			for(var i=0 ; i<keyarr.length ; i++){
				tgGroup.options.add(new Option(map.get(keyarr[i]),keyarr[i]));
			}
			//更新完指标组更新指标列表
			do_changeTgList();
		}
		
		//更新指标列表
		function do_changeTgList(){
			var tgGroup = $("tgGroup");
		 	var index = tgGroup.selectedIndex;
		 	if(index != -1 ){
		 		var url = "<%=basePath%>servlet/EtlTargetSelectSvl2";
				var pars = "method=targetElementList&groupid="+encodeURI(tgGroup.value) ; 
				var tgajax = new Ajax.Request(
			                 url,
			                 {method: 'get', parameters: pars, onSuccess: success_do_changeTgList, onFailure: failure_do_changeTgList}
			                 );
		 	}
		 	else{
		 		//判断下拉框中是否有值
		 		if(tgGroup.options.length==0){
		 			var tglistdiv = $("tglistdiv");
		 			tglistdiv.innerHTML = "";
		 		}
		 	}
		}

		function success_do_changeTgList(xmlhttp){
			var tglistdiv = $("tglistdiv");
			var tgGroup = $("tgGroup");
			tglistdiv.innerHTML = "";
			var restr = xmlhttp.responseText;
			var map = new Map();
			map.load(restr);
			var keyarr = map.keyarr;
			
			for(var i=0 ; i<keyarr.length ; i++){
				var cbx = document.createElement("input") ;
				cbx.type = "checkbox" ;
				cbx.attachEvent("onclick",cbxAction_tgcbx);
				cbx.value = keyarr[i];
				tglistdiv.appendChild(cbx);
				//if(tgGroup.value == selectedTgGroup){
					if(selectedTgList.indexOf(cbx.value+":"+tgGroup.value) != -1){
						cbx.checked = true;
					}
				//}
				tglistdiv.appendChild(document.createTextNode(map.get(keyarr[i])));
				tglistdiv.appendChild(document.createElement("br"));
			}
		}
	
		function failure_do_changeTgList(xmlhttp){
			alert("读取指标组中的指标出错！");
		}
		
		
		//点击指标删除事件
		
		function btnAction_delSelTarget(){
		var targetResult = $("targetResult");
			while(targetResult.selectedIndex != -1){
				do_delSelectedTarget(targetResult.selectedIndex);
			}
		
		}
		
		//点击指标事件
		function cbxAction_tgcbx(){
			var tgcbx = event.srcElement;
			var tgGroup = $("tgGroup");
			var targetResult=$("targetResult");
			var name = tgcbx.nextSibling.nodeValue;
			var value=tgcbx.value+":"+tgGroup.value;
			if(tgcbx.checked){
				/*	if(tgGroup.value != selectedTgGroup){
				selectedTgList.items.length = 0 ;
				selectedTgGroup = tgGroup.value ;
				}
				*/		
				selectedTgList.add(value);
				targetResult.add(new Option(name.substring(0,name.indexOf("{")),value));
				//添加到tgIdNameMap
				if(tgIdNameMap.indexOf(value) == -1){
					tgIdNameMap.put(value,name);
				}
			}
			else{
			 //从下拉框中删除
			    if(targetResult.options.length >0 ){
			    	for(var i=0 ; i<targetResult.options.length ; i++){
			    		if(targetResult.options[i].value == value){
			    			do_delSelectedTarget(i);
			    			break ;
			    		}
			    	}
		    	}
			}
		}
		
		function do_delSelectedTarget(index){
			if(index != -1){
				var targetResult = $("targetResult");
				targetResult.options.remove(index);
				selectedTgList.remove(index);
			}
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextAnalyse.do?task=Step1" METHOD="POST"
			name="additem">
			<input type="hidden" name="parentid" value="${fo.parentid}">
			<input type="hidden" name="pagepath" value="${fo.pagepath}">
			<!-- Zero.jsp -->
			<input type="hidden" name="name" value="${fo.name}">
			<input type="hidden" name="naturalname" value="${fo.naturalname}">
			<!-- BeforeFrist.jsp -->
			<input type="hidden" name="timetype" value="${fo.timetype}">
			<input type="hidden" name="selpoint1" value="${fo.selpoint1}">
			<input type="hidden" name="timelevelinfo" value="${fo.timelevelinfo}">
			<input type="hidden" name="selpoint1Text" value="${fo.selpoint1Text}">
			<!-- Frist.jsp -->
			<input type="hidden" name="dynamicDim" value="${fo.dynamicDim}">
			<input type="hidden" name="preps" value="${fo.preps}">
			<input type="hidden" name="selectedDimValue"
				value="${fo.selectedDimValue}">
			<input type="hidden" name="levelname" value="${fo.levelname}">
			<input type="hidden" name="dimResultValue"
				value="${fo.dimResultValue}">
			<input type="hidden" name="type" value="${fo.type}">
			<input type="hidden" name="dimTreeList" value="${fo.dimTreeList}">
			<input type="hidden" name="choice" value="${fo.choice}">
			<input type="hidden" name="dimTreeDiv" value="${fo.dimTreeDiv}">
			<input type="hidden" name="dimResult" value="${fo.dimResult}">
			<!-- Step.jsp -->
			<input type="hidden" name="tgids" value="${fo.tgids}">
			<input type="hidden" name="tgnames" value="${fo.tgnames}">
			<input type="hidden" name="seltgvalue" value="${fo.seltgvalue}">
			<!-- Step1.jsp -->
			<input type="hidden" name="filtertype" value="${fo.filtertype}">
			<!-- Step2.jsp -->
			<input type="hidden" name="allchkid" value="${fo.allchkid}">
			<input type="hidden" name="alltop" value="${fo.alltop}">
			<input type="hidden" name="alltopvalue" value="${fo.alltopvalue}">
			<input type="hidden" name="allalarm" value="${fo.allalarm}">
			<input type="hidden" name="alltxtalarm" value="${fo.alltxtalarm}">
			<input type="hidden" name="tgfiltSqlvalue" value="${fo.tgfiltSqlvalue}">
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
										<FONT CLASS="OecGlobalPageTitle">内容主题 STEP</FONT>
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
										<FONT CLASS="OecInlineInfo">第 5 步, 共 10 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 然后单击“下一步”。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<div id="targetdiv"
					style="width: 100% ; height: 75% ;  background-color: #EDF0F6 ; overflow: auto ; border: 1 solid #A9C0E5 ;">
					<table>
						<tr>
							<td>
								指标组：
								<select id="tgGroup" name="tgGroup" onChange="selectAction_tgGroup()">

								</select>
							</td>
							<td>
								指标选择的结果：&nbsp;
								<input type="button" class="butt" value="删除选定"
									onclick="btnAction_delSelTarget()">
							</td>
						</tr>
						<tr>
							<td>
								<div id="tglistdiv">
									<script type="text/javascript">
										var preps = document.all.preps.value;
										if(preps!=''){
											preps = preps.replace(/,/g,"&");
											var url = "<%=basePath%>servlet/EtlTargetSelectSvl";
											var pars = document.all.timelevelinfo.value;
											pars = pars + "method=targetGroupList&"+preps;
											var tgajax = new Ajax.Request(url,{method: 'get', parameters: pars, onSuccess: success_do_changeTgGroup, onFailure: failure_do_changeTgGroup});
										}
									</script>
								</div>
							</td>
							<td>
								<div style="margin-top: 8;">
									<select size="6" name="targetResult" style="width: 95%"
										multiple="multiple">
									</select>
								</div>
							</td>
						</tr>
					</table>


				</div>


				<!-- End --Body---- 功能内容定义  --->
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
				if(document.all.tgids.value!="" && document.all.tgnames.value!=""){
					var targetResult=$("targetResult");
					var selectedTgList = new List();
					var tgIdNameMap = new Map();
					var tgids = document.all.tgids.value.split(",");
					var tgnames = document.all.tgnames.value.split(",");
					for(var i=0;i<tgids.length;i++){
						selectedTgList.add(tgids[i]);
						targetResult.add(new Option(tgnames[i],tgids[i]));
						//添加到tgIdNameMap
						if(tgIdNameMap.indexOf(tgids[i]) == -1){
							tgIdNameMap.put(tgids[i],tgnames[i]);
						}
					}
				}
			</script>
		</FORM>
	</BODY>
</HTML>
