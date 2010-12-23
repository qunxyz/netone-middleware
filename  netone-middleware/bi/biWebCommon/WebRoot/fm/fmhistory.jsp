<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>表格显示</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="include/css/css.css" rel="stylesheet"	type="text/css">
	<style type="text/css">
	BODY {
        BORDER-TOP-WIDTH: 4px; 
		SCROLLBAR-FACE-COLOR: #EDF0F6; 
		BORDER-LEFT-WIDTH: 4px; 
		FONT-SIZE: 9pt; 
		BORDER-BOTTOM-WIDTH: 4px; 
		SCROLLBAR-HIGHLIGHT-COLOR: #ffffff; 
		SCROLLBAR-SHADOW-COLOR: #B3CDEA; 
		SCROLLBAR-3DLIGHT-COLOR: #B3CDEA; 
		SCROLLBAR-ARROW-COLOR: #B0C7E1; 
		SCROLLBAR-TRACK-COLOR: #F7FBFF; 
		SCROLLBAR-DARKSHADOW-COLOR: #ffffff; 
		SCROLLBAR-BASE-COLOR: #EEF7FF; 
		BORDER-RIGHT-WIDTH: 4px;
	}
	</style>
	<script type="text/javascript" src="include/js/page.js"></script>
	
  </head>
  
  <body BGCOLOR=#EDF0F6 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0 style="font:14px">
		
		
		<form action="fmHistory.do" name="tableform" method="post" >
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="#A9C0E5" align="center">
				<tr class="td-bg01" align="center">
					<td height="21" bgcolor="#FFFFFF">
						设备名称
					</td>
					<td height="21" bgcolor="#FFFFFF">
						设备中文名
					</td>
					<td height="21" bgcolor="#FFFFFF">
						设备地理位置
					</td>
					<td height="21" bgcolor="#FFFFFF">
						告警号
					</td>
					<td height="21" bgcolor="#FFFFFF">
						告警发生时间
					</td>
					<td height="21" bgcolor="#FFFFFF">
						告警取消时间
					</td>
					<td height="21" bgcolor="#FFFFFF">
						原始告警
					</td>
					<td height="21" bgcolor="#FFFFFF">
						原告警级别
					</td>
					<td height="21" bgcolor="#FFFFFF">
						重定义告警级别
					</td>
					<td height="21" bgcolor="#FFFFFF">
						地区名
					</td>
					<td height="21" bgcolor="#FFFFFF">
						厂家
					</td>
				</tr>
				<!-- 打印表格数据-->
				<c:forEach items="${dataList}" var="row">
					<tr class="td-02" align="center">
						<c:forEach items="${row}" var="cell">
							<td height="21" bgcolor="#FFFFFF">
								${cell}
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			
			<script type="text/javascript">
		    	var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.tableform);
		    	pginfo.write();
		    </script>
						    
		</form>
  </body>
</html>
