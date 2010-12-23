<%@ page contentType="text/html; charset=GBK" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String picurl = basePath + "/servlet/ChartProviderSvl;jsessionid=" + request.getSession().getId()
			+ "?imgid=0.png";
	String chkid = request.getParameter("chkid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>表格显示</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">

		<script type="text/javascript">
    	var pagecontext = "<%=path%>";
    </script>
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
		<script type="text/javascript"
			src="<%=path%>/include/js/etl/tableview.js"></script>
		<script type="text/javascript">
			var frm = null;
			var timer = null;
			function DownLoadPic() {
			
				frm = document.createElement("IFRAME");
				frm.style.display = "none";
				document.body.appendChild(frm);
				
				frm.contentWindow.location.href = '<%=picurl%>';
				timer = setInterval(checkload, 200);
			}
			function checkload() {
				if (frm.contentWindow.document.readyState == "complete") {
					frm.contentWindow.document.execCommand("SaveAs");
					clearInterval(timer);
				}
				document.body.removeChild(frm);
			}
			function filter(){
		   		window.open("NextFilter.do?task=filter1&openid=${param.chkid}");
		   	}
		   	function filter2(){
		   		window.open("NextFilter.do?task=filter3&openid=${param.chkid}");
		   	}
		</script>
	</head>

	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		style="font: 14px">

		<c:if test="${errsmg != null}">
			<script type="text/javascript">
			alert('${errsmg}');
		</script>
		</c:if>


		<form action="<%=path%>/flowpage.do" name="tableform" method="post"
			style="margin: 0px">
			<input type="hidden" name="flowpage2" value="${flowpage }">
			<input type="hidden" name="endTime2" value="${endTime}">
			<input type="hidden" name="dataModelid2" value="${dataModelid}">
			<input type="hidden" name="timelevel2" value="${timelevel}">
			<input type="hidden" name="hour2" value="${hour}">
			<input type="hidden" name="chkid" value="${param.chkid}">
			<input type="hidden" name="act" value="${param.act}">
			<table width="100%" cellspacing="0" border="1" cellpadding="2"
				bordercolordark="#FFFFFF" bordercolorlight="#999999">

				<tr class='tdheadline'>
					<td>
						${param.oneface}
						<c:if test="${param.oneface!='yes'}">
							<input name="buttonshowchart" type="button" value="切片分析"
								onclick="btnshowchart();" class="butt">
						</c:if>
						<c:if test="${param.forcast!='yes'}">
							<input name="buttonforecast" type="button" value="预测分析"
								onclick=" btnforecast();" class="butt">
						</c:if>
						<c:if test="${param.excel!='yes'}">
							<input name="buttonexcel" type="button" value="导出Excel"
								onclick="btnexcel();" class="butt">
						</c:if>

						<c:if test="${flowpage=='flowpage'}">
							<c:if test="${param.superact!='yes'}">
								<input type="button" value="高级交互" onclick="filter();"
									class="butt">
							</c:if>
							<c:if test="${param.normalact!='yes'}">
								<input type="button" value="交互分析" onclick="filter2();"
									class="butt">
							</c:if>
							<input type="hidden" name="showactive" value="${display}">
						</c:if>
						<input name="buttontgfilter" type="button" value="指标过滤"
							onclick="btntgfilter();" class="butt" style="display: none">
						<input name="buttonpic" type="button" value="导出图片"
							onclick="DownLoadPic();" class="butt" style='display: none'>
					</td>
				</tr>
				<tr>
					<td>
						<div style="width: 100%; height: 100%; overflow: auto">
							<table width="100%" cellspacing="0" border="1" cellpadding="2"
								bordercolordark="#FFFFFF" bordercolorlight="#999999">
								<!-- 打印标题-->
								<tr class="td-bg01" align="center">
									<c:forEach items="${titlelist}" var="titlemap">
										<c:forEach items="${titlemap}" var="map">
											<td height="21" bgcolor="#FFFFFF">
												${map.value}&nbsp;
											</td>
										</c:forEach>
									</c:forEach>
								</tr>
								<!-- 打印表格数据-->
								<c:forEach items="${valuelist}" var="valuemap">
									<tr class="td-02" align="center">
										<c:forEach items="${valuemap}" var="map">
											<td height="21" bgcolor="#FFFFFF">
												<c:set var="islink" value="no"></c:set>
												<c:forEach items="${actmap}" var="amap">
													<c:if test="${map.key==amap.key}">
														<c:set var="aparam" value=""></c:set>
														<c:if test="${amap.value[2]!=null}">
															<c:forEach items="${valuemap}" var="maps">
																<c:if test="${maps.key==amap.value[2]}">
																	<c:set value="${maps.value}" var="aparam"></c:set>
																</c:if>
															</c:forEach>
														</c:if>
														<a href="${amap.value[1]}'${aparam}'" target="_blank">${map.value}</a>
														<c:set var="islink" value="yes"></c:set>
													</c:if>
												</c:forEach>
												<c:if test="${islink=='no'}">
													${map.value}&nbsp;
												</c:if>
											</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</table>
							<script type="text/javascript">
						    	var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.tableform);
						    	pginfo.write();
						    </script>
						</div>
					</td>
				</tr>

			</table>
		</form>
		<div style="height: 1px;">
			<form action="<%=path%>/etlVMCreate.do?chkid=<%=chkid%>"
				name="vmcreateform" method="post">
				<input type="hidden" value="" name="act" value="tableView">
				<input type="hidden" value="" name="tgfiltvalue">
				<input type="hidden" value="" name="forecastvalue">
				<input type="hidden" value="" name="modifyvalue">

				<input type="hidden" name="flowpage" value="${flowpage }">
				<input type="hidden" name="endTime" value="${endTime}">
				<input type="hidden" name="dataModelid" value="${dataModelid}">
				<input type="hidden" name="timelevel" value="${timelevel}">
				<input type="hidden" name="hour" value="${hour}">
			</form>
		</div>
		<c:if test="${valuelist == null}">
			<script type="text/javascript">
			    	document.all.buttonforecast.disabled = true;
			    	document.all.buttontgfilter.disabled = true;
			    	document.all.buttonexcel.disabled = true;
			    	document.all.buttonshowchart.disabled = true;
			    	document.all.buttonexcel.disabled = true;
			    	document.all.buttonpic.disabled = true;
		    </script>
		</c:if>
		<c:if test="${ShowFirstGraphModel != null}">
			<script type="text/javascript">
				if(document.all.flowpage.value==""){
	    			var etlchartFrame = parent.document.frames["etlchartFrame"];
			    	etlchartFrame.location.href="<%=path%>/etlChartVM.do?act=CHART_SHOW&ShowFirstGraphModel=y";
			    }
		    </script>
		</c:if>


		<script type="text/javascript">
			var errmsg = '${errmsg}';
			if(errmsg){
				alert(errmsg);
			}
			if(document.all.flowpage.value==""){
				parent.document.all.btnAnalyse.disabled = false ;
				parent.document.all.waiting.style.display='none';
			}
			
		</script>

	</body>
</html>
