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
	<body oncontextmenu="return false" onLoad="createTrackTable();" >

		<div align="center"></div>
		<div align="right"></div>
		<table align="right" width="100%" border="0"  cellpadding="0" cellspacing="0" height="27" style="border:1px solid #398ecf; background-image:url(<%=path%>/image/wf/lct.jpg);">
			<tr>
				<td align="left"><strong>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:saveProcess()'>[保存流程]</a>&nbsp;&nbsp;<a href='javascript:simu();'>[调试流程]</a></strong></td>
				
				<td align="right"><img src="<%=path%>/image/wf/lctLeftPic.jpg" border="0"></td>
			</tr>
		</table>
		<br>
		<div align="center">
			<div id="tools" style="DISPLAY:; Z-INDEX: 102; POSITION: absolute;; left: 1; top: 28px; width:100%; height: 30px; background-image:url(<%=path%>/image/wf/lct2.jpg);border-bottom:1px solid #aec3e3;border-left:1px solid #aec3e3; border-right:1px solid #aec3e3; visibility: visible;">
				<table border="0" align="right" cellpadding="0" cellspacing="0" style="font-size:14px;padding-top:5px; margin-left:7">
					<tr  height="30">
					  <td width="10" align="left">
							<span  style="color:#0fe2e2">|</span>
					  </td>
					  <td width="68" align="left">
							<input class=Menu type="submit" name="create" value="开始" onClick="new trackFactory('start');" style="cursor:hand;color:#336699;font-weight:bold;" ><span  style="color:#336699">|</span>
					  </td>
					  <td width="68" align="left">
							<input class=Menu type="submit" name="create" value="结束" onClick="new trackFactory('end');" style="cursor:hand;color:#336699;font-weight:bold;"><span  style="color:#336699">|</span>
					  </td>
					  <td width="68" align="center">
							<input class=Menu type="submit" name="create" value="节点" onClick="new trackFactory('trackAction');" style="cursor:hand;color:336699;font-weight:bold;">&nbsp;<span  style="color:#336699">|</span>
					  </td>
					  <td width="68" align="left">
							<input class=Menu type="submit" name="create" value="子流程" onClick="new trackFactory('subWorkFlow');" style="cursor:hand;color:#336699;font-weight:bold;"><span  style="color:#336699">|</span>
					  </td>
					  <td width="68" align="left">
							<input class=Menu type="submit" name="create" value="路由" onClick="new trackFactory('route');" style="cursor:hand;color:#336699;font-weight:bold;"><span  style="color:#336699">|</span>
					  </td>

						<td width="68" align="left">
							<input class=Menu type="submit" name="create" value="传输线" onClick="new trackFactory('beenLine');" style="cursor:hand;color:#336699;font-weight:bold;"><span  style="color:#336699">|</span>
					  </td>
					  <td width="68" align="center" >
							<input class=Menu type="button" name="Submit11" value="流程表单" onClick="relationData();" style="cursor:hand;color:#336699;font-weight:bold;"><span  style="color:#336699">|</span>
					  </td>
					  <td width="68" align="center" >
							<input class=Menu type="button" name="Submit11" value="流程属性" onClick="openWindowUpdataProcess();" style="cursor:hand;color:#336699;font-weight:bold;"><span  style="color:#336699">|</span>
     				  </td>

					</tr>
			  </table>
		  </div>
			<p>&nbsp;
		  </p>
			<div id="workFlowProperty" style="DISPLAY:; Z-INDEX: 102; POSITION: absolute; background-image:url(<%=path%>/image/wf/lct2.jpg);border-bottom:1px solid #c1dbf0;border-left:1px solid #c1dbf0; border-right:1px solid #c1dbf0;  left: 1px; top: 28px; width: 100%; height: 30px; visibility: hidden;">
				<table  border="0" cellpadding="0" cellspacing="0" align="center"  style="font-size:14px;padding-top:5px;margin-left:7">
					<tr height="30">

					</tr>
				</table>
			</div>

			<div id="simu" style="DISPLAY:; Z-INDEX: 102; POSITION: absolute; left: 1; top: 28px; width:100%; height: 30px; background-image:url(<%=path%>/image/wf/lct2.jpg);border-bottom:1px solid #aec3e3;border-left:1px solid #aec3e3; border-right:1px solid #aec3e3; height: 30px;visibility: hidden;">
				<table align="center" border="0" cellpadding="0" cellspacing="0" style="font-size:14px;padding-top:5px;margin-left:7">
					<tr height="30">
						<td width="68" align="center">
							
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
