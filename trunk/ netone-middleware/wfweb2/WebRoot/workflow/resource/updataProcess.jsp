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
								������:
							</td>
							<td width="288" bgcolor="#CCCCFF">
								<input id="processName" size="30" type="text" name="textfield2">
							</td>
						</tr>
						<tr align="left">
							<td width="200" bgcolor="#CCCCFF" align="center">
								���̴���ʱ��:
							</td>
							<td width="288" bgcolor="#CCCCFF">
								<input id="processCreateDate" size="30" type="text" name="textfield2" readOnly>
							</td>
						</tr>
					</table>
					<table width="100%">
						<tr align="left">
							<td width="390">
								��չ���Լ�
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
												����
											</div>
										</td>
										<td width="51%">
											<div align="center">
												ֵ
											</div>
										</td>
									</tr>
								</table>
								<IFRAME frameBorder=0 id=pAttributeList name=pExtendFrame src="track/extend/process/processExtend.html" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"></IFRAME>
							</td>
							<td width="55" align="center" valign="top">
								<input type="button" name="Submit2" value="�½�" onclick="(new openWindow()).newExtend();">
								<label>
									<input type="button" name="Submit3" value="�༭" onclick="(new openWindow()).editExtend();">
								</label>
								<input type="button" name="Submit4" value="ɾ��" onclick="deleteOpe();">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center">
						<input type="button" name="Submit" value="�ύ" onClick="(new bean()).editGetValue();">
						&nbsp;
						<input type="button" name="Submit" value="�ر�" onClick="self.close();">
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
