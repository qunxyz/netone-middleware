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

		<title>��������</title>

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
font-family: "����";
font-size: 12px;
margin-left: 0px;
margin-top: 10px;
margin-right: 0px;
margin-bottom: 0px;
}
/*����˵��������ʽ1*/
.skin0 {
position:absolute;
padding-top:4px;
text-align:left;
width:100px; /*��ȣ����Ը���ʵ�ʵĲ˵���Ŀ���Ƶĳ��Ƚ����ʵ��ص���*/
border:2px solid black;
background-color:menu; /*�˵��ı�����ɫ����������ѡ����ϵͳĬ�ϵĲ˵���ɫ*/
font-family:"����";
line-height:20px;
cursor:default;
visibility:hidden; /*��ʼʱ������Ϊ���ɼ�*/
}
/*����˵��������ʽ2*/
.skin1 {
padding-top:4px;
cursor:default;
font:menutext;
position:absolute;
text-align:left;
font-family: "����";
font-size: 10pt;
width:100px; /*��ȣ����Ը���ʵ�ʵĲ˵���Ŀ���Ƶĳ��Ƚ����ʵ��ص���*/
background-color:menu; /*�˵��ı�����ɫ����������ѡ����ϵͳĬ�ϵĲ˵���ɫ*/
border:1 solid buttonface;
visibility:hidden; /*��ʼʱ������Ϊ���ɼ�*/
border:2 outset buttonhighlight;
}

/*����˵�������ʾ��ʽ*/
.menuitems {
padding:2px 1px 2px 10px;
}
-->
</style>
		<script language="javascript">
//����˵���ʾ����ۣ����Դ����涨���2�ָ�ʽ��ѡ����һ
var menuskin = "skin1";
//�Ƿ�����������ڵ�״̬������ʾ�˵���Ŀ����Ӧ�������ַ���
var display_url = 0;
</script>
	</head>

	<body BGCOLOR=#EDF0F6 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0 style="font:14px">
		<div onselectstart="return false" id="ie5menu" class="skin0"
			onMouseover="highlightie5()" onMouseout="lowlightie5()"
			onClick="jumptoie5();">
			<div class="menuitems" id="allMSC" url="javascript:refer('MSC');">
				����MSC
			</div>
			<div class="menuitems" id="allBSC" url="javascript:refer('BSC');">
				����BSC
			</div>
			<div class="menuitems" id="allBTS" url="javascript:refer('BTS');">
				����BTS
			</div>
			<div class="menuitems" id="allCELL" url="javascript:refer('CELL');">
				����CELL
			</div>
			<div class="menuitems" id="allCARRIER"
				url="javascript:refer('CARRIER');">
				����CARRIER
			</div>
			<div class="menuitems" id="allLINKSET"
				url="javascript:refer('LINKSET');">
				����LINKSET
			</div>
			<div class="menuitems" id="allLINK" url="javascript:refer('LINK');">
				����LINK
			</div>
			<div class="menuitems" id="allTRUNKGROUP"
				url="javascript:refer('TRUNKGROUP');">
				����TRUNKGROUP
			</div>
		</div>

		<script language="JavaScript1.2">
