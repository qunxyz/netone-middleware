<%@ page contentType="text/html; charset=GB2312"%>

<%String path = request.getContextPath();
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<link href="../../include/css/workflowtrack/style.css" rel="stylesheet" type="text/css">

		<script src="../../include/js/workflowtrack/process/processAttribute.js">
		</script>
	</head>
	<body onload="updataProcessAttribute()">
	<input type="hidden" name="pathinfo" value="<%=path%>" />
		<table width="500" border="1" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="2" align="center">
					<table width="100%">

						 <input id="processId" size="30" type="hidden" name="textfield" readOnly>
	
						<tr align="left">
							<td width="200" bgcolor="#CCCCFF" align="center">
								流程名:
							</td>
							<td width="288" bgcolor="#CCCCFF">
								<input id="processName" size="30" type="text" name="textfield2">
							</td>
						</tr>
						<tr align="left">
							<td width="200" bgcolor="#CCCCFF" align="center">
								流程创建时间:
							</td>
							<td width="288" bgcolor="#CCCCFF">
								<input id="processCreateDate" size="30" type="text" name="textfield2" readOnly>
							</td>
						</tr>
					</table>
					<table width="100%">
						<tr align="left">
							<td width="390">
								扩展属性集
							</td>
						</tr>
					</table>
					<table width="100%" height="100%" border="1" cellpadding=0 cellspacing=0>
						<tr>
							<td width="439" height="178" valign="top">
								<table width="100%" border="1" cellpadding=0 cellspacing=0 bgcolor="#CCCCCC" style=" BORDER-COLLAPSE: collapse">
									<tr>
										<td width="49%">
											<div align="center">
												名字
											</div>
										</td>
										<td width="51%">
											<div align="center">
												值
											</div>
										</td>
									</tr>
								</table>
								<IFRAME frameBorder=0 id=pAttributeList name=pExtendFrame src="track/extend/process/processExtend.html" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"></IFRAME>
							</td>
							<td width="55" align="center" valign="top">
								<input type="button" name="Submit2" value="新建" onclick="(new openWindow()).newExtend();">
								<label>
									<input type="button" name="Submit3" value="编辑" onclick="(new openWindow()).editExtend();">
								</label>
								<input type="button" name="Submit4" value="删除" onclick="deleteOpe();">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center">
						<input type="button" name="Submit" value="提交" onClick="(new bean()).editGetValue();">
						&nbsp;
						<input type="button" name="Submit" value="关闭" onClick="self.close();">
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
