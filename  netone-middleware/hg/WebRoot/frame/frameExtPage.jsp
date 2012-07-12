<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
String cssURL = request.getContextPath()+ "/script/theme/main/blue/images";	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
		
		<title><c:choose><c:when test="${!empty htmltitleinfo}">${htmltitleinfo} - ${activityName}</c:when><c:otherwise>${activityName}</c:otherwise></c:choose></title>
	</head>
	<body>
		<center>
		<div id="app-header" style="z-index:9999;" align="left">
		<center>
		<div id="top_nd_bg_1" style="height:35px;background-image: url('<%=cssURL%>/top_nd_bg.gif');z-index:9999;">
			<div id="top_nd_bg" style="background-image: url('<%=cssURL%>/top_nd_bg.gif');text-align: left;width:1000px;z-index:9999;">
				<div id="btn_nd">
						<input id="upBtn_5_3" type="button" value=" 新建工单 " class="btn"
							onclick="javascript:window.open('<%=path%>/frame.do?method=onEditViewMain&isadd=1&naturalname=${param.naturalname}');" />
				<input id="upBtn_5_3" type="button" value=" 关 闭 " class="btn"
							onclick="javascript:window.close();" />
				</div>
			</div>		
		</div>
		</center>
	</div>
		<div align="center" style="width:1000px;">
			<div>
			<table width="100%" >
				<tr>
					<td width="12px" align="left">
						<div>
						</div>
					</td>
				</tr>
			</table>
			<hr id="hr_nd">
		</div>
		<div id="tabs" style="height: 100%;">
			<ul>
				<li>
					<a href="#tabs-1" class="ui-tabs-selected">文档</a>
				</li>
			</ul>
			<div id="tabs-1">
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_2" width="100%" >
							<iframe width="100%" height="500"
								src="${urltext}"
								scrolling="auto" frameborder="0"
								>
							</iframe>
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>
<script type="text/javascript">
<!--
$(function() {
			
	$("#tabs").tabs();
	$('#tabs').tabs('select', "tabs-1");
	
});
//-->
</script>
