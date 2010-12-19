<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
		<script type="text/javascript" src="include/js/util.js"></script>
		<link href="frames/css/css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		var preTabId = null;
function changeTab(tab){
	if(preTabId == null){
		preTabId = "tab1";
	}
	
	var preTab = document.getElementById(preTabId)

	preTab.childNodes[0].style.display="none";	
	preTab.childNodes[1].style.display="block";
	
	tab.childNodes[0].style.display="block";	
	tab.childNodes[1].style.display="none";
	
	var prediv = document.getElementById("div_"+preTabId);
	var div = document.getElementById("div_"+ tab.id);
	
	prediv.style.display = "none";
	div.style.display = "block";
	
	preTabId = tab.id ;
}
		
		
		</script>
	</head>
	<body style="margin: 22px">
		<form name="form1" action="" method="post">
			<c:if test="${res == 'res'}">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<%
						int i = 0;
						%>
						<c:forEach items="${list}" var="list">
							<%
										i++;
										String tab = "tab" + i;
							%>
							<%
							if (i == 1) {
							%>
							<td id=<%=tab%> style="cursor: hand" onclick="changeTab(this)"
								width="100">
								<table border="0" cellpadding="0" cellspacing="0"
									style="display: block">
									<tr>
										<td width="4" class="td_page_l"></td>
										<td class="td_page_bg">
											${list.name}
										</td>
										<td width="4" class="td_page_r"></td>
									</tr>
								</table>
								<table border="0" cellpadding="0" cellspacing="0"
									style="display: none">
									<tr>
										<td width="4" class="td_page_on_l"></td>
										<td class="td_page_bg_on">
											${list.name}
										</td>
										<td width="4" class="td_page_on_r"></td>
									</tr>
								</table>
							</td>
							<%
							} else {
							%>
							<td id=<%=tab%> style="cursor: hand" onclick="changeTab(this)"
								width="100">
								<table border="0" cellpadding="0" cellspacing="0"
									style="display: none">
									<tr>
										<td width="4" class="td_page_l"></td>
										<td class="td_page_bg">
											${list.name}
										</td>
										<td width="4" class="td_page_r"></td>
									</tr>
								</table>
								<table border="0" cellpadding="0" cellspacing="0"
									style="display: block">
									<tr>
										<td width="4" class="td_page_on_l"></td>
										<td class="td_page_bg_on">
											${list.name}
										</td>
										<td width="4" class="td_page_on_r"></td>
									</tr>
								</table>
							</td>
							<%
							}
							%>

						</c:forEach>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td height="3" colspan="8" bgcolor="#0d509d"></td>
					</tr>
					<tr>
						<td height="2" colspan="8" bgcolor="#d4d0c8"></td>
					</tr>
				</table>
				<%
					int j = 0;
				%>
				<c:forEach items="${list}" var="list">
					<%
								j++;
								String div_tab = "div_tab" + j;
					%>
					<div id=<%=div_tab%> style="width: 100%;display: none">
						<table width="96%" height="550" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td>
									<iframe src="${list.actionurl}"  height="100%" width="100%" frameborder="0"></iframe>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${res != 'res'}">
				<iframe src="${url}" height="100%" width="100%" frameborder="0"></iframe>
			</c:if>
		</form>
	</body>
</html>
