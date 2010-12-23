<%@ page contentType="text/html; charset=GBK" import="java.util.*"%>
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

		<title>交互分析</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/include/css/xtree2.css">
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
		<SCRIPT type="text/javascript">
		var _SRC_ANALYSIS_TYPE = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_ANALYSIS_TYPE%>";
	</SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<script type="text/javascript" src="<%=path%>/include/js/xtree2.js"></script>
		<script type="text/javascript"
			src="<%=path%>/include/js/xloadtree2.js"></script>
		<script type="text/javascript" src="<%=path%>/include/js/extxtree2.js"></script>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/etl/etlmain.js"></SCRIPT>
		<script type="text/javascript" src="<%=path%>/include/js/calendar.js"></SCRIPT>
		<style>
body {
font-family: "宋体";
font-size: 12px;
margin-left: 0px;
margin-top: 10px;
margin-right: 0px;
margin-bottom: 0px;
}
/*定义菜单方框的样式1*/
.skin0 {
position:absolute;
padding-top:4px;
text-align:left;
width:100px; /*宽度，可以根据实际的菜单项目名称的长度进行适当地调整*/
border:2px solid black;
background-color:menu; /*菜单的背景颜色方案，这里选择了系统默认的菜单颜色*/
font-family:"宋体";
line-height:20px;
cursor:default;
visibility:hidden; /*初始时，设置为不可见*/
}
/*定义菜单方框的样式2*/
.skin1 {
padding-top:4px;
cursor:default;
font:menutext;
position:absolute;
text-align:left;
font-family: "宋体";
font-size: 10pt;
width:100px; /*宽度，可以根据实际的菜单项目名称的长度进行适当地调整*/
background-color:menu; /*菜单的背景颜色方案，这里选择了系统默认的菜单颜色*/
border:1 solid buttonface;
visibility:hidden; /*初始时，设置为不可见*/
border:2 outset buttonhighlight;
}

/*定义菜单条的显示样式*/
.menuitems {
padding:2px 1px 2px 10px;
}
-->
</style>
		<script language="javascript">
//定义菜单显示的外观，可以从上面定义的2种格式中选择其一
var menuskin = "skin1";
//是否在浏览器窗口的状态行中显示菜单项目条对应的链接字符串
var display_url = 0;
</script>
	</head>

	<body BGCOLOR=#EDF0F6 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0 style="font:14px">
		<div onselectstart="return false" id="ie5menu" class="skin0"
			onMouseover="highlightie5()" onMouseout="lowlightie5()"
			onClick="jumptoie5();">
			<div class="menuitems" id="allMSC" url="javascript:refer('MSC');">
				所有MSC
			</div>
			<div class="menuitems" id="allBSC" url="javascript:refer('BSC');">
				所有BSC
			</div>
			<div class="menuitems" id="allBTS" url="javascript:refer('BTS');">
				所有BTS
			</div>
			<div class="menuitems" id="allCELL" url="javascript:refer('CELL');">
				所有CELL
			</div>
			<div class="menuitems" id="allCARRIER"
				url="javascript:refer('CARRIER');">
				所有CARRIER
			</div>
			<div class="menuitems" id="allLINKSET"
				url="javascript:refer('LINKSET');">
				所有LINKSET
			</div>
			<div class="menuitems" id="allLINK" url="javascript:refer('LINK');">
				所有LINK
			</div>
			<div class="menuitems" id="allTRUNKGROUP"
				url="javascript:refer('TRUNKGROUP');">
				所有TRUNKGROUP
			</div>
		</div>

		<script language="JavaScript1.2">
