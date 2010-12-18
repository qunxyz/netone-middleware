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
	String KEY_ALL = "CAKEY_TEMP_9LIST";//所有相关key的列表数组
	String KEY_HEAD = "CAKEY_TEMP_";

	//删除相关的映射
	String key = request.getParameter("removekey");
	if(key!=null&&!key.equals("")){
	  WebCache.removeCache(KEY_HEAD + key);
	}
	
	//展现罗列出所有的映射
	String keyall = (String) WebCache.getCache(KEY_ALL);
	if (keyall != null) {
		String[] keyallarr = keyall.split(",");
		List list = new ArrayList();
		Map index=new HashMap();//用于重建索引,同时也解决索引重复的问题
		for (int i = 0; i < keyallarr.length; i++) {
			String keyvalue = (String) WebCache.getCache(KEY_HEAD
					+ keyallarr[i]);
			//如果相关的映射值存在的情况下
			if(keyvalue!=null&&!keyvalue.equals("")){
				index.put(keyallarr[i],null);
				list.add(new String[] { keyallarr[i], keyvalue });
			}
		}
		//重新构建索引
		StringBuffer indexStr=new StringBuffer();
		for(Iterator itr=index.keySet().iterator();itr.hasNext();){
			indexStr.append(itr.next()+",");
		}
		WebCache.setCache(KEY_ALL,indexStr.toString(),null);//更新索引
		request.setAttribute("list", list);
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
		<script type="text/javascript">
		function remove(key){
		   document.getElementById('removekey').value=key;
			document.forms[0].submit();
		}
		</script>
	</head>

	<body>
		<form action="">
			<input type='hidden' value='' name='removekey' id="removekey" />
			<table border='1' cellspacing="3">
				<tr>
					<td>
						临时登陆用户
					</td>
					<td>
						CA证书KEY
					</td>
					<td>
						操作
					</td>
				</tr>
				<c:forEach items="${list}" var="var">
					<tr>
						
						<td>
							${var[1]}
						</td>
						<td>
							${var[0]}
						</td>
						<td>
							<input type='button' value='删除' onClick="remove('${var[0]}')" />
						</td>
						
					</tr>
				</c:forEach>
			</table>

		</form>
	</body>
</html>
