<%@ page contentType="text/html; charset=GBK" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String picurl = basePath + "/servlet/ChartProviderSvl;jsessionid=" + request.getSession().getId()
			+ "?imgid=0.png";
	String showactive = request.getParameter("showactive");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>图表分析</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link href="<%=path%>/include/css/css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
			function change(){
				if(document.all.che.checked){
					document.all.tr1.style.display="";
				} else {
					document.all.tr1.style.display="none";
				}
			}
		</script>
	</head>

	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		style="font: 14px">
		<table width="100%" height="100%" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr height="10">
				<td>
					&nbsp;&nbsp;
					<input type="checkbox" name="che" value="0" onclick="change();" checked="checked">显示/隐藏
					说明：点中为显示，没点中为隐藏
				</td>
			</tr>
			<tr height="200" id="tr1">
				<td>
					<table width="98%" height="100%" border="0" align="center"
						cellpadding="0" cellspacing="0" bgcolor="#A9C0E5">
						<tr class="td-02">
							<td bgcolor="#FFFFFF">
								<iframe name="etlchartSelect" marginwidth=0 marginheight=0
									src="" align="left" frameborder=0 width="100%" height="100%"
									scrolling="auto"></iframe>
								<input type="hidden" name="showactive" value="<%=showactive%>">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="98%" height="100%" border="0" align="center"
						cellpadding="0" cellspacing="0" bgcolor="#A9C0E5">
						<tr class="td-02">
							<td bgcolor="#FFFFFF">
								<iframe name="etlchartFrame" marginwidth=0 marginheight=0
									src="<%=path%>/etlChartVM.do?act=CHART_SHOW&ShowFirstGraphModel=y&flowpage=flowpage"
									align="left" frameborder=0 width="100%" height="100%"
									scrolling="auto"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>

		</table>
		<script type="text/javascript">
			if(document.all.showactive.value=="0"){
				document.all.che.checked=false;
				document.all.tr1.style.display="none";
			}
		</script>
	</body>
</html>
