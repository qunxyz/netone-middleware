<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@ taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>chart.jsp</title>
	<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    
	<link href="<%=path%>/include/css/css.css" rel="stylesheet" type="text/css">
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
	<script type="text/javascript">
		
		function showChart(charttype,offsetdim,dimstr,tgstr,multichart,pngwidth,maxvalue,showvalue,pictitle,piccolor,xqingxie){
			document.chartshow.selcharttype.value = charttype;
			document.chartshow.OffsetDim.value = offsetdim;
			document.chartshow.seldimvalue.value = dimstr;
			document.chartshow.seltgvalue.value = tgstr;
			if(document.all.flowpage.value=="flowpage"){
				//预测分析,图表页面不用,task为空
				if(document.all.task.value==""){
					document.chartshow.pageinfo.value = parent.opener.pginfo.toPginfostr();
				}
			} else {
				document.chartshow.pageinfo.value = parent.etlViewFrame.pginfo.toPginfostr();
			}
			
			document.chartshow.multichart.value = multichart;
			document.chartshow.pngwidth.value = pngwidth;
			document.chartshow.maxvalue.value = maxvalue;
			document.chartshow.showvalue.value = showvalue;
			document.chartshow.pictitle.value = pictitle;
			document.chartshow.piccolor.value = piccolor;
			document.chartshow.xqingxie.value = xqingxie;
			document.chartshow.submit();
		}
		
		
		function submitsel(){
			var str = "selcharttype="+document.chartselect.selcharttype.value
					+ "&seldimvalue="+document.chartselect.seldimvalue.value
					+ "&seltgvalue="+document.chartselect.seltgvalue.value
					+ "&OffsetDim="+document.chartselect.OffsetDim.value
					+ "&multichart="+document.chartselect.multichart.value
					+ "&act="+document.chartselect.act.value;
			if(document.all.flowpage.value=="flowpage"){
				var etlchartSelect = parent.document.frames["etlchartSelect"];
				if(document.all.task.value!=""){
					//跳转相应图表界面
					 etlchartSelect.location.href="<%=path%>/etlChartVM.do?"+str+"&flowpage=flowpage&task="+document.all.task.value;
				} else{
					 etlchartSelect.location.href="<%=path%>/etlChartVM.do?"+str+"&flowpage=flowpage";				
				}
			} else {
				window.open("<%=path%>/etlChartVM.do?"+str,"chartselect","width=550,height=210,left=250,top=250,resizable=yes");
			}
		}
		
	</script>
</head>

<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
	<c:if test="${errsmg != null}">
		<script type="text/javascript">
			alert('${errsmg}');
		</script>
	</c:if>
	
	<form action="<%=path%>/etlChartVM.do" name="chartselect" method="post" target="_blank">  
		<input type="hidden" name="act" value="CHART_SELECT">
		<input type="hidden" value="${paramMap.selcharttype}" name="selcharttype">
		<input type="hidden" value="${paramMap.seldimvalue}" name="seldimvalue">
		<input type="hidden" value="${paramMap.seltgvalue}" name="seltgvalue">
		<input type="hidden" value="${paramMap.OffsetDim}" name="OffsetDim">
		<input type="hidden" value="${paramMap.multichart}" name="multichart">
		<input type="hidden" value="${paramMap.pngwidth}" name="pngwidth">
		<input type="hidden" value="${paramMap.maxvalue}" name="maxvalue">
		<input type="hidden" value="${paramMap.showvalue}" name="showvalue">
		<input type="hidden" value="${paramMap.pictitle}" name="pictitle">
		<input type="hidden" value="${paramMap.piccolor}" name="piccolor">
		<input type="hidden" value="${paramMap.xqingxie}" name="xqingxie">
	</form>
	
	<form action="<%=path%>/etlChartVM.do" name="chartshow" method="post">  
		<input type="hidden" name="act" value="CHART_SHOW">
		<input type="hidden" value="" name="selcharttype">
		<input type="hidden" value="" name="seldimvalue">
		<input type="hidden" value="" name="seltgvalue">
		<input type="hidden" value="" name="OffsetDim">
		<input type="hidden" value="" name="pageinfo">
		<input type="hidden" value="" name="multichart">
		<input type="hidden" value="" name="pngwidth">
		<input type="hidden" value="" name="maxvalue">
		<input type="hidden" value="" name="showvalue">
		<input type="hidden" value="" name="pictitle">
		<input type="hidden" value="" name="piccolor">
		<input type="hidden" value="" name="xqingxie">
		<input type="hidden" name="flowpage" value="${flowpage}">
		<input type="hidden" name="task" value="${task}">
	</form>

		<c:forEach items="${chartlist}" var="chart" varStatus="vs">
			<img style="margin: 5px" src="${requestScope[chart.imgurlid]}" width="${requestScope[chart.imgwidthid]}" height="${requestScope[chart.imgheightid]}" ></img>
		</c:forEach>
	
	<script type="text/javascript">
		if(document.all.flowpage.value=="flowpage"){
			submitsel();
		}
	</script>
</body>
</html>