//�����ǰ�������Internet Explorer��document.all�ͷ�����
if (document.all && window.print) {

//ѡ��˵��������ʾ��ʽ
ie5menu.className = menuskin;

//�ض�������Ҽ��¼��Ĵ������Ϊ�Զ������showmenuie5
document.oncontextmenu = showmenuie5;

//�ض����������¼��Ĵ������Ϊ�Զ������hidemenuie5
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
				<!-- ��� begin -->
				<td width="400px" valign="top" bgcolor="#EDF0F6" id="analyzebox">
					<table width="100%" height="100%" border="0" align="center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td width="200" id="dimbox">
								<!-- ҵ��ά��ѡ�� -->
								<table width="99%" height="22px" border="0" align="center"
									cellpadding="0" cellspacing="0">
									<tr>
										<td width="90%" background="images/td_bg_1.gif">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<font size="2">ҵ��ά��ѡ��</font>
										</td>
										<td align="right" style="cursor: hand">
											<span style="color: blue;" onclick="showTgbox();">-></span>
										</td>
									</tr>
								</table>
								<div
									style="width: 100% ; height: 100% ; background-color: #EDF0F6 ; border: 1 solid #A9C0E5 ;">
									<!-- ά��ֵѡ�� -->
									<div
										style="width:99%;height:55%;margin:0px; margin-top:0px; border: 0 solid #A9C0E5">
										����ѡ��:
										<select id="dimTreeList" style="width: 90px"
											onchange="selectAction_dimTreeList();">
											<option value="-1">
												---��ѡ��---
											</option>
											<c:forEach items="${dims}" var="dim">
												<option value="${dim[0]}">
													${dim[1]}
												</option>
											</c:forEach>
										</select>
										<!-- ʱ������ѡ��-->
										<select id="selpoint1" onchange="selectAction_dimTreeList();">
											<option value="1">
												��
											</option>
											<option value="2">
												��
											</option>
											<option value="3">
												��
											</option>
											<option value="4" selected>
												ʱ
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
															var dimTree = new WebFXLoadTree('ά��');
															dimTree.write();
															
															
														</script>
													</div>
												</td>
											</tr>
											<tr>
												<td height="60">
													ѡ��Ľ���� &nbsp;&nbsp;&nbsp;
													<input type="button" class="butt" value="ɾ��ѡ��"
														onclick="btnAction_delSelDim()">
													<br>
													<select size="5" id="dimResult" style="width: 95%"
														onchange="selectAction_dimResult()">
													</select>
												</td>
											</tr>
										</table>
									</div>


									<!-- ʱ��ѡ�� -->
									<div
										style="width:99%;height:39%;margin:0px; margin-top:0px; border: 0 solid #A9C0E5">
										<table width="100%" border="0" cellpadding="0" cellspacing="1"
											align="center">
											<tr>
												<td height="23" style="display:none">
													����ѡ��:
													<select id="timeSelType"
														onchange="selectAction_timeSelType()">
														<option value="1">
															æʱѡ��
														</option>
														<option value="0">
															ʱ���(��)
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
															var timeTree = new WebFXLoadTree('ʱ��ѡ��');
															timeTree.write();
															timeTree.setSrc("servlet/EtlTreeSvl?treeModelId=timetree");
															timeTree.expand();
															
															
														</script>
																</div>

																<div id="calendardiv" style="width:100%">


																	<div style="margin-bottom: 5" style='display:none'>
																		ʱ��ѡ��
																		<select id="timeSelectType"
																			onchange="selectAction_timeSelectType()">
																			<option value="0">
																				�ֶ�ʱ��ѡ��
																			</option>
																			<option value="1">
																				����ʱ��ѡ��
																			</option>
																		</select>
																	</div>
																	<div style="margin-top: 5">

																		���
																		<!-- ������ͣ���������-->
																		<select id="selpoint2" onChange="timePartCheck();">
																			<option value="1">
																				��
																			</option>
																			<option value="2">
																				��
																			</option>
																			<option value="3" selected>
																				��
																			</option>
																			<option value="4">
																				ʱ
																			</option>
																			<!-- 
																		<option value="5">
																			��
																		</option>
																		<option value="6">
																			��
																		</option>
																		 -->
																		</select>
																		ʱ��
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
																			����
																			<input type="text" value="1" id="grownum" name='grownum'
																				style="width:50px">
																		</div>

																	</div>
																	��ʼ
																	<input type="text" name="selecttime1"
																		onFocus="calendar();" style="width:120">
																	<br>
																	����
																	<input type="text" name="selecttime2"
																		onFocus="calendar();" style="width:120">
												
																	<input type="button" class="butt" value="ȷ��"
																		onclick="btnAction_submitCalendar()">



																	<!-- ���� -->
																	<input name="growtype" type="radio" checked
																		onclick="changegrowtype(1);" style='display:none'>
																	<!-- �ֶ� -->
																	<input name="growtype" type="radio"
																		onclick="changegrowtype(2);" style='display:none'>



																</div>

															</td>
														</tr>
														<tr>
															<td height="80">
																<div style="margin-top: 8;">
																	ѡ��Ľ����&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	<input type="button" class="butt" value="ɾ��ѡ��"
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
								<!-- ָ��ѡ�� -->
								<table width="99%" height="22px" border="0" align="center"
									cellpadding="0" cellspacing="0">
									<tr>
										<td width="90%" background="images/td_bg_1.gif">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<font size="2">ָ��ѡ��</font>
										</td>
										<td align="right" style="cursor: hand">
											<span style="color: blue;" onclick="hiddenTgbox();"><-</span>
										</td>
									</tr>
								</table>
								<div id="targetdiv"
									style="width: 100% ; height: 75% ;  background-color: #EDF0F6 ; overflow: auto ; border: 1 solid #A9C0E5 ;">
									ָ���飺
									<select id="tgGroup" onChange="selectAction_tgGroup()">

									</select>
									<div id="tglistdiv">
									</div>
								</div>
								<div style="margin-top: 8;">
									ָ��ѡ��Ľ����&nbsp;
									<input type="button" class="butt" value="ɾ��ѡ��"
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
									<input type="button" value="�� ��" class="butt"
										onclick="btntgfilter();" style="width: 80px ; height: 23px" />
									<input type="button" value="�� ��" class="butt" name="btnAnalyse"
										onclick="waiting.style.display='';analyse();"
										style="width: 80px ; height: 23px">
								</form>


							</td>
						</tr>
					</table>
				</td>

				<!-- ��� end -->

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
												<font size="2">�� �� �� ��</font>
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
										&nbsp;&nbsp;&nbsp;<font color='red'>���ݴ��������Ե�.........</font>
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
												<font size="2">ͼ �� �� ��</font>
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








		<!-- forecastdiv ��forecastselectҳ��ʹ�� -->

		<div id="forecastdiv"
			style="display: none;position:absolute;z-index: 4 ">

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="#A9C0E5" align="center">
				<tr>
					<td>
						Ԥ�����
					</td>
				</tr>

				<!--   
			<tr> 
				<td>
				ѡ��ָ�꣺
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
						ѡ��ά�ȣ�
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
						<INPUT type="button" value="ȷ��" onclick="submitforecast();">
						<input type="button" value="�ر�" onclick="window.close();">
					</TD>
				</tr>
			</table>
	</body>
</html>
