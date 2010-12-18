<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="oe.frame.web.WebCache"%>
<%@page import="oe.frame.web.util.WebTip"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String KEY_HEAD = "CAKEY_TEMP_";//每个临时CAkey的头标志
	String KEY_ALL = "CAKEY_TEMP_9LIST";//所有相关key的列表数组

	String usercode = request.getParameter("usercode");
	String cakey = request.getParameter("cakey");
	String limit = request.getParameter("limit");

	long limittime = 0;
	try {
		if(limit!=null&&!limit.equals("")){
			limittime = Integer.parseInt(limit);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	if (usercode != null && !usercode.equals("") && cakey != null
			&& !cakey.equals("") && limittime != 0) {
		long time = System.currentTimeMillis();
		limittime = time + limittime * 24 * 60 * 60 * 1000;
		Date limittimedate = new Date(limittime);
		String key="CAKEY_TEMP_" + cakey;
		WebCache.setCache(key, usercode,
				limittimedate);

		// 更新 Key 索引
		String keyall = (String) WebCache.getCache(KEY_ALL);
		if (keyall == null) {
			keyall = "";
		}
		keyall = keyall + "," + cakey;
		WebCache.setCache(KEY_ALL, keyall, null);
		// 打印更新提示信息
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		WebTip.htmlInfo("映射成功!CA:"+cakey+"到 "+usercode+" 有效期至:" + df.format(limittime), false,
				response);
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>临时CA证书映射</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
	<h1>CA证书映射管理</h1>
		<form action="">
		

			备用CA证书编码:
			<input type="text" id="cakey" name='cakey'>
			<br>
			<br>
			需要临时映射的客户编码:
			<input type="text" id='usercode' name='usercode'
				>
			<br>
			<br>

			有效日期:
			<select id='limit' name='limit'>
				<c:forEach begin="1" end="10" step="1" varStatus="vs">
					<option value="${vs.index }">
						${vs.index }
					</option>
				</c:forEach>
			</select>
			天

			<input type="submit" value='提交'>
			<br>

			<hr>
			<a href='<%=basePath%>human/CATempMappingList.jsp' target='_blank'>[管理所有的映射]</a>
		</form>
	</body>
</html>
