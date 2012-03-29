<%@ page contentType="text/html; charset=GB2312"%>
<jsp:directive.page import="oe.frame.web.util.WebStr"/>

		<%
		request.setCharacterEncoding("gb2312");
		String path = request.getContextPath();
		String workflowStr = (String) request.getAttribute("tScript");

		%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		<title>
			工作流工具
		</title>
		<SCRIPT LANGUAGE="JavaScript" src="<%=path%>/include/js/workflowtrack/workflow.js"></script>
		<link href="<%=path%>/include/css/workflowtrack/style.css" rel="stylesheet" type="text/css">

	</head>

<style>
body{ margin:1px;}

</style>
	<body oncontextmenu="return false" onLoad="submitInitField();" >

		<table align="right" width="100%" border="0" cellpadding="0" cellspacing="0" height="30" style="background-image:url(<%=path%>/image/wf/lct.jpg);">
			<tr style="padding-top:2px;">
				<td align="left"><strong>&nbsp;&nbsp; &nbsp;<img src="<%=path%>/image/wf/saveprocess.png" onclick="saveProcess();this.src='<%=path%>/image/wf/saveprocess_c.png'" onmouseover="this.src='<%=path%>/image/wf/saveprocess_o.png'" onmouseout="this.src='<%=path%>/image/wf/saveprocess.png'"/>&nbsp;&nbsp;
																 <img src="<%=path%>/image/wf/debugprocess.png" onclick="simu();this.src='<%=path%>/image/wf/debugprocess_c.png'" onmouseover="this.src='<%=path%>/image/wf/debugprocess_o.png'" onmouseout="this.src='<%=path%>/image/wf/debugprocess.png'"/>
																 </strong></td>
			</tr>
		</table>
		<br>
		<div align="center">
			<div id="tools" style="DISPLAY:; Z-INDEX: 102; POSITION: absolute;; left: 1; top: 15px; width:100%; height: 35px;background-image:url(<%=path%>/image/wf/lct2.jpg); visibility: visible;margin-top:20px;">
				<table border="0" align="right" cellpadding="0" cellspacing="0" style="font-size:14px; margin-left:7; ">
					<tr  height="35">
					  <td width="70" align="left">
							<img name="create" value="" onClick="new trackFactory('start');" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_start.png" onmousemove="this.src='<%=path%>/image/wf/right/n_start_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_start.png'"/>
					  </td>
					  <td width="70" align="left">
							<img name="create" value="" onClick="new trackFactory('end');" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_end.png" onmousemove="this.src='<%=path%>/image/wf/right/n_end_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_end.png'"/>
					  </td>
					  <td width="70" align="left">
							<img name="create" value="" onClick="new trackFactory('trackAction');" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_act.png" onmousemove="this.src='<%=path%>/image/wf/right/n_act_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_act.png'"/>
					  </td>
					  <td width="70" align="left">
							<img name="create" value="" onClick="new trackFactory('subWorkFlow');" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_subact.png" onmousemove="this.src='<%=path%>/image/wf/right/n_subact_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_subact.png'"/> 
					  </td>
					  <td width="70" align="left">
							<img name="create" value="" onClick="new trackFactory('route');" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_route.png" onmousemove="this.src='<%=path%>/image/wf/right/n_route_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_route.png'"/> 
					  </td>
					  <td width="70" align="left">
							<img name="create" value="" onClick="new trackFactory('turningpoint');" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_turning.png" onmousemove="this.src='<%=path%>/image/wf/right/n_turning_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_turning.png'"/> 
					  </td>
						<td width="70" align="left">
							<img name="create" value="" onClick="new trackFactory('beenLine');" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_line.png" onmousemove="this.src='<%=path%>/image/wf/right/n_line_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_line.png'"/> 
					  </td>
					  <td width="70" align="left" >
							<img name="Submit11" value="" onClick="relationData();" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_processform.png" onmousemove="this.src='<%=path%>/image/wf/right/n_processform_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_processform.png'"/> 
					  </td>
					  <td width="70" align="left" >
							<img name="Submit11" value="" onClick="openWindowUpdataProcess();" style="cursor:hand;width:70px;height:30px;margin-right:10px;font-weight:bold;"src="<%=path%>/image/wf/right/n_property.png" onmousemove="this.src='<%=path%>/image/wf/right/n_property_o.png'" onmouseout="this.src='<%=path%>/image/wf/right/n_property.png'"/> 
     				  </td>

					</tr>
			  </table>
		  </div>
			<p>&nbsp;
		  </p>
			<div id="workFlowProperty" style="DISPLAY:; Z-INDEX: 102; POSITION: absolute; "src="<%=path%>/image/wf/lct2.jpg);border-bottom:1px solid #c1dbf0;border-left:1px solid #c1dbf0; border-right:1px solid #c1dbf0;  left: 1px; top: 28px; width: 100%; height: 30px; visibility: hidden;">
				<table  border="0" cellpadding="0" cellspacing="0" align="center"  style="font-size:14px;padding-top:5px;margin-left:7">
					<tr height="30">

					</tr>
				</table>
			</div>

			<div id="simu" style="DISPLAY:; Z-INDEX: 102; POSITION: absolute; left: 1; top: 28px; width:100%; height: 30px;background-image:url(/image/wf/lct2.jpg);border-bottom:1px solid #aec3e3;border-left:1px solid #aec3e3; border-right:1px solid #aec3e3; height: 30px;visibility: hidden;">
				<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size:14px;padding-top:5px;margin-left:7">
					<tr height="30">
						<td width="70" align="center">
							
								<input class=Menu type="button" name="Submit15" value="启动流程" onClick="simu();" style="cursor:hand;">
							
						</td>
					</tr>
				</table>
			</div>
		</div>

		<%if (workflowStr != null)
				out.print(workflowStr);
		%>


		<span id="iconMenu" name="trackIconMenu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC;">
			<input id="delete" class=workMune onclick='iconMenu.style.display="none";new  deleteEvent("action");' type="button" value="删除">
