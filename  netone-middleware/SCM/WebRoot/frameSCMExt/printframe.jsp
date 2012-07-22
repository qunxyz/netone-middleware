<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<!-- 样式文件 -->	
		${linkcss}
		<!-- 时间控件脚本 -->
		${datecompFunc}
		<style type="text/css">
		table{float:left;}
		</style>
		<title>${htmltitleinfo}</title>
	</head>
	<body>
		<!--  
		<div region="west" title="左边栏" style="width:150px;">
			<BR>
			内容
		</div>
		-->
		<h2 align="center" style="font-size: 22px;">${htmltitleinfo}<h2>
		<input type="hidden" id="runtimeid" name="runtimeid" value="${param.runtimeid}"/>
		<input type="hidden" id="ext" name="ext" value="${param.ext}"/>
		<input type="hidden" id="workcode" name="workcode" value="${param.workcode}"/>
		<input type="hidden" id="htmlcache" name="htmlcache" value="" />
		<input type="hidden" id="chooseresult" name="chooseresult" value="${param.chooseresult}">
		<!-- 动态表单 start-->
		${form}
		<!-- 动态表单 end-->
				
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>
