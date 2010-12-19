
<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
String id=request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>
			My JSP 'cut.jsp' starting page
		</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			var contextpath = "<%=path%>";
		</script>
		<script src="<%=path%>/include/js/prototype.js"></script>
		<script src="<%=path%>/include/js/util.js"></script>
		<script src="<%=path%>/include/js/floatdiv/cut2.js"></script>

		<!--
    	<link rel="stylesheet" type="text/css" href="styles.css">
    	-->
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
	</head>

	<body style="margin:0px;" onload='setiframesrc()'>
		<div id="overdiv" style="filter:Alpha(opacity=0);position:absolute;z-index:1;width:100%;height:4000;border:1 solid blue;background-color:white;top:50px">
		</div>
		<div id="cutdiv" style="display:none;filter:Alpha(opacity=50);position:absolute;z-index:999;width:0;height:0;border:1 solid red;background-color:#E7E7E7;">
		</div>
		<div id="sizediv" style="display:none;position:absolute;z-index:999;width:50;height:40;border:0;background-color:#FFFFFF;">
		</div>
		<table style="width: 100%; height: 4000">
		<form action="">
		<input type='hidden' value='${param.id}' name='id'/>
		<input type='hidden' value='${param.cname}' name='cname'/>
		<input type='hidden' value='${param.naturalname}' name='naturalname'/>
		<input type='hidden' value='' name='args'/>
		<input type='hidden' value='cut' name='objtype'/>
		</form>
			<tr>
				<td height="50">
					<div>
						<font size='2'>
							请输入URL地址:
						</font>
						<input type="text" id="srctext" style="width:300px;" value="${param.url}">
						<input type="hidden" id="realsrc" value="${param.url}" />
						<input type="button" class="butt" value="确定" onclick="setiframesrc();">
					</div>
					<hr>
				</td>
			</tr>
			<tr>
				<td>
					<IFRAME id="ifarme" src="" frameborder="0" scrolling="auto" style="margin:0px;width:100%;height:100%">
					</IFRAME>
				</td>
			</tr>
		</table>
	</body>
</html>
