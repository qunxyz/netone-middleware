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
	String KEY_ALL = "CAKEY_TEMP_9LIST";//�������key���б�����
	String KEY_HEAD = "CAKEY_TEMP_";

	//ɾ����ص�ӳ��
	String key = request.getParameter("removekey");
	if(key!=null&&!key.equals("")){
	  WebCache.removeCache(KEY_HEAD + key);
	}
	
	//չ�����г����е�ӳ��
	String keyall = (String) WebCache.getCache(KEY_ALL);
	if (keyall != null) {
		String[] keyallarr = keyall.split(",");
		List list = new ArrayList();
		Map index=new HashMap();//�����ؽ�����,ͬʱҲ��������ظ�������
		for (int i = 0; i < keyallarr.length; i++) {
			String keyvalue = (String) WebCache.getCache(KEY_HEAD
					+ keyallarr[i]);
			//�����ص�ӳ��ֵ���ڵ������
			if(keyvalue!=null&&!keyvalue.equals("")){
				index.put(keyallarr[i],null);
				list.add(new String[] { keyallarr[i], keyvalue });
			}
		}
		//���¹�������
		StringBuffer indexStr=new StringBuffer();
		for(Iterator itr=index.keySet().iterator();itr.hasNext();){
			indexStr.append(itr.next()+",");
		}
		WebCache.setCache(KEY_ALL,indexStr.toString(),null);//��������
		request.setAttribute("list", list);
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
						��ʱ��½�û�
					</td>
					<td>
						CA֤��KEY
					</td>
					<td>
						����
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
							<input type='button' value='ɾ��' onClick="remove('${var[0]}')" />
						</td>
						
					</tr>
				</c:forEach>
			</table>

		</form>
	</body>
</html>
