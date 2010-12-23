<%@ page contentType="text/html; charset=GBK" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  
<head>
    <base href="<%=basePath%>">
    
    <title>交互分析</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" type="text/css" href="<%=path%>/include/css/xtree2.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/include/css/css.css">
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
    
	<script type="text/javascript" src="<%=path%>/include/js/xtree2.js" ></script>
	<script type="text/javascript" src="<%=path%>/include/js/xloadtree2.js" ></script>
	<SCRIPT type="text/javascript">
		var contextpath = "<%=path%>" ;
		var _SRC_DATAMODELID = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_DATAMODELID%>";
		var _SRC_DIMENSIONID = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_DIMENSIONID%>";
		var _SRC_DIMENSIONVALUE = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_DIMENSIONVALUE%>";
		var _SRC_ANALYSIS_TYPE = "<%=oe.bi.dataModel.bus.DigTreeBuilder._SRC_ANALYSIS_TYPE%>";
	</SCRIPT>
	<SCRIPT type="text/javascript" src="<%=path%>/include/js/biutil.js"></SCRIPT>
    <SCRIPT type="text/javascript" src="<%=path%>/include/js/etl/etlmain.js"></SCRIPT>
    <script type="text/javascript" src="<%=path%>/include/js/calendar.js"></SCRIPT>
  </head>
  
  <body BGCOLOR=#EDF0F6 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
  
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
        
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#3968B8">
			<tr>
				<!-- 左边 begin -->
				
				<td width="285px" valign="top" bgcolor="#EDF0F6">
					<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr height="22px">
							<td width="120">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="4">
											<img src="images/bott_1.gif" width="4" height="22">
										</td>
										<td style="cursor: hand;" background="images/bott_2.gif" align="center" onclick="el('tgdiv').style.display='block';el('dimdiv').style.display='none';">
											选择指标
										</td>
										<td width="3">
											<img src="images/bott_3.gif" width="3" height="22">
										</td>
									</tr>
								</table>
							</td>
							<td width="120">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="4">
											<img src="images/bott_1_on.gif" width="4" height="22">
										</td>
										<td background="images/bott_2_on.gif" align="center" onclick="el('tgdiv').style.display='none';el('dimdiv').style.display='block';"  style="cursor: hand;">
											选择维度
										</td>
										<td width="3">
											<img src="images/bott_3_on.gif" width="3" height="22">
										</td>
									</tr>
								</table>
							</td>
							<td background="images/td_bg_2.gif">
								<a href="#"></a>
							</td>
						</tr>
						<tr>
							<td colspan="3" valign="top">
								<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td valign="top">
											<!-- 选择的div begin-->
											
											<div id="tgdiv" style="width:99%;height:99%;overflow:auto;margin:1px; margin-top:0px; background-color: #FFFFFF ; border-style: solid; border-width: 1px ;border-color: #A9C0E5">
												指标选择:<br>	
									
												<table width="100%" border="0" cellpadding="0" cellspacing="1" align="center">
												
													<c:forEach items="${tgmap}" var="tg" > 
														<tr align="center">
															<td align="left">
																<input type="checkbox" name="cbtg" id="${tg.value.id}" value="[${tg.value.id}][][]" onclick="tgcbxchange(this);"/>
																<span title="${tg.value.name}" style="width: 200px; height: 23px ;overflow: hidden">${tg.value.name}</span>
																<!-- 
																		<select name="counttype" onchange="tgdefineaction();" disabled="disabled">
																			<c:forEach items="${tgfunlist}" var="tf">
																				<OPTION value="${tf.value}"><c:out value="${tf.label}"></c:out></OPTION>
																			</c:forEach> 
																		</select>
																
																<input type="button" disabled="disabled" value="定义" onclick="definetg(this.parentNode.firstChild,'${tg.value.id}','${tg.value.description}','${tg.value.alarm}');"/>
																-->
															</td>
														<tr>	
													</c:forEach>
													
												</table>

											</div>
											
											<div id="dimdiv" style="display: none; width:99%;height:99%;overflow:auto;margin:0px; margin-top:0px;  background-color: #FFFFFF ; border-style: solid; border-width: 1px ;border-color: #A9C0E5">
												<div style="width:98%;height:20%;overflow:auto;margin:0px;float:left;border-style: solid; border-width: 1px ;border-color: #A9C0E5"">	
												维度选择:<br>	
														<table  width="100%" border="0" cellpadding="0" cellspacing="1" >
															<c:forEach items="${dimmap}" var="dim">
																<tr align="left">
																	<td>
																		<input type="checkbox" name="cbdim" value="[][][]" id="${dim.value.id}" onclick="dimcbxchange(this);"/>
																		<span title="${dim.value.name}" style="width: 50px;height: 23px ;overflow: hidden">${dim.value.name}</span>
																		<input type="button" class="butt" value="选择" disabled="disabled" onclick="definedim(this.parentNode.firstChild,'${dim.value.id}','${dim.value.name}','${datamodelid}');">
																	</td>
																</tr>
															</c:forEach>
														</TABLE>
												</div>
												
												<div id="dimmgr" style="width:98%;height:80%;overflow:auto;margin:0px;float:left;border-style: solid; border-width: 1px ;border-color: #A9C0E5"" >
												<input type="button" class="butt" value="全选/全不选" onclick="allselect();" />
												<input type="button" class="butt" value="重置" onclick="reset();" />
												<input type="button" class="butt" value="忙时" onclick="multiselect();" style="display:none"/>	
												<input type="button" class="butt" value="忙时选择" onclick="calendarSelect();"/>
													<div id="calendardiv" style="width:100%;display=none">
														<br/>
														<!-- 时间粒度选择：-->
														<select id="selpoint1" style="display:none ">	
																		<option value="1">年</option>
																		<option value="2">月</option>
																		<option value="3">日</option>
																		<option value="4" selected>时</option>
																		<option value="5">分</option>
																		<option value="6">秒</option>
																	</select>
														时间范围：<br /> <input type="text" name="selecttime1" onFocus="calendar();" style="width:120">到
														<input type="text" name="selecttime2" onFocus="calendar();" style="width:120">
														<br />
														<!-- 间隔类型：增长类型-->
														<select id="selpoint2" style="display:none ">	
																			<option value="1">年</option>
																			<option value="2">月</option>
																			<option value="3" selected>日</option>
																			<option value="4">时</option>
																			<option value="5">分</option>
																			<option value="6">秒</option>
																		</select>
																<!-- 连续 -->
																<input name="growtype" type="radio" checked onclick="changegrowtype(1);" style="display:none ">
																<!-- 分段 -->
																<input name="growtype" type="radio" onclick="changegrowtype(2);" style="display:none ">
																<div style="display:none" id="intervaldiv">
																	步长：<input type="text" value="1" id="grownum" style="width:50px">
																</div>
														<br />
														<div style="width:100%">
															<input type="button" class="butt" value="确定" onclick="submitCalendar()">
														</div>
														<br/>
														<select size="11" id="calendarselresult" style="width:90%" ></select>
													</div>
													<div id="treediv" style="width:100%;overflow-y:auto;">
														<script type="text/javascript">
															var tree = new WebFXLoadTree('维度管理');
															tree.onload= new Function("treesonload(tree);");
															tree.write();
														</script>
													</div>
													<div id="multiselectdiv" style="width:100%;overflow:visible;display:none">
														<br>每隔<input type="text" id="interval" value="1" style="width:30"/>
														<select id="unit">
															<option value="1">年</option>
															<option value="2">月</option>
															<option value="3" selected>日</option>
															<option value="4">时</option>
															<option value="5">分</option>
														</select>
														&nbsp;
														共取<input type="text" id="selsize" value="" style="width:30"/>个 &nbsp;
														<input type="button" class="butt" value="增加" onclick="addmultitime();" >
														<br />
														<select size="9" id="selresult" style="width:90%" ></select>
													</div>
												</div>
											</div>
											
											<!-- 选择的div end-->
										</td>
									</tr>
									<tr height="30px">
										<td valign="middle" align="center" bgcolor="#EDF0F6">
											<input type="button" value="分" class="butt" onclick="waiting.style.display='';analyse();" style="width: 100px ; height: 26px">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				
				<!-- 左边 end -->
				
				<td valign="top"> 
					<div style="width: 100% ; height: 100% ; background-color: #EDF0F6 ; overflow: auto " >
					<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr height="22"><td>
						<table width="98%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
							<tr>
								<td width="46%" background="images/td_bg_1.gif">
									&nbsp;&nbsp;&nbsp;&nbsp;<font size="2">分 析 结 果</font>
								</td>
								<td width="54%" background="images/td_bg_2.gif">
								</td>
							</tr>
						</table>
						</td></tr>
						<tr height="305"><td>
						<table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#A9C0E5">
							<tr>
							<td id='waiting' style='display:none'>
								数据处理中请稍等.........
							</td>
							</tr>
							<tr align="center" >
								<TD>
								  	<iframe name="etlViewFrame" marginwidth=0  marginheight=0 src="<%=path%>/bi/etl/tableView.jsp"  align="left" frameborder=0 width="100%" height="100%" scrolling="no"  ></iframe>
								</TD>
							</TR>
						</table>
						</td></tr>
						<tr height="22"><td>
						<table width="98%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="46%" background="images/td_bg_1.gif">
									&nbsp;&nbsp;&nbsp;&nbsp;<font size="2">图 表 分 析</font>
								</td>
								<td width="54%" background="images/td_bg_2.gif">
									&nbsp;
								</td>
							</tr>
						</table>
						</td></tr>
						<tr height="400"><td>
						<table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#A9C0E5">
							<tr class="td-02">
								<td bgcolor="#FFFFFF">
									<iframe name="etlchartFrame" marginwidth=0 marginheight=0 src="<%=path%>/bi/etl/chartView.jsp"  align="left" frameborder=0 width="100%" height="100%" scrolling="auto" ></iframe>
								</td>
							</tr>
						</table>
						</td></tr>
					</table>
					</div>
				<td>
			</tr>
		</table>
        
        
        
		
		<form action="<%=path%>/etlAnalyse.do" method="post" target="etlViewFrame" >
			<INPUT type="hidden" name="act" value="tableView">
			<INPUT type="hidden" name="seltgvalue" value=""> 
			<input type="hidden" name="seldimvalue" value="">
			<input type="hidden" name="datamodelid" value="${datamodelid}"> 
		</form> 
		
		 
		 
	
	
	
	<!-- forecastdiv 供forecastselect页面使用 -->
	
	<div id="forecastdiv" style="display: none;position:absolute;z-index: 4 ">
										
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#A9C0E5" align="center">
			<tr>
				<td>预测分析</td>
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
		
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#A9C0E5" align="center">
			<tr>
				<td>
				选择维度：
				</td>
			</tr>
			<tr>
					<c:forEach	items="${dimmap}" var="dim">
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
	</div>
	

	
		
	</body>
</html>