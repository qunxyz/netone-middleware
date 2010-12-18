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

	String KEY_HEAD = "CAKEY_TEMP_";//ÿ����ʱCAkey��ͷ��־
	String KEY_ALL = "CAKEY_TEMP_9LIST";//�������key���б�����

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

		// ���� Key ����
		String keyall = (String) WebCache.getCache(KEY_ALL);
		if (keyall == null) {
			keyall = "";
		}
		keyall = keyall + "," + cakey;
		WebCache.setCache(KEY_ALL, keyall, null);
		// ��ӡ������ʾ��Ϣ
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		WebTip.htmlInfo("ӳ��ɹ�!CA:"+cakey+"�� "+usercode+" ��Ч����:" + df.format(limittime), false,
				response);
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>��ʱCA֤��ӳ��</title>

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
	<h1>CA֤��ӳ�����</h1>
		<form action="">
		

			����CA֤�����:
			<input type="text" id="cakey" name='cakey'>
			<br>
			<br>
			��Ҫ��ʱӳ��Ŀͻ�����:
			<input type="text" id='usercode' name='usercode'
				>
			<br>
			<br>

			��Ч����:
			<select id='limit' name='limit'>
				<c:forEach begin="1" end="10" step="1" varStatus="vs">
					<option value="${vs.index }">
						${vs.index }
					</option>
				</c:forEach>
			</select>
			��

			<input type="submit" value='�ύ'>
			<br>

			<hr>
			<a href='<%=basePath%>human/CATempMappingList.jsp' target='_blank'>[�������е�ӳ��]</a>
		</form>
	</body>
</html>
