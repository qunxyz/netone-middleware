<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>添加项4</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
						<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			//history.go(-1);
			var filtertype = document.all.filtertype.value;
			if(filtertype=="0"){
				document.additem.action="<%=path%>/NextAnalyse.do?task=Step1";
			} else {
				document.additem.action="<%=path%>/NextAnalyse.do?task=Step2";
			}
			document.additem.submit();
		}
		//下一步脚本
		function nextsubmit(){
			var active = document.all.active.value;
			if(active=="true"){
				document.additem.action="<%=path%>/NextAnalyse.do?task=Step5";
			} else {
				document.all.showactive.value="";
				document.all.selcharttype.value="";
				document.all.seldatatype.value="";
				document.all.seldim.value="";
				document.all.str.value="";
				document.all.seltg.value="";
				document.all.tgstr.value="";
				document.all.multichart.value="";
				document.all.maxvalue.value="";
				document.all.pngwidth.value="";
				document.all.showvalue.value="";
				document.additem.action="<%=path%>/NextAnalyse.do?task=Done";
			}
			document.additem.submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<input type="hidden" name="parentid" value="${fo.parentid}">
			<input type="hidden" name="pagepath" value="${fo.pagepath}">
			<!-- Zero.jsp -->
			<input type="hidden" name="name" value="${fo.name}">
			<input type="hidden" name="naturalname" value="${fo.naturalname}">
			<!-- BeforeFrist.jsp -->
			<input type="hidden" name="timetype" value="${fo.timetype}">
			<input type="hidden" name="selpoint1" value="${fo.selpoint1}">
			<input type="hidden" name="timelevelinfo" value="${fo.timelevelinfo}">
			<input type="hidden" name="selpoint1Text" value="${fo.selpoint1Text}">
			<!-- Frist.jsp -->
			<input type="hidden" name="dynamicDim" value="${fo.dynamicDim}">
			<input type="hidden" name="preps" value="${fo.preps}">
			<input type="hidden" name="selectedDimValue"
				value="${fo.selectedDimValue}">
			<input type="hidden" name="levelname" value="${fo.levelname}">
			<input type="hidden" name="dimResultValue"
				value="${fo.dimResultValue}">
			<input type="hidden" name="type" value="${fo.type}">
			<input type="hidden" name="dimTreeList" value="${fo.dimTreeList}">
			<input type="hidden" name="choice" value="${fo.choice}">
			<input type="hidden" name="dimTreeDiv" value="${fo.dimTreeDiv}">
			<input type="hidden" name="dimResult" value="${fo.dimResult}">
			<!-- Step.jsp -->
			<input type="hidden" name="tgids" value="${fo.tgids}">
			<input type="hidden" name="tgnames" value="${fo.tgnames}">
			<input type="hidden" name="seltgvalue" value="${fo.seltgvalue}">
			<input type="hidden" name="tgGroup" value="${fo.tgGroup}">
			<input type="hidden" name="tglistdiv" value="${fo.tglistdiv}">
			<input type="hidden" name="targetResult" value="${fo.targetResult}">
			<!-- Step1.jsp -->
			<input type="hidden" name="filtertype" value="${fo.filtertype}">
			<!-- Step2.jsp -->
			<input type="hidden" name="allchkid" value="${fo.allchkid}">
			<input type="hidden" name="alltop" value="${fo.alltop}">
			<input type="hidden" name="alltopvalue" value="${fo.alltopvalue}">
			<input type="hidden" name="allalarm" value="${fo.allalarm}">
			<input type="hidden" name="alltxtalarm" value="${fo.alltxtalarm}">
			<input type="hidden" name="tgfiltSqlvalue" value="${fo.tgfiltSqlvalue}">
			<!-- Step3.jsp -->
			<input type="hidden" name="exterValue" value="${fo.exterValue}">
			<input type="hidden" name="seldimvalue" value="${fo.seldimvalue}">
			<input type="hidden" name="timelevel" value="${fo.timelevel}">
			<input type="hidden" name="selpoint2" value="${fo.selpoint2}">
			<input type="hidden" name="hour" value="${fo.hour}">
			<input type="hidden" name="grownum" value="${fo.grownum}">
			<input type="hidden" name="selecttime1" value="${fo.selecttime1}">
			<input type="hidden" name="selecttime2" value="${fo.selecttime2}">
			<input type="hidden" name="unit" value="${fo.unit}">
			<input type="hidden" name="timeResult" value="${fo.timeResult}">
			<input type="hidden" name="timeResults" value="${fo.timeResults}">
			<!-- Step5.jsp -->
			<input type="hidden" name="showactive" value="${fo.showactive}">
			<input type="hidden" name="selcharttype" value="${fo.selcharttype}">
			<input type="hidden" name="seldatatype" value="${fo.seldatatype}">
			<input type="hidden" name="seldim" value="${fo.seldim}">
			<input type="hidden" name="seltg" value="${fo.seltg}">
			<input type="hidden" name="str" value="${fo.str}">
			<input type="hidden" name="tgstr" value="${fo.tgstr}">
			<input type="hidden" name="multichart" value="${fo.multichart}">
			<input type="hidden" name="maxvalue" value="${fo.maxvalue}">
			<input type="hidden" name="pngwidth" value="${fo.pngwidth}">
			<input type="hidden" name="showvalue" value="${fo.showvalue}">
			<input type="hidden" name="pictitle" value="${fo.pictitle}">
			<input type="hidden" name="piccolor" value="${fo.piccolor}">
			<input type="hidden" name="xqingxie" value="${fo.xqingxie}">
			<!-- Done.jsp -->
			<input type="hidden" name="act" value="${fo.act}">
			<input type="hidden" name="timespecial" value="${fo.timespecial}">
			<input type="hidden" name="sqlview" value="${fo.sqlview}">
			<input type="hidden" name="allchkid2" value="${fo.allchkid2}">
			<input type="hidden" name="allorder" value="${fo.allorder}">
			<input type="hidden" name="tgfiltvalue" value="${fo.tgfiltvalue}">
			<!-- Done2.jsp -->
			<input type="hidden" name="actcondition" value="${fo.actcondition}">
			<!-- Done3.jsp -->
			<input type="hidden" name="acturl" value="${fo.acturl}">
			<!-- Finish.jsp -->
			<input type="hidden" name="lsh" value="${fo.lsh}">
			<input type="hidden" name="tgothername" value="${fo.tgothername}">

			<%@ include file="/include/page/PageHeadInfo.jsp"%>


			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD>
							<TABLE WIDTH="100%" BORDER="0" CELLPADDING="1" CELLSPACING="0">
								<TR CLASS="OecBgColorDark">
									<TD>
										&nbsp;
									</TD>
									<TD WIDTH="100%" valign="center">
										<FONT CLASS="OecGlobalPageTitle">内容主题 STEP</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG
												SRC="<%=basePath%>/include/image/helpsb.gif" ALT="帮助"
												BORDER="0"> </A>
									</TD>
									<TD>
										&nbsp;
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%">
								<TR>
									<TD ROWSPAN="2" VALIGN="TOP">
										<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
											<TR>
												<TD VALIGN="TOP" CLASS="OecBgColorDark">

												</TD>
											</TR>
										</TABLE>
									</TD>
									<TD ROWSPAN="2" VALIGN="TOP">
										&nbsp;&nbsp;&nbsp;
									</TD>
									<TD ALIGN="RIGHT" valign="top" width="100%">
										<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
											<TR>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
														onClick="javascript:forward()">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
														onClick="javascript:nextsubmit()">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="取消"
														onClick="javascript:cancelsubmit()">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 2 步, 共 10 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 切片分析
					是将多维度的数据进行切片运算,帮助我们方便的从某个侧面来观察数据,如果选择是 那么我们将进一步对之前选择过的数据模型进行切片处理</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<div
					style="width:99%;height:39%;margin:0px; margin-top:0px; border: 0 solid #A9C0E5">
					<table width="100%" border="0" cellpadding="0" cellspacing="1">

						<tr>
							<td bgcolor="#FFFFFF" align="left">
								继续切片处理:
								<select name="active">
									<!-- 
									<option value="true" ${fo.active==true?
										"selected":fo.showactive==""?"selected":"" }>
										是
									</option>
									 -->
									<option value="false" ${fo.active==false?"selected":"" }>
										否
									</option>
								</select>
							</td>
						</tr>
					</table>
				</div>
				<!-- End --Body---- 功能内容定义  --->
			</DIV>
			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD ALIGN="RIGHT" valign="top" width="100%">
							<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
								<TR>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
											onClick="javascript:forward()">
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
											onClick="javascript:nextsubmit()">
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="取消"
											onClick="javascript:cancelsubmit()">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
