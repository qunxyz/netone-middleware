<%@ page language="java" import="java.util.*" pageEncoding="GB2312"
	contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String flowpage = request.getParameter("flowpage");
	String task = request.getParameter("task");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>查看图表条件选择框</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/include/css/css.css">
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
		<SCRIPT type="text/javascript">
    	function checkseldim(){
    		var seldatatype = document.all.seldatatype;
    		if(seldatatype.value=='ne-time' || seldatatype.value=='time-ne'){
    			return true ;
    		}

    		var sels = document.getElementsByName("seldim");
    		var nosel = 0
    		for(i=0 ;i<sels.length ; i++){
    			if(sels[i].value == "-1"){
    				nosel++;
    			}
    		}
    		
    		if(nosel!=1){
    			alert("必须有且只有一个维度可以被设置成未选择！");
    			return false;
    		}
    		return true ;
    	}
		
		function submitSel(){
			if(checkseldim()){
		      	  var seldims = document.getElementsByName("seldim");
		      	  var offsetdim ;
		      	  var str="";
		      	  for(i=0 ;i<seldims.length ; i++){
		      	  	 if(seldims[i].value == "-1"){
		      	  	 	offsetdim=seldims[i].id;
		      	  	 }
		      	  	 else{
		      	  	 	str += seldims[i].id+"="+seldims[i].value +",";
		      	  	 }
		      	  }
		      	  
		      	  var seltgs = document.getElementsByName("seltg");
		      	  var tgstr = "";
		      	  for(i=0 ;i<seltgs.length;i++){
		      	  	if(seltgs[i].checked){
		      	  		tgstr += seltgs[i].value+",";
		      	  	}
		      	  }
		      	  
		      	  var charttype = document.all.selcharttype.value ;
		      	  var datetype = document.all.seldatatype.value ;
		      	  charttype = charttype+"$"+datetype;
		      	  
		      	  var multich = document.all.multichart;
		      	  if(document.all.multichart[0].checked){
		      	  	multich = document.all.multichart[0].value ;
		      	  }
		      	  else{
		      	  	multich = document.all.multichart[1].value ;
		      	  }
		      	  
		      	  var pngwidth = document.all.pngwidth.value ;
		      	  var maxvalue = document.all.maxvalue.value ;
		      	  var showvalue = document.all.showvalue.value ;
		      	  var pictitle = document.all.pictitle.value ;
		      	  var piccolor = document.all.piccolor.value ;
		      	  var xqingxie = document.all.xqingxie.value ;
		      	 if(document.all.flowpage.value=="flowpage"){
					 parent.etlchartFrame.showChart(charttype,offsetdim,str,tgstr,multich,pngwidth,maxvalue,showvalue,pictitle,piccolor,xqingxie);
				  } else {
		      	  	self.opener.showChart(charttype,offsetdim,str,tgstr,multich,pngwidth,maxvalue,showvalue,pictitle,piccolor,xqingxie);
		      	  }
		      	  window.close();
			}
		}
    </SCRIPT>
	</head>

	<body>
		<input type="hidden" name="flowpage" value="<%=flowpage%>">
		请选择图表定制条件：
		<table width="100%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#A9C0E5" align="center">
			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="center" width="110">
					选择图表类型：
				</td>
				<td bgcolor="#FFFFFF" align="left">
					<select name="selcharttype">
						<c:forEach items="${graphTypeList}" var="gp">
							<option value="${gp[0]}">
								${gp[1]}
							</option>
						</c:forEach>
					</select>
					&nbsp;
					<select name="seldatatype">
						<option value="default">
							默认统计图
						</option>
						<option value="ne-time">
							三维统计-维度X时间统计图
						</option>
						<option value="time-ne">
							三维统计-时间X维度统计图
						</option>
					</select>
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="center" width="110">
					维度：
				<td bgcolor="#FFFFFF" align="left">
					<c:forEach items="${dimvalues}" var="dm">
						<div style="float: left; margin-right: 5px">
							${dm[1]}:
							<select id="${dm[0]}" name="seldim">
								<OPTION value="-1">
									未选择
								</OPTION>
								<c:forTokens items="${dm[2]}" delims="," var="dmv">
									<option value="${dmv}">
										${dmv}
									</option>
								</c:forTokens>
							</select>
							&nbsp;
						</div>
					</c:forEach>
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="center" width="110">
					指标：
				</td>
				<td bgcolor="#FFFFFF" align="left">
					<c:forEach items="${tglist}" var="tg">
						<div style="float: left; margin-right: 5px">
							<input type="checkbox" name="seltg" value="${tg.value}">
							${tg.label} &nbsp;
						</div>
					</c:forEach>
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="center" width="110">
					多图显示：
				</td>
				<td bgcolor="#FFFFFF" align="left">
					<input type="radio" value="0" name="multichart" checked>
					否
					<input type="radio" value="1" name="multichart">
					是
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" align="center" width="110">
					定制图表：
				</td>
				<td bgcolor="#FFFFFF" align="left">
					y轴最小值:
					<input type="text" value="" name="maxvalue" style="width: 50">
					&nbsp; 图片宽度:
					<input type="text" value="" name="pngwidth" style="width: 50">
					&nbsp; 图片显示数值:
					<select name="showvalue">
						<option value="true">
							是
						</option>
						<option vlaue="false">
							否
						</option>
					</select>
					&nbsp; 图片标题：
					<input type="text" value="" name="pictitle" style="width: 150">
					&nbsp; 图片背景色:
					<input type="text" value="" name="piccolor" style="width: 100">
					&nbsp; X轴文字倾斜值
					<input type="text" value="" name="xqingxie" style="width: 50">
				</td>
			</tr>
			<tr class="td-02">
				<td bgcolor="#FFFFFF" colspan="2" align="right">
					<input type="button" value="确定" onclick="submitSel();" class="butt">
				</td>
			</tr>
		</TABLE>


		<c:if test="${GraphModel!=null}">

			<script type="text/javascript">
	  	 var graphtype = '${GraphModel.graphType}';
	  	 var index = graphtype.indexOf("$");
	  	 if(index!=-1){
	  	 	document.all.selcharttype.value = graphtype.substring(0,index);
	  	 	document.all.seldatatype.value = graphtype.substring(index+1);
	  	 }else{
	  	 	document.all.selcharttype.value = graphtype;
	  	 }
	  	 
		 <c:forEach items="${GraphModel.otherDimension}" var="od">
  	 	 	var dimtmp = document.getElementById('${od.key}');
  	 	 	if(dimtmp){
  	 	 		dimtmp.value="${od.value}";
  	 	 	}
  		 </c:forEach>
		 	  	 
		
		 <c:forTokens items="${GraphModel.choicetarget}" delims="," var="ct">
		  	  var cttgs = document.getElementsByName('seltg');
		  	  for(i=0 ; i<cttgs.length ; i++){
		  	  	if(cttgs[i].value == '${ct}'){
		  	  		cttgs[i].checked=true;
		  	  	}
		  	  }
		  </c:forTokens>
	  </script>
		</c:if>
		<c:if test="${multichart != null}">
			<c:if test="${multichart == '1'}">
				<script type="text/javascript">
				document.all.multichart[1].checked = true;
			</script>
			</c:if>
		</c:if>

	</body>
</html>