<br>
			<input class=workMune onclick='iconMenu.style.display="none";' type="button" value="取消">
		</span>


		<span id="workTrackMenu" name="menu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC;">
			<input id="setProperty" onclick='workTrackMenu.style.display="none";normalActivityAttribute();' class=workMune type="button" value="属性">
			<br>
			<input id="delete" class=workMune onclick='workTrackMenu.style.display="none";new  deleteEvent("action");' type="button" value="删除">
		<br>
			<input class=workMune onclick='workTrackMenu.style.display="none"' type="button" value="取消">
		</span>

		<span id="subflowMenu" name="menu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC;">
			<input id="setProperty" onclick='subflowMenu.style.display="none";subflowActivityAttribute();' class=workMune type="button" value="属性">
<br>
			<input id="delete" class=workMune onclick='subflowMenu.style.display="none";new  deleteEvent("action");' type="button" value="删除">
		<br>
			<input class=workMune onclick='subflowMenu.style.display="none"' type="button" value="取消">
		</span>

		<span id="lineMenu" name="trackLineMenu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC;">
			<input id="setProperty" onclick='lineMenu.style.display="none";lineAttribute()' class=workMune type="button" value="属性">
			<br>
			<input id="delete" class=workMune onclick='lineMenu.style.display="none";new  deleteEvent("line");' type="button" value="删除">
			<br>
			<input class=workMune onclick='lineMenu.style.display="none";' type="button" value="取消">
		</span>


		<span id="iconLineMenu" name="trackIconLineMenu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC;">
			<input id="delete" class=workMune onclick='iconLineMenu.style.display="none";new  deleteEvent("line");' type="button" value="删除">
			<br>
			<input class=workMune onclick='iconLineMenu.style.display="none";' type="button" value="取消">
		</span>

		<input id="defineActionObjName" type="hidden">
		<input id="defineLineName" type="hidden">
		<input id="defineLineTrueId" type="hidden">
		<input id="defineActionTrueId" type="hidden">
		<input id="relationData" type="hidden">

		<FORM id="xpdlForm" name="xpdlFormName" method="post" action="/workflow/resource">
			<%	String newProcess = WebStr.iso8859ToGB2312(request
						.getParameter("newProcessAttribute"));
				String processid = request.getParameter("processid");
				String createprocess=request.getParameter("isCreate");
			%>
			<input id="processid" name="processid" type="hidden" value=<%=processid%>>
			<input id="xpdlContent" name="xpdlContent" type="hidden">
			<input id="description" name="description" type="hidden">
			<input id="createprocess" name="createprocess" type="hidden" value=<%=createprocess%>>

			<input id="newProcess" name="newProcessAttribute" type="hidden" value=<%="\""+newProcess+"\""%>>
			
			<input id="pathinfo" name="pathinfo" type="hidden" value=<%=path%>>
		</FORM>
		<Script>
		////页面上的鼠标信息///////
		document.onmousedown = engage // 按下鼠标的预先
		document.onmousemove = dragIt
		document.onmouseup = release
		new TrackItem();/*获得所有的活动对象*/
		read();
		new lineItem();/*获得所有的线条对象*/
		</Script>
	</body>
</html>