//如果当前浏览器是Internet Explorer，document.all就返回真
if (document.all && window.print) {

//选择菜单方块的显示样式
ie5menu.className = menuskin;

//重定向鼠标右键事件的处理过程为自定义程序showmenuie5
document.oncontextmenu = showmenuie5;

//重定向鼠标左键事件的处理过程为自定义程序hidemenuie5
document.body.onclick = hidemenuie5;
}
</script>
		<script type="text/javascript">
	top.window.moveTo(0,0);
	if (document.all) {
	top.window.resizeTo(screen.availWidth,screen.availHeight);
	}
	else if (document.layers||document.getElementById) {
		if (top.window.outerHeight<screen.availHeight||top.window.outerWidth<screen.availWidth){
		top.window.outerHeight = screen.availHeight;
		top.window.outerWidth = screen.availWidth;
		}
	}
   </script>

		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="1" bgcolor="#3968B8">
			<tr>
				<!-- 左边 begin -->
				<td width="400px" valign="top" bgcolor="#EDF0F6" id="analyzebox">
					<table width="100%" height="100%" border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td width="200" id="dimbox">
								<!-- 业务维度选择 -->
								<table width="99%" height="22px" border="0" align="center"
									cellpadding="0" cellspacing="0">
									<tr>
										<td width="90%" background="images/td_bg_1.gif">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<font size="2">业务维度选择</font>
										</td>
										<td align="right" style="cursor: hand">
											<span style="color: blue;" onclick="showTgbox();">-></span>
										</td>
									</tr>
								</table>
								<div
									style="width: 100% ; height: 100% ; background-color: #EDF0F6 ; border: 1 solid #A9C0E5 ;">
									<!-- 维度值选择 -->
									<div
										style="width:99%;height:55%;margin:0px; margin-top:0px; border: 0 solid #A9C0E5">
										类型选择:
										<select id="dimTreeList" style="width: 90px"
											onchange="selectAction_dimTreeList();">
											<option value="-1">
												---请选择---
											</option>
											<c:forEach items="${dims}" var="dim">
												<option value="${dim[0]}">
													${dim[1]}
												</option>
											</c:forEach>
										</select>
										<!-- 时间粒度选择：-->
										<select id="selpoint1" onchange="selectAction_dimTreeList();">
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
										</select>

										<br>
										<table width="100%" height="90%" cellpadding="0"
											cellspacing="1" align="center">
											<tr>
												<td valign="top">
													<div id="dimTreeDiv"
														style="width:100%;height:100%;overflow:auto;">
														<script type="text/javascript">
															var dimTree = new WebFXLoadTree('维度');
															dimTree.write();
															
															
														</script>
													</div>
												</td>
											</tr>
											<tr>
												<td height="60">
													选择的结果： &nbsp;&nbsp;&nbsp;
													<input type="button" class="butt" value="删除选定"
														onclick="btnAction_delSelDim()">
													<br>
													<select size="5" id="dimResult" style="width: 95%"
														onchange="selectAction_dimResult()">
													</select>
												</td>
											</tr>
										</table>
									</div>


									<!-- 时间选择 -->
									<div
										style="width:99%;height:39%;margin:0px; margin-top:0px; border: 0 solid #A9C0E5">
										<table width="100%" border="0" cellpadding="0" cellspacing="1"
											align="center">
											<tr>
												<td height="23" style="display:none">
													类型选择:
													<select id="timeSelType"
														onchange="selectAction_timeSelType()">
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
													<table width="100%" height="100%" border="0"
														cellpadding="0" cellspacing="1" align="center">
														<tr>
															<td valign="top">
																<div id="timeTreeDiv"
																	style="width:100%;height:100%;overflow:auto;display=none">
																	<script type="text/javascript">
															var timeTree = new WebFXLoadTree('时间选择');
															timeTree.write();
															timeTree.setSrc("servlet/EtlTreeSvl?treeModelId=timetree");
															timeTree.expand();
															
															
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
																		<select id="selpoint2" onChange="timePartCheck();">
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
																			<!-- 
																		<option value="5">
																			分
																		</option>
																		<option value="6">
																			秒
																		</option>
																		 -->
																		</select>
																		时段
																		<select id="hour" name='hour'>
																																					<option value='00'>00</option>
																		<option value='01'>01</option>
