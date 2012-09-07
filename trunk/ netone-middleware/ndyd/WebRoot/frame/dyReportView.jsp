<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
	<script language="javascript" type="text/javascript"
		src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
	<script type="text/javascript" src="<%=path%>/tiny_mce/jquery.tinymce.js"></script>	
	<!-- 样式文件 -->	
	${linkcss}
	<!-- 时间控件脚本 -->
	${datecompFunc}
	<script type="text/javascript">
		var selectObjVar = null;//全局变量 存放需要选择资源返回值的对象
	function $select(o,url){
		selectObjVar=o;
		openWinCenter("选择",encodeURI(encodeURI(url)),800,420,true);
	}
	</script>
	
	<script type="text/javascript">
		var format = "excel";
		function query(){
		    
		    var formatstr = "&format=excel";
		    var form1 = document.getElementById('_xreport_form');
			form1.action="<c:url value='/frame.do?method=queryDyReport' />"+formatstr;
			form1.target="_blank";
			form1.method="POST";
			form1.submit();
		}
		
		$(function() {
			$("#tabs").tabs();
			$('#tabs').tabs('select', "tabs");
		});
		
	</script>
  </head>
  
  <body>
  	<center>
  	
	  	<div align="center" style="width:900px;">
			<div id="tabs" style="height: 100%;">
				<ul>
					<li>
						<a href="#tabs-1" onclick="" class="ui-tabs-selected">报表查询</a>
					</li>
				</ul>
				<div id="tabs-1">
				
					<div align='center'>
					<form id="_xreport_form">
					输入查询语句：
					<textarea rows="20" cols="150" id="sql" name="sql"></textarea>
					</form>
					<BR>
					<input type="button" value="查询" onclick="query();" class="btn">
					</div>
				</div>
			</div>
		</div>
		
	
	</center>
  </body>
</html>
