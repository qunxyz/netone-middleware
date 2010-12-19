<%@ page contentType="text/html; charset=GBK"%>
<%@page import="oe.security3a.client.rmi.ResourceRmi"%>
<%@page import="oe.rmi.client.RmiEntry"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>拷贝创建</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
		
		//打开树选择页面
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value);
		}
		//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		
		function sselected(text,value){
			document.getElementById('copysource').value=value;
		}
		
		function choice(){
			window.open('/cmsWeb/SSelectSvl?appname=PORTALPG.PORTALPG&pagename=pagegroup2');
		}
		
		function copydo(){
			var copysource=document.getElementById('copysource').value;
			window.open('/cmsWeb/CopyPortalSvl?id=${param.id}'+'&copysource='+copysource,'_self');
		}
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px">
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				alert("新建成功！")
				opener.search();
			</script>
		</c:if>
		<c:if test="${CreateSuccess == 'n'}">
			<script type="text/javascript">
				alert("新建目录失败！")
			</script>
		</c:if>
		<div style="width: 100%; height: 100%">
			<form action="PagelistAddSvl?task=addsave" method="post">

				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr style='display: none'>
						<td width="15%">
							有&nbsp;&nbsp;&nbsp;&nbsp;效
						</td>
						<td>
							<input type="checkbox" name="active" value="1" checked />
						</td>
					</tr>

					<tr>
						<td width="15%">
							拷贝源

						</td>
						<td width='300'>
							<input type='text' name='copysource'>
							<a href="javascript:choice()"><font color='blue'>选择</font> </a>
						</td>
					</tr>

				</table>
				<br>
				<div align="center">
					<input type="button" name="btnnew" value="新 建" onclick="copydo()"
						class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
