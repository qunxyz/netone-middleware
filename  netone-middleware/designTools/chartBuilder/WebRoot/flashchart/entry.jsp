<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<%
  	int r = (int) (Math.random() * 999999);// 随机数
  	%>
    <title><%=request.getAttribute("title") %></title>
	<script src="AC_OETags.js" language="javascript"></script>  
	<script language="JavaScript" type="text/javascript" src="../history/history.js"></script>  
	<script language="javascript" type="text/javascript" src="chartType1.js"></script>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="<%=request.getAttribute("title") %>">
	<meta http-equiv="description" content="<%=request.getAttribute("title") %>">
	<script>
 	function onGetJsonParam(){//供Flex调用
 		var json = document.getElementById('jsonParam').innerHTML;
 		return json;
 	}
 	
 	function onSubmitToNext(param){//提交表单并打开新链接
 		 document.getElementById('param').value=param;
		 document.getElementById('$linkform').action="<%=basePath%>flashChartAction";
		 document.getElementById('$linkform').submit();
 	}
	</script> 
  </head>
<body bgcolor="#EBEAE5" leftmargin="0" topmargin="0">
	<form id="$linkform" target="_blank" method="POST" style="display: none">
		<input id="param" name="param" type="hidden">
	</form>
	<div id="jsonParam" style="display: none">
		<%=request.getAttribute("jsonParam") %>
	</div>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="fchart1" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="<%=basePath%>flashchart/flashchart.swf?r=<%=r%>"/>
			<param name="quality" value="high" />
			<param name="bgcolor" value="#869ca7" />
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="<%=basePath%>flashchart/flashchart.swf?r=<%=r%>" quality="high" bgcolor="#869ca7"
				width="100%" height="100%" name="fchart1" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
</body>
</html>