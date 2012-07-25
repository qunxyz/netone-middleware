<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript">
		//新建并上传文件
		function uploadfile(){
		    
			form1.action="PagelistUploadSvl?dirid=${dirid}&add=yes";
			form1.submit();
		}
		</script>
	</head>

	<body style="font-size: 12px;margin: 22px">

		<form action="" method="post" name="form1"
			enctype="multipart/form-data">
			<input type="hidden" name="pagename" value="${pagename}" />
			<input type="hidden" name="dirid" value="${dirid}">
			<input type="hidden" name="appid" value="${appid}">
			<table width="96%" border="0" align="center" cellpadding="0"
				cellspacing="0" id="lie_table">

				<tr>
					<td>
						附件上传
						<input type="file" onchange="autofile();" name="files">
						<input type="button" value="添加" onclick="uploadfile();"
							class="butt">
					</td>
				</tr>
			</table>

			<br>


				<table align=left cellpadding=0 cellspace=0 border=0>
					<tr>
						<c:forEach items="${piclist}" var="picx">
							<td>
								<a href='/device/picroot/${dirid}/${picx}' target='_blank'/>${picx}</a>
							</td>
						</c:forEach>
					</tr>
				</table>

		</form>
	</body>
</html>
