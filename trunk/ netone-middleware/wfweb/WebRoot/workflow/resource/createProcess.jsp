<%@ page contentType="text/html; charset=GBK"%>
<%
	String processid = (String) request.getAttribute("processid");
	String created = (String) request.getAttribute("created");

	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type">

		<link href="<%=path%>/include/css/workflowtrack/style.css"
			rel="stylesheet" type="text/css">
		<script
			src="<%=path%>/include/js/workflowtrack/process/processAttribute.js">
		</script>
	</head>
	<body>
		<FORM action="<%=path%>/workflow/resource/check.jsp"
			name="editProcessForm" method="post">
			<table width="500" border="1" align="left" cellpadding="0"
				cellspacing="0">
				<tr>
					<td colspan="2" align="center">
						<table width="100%">
							<input id="pathinfo" type="hidden" value="<%=path%>">

							<input id="processId" type="hidden" name="textfield" size="35"
								value="<%=processid%>">

							<tr align="left">
								<td width="50%" bgcolor="#C0C0C0" align="center">
									������:
								</td>
								<td width="50%" bgcolor="#C0C0C0">
									<input id="processName" type="text" name="processName"
										size="35" value="">
								</td>
							</tr>
							<tr align="left">
								<td width="50%" bgcolor="#C0C0C0" align="center">
									������:
								</td>
								<td width="50%" bgcolor="#C0C0C0">
									<input id="Name" type="text" name="Name" size="35"
										value="">
								</td>
							</tr>
							<tr style='display:none' align="left">
								<td width="50%" bgcolor="#C0C0C0" align="center">
									���̴���ʱ��:
								</td>
								<td width="50%" bgcolor="#C0C0C0">
									<input id="processCreateDate" type="text" name="textfield2"
										size="35" value="<%=created%>">
								</td>
							</tr>
							<tr align="left">
								<td bgcolor="#C0C0C0" align="left" colspan="2">
									��չ���Լ�
								</td>
							</tr>
						</table>

						<table width="100%" height="100%" border="1" cellpadding=0
							cellspacing=0>
							<tr>
								<td width="439" height="178" valign="top">
									<table width="100%" border="1" cellpadding=0 cellspacing=0
										bgcolor="#CCCCCC" style=" BORDER-COLLAPSE: collapse">
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
									<IFRAME frameBorder=0 id=pAttributeList name=pExtendFrame
										src="<%=path%>/workflow/resource/track/extend/process/processExtend.html"
										style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"></IFRAME>
								</td>
								<td width="55" align="center" valign="top">
									<input type="button" name="Submit2" value="�½�"
										onclick="(new openWindow()).newExtend();">
									<label>
										<input type="button" name="bou" value="�༭"
											onclick="(new openWindow()).editExtend();">
									</label>
									<input type="button" name="Submit4" value="ɾ��"
										onclick="deleteOpe();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
					<script>
					function todo(){
					  				var process=document.getElementById("processName");
									var name=document.getElementById("name");
									if(process.value=='' || name==''){
										alert('������������������������Ϊ��');
										return;
									}
									process.value=process.value+"["+name.value+"]";
									(new bean()).newGetValue();
					}
					</script>
						<div align="center">
							<input type="button" name="Submit" value="�ύ"
								onClick="todo()">
							&nbsp;
							<input type="button" name="Submit" value="�ر�"
								onClick="self.close();">
						</div>
					</td>
				</tr>
			</table>
			<input id="newProcess" name="newProcessAttribute" type="hidden">
			<input id="newProcessOpe" name="newProcessOpeName" type="hidden"
				value="saveProcessOpe.do">
		</FORM>
	</body>
</html>
