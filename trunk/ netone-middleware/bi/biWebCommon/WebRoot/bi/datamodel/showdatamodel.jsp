
<%@ page contentType="text/html; charset=GBK"%>
<%String path = request.getContextPath();
%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>模型展现</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>

<body>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<div align="left">
			<H4>
				<font size="6">业务模型展现</font>
			</H4>
		</div>

		<tr>
			<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td  width="20%" align="center" >
						[ 优&nbsp;化 ]
					</td>
					<td align="center">
						<%=request.getAttribute("optimizeSB")%>
					</td>
				</tr>
			</table>
		</tr>
		<tr>
			<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="20%" align="center">
						[ 数据集 ]
					</td>
					<td align="center">
						<%=request.getAttribute("datasetSB")%>
					</td>
				</tr>
			</table>
		</tr>
		<tr>
			<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="20%" align="center">
						[ 指&nbsp;标 ]
					</td>
					<td align="center">
						<%=request.getAttribute("targetSB")%>
					</td>
				</tr>
			</table>
		</tr>
		<tr>
			<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="20%" align="center">
						[ 维&nbsp;度 ]
					</td>
						<%=request.getAttribute("dimSB")%>
				</tr>
			</table>
		</tr>
	</table>
</body>
</html:html>
