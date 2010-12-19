<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<script type="text/javascript">
		function hidenDo(){
			pageleft.style.display='none';
			show.style.display='';
			hid.style.display='none';
			pagelefttd.style.width='0';
		}
		function showDo(){
			pageleft.style.display='';
			show.style.display='none';
			hid.style.display='';
			pagelefttd.style.width='170';
		}		
		</script>
	</head>
	<body BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0>
		<table width="100%" height="100%" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td colspan='2'>
					<iframe id="head"
						src="/fck/PagelistViewSvl?pagename=simplefcklist&chkid=${param.fckid}"
						scrolling="no" resize="no" height="${param.height}" width="100%"
						frameborder='0'></iframe>
				</td>
			</tr>
			<tr>
				<c:if test="${param.layout!='right'}">
					<td id='pagelefttd' width="170" height="100%" valign="top" nowrap>
						<c:if test="${param.needhidden=='y'}">
							<div style="position: absolute; z-index: 9999;">
								<a href="javascript:hidenDo()" id='hid'><font size='2'>[Òþ±Î]</font>
								</a>
								<a href="javascript:showDo()" id='show' style="display: none"><font
									size='2'>[ÏÔ] </font> </a>
							</div>
						</c:if>
						<div id='pageleft'>
							<iframe id="proletleft"
								src="frames.do?listPath=${param.rs}&type=frame" scrolling="no"
								resize="no" height="100%" width="100%" frameborder='0'></iframe>
						</div>
					</td>
					<td height="100%" align='left'>
						<iframe align="top" id="proletright"
							src="${fn:replace(param.initurl,'$@','&')}" scrolling="no"
							resize="no" height="100%" width="900" frameborder='0'></iframe>
					</td>
				</c:if>
				<c:if test="${param.layout=='right'}">
					<td height="100%" align='left'>
						<iframe align="top" id="proletright"
							src="${fn:replace(param.initurl,'$@','&')}" scrolling="no"
							resize="no" height="100%" width="900" frameborder='0'></iframe>
					</td>
					<td id='pagelefttd' width="170" height="100%" valign="top" nowrap>
						<c:if test="${param.needhidden=='y'}">
							<div style="position: absolute; z-index: 9999;">
								<a href="javascript:hidenDo()" id='hid'><font size='2'>[Òþ±Î]</font>
								</a>
								<a href="javascript:showDo()" id='show' style="display: none"><font
									size='2'>[ÏÔ] </font> </a>
							</div>
						</c:if>
						<div id='pageleft'>
							<iframe id="proletleft"
								src="frames.do?listPath=${param.rs}&type=frame" scrolling="no"
								resize="no" height="100%" width="100%" frameborder='0'></iframe>
						</div>
					</td>

				</c:if>
			</tr>
		</table>
	</body>
</html>
