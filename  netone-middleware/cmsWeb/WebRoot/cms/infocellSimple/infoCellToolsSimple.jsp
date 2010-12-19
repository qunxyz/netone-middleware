<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="Oesee tools">
		<script src="<%=path%>/include/js/prototype.js"></script>

		<script type="text/javascript"
			src="<%=path%>/include/js/cell/cellToolsSample.js"></script>

		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">

	</head>

	<body onLoad="dispJppScriptDemo();" BGCOLOR=#E2E2E2 LEFTMARGIN=0
		TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
		<table>
			<tr>
				<td id='JppScriptDemo'></td>
				<td>
					<input class='butt' id='addJppScript' type='button' value='使用'
						onClick='addJppDemo()'>
					&nbsp;&nbsp;
					<input id='listJppScriptx' type='checkbox' value='ok'
						onClick='openJppDemoDIY()'>
					更多模板
				</td>
				<td align='left'>
					<table>
						<tr>
							<td id='JppScriptDemoMore' style='display:none' nowrap>
							</td>
							<td id='JppScriptDemoMoreUse' style='display:none'>
								<input id='userother' class='butt' type='button' value='刷新'
									onClick='openJppDemoDIY()'>
								<input id='userother' class='butt' type='button' value='使用'
									onClick='addJppDemoMore()'>
								<input id='userother' class='butt' type='button' value='设计'
									onClick='designJppDemoMore()'>

							</td>
						</tr>
					</table>
				</td>
			</tr>
			</table>
			<hr>
			
			<table>
			<tr>
				<td nowrap>

					OESEEJ++脚本中的参数资源:&nbsp;&nbsp;
					图片/附件资源<input name='selGroup' type='radio' value='RS' onClick="openElementAuto('RS')">&nbsp;&nbsp;
					动态表单<input name='selGroup' type='radio' value='DF' onClick="openElementAuto('DF')">&nbsp;&nbsp;
					论坛资源<input name='selGroup' type='radio' value='FM' onClick="openElementAuto('FM')">&nbsp;&nbsp;
					OeseeJ++脚本<input name='selGroup' type='radio' value='SC' onClick="openElementAuto('SC')">

				</td>
			</tr>
			</table>
			<br>
			
			<table style='display:none' id='resourceelement' width='80%'>
			<tr>
				<td nowrap>
					<INPUT class='butt' type='button' value='管理资源' onClick='manEle()'>
					<INPUT class='butt' type='button' value='刷新'
						onClick="openElementAutoWithOutPama()">
				</td>

				<td id='Fmlisttd'>
					<select id='fmlist' onChange="openElementAuto('FM')">
						<OPTION value='52a2a0f20be4e677010be4e9c4c40003'>
							[Java培训]
						</OPTION>
						<OPTION value='52a2a0f20eb6a4a7010eb6bc1a2c0003'>
							[畅想空间应用交流]
						</OPTION>
						<OPTION value='52a2a0f20eb6a4a7010eb6bc09ff0002'>
							[J2EE技术交流区]
						</OPTION>
						<OPTION value='52a2a0f2108276ab011085632e950007'>
							[开源技术交流]
						</OPTION>
						<OPTION value='52a2a0f2108276ab0110856436450009'>
							[软件应用技术交流]
						</OPTION>
						<OPTION value='52a2a0f20eb6a4a7010eb6bbf1570001'>
							[外语互动交流]
						</OPTION>
						<OPTION value='52a2a0f2108276ab01108564f7a8000b'>
							[大学物语]
						</OPTION>
						<OPTION value='52a2a0f210879b6501109c14d8910002'>
							[星期六]
						</OPTION>
					</select>

				</td>
				<td id='Dflist'></td>

				
				<td>
					<input id='iddisp2' type='text' value='' size='15'>
				</td>
				<td nowrap>
					<div id='NextElement'>
					</div>
				</td>
				<td nowrap>
					<input id='iddisp' type='text' value='' size='15'>
				</td>


			</tr>
		</table>
	</body>
</html>
