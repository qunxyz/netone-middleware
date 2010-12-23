<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns:v>
	<head>
		<title>
			无标题文档
		</title>
		<style>
v\:*{behavior:url(#default#VML);}
</style>
	</head>
	<SCRIPT src="/biWeb/include/js/datamodel/design/targetcol.js" type="text/javascript"></script>

	<SCRIPT src="/biWeb/include/js/datamodel/design/biModel.js" type="text/javascript"></script>
	<link href="/biWeb/include/css/style.css" rel="stylesheet" type="text/css">

	<!-- 接受从树图传过来的 模型ID，模型名称参数-->
	<%String modelID = request.getParameter("id");

			String modelName = request.getParameter("name");

			modelID = "id111";

			modelName = "modelname111";

			%>
	<body oncontextmenu="return false">

		<form action="test.do" method="POST" name="mainForm">
			<TABLE>
				<TR>
					<TD>

						<v:rect style="POSITION:absolute;Z-INDEX:0;LEFT:0px;TOP:10px;width:200;height:500;">
							<table>
								<tr>
									<td>
										数据源A-db
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a name="table1" href="#" onclick="new trackFactory('dataNode','table1','ds1.table1','ds1');">
											库表1
										</a>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','table2','ds1.table2','ds1');">
											库表2
										</a>
									</td>
								</tr>


								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','table3','ds1.table3','ds1');">
											库表3
										</a>
									</td>
								</tr>

								<tr>
									<td>
										数据源B-xml
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','库表4','4');">
											库表4
										</a>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','库表5','5');">
											库表5
										</a>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','库表6','6');">
											库表6
										</a>
									</td>
								</tr>

								<tr>
									<td>
										数据源C-...
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','库表7','7');">
											库表7
										</a>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','库表8','8');">
											库表8
										</a>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('dataNode','库表9','9');">
											库表9
										</a>
									</td>
								</tr>
								<!-- -->
							</table>
						</v:rect>
					</TD>
					<TD valign="top">
						<v:rect style="POSITION:absolute;Z-INDEX:0;LEFT:202px;TOP:10px;width:700;height:500;">
							<table>
								<tr>
									<td>

										<a href="#" onclick="new trackFactory('beenLine','');">
											直线
										</a>
										&nbsp;&nbsp;
										<a href="#" onclick="new trackFactory('ZLine','');">
											折线
										</a>
										&nbsp;&nbsp;
										<a href="#" onclick="alert('指标配置窗口')">
											指标配置
										</a>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="#" onclick="saveBusModel()">
											保存
										</a>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;多维业务数据模型
									</td>
								</tr>
							</table>
						</v:rect>
					</TD>
				</TR>
			</TABLE>

			<span id="dataNodeMenu" name="menu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC">
				<input id="filter" class=workMune onclick='dataNodeMenu.style.display="none";showDataSetFilter(this.form.currentNodeID.value);' type="button" value="过滤条件">
				<BR>
				<input id="delete" class=workMune onclick='dataNodeMenu.style.display="none";new  deleteEvent("action");' type="button" value="删   除">
				<hr style="WIDTH: 50px" color="white" SIZE="0">
				<input class=workMune onclick='dataNodeMenu.style.display="none"' type="button" value="取   消">
			</span>

			<span id="lineMenu" name="trackLineMenu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC">
				<input id="setProperty" onclick='lineMenu.style.display="none";lineAttribute()' class=workMune type="button" value="属性">
				<br>
				<input id="delete" class=workMune onclick='lineMenu.style.display="none";new  deleteEvent("line");' type="button" value="删除">
				<hr style="WIDTH: 50px" color="white" SIZE="0">
				<input class=workMune onclick='lineMenu.style.display="none";' type="button" value="取   消">
			</span>


			<input id="defineActionObjName" type="hidden">
			<input id="defineLineName" type="hidden">
			<input id="defineLineTrueId" type="hidden">
			<input id="defineActionTrueId" type="hidden">
			<input id="relationData" type="hidden">
			<input id="newProcess" name="newProcessAttribute" type="hidden" value="processId,processname,extendName&extendValue|extendName1&extendValue1|">
			<input id="currentNodeID" type="hidden">

			<!-- 隐藏域保存当前业务模型id,业务模型名称-->
			<input id="modelID" type="hidden" value="<%=modelID%>">
			<input id="modelName" type="hidden" value="<%=modelName%>">

			<script>
document.onmousedown = engage
document.onmousemove = dragIt
document.onmouseup = release
</script>
		</form>
	</body>
</html>
