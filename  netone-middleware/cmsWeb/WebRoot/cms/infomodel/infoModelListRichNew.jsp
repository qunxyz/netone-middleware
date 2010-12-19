<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String done = (String) request.getAttribute("done");
%>
<html lang="true">
	<head>

		<title>portal列表</title>
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
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">

		<style type="text/css">
<!--
.STYLE1 {color: #D61418}
-->
</style>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<SCRIPT type="text/javascript">


		</SCRIPT>
	</head>

	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		style="font:14px">
		<form action="infomodellistRich.do?levelId=1" name="form1"
			method="post">


			<table width="98%" border="0" cellpadding="1" cellspacing="1"
				style="width: 100%;table-layout: fixed;">

				<tbody>
					<logic:present name="cmsinfomodellist">
						<logic:iterate id="row" name="cmsinfomodellist">
							<tr>


								<td width='85%'>
									<input name='markrs' class='butt' type='button' value='支持'
										onclick="window.open('<%=path%>/servlet/BlogAddMarkSvl?levels=1&modelid=<bean:write name='row'  property='modelid'/>','_blank','height=100, width=400, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')" />

									<font style='display:none'>from</font>

									<IMG
										onclick="window.open('vi/<bean:write name='row'  property='participant'/>.htm')"
										src='/securityweb/user/<bean:write name="row"  property="participant"/>'
										width='60' height='50' />
									<a
										href='http://www.oesee.cn/vi/<bean:write name='row'  property='participant'/>/'
										target='_blank'> <bean:write name="row"
											property="modelname" /> </a> [
									<font color='red'> <bean:write name="row" property="hit" />
									</font>
									<font color='#818181'>点</font>]
									<font color='#818181'>作者:</font>
									<font color='#818181'> <bean:write name="row"
											property="userinfo" filter="false" /> </font>
									<font style='display:none'>to</font>
								</td>

							</tr>
						</logic:iterate>
					</logic:present>
				</tbody>
			</table>


		</form>
	</body>
</html>