<option value='02'>02</option>
<option value='03'>03</option>
<option value='04'>04</option>
<option value='05'>05</option>
<option value='06'>06</option>
<option value='07'>07</option>
<option value='08'>08</option>
<option value='09'>09</option>
																			<script type="text/javascript">
																			for(var i=10 ; i<24 ; i++){
																				document.writeln("<option value='"+i+"'>"+i+"</option>");
																			}
																		</script>
																		</select>
																		<div id="intervaldiv">
																			步长
																			<input type="text" value="1" id="grownum" name='grownum'
																				style="width:50px">
																		</div>

																	</div>
																	起始
																	<input type="text" name="selecttime1"
																		onFocus="calendar();" style="width:120">
																	<br>
																	结束
																	<input type="text" name="selecttime2"
																		onFocus="calendar();" style="width:120">
												
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
																	<select size="5" id="timeResult" style="width: 100%"
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
								</div>
							</td>
							<td width="200" id="tgbox">
								<!-- 指标选择 -->
								<table width="99%" height="22px" border="0" align="center"
									cellpadding="0" cellspacing="0">
									<tr>
										<td width="90%" background="images/td_bg_1.gif">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<font size="2">指标选择</font>
										</td>
										<td align="right" style="cursor: hand">
											<span style="color: blue;" onclick="hiddenTgbox();"><-</span>
										</td>
									</tr>
								</table>
								<div id="targetdiv"
									style="width: 100% ; height: 75% ;  background-color: #EDF0F6 ; overflow: auto ; border: 1 solid #A9C0E5 ;">
									指标组：
									<select id="tgGroup" onChange="selectAction_tgGroup()">

									</select>
									<div id="tglistdiv">
									</div>
								</div>
								<div style="margin-top: 8;">
									指标选择的结果：&nbsp;
									<input type="button" class="butt" value="删除选定"
										onclick="btnAction_delSelTarget()">
									<br>
									<select size="6" id="targetResult" style="width: 95%"
										multiple="multiple">
									</select>
								</div>
							</td>
						</tr>

						<tr height="30px">
							<td valign="middle" align="center" bgcolor="#EDF0F6" colspan="2">


								<form action="<%=path%>/etlAnalyse.do" method="post"
									target="etlViewFrame">

									<input type='text' name='timespecial' value='' size='15'
										style='display:none' />

									<INPUT type="hidden" name="act" value="tableView">
									<INPUT type="hidden" name="seltgvalue" value="">
									<input type="hidden" name="seldimvalue" value="">
									<input type="hidden" name="datamodelid" value="${datamodelid}">
									<input type="hidden" value="" name="tgfiltvalue">
									<input type="hidden" value="" name="tgfiltSqlvalue">
									<input type="hidden" value="" name="exterValue">
									<input type="button" value="过 滤" class="butt"
										onclick="btntgfilter();" style="width: 80px ; height: 23px" />
									<input type="button" value="分 析" class="butt" name="btnAnalyse"
										onclick="waiting.style.display='';analyse();"
										style="width: 80px ; height: 23px">
								</form>


							</td>
						</tr>
					</table>
				</td>

				<!-- 左边 end -->

				<td valign="top">
					<div
						style="width: 100% ; height: 100% ; background-color: #EDF0F6 ; overflow: auto ">
						<table width="100%" height="100%" border="0" align="center"
							cellpadding="0" cellspacing="0">
							<tr height="22">
								<td>
									<table width="98%" height="100%" border="0" align="center"
										cellpadding="0" cellspacing="0">
										<tr>
											<td width="46%" background="images/td_bg_1.gif">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<font size="2">分 析 结 果</font>
											</td>
											<td width="54%" background="images/td_bg_2.gif">
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td id='waiting' style='display:none'>
									<br><br>
									<h3>
										&nbsp;&nbsp;&nbsp;<font color='red'>数据处理中请稍等.........</font>
									</h3>
								</td>
							</tr>
							<tr height="305">
								<td>
									<table width="98%" height="100%" border="0" align="center"
										cellpadding="0" cellspacing="0" bgcolor="#A9C0E5">
										<tr align="center">
											<TD>
												<iframe name="etlViewFrame" marginwidth=0 marginheight=0
													src="<%=path%>/bi/etl/tableView.jsp" align="left"
													frameborder=0 width="100%" height="100%" scrolling="no"></iframe>
											</TD>
										</TR>
									</table>
								</td>
							</tr>
							<tr height="22">
								<td>
									<table width="98%" height="100%" border="0" align="center"
										cellpadding="0" cellspacing="0">
										<tr>
											<td width="46%" background="images/td_bg_1.gif">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<font size="2">图 表 分 析</font>
											</td>
											<td width="54%" background="images/td_bg_2.gif">
												&nbsp;
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="400">
								<td>
									<table width="98%" height="100%" border="0" align="center"
										cellpadding="0" cellspacing="0" bgcolor="#A9C0E5">
										<tr class="td-02">
											<td bgcolor="#FFFFFF">
												<iframe name="etlchartFrame" marginwidth=0 marginheight=0
													src="<%=path%>/bi/etl/chartView.jsp" align="left"
													frameborder=0 width="100%" height="100%" scrolling="auto"></iframe>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				<td>
			</tr>
		</table>








		<!-- forecastdiv 供forecastselect页面使用 -->

		<div id="forecastdiv"
			style="display: none;position:absolute;z-index: 4 ">

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="#A9C0E5" align="center">
				<tr>
					<td>
						预测分析
					</td>
				</tr>

				<!--   
			<tr> 
				<td>
				选择指标：
				</td>
			</tr>
			<tr>
					<c:forEach	items="${tgmap}" var="tg">
						<TD id="fc_${tg.value.id}">
						<input type="checkbox" name="fc_tg_cbx" value="${tg.value.id}">
							${tg.value.name}
						</TD>
					</c:forEach>
			</tr>
			-->

			</table>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="#A9C0E5" align="center">
				<tr>
					<td>
						选择维度：
					</td>
				</tr>
				<tr>
					<c:forEach items="${dimmap}" var="dim">
						<td id="fc_${dim.value.id}">
							<input type="checkbox" name="fc_dim_cbx" value="${dim.value.id}">
							${dim.value.name}
						</td>
					</c:forEach>
					<td>
				</tr>
				<tr>
					<TD>
						<INPUT type="button" value="确定" onclick="submitforecast();">
						<input type="button" value="关闭" onclick="window.close();">
					</TD>
				</tr>
			</table>
	</body>
</html>
