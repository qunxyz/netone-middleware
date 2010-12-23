<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>添加项3</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<script type="text/javascript" src="<%=path%>/include/js/calendar.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			//history.go(-1);
			document.additem.action="<%=path%>/NextAnalyse.do?task=BeforeFirst";
			document.additem.submit();
		}
		//下一步脚本
		function nextsubmit(){
			var param="?parentid=<%=parentid%>&pagepath=<%=pagepath%>";
			
			
			if(selectedTimeList.size() == 0){
				alert("没有选择时间!");
				return ;
			}
			var selectedDimValue = document.all.selectedDimValue.value;
			var timelevel=null;
			if(selectedTimeList.size()>0){
				timelevel = selectedTimeList.get(0).get("levelname");
			}
			var selectedTimeValue = selectedTimeList.join("#");
			var seldimvalue = selectedDimValue+"#"+selectedTimeValue;
			var exterValue = "";
			var dimTreeList = document.all.dimTreeList.value;
			var levelname = document.all.levelname.value;
			if(dimTreeList=="RADIO_TREE_G_HW"||dimTreeList=="RADIO_TREE_G_MOTO"||dimTreeList=="RADIO_TREE_G_ZTE"){
				if(levelname!=""&&levelname!="省PLMN"&&levelname!="REGION"){
					exterValue =getManufacturerValue(dimTreeList);
				}
			}
			document.all.exterValue.value=exterValue;
			document.all.seldimvalue.value=seldimvalue;
			document.all.timelevel.value=timelevel;
			var tmp;
			var timeResult = document.all.timeResult;
			for(var i=0;i<timeResult.length;i++){
				if(i==0){
					tmp = timeResult.options[i].value;
				} else {
					tmp = tmp + "," + timeResult.options[i].value;
				}
			}
			document.all.timeResults.value=tmp;
			document.additem.submit();
			
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//选择的时间，存放nodeobj
		var selectedTimeList = new List();
		
		function btnAction_submitCalendar(){
			var valuex=document.getElementById('grownum').value;
			if(valuex==null || valuex==0){
				alert('步长不允许为空或小于1');
				return;
			}
			submitCalendar();
		}
		function submitCalendar(){
	  		var selpoint1 = document.all.selpoint1.value;
	  		var startTime = $("selecttime1").value;
	  		var endTime = $("selecttime2").value;
	  		var selpoint2 = $("selpoint2").value;
	  		var interval = $("grownum").value;
	  		if(interval==''){
	  			alert("没有指定步长!")
	  			return false;
	  		}
	  		if(startTime==''){
	  			alert('未选择开始时间');
	  			return;
	  		}
	  		if(endTime==''){
	  			alert('未选择结束时间');
	  			return;  		
	  		}
	  		var startHour=startTime.substring(11,13);
	  		var endHour=endTime.substring(11,13);
	  		if(endHour!=startHour){
	  			alert('开始时间的时段 与结束时间的时段必须相同!');
	  			return;  			
	  		}
	  		var hour = $("hour").value;
	  		if(hour.length == 1){
	  				hour = "0"+hour ;
	  		}
	  		
	  		if(hour!=endHour){
	  			alert('时段与结束时间中的时段不同');
	  			return;  		
	  		}
  		
			var regS = new RegExp("-","gi");
      	  	if(parseInt(startTime.replace(regS,'')) >= parseInt(endTime.replace(regS,''))){
				alert("结束时间不能小于等于分析的起始时间！");
				return ;
			}
			
	  		//
	  		var timeSelectType = $("timeSelectType");
	  		if(timeSelectType.value == 0){
	  			//分段选择
	  			startTime = startTime.substring(0,11)+hour+startTime.substring(13);
	  		}
	  		else{
	  			//连续选择
	  			selpoint2 = 4 ;  //设置为小时
	  		}
	  		
	  		if(startTime && endTime){
	  			var prestr1 = "["+(parseInt(selpoint1)+1)+"]";
	  			var prestr2 = "["+startTime.match(getRegExpStr(selpoint1)[0])+"$"
	  						 + (parseInt(selpoint1)+1)+"&"
	  						 + (parseInt(startTime.match(getRegExpStr(selpoint1)[1])[1])+document.all.selpoint1Text.value+",")
	  						 +"]";
	  			var prestr3 = "["+selpoint1+",]";
	  			var prestr = prestr1+prestr2+prestr3 ;
	  			var endtimeprestr = endTime.match(getRegExpStr(selpoint1)[0]);
	  			var src = "<%=basePath%>servlet/EtlTimeSelectSvl?prestr="+encodeURIComponent(prestr)+"&seltype="+selpoint2+"&interval="+interval+"&endTime="+endtimeprestr;
				var rexml = xmlhttp(src);
				var restr = rexml.responseText;
				var times = restr.split("#");
				var timeResult = $("timeResult");
				timeResult.options.length = 0 ;
				selectedTimeList.items.length = 0 ;
				for(var k =0 ; k<times.length ; k++){
					var map = new Map();
					map.load(times[k]);
					var nodeid = map.get("nodeid");
					timeResult.add(new Option(nodeid,nodeid));
					selectedTimeList.add(map);
				}
	  		} else{
	  			alert("未选择时间范围！")
	  		}
		}
		
		function btnAction_delSelTime(){
			var timeResult = $("timeResult");
			while(timeResult.selectedIndex != -1){
				timeResult.options.remove(timeResult.selectedIndex);
				//删除selectedDimList
				selectedTimeList.remove(timeResult.selectedIndex);
			}
		}
		
		function getManufacturerValue(manufacturer){
			if(manufacturer=="RADIO_TREE_G_HW")
				return " and manufacturer='HUAWEI' ";
			if(manufacturer=="RADIO_TREE_G_MOTO")
				return " and manufacturer='MOTOROLA'";
			if(manufacturer=="RADIO_TREE_G_ZTE")
				return " and manufacturer='ZHONGXING' ";
			else
				return "";
		
		}

		function getRegExpStr(selpoint){
			if(selpoint == 1){
				return [/\d{4}/ , /\d{4}/];
			}
			else if(selpoint == 2){
				return [/\d{4}-\d{2}/,/(?:\d{4}-)(\d{2})/];
			}
			else if(selpoint == 3){
				return [/\d{4}-\d{2}-\d{2}/,/(?:\d{4}-\d{2}-)(\d{2})/];
			}
			else if(selpoint == 4){
				return [/\d{4}-\d{2}-\d{2} \d{2}/,/(?:\d{4}-\d{2}-\d{2} )(\d{2})/];
			}
			else if(selpoint == 5){
				return [/\d{4}-\d{2}-\d{2} \d{2}:\d{2}/,/(?:\d{4}-\d{2}-\d{2} \d{2}:)(\d{2})/];
			}
			else if(selpoint == 6){
				return [/\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}/,/(?:\d{4}-\d{2}-\d{2} \d{2}:\d{2}:)(\d{2})/];
			}
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextAnalyse.do?task=First" METHOD="POST"
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
			<input type="hidden" id="selectedDimValue" name="selectedDimValue"
				value="${fo.selectedDimValue}">
			<input type="hidden" name="levelname" value="${fo.levelname}">
			<input type="hidden" name="dimResultValue"
				value="${fo.dimResultValue}">
			<input type="hidden" name="type" value="${fo.type}">
			<input type="hidden" id="dimTreeList" name="dimTreeList" value="${fo.dimTreeList}">
			<input type="hidden" name="choice" value="${fo.choice}">
			<input type="hidden" name="dimTreeDiv" value="${fo.dimTreeDiv}">
			<input type="hidden" name="dimResult" value="${fo.dimResult}">
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
			<input type="hidden" name="tgfiltSqlvalue" value="${fo.tgfiltSqlvalue}">
			<!-- Step3.jsp -->
			<input type="hidden" name="exterValue" value="${fo.exterValue}">
			<input type="hidden" name="seldimvalue" value="${fo.seldimvalue}">
			<input type="hidden" name="unit" value="${fo.unit}">
			<input type="hidden" name="timelevel" value="${fo.timelevel}">
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
										<FONT CLASS="OecGlobalPageTitle">分析主题向导</FONT>
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
										<FONT CLASS="OecInlineInfo">第 3 步, 共 10 步&nbsp;&nbsp;</FONT>

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
						维度选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 然后单击“下一步”。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<div
					style="width:99%;height:39%;margin:0px; margin-top:0px; border: 0 solid #A9C0E5">
					<table width="100%" border="0" cellpadding="0" cellspacing="1"
						align="center">
						<tr>
							<td height="23" style="display:none">
								类型选择:
								<select id="timeSelType" onchange="selectAction_timeSelType()">
									<option value="1">
										忙时选择
									</option>
									<option value="0">
										时间点(树)
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" height="100%" border="0" cellpadding="0"
									cellspacing="1" align="center">
									<tr>
										<td valign="top">
											<div id="timeTreeDiv"
												style="width:100%;height:100%;overflow:auto;display=none">
												<script type="text/javascript">
													
												</script>
											</div>

											<div id="calendardiv" style="width:100%">
												<div style="margin-bottom: 5" style='display:none'>
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
												<div style="margin-top: 5">

													间隔
													<!-- 间隔类型：增长类型-->
													<select name="selpoint2" disabled="disabled">
														<option value="1" ${fo.selpoint1==1?"selected":""}>
															年
														</option>
														<option value="2" ${fo.selpoint1==2?"selected":""}>
															月
														</option>
														<option value="3" ${fo.selpoint1==3?
															"selected":fo.selpoint1==''?"selected":""}>
															日
														</option>
														<option value="4" ${fo.selpoint1==4?"selected":""}>
															时
														</option>
															<!-- 
																		<option value="5">
																			分
																		</option>
																		<option value="6">
																			秒
																		</option>
																		 -->
													</select>
													<br>
													<div id="divhour">
													时段
													<select name="hour">
														<c:forEach var="item" begin="0" end="23">
															<option value="${item}" ${fo.hour==item?"selected":""}>
																${item}
															</option>
														</c:forEach>
													</select>
													</div>
													<div id="intervaldiv">
														步长
														<input type="text" value="${fo.grownum}" name="grownum"
															style="width:50px">
													</div>

												</div>
												起始
												<input type="text" name="selecttime1" onFocus="calendar();"
													style="width:120" value="${fo.selecttime1}">
												<br>
												结束
												<input type="text" name="selecttime2" onFocus="calendar();"
													style="width:120" value="${fo.selecttime2}">
												<input type="button" class="butt" value="确定"
													onclick="btnAction_submitCalendar()">
												<!-- 连续 -->
												<input name="growtype" type="radio" checked
													onclick="changegrowtype(1);" style='display:none'>
												<!-- 分段 -->
												<input name="growtype" type="radio"
													onclick="changegrowtype(2);" style='display:none'>
											</div>

										</td>
									</tr>
									<tr>
										<td height="80">
											<div style="margin-top: 8;">
												选择的结果：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="button" class="butt" value="删除选定"
													onclick="btnAction_delSelTime();">
												<br>
												<select size="5" name="timeResult" style="width: 100%"
													multiple="multiple">
												</select>
											</div>
										</td>
									</tr>
								</table>
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
				if(document.all.grownum.value==''){
					document.all.grownum.value='1';
				}
				
				if(document.all.selecttime1.value!="" && document.all.selecttime2.value!=""){
					btnAction_submitCalendar();
				}
				
				if(document.all.selpoint1.value!="3"){
					document.all.divhour.style.display="none";
				}
			</script>
		</FORM>
	</BODY>
</HTML>
